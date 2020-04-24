package com.it.pokerer.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Round::class], version = 2)
abstract class RoundRoomDatabase: RoomDatabase() {

    abstract fun roundDao(): RoundDao

    companion object {
        private var roundRoomInstance: RoundRoomDatabase? = null

        fun getDatabase(application: Application): RoundRoomDatabase? {
            if(roundRoomInstance == null){
                synchronized(RoundRoomDatabase::class.java){
                    if(roundRoomInstance == null){
                        roundRoomInstance = Room.databaseBuilder(application,
                            RoundRoomDatabase::class.java, "rounds_database")
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return roundRoomInstance
        }
    }

}