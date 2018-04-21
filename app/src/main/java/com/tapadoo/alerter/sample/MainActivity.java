package com.tapadoo.alerter.sample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import io.github.aafactory.sample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerter_activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnAlertDefault).setOnClickListener(this);
        findViewById(R.id.btnAlertColoured).setOnClickListener(this);
        findViewById(R.id.btnAlertCustomIcon).setOnClickListener(this);
        findViewById(R.id.btnAlertTextOnly).setOnClickListener(this);
        findViewById(R.id.btnAlertOnClick).setOnClickListener(this);
        findViewById(R.id.btnAlertVerbose).setOnClickListener(this);
        findViewById(R.id.btnAlertCallback).setOnClickListener(this);
        findViewById(R.id.btnAlertInfiniteDuration).setOnClickListener(this);
        findViewById(R.id.btnAlertWithProgress).setOnClickListener(this);
        findViewById(R.id.btnAlertWithCustomFont).setOnClickListener(this);
        findViewById(R.id.btnAlertSwipeToDismissEnabled).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnAlertColoured) {
            showAlertColoured();
        } else if (i == R.id.btnAlertCustomIcon) {
            showAlertWithIcon();
        } else if (i == R.id.btnAlertTextOnly) {
            showAlertTextOnly();
        } else if (i == R.id.btnAlertOnClick) {
            showAlertWithOnClick();
        } else if (i == R.id.btnAlertVerbose) {
            showAlertVerbose();
        } else if (i == R.id.btnAlertCallback) {
            showAlertCallbacks();
        } else if (i == R.id.btnAlertInfiniteDuration) {
            showAlertInfiniteDuration();
        } else if (i == R.id.btnAlertWithProgress) {
            showAlertWithProgress();
        } else if (i == R.id.btnAlertWithCustomFont) {
            showAlertWithCustomFont();
        } else if (i == R.id.btnAlertSwipeToDismissEnabled) {
            showAlertSwipeToDismissEnabled();
        } else {
            showAlertDefault();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void showAlertDefault() {
        Alerter.create(MainActivity.this)
                .setTitle(R.string.title_activity_example)
                .setText("Alert text...")
                .show();
    }

    private void showAlertColoured() {
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setBackgroundColorRes(R.color.colorAccent)
                .show();
    }

    private void showAlertWithIcon() {
        Alerter.create(MainActivity.this)
                .setText("Alert text...")
                .setIcon(R.drawable.alerter_ic_mail_outline)
                .setIconColorFilter(0) // Optional - Removes white tint
                .show();
    }

    private void showAlertTextOnly() {
        Alerter.create(MainActivity.this)
                .setText("Alert text...")
                .show();
    }

    private void showAlertWithOnClick() {
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setDuration(10000)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "OnClick Called", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void showAlertVerbose() {
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setText("The alert scales to accommodate larger bodies of text. " +
                        "The alert scales to accommodate larger bodies of text. " +
                        "The alert scales to accommodate larger bodies of text.")
                .show();
    }

    private void showAlertCallbacks(){
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setDuration(10000)
                .setOnShowListener(new OnShowAlertListener() {
                    @Override
                    public void onShow() {
                        Toast.makeText(MainActivity.this, "Show Alert", Toast.LENGTH_LONG).show();
                    }
                })
                .setOnHideListener(new OnHideAlertListener() {
                    @Override
                    public void onHide() {
                        Toast.makeText(MainActivity.this, "Hide Alert", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void showAlertInfiniteDuration() {
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableInfiniteDuration(true)
                .show();
    }

    private void showAlertWithProgress() {
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableProgress(true)
                .setProgressColorRes(R.color.colorPrimary)
                .show();
    }

    private void showAlertWithCustomFont() {
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setTitleAppearance(R.style.AlertTextAppearance_Title)
                .setTitleTypeface(Typeface.createFromAsset(getAssets(), "Pacifico-Regular.ttf"))
                .setText("Alert text...")
                .setTextAppearance(R.style.AlertTextAppearance_Text)
                .setTextTypeface(Typeface.createFromAsset(getAssets(), "ScopeOne-Regular.ttf"))
                .show();
    }

    private void showAlertSwipeToDismissEnabled() {
        Alerter.create(MainActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableSwipeToDismiss()
                .setOnHideListener(new OnHideAlertListener() {
                    @Override
                    public void onHide() {
                        Toast.makeText(MainActivity.this, "Hide Alert", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

}
