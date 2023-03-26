package com.farhandev.movieapp.ui.mainscreen.fragement.search

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhandev.movieapp.databinding.FragmentSearchBinding
import com.farhandev.movieapp.service.local.MovieDb
import com.farhandev.movieapp.service.local.MovieModel
import com.farhandev.movieapp.service.network.response.DetailMovieResponse
import com.farhandev.movieapp.service.network.response.GenresItem
import com.farhandev.movieapp.ui.detail.DetailMoviesActivity
import com.farhandev.movieapp.ui.mainscreen.fragement.search.adapter.ListGenreAdapter
import com.farhandev.movieapp.ui.mainscreen.fragement.search.adapter.ListMovieSearchAdapter
import com.farhandev.movieapp.util.NetworkConfig

class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var adapter: ListMovieSearchAdapter
    private lateinit var adapterGenre: ListGenreAdapter
    private lateinit var db: MovieDb

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchBinding = FragmentSearchBinding.inflate(inflater,container, false)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MovieDb(requireActivity())
        searchViewModel.getGenre()

        adapter = ListMovieSearchAdapter(arrayListOf(), object : ListMovieSearchAdapter.OnAdapterListener{
            override fun OnClick(movie: DetailMovieResponse) {
                val movies = MovieModel(id = null,
                    backdropPath = movie.backdropPath,
                    title = movie.title,
                    rating = movie.voteAverage.toString(),true)
                db.movieDao().insertMovie(movies)
                Toast.makeText(requireActivity(), "Add to watch list success", Toast.LENGTH_SHORT).show()
            }
        }, object : ListMovieSearchAdapter.OnAdapterListenerToDetail{
            override fun OnClick(movie: DetailMovieResponse) {
                startActivity(
                    Intent(requireActivity(), DetailMoviesActivity::class.java)
                    .putExtra("movie", movie))
            }

        })

        adapterGenre = ListGenreAdapter(arrayListOf(), object : ListGenreAdapter.OnAdapterListener{
            override fun OnClick(genre: GenresItem) {
                searchViewModel.getMovieByGenre(genre.id.toString())
            }

        })

        searchViewModel.listMovie.observe(requireActivity()) { listMovie ->
            adapter.setListMovie(listMovie)

            if (listMovie.isNullOrEmpty())
                searchBinding.ivEmptyState.visibility = View.VISIBLE
            else
                searchBinding.ivEmptyState.visibility = View.GONE
        }

        searchViewModel.isLoading.observe(requireActivity()) { isLoading ->
            searchBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        with(searchBinding){

            ivBtnSearch.setOnClickListener {
                var movie = etSearch.text.toString()
                when{
                    movie.isNullOrEmpty() -> {
                        Toast.makeText(requireActivity(), "Search Colum can't be null", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        progressBar.visibility = View.VISIBLE
                        searchMovie(etSearch.text.toString())
                    }
                }
            }

            rvSearchMovie.adapter = adapter
            rvSearchMovie.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvSearchMovie.setHasFixedSize(true)

            rvGenre.adapter = adapterGenre
            rvGenre.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL,false)
            rvGenre.setHasFixedSize(true)

            searchViewModel.listGenre.observe(requireActivity()){
                adapterGenre.setGenre(it)
            }
        }
    }

    private fun searchMovie(movie: String) {
        searchViewModel.getSearchMovie(movie,NetworkConfig.API_KEY)
    }
}