package io.github.kloping.Mcore.kotlin.controllers

import io.github.kloping.Mcore.kotlin.services.BaseService
import io.github.kloping.MySpringTool.annotations.*
import io.github.kloping.arr.Class2OMap
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.At

/**
 * @author github-kloping
 */
@Controller
class BaseController {
    @Before
    fun before(contact: Contact) {
        println(contact.id.toString() + "消息 运行之前")
        //若此处抛出 NoRunException 将阻止 Action运行
    }

    @Action("你好啊")
    fun hello(): String {
        return "你好,你好"
    }

    @AutoStand
    var service: BaseService? = null

    @Action("计算<.+=>str>")
    fun add(@Param("str") str: String): String {
        return try {
            val ss = str.split("\\+").toTypedArray()
            val a = ss[0].toInt()
            val b = ss[1].toInt()
            "结果" + service!!.add(a, b)
        } catch (e: NumberFormatException) {
            "格式错误"
        }
    }

    @Action("获取指定类型.*")
    fun getAnyType(cm: Class2OMap): String {
        cm.isIdentical = false
        val (target) = cm.get(At::class.java)
        return String.format("你At了: $target")
    }

    @After
    fun after(contact: Contact, @ReturnResult t1: Any) {
        println("处理结果 =>$t1")
        println(contact.id.toString() + " 消息 运行之后")
        //若此处抛出 NoRunException 将阻止 消息的 发出
    }
}