package org.yamyamgoods.yamyam_android.review.photo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_review_photo.*
import org.yamyamgoods.yamyam_android.R
import org.yamyamgoods.yamyam_android.dataclass.ReviewData
import org.yamyamgoods.yamyam_android.network.ApplicationController
import org.yamyamgoods.yamyam_android.network.NetworkServiceGoods
import org.yamyamgoods.yamyam_android.network.get.GetReviewResponse
import org.yamyamgoods.yamyam_android.review.ReviewActivity
import org.yamyamgoods.yamyam_android.review.photo.adapter.ReviewPhotoRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewPhotoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review_photo, container, false)
    }

    var num: Int = 0
    var dataList: ArrayList<ReviewData> = ArrayList()

    val token: String =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJpYXQiOjE1NjIzMTUzNjYsImV4cCI6MTU2MzYyOTM2Nn0.ZkDGasoDPHTrGvy7yFOT9cPjTQ7gnnUOqekY_zYrAuc"

    val networkService: NetworkServiceGoods by lazy {
        ApplicationController.networkServiceGoods
    }

    lateinit var reviewPhotoRVAdapter : ReviewPhotoRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        var goodsIndex: Int = activity!!.intent.getIntExtra("goodsIdx", -1)
        getReviewResponse(goodsIndex, -1)
    }

    private fun getReviewResponse(goodsIndex: Int, lastIndex: Int){
        Log.v("현주", goodsIndex.toString())
        networkService.getReviewRequest("application/json", token, goodsIndex, 1, lastIndex)
            .enqueue(object: Callback<GetReviewResponse> {
                override fun onFailure(call: Call<GetReviewResponse>, t: Throwable) {
                    Log.e("ReviewAllFragment", t.toString())
                }

                override fun onResponse(call: Call<GetReviewResponse>, response: Response<GetReviewResponse>) {
                    if (response.isSuccessful){
                        Log.v("ReviewAllFragment", "통신 성공")

                        dataList = response.body()!!.data!!.review_data

                        response.body()?.let {
                            reviewPhotoRVAdapter = ReviewPhotoRVAdapter(activity!!, dataList)

                            rv_review_photo_list.apply{
                                adapter = ReviewPhotoRVAdapter(activity!!, dataList)
                                layoutManager = LinearLayoutManager(activity!!)
                            }
                            num  = it.data.photo_count
                            (activity!! as ReviewActivity).setPhotoReviewCount(num)
                        }
                        reviewPhotoRVAdapter.notifyDataSetChanged()
                    }
                }
            })
    }

    private fun setRecyclerView() {
        val ctx = activity!!.applicationContext
        rv_review_photo_list.apply {
            adapter = ReviewPhotoRVAdapter(ctx, dataList)
            layoutManager = LinearLayoutManager(ctx)
        }
    }
}