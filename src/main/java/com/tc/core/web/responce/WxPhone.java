package com.tc.core.web.responce;

import lombok.Data;

@Data
public class WxPhone {

    private String phoneNumber;

    private String purePhoneNumber;

    private String countryCode;

    private Watermark watermark;
}
