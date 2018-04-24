package com.haomins.www.newsgateway;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.ArrayList;

public class NewsService extends Service {
	MainActivity ma;
	String source;
	ArrayList<Article> articles;
	ServiceReceiver serviceReceiver;
	boolean running = true;
	NewsService newsService;

	public NewsService() {
		newsService = this;
	}

	public void addArticle(Article article){
		articles.add(article);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		articles = new ArrayList<Article>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				serviceReceiver = new ServiceReceiver();
				registerReceiver(serviceReceiver, new IntentFilter(MainActivity.REQUEST_ARTICLES));
				while(running){
					if (articles.size() == 0 || articles.size() != articles.get(0).getTotal()){
						try {
							Thread.sleep(250);
							continue;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						Intent responseIntent = new Intent();
						responseIntent.setAction(MainActivity.RESPONSE_ARTICLES);
						responseIntent.putExtra("articles", articles);
						sendBroadcast(responseIntent);
						articles.clear();
					}
				}

			}
		}).start();
		return START_STICKY;
	}

	class ServiceReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			AsyncArticleLoader asyncArticleLoader = new AsyncArticleLoader(newsService);
			asyncArticleLoader.execute(intent.getStringExtra("source"));
		}
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
