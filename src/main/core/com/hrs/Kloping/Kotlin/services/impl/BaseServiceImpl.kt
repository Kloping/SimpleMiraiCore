package com.hrs.Kloping.Kotlin.services.impl

import com.hrs.Kloping.Kotlin.services.BaseService
import com.hrs.MySpringTool.annotations.Entity


/**
 * 在这里实现想要做的事
 */
@Entity("base1")
class BaseServiceImpl : BaseService {
    override fun add(a: Int, b: Int): Number {
        return a + b
    }
}