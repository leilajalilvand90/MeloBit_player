package com.example.musicplayer.models.latesSongs

data class Result(
    val album: Album,
    val artists: List<ArtistX>,
    val audio: Audio,
    val copyrighted: Boolean,
    val downloadCount: String,
    val duration: Int,
    val hasVideo: Boolean,
    val id: String,
    val image: ImageXXX,
    val localized: Boolean,
    val releaseDate: String,
    val title: String
)