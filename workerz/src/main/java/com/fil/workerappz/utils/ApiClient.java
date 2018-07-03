package com.fil.workerappz.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class ApiClient {
  private static Retrofit retrofit = null;

  public static Retrofit getClient() {
    if (retrofit == null) {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

      Gson gson = new GsonBuilder()
              .setLenient()
              .create();

      OkHttpClient client = new OkHttpClient.Builder()
              .connectTimeout(5, TimeUnit.MINUTES)
              .readTimeout(5, TimeUnit.MINUTES)
              .build();

      httpClient.addInterceptor(logging);
      retrofit = new Retrofit.Builder()
              .baseUrl(Constants.IMAGE_URL)
              .addConverterFactory(GsonConverterFactory.create(gson))
              .client(client)
              .build();
    }
    return retrofit;
  }

  private static String bodyToString(final Request request) {
    try {
      final Request copy = request.newBuilder().build();
      final Buffer buffer = new Buffer();
      copy.body().writeTo(buffer);
      return buffer.readUtf8();
    } catch (final IOException e) {
      return "Did not work";
    }
  }

  private static class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      long t1 = System.nanoTime();
      String requestLog = String.format("Sending request %s on %s%n%s",
              request.url(), chain.connection(), request.headers());
      if (request.method().compareToIgnoreCase("post") == 0) {
        requestLog = "\n" + requestLog + "\n" + bodyToString(request);
      }
      CustomLog.d("TAG", "request" + "\n" + requestLog);
      Response response = chain.proceed(request);
      long t2 = System.nanoTime();
      String responseLog = String.format("Received response for %s in %.1fms%n%s",
              response.request().url(), (t2 - t1) / 1e6d, response.headers());
      String bodyString = response.body().string();
      CustomLog.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);
      return response.newBuilder()
              .body(ResponseBody.create(response.body().contentType(), bodyString))
              .build();
    }
  }
}