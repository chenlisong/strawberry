package com.example.demo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class HttpTinyClient {

    static final String DEFAULT_CHARSET = "UTF-8";

    static public HttpResult httpGet(String url, List<String> headers, List<Object> paramValues,
                                     long readTimeoutMs) throws IOException {
        String encodedContent = encodingParams(paramValues, DEFAULT_CHARSET);
        url += (null == encodedContent) ? "" : ("?" + encodedContent);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout((int) readTimeoutMs);
            conn.setReadTimeout((int) readTimeoutMs);
            setHeaders(conn, headers, DEFAULT_CHARSET);

            conn.connect();
            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                resp = IOTinyUtils.toString(conn.getInputStream(), DEFAULT_CHARSET);
            } else {
                resp = IOTinyUtils.toString(conn.getErrorStream(), DEFAULT_CHARSET);
            }
            return new HttpResult(respCode, resp);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return new HttpResult(400, "{}");
    }

    static private String encodingParams(List<Object> paramValues, String encoding)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if (null == paramValues) {
            return null;
        }

        for (Iterator<Object> iter = paramValues.iterator(); iter.hasNext(); ) {
            Object prefix = iter.next();
            Object after = iter.next();

            sb.append(prefix).append("=");
            if(prefix instanceof String && after instanceof String){
                sb.append(URLEncoder.encode(after.toString(), encoding));
            }else {
                sb.append(after);
            }
            if (iter.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    static private void setHeaders(HttpURLConnection conn, List<String> headers, String encoding) {
        if (null != headers) {
            for (Iterator<String> iter = headers.iterator(); iter.hasNext(); ) {
                conn.addRequestProperty(iter.next(), iter.next());
            }
        }
        //conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);

        //String ts = String.valueOf(System.currentTimeMillis());
        //conn.addRequestProperty("Metaq-Client-RequestTS", ts);
    }

    /**
     * @return the http response of given http post request
     */
    static public HttpResult httpPost(String url, List<String> headers, String encodedContent,
                                      long readTimeoutMs) throws IOException {
//        String encodedContent = encodingParams(paramValues, encoding);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout((int) readTimeoutMs);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            setHeaders(conn, headers, DEFAULT_CHARSET);

            conn.getOutputStream().write(encodedContent.getBytes(DEFAULT_CHARSET));

            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                resp = IOTinyUtils.toString(conn.getInputStream(), DEFAULT_CHARSET);
            } else {
                resp = IOTinyUtils.toString(conn.getErrorStream(), DEFAULT_CHARSET);
            }
            return new HttpResult(respCode, resp);
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
    }

    static public class HttpResult {
        final public int code;
        final public String content;

        public HttpResult(int code, String content) {
            this.code = code;
            this.content = content;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(1 << 1);
        System.out.println(URLEncoder.encode("http://workweixin.e-lppz.com/2.html", "utf-8"));

        System.out.println(URLEncoder.encode("ww6b95830ca31329e4", "utf-8"));

        System.out.println(URLEncoder.encode("HkMh4_lKBtz3lHjrr1UBhO7fZGjfxUwEPqlH836csck", "utf-8"));
    }
}