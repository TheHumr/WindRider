package com.example.thehumr.windrider.database.dao;

import com.example.thehumr.windrider.database.table.Segment;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class SegmentDAO {

    public static List<Segment> getAllStarredSegment() {
        return SQLite.select().from(Segment.class).queryList();
    }

    public static void saveWithReferences(Segment segment) {
        if (segment.getMap() != null) {
            segment.getMap().save();
        }
        segment.save();
    }
}
