package com.sbcloud.common.utils;
//package com.sbcloud.common.utils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Map;
//
//import com.duoying.abs.utils.net.HttpClientUtil;
//import com.duoying.abs.utils.text.JsonUtil;
//
//public class AreaUtil {
//
//    /**
//     * 根据ip地址获取省份名称
//     * 
//     * @param ip
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public static String getAreaByIp(String ip) {
//        Map<String, Object> ipMap = JsonUtil
//                .json2Map(HttpClientUtil.get("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip));
//        if (ipMap.isEmpty() || !String.valueOf(ipMap.get("code")).equals("0") || null == ipMap.get("data")) {
//            return null;
//        }
//
//        String ipProvince = "";
//        try {
//            ipProvince = String.valueOf(((Map<String, Object>) ipMap.get("code")).get("region"));
//        } catch (Exception e) {
//            return null;
//        }
//        return ipProvince;
//    }
//
//    /**
//     * 根据手机号获取归属地省份名称
//     * 
//     * @param mobile
//     * @return
//     */
//    public static String getAreaByMobile(String mobile) {
//        String jsonString = null;
//        String urlString = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + mobile;
//        StringBuffer sb = new StringBuffer();
//        BufferedReader buffer;
//        URL url = null;
//        try {
//            url = new URL(urlString);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        InputStream in = null;
//        try {
//            in = url.openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        // 解决乱码问题
//        try {
//            buffer = new BufferedReader(new InputStreamReader(in, "gb2312"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
//        String line = null;
//        try {
//            while ((line = buffer.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        try {
//            in.close();
//            buffer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        jsonString = sb.toString();
//        if (jsonString.contains("province")) {
//            jsonString = jsonString.substring(jsonString.indexOf("province") + "province".length() + 2);
//            jsonString = jsonString.substring(0, jsonString.indexOf("'"));
//            return jsonString;
//        }
//
//        return null;
//    }
//
//}
