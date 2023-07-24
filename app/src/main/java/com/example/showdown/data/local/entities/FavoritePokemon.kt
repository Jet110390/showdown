package com.example.showdown.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritePokemon")
data class FavoritePokemon(
    @PrimaryKey(autoGenerate = true)
    val key: Int? = null,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "image")
    val image: String?,
    @ColumnInfo(name = "type1")
    val type1: String?,
    @ColumnInfo(name = "type2")
    val type2: String?,
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "weight")
    val weight: Int,
    /** might implement
    @ColumnInfo(name = "moves")
    val moves: List<String>,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "hp")
    val hp: Int,
    @ColumnInfo(name = "atk")
    val atk: Int,
    @ColumnInfo(name = "def")
    val def: Int,
    @ColumnInfo(name = "spAtk")
    val spAtk: Int,
    @ColumnInfo(name = "spDef")
    val spDef: Int,
    @ColumnInfo(name = "spd")
    val spd: Int,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "preEvolution")
    val preEvo: String?,
     */
    @ColumnInfo(name = "officialImg")
    val officialImg: String?,
    @ColumnInfo(name = "speciesNumber")
    val speciesNumber: Int?,
    @ColumnInfo(name = "speciesName")
    val speciesName: String?
//    @ColumnInfo(name = "shinyImg")
//    val shinyImg: String?,

    )