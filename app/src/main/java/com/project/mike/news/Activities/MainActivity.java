package com.project.mike.news.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;
import android.os.StrictMode;
import com.project.mike.news.R;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.News;
import android.content.Intent;
import android.content.IntentFilter;
import com.project.mike.news.NewsResultListener;
import com.project.mike.news.Services.ServiceHelper;

public class MainActivity extends AppCompatActivity implements NewsResultListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    private int requestId;
    private TextView resultTextView;
    private Storage storage;

    static {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.news_text);
        TextView topicTextView = (TextView) findViewById(R.id.topic_text);
        storage = Storage.getInstance(this);
        String currentTopic = storage.loadCurrentTopic();
        if (currentTopic == "")
            currentTopic = "it";
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
            requestId = ServiceHelper.getInstance().getNews(this, storage.loadCurrentTopic(), this);
        }
        else {
            News currentNews = storage.getLastSavedNews();
            resultTextView.setText(currentNews.getTitle());
        }
    }

    private void startRefreshService() {
        if (requestId == 0) {
            requestId = ServiceHelper.getInstance().getNews(this, storage.loadCurrentTopic(), this);
        }
        else
            Toast.makeText(this, "There is pending request", Toast.LENGTH_SHORT).show();
    }

    private void stopRefreshService() {
        //Intent intent = new Intent(this, WeatherIntentService.class);
        //startActivity(intent);
    }

    @Override
    public void onNewsResult(String currentTopic) {
        News currentNews = storage.getLastSavedNews();
        resultTextView.setText(currentNews.getTitle());
        requestId = 0;
    }
}
