package com.banzhi.easyintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final Intent intent = getIntent();
        String text = intent.getStringExtra("content");
        Toast.makeText(SecondActivity.this, text, Toast.LENGTH_SHORT).show();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                intent1.putExtra("callback", "SecondActivity返回数据");
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }


}
