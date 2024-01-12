package com.nhkim.applemarket

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nhkim.applemarket.ItemManager.itemList
import com.nhkim.applemarket.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFloatingActionButton()
        setupNotificationOnClickListeners()

    }

    private fun setupNotificationOnClickListeners() {
        binding.ivBell.setOnClickListener {
            notification()
        }
    }

    private fun setupFloatingActionButton() {
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true

        binding.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvMain.canScrollVertically(-1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    binding.floatingBtn.startAnimation(fadeOut)
                    binding.floatingBtn.visibility = View.GONE
                    isTop = true
                    Log.d("myLog", "Top")
                } else {
                    if (isTop) {
                        binding.floatingBtn.visibility = View.VISIBLE
                        binding.floatingBtn.startAnimation(fadeIn)
                        isTop = false
                        Log.d("myLog", "Not Top")
                    }
                }
            }
        })

        if (!binding.rvMain.canScrollVertically(-1)) {
            binding.floatingBtn.visibility = View.GONE
        }
        binding.floatingBtn.setOnClickListener {
            binding.rvMain.smoothScrollToPosition(0)
        }
    }

    private fun setupRecyclerView() {
        val itemAdapter = ItemAdapter(itemList)

        with(binding.rvMain) {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        itemAdapter.itemClick = object : ItemAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(Constants.ITEM_INDEX, position)
                    putExtra(Constants.ITEM_OBJECT, itemList[position])
                }
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            }

            override fun onLongClick(view: View, position: Int): Boolean {
                selectedPosition = position
                deleteDialog(itemAdapter)
                return true
            }
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        exitDialog()
    }

    private fun exitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setMessage("정말 종료하시겠습니까?")
        builder.setIcon(R.drawable.chat)

        val listener = DialogInterface.OnClickListener { dialog, whichButton ->
            when (whichButton) {
                DialogInterface.BUTTON_POSITIVE ->
                    finish()

                DialogInterface.BUTTON_NEGATIVE ->
                    dialog.dismiss()
            }
        }

        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", listener)

        builder.show()
    }

    private fun deleteDialog(itemAdapter: ItemAdapter) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("상품 삭제")
        builder.setMessage("상품을 정말로 삭제하시겠습니까?")
        builder.setIcon(R.drawable.chat)

        val listener = DialogInterface.OnClickListener { _, whichButton ->
            when (whichButton) {
                DialogInterface.BUTTON_POSITIVE ->
                    if (selectedPosition >= 0) {
                        itemAdapter.removeItem(selectedPosition)
                        selectedPosition = -1
                    }
            }
        }

        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", listener)

        builder.show()
    }

    private fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 26 버전 이상
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // 채널에 다양한 정보 설정
                description = "My Channel One Description"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)

        } else {
            // 26 버전 이하
            builder = NotificationCompat.Builder(this)
        }

        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다.")
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("설정한 키워드에 대한 알림이 도착했습니다!!")
            )
        }


        manager.notify(11, builder.build())
    }
}