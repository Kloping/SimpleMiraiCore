package io.github.kloping.Mcore.kotlin.services.impl

import io.github.kloping.Mcore.kotlin.services.BaseService
import io.github.kloping.MySpringTool.annotations.Entity

/**
 * 在这里实现想要做的事
 *
 * @author github-kloping
 */
@Entity("base1")
class BaseServiceImpl : BaseService {
    override fun add(a: Int, b: Int): Number {
        return a + b
    }
}