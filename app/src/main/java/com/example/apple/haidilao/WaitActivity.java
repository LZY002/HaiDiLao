package com.example.apple.haidilao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WaitActivity extends AppCompatActivity {
     private Button btCancleQueue;
     private TextView tvWaiteQueueNumber;
     private  TextView tvWaiteTableNumber;
     private  TextView tvqueueNumber;
    String  waitequeuenumber;
    int waitetablenumber;
    String tabletype;
    String name;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        Bundle bundle = getIntent().getExtras();
        waitequeuenumber = bundle.getString("queuenumber");
        waitetablenumber = bundle.getInt("waitequeuenumber");
        tabletype = bundle.getString("tabletypechosed");
        name = bundle.getString("username");
        tvWaiteQueueNumber = findViewById(R.id.tv_queuenumber);
        tvWaiteTableNumber = findViewById(R.id.tv_waitetablenumber);
        tvqueueNumber = findViewById(R.id.tv_queuetitle);
        tvqueueNumber.setText("Respected "+name);
        tvWaiteQueueNumber.setText(waitequeuenumber);
        tvWaiteTableNumber.setText(String.valueOf(waitetablenumber));
        btCancleQueue = findViewById(R.id.cancelqueue);
        btCancleQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancleTable cancleTable = new CancleTable(tabletype,name);
                try {
                    cancleTable.dealwith(new Callback() {
                        @Override
                        public void onCallback(String json) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WaitActivity.this,"Cancle successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WaitActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }
                            });
                        }

                        @Override
                        public void onFail(final String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WaitActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
