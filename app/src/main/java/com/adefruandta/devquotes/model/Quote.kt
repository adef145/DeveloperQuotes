package com.adefruandta.devquotes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "quotes")
class Quote : Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "sr")
    @SerializedName("sr")
    var sr: String? = null

    @ColumnInfo(name = "en")
    @SerializedName("en")
    var en: String = ""

    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String = ""

    @ColumnInfo(name = "favourite")
    var favourite: Boolean = false

    fun text(): String {
        return sr ?: en
    }
}