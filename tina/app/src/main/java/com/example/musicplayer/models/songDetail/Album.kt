package com.example.musicplayer.models.songDetail

data class Album(
    val artists: List<Artist>,
    val image: ImageX,
    val name: String,
    val releaseDate: String
)