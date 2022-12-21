package com.example.myapplication.Modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemsResult
{
    ItemsModal itemsModal;

    @SerializedName("items")
    List<ItemsModal> list;

    int result;
    String mess;

    public ItemsModal getItemsModal() {
        return itemsModal;
    }

    public void setItemsModal(ItemsModal itemsModal) {
        this.itemsModal = itemsModal;
    }

    public List<ItemsModal> getList() {
        return list;
    }

    public void setList(List<ItemsModal> list) {
        this.list = list;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    @Override
    public String toString() {
        return "ItemsResult{" +
                "itemsModal=" + itemsModal +
                ", list=" + list +
                ", result=" + result +
                ", mess='" + mess + '\'' +
                '}';
    }
}
