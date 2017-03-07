package com.project.mike.news;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.project.mike.news.Services.NewsIntentService;
import com.project.mike.news.NewsResultListener;
import com.project.mike.news.Services.ServiceHelper;

import ru.mail.weather.lib.Storage;

/**
 * Created by mike on 07.03.17.
 */

public class NewsResultReceiver extends ResultReceiver {
    private final int requestId;
    private NewsResultListener newsListener;

    public NewsResultReceiver(int requestId, final Handler handler) {
        super(handler);
        this.requestId = requestId;
    }

    public void setListener(final NewsResultListener listener) {
        newsListener = listener;
    }

    @Override
    protected void onReceiveResult(final int resultCode, final Bundle resultData) {
        int success = 1;
        if (newsListener != null) {
            String currentTopic = (resultData.getString("topic"));
            newsListener.onNewsResult(currentTopic);
        }
        ServiceHelper.getInstance().removeListener(requestId);
    }
}
