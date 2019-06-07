package com.example.apple.haidilao;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.Callback;

import java.io.*;


public class TableSever {

    final int SMALLTABLE_WAITETIME=5;
    final int MIDDLETABLE_WAITETIME=4;
    final int BIGTABLE_WAITETIME=6;
    private final OkHttpClient client = new OkHttpClient();
    public void dealwith(final Activity activity, final TextView smalltable, final TextView middletable, final TextView bigtable, final TextView smalltabletime, final TextView middletabletime, final TextView bigtabletime) throws Exception {

        final Request request = new Request.Builder().url(HttpParams.URL + ":11111/table").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    final TableSeverJson tableSeverJson = gson.fromJson(json, TableSeverJson.class);
                    if (tableSeverJson != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TableInfo table = tableSeverJson.getTable();
                                int smalltables = table.getSmall();
                                int middletables = table.getMiddle();
                                int bigtables = table.getBig();
                                smalltable.setText(String.valueOf(smalltables));
                                middletable.setText(String.valueOf(middletables));
                                bigtable.setText(String.valueOf(bigtables));
                                smalltabletime.setText(String.valueOf(SMALLTABLE_WAITETIME * smalltables));
                                middletabletime.setText(String.valueOf(MIDDLETABLE_WAITETIME * middletables));
                                bigtabletime.setText(String.valueOf(BIGTABLE_WAITETIME * bigtables));
                            }
                        });
                    }
                }
            }
        });
    }



}
