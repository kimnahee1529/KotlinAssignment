package com.nhkim.imagecollector.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhkim.imagecollector.data.Document

class PreferencesRepository(private val sharedPreferences: SharedPreferences) {
    private val favoriteKey = "saveFavoritesData"
    fun saveFavoritesDataList(userDataList: List<Document>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(userDataList)
        editor.putString(favoriteKey, json)
        editor.apply()
    }

    fun loadFavoritesDataList(): List<Document> {
        val json = sharedPreferences.getString(favoriteKey, null)
        return if (json != null) {
            val type = object : TypeToken<List<Document>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

}