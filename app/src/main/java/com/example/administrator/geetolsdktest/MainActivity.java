package com.example.administrator.geetolsdktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gtdev5.geetolsdk.mylibrary.initialization.GeetolSDK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GeetolSDK.init(MainActivity.this);
        findViewById(R.id.button).setOnClickListener(v -> {
            new ShortcutManager(this).addLaunchShortcut(ShortcutManager.WECHAT_PACKAGE);
        });
        StringBuilder sb = new StringBuilder();
        sb.append("我的天啊");
        sb.append(MoneyUtil.fmtMicrometer(MoneyUtil.getCharge(String.valueOf((float)12223.45))));
        ((TextView)findViewById(R.id.textView)).setText(sb.toString());

    }
}
