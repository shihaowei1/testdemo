package com.example.springsecurity.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.example.springsecurity.config.AlipayConfig;
import com.example.springsecurity.dao.FinishedOrderInfoRepository;
import com.example.springsecurity.dao.MyOrderRepository;
import com.example.springsecurity.entity.FinishedOrderInfo;
import com.example.springsecurity.entity.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;


@RestController
public class AlipayController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    MyOrderRepository myOrderRepository;

    @Autowired
    FinishedOrderInfoRepository finishedOrderInfoRepository;

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(@RequestParam("orderId") Long Id) throws AlipayApiException, UnsupportedEncodingException {

        Optional<MyOrder> myOrder = myOrderRepository.findById(Id);
        String OrderId = String.valueOf(Id);
        String Payment = String.valueOf(myOrder.get().getPrice());
        String OrderName = "CN" + String.valueOf(myOrder.get().getUser_id()) +
                "ON" + String.valueOf(myOrder.get().getId());

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        String out_trade_no = new String(OrderId.getBytes("ISO-8859-1"), "UTF-8");
        //付款金额，必填
        String total_amount = new String(Payment.getBytes("ISO-8859-1"), "UTF-8");
        //订单名称，必填
        String subject = new String(OrderName.getBytes("ISO-8859-1"), "UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //输出
        System.out.println(result);

        return result;
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public String refund(@RequestParam("refundId") String out_trade_no,
                         @RequestParam("refundReason") String refund_reason) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //从数据库中查询付款金额
        FinishedOrderInfo finishedOrderInfo = finishedOrderInfoRepository.findByOut_trade_no(out_trade_no);
        String refund_amount = finishedOrderInfo.getTotal_amount();

        String out_request_no = out_trade_no;//退款请求号，必填
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"refund_amount\":\"" + refund_amount + "\","
                + "\"refund_reason\":\"" + refund_reason + "\","
                + "\"out_request_no\":\"" + out_request_no + "\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        System.out.println(result);
        return result;
    }

    @RequestMapping(value = "/payInfo", method = RequestMethod.POST)
    public String payInfo(@RequestParam("orderId") String out_trade_no) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        System.out.println(result);
        return result;
    }

    @RequestMapping(value = "/return_url", method = RequestMethod.GET)
    public String Return_url() throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝返回过来的信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            //修改数据库中的订单信息
            Long Id = Long.valueOf(out_trade_no);
            Optional<MyOrder> myOrder = myOrderRepository.findById(Id);
            MyOrder order = new MyOrder();
            order.setId(myOrder.get().getId());
            order.setUser_id(myOrder.get().getUser_id());
            order.setPrice(myOrder.get().getPrice());
            order.setAmount(myOrder.get().getAmount());
            order.setResult("是");
            myOrderRepository.save(order);

            System.out.println("trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);

            //将完成交易的订单信息存到数据库
            FinishedOrderInfo finishedOrderInfo = new FinishedOrderInfo();
            finishedOrderInfo.setOut_trade_no(out_trade_no);
            finishedOrderInfo.setTrade_no(trade_no);
            finishedOrderInfo.setTotal_amount(total_amount);
            finishedOrderInfoRepository.save(finishedOrderInfo);

            return "支付宝交易号:" + trade_no + "<br/>商户订单号:" + out_trade_no + "<br/>总消费金额:" + total_amount;
        } else {
            System.out.println("验签失败");
            return "验签失败！";
        }
    }

//    @RequestMapping(value = "notify_url", method = RequestMethod.GET)
//    public String Notify_url() throws UnsupportedEncodingException, AlipayApiException {
//        Map<String,String> params = new HashMap<String,String>();
////        Map<String,String[]> requestParams = request.getParameterMap();
////        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
////            String name = (String) iter.next();
////            String[] values = (String[]) requestParams.get(name);
////            String valueStr = "";
////            for (int i = 0; i < values.length; i++) {
////                valueStr = (i == values.length - 1) ? valueStr + values[i]
////                        : valueStr + values[i] + ",";
////            }
////            //乱码解决，这段代码在出现乱码时使用
////            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
////            params.put(name, valueStr);
////        }
//
//        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
//
//        //——请在这里编写您的程序（以下代码仅作参考）——
//
//	/* 实际验证过程建议商户务必添加以下校验：
//	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
//	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
//	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
//	4、验证app_id是否为该商户本身。
//	*/
//        if(signVerified) {//验证成功
//            //商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//
//            //支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//
//            //交易状态
//            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
//
//            if(trade_status.equals("TRADE_FINISHED")){
//                return "TRADE_FINISHED";
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//            }else if (trade_status.equals("TRADE_SUCCESS")){
//                return "TRADE_SUCCESS";
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //付款完成后，支付宝系统发送该交易状态通知
//            }
//
//            System.out.println("success");
//            return "success";
//        }else {//验证失败
//            System.out.println("fail");
//
//            return "fail";
//            //调试用，写文本函数记录程序运行情况是否正常
//            //String sWord = AlipaySignature.getSignCheckContentV1(params);
//            //AlipayConfig.logResult(sWord);
//        }
//    }
}
