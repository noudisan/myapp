package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.common.dao.DaoFactory;
import com.example.dao.LocationDao;


public class LocationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return -1;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
