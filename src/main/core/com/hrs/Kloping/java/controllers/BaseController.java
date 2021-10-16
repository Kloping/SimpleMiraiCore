package com.hrs.Kloping.java.controllers;

import com.hrs.Kloping.java.BotStarter;
import com.hrs.Kloping.java.services.BaseService;
import com.hrs.MySpringTool.annotations.*;
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

    @AutoStand
    BaseService service;

    @Action("计算<.+=>str>")
    public String add(@Param("str") String str) {
        try {
            String[] ss = str.split("\\+");
            Integer a = Integer.parseInt(ss[0]);
            Integer b = Integer.parseInt(ss[1]);
            return "结果" + service.add(a, b);
        } catch (NumberFormatException e) {
            return "格式错误";
        }
    }

    @After
    public void after(Group group, String t1) {
        System.out.println("处理结果  " + t1);
        System.out.println(group.getName() + " 消息 运行之后");
        //若此处抛出 NoRunException 将阻止 消息的 发出
    }
}
