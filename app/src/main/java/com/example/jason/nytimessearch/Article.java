package com.example.jason.nytimessearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jason on 9/19/17.
 */


// https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20160112&sort=oldest&fq=android&api-key=a564f5cd99bd45fa8f09100c6b4a6d5c

public class Article {
    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

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
            } else {
                thumbnail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> parseArticles(JSONArray jsonArray) {

        ArrayList<Article> articles = new ArrayList<>();
        for(int x = 0; x < jsonArray.length(); x++) {
            Article article;
            try {
                article = new Article(jsonArray.getJSONObject(x));
                articles.add(article);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return articles;
    }
}
