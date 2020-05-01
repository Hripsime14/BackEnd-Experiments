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
    List <String> list = new ArrayList<>();

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
        ListAdapter adapter = new ListAdapter(context, R.layout.simple_list_item, list/*new String[]{"Lion", "Tiger", "Monkey"}*/);
        listView.setAdapter(adapter);

    }
    public  class MakeRequest extends AsyncTask<String, String, String> {
        Context context;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
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
                }
            }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("request", "doInBackground: here3" + error);
                }
            });
            myQueue.addToRequestQueue(request);
            createListStuff();
            return null;
        }

//        @Override
//        protected void onPostExecute(String response) {
//            for (int i = 0; i < 10; i++) {
//                if (response != null)
//                list.add(response.substring(i * 10, i * 10 + 10));
//            }
//        }
        private void parse(String response) {
            try {
                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < 11/*jsonArray.length()*/; i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if (obj.has("list_name")) list.add(obj.getString("list_name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
