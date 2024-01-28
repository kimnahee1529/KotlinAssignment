package com.nhkim.imagecollector

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nhkim.imagecollector.ImageSearchFragment.UtilityKeyboard.hideKeyboard
import com.nhkim.imagecollector.data.Document
import com.nhkim.imagecollector.databinding.FragmentImageSearchBinding
import com.nhkim.imagecollector.retrofit.NetWorkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class ImageSearchFragment : Fragment() {

    var items = listOf<Document>()

    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!
    private val imageAdapter by lazy { ImageAdapter() }

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

        val mainActivity = activity as? MainActivity
        val sharedList = mainActivity?.sharedList

        binding.btnSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            Log.d("searchText", searchText)
            communicateNetWork(searchText)
            saveData()
            this.hideKeyboard()
        }

        // Adapter 설정
        imageAdapter.itemClick = object : ImageAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Log.d("item onClick1", position.toString()+"번째 아이템 클릭")
                val clickedItem = imageAdapter.getDocumentAtPosition(position)
                if (clickedItem != null) {
                    Log.d("item onClick2", clickedItem.thumbnail_url)
                    sharedList?.add(clickedItem.thumbnail_url)
                    Log.d("item onClick3", sharedList.toString())
                }
//                Log.d("item onClick2", items[position].thumbnail_url)
//                sharedList?.add(items[position].thumbnail_url)
//                sharedList.let{
//                    Log.d("item onClick", items[position].thumbnail_url)
//                }
            }
//            override fun onLongClick(view: View, position: Int): Boolean {
//                // 아이템 롱 클릭 시 동작
//
//                return true
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun communicateNetWork(search: String) = lifecycleScope.launch {
        try{
            val authKey = "KakaoAK ${NetWorkClient.apiKey}"
            val responseData = NetWorkClient.imageNetWork.getThumbnailImage(authKey, search, size = 80)
            Log.d("responseData", responseData.documents.toString())

//            items = responseData.documents

            withContext(Dispatchers.Main){
                imageAdapter.submitList(responseData.documents)
                if(binding.rvImage.layoutManager == null){
                    binding.rvImage.layoutManager = GridLayoutManager(context, 2)
                    binding.rvImage.adapter = imageAdapter
                }
            }

        } catch (e: Exception){
            Log.e("NetworkError", "Failed to fetch data", e)
        }
    }
    object UtilityKeyboard {
        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
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
}