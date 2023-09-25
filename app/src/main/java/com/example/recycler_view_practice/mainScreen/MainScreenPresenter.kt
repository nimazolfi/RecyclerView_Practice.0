package com.example.recycler_view_practice.mainScreen

import com.example.recycler_view_practice.model.Food
import com.example.recycler_view_practice.model.FoodDao
import com.example.recycler_view_practice.util.BaseView

class MainScreenPresenter(
    private val foodDao: FoodDao
) : MainScreenContract.Presenter {

    private var mainView: MainScreenContract.View? = null
    override fun firstRun() {

        val firstRunFoodList = arrayListOf(
            Food(
                txtSubject = "Pizza",
                txtPrice = "15 $",
                txtDistance = "1.5",
                txtCity = "Rom, Italy",
                urlImg = "https://img.freepik.com/vrije-photo/hoogste-mening-van-pepperonispizza-met-de-groene-paprikaolijf-en-paddestoelen-van-paddestoelworsten-op-zwarte-houten_141793-2158.jpg?size=626&ext=jpg",
                numOfRating = "72",
                rating = 1.0f
            ),
            Food(
                txtSubject = "Hamburger",
                txtPrice = "12 $",
                txtDistance = "1.4",
                txtCity = "Paris, France",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                numOfRating = "19",
                rating = 3.5f
            ),
            Food(
                txtSubject = "Grilled fish",
                txtPrice = "20",
                txtDistance = "2.1",
                txtCity = "Tehran, Iran",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                numOfRating = "10",
                rating = 4f
            ),
            Food(
                txtSubject = "Lasania",
                txtPrice = "40",
                txtDistance = "1.4",
                txtCity = "Isfahan, Iran",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                numOfRating = "30",
                rating = 2f
            ),
            Food(
                txtSubject = "Sushi",
                txtPrice = "20",
                txtDistance = "3.2",
                txtCity = "Mashhad, Iran",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                numOfRating = "200",
                rating = 3f
            ),
            Food(
                txtSubject = "Roasted Fish",
                txtPrice = "40",
                txtDistance = "3.7",
                txtCity = "Jolfa, Iran",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                numOfRating = "50",
                rating = 3.5f
            ),
            Food(
                txtSubject = "Fried chicken",
                txtPrice = "70",
                txtDistance = "3.5",
                txtCity = "NewYork, USA",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                numOfRating = "70",
                rating = 2.5f
            ),
            Food(
                txtSubject = "Vegetable salad",
                txtPrice = "12",
                txtDistance = "3.6",
                txtCity = "Berlin, Germany",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                numOfRating = "40",
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled chicken",
                txtPrice = "10",
                txtDistance = "3.7",
                txtCity = "Beijing, China",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                numOfRating = "15",
                rating = 5f
            ),
            Food(
                txtSubject = "Beryooni",
                txtPrice = "16",
                txtDistance = "10",
                txtCity = "Ilam, Iran",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                numOfRating = "28",
                rating = 4.5f
            ),
            Food(
                txtSubject = "Ghorme Sabzi",
                txtPrice = "11.5",
                txtDistance = "7.5",
                txtCity = "Karaj, Iran",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                numOfRating = "27",
                rating = 5f
            ),
            Food(
                txtSubject = "Rice",
                txtPrice = "12.5",
                txtDistance = "2.4",
                txtCity = "Shiraz, Iran",
                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                numOfRating = "35",
                rating = 2.5f
            )
        )
        foodDao.insertAllFoods(firstRunFoodList)

    }

    override fun onAttach(View: MainScreenContract.View) {
        mainView = View

        val data = foodDao.getAllFoods()
        mainView!!.showFoods(data = data)
    }
    override fun onDetach() {
        mainView = null
    }

    override fun onSearchFood(filter: String) {
        if (filter.isNotEmpty()){
            // show filtered data
            val dataToShow = foodDao.searchFoods(filter)
            mainView!!.refreshFoods(dataToShow)
        }else{
            // show all data
            val dataToShow = foodDao.getAllFoods()
            mainView!!.refreshFoods(dataToShow)
        }
    }
    override fun onAddNewFoodClicked(food: Food) {
        foodDao.insertOrUpdate(food)
        mainView!!.addNewFood(food)
    }
    override fun onDeleteAllClicked() {
        foodDao.deleteAllFoods()
        mainView!!.refreshFoods(foodDao.getAllFoods())
    }
    override fun onUpdateFood(food: Food, position: Int) {
        foodDao.insertOrUpdate(food)
        mainView!!.updateFood(food, position)
    }
    override fun onDeleteFood(food: Food, position: Int) {
        foodDao.deleteFood(food)
        mainView!!.deleteFood(food, position)
    }

}