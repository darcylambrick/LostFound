package com.example.lostfound;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Item> itemList;
    DatabaseHelper db;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<>();
        db = new DatabaseHelper(this);

        loadItems();

        adapter = new ItemAdapter(itemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItems() {
        itemList.clear();
        Cursor cursor = db.getAllItems();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc = cursor.getString(2);
            String type = cursor.getString(3);
            String phone = cursor.getString(4);
            String date = cursor.getString(5);
            String location = cursor.getString(6);
            itemList.add(new Item(id, title, desc, type, phone, date, location));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}
