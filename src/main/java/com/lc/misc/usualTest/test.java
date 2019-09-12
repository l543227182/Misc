package com.lc.misc.usualTest;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class test {
    @Data
    @AllArgsConstructor
    static  class A {
    Integer inta;
    Integer intb;
    }
    public static boolean isNumber(String myString) {

        final String Digits     = "(\\p{Digit}+)";
        final String HexDigits  = "(\\p{XDigit}+)";
        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp        = "[eE][+-]?"+Digits;
        final String fpRegex    =
                ("[\\x00-\\x20]*"+  // Optional leading "whitespace"
                        "[+-]?(" + // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix
                        //
                        // Since this method allows integer-only strings as input
                        // in addition to strings of floating-point literals, the
                        // two sub-patterns below are simplifications of the grammar
                        // productions from section 3.10.2 of
                        // The Java Language Specification.

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.("+Digits+")("+Exp+")?)|"+

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"
        String myRegex = "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";
        if (Pattern.matches(myRegex, myString)){
            return true;
        } else {
            return  false;
        }
    }

    public static void test() {
        final String[] splitTags = "system_win_linux_diskio,fields.collect_instance=[550\\,552],fields.gims_collect_instance=system_win_linux,fields.gims_collect_instance_id=550,fields.os_type=LINUX,fields.private_ip=10.246.204.85,fields.project_id=39,fields.public_ip=116.246.204.85,gims_collect_instance=system_win_linux,gims_collect_instance_id=550,gims_source=system_win_linux_diskio,gims_source_id=786,metricset.name=diskio,system.diskio.name=dm-0".split(",");
        Map<String, String> tagMap = new HashMap<>(16);
        for (String tag : splitTags) {
            final int equalSignIndex = tag.indexOf("=");
            Assert.isTrue(equalSignIndex > 0, "tag must be include equal sign.");
            tagMap.put(tag.substring(0, equalSignIndex), tag.substring(equalSignIndex + 1));
        }
    }
    public static void main(String args[]){
        List<Object> qer = Arrays.asList(1, 2, 3, 4, 5, "qer");
        String join = Joiner.on(",").join(qer);
        System.out.println(join);
    }

    private static void sort() {
        Set<String> sets = new TreeSet<String>(Comparator.naturalOrder());
        sets.add("罗超");
        sets.add("lll");
        sets.add("啊");
        sets.add("hhh");
        sets.add("aa");
        sets.add("罗超");
        sets.add("罗超2");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        sets.add("罗超3");
        String result = (StringUtils.join(sets,","));
        String substring = result.substring(0, result.length() - 1);
        System.out.println(substring);
    }

    private static void a() {
        A a1 = new A(1,1);
        A a2 = new A(2,2);
        A a3 = new A(3,3);
        A a4 = new A(4,4);

        List<A> as = Arrays.asList(a1, a2, a3, a4);
        List<A> collect = as.stream().filter(item -> item.getInta() > 2).collect(Collectors.toList());
        String a = "abc";
        String b = "ab";
        String c = "c";
        System.out.println(a == b + c);
    }
}
