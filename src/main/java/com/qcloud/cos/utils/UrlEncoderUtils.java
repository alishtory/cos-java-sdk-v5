package com.qcloud.cos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlEncoderUtils {

    private static final String PATH_DELIMITER = "/";
    private static final Logger log = LoggerFactory.getLogger(UrlEncoderUtils.class);

    public static String encode(String originUrl) {
        try {
            String encodeStr = URLEncoder.encode(originUrl, "UTF-8").replace("+", "%2b");
            String lowerCaseStr = lowerCaseTran(encodeStr);
            return lowerCaseStr;
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder error, encode utf8, exception: {}", e);
        }
        return null;
    }

    /**
     * URL转义字符转小写
     * @param str
     * @return
     */
    private static String lowerCaseTran(String str){
        if (!StringUtils.isNullOrEmpty(str)){
            char[] chars = str.toCharArray();
            for(int i = 0; i< chars.length-2; i++){
                if (chars[i] == '%'){
                    chars[i+1] = Character.toLowerCase(chars[i+1]);
                    chars[i+2] = Character.toLowerCase(chars[i+2]);
                }
            }
            str = new String(chars);
        }
        return str;
    }

    // encode路径, 不包括分隔符
    public static String encodeEscapeDelimiter(String urlPath) {
        StringBuilder pathBuilder = new StringBuilder();
        String[] pathSegmentsArr = urlPath.split(PATH_DELIMITER);

        for (String pathSegment : pathSegmentsArr) {
            if (!pathSegment.isEmpty()) {
                try {
                    pathBuilder.append(PATH_DELIMITER)
                            .append(URLEncoder.encode(pathSegment, "UTF-8").replace("+", "%20"));
                } catch (UnsupportedEncodingException e) {
                    log.error("URLEncoder error, encode utf8, exception: {}", e);
                }
            }
        }
        if (urlPath.endsWith(PATH_DELIMITER)) {
            pathBuilder.append(PATH_DELIMITER);
        }
        return pathBuilder.toString();
    }

}
