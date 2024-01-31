package com.nhkim.imagecollector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhkim.imagecollector.data.Document
import com.nhkim.imagecollector.repository.PreferencesRepository

class SharedViewModel(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    private val selectedDocuments = mutableListOf<Document>()

    //즐겨찾기 목록
    private val _favorites = MutableLiveData<List<Document>>()
    val favorites: LiveData<List<Document>> = _favorites

    fun toggleFavorite(document: Document) {
        val currentFavorites = _favorites.value ?: emptyList()
        val log = document.isHearted
        if (currentFavorites.contains(document)) {
//            Log.d("LOG", document.isHearted.toString())
            _favorites.value = currentFavorites - document
        } else {
            _favorites.value = currentFavorites + document
        }
    }
}