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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button firstTopicButton = (Button) findViewById(R.id.first_topic_button);
        firstTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Storage.getInstance(MenuActivity.this).saveCurrentTopic("auto");
                startMainActivity();
            }
        });

        Button secoundTopicButton = (Button) findViewById(R.id.secound_topic_button);
        secoundTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Storage.getInstance(MenuActivity.this).saveCurrentTopic("it");
                startMainActivity();
            }
        });

        Button thirdTopicButton = (Button) findViewById(R.id.third_topic_button);
        thirdTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Storage.getInstance(MenuActivity.this).saveCurrentTopic("health");
                startMainActivity();
            }
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
    }
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
