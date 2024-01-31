package com.nhkim.imagecollector.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhkim.imagecollector.data.Document
import com.nhkim.imagecollector.repository.PreferencesRepository

class FavoritesViewModel(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    //즐겨찾기 목록
    private val _favorites = MutableLiveData<List<Document>>()
    val favorites: LiveData<List<Document>> = _favorites

    fun loadFavoritesDataList(){
        val dataList = preferencesRepository.loadFavoritesDataList()
        _favorites.value = dataList
    }

}