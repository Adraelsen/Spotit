package com.ishmael.spotit

import com.adamratzman.spotify.spotifyAppApi
import java.io.File
import java.io.FileNotFoundException
import kotlin.system.exitProcess

fun main() {
    val api = spotifyAppApi(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET")).build()

    val username = getUsername()

    try {
        val playlistSearch = api.playlists.getUserPlaylists(username).complete().map { it!!.name }
        println("User's playlists: ")
        println(playlistSearch)
    }
    catch (e: com.adamratzman.spotify.models.SpotifyUriException) {
        println("The following error has occurred: " + e.message)
    }

    exitProcess(0)
}

fun getUsername(): String {
    return if(File("src/main/resources/data.txt").exists()) {
        File("src/main/resources/data.txt").readText()
    }
    else {
        println("Enter Spotify username")
        val username = readLine()!!.trim()

        println("Save username for later use? (y/n)")
        when(readLine()) {
            "y" -> File("src/main/resources/data.txt").writeText(username)
        }

        username
    }
}
