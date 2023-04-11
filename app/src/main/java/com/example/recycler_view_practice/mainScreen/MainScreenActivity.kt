package com.example.recycler_view_practice.mainScreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view_practice.databinding.ActivityMainBinding
import com.example.recycler_view_practice.databinding.DialogAddNewItemBinding
import com.example.recycler_view_practice.databinding.DialogDeleteItemBinding
import com.example.recycler_view_practice.databinding.DialogUpdateItemBinding
import com.example.recycler_view_practice.model.Food
import com.example.recycler_view_practice.model.FoodDao
import com.example.recycler_view_practice.model.MyDatabase
import java.util.*

//    implement Room :
//    1. Entity (Create table)
//    2. Dao
//    3. Create DataBase

class MainScreenActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    //    Create viewBinding
    private lateinit var binding: ActivityMainBinding

    //    Create a lateinit variable that extend of FoodAdapter
    private lateinit var myAdapter: FoodAdapter

    //    Create a value as ArrayList of Foods for give data of foods later
    private lateinit var foodList: ArrayList<Food>

    //    Create a lat var to access dataBase for do operation in dataBase
    private lateinit var foodDao: FoodDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    Binding ==>
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //    Create dataBase with created database that is an abstract class and -
        //      - and call getDatabase method on it so we give THIS( this thread or this activity ) as context and -
        //        - and set the FOOD-DAO that is in MY-DATA-BASE as foodDao with which operations are performed on the database
        foodDao = MyDatabase.getDatabase(this).foodDao
        //    Create a value for sharedPreferences to check is it first run or no with private mode b-cuz we wanna to use it in our app
        val sharedPreferences = getSharedPreferences("first?", Context.MODE_PRIVATE)
        //    Check is it first run or no with IF and set def value TRUE for checking, that we will change it after first run to false
        if (sharedPreferences.getBoolean("firstRun", true)) {
            firstRun()
            //    After first run, change the value to false and applying it
            sharedPreferences.edit().putBoolean("firstRun", false).apply()
        }
        //    Calling showAllData function
        showAllData()

        binding.RemoveAllFoods.setOnClickListener {
            //    Calling removeAllData function
            removeAllData()
        }

        binding.AddNewFood.setOnClickListener {
            //    Calling addNewFood function
            addNewFood()
        }

        binding.edtSearch.addTextChangedListener { textInput ->
            //    Calling searchOnDataBase function with a String
            searchOnDataBase(textInput.toString())
        }

    }

    private fun firstRun() {
        //    Create a list for foods
//        foodList = arrayListOf(
//            Food(
//                txtSubject = "Pizza",
//                txtPrice = "15 $",
//                txtDistance = "1.5",
//                txtCity = "Rom, Italy",
//                urlImg = "https://img.freepik.com/vrije-photo/hoogste-mening-van-pepperonispizza-met-de-groene-paprikaolijf-en-paddestoelen-van-paddestoelworsten-op-zwarte-houten_141793-2158.jpg?size=626&ext=jpg",
//                numOfRating = "72",
//                rating = 1.0f
//            ),
//            Food(
//                txtSubject = "Hamburger",
//                txtPrice = "12 $",
//                txtDistance = "1.4",
//                txtCity = "Paris, France",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
//                numOfRating = "19",
//                rating = 3.5f
//            ),
//            Food(
//                txtSubject = "Grilled fish",
//                txtPrice = "20",
//                txtDistance = "2.1",
//                txtCity = "Tehran, Iran",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
//                numOfRating = "10",
//                rating = 4f
//            ),
//            Food(
//                txtSubject = "Lasania",
//                txtPrice = "40",
//                txtDistance = "1.4",
//                txtCity = "Isfahan, Iran",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
//                numOfRating = "30",
//                rating = 2f
//            ),
//            Food(
//                txtSubject = "Sushi",
//                txtPrice = "20",
//                txtDistance = "3.2",
//                txtCity = "Mashhad, Iran",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
//                numOfRating = "200",
//                rating = 3f
//            ),
//            Food(
//                txtSubject = "Roasted Fish",
//                txtPrice = "40",
//                txtDistance = "3.7",
//                txtCity = "Jolfa, Iran",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
//                numOfRating = "50",
//                rating = 3.5f
//            ),
//            Food(
//                txtSubject = "Fried chicken",
//                txtPrice = "70",
//                txtDistance = "3.5",
//                txtCity = "NewYork, USA",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
//                numOfRating = "70",
//                rating = 2.5f
//            ),
//            Food(
//                txtSubject = "Vegetable salad",
//                txtPrice = "12",
//                txtDistance = "3.6",
//                txtCity = "Berlin, Germany",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
//                numOfRating = "40",
//                rating = 4.5f
//            ),
//            Food(
//                txtSubject = "Grilled chicken",
//                txtPrice = "10",
//                txtDistance = "3.7",
//                txtCity = "Beijing, China",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
//                numOfRating = "15",
//                rating = 5f
//            ),
//            Food(
//                txtSubject = "Beryooni",
//                txtPrice = "16",
//                txtDistance = "10",
//                txtCity = "Ilam, Iran",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
//                numOfRating = "28",
//                rating = 4.5f
//            ),
//            Food(
//                txtSubject = "Ghorme Sabzi",
//                txtPrice = "11.5",
//                txtDistance = "7.5",
//                txtCity = "Karaj, Iran",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
//                numOfRating = "27",
//                rating = 5f
//            ),
//            Food(
//                txtSubject = "Rice",
//                txtPrice = "12.5",
//                txtDistance = "2.4",
//                txtCity = "Shiraz, Iran",
//                urlImg = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
//                numOfRating = "35",
//                rating = 2.5f
//            )
//        )
        //    Insert basic foods in dataBase that we give as ArrayList
        foodDao.insertAllFoods(foodList)
    }

    private fun removeAllData() {
        //    Delete al data in dataBase
        foodDao.deleteAllFoods()
        //    Show data from dataBase
        showAllData()
    }

    private fun addNewFood() {

        //    Create a value to build and hold AlertDialog
        val dialog = AlertDialog.Builder(this).create()
        //    Create a value like binding to find views in dialog
        val view = DialogAddNewItemBinding.inflate(layoutInflater)

        //    Set view with root
        dialog.setView(view.root)
        //    Set cancelable true
        dialog.setCancelable(true)
        //    Show dialog
        dialog.show()

        //    Set the btn on clickable for submit information
        view.btnDone.setOnClickListener {

            //    Set a condition for filled boxes
            if (
                view.enterFoodPicURL.length() > 0 &&
                view.enterFoodCity.length() > 0 &&
                view.enterFoodName.length() > 0 &&
                view.enterFoodPrice.length() > 0 &&
                view.enterFoodDistance.length() > 0
            ) {
                //    If condition is met, Add a new food with getting data
                val textName = view.enterFoodName.toString()
                val foodPrice = view.enterFoodPrice.toString()
                val foodDistance = view.enterFoodDistance.toString()
                val foodCity = view.enterFoodCity.toString()
                val picURL = view.enterFoodPicURL.toString()
                val txtRatingNum = view.foodRatedNumber.toString()
                val min = 1f
                val max = 5f
                val random: Float = min + Random().nextFloat() * (max - min)
                //    Send data to the newFood
                val newFood = Food(
                    txtSubject = textName,
                    txtPrice = foodPrice,
                    txtDistance = foodDistance,
                    txtCity = foodCity,
                    urlImg = picURL,
                    numOfRating = txtRatingNum,
                    rating = random
                )

                //    Call the adapter with addFood function and set newFood in constructor
                myAdapter.addFood(newFood)
                //    Add created food with given data in dataBase
                foodDao.insertOrUpdate(newFood)
                //    Scroll the created view in top
                binding.recyclerMain.scrollToPosition(0)
                //    Dismiss the created dialog
                dialog.dismiss()

            } else {
                //    Show toast to fill in all fields
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun showAllData() {
        //    Create a value to get and keep the data from dataBase
        val foodData = foodDao.getAllFoods()
        //    Set adapter list with created list --> or --> set data in a value for adapter with database data
        myAdapter = FoodAdapter(ArrayList(foodData), this)
        //    Set created value as recycler adapter
        binding.recyclerMain.adapter = myAdapter
        //    Manage recycler layout ->
        //      -> Set this for context
        //      -> Set recycler view VERTICAL
        //      -> Set reverse False, b-cuz we wanna to show it according to the database and respectively
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //    Set a log just for test if is it work or nope with foods data in logcat
        Log.v("testFoodsAreInserted", foodData.toString())
    }

    private fun searchOnDataBase(textInput: String) {
        //    Check the entered to not empty
        if (textInput.isNotEmpty()) {
            val searchedData = foodDao.searchFoods(textInput)
            //    Create a copy of our list as ArrayList
            val cloneList = foodList.clone() as ArrayList<Food>
            //    Create a value to keep filtered items that is related to the textInput
            val filteredList = cloneList.filter { foodItem ->
                //    Check the FoodName that contains the textInput
                foodItem.txtSubject.contains(textInput)
            }
            //    Call setData function in Adapter and set filteredList as ArrayList
            myAdapter.setData(ArrayList(filteredList))
            //    If is empty run the else
        } else {

            val data = foodDao.getAllFoods()
//                Call setData function in Adapter and set a copy of data as ArrayList
            myAdapter.setData(ArrayList(data))

        }

    }

    //    Override functions that we defined in our interface ==>
    //    =>    Override fun onFoodClicked
    override fun onFoodClicked(food: Food, position: Int) {
        //    Create a value to build and hold AlertDialog
        val dialog = AlertDialog.Builder(this).create()
        //    Create a value like binding to find views in this dialog
        val update = DialogUpdateItemBinding.inflate(layoutInflater)
        //    Set view with root
        dialog.setView(update.root)
        //    Set cancelable true
        dialog.setCancelable(true)
        //    Show created dialog
        dialog.show()

        //    Binding.viewID.setText(item.valueInDataClass)
        update.enterFoodName.setText(food.txtSubject)
        update.enterFoodCity.setText(food.txtCity)
        update.enterFoodDistance.setText(food.txtDistance)
        update.enterFoodPrice.setText(food.txtPrice)
        update.enterFoodPicURL.setText(food.urlImg)
        update.foodRatedNumber.setText(food.numOfRating)

        //    Enable setOnClickListener for Cancel btn
        update.btnCanCel.setOnClickListener {
            //    Dismiss the created dialog
            dialog.dismiss()
        }

        //    Enable setOnClickListener for Done btn
        update.btnDone.setOnClickListener {

            //    Set a condition for filled boxes
            if (
                update.enterFoodPicURL.length() > 0 &&
                update.enterFoodCity.length() > 0 &&
                update.enterFoodName.length() > 0 &&
                update.enterFoodPrice.length() > 0 &&
                update.enterFoodDistance.length() > 0
            ) {
                //    If condition is met, Add a new food with getting data
                val textName = update.enterFoodName.toString()
                val foodPrice = update.enterFoodPrice.toString()
                val foodDistance = update.enterFoodDistance.toString()
                val foodCity = update.enterFoodCity.toString()
                val picURL = update.enterFoodPicURL.toString()
                val txtRatingNum = update.foodRatedNumber.toString()
                val min = 1f
                val max = 5f
                val random: Float = min + Random().nextFloat() * (max - min)

                //    Send data to the newFood
                val newFood =
                    Food(
                        id = food.id,
                        txtSubject = textName,
                        txtPrice = foodPrice,
                        txtDistance = foodDistance,
                        txtCity = foodCity,
                        urlImg = picURL,
                        numOfRating = txtRatingNum,
                        rating = random
                    )
                //    Update the item with given data and position in adapter
                myAdapter.updateFood(newFood, position)
                //    Update the item in dataBase
                foodDao.insertOrUpdate(newFood)
                //    Dismiss the created dialog
                dialog.dismiss()

                //    If condition is not met, run else
            } else {
                //    Show toast to fill in all fields
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

        }

    }

    //    =>    override fun onFoodLongClicked
    override fun onFoodLongClicked(food: Food, position: Int) {
//        Create a value to build and create AlertDialog
        val dialog = AlertDialog.Builder(this).create()
//        Create a value like binding to find views in this dialog
        val delete = DialogDeleteItemBinding.inflate(layoutInflater)
//        Set view with root
        dialog.setView(delete.root)
//        Set cancelable true
        dialog.setCancelable(true)
//        Show created dialog
        dialog.show()

//        Enable setOnClickListener for cancel btn
        delete.btnCancel.setOnClickListener {
//            Dismiss the created dialog
            dialog.dismiss()
        }

//        Enable setOnClickListener for OK btn
        delete.btnSure.setOnClickListener {
//            Dismiss the created dialog
            dialog.dismiss()
//            Delete the item with position
            myAdapter.removeFood(food, position)
            foodDao.deleteFood(food)
        }

    }

}