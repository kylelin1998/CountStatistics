package web.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import web.common.constant.RedisConstant;
import web.common.util.ExceptionUtil;
import web.config.AppConfig;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LimitRateRedisHandler {

    @Autowired
    private AppConfig config;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void add(String ip) {
        try {
            String key = RedisConstant.IpLimit + ip;
            Boolean hasKey = redisTemplate.hasKey(key);

            redisTemplate.opsForValue().increment(key, 1);
            if (null == hasKey || !hasKey) {
                redisTemplate.expire(key, config.getRate().getIntervalHour(), TimeUnit.HOURS);
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTraceWithCustomInfoToStr(e));
        }
    }

    public Boolean verify(String ip) {
        try {
            String key = RedisConstant.IpLimit + ip;
            Boolean hasKey = redisTemplate.hasKey(key);
            if (null == hasKey) return null;
            if (!hasKey) return true;
            Integer value = (Integer) redisTemplate.opsForValue().get(key);
            if (value.intValue() > config.getRate().getUriCount()) {
                return false;
            }

            return true;
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTraceWithCustomInfoToStr(e));
        }
        return null;
    }

}
