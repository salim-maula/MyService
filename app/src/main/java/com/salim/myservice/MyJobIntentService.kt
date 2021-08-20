package com.salim.myservice

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    companion object{
        private const val JOB_ID = 1000
        internal const val EXTRA_DURATION = "extra_duration"
        private val TAG = MyJobIntentService::class.java.simpleName

        fun enqueWork(context: Context, intent: Intent){
            enqueueWork(context,MyJobIntentService::class.java, JOB_ID,intent)
        }

    }

    override fun onHandleWork(intent: Intent) {

        Log.d(TAG, "onHandleWork : selesai...")
        val duration = intent.getLongExtra(EXTRA_DURATION,0)
        try {
            Thread.sleep(duration)
            Log.d(TAG, "onHAndleWork : selesai....")
        }catch (e: InterruptedException){
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

}