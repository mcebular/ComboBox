package com.mc0239.combobox.Preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mc0239.combobox.ComboBox;
import com.mc0239.combobox.ComboBoxAdapter;
import com.mc0239.combobox.R;

/**
 * Created by martin on 11/3/17.
 */

public class ComboBoxPreference extends DialogPreference {

    private String value;

    private int arrayEntries;
    private int arrayValues;

    private int etInputType;
    private String etHint;

    public ComboBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ComboBoxPreference, 0, 0);

        arrayEntries = a.getResourceId(R.styleable.ComboBoxPreference_entries, 0);
        arrayValues = a.getResourceId(R.styleable.ComboBoxPreference_entryValues, 0);
        etInputType = a.getInt(R.styleable.ComboBoxPreference_android_inputType, 0);
        etHint = a.getString(R.styleable.ComboBoxPreference_android_hint);

        setDialogLayoutResource(R.layout.combo_box_preference);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if(positiveResult) {
            // save new value
            persistString(value);
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if(restorePersistedValue) {
            value = this.getPersistedString("");
        } else {
            value = (String) defaultValue;
            persistString(value);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        if(a.getString(index) != null)
            return a.getString(index);
        else return "";
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        ComboBox comboBox = view.findViewById(R.id.comboBox);

        if(comboBox != null) {
            comboBox.setAdapter(ComboBoxAdapter.createFromResources(getContext(), arrayEntries, arrayValues));
            comboBox.setValue(getPersistedString(""));
            comboBox.setOnValueChangedListener(new ComboBox.OnValueChangedListener() {
                @Override
                public void onValueChanged(String value, boolean isCustom) {
                    ComboBoxPreference.this.value = value;
                }
            });

            comboBox.setInputType(etInputType);
            comboBox.setHint(etHint);
        } else Log.wtf(getClass().getSimpleName(), "ComboBox was not found when Dialog was bound!");

    }

    public String getValue() {
        return value;
    }

    public String getEntry() {
        String[] entries = getContext().getResources().getStringArray(arrayEntries);
        String[] values = getContext().getResources().getStringArray(arrayValues);
        for(int i=0; i<entries.length; i++) {
            if(values[i].equals(value)) return entries[i];
        }
        return value;
    }
}
