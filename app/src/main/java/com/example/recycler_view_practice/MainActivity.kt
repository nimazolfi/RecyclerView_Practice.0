package com.example.recycler_view_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view_practice.databinding.ActivityMainBinding
import com.example.recycler_view_practice.databinding.DialogAddNewItemBinding
import com.example.recycler_view_practice.databinding.DialogDeleteItemBinding
import com.example.recycler_view_practice.databinding.DialogUpdateItemBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents{

//    Create viewBinding
    private lateinit var binding : ActivityMainBinding

//    Create a lateinit variable that extend of FoodAdapter
    private lateinit var myAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Create a list for foods
        val foodList = arrayListOf(

            Food(
                "Pizza",
                "15 $",
                "1.5",
                "Rom, Italy",
                "https://img.freepik.com/vrije-photo/hoogste-mening-van-pepperonispizza-met-de-groene-paprikaolijf-en-paddestoelen-van-paddestoelworsten-op-zwarte-houten_141793-2158.jpg?size=626&ext=jpg",
                "72",
                rating = 1.0f
            ),
            Food(
                "Hamburger",
                "12 $",
                "1.4",
                "Paris, France",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                "19",
                3.1f
            ),
            Food(
                "Grilled fish" ,
                "20" ,
                "2.1" ,
                "Tehran, Iran" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg" ,
                "10" ,
                4f
            ) ,
            Food(
                "Lasania" ,
                "40" ,
                "1.4" ,
                "Isfahan, Iran" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg" ,
                "30" ,
                2f
            ) ,
            Food(
                "Sushi" ,
                "20" ,
                "3.2" ,
                "Mashhad, Iran" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg" ,
                "200" ,
                3f
            ) ,
            Food(
                "Roasted Fish" ,
                "40" ,
                "3.7" ,
                "Jolfa, Iran" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg" ,
                "50" ,
                3.5f
            ) ,
            Food(
                "Fried chicken" ,
                "70" ,
                "3.5" ,
                "NewYork, USA" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg" ,
                "70" ,
                2.5f
            ) ,
            Food(
                "Vegetable salad" ,
                "12" ,
                "3.6" ,
                "Berlin, Germany" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg" ,
                "40" ,
                4.5f
            ) ,
            Food(
                "Grilled chicken" ,
                "10" ,
                "3.7" ,
                "Beijing, China" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg" ,
                "15" ,
                5f
            ) ,
            Food(
                "Beryooni" ,
                "16" ,
                "10" ,
                "Ilam, Iran" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg" ,
                "28" ,
                4.5f
            ) ,
            Food(
                "Ghorme Sabzi" ,
                "11.5" ,
                "7.5" ,
                "Karaj, Iran" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg" ,
                "27" ,
                5f
            ) ,
            Food(
                "Rice" ,
                "12.5" ,
                "2.4" ,
                "Shiraz, Iran" ,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg" ,
                "35" ,
                2.5f
            )
            )

//        Set adapter list with created list --> or --> set data in a value for adapter
        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)

//        Set created value as recycler adapter
        binding.recyclerMain.adapter = myAdapter

//        Set Linearlayout VERTICAL
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

//        Set the add icon on clickable
        binding.AddNewFood.setOnClickListener {

//            Create a value to build and create AlertDialog
            val dialog = AlertDialog.Builder(this).create()

//            Create a value like binding to find views in dialog
            val view = DialogAddNewItemBinding.inflate(layoutInflater)


//            Set view with root
            dialog.setView(view.root)

//            Set cancelable true
            dialog.setCancelable(true)

//            Show dialog
            dialog.show()

//            Set the btn on clickable for submit information
            view.btnDone.setOnClickListener{

//                Set a condition for filled boxes
                if (
                    view.enterFoodPicURL.length() > 0 &&
                    view.enterFoodCity.length() > 0 &&
                    view.enterFoodName.length() > 0 &&
                    view.enterFoodPrice.length() > 0 &&
                    view.enterFoodDistance.length() > 0
                ) {

//                    If condition is met, Add a new food with getting data
                    val textName = view.enterFoodName.toString()
                    val foodPrice = view.enterFoodPrice.toString()
                    val foodDistance = view.enterFoodDistance.toString()
                    val foodCity = view.enterFoodCity.toString()
                    val picURL = view.enterFoodPicURL.toString()
                    val txtRatingNum = view.foodRatedNumber.toString()
                    val min = 1f
                    val max = 5f
                    val random :Float = min + Random().nextFloat() * (max - min)

//                    Send data to the newFood
                    val newFood = Food(textName, foodPrice, foodDistance, foodCity, picURL, txtRatingNum, random)

//                    Call the adapter with addFood function and set newFood in constructor
                    myAdapter.addFood(newFood)

//                    Scroll the created view in top
                    binding.recyclerMain.scrollToPosition(0)

//                    Dismiss the created dialog
                    dialog.dismiss()

//                If condition is not met, run else
                }else{

//                    Show toast to fill in all fields
                    Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()

                }
                
            }

        }

//        Set the search box for searching and input text as textInput
        binding.edtSearch.addTextChangedListener { textInput ->

//            Check the entered to not empty
            if (textInput!!.isNotEmpty()){

//                Create a copy of our list as ArrayList
                val cloneList = foodList.clone() as ArrayList<Food>

//                Create a value to keep filtered items that is related to the textInput
                val filteredList = cloneList.filter { foodItem ->

//                    Check the FoodName that contains the textInput
                    foodItem.txtSubject.contains(textInput)

                }

//                Call setData function in Adapter and set filteredList as ArrayList
                myAdapter.setData(ArrayList(filteredList))

//            If is empty run the else
            }else{

//                Call setData function in Adapter and set a copy of data as ArrayList
                myAdapter.setData(foodList.clone() as ArrayList<Food>)

            }

        }

    }


//    Override functions that we define in our interface ==>

//    =>    Override fun onFoodClicked
    override fun onFoodClicked(food: Food, position: Int) {

//        Create a value to build and create AlertDialog
        val dialog = AlertDialog.Builder(this).create()

//        Create a value like binding to find views in this dialog
        val update = DialogUpdateItemBinding.inflate(layoutInflater)

//        Set view with root
        dialog.setView(update.root)

//        Set cancelable true
        dialog.setCancelable(true)

//        Show created dialog
        dialog.show()

        update.enterFoodName.setText(food.txtSubject)
        update.enterFoodCity.setText(food.txtCity)
        update.enterFoodDistance.setText(food.txtDistance)
        update.enterFoodPrice.setText(food.txtPrice)
        update.enterFoodPicURL.setText(food.urlImg)
        update.foodRatedNumber.setText(food.numOfRating)

//        Enable setOnClickListener for Cancel btn
        update.btnCanCel.setOnClickListener {

//            Dismiss the created dialog
            dialog.dismiss()

        }

//        Enable setOnClickListener for Done btn
        update.btnDone.setOnClickListener {

//            Set a condition for filled boxes
            if (
                update.enterFoodPicURL.length() > 0 &&
                update.enterFoodCity.length() > 0 &&
                update.enterFoodName.length() > 0 &&
                update.enterFoodPrice.length() > 0 &&
                update.enterFoodDistance.length() > 0
            ) {

//                If condition is met, Add a new food with getting data
                val textName = update.enterFoodName.toString()
                val foodPrice = update.enterFoodPrice.toString()
                val foodDistance = update.enterFoodDistance.toString()
                val foodCity = update.enterFoodCity.toString()
                val picURL = update.enterFoodPicURL.toString()
                val txtRatingNum = update.foodRatedNumber.toString()
                val min = 1f
                val max = 5f
                val random :Float = min + Random().nextFloat() * (max - min)

//                Send data to the newFood
                val newFood = Food(textName, foodPrice, foodDistance, foodCity, picURL, txtRatingNum, random)

//                Update the item with given data and position
                myAdapter.updateFood(newFood, position)

//                Dismiss the created dialog
                dialog.dismiss()

//            If condition is not met, run else
            }else{

//                Show toast to fill in all fields
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

        }

    }
    
}