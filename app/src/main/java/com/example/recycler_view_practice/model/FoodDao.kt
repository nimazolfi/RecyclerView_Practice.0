package com.example.recycler_view_practice.model

import androidx.room.*

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(food: Food)

//    @Insert
//    fun insertFood(food: Food)

    @Insert
    fun insertAllFoods(data :List<Food>)

//    @Update
//    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

    @Query(" DELETE FROM Table_food ")
    fun deleteAllFoods()

    @Query(" SELECT * FROM Table_food ")
    fun getAllFoods(): List<Food>

    @Query(
        " SELECT * FROM Table_food WHERE txtSubject LIKE '%'||:searching||'%' "
    )
    fun searchFoods(searching: String): List<Food>

}