package com.mc0239.spinnerwithcustomvalue;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.mc0239.combobox.preference.ComboBoxPreference;
import com.mc0239.combobox.preference.ComboBoxPreferenceDialogFragmentCompat;

import java.util.Map;

/**
 * Created by martin on 11/3/17.
 */

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
            loadSummary(entry.getKey());
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // TODO
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        if (preference instanceof ComboBoxPreference) {
            ComboBoxPreferenceDialogFragmentCompat dialog = ComboBoxPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            dialog.setTargetFragment(this, 0);
            dialog.show(getFragmentManager(), "hehe xd");
        } else {
            super.onDisplayPreferenceDialog(preference);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadSummary(key);
    }

    /** Load and display the currently set preference in summary of the preference.
     * @param key preference key to load
     */
    private void loadSummary(String key) {
        Preference pref =  findPreference(key);
        if(pref instanceof ComboBoxPreference) {
            ComboBoxPreference p = (ComboBoxPreference) pref;
            p.setSummary(p.getEntry());
        }
    }

}
