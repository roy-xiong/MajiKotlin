package com.roy.kotlin.test.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.roy.kotlin.test.R
import com.roy.kotlin.test.entity.InfoEntity
import com.roy.kotlin.test.vm.HistoryListViewModel
import kotlinx.android.synthetic.main.activity_history_list.*

class HistoryListActivity : BaseActivity<HistoryListViewModel, ViewDataBinding>() {
    var page = 1
    var pageSize = 20
    lateinit var adapter: BaseQuickAdapter<InfoEntity, BaseViewHolder>
    override fun initData() {
        viewModel.getGitHubApiFromDB(page, pageSize).observe(this, {
            if (swLayout.isRefreshing) {
                swLayout.isRefreshing = false
            }
            if (page == 1) {
                adapter.setNewInstance(it)
            } else {
                adapter.addData(it)
            }
            if (it.size < pageSize) {
                adapter.loadMoreModule.loadMoreEnd()
                adapter.removeAllFooterView()
            } else {
                adapter.loadMoreModule.loadMoreComplete()
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        val datas = arrayListOf<InfoEntity>()
        rv.layoutManager = LinearLayoutManager(this)
        adapter = object : BaseQuickAdapter<InfoEntity, BaseViewHolder>(R.layout.item_info, datas),
            LoadMoreModule {
            override fun convert(holder: BaseViewHolder, item: InfoEntity) {
                holder.setText(R.id.tvInfo, item.authorizations_url)
            }
        }
        adapter.loadMoreModule.setOnLoadMoreListener {
            page++
            viewModel.getGitHubApiFromDB(page, pageSize)
        }
        rv.adapter = adapter
        swLayout.setOnRefreshListener {
            page = 1
            viewModel.getGitHubApiFromDB(page, pageSize)
        }
    }

    override fun layoutId(): Int = R.layout.activity_history_list

}