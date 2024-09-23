package com.rest.ets.util;

import com.rest.ets.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheHelper {

    @CachePut(cacheNames = "nonverifiedusers", key = "#user.email")
    public  User userCache(User user){
        return  user;
    }

    @Cacheable(cacheNames = "nonverifiedusers", key = "#email")
    public User getRegisteringEmail(String email){
       return new User();
    }


    @Cacheable(cacheNames = "otps", key = "#email")
    public  Integer otpCache(int otp,String email){
        return  otp;
    }

    @Cacheable(cacheNames = "otps", key = "#email")
    public int  getOtp(String email){
        return  0;
    }
}
