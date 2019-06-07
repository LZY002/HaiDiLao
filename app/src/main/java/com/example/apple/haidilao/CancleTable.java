package com.example.apple.haidilao;

import android.util.Log;
import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.Callback;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;


public class CancleTable {
    OkHttpClient client = new OkHttpClient();


   private String tabletype;
   String name;
    CancleTable(String tabletype ,String name){
        this.tabletype = tabletype;
        this.name = name;

    }
    void dealwith(final com.example.apple.haidilao.Callback callback) {
        final Request request = new Request.Builder().url(HttpParams.URL + ":11111/cancle?type=" + tabletype+"&name="+name).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFail(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    if (callback != null) {
                        callback.onCallback(response.body().string());
                    }
                }
            }
        });
    }
}
