package it.alexm.beers.data.api

import it.alexm.beers.data.vo.Beer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersService {

    @GET("beers")
    suspend fun getPagedBeers(
        @Query("page") page: Int,
        @Query("beer_name") beerName: String?,
        @Query("brewed_after") start: String?,
        @Query("brewed_before") end: String?
    ): Response<List<Beer>>
}