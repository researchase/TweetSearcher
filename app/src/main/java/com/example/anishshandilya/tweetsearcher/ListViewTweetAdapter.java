package com.example.anishshandilya.tweetsearcher;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;


public class ListViewTweetAdapter extends ArrayAdapter<Tweet>
{

    private ViewHolder tweetHolder;
    private String searchKey;

    public ListViewTweetAdapter(Context context, List<Tweet> tweets, String searchKey) {

        super(context,R.layout.activity_list_view_tweet_adapter, tweets);

        this.searchKey = searchKey;
    }


    static class ViewHolder
    {
        public ImageView image;
        public TextView text;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {

        Tweet tweet = getItem(position);

        // Making use of ViewHolder pattern
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_view_tweet_adapter, parent, false);

            tweetHolder = new ViewHolder();
            tweetHolder.text = (TextView) view.findViewById(R.id.tvTweet);
            tweetHolder.image = (ImageView) view.findViewById(R.id.ivProfilePic);

            view.setTag(tweetHolder);

        }
        else
            tweetHolder = (ViewHolder) view.getTag();


        if(tweet != null) {


            String searchHit = "";
            String beforeBold = tweet.text;

            // Code to handle highlighting the search keys in the result
            int startOfKey = beforeBold.toLowerCase().indexOf(searchKey.toLowerCase());
            int keyLength = searchKey.length();

            if(startOfKey != -1)
                searchHit = beforeBold.substring(startOfKey, startOfKey + keyLength);

            // bold the term and highlight it with an orange hue
            String afterBold = beforeBold.replaceAll(searchHit, "<font color=\"#FAB937\"><b>" + searchHit + "</b></font>");


            tweetHolder.text.setText(Html.fromHtml(afterBold));

            // use Picasso to retrieve user profile images
            Picasso.with(view.getContext()).load(tweet.user.profileBackgroundImageUrl)
                                           .resize(75,75)
                                           .into(tweetHolder.image);
        }

        return view;
    }

}
