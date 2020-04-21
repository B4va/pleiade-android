package com.pleiade.android.utils;

import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.pleiade.android.R;

import java.util.Objects;

public class UIHelper {

    public static void setActionBar(AppCompatActivity activity){
        Objects.requireNonNull(activity.getSupportActionBar());
        activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().setCustomView(R.layout.custom_action_bar);
    }
}
