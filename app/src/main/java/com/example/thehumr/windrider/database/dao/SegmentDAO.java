package com.example.thehumr.windrider.database.dao;

import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.database.table.Segment_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class SegmentDAO {

    public static Segment getSegment(int segmentID) {
        return SQLite.select().from(Segment.class).where(Segment_Table.id.eq(segmentID)).querySingle();
    }

    public static List<Segment> getAllStarredSegment() {
        return SQLite.select().from(Segment.class).queryList();
    }

    public static void saveWithReferences(Segment segment) {
        if (segment.getMap() != null) {
            segment.getMap().save();
        }
        if (segment.getWeather() != null) {
            segment.getWeather().save();
        }
        segment.save();
    }
}
