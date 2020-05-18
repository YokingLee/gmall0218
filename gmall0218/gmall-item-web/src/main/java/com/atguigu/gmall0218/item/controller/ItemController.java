package com.atguigu.gmall0218.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall0218.bean.SkuInfo;
import com.atguigu.gmall0218.bean.SkuSaleAttrValue;
import com.atguigu.gmall0218.bean.SpuSaleAttr;
import com.atguigu.gmall0218.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class ItemController {

    @Reference
    private ManageService manageService;

    // localhost:8084/33.html
    // 控制器
    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, HttpServletRequest request){
        // 根据skuId 获取数据
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);
        // 显示图片列表！
        // 根据skuId skuImage 中
//        List<SkuImage> skuImageList = manageService.getSkuImageBySkuId(skuId);
//        // 将图片集合保存到作用域中
//        request.setAttribute("skuImageList",skuImageList);

        // 查询销售属性，销售属性值集合 spuId，skuId
        List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrListCheckBySku(skuInfo);

        // 获取销售属性值Id
        List<SkuSaleAttrValue> skuSaleAttrValueList = manageService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());
        // 遍历集合拼接字符串 {"118|120":"33" ,"119|122":"34","118|122":"36"}
        // 增强for 循环
        // 将数据放入map 中，然后将map 转换为想要的json 格式！
        // key=118|120
        // map.put("118|120"，"33") JSON.toJSONString(map);
        // map.put(key，skuId) JSON.toJSONString(map);

        //        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
        //            // 什么时候拼接，
        //            // 什么时候停止拼接 当本次循环的skuId 与 下次循环的skuId 不一致的时候！停止拼接。拼接到最后则停止拼接！？
        //        }

        String key = "";
        HashMap<String, Object> map = new HashMap<>();
        // 普通循环

        for (int i = 0; i < skuSaleAttrValueList.size(); i++) {
            SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueList.get(i);
            // 什么时候停止拼接 当本次循环的skuId 与 下次循环的skuId 不一致的时候！停止拼接。拼接到最后则停止拼接！？
            // 什么时候加|

            // 第一次拼接 key=118
            // 第二次拼接 key=118|
            // 第三次拼接 key=118|120 放入map 中 ，并清空key
            // 第四次拼接 key=119
            if (key.length()>0){
                key+="|";
            }
            key+= skuSaleAttrValue.getSaleAttrValueId();
            if ((i+1)== skuSaleAttrValueList.size() || !skuSaleAttrValue.getSkuId().equals( skuSaleAttrValueList.get(i+1).getSkuId())){
                // 放入map集合
                map.put(key,skuSaleAttrValue.getSkuId());
                // 并且清空key
                key="";
            }
        }
        // 将map 转换为json 字符串
        String valuesSkuJson  = JSON.toJSONString(map);
        System.out.println("拼接Json：="+valuesSkuJson );

        // 保存json
        request.setAttribute("valuesSkuJson",valuesSkuJson);


        request.setAttribute("spuSaleAttrList",spuSaleAttrList);
        // 保存到作用域
        request.setAttribute("skuInfo",skuInfo);
        return "item";
    }


}
