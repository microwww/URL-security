package com.github.microwww.security.cli.imp;

import com.github.microwww.security.cli.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpClientImpl implements HttpClient {
    private static final List<String> utf8 = Collections.singletonList("text/html; charset=utf-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public String get(String url, String... param) throws IOException {
        HttpURLConnection request = getRequest(url, param);
        return readString(request);
    }

    public static String readString(HttpURLConnection request) throws IOException {
        String ct = request.getHeaderFields().getOrDefault("content-type", utf8).get(0).toLowerCase();
        Charset charset = parseContentType(ct, UTF_8);

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));
        StringBuilder buf = new StringBuilder();
        char[] ch = new char[10240];
        while (true) {
            int len = reader.read(ch, 0, ch.length);
            if (len < 0) {
                break;
            }
            buf.append(ch, 0, len);
        }
        return buf.toString();
    }

    public HttpURLConnection getRequest(String url, String... param) throws IOException {
        StringBuilder buf = new StringBuilder(url);
        if (url.indexOf('?') >= 0 && !url.endsWith("&")) {
            buf.append("&");
        }
        join(buf, param);
        URL u = new URL(buf.toString());
        HttpURLConnection cnn = (HttpURLConnection) u.openConnection(); //login(account, password);
        cnn.setRequestMethod("GET");
        cnn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        cnn.setRequestProperty("connection", "Keep-Alive");
        cnn.connect();
        int code = cnn.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            return cnn;
        }
        throw new IOException("Request Not 200 !" + url);
    }

    // content-type: text/html; charset=utf-8
    private static Charset parseContentType(String contentType, Charset def) {
        // String contentType = cnn.getHeaderFields().getOrDefault("content-type", utf8).get(0).toLowerCase();
        if (contentType == null) {
            return def;
        }
        int p = contentType.indexOf("charset=");
        if (p >= 0) {
            int i = contentType.indexOf(";", p);
            if (i < 0) {
                i = contentType.length();
            }
            String charset = contentType.substring(p, i).split("=")[1];
            try {
                return Charset.forName(charset);
            } catch (UnsupportedCharsetException e) {
            }
        }
        return def;
    }

    @Override
    public String post(String url, String... param) throws IOException {
        return readString(requestPost(url, param));
    }

    @Override
    public String post(String url, Map<String, String> param) throws IOException {
        String[] arr = map2array(param);
        return this.post(url, arr);
    }

    public HttpURLConnection requestPost(String url, String... param) throws IOException {
        HttpURLConnection cnn = (HttpURLConnection) new URL(url).openConnection(); //login(account, password);
        cnn.setDoInput(true);
        cnn.setRequestMethod("POST");
        cnn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        cnn.setRequestProperty("connection", "Keep-Alive");
        cnn.connect();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cnn.getOutputStream(), UTF_8));
        String req = join(new StringBuilder(), param).toString();
        writer.write(req);
        writer.flush();
        int code = cnn.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            return cnn;
        }
        throw new IOException("Request Not 200 !" + url);
    }

    private static StringBuilder join(StringBuilder writer, String[] param) throws IOException {
        for (int i = 0; i < param.length; i++) {
            if (i != 0) {
                writer.append("&");
            }
            writer.append(URLEncoder.encode(param[i], "UTF-8"));
            i++;
            if (i < param.length) {
                writer.append("=").append(URLEncoder.encode(param[i], "UTF-8"));
            }
        }
        return writer;
    }

    private static String[] map2array(Map<String, String> param) {
        String[] val = new String[param.size() * 2];
        int i = 0;
        for (String key : param.keySet()) {
            val[i++] = key;
            val[i++] = param.get(key);
        }
        return val;
    }
}
