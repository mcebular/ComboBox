package com.mc0239.combobox;

import android.content.Context;
import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 11/3/17.
 */

public class ComboBoxAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<Pair<String, String>> entries;
    private Context context;
    private String customEntryText;

    public ComboBoxAdapter(Context context, @NonNull List<Pair<String, String>> items, @NonNull String customEntryText) {
        this.entries = items;
        this.context = context;
        this.customEntryText = customEntryText;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Pair<String, String> getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        Pair<String, String> currentItem = getItem(position);

        TextView text1 = convertView.findViewById(android.R.id.text1);

        if(currentItem == null) {
            text1.setText(customEntryText);
        } else {
            text1.setText(currentItem.first);
        }

        return convertView;
    }

    public static ComboBoxAdapter createFromResources(Context context, @ArrayRes int entries, @ArrayRes int values) {
        String[] arrayEntries = context.getResources().getStringArray(entries);
        String[] arrayValues = context.getResources().getStringArray(values);

        List<Pair<String, String>> items = new ArrayList<>(arrayEntries.length);
        String customText = "Custom:";
        for(int i=0; i<arrayEntries.length; i++) {
            //Log.d(ComboBoxAdapter.class.getSimpleName(), String.format("%s - %s", arrayEntries[i], arrayValues[i]));
            if(arrayValues[i].equals("")) {
                customText = arrayEntries[i];
                items.add(null);
            } else {
                items.add(new Pair<>(arrayEntries[i], arrayValues[i]));
            }
        }

        return new ComboBoxAdapter(context, items, customText);
    }
}
