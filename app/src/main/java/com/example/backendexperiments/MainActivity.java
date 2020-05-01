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
import com.example.backendexperiments.Util.Parser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String urlString = "https://api.nytimes.com/svc/books/v3/lists/names.json?api-key=ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
    MyRequestQueue myQueue;
    Context context;
    ListView listView;
    List<BooksListsModel> booksListsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        context = getApplicationContext();
        myQueue = MyRequestQueue.getInstance(context);
        MakeRequest request = new MakeRequest(context);
        request.execute();
    }


    public void createListStuff() {
        ListAdapter adapter = new ListAdapter(context, R.layout.simple_list_item, booksListsModelList/*new String[]{"Lion", "Tiger", "Monkey"}*/);
        listView.setAdapter(adapter);

    }
    public  class MakeRequest extends AsyncTask<String, Void, String> {
        Context context;
        MakeRequest(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {
            StringRequest request = new StringRequest(Request.Method.GET, urlString,
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
                    Log.d("request", "doInBackground: error = " + error);
                }
            });
            myQueue.addToRequestQueue(request);
            return null;
        }
    }
}
