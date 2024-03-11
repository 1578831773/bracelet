package com.bracelet.utils;

public class CheckUtils {
    /**
     * 防止js注入
    * @param s
     * @return
     */
    public static String jSSQL(String s) {
        if(s == null || s.isEmpty()) {
            return s;
        }else {
            StringBuilder sb = new StringBuilder(s.length() + 16);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '>':
                        sb.append("&gt");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '\'':
                        sb.append("&#39;");
                        break;
                    case '\"':
                        sb.append("&quot;");
                        break;
                    case '&':
                        sb.append('＆');
                        break;

                    case '#':
                        sb.append('＃');
                        break;
                    case '(':
                        sb.append('（');
                        break;
                    case ')':
                        sb.append('）');
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
            return sb.toString();
        }
    }


    /**
     * 防止js,sql注入
     * @param s
     * @return
     */
    public static String checkSQLInject(String s) {
        String[] inject = { "script", "mid", "master", "truncate", "insert", "select", "delete", "update", "declare",
                "iframe", "'", "onreadystatechange", "alert", "atestu", "xss", "svg", "confirm", "prompt", "onload", "onmouseover", "onfocus", "onerror","from" };
        for(int i = 0;i < inject.length; ++i) {
            if(s.indexOf(inject[i]) >= 0) {
                s= s.replaceAll(inject[i], "");
            }
        }
        return s;
    }


}
