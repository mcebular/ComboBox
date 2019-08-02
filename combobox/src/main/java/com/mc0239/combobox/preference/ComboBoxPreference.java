package com.mc0239.combobox.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.preference.DialogPreference;

import com.mc0239.combobox.R;

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
    }

    public void setValue(String value) {
        this.value = value;

        persistString(value);
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

    @Override
    protected void onSetInitialValue(@Nullable Object defaultValue) {
        value = this.getPersistedString((String) defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        if(a.getString(index) != null)
            return a.getString(index);
        else return "";
    }

    public int getArrayEntries() {
        return arrayEntries;
    }

    public int getArrayValues() {
        return arrayValues;
    }

    public int getInputType() {
        return etInputType;
    }

    public String getHint() {
        return etHint;
    }
}
