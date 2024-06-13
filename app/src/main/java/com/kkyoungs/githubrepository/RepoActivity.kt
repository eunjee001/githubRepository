package com.kkyoungs.githubrepository

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkyoungs.githubrepository.databinding.ActivityRepoBinding
import com.kkyoungs.githubrepository.model.Repo
import com.kkyoungs.githubrepository.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRepoBinding
    private lateinit var adapter: RepoAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("username") ?: return

        binding.usernameTextView.text= userName
        adapter = RepoAdapter()

        binding.repoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RepoActivity)
        }
        listRepo(userName)
    }

    private fun listRepo(username : String){
        val githubService = retrofit.create(GithubService::class.java)
        githubService.listRepos("square").enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e("MainActivity", response.body().toString())
                binding.usernameTextView.text = username
                adapter.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }

        })
    }
}