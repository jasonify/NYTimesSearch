package com.example.jason.nytimessearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jason on 9/19/17.
 */


// https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20160112&sort=oldest&fq=android&api-key=a564f5cd99bd45fa8f09100c6b4a6d5c

public class Article {
    String webUrl;
    String headline;
    String thumbnail;

    public Article(JSONObject jsonObject) {

        try {
            webUrl = jsonObject.getString("web_url");
            headline = jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia.length() > 0 ) {
                thumbnail = "http://www.nytimes.com/" + multimedia.getJSONObject(0).getString("url");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
