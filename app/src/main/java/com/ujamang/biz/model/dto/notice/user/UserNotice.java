package com.ujamang.biz.model.dto.notice.user;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class UserNotice {

    @Getter
    @Builder
    public static class Response{
        public boolean ok;
        public int totalCount;
        public List<itemString.UserNoticeItem[]> item;
    }
}
