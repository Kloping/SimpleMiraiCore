package com.hrs.Kloping.java.controllers;

import com.hrs.Kloping.java.BotStarter;
import com.hrs.MySpringTool.annotations.Action;
import com.hrs.MySpringTool.annotations.After;
import com.hrs.MySpringTool.annotations.Before;
import com.hrs.MySpringTool.annotations.Controller;
import net.mamoe.mirai.contact.Group;

@Controller
public class BaseController {
    @Before
    public void before(Group group) {
        System.out.println(group.getName() + "消息 运行之前");
        //若此处抛出 NoRunException 将阻止 Action运行
    }

    @Action("你好啊")
    public String hello() {
        return "你好,你好";
    }

    @Action("[@" + BotStarter.qq + "]你好")
    public String hello_() {
        return "你好啊";
    }


    @After
    public void after(Group group, String t1) {
        System.out.println("处理结果  " + t1);
        System.out.println(group.getName() + " 消息 运行之后");
        //若此处抛出 NoRunException 将阻止 消息的 发出
    }
}
