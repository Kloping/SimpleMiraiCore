package com.hrs.Kloping.Kotlin.controllers

import com.hrs.Kloping.Kotlin.BotStarter
import com.hrs.MySpringTool.annotations.Action
import com.hrs.MySpringTool.annotations.After
import com.hrs.MySpringTool.annotations.Before
import com.hrs.MySpringTool.annotations.Controller
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

    @Action("[@" + BotStarter.qq + "]你好")
    fun hello_(): String {
        return "你好啊"
    }

    @After
    fun after(group: Group, t1: String) {
        println("处理结果  $t1")
        println(group.name + " 消息 运行之后")
        //若此处抛出 NoRunException 将阻止 消息的 发出
    }
}