package com.farhandev.movieapp.service.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PopularResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<DetailMovieResponse>,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
) : Parcelable
