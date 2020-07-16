package com.adefruandta.devquotes.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.adefruandta.devquotes.model.Quote

@Database(entities = [Quote::class], version = 2)
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

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `quotes` ADD COLUMN `favourite` INTEGER DEFAULT 0 NOT NULL")
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                Db::class.java, "DeveloperQuotes.db"
            ).addMigrations(MIGRATION_1_2).build()
    }
}