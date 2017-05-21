package com.example.tainguyen.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.json.JSONObject;

public class loginfb extends AppCompatActivity implements  View.OnClickListener {
    ProfilePictureView img;
    TextView tv;
    Button tvmh;
    CallbackManager callbackManager;
    private    LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_loginfb);
        onCreateFb();
    }
    public void  onCreateFb(){  // hàm quản lý các sự kiện khi mình click login with fb, bào gôm đăng nhập lần thành công, không chọn tài khoản đăng nhập, đăng nhập lỗi
        img =(ProfilePictureView) findViewById(R.id.img);
        tvmh=(Button)findViewById(R.id.tvmh);
        tvmh.setOnClickListener(this);
        //  img.setVisibility();
        callbackManager= CallbackManager.Factory.create();
        tv=(TextView)findViewById(R.id.tvfb);
        loginButton=(LoginButton)findViewById(R.id.fb_login_id);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { // đăng nhập thành công

                tv.setText("Login success");

                getFbInfo();
            }
            public void onCancel() { // không chọn tài khoản dăng nhập
                tv.setText("Login success");
                Toast.makeText(loginfb.this, "Login Facebook   out.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException exception) { // error
                tv.setText("login error");
                Toast.makeText(loginfb.this, "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void getFbInfo() { // hàm lấy thông tin tài khoản fb khi đăng nhập thành công
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                img.setProfileId(me.optString("id"));
                                tv.setText("Tài khoản: "+ me.optString("name")+"\n"+"Id: " + me.optString("id"));
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
    public void onClick(View v) {  // xử lý sự kiện click login = google
        switch ( v.getId()){
            case R.id.tvmh:
                Comback();
                break;
        }
    }
    public void Comback(){
        Intent intent=new Intent(loginfb.this,MainActivity.class);
        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    }
}
