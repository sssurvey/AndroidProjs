package com.haomins.www.newsgateway;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;


public class ArticleFragment extends Fragment implements Serializable{
	public static final android.support.v4.app.Fragment newInstance(Article article){
		ArticleFragment f = new ArticleFragment();
		Bundle bdl = new Bundle(1);
		bdl.putSerializable("article", article);
		f.setArguments(bdl);
		return f;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
