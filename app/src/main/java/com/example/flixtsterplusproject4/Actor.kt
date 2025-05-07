package com.example.flixtsterplusproject4

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ActorSearchResponse(
    @SerialName("results")
    val results: List<Actor>?
)

@Keep
@Serializable
data class Actor(
    @SerialName("name")
    val name: String?,
    @SerialName("known_for")
    var knownFor: List<KnownFor>?,
    @SerialName("profile_path")
    var headshotURL: String?
) : java.io.Serializable

@Keep
@Serializable
data class KnownForWrapper(
    @SerialName("known_For")
    val knownFor: List<KnownFor>?
) : java.io.Serializable

@Keep
@Serializable
data class KnownFor(
    @SerialName("name")
    var knownForName: String? = null,
    @SerialName("title")
    var knownForTitle: String? = null,
    @SerialName("poster_path")
    var posterURL: String?,
    @SerialName("overview")
    var description: String?
) : java.io.Serializable
