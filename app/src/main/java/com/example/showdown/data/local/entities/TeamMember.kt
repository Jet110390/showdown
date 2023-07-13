package com.example.showdown.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Teams::class,
        parentColumns = arrayOf("teamId"),
        childColumns = arrayOf("teamId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Teams::class,
        parentColumns = arrayOf("slot1"),
        childColumns = arrayOf("slotId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TeamMember(
    @PrimaryKey(autoGenerate = true)
    val key: Int? = null,
    @ColumnInfo(name = "teamId")
    val teamId: Int,
    @ColumnInfo(name = "slotId")
    val slotId: Int,
)
