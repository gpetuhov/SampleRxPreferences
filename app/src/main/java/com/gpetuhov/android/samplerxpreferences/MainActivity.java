package com.gpetuhov.android.samplerxpreferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

public class MainActivity extends AppCompatActivity {

    // Key of the preference to observe
    public static final String PREF_TO_OBSERVE = "pref_to_observe";

    public static final String LOG_TAG = MainActivity.class.getName();

    // Preferences to be observed
    Preference<String> observablePreference;
    Preference<String> observablePreference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create RxSharedPreference instance
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

        // Create Preference objects
        observablePreference = rxPreferences.getString(PREF_TO_OBSERVE, "Default value");
        // Pref with PREF_TO_OBSERVE key in SharedPrefs gets value "Default value"
        // (if the app runs for the first time)
        // observablePreference2 references the same preference as observablePreference,
        // so its value is always the same as observablePreference.
        observablePreference2 = rxPreferences.getString(PREF_TO_OBSERVE);

        Log.d(LOG_TAG, "Pref 1 = " + observablePreference.get());
        Log.d(LOG_TAG, "Pref 2 = " + observablePreference.get());
        // Output is the same: "Default value"

        observablePreference.set("First value");

        Log.d(LOG_TAG, "Pref 1 = " + observablePreference.get());
        Log.d(LOG_TAG, "Pref 2 = " + observablePreference.get());
        // Output is the same: "First value"

        // Subscribe to changes of observablePreference
        observablePreference.asObservable().subscribe(
                value -> {
                    // This will run one time on the moment of subscribe
                    // and then will run every time preference changes.
                    Log.d(LOG_TAG, "Pref 1 = " + value);
                }
        );

        // These changes will trigger the subscriber above
        observablePreference.set("Second value");
        observablePreference.set("Third value");
    }
}