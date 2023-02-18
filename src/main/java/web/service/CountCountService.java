package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.common.response.Response;
import web.common.response.RspCode;
import web.redis.CountRedisHandler;
import web.redis.LimitRateRedisHandler;

@Service
public class CountCountService {

    @Autowired
    private CountRedisHandler countRedisHandler;

    @Autowired
    private LimitRateRedisHandler limitRateRedisHandler;

    public Response add(String uri, String ip) {
        Boolean verify = limitRateRedisHandler.verify(ip);
        if (null == verify) return Response.build(RspCode.UNKNOWN);
        if (!verify) {
            return Response.build(RspCode.GENERATE_TOO_MANY);
        }

        long l = countRedisHandler.add(uri, ip);
        return Response.build(RspCode.SUCCESS, l);
    }

    public Response count(String uri) {
        long count = countRedisHandler.count(uri);
        return Response.build(RspCode.SUCCESS, count);
    }

}
