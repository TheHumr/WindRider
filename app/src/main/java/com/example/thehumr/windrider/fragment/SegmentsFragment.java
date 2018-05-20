package com.example.thehumr.windrider.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thehumr.windrider.R;
import com.example.thehumr.windrider.database.dao.SegmentDAO;
import com.example.thehumr.windrider.database.table.Map;
import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.database.table.Weather;
import com.example.thehumr.windrider.event.SegmentLoadEvent;
import com.example.thehumr.windrider.utils.EvaluationUtils;
import com.example.thehumr.windrider.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class SegmentsFragment extends android.support.v4.app.Fragment {

    public static final double WIND_ANGLE = 270;

    SegmentAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.calendarView)
    HorizontalCalendarView calendarView;
    @BindView(R.id.settingsPanel)
    LinearLayout settingsPanel;
    @BindView(R.id.settingsPanelImageView)
    ImageView settingsPanelImageView;

    List<Segment> segments;
    HorizontalCalendar horizontalCalendar;

    private boolean settingsPanelShown = true;
    private int day = 0;

    public SegmentsFragment() {
        // Required empty public constructor
    }

    public static SegmentsFragment newInstance() {
        SegmentsFragment fragment = new SegmentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_segments, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadSegments();

        adapter = new SegmentAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        setupCalendar();
    }

    private void setupCalendar() {
        Calendar startDate = Calendar.getInstance();

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 5);
        horizontalCalendar = new HorizontalCalendar.Builder(getActivity(), calendarView.getId())
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                day = position - 2;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.settingsPanelImageView)
    public void onSettingsImageViewClicked() {
        if (settingsPanelShown) {
            settingsPanelImageView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_down));
            Animation bottomDown = AnimationUtils.loadAnimation(getContext(),
                    R.anim.bottom_down);
            bottomDown.setFillEnabled(true);
            bottomDown.setFillAfter(true);
            settingsPanel.startAnimation(bottomDown);
        } else {
            settingsPanelImageView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_keyboard_arrow_up));
            Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                    R.anim.bottom_up);
            bottomUp.setFillEnabled(true);
            bottomUp.setFillAfter(true);
            settingsPanel.startAnimation(bottomUp);
        }
        settingsPanelShown = !settingsPanelShown;
    }

    class SegmentAdapter extends RecyclerView.Adapter<SegmentAdapter.ItemViewHolder> {

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_segment, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SegmentAdapter.ItemViewHolder holder, int position) {

            Segment segment = segments.get(position);

            holder.nameTextView.setText(segment.getName());
            holder.locationTextView.setText(segment.getLocation());
            holder.distanceTextView.setText(StringUtils.formatDistanceKm(segment.getDistance()));
            holder.elevationGainTextView.setText(StringUtils.formatDistance(segment.getTotalElevationGain()));
            holder.averageGradeTextView.setText(StringUtils.formatGrade(segment.getAverageGrade()));

            Map map = segment.getMap();
            double segmentAngle = 0;
            if (map != null) {
                segmentAngle = map.getAngle();
            }
            Weather weather = segment.getWeather();
            double windAngle = 0;
            if (weather != null) {
                windAngle = weather.getWeathers().get(day * 7).getWind().getDegree();
            }
            EvaluationUtils.setupEvaluationImageView(getActivity(), holder.arrowImageView, segmentAngle, windAngle);

        }

        @Override
        public int getItemCount() {
            return segments.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.nameTextView)
            TextView nameTextView;
            @BindView(R.id.locationTextView)
            TextView locationTextView;
            @BindView(R.id.distanceTextView)
            TextView distanceTextView;
            @BindView(R.id.elevationGainTextView)
            TextView elevationGainTextView;
            @BindView(R.id.averageGradeTextView)
            TextView averageGradeTextView;
            @BindView(R.id.rankTextView)
            TextView rankTextView;
            @BindView(R.id.arrowImageView)
            ImageView arrowImageView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void onClick(View view) {

            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SegmentLoadEvent event) {
        loadSegments();
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), event.getState().toString(), Toast.LENGTH_SHORT).show();
    }

    public void loadSegments() {
        segments = SegmentDAO.getAllStarredSegment();
    }
}
