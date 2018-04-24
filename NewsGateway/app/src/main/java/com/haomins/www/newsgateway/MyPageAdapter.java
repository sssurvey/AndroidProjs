package com.haomins.www.newsgateway;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
	MainActivity ma;
	private long baseId = 0;
	public MyPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public android.support.v4.app.Fragment getItem(int position) {
		return ma.fragments.get(position);
	}

	@Override
	public int getCount() {
		return ma.fragments.size();
	}

	@Override
	public long getItemId(int position) {
		return baseId+position;
	}

	public void notifyChangeInPosition(int n) {
		// shift the ID returned by getItemId outside the range of all previous fragments
		baseId += getCount() + n;
	}
}