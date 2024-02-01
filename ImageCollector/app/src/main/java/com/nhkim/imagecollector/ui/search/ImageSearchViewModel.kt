package com.nhkim.imagecollector.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhkim.imagecollector.data.Document
import com.nhkim.imagecollector.repository.ImageRepository
import com.nhkim.imagecollector.repository.PreferencesRepository
import kotlinx.coroutines.launch

class ImageSearchViewModel(
    private val imageRepository: ImageRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    //이미지 받아오기
    private val _imagesData = MutableLiveData<List<Document>>()
    val imagesData: LiveData<List<Document>> = _imagesData

    //즐겨찾기 목록
    private val _favorites = MutableLiveData<List<Document>>()
    val favorites: LiveData<List<Document>> = _favorites

    fun searchImages(query: String) {
        Log.d("viewModel 시작", "searchImages")
        viewModelScope.launch {
            runCatching {
                val response = imageRepository.searchImage(query)
                Log.d("viewModel 반환", "searchImages, $response")
                _imagesData.postValue(response.documents)
            }.onFailure {
                Log.e("TAG", "fetchTrendingVideos() failed! : ${it.message}")
            }
        }
    }

    fun toggleFavorite(document: Document) {
        preferencesRepository.toggleFavorite(document)
    }
}


