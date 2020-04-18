package com.it.pokerer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoundDao {

    @Insert
    fun insert(round: Round)

    @Query("Select * from Round ORDER BY ts DESC")
    fun getRounds(): LiveData<List<Round>>

    @Delete
    fun deleteRound(round: Round)
}