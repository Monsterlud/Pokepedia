package com.monsalud.pokepedia.data.datasource.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokepediaDatabase : RoomDatabase() {

    abstract fun pokepediaDao(): PokepediaDAO

    companion object {
        @Volatile
        private var INSTANCE: PokepediaDatabase? = null

        fun getDatabase(context: Context): PokepediaDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = PokepediaDatabase::class.java,
                    name = "weather_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}