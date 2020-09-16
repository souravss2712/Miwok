package com.example.miwok;

import android.view.View;
import android.widget.Toast;

public class NumbersclickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "open number list", Toast.LENGTH_SHORT).show();
    }
}
