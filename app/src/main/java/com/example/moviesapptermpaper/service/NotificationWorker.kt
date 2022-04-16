package com.example.moviesapptermpaper.service

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.data.mappers.DataMapper
import com.example.data.network.RetrofitService
import com.example.data.util.*
import com.example.domain.entities.Movie
import com.example.domain.entities.MovieResponse
import com.example.moviesapptermpaper.R
import com.example.moviesapptermpaper.ui.details.DetailsActivity
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

/** Worker sends request to API, gets item
 * and creates notification with transition to DetailsActivity*/

class NotificationWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val movie = getMovie()
        createNotification(
            applicationContext,
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            DetailsActivity::class.java,
            R.drawable.ic_home,
            applicationContext.getString(R.string.we_got_new_movie_recommendation_for_you),
            "Check \"${movie.title}\"",
            movie
        )
        return Result.retry()
    }

    private suspend fun getMovie(): Movie {
        var movie: Movie? = null
        retrofitService.getTrendingMovies().enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val randomIndex = (0..19).random()
                    movie = (response.body() as MovieResponse).results[randomIndex]
                    movie = DataMapper.mapMovie(movie!!)
                    Log.d(TAG_NOTIFICATION_WORKER, "onResponse() success movie: $movie")
                }
                else Log.d(TAG_NOTIFICATION_WORKER, "onResponse() fail movie: $movie")
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG_NOTIFICATION_WORKER, "onFailure() movie: $movie")
            }
        })
        delay(5000)
        return movie!!
    }

    companion object {
        private lateinit var retrofitService: RetrofitService

        fun startWorker(context: Context, rs: RetrofitService) {
            retrofitService = rs
            val constraint = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val workRequest = PeriodicWorkRequest
                .Builder(NotificationWorker::class.java, 20, TimeUnit.HOURS)
                .setInitialDelay(1, TimeUnit.HOURS)
                .setConstraints(constraint)
                .addTag(TAG_NOTIFICATION_WORK_MANAGER)
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

}