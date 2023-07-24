package com.example.showdown.data.local.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "variant")
data class Variant(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "image")
    val image: String?,
    @ColumnInfo(name = "type1")
    val type1: String?,
    @ColumnInfo(name = "type2")
    val type2: String? =" ",
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "weight")
    val weight: Int,
    @ColumnInfo(name = "species")
    val speciesID: Int,
    @ColumnInfo(name = "officialImg")
    val officialImg: String?,
    @ColumnInfo(name = "speciesName")
    val speciesName: String?
//    @ColumnInfo(name = "shinyImg")
//    val shinyImg: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
    )

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
            officialImg = officialImg,
            speciesNumber = speciesID,
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
        parcel.writeInt(speciesID)
        parcel.writeString(officialImg)
        parcel.writeString(speciesName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Variant> {
        override fun createFromParcel(parcel: Parcel): Variant {
            return Variant(parcel)
        }

        override fun newArray(size: Int): Array<Variant?> {
            return arrayOfNulls(size)
        }
    }
}
