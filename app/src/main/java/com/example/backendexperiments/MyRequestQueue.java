package com.example.backendexperiments;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyRequestQueue {//stex avelacnel api link@
    private static MyRequestQueue instance;
    private RequestQueue requestQueue;
    private static Context context;
    private String baseURL = "https://api.nytimes.com/svc/books/v3/lists/";
    private String key = "ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
    private String date;
    //2 URL unenal, mek@ overview a veradardznum, mek@ book list


    public static MyRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new MyRequestQueue(context);
        }
        return new MyRequestQueue(context);
    }

    private MyRequestQueue(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverViewURL(String date) {
//        urlDateString = "https://api.nytimes.com/svc/books/v3/lists/overview/" + year + "-" + month + "-" + day + ".json?api-key=ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
        return baseURL + "overview/" + date + ".json?api-key=" + key;
    }

    public String getBookListURL() {
        return baseURL + "names" +  ".json?api-key=" + key;//https://api.nytimes.com/svc/books/v3/lists/names.json?api-key=ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
    }
}
