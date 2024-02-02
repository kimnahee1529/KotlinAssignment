package com.nhkim.imagecollector.data.model

import java.util.Date

class SearchItemModel(
    var type: String,
    var title: String,
    var dateTime: Date,
    var url: String
) {
    // 항목이 '좋아요' 상태인지 나타내는 변수. 기본값은 false.
    var isLike = false
}