package com.project.mike.news.Services;

/**
 * Created by mike on 07.03.17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.project.mike.news.NewsResultReceiver;
import java.util.Hashtable;
import java.util.Map;
import com.project.mike.news.Services.NewsIntentService;

public class ServiceHelper {
    private int idCounter = 1;
    private final Map<Integer, NewsResultReceiver> resultReceivers = new Hashtable<>();
    private static ServiceHelper instance;

    private ServiceHelper() {
    }

    public static synchronized ServiceHelper getInstance() {
        if (instance == null) {
            instance = new ServiceHelper();
        }
        return instance;
    }

    public int getNews(final Context context, NewsResultListener listener) {
        final NewsResultReceiver receiver = new NewsResultReceiver(idCounter, new Handler());
        receiver.setListener(listener);
        resultReceivers.put(idCounter, receiver);
        Intent intent = new Intent(context, NewsIntentService.class);
        intent.putExtra("receiver", receiver);
        context.startService(intent);
        return idCounter++;
    }

    public void removeListener(final int id) {
        NewsResultReceiver receiver = resultReceivers.remove(id);
        if (receiver != null) {
            receiver.setListener(null);
        }
    }

    public interface NewsResultListener {
        void onNewsResult(int code);
    }
}
