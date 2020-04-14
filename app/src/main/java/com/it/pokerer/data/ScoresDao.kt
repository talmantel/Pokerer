package com.it.pokerer.data

interface ScoresDao{
    fun getPlayerScore(player: Player): Int
    fun setPlayerScore(player: Player, score: Int)
}