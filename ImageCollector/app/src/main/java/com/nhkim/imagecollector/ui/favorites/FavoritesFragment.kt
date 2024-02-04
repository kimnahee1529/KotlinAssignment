package com.nhkim.imagecollector.ui.favorites

import android.content.Context
import android.os.Bundle
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
    private val favoriteImageAdapter by lazy { FavoriteImageAdapter() }

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
            adapter = favoriteImageAdapter
            favoriteImageAdapter.setItemClick(this@FavoritesFragment)
        }

        viewModel.loadFavoritesDataList()
        viewModel.favorites.observe(viewLifecycleOwner) {
            favoriteImageAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDeleteClick(view: View, position: Int) {

        //클릭한 아이템의 참조 얻기
        favoriteImageAdapter.currentList[position]?.let { document ->

            //favoriteImageAdapter.currentList.toMutableList()로 복사본을 만듦
            val updatedList = favoriteImageAdapter.currentList.toMutableList().apply {
                //복사본을 만들어 선택된 위치의 아이템을 리스트에서 제거
                removeAt(position)
            }
            //UI를 즉시 업데이트 함
            favoriteImageAdapter.submitList(updatedList)

            viewModel.toggleFavorite(document)
        }
    }
}