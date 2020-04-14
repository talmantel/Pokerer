package com.it.pokerer.data

import android.app.Application
import android.content.Context

class ScoresSharedPrefs(application: Application): ScoresDao {

    companion object{
        const val SHARED_PREFS = "pokerer_shared_prefs"

        val scoreKeys = mapOf(
            Pair(Player.GIL, "gilScore"),
            Pair(Player.TAL, "talScore"),
            Pair(Player.SHAY, "shayScore")
        )
    }

    private val sharedPrefs = application.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun getPlayerScore(player: Player) =  sharedPrefs.getInt(scoreKeys[player], 0)

    override fun setPlayerScore(player: Player, score: Int) {
        sharedPrefs.edit()
            .putInt(scoreKeys[player], score)
            .apply()
    }


}