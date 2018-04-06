package com.haomins.www.knowyourgovernment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by haominshi on 3/31/18.
 */

public class OfficialActivity extends AppCompatActivity {
	TextView locationTextView,titleTextView,officeTextView,nameTextView,partyTextView, addressTextView,phoneTextView,emailTextView,webTextView;
	ImageButton photoImageButton, facebookImageButton, youtubeImageButton, googleplusImageButton, twitterImageButton;

	Intent intent;
	Official official;



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_official);

		locationTextView = findViewById(R.id.OA_location_info);
		officeTextView = findViewById((R.id.OA_office_title));
		nameTextView = findViewById(R.id.OA_name);
		partyTextView = findViewById(R.id.OA_party);
		photoImageButton = findViewById(R.id.OA_portait_button);
		addressTextView = findViewById(R.id.OA_address_detail);
		phoneTextView = findViewById(R.id.OA_phone_detail);
		emailTextView = findViewById(R.id.OA_email_detail);
		webTextView = findViewById(R.id.OA_website_detail);
		facebookImageButton = findViewById(R.id.OA_facebook_b);
		youtubeImageButton = findViewById(R.id.OA_youtube_b);
		twitterImageButton = findViewById(R.id.OA_twitter_b);
		googleplusImageButton = findViewById(R.id.OA_googleplus_b);


		intent = getIntent();


		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		locationTextView.setText(intent.getCharSequenceExtra("location"));

		//////////
		if(official.getOfficeName()!= null){
			officeTextView.setText(official.getOfficeName());
		}
		if(official.getName()!=null){
			nameTextView.setText(official.getName());
		}
		if(official.getAddress()!=null){
			addressTextView.setText(official.getAddress());
		}
		if(official.getPhone()!=null){
			phoneTextView.setText(official.getPhone());
		}
		if(official.getUrl()!=null){
			webTextView.setText(official.getUrl());
		}
		if(official.getEmail()!=null){
			emailTextView.setText(official.getEmail());
		}
		//////////


		//deal with party
		if (official.getParty() != null) {
			switch (official.getParty()) {
				case "Republican":

					getWindow().getDecorView().setBackgroundColor(Color.RED);
					partyTextView.setText('(' + official.getParty() + ')');

					break;
				case "Democratic":

					partyTextView.setText('(' + official.getParty() + ')');
					getWindow().getDecorView().setBackgroundColor(Color.BLUE);

					break;
				default:

					getWindow().getDecorView().setBackgroundColor(Color.BLACK);

					break;
			}
		} else {

			getWindow().getDecorView().setBackgroundColor(Color.BLACK);

		}
		//////////
		//deal with icons
		if (official.getChannel() == null) {
			facebookImageButton.setVisibility(View.INVISIBLE);
			twitterImageButton.setVisibility(View.INVISIBLE);
			youtubeImageButton.setVisibility(View.INVISIBLE);
			googleplusImageButton.setVisibility(View.INVISIBLE);

		} else {
			if (official.getChannel().getFacebook() == null) {
				facebookImageButton.setVisibility(View.INVISIBLE);
			}
			if (official.getChannel().getTwitter() == null){
				twitterImageButton.setVisibility(View.INVISIBLE);
			}
			if (official.getChannel().getGoogleplus() == null){
				googleplusImageButton.setVisibility(View.INVISIBLE);
			}
			if (official.getChannel().getYoutube() == null){
				youtubeImageButton.setVisibility(View.INVISIBLE);
			}
		}

		if (official.getPhotoUrl() != null){
			Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
				@Override
				public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
					final String changedUrl = official.getPhotoUrl().replace("http:", "https:");
					picasso.load(changedUrl) .error(R.drawable.brokenimage)
							.placeholder(R.drawable.placeholder) .into(photoImageButton);
				}
			}).build();

			picasso.load(official.getPhotoUrl()) .error(R.drawable.brokenimage)
					.placeholder(R.drawable.placeholder) .into(photoImageButton);
		} else {
			Picasso.get().load(official.getPhotoUrl()) .error(R.drawable.brokenimage).placeholder(R.drawable.missingimage) .into(photoImageButton);
		}

		//generator();

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}


	public void phtoPutton(View v){
		if (official.getPhotoUrl() == null){
			return;
		}
		Intent intent = new Intent(this, PhotoActivity.class);
		intent.putExtra("official", official);
		intent.putExtra("location", locationTextView.getText());
		startActivityForResult(intent, 1);
	}
	////////// deal with icon clicks, add later, code provided
	public void facbookClicked(View v){
		String FACEBOOK_URL = "https://www.facebook.com/" + official.getChannel().getFacebook();
		String urlToUse;
		PackageManager packageManager = getPackageManager();
		try {
			int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode; if (versionCode >= 3002850) { //newer versions of fb app
				urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL; } else { //older versions of fb app
				urlToUse = "fb://page/" + official.getChannel().getFacebook(); }
		} catch (PackageManager.NameNotFoundException e) { urlToUse = FACEBOOK_URL; //normal web url
		}
		Intent facebookIntent = new Intent(Intent.ACTION_VIEW); facebookIntent.setData(Uri.parse(urlToUse)); startActivity(facebookIntent);
	}

	public void googleplusClicked(View v){
		String name = official.getChannel().getGoogleplus();
		Intent intent = null;
		try {
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
			intent.putExtra("customAppUri", name);
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + name)));
		}
	}

	public void youtubeClicked(View v){
		String name = official.getChannel().getYoutube();
		Intent intent = null;
		try {
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setPackage("com.google.android.youtube");
			intent.setData(Uri.parse("https://www.youtube.com/" + name));
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
		}
	}
	public void twitterClicked(View v){
		Intent intent = null;
		String name = official.getChannel().getTwitter();
		try {
			// get the Twitter app if possible
			getPackageManager().getPackageInfo("com.twitter.android", 0);
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name)); intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		} catch (Exception e) {
			// no Twitter app, revert to browser
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name)); }
		startActivity(intent);
	}
	//////////



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//public void generator(){}
}



