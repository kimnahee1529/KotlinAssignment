package com.nhkim.imagecollector.data.model

class SearchItemModel(var type: Int, var title: String, var dateTime: String, var url: String) {

    // 항목이 '좋아요' 상태인지 나타내는 변수. 기본값은 false.
    var isLike = false
}