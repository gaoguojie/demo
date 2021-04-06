package com.example.demo.javase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Data
@NoArgsConstructor
public class Person {

    int id;
    String personName;

    public Person(String personName) {
        this.personName = personName;
    }


    public static void main(String[] args) {
        String a = "adaasdsadasdff";
       List list = getMax(a);
        System.out.println(list.get(0) + "---" + list.get(1));
    }

    static List getMax(String str) {
        String charData = "";
        int max = 0;
        List<String> list = new ArrayList<>();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            int temp = 0;
            Pattern p = Pattern.compile(String.valueOf(c[i]));
            Matcher m = p.matcher(str);
            while (m.find()) {
                temp++;
            }
            if (temp > max) {
                max = temp;
                charData = String.valueOf(c[i]);

            }
        }
        list.add(String.valueOf(max));
        list.add(charData);
        return list;
    }
}
