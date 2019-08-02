package com.mc0239.combobox.preference;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.preference.PreferenceDialogFragmentCompat;

import com.mc0239.combobox.ComboBox;
import com.mc0239.combobox.ComboBoxAdapter;
import com.mc0239.combobox.R;

public class ComboBoxPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {

    private ComboBox comboBox;
    private String comboBoxValue;

    public static ComboBoxPreferenceDialogFragmentCompat newInstance(String key) {
        ComboBoxPreferenceDialogFragmentCompat fragment = new ComboBoxPreferenceDialogFragmentCompat();
        Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    private ComboBoxPreference getComboBoxPreference() {
        return (ComboBoxPreference) getPreference();
    }

    @Override
    protected View onCreateDialogView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.combo_box_preference, null);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        comboBox = view.findViewById(R.id.comboBox);
        comboBoxValue = comboBox.getSelectedValue();

        if(comboBox != null) {
            comboBox.setAdapter(ComboBoxAdapter.createFromResources(view.getContext(),
                    getComboBoxPreference().getArrayEntries(),
                    getComboBoxPreference().getArrayValues()));
            comboBox.setValue(getComboBoxPreference().getValue());
            comboBox.setOnValueChangedListener(new ComboBox.OnValueChangedListener() {
                @Override
                public void onValueChanged(String value, boolean isCustom) {
                    comboBoxValue = value;
                }
            });

            comboBox.setInputType(getComboBoxPreference().getInputType());
            comboBox.setHint(getComboBoxPreference().getHint());
        } else Log.wtf(getClass().getSimpleName(), "ComboBox was not found when Dialog was bound!");
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            if (getComboBoxPreference().callChangeListener(comboBoxValue)) {
                getComboBoxPreference().setValue(comboBoxValue);
            }
        }
    }

}
