package com.example.backendexperiments.Util;

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
                if (model != null)
                    booksListsModels.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return booksListsModels;
    }
}

