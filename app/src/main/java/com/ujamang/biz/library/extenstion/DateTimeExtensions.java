package com.ujamang.biz.library.extenstion;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateTimeExtensions {
     /**
     * ISO DateTime > Local DateTime으로 변환
     * API Level 26 이하 버전에서 안드로이드 디바이스로 테스트 해야함.
     * @see <a href="https://heeeju4lov.tistory.com/36">관련 블로그</a>
     * @param dateTime             yyyyMMddTHHmmZ format (ex: 20220222T0330Z)
     * @param timeZoneName         타임존 이름 (ex: Asia/Seoul)
     * @param outputDateTimeFormat 반환할 DateTime 형식 (ex: yyyy-MM-dd HH:mm or yyyyMMddHHmm)
     * @return String
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertIsoToLocal(String dateTime, String timeZoneName, String outputDateTimeFormat) {
        ZoneId zoneId = ZoneId.of(timeZoneName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm'Z'");
        ZonedDateTime localDateTime = LocalDateTime.parse(dateTime, formatter).atZone(zoneId);
        String convertDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'"));

        System.out.println(convertDateTime);

        LocalDateTime dateTime1 = LocalDateTime.from(
                Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(convertDateTime))
                        .atZone(ZoneId.of(timeZoneName))
        );

        return dateTime1.format(DateTimeFormatter.ofPattern(outputDateTimeFormat));
    }


    /**
     * 지난주, 이번주, 다음주 일자 기간
     * @param startWeekOfDay 시작요일 (1: 월요일..... 7:일요일)
     * @param datePattern 표시 일자 형식 (yyyyMMdd or yyyy-MM-dd...)
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DateTimeModel.Duration threeWeekDateSeparate(int startWeekOfDay, String datePattern) {
        Calendar cal = Calendar.getInstance();
        LocalDate now = LocalDate.now();
        int dayOfWeekValue = now.getDayOfWeek().getValue();

        int minusDays = startWeekOfDay == 7
                ? -dayOfWeekValue
                : 1 - dayOfWeekValue;

        LocalDate thisWeekStartDate = now.plusDays(minusDays);
        LocalDate thisWeekEndtDate = thisWeekStartDate.plusDays(6);

        LocalDate lastWeekStartDate = thisWeekStartDate.minusDays(7);
        LocalDate lastWeekEndtDate = thisWeekEndtDate.minusDays(7);

        LocalDate nextWeekStartDate = thisWeekStartDate.plusDays(7);
        LocalDate nextWeekEndtDate = thisWeekEndtDate.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        return DateTimeModel.Duration.builder()
                .lastWeekStartDate(lastWeekStartDate.toString())
                .lastWeekEndDate(lastWeekEndtDate.toString())
                .thisWeekStartDate(thisWeekStartDate.toString())
                .thisWeekEndDate(thisWeekEndtDate.toString())
                .nextWeekStartDate(nextWeekStartDate.toString())
                .nextWeekEndDate(nextWeekEndtDate.toString())
                .build();
    }

}
