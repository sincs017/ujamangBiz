package com.ujamang.biz.model.dto.auth;

import lombok.Builder;
import lombok.Getter;

public class Login {

    @Getter
    @Builder
    public static class Request{
        public String memberCompany;
        public String memberId;
        public String password;
        public String machineType;
        public String latitude;
        public String longitude;
        public String country;
        public String locality;
        public String sublocalityLevel1;
        public String sublocalityLevel2;
        public String sublocalityLevel3;
        public String sublocalityLevel4;
        public String sublocalityLevel5;
        public String premise;
        public String machineIp;
        public String externalIp;
        public String mobileUniqueId;
        public String mobileBrand;
        public String mobileDeviceId;
        public String mobileSystemName;
        public String mobileSystemVersion;
        public String mobileReadableVersion;
        public String mobileDeviceName;
        public String mobileTabletYesNo;
        public String mobileDeviceToken;
    }

    @Getter
    @Builder
    public static class Response{
        public boolean ok;
        public String message;
        public String memberCompany;
        public String memberId;
        public String memberNo;
        public String officePositionUserCodeName;
        public String nameKorean;
        public String memberGradeCommonCode;
        public String memberTypeCommonCode;
        public String memberTypeCommonCodeName;
        public String freeMemberExpired;
        public String memberExpired;
        public String serviceStartDate;
        public String serviceEndDate;
        public String authToken;
        public String refreshTokenEpochExpires;
        public String refreshToken;
        //public List<String> item;
    }
}
