package com.example.springsecurity.controller;

import com.aliyun.oss.OSSClient;
import com.example.springsecurity.dao.GoodsRepository;
import com.example.springsecurity.entity.Goods;
import com.example.springsecurity.service.GoodsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
public class GoodsController {

    @Resource
    GoodsService goodsService;

    @Autowired
    HttpServletRequest request;


    @RequestMapping(value = "/allGoodsInfo", method = RequestMethod.POST)
    public List<Goods> allGoodsInfo() {
        return goodsService.allGoodsInfo();
    }

    @RequestMapping(value = "/addNewGoods", method = RequestMethod.POST)
    public String addNewGoods(@RequestParam("cata") String cata,
                              @RequestParam("goodsName") String goodsName,
                              @RequestParam("price") Long price,
                              @RequestParam("saled") Long saled,
                              @RequestParam("remain") Long remain,
                              @RequestParam("goods_info") String goodsInfo) {

        if (cata.length() <= 0 || goodsName.length() <= 0 || price == null ||
                saled == null || remain == null || goodsInfo.length() <= 0) {
            return "false";
        }
        return goodsService.addNewGoods(cata, goodsName, price, saled, remain, goodsInfo);
    }

    @RequestMapping(value = "/showInfo", method = RequestMethod.POST)
    public String showInfo(@RequestParam("goodsId") Long goodsId) {
        return goodsService.showInfo(goodsId);
    }

    @RequestMapping(value = "/delGoods", method = RequestMethod.POST)
    public String delGoods(@RequestParam("delGoodsId") Long delGoodsId) {
        goodsService.delGoods(delGoodsId);
        return "true";
    }

    @RequestMapping(value = "/changeGoodsInfo", method = RequestMethod.POST)
    public String changeGoodsInfo(@RequestParam("goodsId") Long goodsId,
                                  @RequestParam("cata") String cata,
                                  @RequestParam("goodsName") String goodsName,
                                  @RequestParam("price") Long price,
                                  @RequestParam("saled") Long saled,
                                  @RequestParam("remain") Long remain,
                                  @RequestParam("goods_info") String goodsInfo) {
        return goodsService.changeGoodsInfo(goodsId, cata, goodsName, price, saled, remain, goodsInfo);
    }

    @RequestMapping(value = "/saveImg", method = RequestMethod.POST)
    public JSONObject uploadImgToOSS(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Part part = null;
        try {
            part = request.getPart("myFileName");// myFileName是文件域的name属性值
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        // 包含原始文件名的字符串
        String fi = part.getHeader("content-disposition");
        // 提取文件拓展名
        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length() - 1);
        // 生成实际存储的真实文件名
        String realName = UUID.randomUUID().toString() + fileNameExtension;
        // 图片存放的真实路径
        String realPath = "http://salesdemo.oss-cn-shanghai.aliyuncs.com/" + realName;
        // 将文件写入指定路径下

//        System.out.println("part: " + part + "\n" + "fi: " + fi + "\n" + "realName: " + realName);

        String endpoint = null;
        endpoint = "http://oss-cn-shanghai.aliyuncs.com";

        String accessId = null;
        accessId = "LTAIouJrku6vfpqB";

        String accessKey = null;
        accessKey = "1OFd95tn3HvappdYITB5zJZyAFMHhg";

        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        String dir = "";
        String ossUrl = "";
        try {
            InputStream inputStream = part.getInputStream();
            ossUrl = dir + realName;
            client.putObject("salesdemo", ossUrl, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.shutdown();
        // 返回图片的URL地址
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(realPath);
        jsonObject.put("errno", 0);
        jsonObject.put("data", jsonArray);

//        System.out.println("jsonArray: " + jsonArray);
//        System.out.println("jsonObject: " + jsonObject);
        return jsonObject;
    }
}
