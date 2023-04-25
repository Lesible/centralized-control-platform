package com.shenhao.controller;

import com.shenhao.resp.BaseResponse;
import com.shenhao.resp.ResponseBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 何嘉豪
 */
@RestController
public class AuthController {

    @RequestMapping("test")
    public BaseResponse<?> test() {
        return ResponseBuilder.success("test");
    }
}
