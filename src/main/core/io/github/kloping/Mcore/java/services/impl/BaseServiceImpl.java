package io.github.kloping.Mcore.java.services.impl;

import io.github.kloping.Mcore.java.BotStarter;
import io.github.kloping.Mcore.java.services.BaseService;
import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;

/**
 * 在这里实现想要做的事
 *
 * @author github-kloping
 */
@Entity("base1")
public class BaseServiceImpl implements BaseService {
    @Override
    public Number add(int a, int b) {
        return a + b;
    }
}
