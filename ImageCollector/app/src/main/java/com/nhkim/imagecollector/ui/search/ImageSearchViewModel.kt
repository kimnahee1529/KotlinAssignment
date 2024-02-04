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
    private var isImageSearchFinished = false
    private var isVideoSearchFinished = false
    private val combinedList: ArrayList<SearchItemModel> = ArrayList()

    fun searchData(query: String){
        searchImages(query)
        searchVideos(query)
    }
    private fun searchImages(query: String) {
        Log.d("viewModel 시작", "searchImages")
        viewModelScope.launch {
            runCatching {
                val response = imageRepository.searchImage(query)
                Log.d("viewModel 반환", "searchImages, $response")
                val searchItemModels = response.documents.map { document ->
                    convertToSearchItemModel(document)
                }
                Log.d("convert 후 viewModel 반환", "searchImages, $searchItemModels")
                combinedList.addAll(searchItemModels)
                isImageSearchFinished = true
                postCombinedDataIfReady()
            }.onFailure {
                Log.e("TAG", "fetchTrendingVideos() failed! : ${it.message}")
            }
        }
    }

    private fun searchVideos(query: String) {
        Log.d("viewModel 시작", "searchVideos")
        viewModelScope.launch {
            runCatching {
                val response = imageRepository.searchVideo(query)
                Log.d("viewModel 반환", "searchVideos, $response")
                val searchItemModels = response.documents.map { document ->
                    convertToSearchItemModel(document)
                }
                combinedList.addAll(searchItemModels)
                isVideoSearchFinished = true
                postCombinedDataIfReady()
            }.onFailure {
                Log.e("TAG", "searchVideos() failed! : ${it.message}")
            }
        }
    }

    private fun postCombinedDataIfReady(){
        if (isImageSearchFinished && isVideoSearchFinished) {
            _imagesData.postValue(ArrayList(combinedList).sortedByDescending { it.dateTime })
            // 검색 상태 초기화
            isImageSearchFinished = false
            isVideoSearchFinished = false
            combinedList.clear()
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