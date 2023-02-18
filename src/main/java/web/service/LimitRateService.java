package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import web.redis.LimitRateRedisHandler;

@Service
public class LimitRateService {

    @Value("${app.rate.on:true}")
    private Boolean rate;
    @Value("${app.rate.interval-minute:60}")
    private Integer intervalMinute;

    @Autowired
    private LimitRateRedisHandler limitRateRedisHandler;

    public boolean isAbleToHandle() {


        return false;
    }

}
