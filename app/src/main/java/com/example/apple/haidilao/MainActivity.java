package com.example.apple.haidilao;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLogin = findViewById(R.id.bt_register);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextusr = findViewById(R.id.et_usename);
                EditText editTextpassword = findViewById(R.id.et_password);
                String name = editTextusr.getText().toString().trim();
                String password = editTextpassword.getText().toString().trim();
                isExist(name,password);
            }
        });
    }
    void isExist(final String name , String password) {
        JsonObject jsonObject = new JsonObject(1);
        jsonObject.setUserName(name);
        jsonObject.setPassword(password);
        ClientSever clientSever = new ClientSever(jsonObject);
        clientSever.dealwith(new Callback() {
            @Override
            public void onCallback(final String result) {
                if ("D".equals(result)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Incorrect account or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("level", result);
                            bundle.putString("username",name);
                            intent.putExtras(bundle);
                            Toast.makeText(MainActivity.this, "Welcome to HaiDiLao", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFail(final String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
