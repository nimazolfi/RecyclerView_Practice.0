package com.example.recycler_view_practice.mainScreen

import android.content.Context
import android.os.Bundle
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
import com.example.recycler_view_practice.util.showToast
import java.util.Random

//    implement Room :
//    1. Entity (Create table)
//    2. Dao
//    3. Create DataBase

class MainScreenActivity : AppCompatActivity(), FoodAdapter.FoodEvents, MainScreenContract.View {
    //    Create viewBinding
    private lateinit var binding: ActivityMainBinding

    //    Create a lateinit variable that extend of FoodAdapter
    private lateinit var myAdapter: FoodAdapter

    private lateinit var mainAdapter: FoodAdapter

    //    Create a value as ArrayList of Foods for give data of foods later
    private lateinit var foodList: ArrayList<Food>

    //    Create a lat var to access dataBase for do operation in dataBase
    private lateinit var foodDao: FoodDao

    private lateinit var mainScreenPresenter: MainScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    Binding ==>
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainScreenPresenter = MainScreenPresenter(MyDatabase.getDatabase(this).foodDao)

        //    Create dataBase with created database that is an abstract class and -
        //      - and call getDatabase method on it so we give THIS( this thread or this activity ) as context and -
        //        - and set the FOOD-DAO that is in MY-DATA-BASE as foodDao with which operations are performed on the database
        foodDao = MyDatabase.getDatabase(this).foodDao

        //    Create a value for sharedPreferences to check is it first run or no with private mode b-cuz we wanna to use it in our app
        val sharedPreferences = getSharedPreferences("first?", Context.MODE_PRIVATE)

        //    Check is it first run or no with IF and set def value TRUE for checking, that we will change it after first run to false
        if (sharedPreferences.getBoolean("firstRun", true)) {
            mainScreenPresenter.firstRun()

//            firstRun()
            //    After first run, change the value to false and applying it
            sharedPreferences.edit().putBoolean("firstRun", false).apply()
        }
        //    Calling showAllData function
//        showAllData()


        mainScreenPresenter.onAttach(this)

        binding.RemoveAllFoods.setOnClickListener {
            //    Calling removeAllData function
//            removeAllData()

            mainScreenPresenter.onDeleteAllClicked()

        }

        binding.AddNewFood.setOnClickListener {
            //    Calling addNewFood function
            addNewFood()
        }

        binding.edtSearch.addTextChangedListener { textInput ->
            //    Calling searchOnDataBase function with a String
            searchOnDataBase(textInput.toString())

            mainScreenPresenter.onSearchFood(textInput.toString())

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainScreenPresenter.onDetach()
    }

    private fun removeAllData() {
        //    Delete al data in dataBase
//        foodDao.deleteAllFoods()
        //    Show data from dataBase
//        showAllData()
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

                mainScreenPresenter.onAddNewFoodClicked(newFood)

                //    Call the adapter with addFood function and set newFood in constructor
//                myAdapter.addFood(newFood)
                //    Add created food with given data in dataBase
//                foodDao.insertOrUpdate(newFood)
                //    Scroll the created view in top
//                binding.recyclerMain.scrollToPosition(0)
                //    Dismiss the created dialog
                dialog.dismiss()

            } else {
                //    Show toast to fill in all fields
//                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()

                showToast("Please fill in all the fields")

            }

        }

    }

    private fun showAllData() {
        //    Create a value to get and keep the data from dataBase
//        val foodData = foodDao.getAllFoods()
//        //    Set adapter list with created list --> or --> set data in a value for adapter with database data
//        myAdapter = FoodAdapter(ArrayList(foodData), this)
//        //    Set created value as recycler adapter
//        binding.recyclerMain.adapter = myAdapter
//        //    Manage recycler layout ->
//        //      -> Set this for context
//        //      -> Set recycler view VERTICAL
//        //      -> Set reverse False, b-cuz we wanna to show it according to the database and respectively
//        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//
//        //    Set a log just for test if is it work or nope with foods data in logcat
//        Log.v("testFoodsAreInserted", foodData.toString())
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
//                myAdapter.updateFood(newFood, position)
                //    Update the item in dataBase
//                foodDao.insertOrUpdate(newFood)
                //    Dismiss the created dialog

                mainScreenPresenter.onUpdateFood(newFood, position)

                dialog.dismiss()

                //    If condition is not met, run else
            } else {
                //    Show toast to fill in all fields
//                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()

                showToast("Please fill in all fields")

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

            mainScreenPresenter.onDeleteFood(food, position)

            //  Dismiss the created dialog
            dialog.dismiss()

            //  Delete the item with position
//            myAdapter.removeFood(food, position)
//            foodDao.deleteFood(food)
        }

    }

    override fun showFoods(data: List<Food>) {
        mainAdapter = FoodAdapter(ArrayList(data), this)
        binding.recyclerMain.adapter = mainAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun refreshFoods(data: List<Food>) {
        mainAdapter.setData(ArrayList(data))
    }

    override fun addNewFood(newFood: Food) {
        mainAdapter.addFood(newFood)
    }

    override fun deleteFood(oldFood: Food, position: Int) {
        mainAdapter.removeFood(oldFood, position)
    }

    override fun updateFood(editingFood: Food, position: Int) {
        mainAdapter.updateFood(editingFood, position)
    }

    override fun deleteAllFoods() {
//        mainAdapter.removeFood()
    }

    override fun showProgressBar() {
//
    }

}