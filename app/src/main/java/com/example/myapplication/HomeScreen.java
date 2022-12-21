package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.API.APIRetrofit;
import com.example.myapplication.Modal.ItemsModal;
import com.example.myapplication.Modal.ItemsResult;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity {
    String username,password,email;
    RecyclerView rcv;
    FloatingActionButton fab;
    ImageView imgDate,imgDel;
    TextView pid;
    EditText etName,etPrice,etNameUp,etPriceUp;
    Button btnCancel,btnAdd,btnCancelUp,btnSaveUp;
    Spinner spn,spnUp;
    String brandId,name,brand,nameUp,brandUp;
    Double price,priceUp;
    Date dob;
    int id;
    ItemsModal item;
    SwipeRefreshLayout swpLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        rcv=findViewById(R.id.rcvItems);
        fab=findViewById(R.id.fab);
        swpLayout=findViewById(R.id.swpLayout);
        //get dữ liệu user;

        item = new ItemsModal();
        id = item.getPid();


        swpLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showItems();
                swpLayout.setRefreshing(false);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd();
            }
        });
        showItems();



//        Intent i = getIntent();
//        id=i.getIntExtra("id",1000);
//        username=i.getStringExtra("username");
//        password=i.getStringExtra("password");
//        email=i.getStringExtra("email");
//        Log.i("User: ",id+"-"+username+"-"+password+"-" +email);
    }
    public void dialogAdd(){
        Dialog dialog=new Dialog(HomeScreen.this);
        dialog.setContentView(R.layout.layout_dialog_add_item);
        etName=dialog.findViewById(R.id.etNameItem);
        etPrice=dialog.findViewById(R.id.etPriceItem);
//        etDob=dialog.findViewById(R.id.etDobItem);
        spn=dialog.findViewById(R.id.spnBrandItem);
        btnCancel=dialog.findViewById(R.id.btnCancel);
        btnAdd=dialog.findViewById(R.id.btnAdd);
//        imgDate=dialog.findViewById(R.id.imgDate);
//        dob = new Date();




//        imgDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chonNgay();
//            }
//        });
        ArrayList<ItemsModal> itemsModals = new ArrayList<>();
        ArrayList<String> arrBrand = new ArrayList<>();
        arrBrand.add("Apple");
        arrBrand.add("Samsung");
        arrBrand.add("Xiaomi");
        arrBrand.add("Asus");
        arrBrand.add("Nokia");
        arrBrand.add("Oppo");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrBrand);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn.setAdapter(arrayAdapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brandId=arrBrand.get(i);
                Toast.makeText(HomeScreen.this,brandId,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=etName.getText().toString();
                price= Double.valueOf(etPrice.getText().toString());
                brand=brandId;
                APIRetrofit.apiRtf.insertItem(name,price,brand).enqueue(new Callback<ItemsResult>() {
                    @Override
                    public void onResponse(Call<ItemsResult> call, Response<ItemsResult> response) {
                        if (response.isSuccessful()){
                            ItemsResult itemsResult=response.body();
                            if (itemsResult.getResult()==1){
                                Toast.makeText(HomeScreen.this,itemsResult.getMess(),Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                showItems();
                            }
                            else {
                                Toast.makeText(HomeScreen.this,itemsResult.getMess(),Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ItemsResult> call, Throwable t) {

                    }
                });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Log.i("Date", dob+"");
            }
        });
        dialog.show();

    }
    public void showItems(){
        APIRetrofit.apiRtf.showItems().enqueue(new Callback<ItemsResult>() {
            @Override
            public void onResponse(Call<ItemsResult> call, Response<ItemsResult> response) {
                if (response.isSuccessful()){
                    ItemsResult itemsResult=response.body();
                    if (itemsResult.getResult()==1){
                        List<ItemsModal> list = new ArrayList<>();
                        list=itemsResult.getList();
                        ItemAdapter adapter = new ItemAdapter(HomeScreen.this,list, HomeScreen.this);
                        rcv.setLayoutManager(new LinearLayoutManager(HomeScreen.this,RecyclerView.VERTICAL,false));
                        rcv.setAdapter(adapter);
                    }
                    else {
                        Toast.makeText(HomeScreen.this,itemsResult.getMess(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemsResult> call, Throwable t) {
                Log.i("Items: ",t.getMessage());
            }
        });
    }
//    public void chonNgay(){
//        final Calendar calendar=Calendar.getInstance();
//        int ngay=calendar.get(Calendar.DAY_OF_MONTH);
//        int thang=calendar.get(Calendar.MONTH);
//        int nam=calendar.get(Calendar.YEAR);
//        DatePickerDialog datePickerDialog=new DatePickerDialog(HomeScreen.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                sdf = new SimpleDateFormat("yyyy-MM-dd");
//                etDob.setText(year+"-"+(month+1)+"-"+dayOfMonth);
//            }
//        },nam,thang,ngay);
//
//        datePickerDialog.show();
//    }

}