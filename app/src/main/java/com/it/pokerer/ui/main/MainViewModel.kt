package com.it.pokerer.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.it.pokerer.Repository
import com.it.pokerer.Repository.Companion.Player

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val repository = Repository.instance

    private val scores: MutableMap<Player, MutableLiveData<Int>> = mutableMapOf()

    private val _lastBets: MutableLiveData<Map<Player, Int>> = MutableLiveData()
    val lastBets: LiveData<Map<Player, Int>> = _lastBets

    fun getPlayerScore(player : Player): LiveData<Int>? = scores[player]

    init {
        Player.values().forEach { player ->
            MutableLiveData(0).apply {
                value = repository.getPlayerScore(context, player)
            }.also {
                scores[player] = it
            }
        }
    }


    fun roundPlayed(bets: Map<Player, Int>, winner: Player){
        var totalWon = 0

        val newLastBets = mutableMapOf<Player, Int>()

        bets.forEach {
            if(it.key != winner && it.value != 0){
                totalWon += it.value
                val newScore = scores[it.key]!!.value!! - it.value
                scores[it.key]!!.value = newScore
                repository.setPlayerScore(context, it.key, newScore)
                newLastBets[it.key] = it.value
            }
        }

        if(totalWon > 0) {
            val winnerNewScore = scores[winner]!!.value!! + totalWon
            scores[winner]!!.value = winnerNewScore
            newLastBets[winner] = totalWon
            repository.setPlayerScore(context, winner, winnerNewScore)
            _lastBets.value = newLastBets
        }
    }
}
