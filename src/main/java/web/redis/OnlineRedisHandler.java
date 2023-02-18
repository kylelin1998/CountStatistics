package web.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import web.common.constant.RedisConstant;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class OnlineRedisHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static String KeyFormat = RedisConstant.Heartbeat + "%s:%s";

    public void heartbeat(String uri, String ip) {
        redisTemplate.opsForValue().set(String.format(KeyFormat, uri, ip), uri, 3, TimeUnit.MINUTES);
    }

    public long count(String uri) {
        Set<String> keys = redisTemplate.keys(RedisConstant.Heartbeat + uri + ":*");
        if (null == keys) {
            return 0L;
        } else {
            return keys.size();
        }
    }

}
