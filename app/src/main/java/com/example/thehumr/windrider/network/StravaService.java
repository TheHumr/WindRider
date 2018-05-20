package com.example.thehumr.windrider.network;

import com.example.thehumr.windrider.database.table.Segment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.thehumr.windrider.network.RestClient.ACCESS_TOKEN;

/**
 * Created by ondraboura on 17/05/2018.
 */

public interface StravaService {

    String ENDPOINT_segment = "segments";

    String ENDPOINT_starredSegments = "segments/starred";

    @GET(ENDPOINT_segment + "/{id}")
    Call<Segment> getSegment(@Path(value = "id") int id, @Query(ACCESS_TOKEN) String token);

    @GET(ENDPOINT_starredSegments)
    Call<List<Segment>> getStarredSegments(@Query(ACCESS_TOKEN) String token);
}
