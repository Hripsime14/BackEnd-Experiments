package com.example.backendexperiments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.backendexperiments.Models.BooksListsModel;
import com.example.backendexperiments.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<BooksListsModel> {
    private Context context;
    private int resource;
    private List<BooksListsModel> list;

    public ListAdapter(Context context, int resource, List<BooksListsModel> booksListsModels) {
        super(context, resource, booksListsModels);
        this.context = context;
        this.resource = resource;
        this.list = booksListsModels;
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
        textView = convertView.findViewById(R.id.list_item_text3);
        textView.setText("3. " +list.get(position).getListNameEncoded());
        textView = convertView.findViewById(R.id.list_item_text4);
        textView.setText("4. " +list.get(position).getOldest_published_date());
        textView = convertView.findViewById(R.id.list_item_text5);
        textView.setText("5. " +list.get(position).getNewest_published_date());
        textView = convertView.findViewById(R.id.list_item_text6);
        textView.setText("6. " +list.get(position).getUpdated());
        return convertView;
    }
}
