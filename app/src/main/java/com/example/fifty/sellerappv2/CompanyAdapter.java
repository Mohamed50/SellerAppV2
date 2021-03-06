package com.example.fifty.sellerappv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Fifty on 6/6/2018.
 */

public class CompanyAdapter extends ArrayAdapter<CompanyItem> {
    public CompanyAdapter(Context context, ArrayList<CompanyItem> departmentItemArrayList) {
        super(context, 0,departmentItemArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position , View convertView , ViewGroup parent){
        if(convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.company_spinner_row,parent,false);
        }
        ImageView bankIcon = (ImageView) convertView.findViewById(R.id.bankItemIcon);
        TextView bankName = (TextView) convertView.findViewById(R.id.bankItemName);
        CompanyItem currentItem = getItem(position);
        if(currentItem != null){
            bankIcon.setImageResource(currentItem.getCompenyicon());
            bankName.setText(currentItem.getCompanyTypeName());
        }

        return convertView;
    }
}
