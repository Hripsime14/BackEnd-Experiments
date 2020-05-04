package com.example.backendexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.backendexperiments.Adapters.ListAdapter;
import com.example.backendexperiments.Models.BooksListsModel;
import com.example.backendexperiments.Util.Parser;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private Context context;
    private ListView listView;
    ListAdapter adapter;
    MyRequestQueue myQueue;
    List<BooksListsModel> booksListsModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        context = getApplicationContext();

        listView = findViewById(R.id.listViewId);
        createListStuff();
        myQueue = MyRequestQueue.getInstance(context);
        MakeOverviewRequest request = new MakeOverviewRequest(context);
        request.execute();
}
    public void createListStuff() {
        if (adapter == null) adapter = new ListAdapter(context, R.layout.simple_list_item, booksListsModelList);

        listView.setAdapter(adapter);
    }

    public  class MakeOverviewRequest extends AsyncTask<String, Void, String> {
        Context context;
        MakeOverviewRequest(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {
            StringRequest request = new StringRequest(Request.Method.GET, myQueue.getBookListURL(),
                    new Response.Listener<String>() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(String response) {
                            booksListsModelList = new Parser().parseLists(response);
                            createListStuff();
                        }
                    }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ExperimentsLog", "onErrorResponse: url = " + myQueue.getBookListURL());
                    Log.d("ExperimentsLog", "doInBackground: error = " + error);
                }
            });
            myQueue.addToRequestQueue(request);
            return null;
        }
    }
}
