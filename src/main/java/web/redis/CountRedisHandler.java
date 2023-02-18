package web.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import web.common.constant.RedisConstant;
import web.config.AppConfig;

import java.util.concurrent.TimeUnit;

@Component
public class CountRedisHandler {

    @Autowired
    private AppConfig config;

    @Autowired
    private LimitRateRedisHandler limitRateRedisHandler;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static String KeyFormat = RedisConstant.Count + "%s";

    public long add(String uri, String ip) {
        String key = String.format(KeyFormat, uri);
        Boolean hasKey = redisTemplate.hasKey(key);
        if (null == hasKey || !hasKey) {
            limitRateRedisHandler.add(ip);
        }

        Long i = redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expire(key, config.getUriExpireDay(), TimeUnit.DAYS);
        if (null == i) return 0L;
        return i;
    }

    public long count(String uri) {
        String key = String.format(KeyFormat, uri);
        Long i = (Long) redisTemplate.opsForValue().get(key);
        if (null == i) return 0L;
        return i;
    }

}
