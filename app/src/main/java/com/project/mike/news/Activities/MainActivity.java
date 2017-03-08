package com.project.mike.news.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.text.SimpleDateFormat;
import android.widget.TextView;
import android.widget.Button;

import com.project.mike.news.NewsResultReceiver;
import com.project.mike.news.R;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.News;
import android.content.Intent;
import android.content.IntentFilter;
import com.project.mike.news.Services.ServiceHelper.NewsResultListener;
import com.project.mike.news.Services.ServiceHelper;
import java.util.Locale;
import ru.mail.weather.lib.Scheduler;
import com.project.mike.news.Services.NewsIntentService;

public class MainActivity extends AppCompatActivity implements NewsResultListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    private int requestId;
    private static Storage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView topicTextView = (TextView) findViewById(R.id.topic_text);
        storage = Storage.getInstance(this);
        String currentTopic = storage.loadCurrentTopic();
        if (currentTopic == "") {
            currentTopic = "it";
            storage.saveCurrentTopic("it");
        }
        topicTextView.setText(currentTopic);
        Button startActivity = (Button) findViewById(R.id.btn_start_activity);
        startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMenuActivity();
            }
        });

        Button refreshNews = (Button) findViewById(R.id.btn_refresh);
        refreshNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        Button startService = (Button) findViewById(R.id.btn_start_refresh);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRefreshService();
            }
        });

        Button stopService = (Button) findViewById(R.id.btn_stop_refresh);
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRefreshService();
            }
        });

        refresh();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView topicTextView = (TextView) findViewById(R.id.topic_text);
        String currentTopic = storage.loadCurrentTopic();
        if (currentTopic == "") {
            currentTopic = "it";
            storage.saveCurrentTopic("it");
        }
        topicTextView.setText(currentTopic);
    }

    @Override
    protected void onDestroy() {
        ServiceHelper.getInstance().removeListener(requestId);
        super.onStop();
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void refresh() {
        if (requestId == 0) {
            requestId = ServiceHelper.getInstance().getNews(this, this);
        }
        else {

        }
    }

    private void startRefreshService() {
        Intent intent = new Intent(this, NewsIntentService.class);
        Scheduler scheduler = Scheduler.getInstance();
        scheduler.schedule(this, intent, 60*1000L);
    }

    private void stopRefreshService() {
        Intent intent = new Intent(this, NewsIntentService.class);
        Scheduler scheduler = Scheduler.getInstance();
        scheduler.unschedule(this, intent);
    }

    @Override
    public void onNewsResult(int code) {
        if (code == NewsIntentService.RESULT_SUCCESS) {
            News currentNews = storage.getLastSavedNews();
            TextView titleTextView = (TextView) findViewById(R.id.title_text);
            titleTextView.setText(currentNews.getTitle());
            TextView dateTextView = (TextView) findViewById(R.id.date_text);
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            dateTextView.setText(date.format(currentNews.getDate()));
            TextView contentTextView = (TextView) findViewById(R.id.content_text);
            contentTextView.setText(currentNews.getBody());
        }
        requestId = 0;
    }
}
