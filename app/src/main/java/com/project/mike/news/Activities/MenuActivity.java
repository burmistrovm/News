package com.project.mike.news.Activities;

/**
 * Created by mike on 07.03.17.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.project.mike.news.R;
import android.content.Intent;
import android.content.IntentFilter;
import ru.mail.weather.lib.Storage;


public class MenuActivity extends AppCompatActivity {
    private static Storage storage;

    private final View.OnClickListener onFirstClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            storage.saveCurrentTopic("auto");
            finish();
        }
    };

    private final View.OnClickListener onSecoundClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            storage.saveCurrentTopic("it");
            finish();
        }
    };

    private final View.OnClickListener onThirdClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            storage.saveCurrentTopic("health");
            finish();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        storage = Storage.getInstance(MenuActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button firstTopicButton = (Button) findViewById(R.id.first_topic_button);
        firstTopicButton.setOnClickListener(onFirstClick);
        Button secoundTopicButton = (Button) findViewById(R.id.secound_topic_button);
        secoundTopicButton.setOnClickListener(onSecoundClick);

        Button thirdTopicButton = (Button) findViewById(R.id.third_topic_button);
        thirdTopicButton.setOnClickListener(onThirdClick);


        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
    }
}
