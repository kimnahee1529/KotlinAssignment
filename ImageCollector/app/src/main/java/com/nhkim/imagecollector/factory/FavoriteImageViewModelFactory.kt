package com.nhkim.imagecollector.factory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhkim.imagecollector.repository.ImageRepository
import com.nhkim.imagecollector.repository.PreferencesRepository
import com.nhkim.imagecollector.ui.favorites.FavoritesViewModel

class FavoriteImageViewModelFactory(
    private val repository: ImageRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("Factory", "searchImages")
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository, preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}