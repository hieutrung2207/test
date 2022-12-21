package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.API.APIRetrofit;
import com.example.myapplication.Modal.UserModal;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {
    EditText etUser,etPass,etRePass,etEmail;
    Button btnBack,btnSave;

    String user,pass,rePass,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        etUser=findViewById(R.id.etUserReg);
        etPass=findViewById(R.id.etPassReg);

        etRePass=findViewById(R.id.etRePassReg);
        etEmail=findViewById(R.id.etEmailReg);
        btnBack=findViewById(R.id.btnBack);
        btnSave=findViewById(R.id.btnRegAdd);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterScreen.this,LoginScreen.class);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }
    private void save(){
        user = etUser.getText().toString();
        pass = etPass.getText().toString();
        rePass= etRePass.getText().toString();
        email=etEmail.getText().toString();
        if (!pass.equals(rePass) || user.equals("") || pass.equals("") || rePass.equals("") || email.equals("")){
            Toast.makeText(RegisterScreen.this,"Invalid Register",Toast.LENGTH_LONG).show();
        }
        else{
            APIRetrofit.apiRtf.Reg(user,pass,email).enqueue(new Callback<UserModal>() {
                @Override
                public void onResponse(Call<UserModal> call, Response<UserModal> response) {
                    if (response.isSuccessful()){
                        UserModal userModal=response.body();
                        if (userModal.getResult()==1){
                            Toast.makeText(RegisterScreen.this,userModal.getMess(),Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegisterScreen.this,LoginScreen.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterScreen.this,userModal.getMess(),Toast.LENGTH_LONG).show();
                        }

                        Log.i("Response",response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<UserModal> call, Throwable t) {
                    Log.i("Disconneted",t.getMessage());
                }
            });
        }


    }
}