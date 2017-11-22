package com.dicks.goods.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.dicks.goods.DicksApplication;
import com.dicks.goods.R;
import com.dicks.goods.model.VenueListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DickDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final int permissionRequestCode = 1;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.value_name)
    TextView nameText;
    @BindView(R.id.value_verified)
    TextView verifiedText;
    @BindView(R.id.value_url)
    TextView urlText;
    @BindView(R.id.value_ratings)
    TextView ratingText;
    @BindView(R.id.value_address)
    TextView addressText;
    @BindView(R.id.contact_phone)
    ImageView contactPhone;
    @BindView(R.id.contact_facebook)
    ImageView contactFacebook;
    @BindView(R.id.contact_twitter)
    ImageView contactTwitter;
    @BindView(R.id.photos_scroll_view)
    LinearLayout scrollView;
    VenueListModel.Venues venueSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dick_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((DicksApplication) getApplicationContext()).getDicksComponent().inject(this);
        int venueIndex = getIntent().getIntExtra(MainActivity.VENUE_DETAILS, 1);
        venueSelected = ((DicksApplication) getApplication()).getVenueList().get(venueIndex);
        List<VenueListModel.Photos> photosList = venueSelected.getPhotos();
        if (!photosList.isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(photosList.get(0).getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                            Log.d("Glide", "onLoadFailed");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("Glide", "onResourceReady");
                            appBarLayout.setBackground(resource);
                            return false;
                        }
                    }).submit();
        }
        getSupportActionBar().setTitle(venueSelected.getName());

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(venueSelected.isFavourite() ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                venueSelected.setFavourite(!venueSelected.isFavourite());
                fab.setImageResource(venueSelected.isFavourite() ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
            }
        });

        updateDetails();
    }

    private void updateDetails() {
        nameText.setText(venueSelected.getName());
        verifiedText.setText(String.valueOf(venueSelected.getVerified()));
        if(venueSelected.getUrl()!=null) {
            urlText.setText(Html.fromHtml(venueSelected.getUrl()));
        }
        urlText.setLinksClickable(true);
        urlText.setAutoLinkMask(Linkify.ALL);
        ratingText.setText(venueSelected.getRating() + " out of " + venueSelected.getRatingsignals() + " ratings");
        addressText.setText(getAddress(venueSelected.getAddress()));
        contactPhone.setOnClickListener(this);
        contactFacebook.setOnClickListener(this);
        contactTwitter.setOnClickListener(this);
        for (VenueListModel.Photos photo : venueSelected.getPhotos()) {
            scrollView.addView(getImageView(photo.getUrl()));
        }
    }

    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private View getImageView(String url) {
        final ImageView imageView = new ImageView(this);
        imageView.setPadding(5, 5, 5, 5);
        Glide.with(getApplicationContext())
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        Log.d("Glide", "onLoadFailed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "onResourceReady");
                        imageView.setImageDrawable(resource);
                        return false;
                    }
                }).submit();
        return imageView;
    }

    private String getAddress(VenueListModel.Location address) {
        if (address == null) return "";
        return address.getAddress() + "\n" +
                address.getCity() + "\n" +
                address.getState() + "\n" +
                address.getCountry() + "\n" +
                address.getPostalcode();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (venueSelected.getContacts().isEmpty()) return;
        switch (view.getId()) {
            case R.id.contact_phone:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + venueSelected.getContacts().get(0).getPhone()));
                if (ActivityCompat.checkSelfPermission(DickDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DickDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, permissionRequestCode);
                    return;
                }
                startActivity(intent);
                break;
            case R.id.contact_facebook:
                openUrl("http://facebook.com/" + venueSelected.getContacts().get(0).getFacebook());
                break;
            case R.id.contact_twitter:
                openUrl("http://twitter.com/" + venueSelected.getContacts().get(0).getTwitter());
                break;
        }

    }
}
