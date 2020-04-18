package com.it.pokerer.ui.main

import androidx.lifecycle.*
import com.it.pokerer.data.Player
import com.it.pokerer.data.Repository
import com.it.pokerer.data.Round
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val repository: Repository) : ViewModel() {

    enum class Fragment {ROUND, HISTORY}
    var currentFragment: MutableLiveData<Fragment> = MutableLiveData(Fragment.ROUND)

    val allRounds: LiveData<List<Round>> = repository.getRounds()

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

    val lastBets: LiveData<Map<Player, Int>> = Transformations.map(allRounds) {rounds ->
        val bets = mutableMapOf<Player, Int>()
        val round = rounds[0]
        bets[Player.GIL] = -round.gilWon
        bets[Player.TAL] = -round.talWon
        bets[Player.SHAY] = -round.shayWon
        bets
    }

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
            viewModelScope.launch {
                repository.addRound(
                    Round(
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                            .format(Date(System.currentTimeMillis())),
                        -(newLastBets[Player.GIL] ?: 0),
                        -(newLastBets[Player.TAL] ?: 0),
                        -(newLastBets[Player.SHAY] ?: 0)
                    )
                )
            }
        }
    }


    fun undoRound(round: Round){
        if(round.gilWon != 0) {
            scores[Player.GIL]!!.value = scores[Player.GIL]!!.value!! - round.gilWon
            repository.setPlayerScore(Player.GIL, scores[Player.GIL]!!.value!!)
        }
        if(round.talWon != 0) {
            scores[Player.TAL]!!.value = scores[Player.TAL]!!.value!! - round.talWon
            repository.setPlayerScore(Player.TAL, scores[Player.TAL]!!.value!!)
        }
        if(round.shayWon != 0) {
            scores[Player.SHAY]!!.value = scores[Player.SHAY]!!.value!! - round.shayWon
            repository.setPlayerScore(Player.SHAY, scores[Player.SHAY]!!.value!!)
        }
        viewModelScope.launch {
            repository.removeRound(round)
        }
    }
}
