package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.common.response.Response;
import web.common.response.RspCode;
import web.redis.OnlineRedisHandler;

@Service
public class OnlineService {

    @Autowired
    private OnlineRedisHandler onlineRedisHandler;

    public Response heartbeat(String uri, String ip) {
        onlineRedisHandler.heartbeat(uri, ip);
        return count(uri);
    }

    public Response count(String uri) {
        long count = onlineRedisHandler.count(uri);
        return Response.build(RspCode.SUCCESS, count);
    }


}
