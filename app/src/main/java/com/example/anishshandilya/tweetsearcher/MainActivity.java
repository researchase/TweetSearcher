/*
 * TwitterAPIMEAndroidOAuthSample.java
 * 06/10/2011
 * Twitter API Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.example.anishshandilya.tweetsearcher;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
{

    private ArrayList<Tweet> foundTweets;
    private TwitterSession session;
    private ListView lvTweets;
    private ListViewTweetAdapter adapter;
    private String searchKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foundTweets = new ArrayList<Tweet>();
        Button searchButton = (Button) findViewById(R.id.btnSearch);
        final EditText etSearchKey = (EditText) findViewById(R.id.etKey);
        lvTweets = (ListView) findViewById(R.id.lvTweets);


        // Twitter Authentication
        session = Twitter.getSessionManager().getActiveSession();

    searchButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchKey(etSearchKey.getText().toString());
        }
    });


    }

    // method that searches for key through Twitter, using Twitter API calls via Fabric
    private void searchKey(String key)
    {
        TwitterApiClient twitterApiClient = Twitter.getApiClient(session);
        SearchService searchService = twitterApiClient.getSearchService();
        searchKey = key;

        // The search query to find the key
        searchService.tweets(key, null, null, null, null ,null ,null, null, null, null, new Callback<Search>() {

            @Override
            public void success(Result<Search> result) {

                List<Tweet> temp = result.data.tweets;
                foundTweets.clear();

                for(int i = 0; i < temp.size(); i++)
                {
                    foundTweets.add(temp.get(i));
                }

                adapter = new ListViewTweetAdapter(getApplicationContext(), foundTweets, searchKey);

                lvTweets.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            public void failure(TwitterException exception) {
                //Do something on failure
            }
        });
    }

}
