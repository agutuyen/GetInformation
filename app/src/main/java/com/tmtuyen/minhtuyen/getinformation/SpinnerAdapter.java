package com.tmtuyen.minhtuyen.getinformation;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MinhTuyen on 06/04/2017.
 */

public class SpinnerAdapter extends ArrayAdapter {
    LayoutInflater inflater;
    String[] dsĐiaiem = {"Chọn đối tượng ...", "Bến xe, tàu", "Bưu điện", "Cơ quan hành chính", "Cơ sở lưu trú"
            , "Cơ sở y tế","Công ty du lịch", "Địa điểm ẩm thực", "Địa điểm du lịch", "Địa điểm mua sắm", "Máy ATM", "Ngân hàng", "Trạm xăng"};
    int[] dsIcon = {R.drawable.ic_list, R.drawable.ic_subway, R.drawable.ic_poffice, R.drawable.ic_hc,
            R.drawable.ic_hotel, R.drawable.ic_hopital,R.drawable.ic_tour,  R.drawable.ic_restaurant, R.drawable.ic_travel,
            R.drawable.ic_shopping, R.drawable.ic_atm, R.drawable.ic_bank, R.drawable.ic_ev_station};

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.spinner_item, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);
        imageView.setImageResource(dsIcon[position]);
        TextView textView = (TextView) itemView.findViewById(R.id.txt);
        textView.setText(dsĐiaiem[position]);
        return itemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {
        return getView(position, convertView, parent);

    }

    @Override
    public int getCount() {
        return dsĐiaiem.length;
    }
}
