package com.lcaohoanq.constant

import com.lcaohoanq.utils.EnvUtil

object ApiConstant {
    val BASE_URL_FE: String = EnvUtil.get("DEVELOPMENT_FRONTEND_URL") ?: "http://localhost:8082"
    val BASE_URL_BE: String = EnvUtil.get("DEVELOPMENT_BACKEND_URL") ?: "http://localhost:8082/api/v1"
}
