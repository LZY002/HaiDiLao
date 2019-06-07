package com.example.apple.haidilao;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
   private TextView tv_sayhello;
   private Button bt_backToLogin;
   private Button bt_takeTheQueue;
   private RadioButton smalltable;
   private  RadioButton middletable;
   private  RadioButton bigtable;
   private TextView tv_smalltableNumber;
    private TextView tv_middleltableNumber;
    private TextView tv_bigtableNumber;
    private TextView tv_smalltableTime;
    private TextView tv_middletableTime;
    private TextView tv_bigtableTime;
    String level;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        OnClick onClick = new OnClick();
        bt_takeTheQueue=findViewById(R.id.bt_takeTheNumber);
        bt_backToLogin=findViewById(R.id.bt_backtologin);
        tv_smalltableNumber = findViewById(R.id.tv_smallTableNumber);
        tv_middleltableNumber = findViewById(R.id.tv_middleTableNumber);
        tv_bigtableNumber = findViewById(R.id.tv_bigTableNumber);
        tv_smalltableTime = findViewById(R.id.tv_smallTableTime);
        tv_middletableTime = findViewById(R.id.tv_middleTableTime);
        tv_bigtableTime = findViewById(R.id.tv_bigTableTime);
        TableSever tableSever = new TableSever();
        try {
            tableSever.dealwith(this, tv_smalltableNumber, tv_middleltableNumber, tv_bigtableNumber, tv_smalltableTime, tv_middletableTime, tv_bigtableTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle bundle = getIntent().getExtras();
         level = bundle.getString("level");
         name = bundle.getString("username");
        tv_sayhello = findViewById(R.id.tv_sayhello);
        tv_sayhello.setText(name);
        bt_backToLogin.setOnClickListener(onClick);
        bt_takeTheQueue.setOnClickListener(onClick);
    }
    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_backtologin:
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_takeTheNumber:
                    smalltable = findViewById(R.id.onetwopeople);
                    middletable = findViewById(R.id.threefourpeople);
                    bigtable = findViewById(R.id.morethanfivepeople);
                    final String tabletype = choseWhichTable(smalltable,middletable,bigtable);
                    TableChosenSever tableChosenSever = new TableChosenSever(tabletype,level,name);
                    tableChosenSever.dealwith(new Callback() {
                        @Override
                        public void onCallback(String json) {
                            final TableChosenSeverJson info = new Gson().fromJson(json, TableChosenSeverJson.class);
                            if (info != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("queuenumber",info.getQueuenumber());
                                        bundle.putInt("waitequeuenumber",info.getWaitnumber());
                                        bundle.putString("tabletypechosed",tabletype);
                                        bundle.putString("username",name);
                                        Intent intent1 = new Intent(LoginActivity.this,ProgressBarActivity.class);
                                        intent1.putExtras(bundle);
                                        startActivity(intent1);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFail(final String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    break;
            }


        }
    }
    String choseWhichTable(RadioButton smalltable,RadioButton middletable,RadioButton bigtable){
        if(smalltable.isChecked())
            return "small";
        if(middletable.isChecked())
            return "middle";
        if(bigtable.isChecked())
            return "big";
        return "small";

    }
}
