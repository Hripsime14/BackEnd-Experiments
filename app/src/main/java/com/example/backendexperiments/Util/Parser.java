package com.example.backendexperiments.Util;

import android.util.Log;

import com.example.backendexperiments.Models.BooksListsModel;
import com.example.backendexperiments.Models.BooksOverviewModel;

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

    public List<BooksOverviewModel> parseListsWithDate(String response) {
        JSONObject jsonObject;
        JSONArray jsonArray;
        BooksOverviewModel model;
        List<BooksOverviewModel> booksListsModels = new ArrayList<>();

        try {
            jsonObject = new JSONObject(response);
            JSONObject object0 = jsonObject.getJSONObject("results");
            jsonArray = object0.getJSONArray("lists");

            JSONObject object = (JSONObject) jsonArray.get(0);
            jsonArray = (JSONArray) object.get("books");

            for (int i = 0; i < jsonArray.length(); i++) {
                model = new BooksOverviewModel();
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.has("author"))
                    model.setAuthor(obj.getString("author"));
                if (obj.has("title"))
                    model.setTitle(obj.getString("title"));
                if (obj.has("created_date"))
                    model.setCreatedDate(obj.getString("created_date"));
                if (obj.has("description"))
                    model.setDescription(obj.getString("description"));
                booksListsModels.add(model);
            }
        } catch (JSONException e) {
            Log.d("ExperimentsLog", "parseLists: error = " + e);
            e.printStackTrace();
        }
        return booksListsModels;
    }
}

