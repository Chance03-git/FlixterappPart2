package com.example.flixtsterplusproject4

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide



class DetailActivity : AppCompatActivity() {
    private lateinit var headshotView: ImageView
    private lateinit var posterView: ImageView
    private lateinit var nameView: TextView
    private lateinit var knownForView: TextView
    private lateinit var overviewView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail)
        headshotView = findViewById(R.id.detailHeadshot)
        posterView = findViewById(R.id.detailFilm)
        nameView = findViewById(R.id.detailName)
        overviewView = findViewById(R.id.detailOverview)
        knownForView = findViewById(R.id.detailKnownFor)

        val actor = intent.getSerializableExtra("ACTOR_EXTRA") as Actor

        knownForView.text = "Known For: " +
                (actor.knownFor?.get(0)?.knownForTitle ?: actor.knownFor?.get(0)?.knownForName ?: "None")

        // Set title and abstract information for the article
        nameView.text = actor.name
        overviewView.text = actor.knownFor?.get(0)?.description

        // Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" +actor.headshotURL)
            .into(headshotView)

        // Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" +actor.knownFor?.get(0)?.posterURL)
            .into(posterView)
    }
}