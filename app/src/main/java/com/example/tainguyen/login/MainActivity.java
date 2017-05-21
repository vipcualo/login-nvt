package com.example.tainguyen.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import  java.net.*;
/*  Đầu tiên e thiết lập cho phép kết nối mạng internet <uses-permission android:name="android.permission.INTERNET" > trong file AndroidManifest.xml
    Code giao diện trong file activity_main.xml
    thiết lập thêm 1 số điều kiện để kết nối đc vs fb và google trong 2 file build.gradle ( project và module)
*/
import  java.util.*;
import com.facebook.login.*;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    Button lifb;
    Button ligg;
    Button exit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lifb=(Button)findViewById(R.id.loginfb);
        lifb.setOnClickListener(this);
        ligg=(Button)findViewById(R.id.logingg);
        ligg.setOnClickListener(this);
        exit=(Button)findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }
    public void onClick(View v) {  // xử lý sự kiện click login = google
        switch ( v.getId()){
            case R.id.loginfb:
                Loginfb();
                break;
            case R.id.logingg:
                ;Logingg();
                break;
            case R.id.exit:
                Exit();
                break;
        }
    }
    public void Exit(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void Loginfb(){
        Intent intent=new Intent(MainActivity.this,loginfb.class);
        startActivity(intent);
    }
    public void Logingg(){
        Intent intent=new Intent(MainActivity.this,logingoogle.class);
        startActivity(intent);
    }
}
