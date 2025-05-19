package com.example.lostfound;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private DatabaseHelper db;
    private List<Item> itemList;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        db = new DatabaseHelper(this);
        itemList = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadItemsAndShowMarkers();
    }

    private void loadItemsAndShowMarkers() {
        itemList.clear();
        itemList = getAllItems();

        for (Item item : itemList) {
            try {
                List<Address> addresses = geocoder.getFromLocationName(item.location, 1);
                if (!addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng position = new LatLng(address.getLatitude(), address.getLongitude());

                    mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(item.title)
                            .snippet("Type: " + item.type + "\nPhone: " + item.phone + "\nDate: " + item.date)
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                    item.type.equalsIgnoreCase("Lost") ?
                                            BitmapDescriptorFactory.HUE_RED :
                                            BitmapDescriptorFactory.HUE_GREEN
                            ))
                    );
                }
            } catch (IOException e) {
                Toast.makeText(this, "Geocode failed for: " + item.location, Toast.LENGTH_SHORT).show();
            }
        }

        if (!itemList.isEmpty()) {
            try {
                Address firstAddress = geocoder.getFromLocationName(itemList.get(0).location, 1).get(0);
                LatLng firstLatLng = new LatLng(firstAddress.getLatitude(), firstAddress.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 12));
            } catch (Exception ignored) {}
        }
    }

    private List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        var cursor = db.getAllItems();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String type = cursor.getString(3);
            String phone = cursor.getString(4);
            String date = cursor.getString(5);
            String location = cursor.getString(6);

            list.add(new Item(id, title, description, type, phone, date, location));
        }
        return list;
    }
}
