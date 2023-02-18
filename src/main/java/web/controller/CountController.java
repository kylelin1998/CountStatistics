package web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.common.response.Response;
import web.common.response.RspCode;
import web.common.util.IpUtil;
import web.service.CountCountService;

import javax.servlet.http.HttpServletRequest;

@Validated
@Controller
@RequestMapping(value = "/api/count", produces = "application/json")
public class CountController {

    @Autowired
    private CountCountService countCountService;

    @CrossOrigin
    @GetMapping("/{uri}/add")
    @ResponseBody
    public Response add(@PathVariable("uri") String uri, HttpServletRequest req) {
        if (StringUtils.isBlank(uri)) {
            return Response.build(RspCode.PARAMETER_ERROR);
        } else if (uri.length() > 15) {
            return Response.build(RspCode.PARAMETER_ERROR);
        } else if (!StringUtils.isAlpha(uri)) {
            return Response.build(RspCode.PARAMETER_ERROR);
        }

        String ip = IpUtil.getIpAddress(req);
        return countCountService.add(uri, ip);
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
        return countCountService.count(uri);
    }

}
