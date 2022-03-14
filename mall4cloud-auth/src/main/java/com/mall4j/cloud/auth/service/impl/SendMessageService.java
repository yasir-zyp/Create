package com.mall4j.cloud.auth.service.impl;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service

public class SendMessageService {
    //普通短信审核通过
    public Map<String,Object> sendsms(String mobile,String name,String organizationName) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥
        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "33413"),
                new NameValuePair("templateId", "51650"),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", URLEncoder.encode(name+"##"+organizationName, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }
    //普通短信审核不通过
    public Map<String,Object> sendsErrorms(String mobile,String name,String organizationName) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "33413"),
                new NameValuePair("templateId", "53494"),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", URLEncoder.encode(name+"##"+organizationName, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }
    /*//提醒查看公告短信(群发)
    public Map<String,Object> sendsms2(List<User> users, String noticeName) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥
        //组装个性短信内容
        JSONObject jsonObj = new JSONObject();
        System.out.println("user列表"+users);
        for (User u:users
             ) {
            jsonObj.put(u.getPhone(),u.getUserName()+"##"+noticeName);
        }
        *//*jsonObj.put("13700000000","先生##9:40##快递公司##1234567");
        jsonObj.put("13700000001","女士##10:10##物流公司##000000");*//*//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        System.out.println("jsonObj"+jsonObj);
        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "33413"),
                new NameValuePair("templateId", "52242"),
                new NameValuePair("data", URLEncoder.encode(jsonObj.toString(), "utf-8"))
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");
        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }
    //普通短信需要提供templateId
    public Map<String,Object> sendsMessageOnly(String mobile,String name,String organizationName,String templateId,String sta) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥
        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "33413"),
                new NameValuePair("templateId", templateId),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", URLEncoder.encode(name+"##"+organizationName+"##"+sta, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }*/

    //普通短信企业删除
    public Map<String,Object> sendsDelErrorms(String mobile,String organizationName) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "33413"),
                new NameValuePair("templateId", "161610"),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", URLEncoder.encode(organizationName, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }
    public Map<String,Object> login(String mobile,String code) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        String phoneW=mobile.substring(7);

        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥
        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "145173"),
                new NameValuePair("templateId", "179400"),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", URLEncoder.encode(code+"##"+phoneW, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }


    public Map<String,Object> sendsDeclareMessage(String mobile,String organizationName,String time) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "145173"),
                new NameValuePair("templateId", "183912"),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", URLEncoder.encode(organizationName+"##"+time, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }


   /* //提醒查看开锁短信
    public Map<String,Object> openLock(User user, String noticeName) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        String accesskey = "JB4mPoGFASCPr4co"; //用户开发key
        String accessSecret = "BNSFZ1oTNo6iKSnRsrk7FXrntQkwPKyJ"; //用户开发秘钥
        //组装个性短信内容
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(user.getPhone(),user.getUserName()+"##"+noticeName);
        *//*jsonObj.put("13700000000","先生##9:40##快递公司##1234567");
        jsonObj.put("13700000001","女士##10:10##物流公司##000000");*//*//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        System.out.println("jsonObj"+jsonObj);
        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "33413"),
                new NameValuePair("templateId", "52242"),
                new NameValuePair("data", URLEncoder.encode(jsonObj.toString(), "utf-8"))
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");
        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        Map<String,Object> map=new HashMap<>();
        map.put("code",statusCode);
        return map;
    }*/
}
