package com.example.thehumr.windrider.network;

import com.example.thehumr.windrider.database.dto.SegmentContainer;
import com.example.thehumr.windrider.database.table.Segment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.thehumr.windrider.network.RestClient.ACCESS_TOKEN;

/**
 * Created by ondraboura on 17/05/2018.
 */

public interface StravaService {

    public static final String ENDPOINT_starredSegments = "segments/starred";

    @GET(ENDPOINT_starredSegments)
    Call<List<Segment>> getStarredSegments(@Query(ACCESS_TOKEN) String token);
}
