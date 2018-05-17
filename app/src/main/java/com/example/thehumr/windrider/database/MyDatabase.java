package com.example.thehumr.windrider.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by ondraboura on 28/12/2017.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";

    public static final int VERSION = 1;
}
