package org.yamyamgoods.yamyam_android.home.store.ranking

data class StoreRankingItem(

        val storeIdx: Int,

        val idx: Int,
        val imageUrl: Int,// 수정할 것

        val storeName: String,
        val hashTags: List<String>,
        val starRate: Float,
        val reviewCount: Int,

        val isLiked: Boolean

)