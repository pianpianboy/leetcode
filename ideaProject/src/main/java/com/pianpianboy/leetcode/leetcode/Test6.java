package com.pianpianboy.leetcode.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @ClassName Test6
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-21 10:55
 */
public class Test6 {

    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        HashMap<Integer,Integer> map = new HashMap<>();
        map.add(5);
        System.out.println(map.add(5));
        System.out.println(map.add(4));
        for(Map.Entry<Integer,Integer>entry: map.entrySet()){
            entry.getValue();
        }
    }
}
