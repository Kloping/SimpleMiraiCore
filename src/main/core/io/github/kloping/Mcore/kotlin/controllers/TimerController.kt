package io.github.kloping.Mcore.kotlin.controllers

import io.github.kloping.MySpringTool.annotations.Controller
import io.github.kloping.MySpringTool.annotations.Schedule
import io.github.kloping.MySpringTool.annotations.TimeEve

/**
 * @author github-kloping
 */
@Controller
object TimerController {
    @TimeEve(10 * 1000)
    fun eve1() {
        println("10秒执行一次")
    }

    @Schedule("00:00:00")
    fun m1() {
        println("半夜12点执行")
    }

    @Schedule("06:00:00")
    fun m2() {
        println("早上点执行")
    }
}