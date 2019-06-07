package com.example.apple.haidilao;

import android.util.Log;
import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.Callback;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;


public class TableChosenSever {

    private String tabletype ;
    private  String level;
    private  String username;
    OkHttpClient client = new OkHttpClient();
    TableChosenSever(String tabletype,String level,String username){
        this.tabletype = tabletype;
        this.level = level;
        this.username = username;
    }

    void dealwith(final com.example.apple.haidilao.Callback callback) {
        RequestBody formBody = new FormBody.Builder().add("tabletype",tabletype).add("level",level).add("username",username).build();
        final Request request = new Request.Builder().url(HttpParams.URL + ":11111/getnumber").post(formBody).build();

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
                    String json = response.body().string();
                    if (callback != null) {
                        callback.onCallback(json);
                    }
                }
            }
        });
    }
}
