package com.banzhi.easyintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.banzhi.easyintentlib.EasyIntent;
import com.banzhi.easyintentlib.OnActivityResultCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("content", "mainactivity传递数据");
                EasyIntent.getBuilder(MainActivity.this)
                        .putIntent(intent)
                        .setRequestCode(112)
                        .build()
                        .startForResult(new OnActivityResultCallback() {

                            @Override
                            public void onActivityResultCallback(int requestCode, Intent data) {
                                String text = data.getStringExtra("callback");
                                Toast.makeText(MainActivity.this, "接收到" + text, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}
