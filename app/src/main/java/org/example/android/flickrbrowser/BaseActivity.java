package org.example.android.flickrbrowser;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Set a toolbar to act as the ActionBar for this Activity window.
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    // Set a toolbar to act as the ActionBar for this Activity window.
    protected Toolbar activateToolbar() {
        if(toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.app_bar);
            if(toolbar != null) {
                setSupportActionBar(toolbar);
            }
        }
        return toolbar;
    }

    // Set a toolbar with home button to act as the ActionBar for this Activity window.
    protected Toolbar activateToolbarWithHomeEnabled() {
        activateToolbar();
        if(toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;

    }
}