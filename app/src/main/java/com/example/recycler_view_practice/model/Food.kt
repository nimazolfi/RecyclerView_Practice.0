package com.example.recycler_view_practice.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//    Create a data class for keep data and data type of item food
@Entity(tableName = "Table_food")
data class Food (

    @PrimaryKey(autoGenerate = true)
    val id :Int? = null,

    val txtSubject :String,
    val txtPrice :String,
    val txtDistance :String,
    val txtCity :String,

//    @ColumnInfo(name = "urlPic")
    val urlImg :String,

    val numOfRating :String,
    val rating :Float

)