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

public class ListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> list;

    ListAdapter(@NonNull Context context, int resource, @NonNull List<String> response) {
        super(context, resource, response);
        this.context = context;
        this.resource = resource;
        this.list = response;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
        }
        TextView textView = convertView.findViewById(R.id.list_item_text);
        textView.setText(list.get(position));
        return convertView;
    }
}
