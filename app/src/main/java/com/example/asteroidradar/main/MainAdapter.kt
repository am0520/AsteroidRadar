package com.example.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.databinding.AsteroidItemBinding

class MainAdapter(private val onClick: (Asteroid) -> Unit) :
    ListAdapter<Asteroid, MainAdapter.AsteroidViewHolder>(AsteroidDiffCallBack()) {

    class AsteroidViewHolder private constructor(private val binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): AsteroidViewHolder {
                val binding = AsteroidItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return AsteroidViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener { onClick(asteroid) }
        holder.bind(asteroid)
    }

}

class AsteroidDiffCallBack : DiffUtil.ItemCallback<Asteroid>() {

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}
