package com.it.pokerer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Round (@PrimaryKey val ts: String,
             val gilWon: Int,
             val talWon: Int,
             val shayWon: Int)