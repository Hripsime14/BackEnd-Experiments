package com.example.backendexperiments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ResponseModel> {
    private Context context;
    private int resource;
    private List<ResponseModel> list;

    ListAdapter(Context context, int resource, List<ResponseModel> responseModels) {
        super(context, resource, responseModels);
        this.context = context;
        this.resource = resource;
        this.list = responseModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
        }
        TextView textView = convertView.findViewById(R.id.list_item_text1);
        textView.setText("1. " + list.get(position).getListName());
        textView = convertView.findViewById(R.id.list_item_text2);
        textView.setText("2. " +list.get(position).getDisplayName());
        return convertView;
    }
}
