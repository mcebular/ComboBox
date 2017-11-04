# EditableSeekBar

Combination of Spinner and EditText. Android 4.4+ (API 19)

# Usage
*For example usages, see the sample app in the `app/` folder.*

1. Clone repo and add `/ComboBox/combobox/` as a module and dependency
in your project's settings.gradle:

```
include ':app', ':combobox'
project(':combobox').projectDir = new File('/path/to/ComboBox/combobox/')
```

and in your project's `app/build.gradle` dependencies:

```
dependencies {
    ...
    implementation project(':combobox')
}
```

2. Include ComboBox widget in your layout

```
<com.mc0239.ComboBox
    android:id="@+id/comboBox2"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

3.a. Add an adapter to ComboBox using resource arrays:

```
ComboBox comboBox1 = findViewById(R.id.comboBox1);
ComboBoxAdapter adapter1 = ComboBoxAdapter
        .createFromResources(this, R.array.server_entries_array, R.array.server_values_array);
comboBox1.setAdapter(adapter1);
```

To create a custom entry in array, leave an empty `<item/>` tag in values array:

```
<string-array name="server_entries_array">
    <item>Germany</item>
    <item>UK</item>
    <item>USA</item>
    <item>Other</item> <!-- entry name for custom entry -->
</string-array>
<string-array name="server_values_array">
    <item>http://www.example.de/</item>
    <item>http://www.example.co.uk/</item>
    <item>http://www.example.com/</item>
    <item /> <!-- this is "custom" entry -->
</string-array>
```

3.b. Or add an adapter to ComboBox using `List<Pair<String, String>>` arrays:
```

ArrayList<Pair<String, String>> list = new ArrayList<>();
list.add(new Pair<>("Entry 1", "value1"));
list.add(new Pair<>("Entry 2", "value2"));
...
list.add(null); // this is your "custom" entry
...

ComboBox comboBox2 = findViewById(R.id.comboBox2);
ComboBoxAdapter adapter2 = new ComboBoxAdapter(this, list, "Custom value:");
comboBox2.setAdapter(adapter2);
```

4. (Optional) Customize EditText input and/or listen for selected item changes:

```
// EditText hint and input type
comboBox2.setHint("value x");
comboBox2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

comboBox2.setOnValueChangedListener(new ComboBox.OnValueChangedListener() {
        @Override
        public void onValueChanged(String value, boolean isCustom) {
            textView.setText(String.format("Selected item: %s [isCustom? %s]", value, isCustom));
        }
});
```

# Using as a preference

You can use ComboBox as a preference on a PreferenceScreen

```
<com.mc0239.Preference.ComboBoxPreference
    android:title="Choose server"
    android:key="server"
    app:entries="@array/server_entries_array"
    app:entryValues="@array/server_values_array"
    android:defaultValue="http://www.example.co.uk/"
    android:hint="http://example.com/"
    android:inputType="textUri" />
```

Note: `app:entries` and `app:entryValues` **must be set!**

## Customization

ComboBoxPreference has these customizations availiable: 

 * `android:inputType` EditText input type
 * `android:hint` EditText hint

For value and title use preferences' existing values, e.g. `title` and `defaultValue`.
