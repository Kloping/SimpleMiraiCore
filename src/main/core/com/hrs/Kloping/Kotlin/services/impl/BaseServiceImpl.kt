package com.hrs.Kloping.Kotlin.services.impl

import com.hrs.Kloping.Kotlin.services.BaseService
import com.hrs.MySpringTool.annotations.Entity

@Entity("base1")
class BaseServiceImpl : BaseService {
    override fun add(a: Int, b: Int): Number {
        return a + b
    }
}