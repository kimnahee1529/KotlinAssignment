package com.nhkim.imagecollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nhkim.imagecollector.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var sharedList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageSearchFragment = ImageSearchFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_fragment, imageSearchFragment)
                commit()
            }
        }

        binding.btnFavorites.setOnClickListener {
            val favoritesFragment = FavoritesFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment, favoritesFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.btnImageSearch.setOnClickListener {
            val imageSearchFragment = ImageSearchFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment, imageSearchFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}