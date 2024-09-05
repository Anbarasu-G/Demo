package com.rest.ets.util;

import com.rest.ets.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheHelper {

    @CachePut(cacheNames = "nonverifiedUsers", key = "#user.email")
    public  User userCache(User user){
        return  user;
    }

    @Cacheable(cacheNames = "nonverifiedotp", key = "#otp")
    public  Integer otpCache(int otp){
        return  otp;
    }
}
