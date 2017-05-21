package com.example.tainguyen.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class logingoogle extends AppCompatActivity implements  View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    TextView tv;
    private SignInButton signin; // nutlogin của google
    ImageView imggg;
    Button tvhm;
    Button logoutgmail;
    GoogleApiClient googleApiClient; // API của google
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logingoogle);
        onCreateGoogle();
    }
    public void onCreateGoogle()
    {
        tv=(TextView)findViewById(R.id.tv);
        logoutgmail=(Button)findViewById(R.id.logoutgmail);
        imggg=(ImageView)findViewById(R.id.imggg);
        tvhm=(Button)findViewById(R.id.tvmhc);
        tvhm.setOnClickListener(this);
        signin=(SignInButton)findViewById(R.id.bn_login);
        logoutgmail.setOnClickListener(this);
        signin.setOnClickListener(this); // thiết lập sụ kiện lắng nghe cho nút login google
        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
    }
    public void onClick(View v) {  // xử lý sự kiện click login = google
        switch ( v.getId()){
            case R.id.bn_login:
                signIn();
                break;
            case R.id.logoutgmail:
                logoutgmail();
                break;
            case R.id.tvmhc:
                Comback();
                break;
        }

    }
    public void Comback(){
        Intent intent=new Intent(logingoogle.this,MainActivity.class);
        startActivity(intent);

    }
    public void logoutgmail(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                tv.setText("Tk gmail log out");
                Intent intent=new Intent(logingoogle.this,logingoogle.class);
                startActivity(intent);
                imggg.setImageResource(R.mipmap.ic_launcher);
            }
        });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient ); // giao diện chọn tài khoản gmail đăng nhập
        startActivityForResult(intent,9001);
    }
    private void handleResult(GoogleSignInResult result)
    {
        if(result.isSuccess()){  // đăng nhapah gmail thành công
            GoogleSignInAccount googleSignInAccount= result.getSignInAccount();
            tv.setText("Tài khoản: " + googleSignInAccount.getEmail());
            Picasso.with(this).load(googleSignInAccount.getPhotoUrl()).into(imggg);
            updateUI(true);
        }
        else { // đăng nhập gmail lỗi
            updateUI(false);
        }
    }
    private void updateUI(boolean isLogin)
    {
        if(isLogin){  // đăng nhập thành công, chuyển giao diện
            signin.setVisibility(View.GONE);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }
}
