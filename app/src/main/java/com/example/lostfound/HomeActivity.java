package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnCreate, btnViewList, btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCreate = findViewById(R.id.btnCreateAdvert);
        btnViewList = findViewById(R.id.btnViewList);
        btnMap = findViewById(R.id.btnShowMap);

        btnCreate.setOnClickListener(v -> startActivity(new Intent(this, AddItemActivity.class)));
        btnViewList.setOnClickListener(v -> startActivity(new Intent(this, ItemListActivity.class)));
        btnMap.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
    }
}
