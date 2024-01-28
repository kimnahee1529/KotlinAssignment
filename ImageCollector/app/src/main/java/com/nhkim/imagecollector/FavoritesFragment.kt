package com.nhkim.imagecollector

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhkim.imagecollector.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val imageAdapter by lazy { ImageAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as? MainActivity
        val sharedList = mainActivity?.sharedList
        Log.d("favorite fragment", sharedList.toString())

//        imageAdapter.submitList(responseData.documents)
//        if(binding.rvImage.layoutManager == null){
//            binding.rvImage.layoutManager = GridLayoutManager(context, 2)
//            binding.rvImage.adapter = imageAdapter
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}