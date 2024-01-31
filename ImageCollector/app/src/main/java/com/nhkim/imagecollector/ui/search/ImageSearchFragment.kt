package com.nhkim.imagecollector.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nhkim.imagecollector.SharedViewModel
import com.nhkim.imagecollector.data.Document
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

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: ImageSearchViewModel by viewModels {
        val preferences = requireContext().getSharedPreferences(favoriteKey, Context.MODE_PRIVATE)
        ImageSearchViewModelFactory(ImageRepository(), PreferencesRepository(preferences))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        initViewModel()

        binding.btnSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            Log.d("searchText", searchText)
            if (searchText.isNotEmpty()) {
                Log.d("fragment 시작", "searchImages")
                viewModel.searchImages(searchText)
            }
            saveData()
            this.hideKeyboard()
        }
    }

    private fun initViewModel() = with(viewModel) {
        imagesData.observe(viewLifecycleOwner) { documents ->
            Log.d("fragment observe", "searchImages, $documents")
            showImages(documents)
        }
    }
    private fun showImages(documents: List<Document>){
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
        imageSearchAdapter.getDocumentAtPosition(position)?.let{ document ->
            val d = document
//            sharedViewModel.toggleFavorite(document)
            viewModel.saveFavoritesDataList(document)
        }

    }

}