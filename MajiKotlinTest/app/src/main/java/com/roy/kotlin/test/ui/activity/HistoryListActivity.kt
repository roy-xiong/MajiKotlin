package com.roy.kotlin.test.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseActivity
import com.aleyn.mvvm.base.NoViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.roy.kotlin.test.R
import kotlinx.android.synthetic.main.activity_history_list.*

class HistoryListActivity : BaseActivity<NoViewModel, ViewDataBinding>() {
    var page =1
    var pageSize = 20
    lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>
    override fun initData() {
//        viewModel.getWishList(page).observe(this, {
//        if (swLayout.isRefreshing) {
//            swLayout.isRefreshing = false
//        }
//        if (page == 1) {
//            adapter.setNewInstance(it.records)
//        } else {
//            adapter.addData(it.records)
//        }
//        if (it.records.size < pageSize) {
//            adapter.loadMoreModule.loadMoreEnd()
//            adapter.removeAllFooterView()
//        } else {
//            adapter.loadMoreModule.loadMoreComplete()
//        }
//        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        val datas = arrayListOf<String>()
        rv.layoutManager = LinearLayoutManager(this)
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_info, datas),
            LoadMoreModule {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tvInfo, item)
            }
        }
        rv.adapter = adapter
    }

    override fun layoutId(): Int = R.layout.activity_history_list

}