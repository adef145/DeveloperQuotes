package com.adefruandta.devquotes.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adefruandta.devquotes.model.Quote

@Database(entities = [Quote::class], version = 1)
@TypeConverters(Converters::class)
abstract class Db : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {

        @Volatile
        private var INSTANCE: Db? = null

        fun getInstance(context: Context): Db =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                Db::class.java, "DeveloperQuotes.db"
            ).build()
    }
}