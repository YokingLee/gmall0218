package com.atguigu.gmall0218.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0218.bean.UserAddress;
import com.atguigu.gmall0218.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Controller
public class OrderController {

//    @Autowired
    @Reference
    private UserService userService;

//    @RequestMapping("trade")
//    public String trade(){
//        //返回一个视图 页面thymleaf
//        return "index";
//    }

    @RequestMapping("trade")
    @ResponseBody  //返回json字符串 ，fastJson.jar  直接将数据显示到页面
    public List<UserAddress> trade(String userId){
        //返回一个视图 页面thymleaf
        System.out.println(userId+"------------------------------------");
        return userService.getUserAddressList(userId);
    }

}
