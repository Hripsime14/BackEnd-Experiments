package com.example.backendexperiments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.backendexperiments.Models.BooksOverviewModel;
import com.example.backendexperiments.R;

import java.util.List;

public class OverviewAdapter extends ArrayAdapter<BooksOverviewModel> {
    private Context context;
    private int resource;
    private List<BooksOverviewModel> list;

    public OverviewAdapter(Context context, int resource, List<BooksOverviewModel> booksListsModels) {
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
        TextView textView = convertView.findViewById(R.id.overview_item_text1);
        textView.setText("1. " + list.get(position).getAuthor());
        textView = convertView.findViewById(R.id.overview_item_text2);
        textView.setText("2. " +list.get(position).getTitle());
        textView = convertView.findViewById(R.id.overview_item_text3);
        textView.setText("3. " +list.get(position).getDescription());
        textView = convertView.findViewById(R.id.overview_item_text4);
        textView.setText("4. " +list.get(position).getCreatedDate());
        return convertView;
    }
}
