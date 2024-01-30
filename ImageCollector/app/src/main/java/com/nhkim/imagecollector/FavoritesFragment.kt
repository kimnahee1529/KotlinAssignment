package com.nhkim.imagecollector

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nhkim.imagecollector.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(), ImageFavoriteAdapter.FavoriteItemClick {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val imageFavoriteAdapter by lazy { ImageFavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as? MainActivity
        val sharedList = mainActivity?.sharedList

        imageFavoriteAdapter.submitList(sharedList)

        binding.rvImage.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = imageFavoriteAdapter
            imageFavoriteAdapter.setItemClick(this@FavoritesFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDeleteClick(view: View, position: Int) {
        Log.d("리스너", "onDeleteClick")
        imageFavoriteAdapter.removeItem(position)
    }
}