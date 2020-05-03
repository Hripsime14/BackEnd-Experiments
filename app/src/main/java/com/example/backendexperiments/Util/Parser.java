package com.example.backendexperiments.Util;

import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.backendexperiments.BooksListsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<BooksListsModel> parseLists(String response) {
        JSONObject jsonObject;
        JSONArray jsonArray;
        BooksListsModel model;
        List<BooksListsModel> booksListsModels = new ArrayList<>();

        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                model = new BooksListsModel();
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
                booksListsModels.add(model);
            }
        } catch (JSONException e) {
            Log.d("ExperimentsLog", "parseLists: error = " + e);
            e.printStackTrace();
        }
        return booksListsModels;
    }

    public List<BooksListsModel> parseListsWithDate(String response) {
        JSONObject jsonObject;
        JSONArray jsonArray;
        BooksListsModel model;
        List<BooksListsModel> booksListsModels = new ArrayList<>();

        try {
            jsonObject = new JSONObject(response);
            JSONObject object0 = jsonObject.getJSONObject("results");
            jsonArray = object0.getJSONArray("lists");

            JSONObject object = (JSONObject) jsonArray.get(0);
            jsonArray = (JSONArray) object.get("books");

            for (int i = 0; i < jsonArray.length(); i++) {
                model = new BooksListsModel();
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.has("author"))
                    if (obj.has("title"))
                        model.setUpdated(obj.getString("title"));
                    model.setListName(obj.getString("author"));
                if (obj.has("contributor"))
                    model.setDisplayName(obj.getString("contributor"));
                if (obj.has("created_date"))
                    model.setListNameEncoded(obj.getString("created_date"));
                if (obj.has("description"))
                    model.setOldest_published_date(obj.getString("description"));
                if (obj.has("price"))
                    model.setNewest_published_date(obj.getString("price"));
                booksListsModels.add(model);
            }
        } catch (JSONException e) {
            Log.d("ExperimentsLog", "parseLists: error = " + e);
            e.printStackTrace();
        }
        return booksListsModels;
    }
}

