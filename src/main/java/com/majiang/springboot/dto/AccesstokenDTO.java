package com.majiang.springboot.dto;

import lombok.Data;

@Data
public class AccesstokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
