package com.example.shoponline.Shared.Utils;

import com.example.shoponline.Activity.MainActivity;

import java.text.Normalizer;

public class CommonFunction {
    /**
     * Kiểm tra chuỗi rỗng
     */
    public static boolean isEmpty(String value) {
        return value.trim().isEmpty();
    }


    public static String convertStringToUnaccented(String str) {
        if (str == null) {
            return null;
        }

        String unaccentedStr = Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("đ", "d");

        return unaccentedStr;
    }
}
