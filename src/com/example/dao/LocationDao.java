package com.example.dao;

import android.database.sqlite.SQLiteOpenHelper;
import com.common.dao.BaseDAO;
import com.example.model.Location;

import java.util.List;

public class LocationDao extends BaseDAO<Location, Integer> {

    public LocationDao(SQLiteOpenHelper sqliteOpenHelper) {
        super(sqliteOpenHelper);
    }

    public Location getLastLocation() {
        List<Location> locationList = query(null, null, null, null, null, "LOCATION_TIME DESC", "0, 1");
        if (locationList == null || locationList.size() == 0) {
            return null;
        }

        return locationList.get(0);
    }
}
