package com.tenda.digital.coupon.common.utility;

import java.text.Normalizer;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class StringTransformUtil {

    private StringTransformUtil() {}

    public static final UnaryOperator<String> removeExtraSpaces =
            str -> str == null ? null : Pattern.compile("\\s{2,}").matcher(str.trim()).replaceAll(" ");

    public static final UnaryOperator<String> removeAccents =
            str -> str == null ? null : Normalizer.normalize(str, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    public static final UnaryOperator<String> toUpperCase =
            str -> str == null ? null : str.toUpperCase();

    public static final UnaryOperator<String> toLowerCase =
            str -> str == null ? null : str.toLowerCase();

    public static final UnaryOperator<String> removeNonNumeric =
            str -> str == null ? null : str.replaceAll("\\D", "");

    public static final UnaryOperator<String> removeNonAlphanumeric =
            str -> str == null ? null : str.replaceAll("[^a-zA-Z0-9]", "");

    public static final UnaryOperator<String> removeNonAlphanumericExceptCommaAndSpace =
            str -> str == null ? null : str.replaceAll("[^a-zA-Z0-9,\\s]", "");

    public static final UnaryOperator<String> removeAllSpaces =
            str -> str == null ? null : str.replaceAll("\\s+", "");

    @SafeVarargs
    public static String transform(String str, UnaryOperator<String>... transformations) {
        String result = str;
        for (UnaryOperator<String> transformation : transformations) {
            result = transformation.apply(result);
        }
        return result;
    }
}


