package com.it.pokerer.data

import androidx.lifecycle.LiveData

interface Repository {
    fun getPlayerScore(player: Player): Int
    fun setPlayerScore(player: Player, score: Int)
    suspend fun addRound(round: Round)
    fun getRounds(): LiveData<List<Round>>
    suspend fun removeRound(round: Round)
}