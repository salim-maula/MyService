package com.salim.myservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var mServiceBound = false
    private lateinit var mBoundService : MyBoundService

    private val mServicesConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound=true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServiceBound = false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartServices = this.findViewById<Button>(R.id.btn_start_job_intent_service)
        btnStartServices.setOnClickListener {
                val mStartServiceIntent = Intent(this, MyService::class.java)
            startService(mStartServiceIntent)
        }

        val btnStatrtJobIntentService = findViewById<Button>(R.id.btn_start_job_intent_service)
        btnStatrtJobIntentService.setOnClickListener {
                val mStartIntentService = Intent(this, MyJobIntentService::class.java)
            mStartIntentService.putExtra(MyJobIntentService.EXTRA_DURATION,5000L)
            MyJobIntentService.enqueWork(this, mStartIntentService)
        }

        val btnStatrtBoundService = findViewById<Button>(R.id.btn_start_bound_service)
        btnStatrtBoundService.setOnClickListener {
            val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
            bindService(mBoundServiceIntent, mServicesConnection, BIND_AUTO_CREATE)
        }

        val btnStopBoundService = findViewById<Button>(R.id.btn_stop_bound_service)
        btnStopBoundService.setOnClickListener {
            unbindService(mServicesConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound){
            unbindService(mServicesConnection)
        }
    }
}