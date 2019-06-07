package com.example.apple.haidilao;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.*;


public class ClientSever {

    JsonObject user;
    private final OkHttpClient client = new OkHttpClient();
    String level="";
    ClientSeverJson clientSeverJson;
    ClientSever(JsonObject user){
        this.user = user;
    }


    public void dealwith(final Callback onCallback)  {
        String username = user.getUserName();
        String password = user.getPassword();
        RequestBody formBody = new FormBody.Builder().add("username",username).add("password",password).build();
        final Request request = new Request.Builder().url(HttpParams.URL + ":11111/login").post(formBody).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onCallback != null) {
                    onCallback.onFail(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        level = response.body().string();
                        Gson gson = new Gson();
                        clientSeverJson = gson.fromJson(level,ClientSeverJson.class);
                        if (onCallback != null) {
                            onCallback.onCallback(clientSeverJson.getLevel());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (onCallback != null) {
                            onCallback.onFail(e.getMessage());
                        }
                    }
                }
            }
        });
    }


}
