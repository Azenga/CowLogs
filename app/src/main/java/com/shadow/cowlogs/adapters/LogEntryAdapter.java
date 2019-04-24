package com.shadow.cowlogs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shadow.cowlogs.R;
import com.shadow.cowlogs.models.LogEntry;

import java.util.List;

public class LogEntryAdapter extends BaseAdapter {

    private Context context;
    private List<LogEntry> logEntryList;

    public LogEntryAdapter(Context context, List<LogEntry> logEntryList) {
        this.context = context;
        this.logEntryList = logEntryList;
    }

    @Override
    public int getCount() {
        return logEntryList.size();
    }

    @Override
    public Object getItem(int position) {
        return logEntryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return logEntryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView conditionTV, dateTimeTV, idTV, weightTV, ageTV;

        convertView = LayoutInflater.from(context).inflate(R.layout.single_etry_view_item, parent, false);

        conditionTV = convertView.findViewById(R.id.condition_tv);
        dateTimeTV = convertView.findViewById(R.id.date_time_tv);
        idTV = convertView.findViewById(R.id.id_tv);
        weightTV = convertView.findViewById(R.id.weight_tv);
        ageTV = convertView.findViewById(R.id.age_tv);

        LogEntry entry = logEntryList.get(position);

        conditionTV.setText(entry.getCondition());
        dateTimeTV.setText(entry.getDateTime());
        idTV.setText(String.valueOf(entry.getId()));
        weightTV.setText(String.valueOf(entry.getWeight()));
        ageTV.setText(String.valueOf(entry.getAge()));

        return convertView;
    }
}
