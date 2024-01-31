package com.nhkim.imagecollector.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhkim.imagecollector.data.Document
import com.nhkim.imagecollector.repository.ImageRepository
import kotlinx.coroutines.launch

class ImageSearchViewModel(
    private val repository: ImageRepository
): ViewModel() {

    val _imagesData = MutableLiveData<List<Document>>()
    val imagesData: LiveData<List<Document>> = _imagesData

    fun searchImages(query: String){
        Log.d("viewModel 시작", "searchImages")
        viewModelScope.launch {
            runCatching {
                val response = repository.searchImage(query)
                Log.d("viewModel 반환", "searchImages, $response")
                _imagesData.postValue(response.documents)
            } .onFailure {
                Log.e("TAG", "fetchTrendingVideos() failed! : ${it.message}")
            }
        }
    }
}


