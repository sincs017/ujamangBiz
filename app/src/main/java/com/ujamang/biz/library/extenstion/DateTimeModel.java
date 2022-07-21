package com.ujamang.biz.library.extenstion;

import lombok.Builder;
import lombok.Getter;

public class DateTimeModel {
    @Getter
    @Builder
    public static class Duration {
        public String thisWeekStartDate;
        public String thisWeekEndDate;
        public String lastWeekStartDate;
        public String lastWeekEndDate;
        public String nextWeekStartDate;
        public String nextWeekEndDate;
    }
}
