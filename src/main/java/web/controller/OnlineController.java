package web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.common.response.Response;
import web.common.response.RspCode;
import web.common.util.IpUtil;
import web.service.OnlineService;

import javax.servlet.http.HttpServletRequest;

@Validated
@Controller
@RequestMapping(value = "/api/online", produces = "application/json")
public class OnlineController {

    @Autowired
    private OnlineService onlineService;

    @CrossOrigin
    @GetMapping("/{uri}/heartbeat")
    @ResponseBody
    public Response heartbeat(@PathVariable("uri") String uri, HttpServletRequest req) {
        if (StringUtils.isBlank(uri)) {
            return Response.build(RspCode.PARAMETER_ERROR);
        } else if (uri.length() > 15) {
            return Response.build(RspCode.PARAMETER_ERROR);
        } else if (!StringUtils.isAlpha(uri)) {
            return Response.build(RspCode.PARAMETER_ERROR);
        }

        String ip = IpUtil.getIpAddress(req);
        return onlineService.heartbeat(uri, ip);
    }

    @CrossOrigin
    @GetMapping("/{uri}/count")
    @ResponseBody
    public Response count(@PathVariable("uri") String uri) {
        if (StringUtils.isBlank(uri)) {
            return Response.build(RspCode.PARAMETER_ERROR);
        } else if (uri.length() > 15) {
            return Response.build(RspCode.PARAMETER_ERROR);
        } else if (!StringUtils.isAlpha(uri)) {
            return Response.build(RspCode.PARAMETER_ERROR);
        }
        return onlineService.count(uri);
    }

}
