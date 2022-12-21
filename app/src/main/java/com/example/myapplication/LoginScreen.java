package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.API.APIRetrofit;
import com.example.myapplication.Modal.UserModal;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    EditText etMail,etPass,etEmailSend;
    Button btnDangKi,btnDangNhap,btnBack,btnSend;
    Dialog dialog;
    String mail,pass;
    TextView tvForgetPass,tvChangePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        etMail=findViewById(R.id.etMailLog);
        etPass=findViewById(R.id.etPassLog);
        tvForgetPass=findViewById(R.id.tvForgetPass);
        tvChangePass=findViewById(R.id.tvChangePass);
        btnDangKi=findViewById(R.id.btnReg);
        btnDangNhap=findViewById(R.id.btnLog);
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this,RegisterScreen.class);
                startActivity(i);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, HomeScreen.class);
                //                login();
            }
        });
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRsPass();
            }
        });
        tvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this,ChangePassScreen.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void dialogRsPass() {
        dialog = new Dialog(LoginScreen.this);
        dialog.setContentView(R.layout.layout_dialog_reset_pass);
        etEmailSend=dialog.findViewById(R.id.etEmailSend);
        btnBack=dialog.findViewById(R.id.btnBack);
        btnSend=dialog.findViewById(R.id.btnSend);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();
    }

    public void login(){
        mail=etMail.getText().toString();
        pass=etPass.getText().toString();
        if (mail.equals("") || pass.equals("")){
            Toast.makeText(LoginScreen.this,"Invalid Register",Toast.LENGTH_LONG).show();
        }
        else {
            APIRetrofit.apiRtf.Login(mail,pass).enqueue(new Callback<UserModal>() {
                @Override
                public void onResponse(Call<UserModal> call, Response<UserModal> response) {
                    if (response.isSuccessful()){
                        UserModal userModal= response.body();
                        if (userModal.getResult()==1){
                            Toast.makeText(LoginScreen.this,userModal.getMess(),Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginScreen.this,HomeScreen.class);
                            i.putExtra("id",userModal.getId());
                            i.putExtra("username",userModal.getUsername());
                            i.putExtra("password",userModal.getPassword());
                            i.putExtra("email",userModal.getEmail());
                            startActivity(i);
                            finish();

                        }
                    }
                }

                @Override
                public void onFailure(Call<UserModal> call, Throwable t) {

                }
            });
        }

    }


}