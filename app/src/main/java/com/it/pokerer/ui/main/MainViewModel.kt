package com.it.pokerer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.it.pokerer.data.Player
import com.it.pokerer.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val scores: MutableMap<Player, MutableLiveData<Int>> by lazy {
        mutableMapOf<Player, MutableLiveData<Int>>().also { scores ->
            Player.values().forEach { player ->
                MutableLiveData(0).apply {
                    value = repository.getPlayerScore(player)
                }.also {
                    scores[player] = it
                }
            }
        }
    }

    private val _lastBets: MutableLiveData<Map<Player, Int>> = MutableLiveData()
    val lastBets: LiveData<Map<Player, Int>> = _lastBets

    fun getPlayerScore(player : Player): LiveData<Int>? = scores[player]

    fun roundPlayed(bets: Map<Player, Int>, winner: Player){
        var totalWon = 0

        val newLastBets = mutableMapOf<Player, Int>()

        bets.forEach {
            if(it.key != winner && it.value != 0){
                totalWon += it.value
                val newScore = scores[it.key]!!.value!! - it.value
                scores[it.key]!!.value = newScore
                repository.setPlayerScore(it.key, newScore)
                newLastBets[it.key] = it.value
            }
        }

        if(totalWon > 0) {
            val winnerNewScore = scores[winner]!!.value!! + totalWon
            scores[winner]!!.value = winnerNewScore
            newLastBets[winner] = -totalWon
            repository.setPlayerScore(winner, winnerNewScore)
            _lastBets.value = newLastBets
        }
    }
}
