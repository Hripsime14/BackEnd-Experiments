package com.example.backendexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.backendexperiments.Util.Parser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    String urlString = "https://api.nytimes.com/svc/books/v3/lists/names.json?api-key=ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
    String urlString;
    MyRequestQueue myQueue;
    Context context;
    ListView listView;
    ListAdapter adapter;
    List<BooksListsModel> booksListsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        myQueue = MyRequestQueue.getInstance(context);
//        MakeRequest request = new MakeRequest(context);
//        request.execute();
        final DatePicker simpleDatePicker = findViewById(R.id.simpleDatePicker);
        simpleDatePicker.setVisibility(View.INVISIBLE);
        listView = findViewById(R.id.listViewId);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                simpleDatePicker.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        Button selectButton = findViewById(R.id.selectButtonId);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleDatePicker.setVisibility(View.VISIBLE);
            }
        });
        Button okButton = findViewById(R.id.okButtonId);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(simpleDatePicker);
                simpleDatePicker.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void getDate(DatePicker simpleDatePicker) {
        String year = simpleDatePicker.getYear() + "";
        String month = simpleDatePicker.getMonth() + "";
        String day = simpleDatePicker.getDayOfMonth() + "";
        if(Integer.parseInt(month) < 10) month = "0" + month;
        if(Integer.parseInt(day) < 10) day  = "0" + day ;
        urlString = "https://api.nytimes.com/svc/books/v3/lists/overview/" + year + "-" + month + "-" + day + ".json?api-key=ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
        Log.d("ExperimentsLog", "getDate: url = " + urlString);
        MakeRequest request = new MakeRequest(context);
        request.execute();
    }

    public void createListStuff() {
        if (adapter == null) adapter = new ListAdapter(context, R.layout.simple_list_item, booksListsModelList);
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
//                    booksListsModelList = new Parser().parseLists(response);
                    booksListsModelList = new Parser().parseListsWithDate(response);
                    createListStuff();
                }
            }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ExperimentsLog", "doInBackground: error = " + error);
                }
            });
            myQueue.addToRequestQueue(request);
            return null;
        }
    }
}
