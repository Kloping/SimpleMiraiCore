package io.github.kloping.Mcore.java.controllers;

import io.github.kloping.Mcore.java.services.BaseService;
import io.github.kloping.MySpringTool.annotations.*;
import io.github.kloping.arr.Class2OMap;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.At;

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

    @Action("获取指定类型.*")
    public String getAnyType(Class2OMap cm) {
        cm.setIdentical(false);
        At at = cm.get(At.class);
        return String.format("你At了: " + at.getTarget());
    }

    @After
    public void after(Group group, String t1) {
        System.out.println("处理结果  " + t1);
        System.out.println(group.getName() + " 消息 运行之后");
        //若此处抛出 NoRunException 将阻止 消息的 发出
    }
}
