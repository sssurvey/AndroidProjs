package com.haomins.www.knowyourgovernment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/**
 * Created by haominshi on 3/31/18.
 */

public class PhotoActivity extends AppCompatActivity {

	Intent intent;
	Official official;
	TextView locationTextView;
	TextView officeTextView;
	TextView nameTextView;
	ImageView photoView;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_activity);

		locationTextView = findViewById(R.id.PA_location_info);
		officeTextView = findViewById(R.id.PA_Job_title);
		nameTextView = findViewById(R.id.PA_name);
		photoView = findViewById(R.id.PA_portrait);

		intent = getIntent();
		official = (Official) intent.getSerializableExtra("official");
		//CharSequence ch = intent.getCharSequenceExtra("location");
		locationTextView.setText(intent.getCharSequenceExtra("location"));
		officeTextView.setText(official.getOfficeName());
		nameTextView.setText(official.getName());

		if (official.getParty() != null) {
			switch (official.getParty()) {
				case "Republican":
					getWindow().getDecorView().setBackgroundColor(Color.RED);
					break;
				case "Democratic":
					getWindow().getDecorView().setBackgroundColor(Color.BLUE);
					break;
				default:
					getWindow().getDecorView().setBackgroundColor(Color.BLACK);
					break;
			}
		} else getWindow().getDecorView().setBackgroundColor(Color.BLACK);

		if (official.getPhotoUrl() != null){
			Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
				@Override
				public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
					final String changedUrl = official.getPhotoUrl().replace("http:", "https:");
					picasso.load(changedUrl) .error(R.drawable.brokenimage)
							.placeholder(R.drawable.placeholder) .into(photoView);
				}
			}).build();

			picasso.load(official.getPhotoUrl()) .error(R.drawable.brokenimage)
					.placeholder(R.drawable.placeholder) .into(photoView);
		} else {
			Picasso.get().load(official.getPhotoUrl()) .error(R.drawable.brokenimage).placeholder(R.drawable.missingimage) .into(photoView);}
	}
}
