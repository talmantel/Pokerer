package com.it.pokerer.data

interface Repository {
    fun getPlayerScore(player: Player): Int
    fun setPlayerScore(player: Player, score: Int)
}