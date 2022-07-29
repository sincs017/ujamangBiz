package com.ujamang.biz.ui.drawer.notice;

public class NoticeItem {
    private String notice;
    private String registerDate;

    public NoticeItem(String notice, String registerDate) {
        this.notice = notice;
        this.registerDate = registerDate;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
