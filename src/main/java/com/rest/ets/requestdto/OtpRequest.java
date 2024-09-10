package com.rest.ets.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {
    private String email;
    private int otp;
}
