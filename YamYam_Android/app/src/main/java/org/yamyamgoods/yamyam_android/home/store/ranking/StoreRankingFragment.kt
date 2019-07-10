package org.yamyamgoods.yamyam_android.home.store.ranking

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_store_ranking.*
import org.jetbrains.anko.toast
import org.yamyamgoods.yamyam_android.R
import org.yamyamgoods.yamyam_android.dataclass.StoreCategory
import org.yamyamgoods.yamyam_android.home.store.ranking.adapter.StoreRankingRVAdapter
import org.yamyamgoods.yamyam_android.network.ApplicationController
import org.yamyamgoods.yamyam_android.util.TempData

class StoreRankingFragment : Fragment(), StoreRankingContract.View {

    override lateinit var presenter: StoreRankingContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_store_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenterInit()

        getServerData()
        viewInit()
    }

    override fun showServerFailToast(message: String, t: Throwable) {
        activity!!.toast(message)
        Log.v("Malibin Debug", "t : ${t.message}, stack : ${TextUtils.join("\n", t.stackTrace)}")
    }

    override fun setStoreCategory(data: List<StoreCategory>) {
        categorySpinnerInit()
    }

    private fun presenterInit() {
        presenter = StoreRankingPresenter().apply {
            userToken =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJpYXQiOjE1NjIzMTUzNjYsImV4cCI6MTU2MzYyOTM2Nn0.ZkDGasoDPHTrGvy7yFOT9cPjTQ7gnnUOqekY_zYrAuc"
            view = this@StoreRankingFragment
            storeRepository = ApplicationController.networkServiceStore
        }
    }

    private fun getServerData() {
        presenter.getStoreCategory()
    }

    private fun categorySpinnerInit() {
        //spinner_rv_item_store_ranking_frag.adapter = ArrayAdapter(this,)
    }


    private fun viewInit() {
        rv_item_store_ranking_frag_list.apply {
            val ctx = activity!!.applicationContext
            adapter = StoreRankingRVAdapter(ctx, TempData.storeRankings())
            layoutManager = LinearLayoutManager(ctx)
            addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
        }
    }
}