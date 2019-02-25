package com.fhss.shop.base;

import android.support.annotation.NonNull;

import com.zrq.base.util.LogUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 描述:
 *
 * @author zhangrq
 * 2018/3/28 16:42
 */

public class HttpLogInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        // 打印请求地址
        LogUtil.i("数据请求地址", request.url().toString());
        // 打印请求头
        Headers headers = request.headers();
        StringBuilder rawStringBuilder = new StringBuilder();
        StringBuilder decryptStringBuilder = new StringBuilder();
        for (int i = 0; i < headers.size(); i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            rawStringBuilder.append("（").append(name).append(" = ").append(value).append("）");// 原始的
//            value = AESCipher.aesDecryptString(value);// 解码
            decryptStringBuilder.append("（").append(name).append(" = ").append(value).append("）");// 解密的
        }
        LogUtil.i("数据请求头-请求的", rawStringBuilder.toString());
        LogUtil.i("数据请求头-解密后", decryptStringBuilder.toString());
        // 打印请求参数
        RequestBody requestBody = request.body();
        if (requestBody != null && requestBody.contentLength() != 0) {
            Buffer requestBuffer = new Buffer();
            requestBody.writeTo(requestBuffer);
            String requestParamsStr = requestBuffer.readString(Charset.forName("UTF-8"));
//            requestParamsStr = URLDecoder.decode(requestParamsStr);// 参数已经转码，解码
            LogUtil.i("数据请求参数-请求的", requestParamsStr);
            if (requestParamsStr.startsWith("data=")) {
                String substring = requestParamsStr.substring("data=".length());
//                LogUtil.i("数据请求参数-解密后", AESCipher.aesDecryptString(substring));
            }
        }
        // 打印返回数据
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (responseBody != null && responseBody.contentLength() != 0) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            LogUtil.i("数据返回", buffer.clone().readString(Charset.forName("UTF-8")));
        }
        return response;
    }
}
