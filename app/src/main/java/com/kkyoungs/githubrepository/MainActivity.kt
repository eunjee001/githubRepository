package com.kkyoungs.githubrepository

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkyoungs.githubrepository.databinding.ActivityMainBinding
import com.kkyoungs.githubrepository.model.Repo
import com.kkyoungs.githubrepository.model.UserDto
import com.kkyoungs.githubrepository.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    lateinit var userAdapter : UserAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val handler = Handler(Looper.getMainLooper())
    private var searchFor : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        githubService.listRepos("square").enqueue(object : Callback<List<Repo>>{
//            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
//                Log.e("MainActivity", response.body().toString())
//            }
//
//            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
//            }
//
//        })


        userAdapter = UserAdapter{
            val intent = Intent(this@MainActivity, RepoActivity::class.java)
            intent.putExtra("username", it.username)
            startActivity(intent)
        }

        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        val runnable = Runnable{
            searchUser(searchFor)
        }
        binding.searchEditText.addTextChangedListener {
            searchFor = it.toString()
            //시간을 잠깐 멈췄을때 대기 하고 있는 것.
            handler.removeCallbacks(runnable)
            handler.postDelayed(
                runnable,
                300
            )
        }
    }

    private fun searchUser(query : String){
        val githubService = retrofit.create(GithubService::class.java)

        githubService.searchUsers(query).enqueue(object :Callback<UserDto>{
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e("MainActivity", "Search User" +response.body().toString())
                userAdapter.submitList(response.body()?.items)

            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(this@MainActivity, "에러 발생!", Toast.LENGTH_SHORT).show()
            }

        })
    }


}