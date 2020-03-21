package com.pianpianboy.leetcode.leetcode;

import java.util.*;

/**
 * @ClassName Test1
 * @Description TODO
 * @Author liaomengjie
 * @Date 2020-03-17 10:12
 */
public class Test1 {

    public static void main(String[] args) {
        HashMap<Character,Integer> map = new HashMap<>();

        for(Map.Entry<Character,Integer> entry: map.entrySet()){
            entry.getKey();
        }
        String t = "";
        String[] split = t.split(",");


        String[] words= {"cat","bt","hat","tree"};
        String chars = "atach";
        System.out.println('a'-97);
        int [] arr = {1,1};
        int[] arr1;
        arr1 = Arrays.copyOf(arr,26);

        List<Integer> list = new LinkedList<>();
        ((LinkedList<Integer>) list).removeLast();
        //countCharacters(words,chars);
    }

    public static int countCharacters(String[] words, String chars) {
        if(chars==null||chars.length()==0){
            return 0;
        }
        int  res = 0;
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i=0;i<chars.length();i++){
            char ch = chars.charAt(i);
            if(map.containsKey(ch)){
                map.put(ch,map.get(ch)+1);
            }else{
                map.put(ch,1);
            }
        }


        for(int i=0;i<words.length;i++){
            String word = words[i];
            boolean flag = true;
            HashMap<Character,Integer> newMap = new HashMap<>();
            newMap.putAll(map);
            for(int j=0;j<word.length();j++){
                char chs = word.charAt(j);
                if(!newMap.containsKey(chs)||newMap.get(chs)<1){
                    flag = false;
                    break;
                }else{
                    newMap.put(chs,newMap.get(chs)-1);
                }
            }
            if(flag) res += word.length();
        }
        return res;
    }
}
