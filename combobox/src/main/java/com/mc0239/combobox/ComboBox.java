package com.mc0239.combobox;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * Created by martin on 11/3/17.
 */

public class ComboBox extends LinearLayout {

    public interface OnValueChangedListener {
        void onValueChanged(String value, boolean isCustom);
    }

    private EditText editText;
    private Spinner spinner;
    private ComboBoxAdapter adapter;
    private OnValueChangedListener onValueChangedListener;

    public ComboBox(Context context) {
        super(context);
    }

    public ComboBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.combo_box, this);

        editText = findViewById(R.id.coolEditText);
        spinner = findViewById(R.id.coolSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem() != null) {
                    editText.setVisibility(GONE);
                    if(onValueChangedListener != null) onValueChangedListener.onValueChanged(((Pair<String, String>) adapterView.getSelectedItem()).second, false);
                } else {
                    editText.setVisibility(VISIBLE);
                    if(onValueChangedListener != null) onValueChangedListener.onValueChanged(editText.getText().toString(), true);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(onValueChangedListener != null) onValueChangedListener.onValueChanged(editable.toString(), true);
            }
        });
    }

    public ComboBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ComboBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    /**
     * Returns currently selected value. If selected item is an Entry-Value pair, it
     * returns pair's value; otherwise it returns the custom input value.
     * @return Value of an Entry-Value pair, or a custom input value.
     */
    public String getSelectedValue() {
        if(spinner.getSelectedItem() != null) {
            return ((Pair<String, String>) spinner.getSelectedItem()).second;
        } else {
            return editText.getText().toString();
        }
    }

    /**
     * Returns currently selected entry. If selected item is an Entry-Value pair, it
     * returns pair's entry; <b>otherwise it returns the custom input value</b>, just like the
     * {@link #getSelectedValue()} method.
     * @return Entry of an Entry-Value pair, or a custom input value.
     */
    public String getSelectedEntry() {
        if(spinner.getSelectedItem() != null) {
            return ((Pair<String, String>) spinner.getSelectedItem()).first;
        } else {
            return editText.getText().toString();
        }
    }

    /**
     * Sets the value of the ComboBox. If value is found in ComboBox's adapter, then the selected
     * item is set to that Entry-Value pair. Otherwise (that is, if a custom input entry position is
     * found in adapter) set the selected item to custom value and fill the EditText with the given
     * value.
     * @param value Value to set ComboBox to.
     * @return true, if value was found in adapter and ComboBox's selection was set to Entry-Value
     * pair;<br />false, if value was not found in adapter and custom value was set.
     */
    public boolean setValue(String value) {
        int customFieldPosition = -1;
        for(int i=0; i < adapter.getCount(); i++) {
            Pair<String, String> p = adapter.getItem(i);
            if(p == null) {
                if(customFieldPosition == -1) customFieldPosition = i;
            } else {
                if(p.second.equals(value)) {
                    spinner.setSelection(i);
                    return true;
                }
            }
        }
        if(customFieldPosition != -1) spinner.setSelection(customFieldPosition);
        editText.setText(value);
        return false;
    }

    /**
     * @see Spinner#setAdapter(SpinnerAdapter)
     */
    public void setAdapter(ComboBoxAdapter adapter) {
        this.adapter = adapter;
        spinner.setAdapter(adapter);
    }

    /**
     * @see EditText#setInputType(int)
     */
    public void setInputType(int inputType) {
        editText.setInputType(inputType);
    }

    /**
     * @see EditText#setHint(CharSequence)
     */
    public void setHint(String hint) {
        editText.setHint(hint);
    }
}
