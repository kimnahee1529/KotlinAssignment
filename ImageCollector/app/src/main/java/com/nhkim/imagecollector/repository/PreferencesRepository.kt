package com.nhkim.imagecollector.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhkim.imagecollector.data.image.Document

class PreferencesRepository(private val sharedPreferences: SharedPreferences) {
    private val favoriteKey = "saveFavoritesData"

    fun loadFavoritesDataList(): List<Document> {
        val json = sharedPreferences.getString(favoriteKey, null)
        return if (json != null) {
            val type = object : TypeToken<List<Document>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun toggleFavorite(document: Document) {
        val currentFavorites = getFavoritesDataList() // 현재 저장된 즐겨찾기 리스트를 가져옴
        val editor = sharedPreferences.edit()
        val gson = Gson()

        var foundDocument: Document? = null

        for (favDoc in currentFavorites) {
            if (favDoc.thumbnail_url == document.thumbnail_url) {
                foundDocument = favDoc
                break
            }
        }

        if (foundDocument != null) {
            currentFavorites.remove(foundDocument)
        } else {
            currentFavorites.add(document)
        }

        val json = gson.toJson(currentFavorites)
        editor.putString(favoriteKey, json)
        editor.apply()
    }

    fun getFavoritesDataList(): MutableList<Document> {
        val json = sharedPreferences.getString(favoriteKey, null)
        val type = object : TypeToken<List<Document>>() {}.type
        return Gson().fromJson(json, type) ?: mutableListOf()
    }

    fun deleteFavorite(document: Document) {
        val currentFavorites = getFavoritesDataList()
        val gson = Gson()
        currentFavorites.remove(document)
        val json = gson.toJson(currentFavorites)
        sharedPreferences.edit().putString(favoriteKey, json).apply()
    }

}