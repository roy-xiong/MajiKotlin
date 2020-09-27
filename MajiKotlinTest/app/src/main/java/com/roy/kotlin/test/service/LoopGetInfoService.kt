package com.roy.kotlin.test.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.text.format.DateUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.Utils
import com.roy.kotlin.test.event.RefreshEvent
import com.roy.kotlin.test.http.HttpData
import org.greenrobot.eventbus.EventBus
import java.sql.Ref
import java.util.*
import java.util.concurrent.TimeUnit

class LoopGetInfoService : Service() {
    var loopGetInfoTask: LoopGetInfoTask? = null

    companion object {
        /**
         * 开启服务
         */
        fun start(context: Context) {
            val intent = Intent(context, LoopGetInfoService::class.java)
            context.startService(intent)
        }

        /**
         * 关闭服务
         */
        fun stop(context: Context) {
            val intent = Intent(context, LoopGetInfoService::class.java)
            context.stopService(intent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (loopGetInfoTask != null) {
            ThreadUtils.cancel(loopGetInfoTask)
            loopGetInfoTask = null
        }
        if (loopGetInfoTask == null) {
            loopGetInfoTask = LoopGetInfoTask()
            ThreadUtils.executeByIoAtFixRate(loopGetInfoTask, 10, TimeUnit.SECONDS)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? = null


    class LoopGetInfoTask : ThreadUtils.SimpleTask<String>() {
        override fun doInBackground(): String {
            //调用接口
            EventBus.getDefault()
                .post(RefreshEvent(TimeUtils.date2String(Date(), "HH:mm:ss")))
            return "线程池执行上传gps任务-->"
        }

        override fun onSuccess(result: String) {
//            LogUtils.e(result.plus("执行成功"))
        }
    }

}