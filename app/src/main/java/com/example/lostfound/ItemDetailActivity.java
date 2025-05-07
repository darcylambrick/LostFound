package com.example.lostfound;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvDesc, tvType, tvPhone, tvDate, tvLocation;
    Button btnDelete;
    DatabaseHelper db;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDescription);
        tvType = findViewById(R.id.tvType);
        tvPhone = findViewById(R.id.tvPhone);
        tvDate = findViewById(R.id.tvDate);
        tvLocation = findViewById(R.id.tvLocation);
        btnDelete = findViewById(R.id.btnDelete);
        db = new DatabaseHelper(this);

        item = (Item) getIntent().getSerializableExtra("item");

        tvTitle.setText(item.title);
        tvDesc.setText(item.description);
        tvType.setText(item.type);
        tvPhone.setText(item.phone);
        tvDate.setText(item.date);
        tvLocation.setText(item.location);

        btnDelete.setOnClickListener(v -> {
            db.deleteItem(item.id);
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
