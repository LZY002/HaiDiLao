package com.example.apple.haidilao;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ProgressBarActivity extends AppCompatActivity {
  private ProgressBar wait;
  private Button cancel;
     Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(wait.getProgress()<100){
                handler.postDelayed(runnable,100);
            }
            else {
                Toast.makeText(ProgressBarActivity.this,"Queued successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProgressBarActivity.this,WaitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("queuenumber",waitequeuenumber);
                bundle.putInt("waitequeuenumber",waitetablenumber);
                bundle.putString("tabletypechosed", tableType);
                bundle.putString("username",name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    };
     Runnable runnable = new Runnable() {
         @Override
         public void run() {
             wait.setProgress(wait.getProgress()+2);
             handler.sendEmptyMessage(0);

         }
     };
    private String waitequeuenumber;
    private int waitetablenumber;
    private String tableType;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        Bundle bundle = getIntent().getExtras();
        waitequeuenumber = bundle.getString("queuenumber");
        waitetablenumber = bundle.getInt("waitequeuenumber");
        tableType = bundle.getString("tabletypechosed");
        name = bundle.getString("username");
        cancel = findViewById(R.id.bt_cancel);
        wait = findViewById(R.id.pb_wait);
        handler.sendEmptyMessage(0);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressBarActivity.this,LoginActivity.class);
                Bundle send = new Bundle();
                send.putString("queuenumber", waitequeuenumber);
                send.putInt("waitequeuenumber", waitetablenumber);
                intent.putExtras(send);
                startActivity(intent);
            }
        });


    }


}
