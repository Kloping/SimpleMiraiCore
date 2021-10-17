package com.hrs.Kloping.Kotlin.controllers

import com.hrs.MySpringTool.annotations.Controller
import com.hrs.MySpringTool.annotations.Schedule
import com.hrs.MySpringTool.annotations.TimeEve

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