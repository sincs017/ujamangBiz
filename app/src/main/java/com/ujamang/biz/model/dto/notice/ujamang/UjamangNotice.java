package com.ujamang.biz.model.dto.notice.ujamang;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class UjamangNotice {

    @Getter
    @Builder
    public static class Response{
        public boolean ok;
        public int totalCount;
        public List<String[]> item;
    }
}
