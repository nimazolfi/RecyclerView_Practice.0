package com.example.recycler_view_practice.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycler_view_practice.databinding.ItemFoodBinding
import com.example.recycler_view_practice.model.Food
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

//    Create an adapter for Recycler view and get a data as list for item in constructor and extend ->
//        -> of RecyclerViewAdapter with type of item viewHolder that get in constructor
//    And get a foodEvent of FoodEvent
class FoodAdapter(private val data: ArrayList<Food>, private val foodEvents: FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    //    Create viewHolder to keep view in our adapter and show them as a View and make a binding
//        then extend of viewHolder that get the binding root
    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //    Create a function to set correct data in correct positions
        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {

//            Add all data in their position
            binding.foodName.text = data[position].txtSubject
            binding.foodPrice.text = data[position].txtPrice
            binding.foodDistance.text = data[position].txtDistance + " km from you"
            binding.ratingNum.text = data[position].numOfRating + " Rating"
            binding.foodPlace.text = data[position].txtCity
            binding.ratingBar.rating = data[position].rating

//            Use Glide library for images
            Glide
                .with(binding.root.context)
                .load(data[position].urlImg)
                .transform(RoundedCornersTransformation(16, 4))
                .into(binding.itemImgFood).toString()

//            Enable setOnClickListener for the view and onFoodClicked function
            itemView.setOnClickListener {

//                Enable onFoodClicked for foodEvent
                foodEvents.onFoodClicked(data[adapterPosition], adapterPosition)

            }

//            Enable setOnLongClickListener for the view and onFoodLongClicked function
            itemView.setOnLongClickListener {

//                Enable onFoodLongClicked for foodEvent with a data position and adapter position
                foodEvents.onFoodLongClicked(data[adapterPosition], adapterPosition)

//                This function should return a Boolean
                true

            }

        }

    }

//    Override adapter functions

    //    Override viewHolder function
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

//        Set a Log for app
        Log.v("testApp", "onCreateViewHolder called")

//        Create a value as binding to set views and know where they are come on it
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)

//        Return view
        return FoodViewHolder(binding)

    }

    //    Override bindViewHolder function to find views and set them to the position
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        Log.v("testApp", "onBindViewHolder called")
//        Set view with data in position
        holder.bindData(position)
    }

    //    Override getItemCount function to count created views
    override fun getItemCount(): Int {
        //    Return created views
        return data.size
    }

    //        Create a function to add food ->
    //        -> This function gets a newFood as Food class and add it on the top to the 0 index
    fun addFood(newFood: Food) {
        //    Add new food in data and set it at first
        data.add(0, newFood)
        //    Notify set a position for the getting data in top with an animation
        notifyItemInserted(0)
    }

    //        Create a function to delete an item ->
    //      -> This function gets a oldFood as Food and a position as Int
    fun removeFood(oldFood: Food, oldPosition: Int) {
        //    Delete selected item
        data.remove(oldFood)
        //    Notify delete selected position with an animation
        notifyItemRemoved(oldPosition)
    }

    //        Create a function to update an item ->
    //      -> This function gets a new data for newFood and items position
    fun updateFood(newFood: Food, position: Int) {
        //    Set new data on the old food position
        data[position] = newFood
        //    Notify updated item with an animation
        notifyItemChanged(position)
    }

    //        Create a function to set data in adapter ->
    //      -> This function gets a List of ArrayList with Food items
    fun setData(newList: ArrayList<Food>) {
        //    Clear current data
        data.clear()
        //    Add taken data as newList to data
        data.addAll(newList)
        //    Notify Set data item with an animation
        notifyDataSetChanged()
    }

    //    Create a interface to set accessibility on MainActivity
    interface FoodEvents {
        //    Create a function for one click
        fun onFoodClicked(food: Food, position: Int)

        //    Create a function for long click
        fun onFoodLongClicked(food: Food, position: Int)
    }

}