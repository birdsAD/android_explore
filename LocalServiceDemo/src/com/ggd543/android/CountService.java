package com.ggd543.android;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * User: ¡ı”¿Ω°
 * Date: 11-12-27
 * Time: œ¬ŒÁ5:36
 * To change this template use File | Settings | File Templates.
 */
public class CountService extends Service {
    private boolean isStop;
    private int count;
    private static final String TAG = "CountService";

    private class ServiceBinder extends Binder implements ICountService {
        @Override
        public int getCounter() {
            return CountService.this.getCounter();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind...");
        return new ServiceBinder();
    }

    private synchronized void increseCounter() {
        count++;
    }

    private synchronized int getCounter() {
        return count;
    }

    private synchronized void setCounter(int c) {
        this.count = c;
    }

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
        Log.v(TAG, "onCreate ... ");
        setCounter(0);
        isStop = false;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        Log.v(TAG, "onStart...");

        new Thread(new Runnable() {
            private String TAG = "CounterThread";

            @Override
            public void run() {
                while (!isStop) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    increseCounter();
                    Log.v(TAG, "ount is " + getCounter());
                }
            }
        }).start();
        super.onStart(intent, startId);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "OnUnbind... ");
        return super.onUnbind(intent);    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(TAG, "onRebind");
        super.onRebind(intent);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy...");
        isStop = true;
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
