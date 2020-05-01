package com.example.backendexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String urlString = "https://api.nytimes.com/svc/books/v3/lists/names.json?api-key=ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
    MyRequestQueue myQueue;
    Context context;
    ListView listView;
    List <ResponseModel> list = new ArrayList<>();
    List<ResponseModel> responseModelList = new ArrayList<>();

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

    void fillList() {
        for (ResponseModel model : responseModelList) {
            if (model != null && model.getListName() != null){
                list.add(model);
                Log.d("Temp", "fillList: name = " + model.getListName());
            }
        }
    }

    public void createListStuff() {
        ListAdapter adapter = new ListAdapter(context, R.layout.simple_list_item, responseModelList/*new String[]{"Lion", "Tiger", "Monkey"}*/);
        listView.setAdapter(adapter);

    }
    public  class MakeRequest extends AsyncTask<String, String, String> {
        Context context;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        ResponseModel model = null;
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
                    parse(response);
                    createListStuff();
                    fillList();
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
        private void parse(String response) {
            try {
                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    model = new ResponseModel();
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if (obj.has("list_name"))
                        model.setListName(obj.getString("list_name"));
                    if (obj.has("display_name"))
                        model.setDisplayName(obj.getString("display_name"));
                    if (obj.has("list_name_encoded"))
                        model.setListNameEncoded(obj.getString("list_name_encoded"));
                    if (obj.has("oldest_published_date"))
                        model.setOldest_published_date(obj.getString("oldest_published_date"));
                    if (obj.has("newest_published_date"))
                        model.setNewest_published_date(obj.getString("newest_published_date"));
                    if (obj.has("updated"))
                        model.setUpdated(obj.getString("updated"));
                    if (model != null)
                    responseModelList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
