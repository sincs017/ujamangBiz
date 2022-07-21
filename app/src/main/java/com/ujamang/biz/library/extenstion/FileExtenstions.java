package com.ujamang.biz.library.extenstion;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileExtenstions {
     /**
     * 업로드 허용 파일 확장자
     * @param text 입력값
     * @param fileExtensionList 허용할 파일 확장자 (ArrayList로 전달)
     * @return boolean
     */
    public static boolean isAllowUploadFileExtension(String text, ArrayList<String> fileExtensionList) {
        String allowFileExtension = String.join("|", fileExtensionList);
        String pattern = "([^\\s]+(\\.(?i)(" + allowFileExtension + "))$)";

        return Pattern.compile(pattern)
                .matcher(text)
                .find();
    }
}
