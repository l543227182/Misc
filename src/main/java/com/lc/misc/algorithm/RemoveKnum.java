package com.lc.misc.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luochao
 * @date 2020/11/15 4:46 下午
 */
public class RemoveKnum {
    public static void main(String[] args) {
        String s = new RemoveKnum().removeKdigits("1432219", 3);
        System.out.println(s);
    }

    public String removeKdigits(String num, int k) {
        int i = num.indexOf("0");
        if (i != -1 && i <= k) {
            int restK = k - i;
            String subNum = num.substring(i + 1);
            if (subNum.length() == 0 || isZero(subNum)) {
                return "0";
            }
            if (restK == 0) {
                return subNum;
            }
            return compareNum(findNotZero(subNum), restK);
        }
        String s = compareNum(num, k);
        return s;
    }

    public boolean isZero(String num) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.equals(new BigDecimal("0"));
    }

    public String findNotZero(String num) {
        int i = 0;
        while (i < num.length() && num.charAt(i) == '0') {
            i++;
        }
        return num.substring(i);
    }

    public String compareNum(String num, int k) {
        String s1 = pickTheLargestNum(num, k, false);
        String s2 = pickTheLargestNum(num, k, true);
        if (new BigDecimal(s2).compareTo(new BigDecimal(s1)) > 0) {
            return s1;
        } else {
            return s2;
        }
    }

    public String pickTheLargestNum(String num, int k, boolean isRemove) {
        if (num.length() == k) {
            return "0";
        }
        List<String> nums = Arrays.stream(num.split("")).collect(Collectors.toList());
        if (isRemove) {
            nums.remove(nums.size() - 1);
        }

        List<String> numList = nums.stream().sorted(String::compareTo).collect(Collectors.toList());
        Collections.reverse(numList);
        List<String> theMaxKNum = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            theMaxKNum.add(numList.get(i));
        }
        List<String> sourceList = Arrays.stream(num.split("")).collect(Collectors.toList());
        theMaxKNum.forEach(item -> {
            sourceList.remove(item);
        });
        String result = "";
        for (String item : sourceList) {
            result += item;
        }
        return result;
    }
}
