package com.hrs.Kloping.java.services.impl;

import com.hrs.Kloping.java.services.BaseService;
import com.hrs.MySpringTool.annotations.Entity;


/**
 * 在这里实现想要做的事
 */
@Entity("base1")
public class BaseServiceImpl implements BaseService {
    @Override
    public Number add(int a, int b) {
        return a + b;
    }
}
