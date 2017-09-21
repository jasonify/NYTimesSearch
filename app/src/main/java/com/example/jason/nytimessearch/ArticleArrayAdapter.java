package com.example.jason.nytimessearch;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by jason on 9/20/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }
}
