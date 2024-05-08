package com.kkyoungs.githubrepository

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.kkyoungs.githubrepository.databinding.ActivityRepoBinding

class RepoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRepoBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}