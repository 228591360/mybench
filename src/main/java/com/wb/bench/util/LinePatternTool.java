package com.wb.bench.util;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinePatternTool {
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String lineToHump = lineToHump("f_parent_no_leader");
        System.out.println(lineToHump);// fParentNoLeader
      //  System.out.println(humpToLine(lineToHump));// f_parent_no_leader
        System.out.println(humpToLine2(lineToHump));// f_parent_no_leader
        System.out.println(getParam("https://google.com/?name=jeff&nick=213"));

        int[] num={1,2,3,6,8,11};
        int[] c=find(num,9);
        System.out.println(Arrays.toString(c));
    }

    /**
     * 给出一个数组和一个整数目标值 target，你需要找出 2 个数字，他们相加之和等于目标数字，并返回这两个数字的数组下标（升序排序）
     * 注：你可以假设给出的入参一定可以找出这样 2 个数字，并且是唯一解
     * 注：数组中同一个数字不能使用两遍
     * 例子：数组 [3,4,7,15] 目标 10，则 3 + 7 满足目标 10，返回他们的下标 [0, 2]
     */
    public static  int[] find(int[] a,int key){
        int[] result={1,1};
        HashMap<Integer,Integer> map=new HashMap<Integer, Integer>();
        for(int i=0;i<a.length;i++){
            map.put(a[i],i);
        }
        for(int i=0;i<a.length;i++){
            int two=key-a[i];
            if(map.containsKey(two)&&key!=2*two){
                result[0]=i;
                result[1]=map.get(two);
                return  result;
            }

        }
        return  result;
    }

    /**
     * 输入一个合法的 URL 返回它的 query string 解析结果（数据结构参考下方单元测试）
     */
    public static String getParam(String url) {
        String params = url.substring(url.indexOf("?") + 1);
        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(params);
        return JSON.toJSONString(split);
    }
}
