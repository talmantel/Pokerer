package com.it.pokerer.data

import android.app.Application
import android.content.Context

class ScoresSharedPrefs private constructor(application: Application): ScoresDao {

    companion object{
        const val SHARED_PREFS = "pokerer_shared_prefs"

        val scoreKeys = mapOf(
            Pair(Player.GIL, "gilScore"),
            Pair(Player.TAL, "talScore"),
            Pair(Player.SHAY, "shayScore")
        )

        private var instance: ScoresSharedPrefs? = null

        fun getInstance(application: Application): ScoresDao {
            if(instance == null){
                synchronized(ScoresSharedPrefs::class.java){
                    if(instance == null){
                        instance = ScoresSharedPrefs(application)
                    }
                }
            }
            return instance!!
        }
    }

    private val sharedPrefs = application.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun getPlayerScore(player: Player) =  sharedPrefs.getInt(scoreKeys[player], 0)

    override fun setPlayerScore(player: Player, score: Int) {
        sharedPrefs.edit()
            .putInt(scoreKeys[player], score)
            .apply()
    }


}