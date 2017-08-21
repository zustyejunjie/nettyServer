package com.netty.server.codec.messagepack;


import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.util.ArrayList;
import java.util.List;

/**
 * messagepack 简单demo
 * Created by yejj on 2017/7/17 0017.
 */
public class Test {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("zhangsan");
        list.add("lisi");
        list.add("wangwu");

        MessagePack pack = new MessagePack();
        try {
            byte[] raw = pack.write(list, Templates.tList(Templates.TString));

            List<String> listResult = pack.read(raw, Templates.tList(Templates.TString));
            System.out.println(listResult.get(0));
            System.out.println(listResult.get(1));
            System.out.println(listResult.get(2));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
