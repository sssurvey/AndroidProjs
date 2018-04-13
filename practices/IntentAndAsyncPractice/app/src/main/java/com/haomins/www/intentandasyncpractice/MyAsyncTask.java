package com.haomins.www.intentandasyncpractice;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Long, Integer, String>{
	MainActivity ma;

	public MyAsyncTask(MainActivity ma){
		this.ma = ma;
	}


	@Override
	protected String doInBackground(Long... longs){
		long a = longs[0];
		try {
			for (int i = 0; i < a; i++) {
				Thread.sleep(1000); // 1 second in millis

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "Done";
	}

	@Override
	protected void onPostExecute(String s) {
		Toast.makeText(ma, "finished", Toast.LENGTH_SHORT).show();
		TextView tv = ma.findViewById(R.id.main_async);
		tv.setText(s);
		super.onPostExecute(s);
	}
}
