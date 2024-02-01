package com.nhkim.imagecollector.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhkim.imagecollector.repository.ImageRepository
import com.nhkim.imagecollector.repository.PreferencesRepository
import com.nhkim.imagecollector.ui.search.ImageSearchViewModel

class ImageSearchViewModelFactory(
    private val imageRepository: ImageRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("Factory", "searchImages")
        if (modelClass.isAssignableFrom(ImageSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageSearchViewModel(imageRepository, preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}