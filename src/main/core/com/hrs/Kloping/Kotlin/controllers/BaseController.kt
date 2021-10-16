package com.hrs.Kloping.Kotlin.controllers

import com.hrs.Kloping.Kotlin.BotStarter
import com.hrs.Kloping.java.services.BaseService
import com.hrs.MySpringTool.annotations.*
import net.mamoe.mirai.contact.Group

@Controller
class BaseController() {

    @Before
    fun before(group: Group) {
        println(group.name + "消息 运行之前")
        //若此处抛出 NoRunException 将阻止 Action运行
    }

    @Action("你好啊")
    fun hello(): String {
        return "你好,你好"
    }

    @AutoStand
    var service: BaseService? = null

    @Action("计算<.+=>str>")
    fun add(@Param("str") str: String): String? {
        return try {
            val ss = str.split("\\+").toTypedArray()
            val a = ss[0].toInt()
            val b = ss[1].toInt()
            "结果" + service!!.add(a, b)
        } catch (e: NumberFormatException) {
            "格式错误"
        }
    }

    @After
    fun after(group: Group, t1: String) {
        println("处理结果  $t1")
        println(group.name + " 消息 运行之后")
        //若此处抛出 NoRunException 将阻止 消息的 发出
    }
}