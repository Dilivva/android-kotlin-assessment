package com.basebox.weatherinsights.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.basebox.weatherinsights.data.repo.InsightRepository

//class InsightViewModelFactory(private val repository: InsightRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(InsightViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return InsightViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
