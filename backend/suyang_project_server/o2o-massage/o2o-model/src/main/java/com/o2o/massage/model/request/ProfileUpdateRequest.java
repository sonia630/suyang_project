package com.o2o.massage.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileUpdateRequest {
    private String userName;
    //private String phone;
    private Integer gender;
    private String realName;
    private String email;
    private Date birthday;//yyyy-MM-dd
    private String idNum;
    private String headPic;
    private String verifyCode;
}