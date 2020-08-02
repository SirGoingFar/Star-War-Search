package com.trivago.starwarsearch.network.model

class ApiResponse<Data> {
    var status = false
    var message: String? = null
    var data: Data? = null
}