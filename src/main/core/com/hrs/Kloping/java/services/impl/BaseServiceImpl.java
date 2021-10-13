package com.hrs.Kloping.java.services.impl;

import com.hrs.Kloping.java.services.BaseService;
import com.hrs.MySpringTool.annotations.Entity;

@Entity("base1")
public class BaseServiceImpl implements BaseService {
    @Override
    public Number add(int a, int b) {
        return a + b;
    }
}
