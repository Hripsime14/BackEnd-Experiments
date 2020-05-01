package com.example.backendexperiments;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyRequestQueue {
    private static MyRequestQueue instance;
    private RequestQueue requestQueue;
    private static Context context;

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
}
