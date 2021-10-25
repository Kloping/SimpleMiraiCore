package com.hrs.Kloping.java.utils;

import net.mamoe.mirai.message.data.*;

public class MessageUtils {
    /**
     * 将消息链 转为 可匹配的 文本
     * @param messageChain
     * @return
     */
    public static String toText(MessageChain messageChain) {
        StringBuilder sb = new StringBuilder();
        for (Message message : messageChain) {
            if (message instanceof MessageSource) continue;
            else if (message instanceof PlainText) sb.append(message.toString().trim());
            else if (message instanceof At) sb.append("[@").append(((At) message).getTarget()).append("]");
            else if (message instanceof Image) sb.append("[图片]");
            else if (message instanceof Audio) sb.append("[语音]");
            else sb.append("[其他消息]");
            //可以自己定义
        }
        return sb.toString();
    }
}
