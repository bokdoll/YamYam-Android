package org.yamyamgoods.yamyam_android.network.post

/**
 * Created By Yun Hyeok
 * on 7월 10, 2019
 */

data class PostBookmarkRequestDTO(
        var goodsIdx: Int,
        var goodsScrapPrice: String,
        var goodsScrapLabel: String,
        var options: List<String>?
)