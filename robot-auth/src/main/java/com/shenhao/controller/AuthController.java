package com.shenhao.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Consts;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.shenhao.exception.AuthException;
import com.shenhao.resp.BaseResponse;
import com.shenhao.resp.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * @author 何嘉豪
 */
@Slf4j
@RestController
public class AuthController {

    @RequestMapping("test")
    public BaseResponse<?> test() {
        return ResponseBuilder.success("test");
    }

    // 处理所有OAuth相关请求
    @RequestMapping("/oauth/token")
    public Object request() {
        SaRequest request = SaHolder.getRequest();
        // 根据 basic 验证判断 clientId clientSecret
        String authorization = request.getHeader("Authorization");
        Base64.Decoder decoder = Base64.getDecoder();
        if (StringUtils.isBlank(authorization) || !authorization.toLowerCase().startsWith("basic ")) {
            throw new AuthException(String.format("error http basic authorization:%s", authorization));
        }
        try {
            byte[] decoded = decoder.decode(authorization.substring(authorization.indexOf(" ") + 1));
            String pair = new String(decoded, StandardCharsets.UTF_8);
            String[] split = pair.split(":");
            if (split.length != 2) {
                throw new AuthException(String.format("illegal basic authorization:%s", pair));
            }
            Object source = request.getSource();
            HttpServletRequest hsr = (HttpServletRequest) source;
            Map<String, String[]> parameterMap = hsr.getParameterMap();
            parameterMap.put(SaOAuth2Consts.Param.client_id, new String[]{split[0]});
            parameterMap.put(SaOAuth2Consts.Param.client_secret, new String[]{split[1]});
        } catch (Exception e) {
            throw new AuthException(String.format("error http basic authorization:%s", authorization), e);
        }
        log.info("------- 进入请求:{}", request.getUrl());
        SaOAuth2Handle.serverRequest();
        return "";
    }

    // Sa-OAuth2 定制化配置
    @Autowired
    public void setSaOAuth2Config(SaOAuth2Config cfg) {
        cfg.
                // 配置：未登录时返回的View
                        setNotLoginView(() -> {
                    String msg = "当前会话在OAuth-Server端尚未登录，请先访问"
                            + "<a href='/oauth2/doLogin?name=sa&pwd=123456' target='_blank'> doLogin登录 </a>"
                            + "进行登录之后，刷新页面开始授权";
                    return msg;
                }).
                // 配置：登录处理函数
                        setDoLoginHandle((name, pwd) -> {
                    if ("sa".equals(name) && "123456".equals(pwd)) {
                        StpUtil.login(10001);
                        return SaResult.ok();
                    }
                    return SaResult.error("账号名或密码错误");
                }).
                // 配置：确认授权时返回的View
                        setConfirmView((clientId, scope) -> {
                    String msg = "<p>应用 " + clientId + " 请求授权：" + scope + "</p>"
                            + "<p>请确认：<a href='/oauth2/doConfirm?client_id=" + clientId + "&scope=" + scope + "' target='_blank'> 确认授权 </a></p>"
                            + "<p>确认之后刷新页面</p>";
                    return msg;
                })
        ;
    }

    // 全局异常拦截
    @ExceptionHandler
    public BaseResponse<?> handlerException(Exception e) {
        SaRequest request = SaHolder.getRequest();
        log.error("request failed,url:{},msg:{}", request.getUrl(), e.getMessage(), e);
        return ResponseBuilder.failed(e.getMessage());
    }

}
