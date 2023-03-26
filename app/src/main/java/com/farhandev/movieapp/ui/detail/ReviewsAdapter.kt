package com.farhandev.movieapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhandev.movieapp.R
import com.farhandev.movieapp.databinding.ItemReviewsBinding
import com.farhandev.movieapp.databinding.SearchItemBinding
import com.farhandev.movieapp.service.network.response.DetailMovieResponse
import com.farhandev.movieapp.service.network.response.ResultsItem
import com.farhandev.movieapp.service.network.response.ReviewResponse
import com.farhandev.movieapp.util.NetworkConfig

class ReviewsAdapter(val listReview: ArrayList<ResultsItem>, val listener : OnAdapterListener)
    : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>(){

    fun setListMovie(review: List<ResultsItem>){
        listReview.clear()
        listReview.addAll(review)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewsAdapter.ViewHolder {
        val binding = ItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewsAdapter.ViewHolder, position: Int) {
        holder.bind(listReview[position])
    }

    override fun getItemCount(): Int = listReview.size

    inner class ViewHolder(val binding: ItemReviewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(review : ResultsItem){
            with(binding){
                Glide.with(itemView.context)
                    .load("${NetworkConfig.BASE_IMAGE_URL}${review.authorDetails?.avatarPath}")
                    .into(binding.ivAuthor)

                txtAuthor.text = review.author
                txtComment.text = review.content
                txtRating.text = "Rating: ${review.authorDetails?.rating}"
            }
        }
    }

    interface OnAdapterListener{
        fun OnClick(movie: ResultsItem)
    }
}