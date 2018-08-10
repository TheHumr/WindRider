package com.example.thehumr.windrider.fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.thehumr.windrider.R;
import com.example.thehumr.windrider.activity.SegmentDetailActivity;
import com.example.thehumr.windrider.database.dao.SegmentDAO;
import com.example.thehumr.windrider.database.table.Map;
import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.database.table.Weather;
import com.example.thehumr.windrider.event.SegmentLoadEvent;
import com.example.thehumr.windrider.service.SegmentsLoadService;
import com.example.thehumr.windrider.utils.EvaluationUtils;
import com.example.thehumr.windrider.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class SegmentsFragment extends android.support.v4.app.Fragment {

    public static final String ARG_ITEM_ID = "ARG_ITEM_ID";
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

    private Calendar calendarDate;

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

        calendarDate = new GregorianCalendar();
        calendarDate.setTime(new Date());

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
                calendarDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), calendarDate.get(Calendar.HOUR_OF_DAY), calendarDate.get(Calendar.MINUTE));
                openTimePicker();
            }
        });
    }

    private void openTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendarDate.set(calendarDate.get(Calendar.YEAR), calendarDate.get(Calendar.MONTH), calendarDate.get(Calendar.DAY_OF_MONTH), hour, minute);
                sortItems();
                adapter.notifyDataSetChanged();
            }
        }, calendarDate.get(Calendar.HOUR_OF_DAY), calendarDate.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void sortItems() {
        final long dateLong = calendarDate.getTimeInMillis();
        for (Segment segment : segments) {
            segment.calculateEvaluation(dateLong);
        }
        Collections.sort(segments, new Comparator<Segment>() {
            @Override
            public int compare(Segment s1, Segment s2) {
                if (s1.getEvaluation(dateLong) <= s2.getEvaluation(dateLong)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        getMySegments();
    }

    public void getMySegments() {
        Intent intent = new Intent(getActivity(), SegmentsLoadService.class);
        getActivity().startService(intent);
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
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_segment, parent, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Segment segment = segments.get(recyclerView.getChildLayoutPosition(itemView));
                    Intent intent = new Intent(getActivity(), SegmentDetailActivity.class);
                    intent.putExtra(ARG_ITEM_ID, segment.getId());
                    startActivity(intent);
                }
            });
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

            double evaluation = segment.getEvaluation(calendarDate.getTimeInMillis());
            String prefix;
            if (evaluation >= 1){
                prefix = "-";
            } else {
                prefix = "+";
            }

            holder.rankTextView.setText(prefix + String.valueOf((int)(Math.abs(evaluation - 1) * 100)) + "%");
            holder.rankTextView.setTextColor(EvaluationUtils.getEvaluatedColor(getActivity(), evaluation));

            Map map = segment.getMap();
            double segmentAngle = 0;
            if (map != null) {
                segmentAngle = map.getAngle();
            }
            Weather weather = segment.getWeather();
            double windAngle = 0;
            if (weather != null) {
                windAngle = weather.getWindByTime(calendarDate.getTimeInMillis()).getDegree();
            }
            EvaluationUtils.setupEvaluationImageView(getActivity(), holder.arrowImageView, evaluation);
            holder.arrowImageView.setRotation((float) EvaluationUtils.getRelativeWindAngle(segmentAngle, windAngle) + 180);

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
