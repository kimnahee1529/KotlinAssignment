package com.nhkim.imagecollector.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhkim.imagecollector.data.image.Document as ImageDocument
import com.nhkim.imagecollector.data.video.Document as VideoDocument
import com.nhkim.imagecollector.data.model.SearchItemModel
import com.nhkim.imagecollector.repository.ImageRepository
import com.nhkim.imagecollector.repository.PreferencesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ImageSearchViewModel(
    private val imageRepository: ImageRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    //이미지 받아오기
    private val _imagesData = MutableLiveData<List<SearchItemModel>>()
    val imagesData: LiveData<List<SearchItemModel>> = _imagesData

    fun searchData(query: String){
        viewModelScope.launch {
            val imageSearchDeferred = async { imageRepository.searchImage(query) }
            val videoSearchDeferred  = async { imageRepository.searchVideo(query) }

            kotlin.runCatching {
                val imageResponse = imageSearchDeferred.await()
                val videoResponse = videoSearchDeferred.await()

                val imageModels = imageResponse.documents.map { convertToSearchItemModel(it) }
                val videoModels = videoResponse.documents.map { convertToSearchItemModel(it) }

                val combinedList = (imageModels + videoModels).sortedByDescending { it.dateTime }

                _imagesData.postValue(combinedList)
            }.onFailure {
                Log.e("TAG", "searchData() failed! : ${it.message}")
            }
        }
    }

    fun toggleFavorite(document: SearchItemModel) {
        preferencesRepository.toggleFavorite(document)
    }

    private fun convertToSearchItemModel(document: ImageDocument): SearchItemModel {

        return SearchItemModel(
            type = "Image",
            title = document.display_sitename,
            dateTime = document.datetime,
            url = document.thumbnail_url
        ).apply {
            this.isLike = document.isHearted
        }
    }

    private fun convertToSearchItemModel(document: VideoDocument): SearchItemModel {

        return SearchItemModel(
            type = "Video",
            title = document.title,
            dateTime = document.datetime,
            url = document.thumbnail
        ).apply {
            this.isLike = document.isHearted
        }
    }
}


