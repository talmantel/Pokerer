package com.it.pokerer.data

class RepositoryImpl
    (private val scoresDao: ScoresDao): Repository{

    override fun getPlayerScore(player: Player)
            = scoresDao.getPlayerScore(player)


    override fun setPlayerScore(player: Player, score: Int) {
        scoresDao.setPlayerScore(player, score)
    }


}