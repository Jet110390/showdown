package com.example.showdown.data.local.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//create variant table
@Entity(tableName = "pokemon")
data class Pokemon(
//    @PrimaryKey(autoGenerate = true)
//    val key: Int = 1,
//    @ColumnInfo(name = "id")
    @PrimaryKey
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
//    @ColumnInfo(name = "moves")
//    val moves: List<String>,
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
    @ColumnInfo(name = "officialImg")
    val officialImg: String?,
    @ColumnInfo(name = "speciesNumber")
    val speciesNumber: Int?,
    @ColumnInfo(name = "speciesName")
    val speciesName: String?,
    @ColumnInfo(name = "variants")
    val variantAmount: Int?

//    @ColumnInfo(name = "shinyImg")
//    val shinyImg: String?,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    fun toFavPokemon(): FavoritePokemon {
        return FavoritePokemon(
            id = id,
            name = name,
            image = image,
            type1 = type1,
            type2 = type2,
            height = height,
            weight = weight,
//            moves = moves,
//            description = description,
//            hp = hp,
//            atk = atk,
//            def = def,
//            spAtk = spAtk,
//            spDef = spDef,
//            spd = spd,
//            title = title,
//            preEvo = preEvo,
            officialImg = officialImg,
            speciesNumber = speciesNumber,
            speciesName = speciesName
//            shinyImg =  shinyImg


        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(type1)
        parcel.writeString(type2)
        parcel.writeInt(height)
        parcel.writeInt(weight)
        parcel.writeString(description)
        parcel.writeInt(hp)
        parcel.writeInt(atk)
        parcel.writeInt(def)
        parcel.writeInt(spAtk)
        parcel.writeInt(spDef)
        parcel.writeInt(spd)
        parcel.writeString(title)
        parcel.writeString(preEvo)
        parcel.writeString(officialImg)
        parcel.writeValue(speciesNumber)
        parcel.writeString(speciesName)
        parcel.writeValue(variantAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}