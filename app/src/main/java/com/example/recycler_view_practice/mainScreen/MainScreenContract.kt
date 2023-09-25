package com.example.recycler_view_practice.mainScreen

import com.example.recycler_view_practice.model.Food
import com.example.recycler_view_practice.util.BasePresenter
import com.example.recycler_view_practice.util.BaseView

interface MainScreenContract {

    interface Presenter: BasePresenter<MainScreenContract.View>{
        fun firstRun()

        fun onSearchFood(filter: String)
        fun onAddNewFoodClicked(food: Food)
        fun onDeleteAllClicked()

        fun onUpdateFood(food: Food, position: Int)
        fun onDeleteFood(food: Food, position: Int)
    }

    interface View: BaseView{
        fun showFoods(data: List<Food>)
        fun refreshFoods(data: List<Food>)
        fun addNewFood(newFood: Food)
        fun deleteFood(oldFood: Food, position: Int)
        fun updateFood(editingFood: Food, position: Int)
        fun deleteAllFoods()
    }

}