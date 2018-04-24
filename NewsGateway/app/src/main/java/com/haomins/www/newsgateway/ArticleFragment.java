package com.haomins.www.newsgateway;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class ArticleFragment extends Fragment implements Serializable {
	public static final android.support.v4.app.Fragment newInstance(Article article) {
		ArticleFragment f = new ArticleFragment();
		Bundle bdl = new Bundle(1);
		bdl.putSerializable("article", article);
		f.setArguments(bdl);
		return f;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_article, container, false);
		TextView titleTV, authorTV, timeTV, contentTV, indexTV;
		final ImageButton imageButton = v.findViewById(R.id.frag_imageB);
		titleTV = v.findViewById(R.id.frag_title);
		authorTV = v.findViewById(R.id.frag_author);
		timeTV = v.findViewById(R.id.frag_time);
		contentTV = v.findViewById(R.id.frag_content);
		indexTV = v.findViewById(R.id.frag_page);

		final Article article;
		if (savedInstanceState == null) {
			article = (Article) getArguments().getSerializable("article");
		} else {
			article = (Article) savedInstanceState.getSerializable("article");
		}

		timeTV.setText(article.getTime().split("T")[0]);
		titleTV.setText(article.getTitle());
		authorTV.setText(article.getAuthor());
		contentTV.setText(article.getDescription());
		indexTV.setText("" + article.getIndex() + " of " + article.getTotal());

		if (article.getURL_IMAGE() != null) {
			Picasso picasso = new Picasso.Builder(v.getContext()).listener(new Picasso.Listener() {
				@Override
				public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
					final String changedUrl = article.getURL_IMAGE().replace("http:", "https:");
					picasso.load(changedUrl).error(R.drawable.broken)
							.placeholder(R.drawable.load).into(imageButton);
				}
			}).build();

			picasso.load(article.getURL_IMAGE()).error(R.drawable.broken)
					.placeholder(R.drawable.load).into(imageButton);
		} else {
			//modified -------------
			Picasso.get().load(article.getURL_IMAGE()).error(R.drawable.broken).placeholder(R.drawable.noimage);
		}

		final Intent intent = new Intent((Intent.ACTION_VIEW));
		intent.setData(Uri.parse(article.getURL()));
		titleTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intent);
			}
		});
		contentTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intent);
			}
		});
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intent);
			}
		});

		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("article", getArguments().getSerializable("article"));
	}
}

