package com.phoenix.soft.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phoenix.soft.agenda.resource.ResAccount;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

class AccAdapter extends ArrayAdapter<ResAccount> {
    private int resourceId;

    public AccAdapter(Context context, int resource, List<ResAccount> object) {
        super(context, resource,object);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResAccount acc = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView tv_acc = (TextView) view.findViewById(R.id.tv_list_acc);
        TextView tv_bal = (TextView) view.findViewById(R.id.tv_list_bal);
        tv_acc.setText(acc.getName());
        tv_bal.setText(acc.getBal()+"");

        return view;
    }
}
