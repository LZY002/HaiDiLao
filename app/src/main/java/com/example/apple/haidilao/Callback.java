package com.example.apple.haidilao;

public interface Callback {
    void onCallback(String json);

    void onFail(String error);
}
