package com.mc0239.spinnerwithcustomvalue;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.util.Pair;
import android.widget.TextView;

import com.mc0239.combobox.ComboBox;
import com.mc0239.combobox.ComboBoxAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView1);

        // simple usage example with entries and values defined in resources
        // use an empty <item/> tag for a custom value field.
        ComboBox comboBox1 = findViewById(R.id.comboBox1);
        ComboBoxAdapter adapter1 = ComboBoxAdapter.createFromResources(this, R.array.server_entries_array, R.array.server_values_array);
        comboBox1.setAdapter(adapter1);

        // usage example with a List structure
        // add a null element to list for a custom value field.
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("Entry 1", "value1"));
        list.add(new Pair<>("Entry 2", "value2"));
        list.add(new Pair<>("Entry 3", "value3"));
        list.add(null);
        list.add(new Pair<>("Entry 4", "value4"));
        list.add(new Pair<>("Entry 5", "value5"));
        list.add(new Pair<>("Entry 6", "value6"));

        ComboBox comboBox2 = findViewById(R.id.comboBox2);
        ComboBoxAdapter adapter2 = new ComboBoxAdapter(this, list, "Own:");
        comboBox2.setAdapter(adapter2);
        // a little bit of edit text customization
        comboBox2.setHint("value x");
        comboBox2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        // listener for selected value changes
        // fired when spinner item is changed, or if custom values is selected, when text changes.
        // note that because of text change listening, this function is called often if custom value
        // is being typed in. Text-typing calls can be filtered out by checking the isCustom
        // parameter
        comboBox2.setOnValueChangedListener(new ComboBox.OnValueChangedListener() {
            @Override
            public void onValueChanged(String value, boolean isCustom) {
                textView.setText(String.format("Selected item: %s [isCustom? %s]", value, isCustom));
            }
        });

        // example usage of a ComboBoxPreference (see res/xml/settings.xml)
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MyPreferenceFragment()).commit();
    }

}
