package com.example.thehumr.windrider.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thehumr.windrider.R;
import com.example.thehumr.windrider.database.dao.SegmentDAO;
import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.event.SegmentLoadEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SegmentsFragment extends android.support.v4.app.Fragment {

    SegmentAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<Segment> segments;

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

        }

        @Override
        public int getItemCount() {
            return segments.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.nameTextView)
            TextView nameTextView;

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
