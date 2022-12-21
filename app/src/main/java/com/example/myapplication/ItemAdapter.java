package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.APIRetrofit;
import com.example.myapplication.Modal.ItemsModal;
import com.example.myapplication.Modal.ItemsResult;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder>{

    Context context;
    List<ItemsModal> list;

    HomeScreen homeScreen;
    EditText etName,etPrice,etBrand;
    Button btnCancel,btnSave;
    Spinner spn;
    String brandSelected="";
    public ItemAdapter(Context context, List<ItemsModal> list, HomeScreen homeScreen) {
        this.context = context;
        this.list = list;
        this.homeScreen = homeScreen;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NumberFormat nFormat=NumberFormat.getInstance(Locale.US);
        ItemsModal itemsModal = list.get(position);
        holder.tvNameItem.setText(itemsModal.getName());
        holder.tvPriceItem.setText(nFormat.format(itemsModal.getPrice())+ " VNĐ");
        holder.imgDelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaItem(itemsModal.getPid());
            }
        });
        holder.flayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,itemsModal.getPid()+"",Toast.LENGTH_LONG).show();

            }
        });

        holder.flayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                updateItem(itemsModal.getPid(),itemsModal.getName(),itemsModal.getPrice(),itemsModal.getBrand());
                return false;
            }
        });



    }


    public void updateItem(int pid, String name, Double price, String brand){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_update_item);
        etName=dialog.findViewById(R.id.etNameUp);
        etPrice=dialog.findViewById(R.id.etPriceUp);
        spn=dialog.findViewById(R.id.spnBrand);
        btnCancel=dialog.findViewById(R.id.btnCancelUp);
        btnSave=dialog.findViewById(R.id.btnSaveUp);

        etName.setText(name);
        etPrice.setText(price+"");


        ArrayList<String> arrBrand = new ArrayList<>();
        arrBrand.add("Apple");
        arrBrand.add("Samsung");
        arrBrand.add("Xiaomi");
        arrBrand.add("Asus");
        arrBrand.add("Nokia");
        arrBrand.add("Oppo");

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item,arrBrand);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn.setAdapter(arrayAdapter);

        int indexSelected = arrBrand.indexOf(brand);
        spn.setSelection(indexSelected);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brandSelected = arrBrand.get(i);
                Toast.makeText(context, brandSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUp=etName.getText().toString();
                Double priceUp= Double.valueOf(etPrice.getText().toString());

                APIRetrofit.apiRtf.updateItem(pid,nameUp,priceUp,brandSelected).enqueue(new Callback<ItemsResult>() {
                    @Override
                    public void onResponse(Call<ItemsResult> call, Response<ItemsResult> response) {
                        if (response.isSuccessful()){
                            ItemsResult itemsResult=response.body();
                            if (itemsResult.getResult()==1){
                                dialog.dismiss();
                                Toast.makeText(context,itemsResult.getMess(),Toast.LENGTH_LONG).show();
                                homeScreen.showItems();
                            }
                            else {
                                Toast.makeText(context,itemsResult.getMess(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ItemsResult> call, Throwable t) {
                    }
                });

            }
        });
        dialog.show();
    }

    public void xoaItem(int pid) {
        Log.i("Del: ","http://192.168.1.12/MOD403_ASM/deleteItem.php?pid="+pid);
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không???");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                APIRetrofit.apiRtf.deleteItem(pid).enqueue(new Callback<ItemsResult>() {
                    @Override
                    public void onResponse(Call<ItemsResult> call, Response<ItemsResult> response) {
                        if (response.isSuccessful()){
                            ItemsResult itemsResult=response.body();
                            if (itemsResult.getResult()==1){
                                Toast.makeText(context,itemsResult.getMess(),Toast.LENGTH_LONG).show();
                                homeScreen.showItems();
                            }
                            else {
                                Toast.makeText(context,itemsResult.getMess(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ItemsResult> call, Throwable t) {

                    }

                });

                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        builder.show();

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem,imgDelItem;
        TextView tvNameItem,tvPriceItem;
        FrameLayout flayout;


        public viewHolder(@NonNull View itemView) {

            super(itemView);
            imgItem=itemView.findViewById(R.id.imgItem);
            imgDelItem=itemView.findViewById(R.id.imgDelItem);
            tvNameItem=itemView.findViewById(R.id.tvNameItem);
            tvPriceItem=itemView.findViewById(R.id.tvPriceItem);
            flayout=itemView.findViewById(R.id.layoutItem);




        }



    }

}
