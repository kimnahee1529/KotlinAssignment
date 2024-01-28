package com.nhkim.applemarket

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhkim.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemKey = "Item"
//
        val itemAdapter = ItemAdapter(ItemManager.itemList)
        binding.rvMain.clipToOutline = true

        with(binding.rvMain){
//            this.adapter = itemAdapter
//            adapter = itemAdapter
            this@with.adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }


        binding.ivBell.setOnClickListener {
            notification()
        }

        itemAdapter.itemClick = object: ItemAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(itemKey, ItemManager.itemList[position])
                }
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            }
        }

    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        popUpDialog()
    }
    private fun popUpDialog(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setMessage("정말 종료하시겠습니까?")
        builder.setIcon(R.mipmap.ic_launcher)

        // 버튼 클릭시에 무슨 작업을 할 것인가!
        val listener = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                when (p1) {
//                        DialogInterface.BUTTON_POSITIVE ->
//                            binding.tvTitle.text = "BUTTON_POSITIVE"
//                        DialogInterface.BUTTON_NEUTRAL ->
//                            binding.tvTitle.text = "BUTTON_NEUTRAL"
//                        DialogInterface.BUTTON_NEGATIVE ->
//                            binding.tvTitle.text = "BUTTON_NEGATIVE"
                }
            }
        }

        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", listener)

        builder.show()
    }

    fun notification(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 26 버전 이상
            val channelId="one-channel"
            val channelName="My Channel One"
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

        }else {
            // 26 버전 이하
            builder = NotificationCompat.Builder(this)
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.pizza)
//        val intent = Intent(this, SecondActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다.")
            setStyle(NotificationCompat.BigTextStyle()
                .bigText("설정한 키워드에 대한 알림이 도착했습니다!!"))
                        setLargeIcon(bitmap)
//            setStyle(NotificationCompat.BigPictureStyle()
//                    .bigPicture(bitmap)
//                    .bigLargeIcon(null))  // hide largeIcon while expanding
//                        addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }


        manager.notify(11, builder.build())
    }
}