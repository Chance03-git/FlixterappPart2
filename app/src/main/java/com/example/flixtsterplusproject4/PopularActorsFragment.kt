package com.example.flixtsterplusproject4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler.JSON
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class PopularActorsFragment : Fragment(), OnListFragmentInteractionListener {

    private val actors = mutableListOf<Actor>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client["https://api.themoviedb.org/3/person/popular", params, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                progressBar.hide()
                Log.d("PopularActorsFragment", json.jsonObject.toString())
                try {
                    // ✅ Here's your added line
                    val parsedJson = Json { ignoreUnknownKeys = true }
                        .decodeFromString<ActorSearchResponse>(json.jsonObject.toString())

                    parsedJson.results?.let { list ->
                        actors.addAll(list)
                    }
                } catch (e: Exception) {
                    Log.e("PopularActorsFragment", "Exception: $e")
                }
                recyclerView.adapter = ActorRecyclerViewAdapter(actors, this@PopularActorsFragment)
                Log.d("PopularActorsFragment", "response successful")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                progressBar.hide()
                t?.message?.let {
                    Log.e("PopularActorsFragment", "HTML Error Code: $statusCode, Headers: $headers")
                    Log.e("PopularActorsFragment", errorResponse)
                }
            }
        }]
    }

    override fun onItemClick(item: Actor) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("ACTOR_EXTRA", item)
        context?.startActivity(intent)
    }
}
