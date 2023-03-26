package com.farhandev.movieapp.ui.mainscreen.fragement.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhandev.movieapp.R
import com.farhandev.movieapp.databinding.ItemGenreBinding
import com.farhandev.movieapp.databinding.SearchItemBinding
import com.farhandev.movieapp.service.network.response.DetailMovieResponse
import com.farhandev.movieapp.service.network.response.GenresItem
import com.farhandev.movieapp.util.NetworkConfig

class ListGenreAdapter(val listGenre: ArrayList<GenresItem>,
                       val listener : OnAdapterListener)
    : RecyclerView.Adapter<ListGenreAdapter.ViewHolder>(){

    fun setGenre(genre: List<GenresItem>){
        listGenre.clear()
        listGenre.addAll(genre)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListGenreAdapter.ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListGenreAdapter.ViewHolder, position: Int) {
        holder.bind(listGenre[position])
    }

    override fun getItemCount(): Int = listGenre.size

    inner class ViewHolder(val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(genre : GenresItem){
            with(binding){

                txtGenre.text = genre.name

                itemView.setOnClickListener {
                    listener.OnClick(genre)
                }
            }
        }
    }

    interface OnAdapterListener{
        fun OnClick(genre: GenresItem)
    }
}