package com.trivago.starwarsearch.common.network.model

class ApiResponse<Data> {
    var status = false
    var message: String? = null
    var data: Data? = null
}