package com.farhandev.movieapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.farhandev.movieapp.databinding.ActivityDetailMoviesBinding
import com.farhandev.movieapp.service.network.response.DetailMovieResponse
import com.farhandev.movieapp.service.network.response.ResultsItem
import com.farhandev.movieapp.util.NetworkConfig
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class DetailMoviesActivity : AppCompatActivity() {
    private lateinit var detailMovieBinding: ActivityDetailMoviesBinding
    private val detailMovieViewModel: DetailMovieViewModel by viewModels()
    private lateinit var adapter: ReviewsAdapter
    private lateinit var movie: DetailMovieResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailMovieBinding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        setContentView(detailMovieBinding.root)

        movie = intent.getParcelableExtra("movie")!!
        detailMovieViewModel.getVideoUrl(movie.id.toString())
        detailMovieViewModel.getReviews(movie.id.toString())

        adapter = ReviewsAdapter(arrayListOf(),object : ReviewsAdapter.OnAdapterListener{
            override fun OnClick(movie: ResultsItem) {}

        })

        detailMovieViewModel.videoUrl.observe(this@DetailMoviesActivity){

            detailMovieBinding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(it[0].key!!, 0f)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    super.onStateChange(youTubePlayer, state)
                }
            })
        }

        detailMovieViewModel.reviews.observe(this@DetailMoviesActivity){
            adapter.setListMovie(it)
        }

        with(detailMovieBinding){
            Glide.with(this@DetailMoviesActivity)
                .load("${NetworkConfig.BASE_IMAGE_URL}${movie.backdropPath}")
                .into(ivBackdrop)

            Glide.with(this@DetailMoviesActivity)
                .load("${NetworkConfig.BASE_IMAGE_URL}${movie.posterPath}")
                .into(ivPosterPath)

            txtTitle.text = movie.title
            txtOverview.text = movie.overview
            txtRating.text = "Rating: ${movie.voteAverage}"
            txtReleaseDate.text = "Released ${movie.releaseDate}"

            rvReviews.adapter = adapter
            rvReviews.layoutManager = LinearLayoutManager(this@DetailMoviesActivity,LinearLayoutManager.VERTICAL,false)
            rvReviews.setHasFixedSize(true)
        }
    }
}