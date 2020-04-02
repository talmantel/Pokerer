package com.it.pokerer

import android.content.Context

class Repository private constructor(){

    companion object{
        enum class Player {GIL, TAL, SHAY}
        const val SHARED_PREFS = "pokerer_shared_prefs"

        val scoreKeys = mapOf(
            Pair(Player.GIL, "gilScore"),
            Pair(Player.TAL, "talScore"),
            Pair(Player.SHAY, "shayScore")
        )

        var instance =  Repository()
            private set
    }


    fun getPlayerScore(context: Context, player: Player): Int {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getInt(scoreKeys[player], 0)
    }

    fun setPlayerScore(context: Context, player: Player, score: Int) {
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(scoreKeys[player], score)
            .apply()
    }


}