package com.adefruandta.devquotes.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adefruandta.devquotes.model.Quote
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quotes WHERE id = :id")
    fun get(id: String): Single<Quote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quote: Quote): Completable
}