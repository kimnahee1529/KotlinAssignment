package com.nhkim.imagecollector.ui.favorites

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nhkim.imagecollector.databinding.FragmentFavoritesBinding
import com.nhkim.imagecollector.factory.FavoriteImageViewModelFactory
import com.nhkim.imagecollector.repository.PreferencesRepository

class FavoritesFragment : Fragment(), FavoriteImageAdapter.FavoriteItemClick {
    private val favoriteKey = "saveFavoritesData"

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val imageFavoriteAdapter by lazy { FavoriteImageAdapter() }

    private val viewModel: FavoritesViewModel by viewModels {
        val preferences = requireContext().getSharedPreferences(favoriteKey, Context.MODE_PRIVATE)
        FavoriteImageViewModelFactory(PreferencesRepository(preferences))
    }

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
        setupImageRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setupImageRecyclerView()
    }

    private fun setupImageRecyclerView() {

        binding.rvImage.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = imageFavoriteAdapter
            imageFavoriteAdapter.setItemClick(this@FavoritesFragment)
        }

        viewModel.loadFavoritesDataList()
        viewModel.favorites.observe(viewLifecycleOwner) {
            imageFavoriteAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDeleteClick(view: View, position: Int) {
        Log.d("리스너", "onDeleteClick")
        val currentList = imageFavoriteAdapter.currentList.toMutableList()
        val documentToDelete = currentList[position]
        currentList.removeAt(position)
        imageFavoriteAdapter.submitList(currentList)
        viewModel.deleteFavorite(documentToDelete)
    }
}