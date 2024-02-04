package com.nhkim.imagecollector.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nhkim.imagecollector.data.model.SearchItemModel
import com.nhkim.imagecollector.databinding.FragmentImageSearchBinding
import com.nhkim.imagecollector.factory.ImageSearchViewModelFactory
import com.nhkim.imagecollector.repository.ImageRepository
import com.nhkim.imagecollector.repository.PreferencesRepository
import com.nhkim.imagecollector.utils.UtilityKeyboard.hideKeyboard


class ImageSearchFragment : Fragment(), ImageSearchAdapter.SearchItemClick {
    private val favoriteKey = "saveFavoritesData"
    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!
    private val imageSearchAdapter by lazy { ImageSearchAdapter() }
    private val viewModel: ImageSearchViewModel by viewModels {
        val preferences = requireContext().getSharedPreferences(favoriteKey, Context.MODE_PRIVATE)
        ImageSearchViewModelFactory(ImageRepository(), PreferencesRepository(preferences))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)

        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        imagesData.observe(viewLifecycleOwner) { documents ->
            updateSearchResults(documents)
        }
    }

    private fun setupListeners() {
        binding.btnSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString()

            if (searchText.isNotEmpty()) {
                viewModel.searchData(searchText)
            }
            saveData()
            this.hideKeyboard()
        }
        binding.floatingBtn.setOnClickListener {
            binding.rvImage.smoothScrollToPosition(0)
        }
    }

    private fun updateSearchResults(documents: List<SearchItemModel>) {
        imageSearchAdapter.submitList(documents)
        if (binding.rvImage.layoutManager == null) {
            binding.rvImage.layoutManager = GridLayoutManager(context, 2)
            binding.rvImage.adapter = imageSearchAdapter
            imageSearchAdapter.setItemClick(this@ImageSearchFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun saveData() {
        val pref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val edit = pref?.edit()
        edit?.putString("name", binding.etSearch.text.toString())
        edit?.apply()
    }

    private fun loadData() {
        val pref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val name = pref?.getString("name", "")
        binding.etSearch.setText(name)
    }

    override fun onHeartClick(view: View, position: Int) {
        Log.d("리스너", "onHeartClick")
        imageSearchAdapter.getDocumentAtPosition(position)?.let { document ->
            viewModel.toggleFavorite(document)
        }
    }
}