package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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

public class ChangePassScreen extends AppCompatActivity {
    EditText etEmail,etPass,etNewPass,etReNewPass;
    Button btnBack,btnSave;
    String email,passOld,passNew,rePassNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_screen);
        etEmail=findViewById(R.id.etEmailChangePass);
        etPass=findViewById(R.id.etPassOld);
        etNewPass=findViewById(R.id.etPassNew);
        etReNewPass=findViewById(R.id.etRePassNew);
        btnBack=findViewById(R.id.btnBack);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePassScreen.this,LoginScreen.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void changePass(){
        email=etEmail.getText().toString();
        passOld=etPass.getText().toString();
        passNew=etNewPass.getText().toString();
        rePassNew=etReNewPass.getText().toString();
        if (email.equals("") || passOld.equals("") || passNew.equals("") || rePassNew.equals("") || !passNew.equals(rePassNew)){
            Toast.makeText(ChangePassScreen.this,"Invalid ChangePass",Toast.LENGTH_LONG).show();
        }
        else {
            APIRetrofit.apiRtf.ChangePass(email,passOld,passNew).enqueue(new Callback<UserModal>() {
                @Override
                public void onResponse(Call<UserModal> call, Response<UserModal> response) {
                    if (response.isSuccessful()){
                        UserModal userModal=response.body();
                        if (userModal.getResult()==1){
                            Toast.makeText(ChangePassScreen.this,userModal.getMess(),Toast.LENGTH_LONG).show();
                            etEmail.setText("");
                            etPass.setText("");
                            etNewPass.setText("");
                            etReNewPass.setText("");
                        }
                        else {
                            Toast.makeText(ChangePassScreen.this,userModal.getMess(),Toast.LENGTH_LONG).show();
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