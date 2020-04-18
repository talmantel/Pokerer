package com.it.pokerer.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl private constructor(private val scoresDao: ScoresDao, private val roundDao: RoundDao): Repository{

    companion object {
        private var instance: RepositoryImpl? = null

        fun getInstance(scoresDao: ScoresDao, roundDao: RoundDao): Repository {
            if(instance == null){
                synchronized(RepositoryImpl::class.java){
                    if(instance == null){
                        instance = RepositoryImpl(scoresDao, roundDao)
                    }
                }
            }
            return instance!!
        }
    }

    override fun getPlayerScore(player: Player)
            = scoresDao.getPlayerScore(player)


    override fun setPlayerScore(player: Player, score: Int) {
        scoresDao.setPlayerScore(player, score)
    }

    override suspend fun addRound(round: Round) {
        withContext(Dispatchers.IO){
            roundDao.insert(round)
        }
    }

    override fun getRounds(): LiveData<List<Round>> {
        return roundDao.getRounds()
    }

    override suspend fun removeRound(round: Round) {
        withContext(Dispatchers.IO){
            roundDao.deleteRound(round)
        }
    }


}