package com.hrs.Kloping.java.utils;

import net.mamoe.mirai.message.data.*;

public class MessageUtils {
    public static String toText(MessageChain messageChain) {
        StringBuilder sb = new StringBuilder();
        for (Message message : messageChain) {
            if (message instanceof MessageSource) continue;
            if (message instanceof PlainText) sb.append(message.toString().trim());
            if (message instanceof At) sb.append("[@").append(((At) message).getTarget()).append("]");
            //.........
        }
        return sb.toString();
    }
}
