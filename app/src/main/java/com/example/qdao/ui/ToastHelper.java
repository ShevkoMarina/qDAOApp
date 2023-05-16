package com.example.qdao.ui;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public static void make(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
