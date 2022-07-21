package com.ujamang.biz.library.extenstion;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.MalformedURLException;
import java.util.regex.Pattern;

public class StringExtenstions {
    /**
     * 널값, 공백 체크
     * @see <a href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html">commons-lang3 StringUtils</a>
     * @param text 입력값
     * @return boolean
     */
    public static boolean isNullOrEmpty(String text) {
        return StringUtils.isBlank(text);
    }

    /**
     * 비밀번호 유효성 체크 (8~30자, 영문, 숫자, 특수문자가 포함되어야합니다.)
     * @param text 입력값
     * @return boolean
     */
    public static boolean isPassword(String text) {
        String pattern = "^.*(?=^.{8,30}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[`,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,,,,.,,/,?,;,:,',\",{,\\[,},\\],\\\\,|]).*$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 일반전화 체크 (일번전화와 1577-0000)
     * @param text 하이픈을 포함한 입력값
     * @return boolean
     */
    public static boolean isTelephoneNumber(String text) {
        String[] textArray = text.split(text);
        String pattern = StringUtils.isEmpty(textArray[0]) ? "^\\d{0}-\\d{4}-\\d{4}$" : "^\\d{2,3}-\\d{3,4}-\\d{4}$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 핸드폰 체크
     * @param text 하이픈을 포함한 입력값
     * @return
     */
    public static boolean isMobileNumber(String text) {
        String pattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 이메일 체크
     * @param text 입력값
     * @return boolean
     */
    public static boolean isEmail(String text) {
        String pattern = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 사업자 번호 체크
     * @param text 하이픈을 포함한 입력값
     * @return boolean
     */
    public static boolean isCoperationNumber(String text) {
        String pattern = "^\\d{3}-\\d{2}-\\d{5}$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 숫자만 입력 체크
     * @param text 입력값
     * @return boolean
     */
    public static boolean isNumeric(String text) {
        return NumberUtils.isDigits(text);
    }

    /**
     * 한글만 입력 체크
     * @param text 입력값
     * @return boolean
     */
    public static boolean isKorean(String text) {
        String pattern = "^[ㄱ-힣\\s]+$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 영문만 입력 체크
     * @param text 입력값
     * @return boolean
     */
    public static boolean isAlphabet(String text) {
        String pattern = "^[a-zA-Z]*$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 영문 숫자만 입력 체크
     * @param text 입력값
     * @return boolean
     */
    public static boolean isAlphabetNumeric(String text) {
        String pattern = "^[a-zA-Z0-9]*$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 영문 숫자, 하이픈, 대시만 입력 체크
     * @param text 입력값
     * @return boolean
     */
    public static boolean isAlphabetNumericHypenUnderbar(String text) {
        String pattern = "^[a-zA-Z0-9-_]*$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * URL 체크
     * @param text 입력값
     * @return
     */
    public static boolean isUrl(String text) throws MalformedURLException {
        String protocolPattern = "^(http:\\/\\/|https:\\/\\/)";

        String pattern = Pattern.compile(protocolPattern).matcher(text).find()
                ? "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$"
                : "^[-a-zA-Z0-9@:%._\\\\+~#=]{1,256}\\\\.[a-zA-Z0-9()]{1,6}\\\\b(?:[-a-zA-Z0-9()@:%_\\\\+.~#?&//=]*)$";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }

    /**
     * 입력 최소 글자수 체크
     * @param text 입력값
     * @param size 최소값
     * @return boolean
     */
    public static boolean isMinimumLength(String text, int size) {
        int textLength = isNullOrEmpty(text)
                ? 0
                : text.length();

        if (textLength < size) {
            return false;
        }

        return true;
    }

    /**
     * 입력 최대 글자수 체크
     * @param text 입력값
     * @param size 최소값
     * @return boolean
     */
    public static boolean isMaximumLength(String text, int size) {
        int textLength = isNullOrEmpty(text)
                ? 0
                : text.length();

        if (textLength > size) {
            return false;
        }

        return true;
    }

    /**
     * 입력 최소~최대 글자수 체크
     * @param text 입력값
     * @param min 최소값
     * @param max 최대값
     * @return boolean
     */
    public static boolean isRangeLength(String text, int min, int max) {
        int textLength = isNullOrEmpty(text)
                ? 0
                : text.length();

        if (textLength > max || textLength < min) {
            return false;
        }

        return true;
    }
}
