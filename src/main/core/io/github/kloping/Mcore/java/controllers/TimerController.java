package io.github.kloping.Mcore.java.controllers;

import io.github.kloping.MySpringTool.annotations.Controller;
import io.github.kloping.MySpringTool.annotations.Schedule;
import io.github.kloping.MySpringTool.annotations.TimeEve;

/**
 * @author github-kloping
 */
@Controller
public class TimerController {
    @TimeEve(10 * 1000)
    public static void eve1() {
        System.out.println("10秒执行一次");
    }

    @Schedule("00:00:00")
    public static void m1() {
        System.out.println("半夜12点执行");
    }

    @Schedule("06:00:00")
    public static void m2() {
        System.out.println("早上点执行");
    }
}
