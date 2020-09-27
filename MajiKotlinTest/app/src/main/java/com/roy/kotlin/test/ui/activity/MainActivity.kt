package com.roy.kotlin.test.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import com.roy.kotlin.test.R
import com.roy.kotlin.test.event.RefreshEvent
import com.roy.kotlin.test.service.LoopGetInfoService
import com.roy.kotlin.test.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity<MainViewModel, ViewDataBinding>() {
    override fun initData() {
        viewModel.getGitHubApi().observe(this, {
            tvInfo.text = it.authorizations_url

        })
        LoopGetInfoService.start(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)

    }

    override fun layoutId(): Int = R.layout.activity_main
    override fun onDestroy() {
        super.onDestroy()
        LoopGetInfoService.stop(this)
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefreshInfo(refreshEvent: RefreshEvent) {
        LogUtils.e("时间".plus(refreshEvent.time).plus(ThreadUtils.isMainThread()))
        viewModel.getGitHubApi()
    }
}