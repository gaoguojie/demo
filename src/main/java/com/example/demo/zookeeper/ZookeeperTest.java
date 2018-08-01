package com.example.demo.zookeeper;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/14
 */
public class ZookeeperTest {



    public static void main(String[] args){

        String path = "/test_zookeeper";
        String reason = null;
        char lastc = '/';
        char chars[] = path.toCharArray();
        char c;
        for (int i = 1; i < chars.length; lastc = chars[i], i++) {
            c = chars[i];

            if (c == 0) {
                reason = "null character not allowed @" + i;
                break;
            } else if (c == '/' && lastc == '/') {
                reason = "empty node name specified @" + i;
                break;
            } else if (c == '.' && lastc == '.') {
                if (chars[i-2] == '/' &&
                        ((i + 1 == chars.length)
                                || chars[i+1] == '/')) {
                    reason = "relative paths not allowed @" + i;
                    break;
                }
            } else if (c == '.') {
                if (chars[i-1] == '/' &&
                        ((i + 1 == chars.length)
                                || chars[i+1] == '/')) {
                    reason = "relative paths not allowed @" + i;
                    break;
                }
            } else if (c > '\u0000' && c < '\u001f'
                    || c > '\u007f' && c < '\u009F'
                    || c > '\ud800' && c < '\uf8ff'
                    || c > '\ufff0' && c < '\uffff') {
                reason = "invalid charater @" + i;
                break;
            }
        }
        System.out.println(reason);
    }
}
