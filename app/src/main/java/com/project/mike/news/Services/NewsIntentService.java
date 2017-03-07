package com.project.mike.news.Services;
import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;
import android.os.Bundle;

import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.News;
import java.io.IOException;
/**
 * Created by mike on 07.03.17.
 */

public class NewsIntentService extends IntentService {
    private static NewsLoader loader;
    public NewsIntentService() {
        super("NewsIntentService");
        Log.d("currTop", "S");
    }

    public final static int RESULT_SUCCESS = 1;
    public final static int RESULT_ERROR = 2;

    @Override
    protected void onHandleIntent(Intent intent) {
        final Bundle data = new Bundle();
        loader = new NewsLoader();
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String currentTopic = Storage.getInstance(this).loadCurrentTopic();
        try {
            java.util.concurrent.TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {

        }
        try {
            News news = loader.loadNews(currentTopic);
            Storage.getInstance(this).saveNews(news);
            data.putString("topic", currentTopic);
            receiver.send(RESULT_SUCCESS,data);
        }
        catch (IOException ex) {
            Log.d("Helper", "LoadError");
        }
    }
}
