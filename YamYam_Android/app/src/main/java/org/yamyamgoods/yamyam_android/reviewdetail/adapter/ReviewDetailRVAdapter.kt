package org.yamyamgoods.yamyam_android.reviewdetail.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import org.yamyamgoods.yamyam_android.R
import org.yamyamgoods.yamyam_android.network.get.ReviewCommentData
import org.yamyamgoods.yamyam_android.reviewdetail.ReviewDetailActivity

class ReviewDetailRVAdapter(private val ctx: Context, private val dataList: List<ReviewCommentData>): RecyclerView.Adapter<ReviewDetailRVAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewDetailRVAdapter.Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_review_detail_comment, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        dataList[position].let{ item->
            Glide.with(ctx)
                    .load(item.user_img)
                    .centerCrop()
                    .circleCrop()
                    .into(holder.ivUserImage)

            holder.tvUserNickName.text = item.user_name
            holder.tvDate.text = item.goods_review_cmt_date
            holder.tvContents.text = item.goods_review_cmt_content

            holder.clCommentItem.setOnClickListener{
                (ctx as ReviewDetailActivity).editTextTag(item.user_name, item.user_idx)
            }
        }
    }

    inner class Holder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        var clCommentItem: ConstraintLayout = itemView.findViewById(R.id.cl_rv_item_reveiw_detail_comment) as ConstraintLayout
        var ivUserImage: ImageView = itemView.findViewById(R.id.iv_rv_item_review_detail_comment_user_image) as ImageView
        var tvUserNickName: TextView = itemView.findViewById(R.id.tv_rv_item_review_detail_comment_user_nickname) as TextView
        var tvDate: TextView = itemView.findViewById(R.id.tv_rv_item_review_detail_comment_date) as TextView
        var tvContents: TextView = itemView.findViewById(R.id.tv_rv_item_review_detail_comment_contents) as TextView
    }
}