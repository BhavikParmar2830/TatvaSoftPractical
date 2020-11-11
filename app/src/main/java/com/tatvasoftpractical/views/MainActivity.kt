package com.tatvasoftpractical.views

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatvasoftpractical.R
import com.tatvasoftpractical.base.BaseActivity
import com.tatvasoftpractical.databinding.ActivityMainBinding
import com.tatvasoftpractical.viewmodels.MainActivityViewModel
import java.util.logging.Handler
import kotlin.reflect.KClass


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    private val TAG = "MainActivity"

    override val modelClass: KClass<MainActivityViewModel> = MainActivityViewModel::class
    override val layoutId: Int = R.layout.activity_main

    private lateinit var manager: LinearLayoutManager
    private var adapter: UserListAdapter? = null

    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    override fun initControls() {

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        manager = LinearLayoutManager(this)
        addObserver()

        viewModel.getUserDetailsCall()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.offset = 0
            viewModel.userList.value = ArrayList()
            viewModel.userListSize.value = 0
            viewModel.getUserDetailsCall()
        }

        binding.rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = manager.childCount
                    totalItemCount = manager.itemCount
                    pastVisiblesItems = manager.findFirstVisibleItemPosition()

                    if (viewModel.loading.value!!) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {

                            viewModel.loading.value = false
                            viewModel.offset = viewModel.offset + 10

                            if (viewModel.hasMore) {
                                android.os.Handler()
                                    .postDelayed({ viewModel.getUserDetailsCall() }, 1500)
                            }
                        }
                    }
                }
            }
        })
    }

    private fun addObserver() {

        viewModel.userListSize.observe(this, Observer {
            viewModel.loading.value = true
            if (viewModel.offset == 0) {
                initRecyclerView()
            } else {
                adapter!!.notifyDataSetChanged()
            }
            if (binding.swipeRefresh.isRefreshing){
                binding.swipeRefresh.isRefreshing = false
            }
        })
    }

    private fun initRecyclerView() {
        adapter = UserListAdapter(this, viewModel.userList.value!!)
        binding.rvUserList.layoutManager = manager
        binding.rvUserList.adapter = adapter
    }
}