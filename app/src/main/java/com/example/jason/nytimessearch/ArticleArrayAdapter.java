package com.example.jason.nytimessearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jason on 9/20/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Article article = this.getItem(position);

        if (convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }
            ImageView  imageView = (ImageView) convertView.findViewById(R.id.ivImage);
            imageView.setImageResource(0);

            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            tvTitle.setText(article.getHeadline());

            String thumbnail = article.getThumbnail();

            if(!TextUtils.isEmpty(thumbnail)) {
                Picasso.with(getContext())
                        .load(thumbnail)
                        .placeholder(R.drawable.spinner_small)
                        .error(R.drawable.error)
                        .into(imageView);

            } else {
                imageView.setImageResource(R.drawable.gradient_small);
            }

        return convertView;
    }
}
