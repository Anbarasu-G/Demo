package com.rest.ets.util;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class MessageModel {
    private String to;
    private Date sendDate;
    private String subject;
    private String text;

}
