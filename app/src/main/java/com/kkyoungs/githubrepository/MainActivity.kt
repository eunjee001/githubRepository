package com.kkyoungs.githubrepository

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val githubService = retrofit.create(GithubService::class.java)
        githubService.listRepos("square").enqueue(object : Callback<List<Repo>>{
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e("MainActivity", response.body().toString())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }

        })

        githubService.searchUsers("squar").enqueue(object :Callback<UserDto>{
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e("MainActivity", "Search User" +response.body().toString())
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
            }

        })
    }
}