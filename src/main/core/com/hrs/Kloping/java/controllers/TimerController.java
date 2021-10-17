package com.hrs.Kloping.java.controllers;

import com.hrs.MySpringTool.annotations.Controller;
import com.hrs.MySpringTool.annotations.Schedule;
import com.hrs.MySpringTool.annotations.TimeEve;

@Controller
public class TimerController {
    @TimeEve(10*1000)
    public static void eve1(){
        System.out.println("10秒执行一次");
    }

    @Schedule("00:00:00")
    public static void m1(){
        System.out.println("半夜12点执行");
    }

    @Schedule("06:00:00")
    public static void m2(){
        System.out.println("早上点执行");
    }
}
