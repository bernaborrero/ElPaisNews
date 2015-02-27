package com.bernabeborrero.elpaisnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.bernabeborrero.elpaisnews.model.NewsItem;


public class DetailActivity extends Activity {
    public static final String ITEM = "item";
    private ShareActionProvider mShareActionProvider;
    private NewsItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        item = (NewsItem) getIntent().getSerializableExtra(ITEM);
        ImageView image = (ImageView) findViewById(R.id.detailImage);
        TextView link = (TextView) findViewById(R.id.detailLink);

        image.setImageBitmap(item.getImage());
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((TextView) findViewById(R.id.detailTitle)).setText(item.getTitle());
        ((TextView) findViewById(R.id.detailContent)).setText(Html.fromHtml(item.getContent()));
        link.setText(
                Html.fromHtml(getString(R.string.read_more) + " <a href=\"" + item.getLink() + "\">" + item.getLink() + "</a>")
        );
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        mShareActionProvider.setShareIntent(getShareIntent());
        return true;
    }

    private Intent getShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, item.getTitle() + " " + item.getLink());
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
