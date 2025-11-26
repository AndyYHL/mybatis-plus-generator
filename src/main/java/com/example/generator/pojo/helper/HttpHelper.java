package com.example.generator.pojo.helper;

import com.example.generator.util.IOUtil;
import com.example.generator.util.SslUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * okhttp
 *
 * @author Administrator-YHL
 */
@Slf4j
public class HttpHelper {

    public static final int DEFAULT_TIMEOUT = 30;
    public static final int GET = 0;
    public static final int POST = 1;

    private int method = 0;
    private String url;

    private int timeout = DEFAULT_TIMEOUT;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private List<HttpMultipart> multipart = new ArrayList<>();
    private String body;

    private byte[] certFile;
    private String certKey;
    private String authUsername;
    private String authPassword;
    private String proxyIp;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;

    private HttpResponse response;
    private OutputStream outputStream;

    public HttpHelper(String url, int method) {
        this.url = url.trim();
        this.method = method;
    }

    public static HttpHelper createGet(String url) {
        return new HttpHelper(url, GET);
    }

    public static HttpHelper createPost(String url) {
        return new HttpHelper(url, POST);
    }

    public HttpHelper addHeader(String key, String value) {
        headers.put(key, value.trim());
        return this;
    }

    public HttpHelper addHeader(Map<String, String> header) {
        this.headers.putAll(header);
        return this;
    }

    public HttpHelper addParam(String key, String value) {
        params.put(key, value.trim());
        return this;
    }

    public HttpHelper addParam(Map<String, String> param) {
        this.params.putAll(param);
        return this;
    }

    public HttpHelper addMultipart(String key, String filename, byte[] file) {
        multipart.add(new HttpMultipart(key, filename, file));
        return this;
    }

    public HttpHelper setCertFile(byte[] certFile, String certKey) {
        this.certFile = certFile;
        this.certKey = certKey;
        return this;
    }

    public HttpHelper setBody(String body) {
        this.body = body;
        return this;
    }

    public HttpHelper setTimeout(int timeout) {
        if (timeout >= 1000 && timeout <= 600000) {
            this.timeout = timeout;
        }
        return this;
    }

    public HttpHelper setAuth(String username, String password) {
        this.authUsername = username;
        this.authPassword = password;
        return this;
    }

    public HttpHelper setProxy(String proxyIp, int proxyPort) {
        this.proxyIp = proxyIp;
        this.proxyPort = proxyPort;
        return this;
    }

    public HttpHelper setProxyAuth(String proxyUsername, String proxyPassword) {
        this.proxyUsername = proxyUsername;
        this.proxyPassword = proxyPassword;
        return this;
    }

    public HttpHelper setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public static boolean checkHttpUrl(String url) {
        url = url.trim();
        return !StringUtils.isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://"));
    }

    public HttpResponse execute() {
        response = new HttpResponse();
        try {
            headers.remove(null);
            params.remove(null);
            Request.Builder reqBuilder = new Request.Builder();
            reqBuilder.url(url);
            if (method == GET) {
                reqBuilder.get();
            }
            if (method == POST) {
                RequestBody reqBody = null;
                if (body != null) {
                    reqBody = RequestBody.create(body, MediaType.parse("application/json"));
                }
                if (!multipart.isEmpty()) {
                    MultipartBody.Builder fileBuilder = new MultipartBody.Builder();
                    fileBuilder.setType(MultipartBody.FORM);
                    params.forEach(fileBuilder::addFormDataPart);
                    multipart.forEach(part -> {
                        RequestBody fileBody = RequestBody.create(part.getFile(), MediaType.parse("multipart/form-data"));
                        fileBuilder.addFormDataPart(part.getKey(), part.getFilename(), fileBody);
                    });
                    reqBody = fileBuilder.build();
                }
                if (multipart.isEmpty() && !params.isEmpty()) {
                    FormBody.Builder formBuilder = new FormBody.Builder();
                    params.forEach(formBuilder::add);
                    reqBody = formBuilder.build();
                }
                assert reqBody != null;
                reqBuilder.post(reqBody);
            }
            headers.forEach(reqBuilder::addHeader);
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
            clientBuilder.readTimeout(timeout, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
            if (certFile != null) {
                clientBuilder.sslSocketFactory(Objects.requireNonNull(SslUtil.createSSLSocketFactory(certFile, certKey)), SslUtil.createTrustManager());
            } else {
                clientBuilder.sslSocketFactory(Objects.requireNonNull(SslUtil.createSSLSocketFactory()), SslUtil.createTrustManager());
            }
            clientBuilder.setHostnameVerifier$okhttp(SslUtil.createHostnameVerifier());
            if (proxyIp != null) {
                clientBuilder.setProxy$okhttp(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort)));
                if (proxyUsername != null) {
                    reqBuilder.header("Proxy-Authorization", Credentials.basic(proxyUsername, proxyPassword));
                }
            }
            if (authUsername != null) {
                reqBuilder.header("Authorization", Credentials.basic(authUsername, authPassword));
            }
            clientBuilder.setRetryOnConnectionFailure$okhttp(false);
            OkHttpClient client = clientBuilder.build();
            Response resp = client.newCall(reqBuilder.build()).execute();
            if (outputStream != null) {
                assert resp.body() != null;
                IOUtil.write(resp.body().byteStream(), outputStream);
                return response;
            }
            response.setCode(resp.code());
            if (resp.code() != 200) {
                log.error("response.code:" + response.getCode());
                return response;
            }
            response.setHeaders(resp.headers());
            assert resp.body() != null;
            response.setBody(resp.body().string());
            return response;
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            log.error("[" + url + "]error:", e);
        }
        return response;
    }

    @Getter
    @Setter
    public static class HttpResponse {
        private int code;
        private String msg;
        private Headers headers;
        private String body;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class HttpMultipart {
        private String key;
        private String filename;
        private byte[] file;
    }

    public static void main(String[] args) {
        /*HttpHelper
                .createPost(url)
                .addMultipart("file", fileName, bytes)
                .addParam(formMap)
                .execute().getBody();*/
    }
}
