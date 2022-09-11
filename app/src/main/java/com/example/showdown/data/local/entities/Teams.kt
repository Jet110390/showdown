package com.example.showdown.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Teams")
data class Teams(
    @PrimaryKey(autoGenerate = true)
    val key: Int? = null,
    @ColumnInfo(name = "teamId")
    val teamId: Int,
    @ColumnInfo(name = "teamName")
    val teamName: String,
    @ColumnInfo(name = "format")
    val battleFormat: String,
    @ColumnInfo(name = "slot1")
    val slot1: Int,
    @ColumnInfo(name = "slot2")
    val slot2: Int,
    @ColumnInfo(name = "slot3")
    val slot3: Int,
    @ColumnInfo(name = "slot4")
    val slot4: Int,
    @ColumnInfo(name = "slot5")
    val slot5: Int,
    @ColumnInfo(name = "slot6")
    val slot6: Int,
)
