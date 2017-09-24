package com.example.jason.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.jason.nytimessearch.Article;
import com.example.jason.nytimessearch.ArticleArrayAdapter;
import com.example.jason.nytimessearch.EndlessScrollListener;
import com.example.jason.nytimessearch.R;
import com.example.jason.nytimessearch.utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    EndlessScrollListener scrollListener;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    String sortingOrder = "newest";

    int year = 2016;
    int month = 1;
    int day = 1;

    Boolean isFiltered = false;

    Boolean isArtsChecked = false;
    Boolean isFashionChecked = false;
    Boolean isSportsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
        initFilterVariables();
    }


    public void initFilterVariables(){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

    }
    public void setupViews() {



        etQuery =  (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);

        scrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
//                if(page <= 100 ) {
                Log.d("DEBUG",  "paginating: " + page);
                loadNextDataFromApi(page);
                return true;
//                }
//                return false;
            }
        };

        gvResults.setOnScrollListener(scrollListener);



        btnSearch = (Button) findViewById(R.id.btnSearch);

        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // RETURN TO PARENT ACTIVITY:

                // http://guides.codepath.com/android/Using-Intents-to-Create-Flows#passing-data-to-launched-activities

                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                Article article = articles.get(position);
                i.putExtra("article", article);
                startActivity(i);
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int page) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = getUrlParams(page -1);

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = response.getJSONObject("response").getJSONArray("docs");
                    ArrayList<Article> newArticles = Article.parseArticles(docs);
                    articles.addAll(newArticles);
                    adapter.notifyDataSetChanged();
//                    Log.d("DEBUG",  articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem filtersButton = menu.findItem(R.id.menu_item_filters);

        filtersButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                presentFilters();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onArticleSearch(View view) {
        // 1. First, clear the array of data
        articles.clear();
        // 2. Notify the adapter of the update
        adapter.notifyDataSetChanged();
        scrollListener.resetState();



        // Toast.makeText(this, "Searched " + query, Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = getUrlParams(0);

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.parseArticles(docs));
                    adapter.notifyDataSetChanged();
//                    Log.d("DEBUG",  articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RequestParams getUrlParams(int page) {
        String query = etQuery.getText().toString();

        RequestParams params = new RequestParams();
        params.put("api-key", "a564f5cd99bd45fa8f09100c6b4a6d5c");
        params.put("page", page);
        params.put("q", query);

        if (isFiltered) {
            String dateString = "" + year;
            month += 1; // Months are in 0 -11 for date Picker :(
            if(month < 10) {
                dateString += "0" + month;
            } else{
                dateString += month;
            }
            month -=1; // Months are in 0 -11 for date Picker :(

            if(day < 10){
                dateString += "0" + day;
            } else {
                dateString += day;
            }
            params.put("begin_date", dateString);

            params.put("sort", sortingOrder);


            // Setup news_desk:
            String newDesk = "news_desk:(";
            Boolean addedNewDesk = false;
            if(isArtsChecked) {
                newDesk += "\"Arts\"";
                addedNewDesk = true;
            }

            if(isFashionChecked) {
                if(addedNewDesk){
                    newDesk += " ";
                    addedNewDesk = false;
                }

                newDesk +=   "\"Fashion & Style\"";
                addedNewDesk = true;
            }

            if(isSportsChecked) {
                if(addedNewDesk){
                    newDesk += " ";
                    addedNewDesk = false;
                }

                newDesk +=   "\"Sports\"";
                addedNewDesk = true;
            }

            newDesk += ")";
            if(!isArtsChecked && !isFashionChecked && !isSportsChecked ) { // None are true so nothing;

            } else {
                Log.d("DEBUG", newDesk);
                params.put("fq", newDesk);
            }


            Log.d("DEBUG", params.toString());
        }


        return params;
    }

    private void presentFilters() {
        Log.d("debug", "clicked filters");
        Intent intent = new Intent(SearchActivity.this,  FiltersActivity.class);
        intent.putExtra("sorting_order", sortingOrder);

        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("day", day);

        intent.putExtra("is_arts_checked", isArtsChecked);
        intent.putExtra("is_fashion_checked", isFashionChecked);
        intent.putExtra("is_sports_checked", isSportsChecked);


        startActivityForResult(intent, Constants.FILTER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == Constants.FILTER_REQUEST_CODE) {
            // Extract name value from result extras
            isFiltered = true;

            sortingOrder = data.getExtras().getString("sorting_order");

            year = data.getExtras().getInt("year");
            month = data.getExtras().getInt("month");
            day = data.getExtras().getInt("day");

            isArtsChecked =  data.getExtras().getBoolean("is_arts_checked");
            isFashionChecked =  data.getExtras().getBoolean("is_fashion_checked");
            isSportsChecked =  data.getExtras().getBoolean("is_sports_checked");

            // Toast the name to display temporarily on screen
//            Toast.makeText(this, sortingOrder, Toast.LENGTH_SHORT).show();
            onArticleSearch(null);
        } else {
            Log.d("DEBUG", "not okay??");
        }
    }


}
