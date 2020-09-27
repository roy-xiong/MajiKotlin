package com.roy.kotlin.test.http;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 日志打印拦截器
 * Created by yu on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {

    private boolean printLog;

    public LoggerInterceptor(boolean printLog) {
        this.printLog = printLog;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);

    }

    private Response logForResponse(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();

            HttpUrl url = clone.request().url();
//            String tag = ">" + url.pathSegments().get(url.pathSize() - 1);

            Log.e("###", "╔════════════response start══════════════════════════════════════════");
            Log.e("###", "║ " + clone.code() + "  " + clone.message() + "  " + url);

            if (printLog) {
                ResponseBody body = clone.body();
                Headers headers = clone.headers();
                if (headers != null && headers.size() > 0) {
                    for (int i = 0; i < headers.size(); i++) {
                        Log.e("##" + i, "║ " + headers.name(i) + ": " + headers.value(i));
                    }
                }
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Log.e("###", "║ contentType: " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Log.e("###", "║ content: " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            Log.e("###", "╚════════════response end════════════════════════════════════════════");
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.e("###", "║ responseBody's content : maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            Log.e("###", "╚════════════response end════════════════════════════════════════════");
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            HttpUrl url = request.url();
//            String tag = ">" + url.pathSegments().get(url.pathSize() - 1);

            Log.e("###", "╔════════════request start══════════════════════════════════════════");
            Log.e("###", "║ " + request.method() + ' ' + url);

            if (printLog) {
                Headers headers = request.headers();
                if (headers != null && headers.size() > 0) {
                    for (int i = 0; i < headers.size(); i++) {
                        Log.e("##" + i, "║ " + headers.name(i) + ": " + headers.value(i));
                    }
//                Log.e("###", "║ headers : " + headers.toString().replace("\n", " ║ "));
                }
                RequestBody requestBody = request.body();
                if (requestBody != null) {
                    MediaType mediaType = requestBody.contentType();
                    if (mediaType != null) {
                        Log.e("###", "║ contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            Log.e("###", "║ content : " + bodyToString(request));
                        } else {
                            Log.e("###", "║ content : maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
            Log.e("###", "╚════════════request end════════════════════════════════════════════");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("hal+json") ||
                    mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
            )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
