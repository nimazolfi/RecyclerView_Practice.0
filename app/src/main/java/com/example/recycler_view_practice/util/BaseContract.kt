package com.example.recycler_view_practice.util

interface BasePresenter<T>{
    fun onAttach(view: T)
    fun onDetach()
}

interface BaseView{
    fun showProgressBar()
}