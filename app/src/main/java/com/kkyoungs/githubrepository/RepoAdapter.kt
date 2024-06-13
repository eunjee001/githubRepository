package com.kkyoungs.githubrepository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kkyoungs.githubrepository.databinding.ItemRepoBinding
import com.kkyoungs.githubrepository.model.Repo

class RepoAdapter : ListAdapter<Repo, RepoAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val viewBinding: ItemRepoBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(repo : Repo){
            viewBinding.repoNameTextView.text = repo.name
            viewBinding.descriptionTextView.text= repo.description
            viewBinding.startCountTextView.text = repo.starCount.toString()
            viewBinding.forkCountTextView.text = repo.forkCount.toString()
        }
    }
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Repo>(){
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])

    }
}