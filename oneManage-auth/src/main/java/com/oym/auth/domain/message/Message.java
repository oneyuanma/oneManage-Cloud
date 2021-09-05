package com.oym.auth.domain.message;

import lombok.Data;

@Data
public class Message {

    private Long id;    //id

    private int code;  //返回码

    private String msg; //消息

}
