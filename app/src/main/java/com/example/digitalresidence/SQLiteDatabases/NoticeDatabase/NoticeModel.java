package com.example.digitalresidence.SQLiteDatabases.NoticeDatabase;

public class NoticeModel {
    private int NoticeId;
    private String NoticeTitle;
    private String NoticeApplicableTo;
    private String NoticeDescription;
    private String NoticeTimeStamp;

    public NoticeModel(){}

    public NoticeModel(int noticeId, String noticeTitle, String noticeApplicableTo, String noticeDescription, String noticeTimeStamp) {
        NoticeId = noticeId;
        NoticeTitle = noticeTitle;
        NoticeApplicableTo = noticeApplicableTo;
        NoticeDescription = noticeDescription;
        this.NoticeTimeStamp = noticeTimeStamp;
    }

    public int getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(int noticeId) {
        NoticeId = noticeId;
    }

    public String getNoticeTitle() {
        return NoticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        NoticeTitle = noticeTitle;
    }

    public String getNoticeApplicableTo() {
        return NoticeApplicableTo;
    }

    public void setNoticeApplicableTo(String noticeApplicableTo) {
        NoticeApplicableTo = noticeApplicableTo;
    }

    public String getNoticeDescription() {
        return NoticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        NoticeDescription = noticeDescription;
    }

    public String getNoticeTimeStamp() {
        return NoticeTimeStamp;
    }

    public void setNoticeTimeStamp(String noticeTimeStamp) {
        NoticeTimeStamp = noticeTimeStamp;
    }
}
