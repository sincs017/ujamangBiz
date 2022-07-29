package com.ujamang.biz.model.dto.notice.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeUser {
    public static class Response {
        public boolean ok;
        public int totalCount;
        public List<Items> item;
    }

    public static class Items {
        public int idx;
        public String title;
        public int hits;
        public String registerDate;
    }


    /*public boolean ok;
    public int totalCount;
    public List<Items> item;


    public static class Items {
        public int idx;
        public String title;
        public int hits;
        public String registerDate;
    }*/

}


