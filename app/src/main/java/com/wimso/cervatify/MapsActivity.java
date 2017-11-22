package com.wimso.cervatify;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wimso.cervatify.db.SQLiteDB;
import com.wimso.cervatify.model.Cerveja;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SQLiteDB sqLiteDB;

    public static void start(Context context){
        Intent intent = new Intent(context, MapsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        sqLiteDB = new SQLiteDB(this);
        List<Cerveja> cervejaList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Cerveja cerveja;

        if (cursor.moveToFirst()) {
            do {

                cerveja = new Cerveja();

                cerveja.setId(cursor.getInt(0));
                cerveja.setNome(cursor.getString(1));
                cerveja.setPreco(cursor.getString(2));
                cerveja.setLocal(cursor.getString(3));

                cervejaList.add(cerveja);
            }while (cursor.moveToNext());
        }

        for (Cerveja c : cervejaList) {
            LatLng latLng = getLatLongFromAddress(c.getLocal());
            System.out.println(c.getLocal());
            map.addMarker(new MarkerOptions().position(latLng).title(c.getNome() + " - R$ " + c.getPreco()));
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-3.71839, -38.5434), 11));
    }

    private LatLng getLatLongFromAddress(String address)
    {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            if (addresses.size() > 0) {
                double lat = addresses.get(0).getLatitude();
                double lng = addresses.get(0).getLongitude();

                return new LatLng(lat,lng);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
