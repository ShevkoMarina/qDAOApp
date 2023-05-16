package com.example.qdao.ui.create_dao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.example.qdao.R;
import com.example.qdao.ui.my_proposals.MyProposalsActivity;

public class CreateDaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dao);

        Button createDaoBtn = findViewById(R.id.dao_create_dao_btn);

        createDaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(CreateDaoActivity.this, MyProposalsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 4000);

            }
        });
    }
}