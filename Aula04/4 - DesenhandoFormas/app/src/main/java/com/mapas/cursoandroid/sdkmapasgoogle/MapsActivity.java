package com.mapas.cursoandroid.sdkmapasgoogle;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Mudar exibição do mapa
        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );

        // Add a marker in Sydney and move the camera
        final LatLng ibirapuera = new LatLng(-23.587097, -46.657635);
        //-23.587097, -46.657635

        /*CircleOptions circleOptions = new CircleOptions();
        circleOptions.center( ibirapuera );
        circleOptions.radius(500);//em metros
        circleOptions.strokeWidth(10);
        circleOptions.strokeColor(Color.GREEN);
        circleOptions.fillColor( Color.argb(128,255, 153, 0) );//0 até 255 alpha
        mMap.addCircle( circleOptions );*/

        /*PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(new LatLng(-23.585406, -46.660405));
        polygonOptions.add(new LatLng(-23.585181, -46.660080));
        polygonOptions.add(new LatLng(-23.586221, -46.659156));
        polygonOptions.add(new LatLng(-23.586447, -46.659472));
        polygonOptions.strokeColor(Color.GREEN);
        polygonOptions.strokeWidth(10);
        polygonOptions.fillColor(Color.argb(128,255, 153, 0));
        mMap.addPolygon( polygonOptions );*/

        //Adicionando evento de clique no mapa
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                /*Toast.makeText(MapsActivity.this,
                        "onClick Lat: " + latitude + " long:" + longitude ,
                        Toast.LENGTH_SHORT).show();*/

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(ibirapuera);
                polylineOptions.add( latLng );
                polylineOptions.color(Color.BLUE);
                polylineOptions.width(20);

                mMap.addPolyline( polylineOptions );

                mMap.addMarker(
                        new MarkerOptions()
                                .position( latLng )
                                .title("Local")
                                .snippet("Descricao")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icone_loja))
                );

            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                Toast.makeText(MapsActivity.this,
                        "onLong Lat: " + latitude + " long:" + longitude ,
                        Toast.LENGTH_SHORT).show();

                mMap.addMarker(
                        new MarkerOptions()
                                .position( latLng )
                                .title("Local")
                                .snippet("Descricao")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carro))
                );
            }
        });

        mMap.addMarker(
                new MarkerOptions()
                        .position( ibirapuera )
                        .title("Ibirapuera")
                        /*.icon(
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                        )*/
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icone_carro_roxo_48px))
        );
        mMap.moveCamera(//2.0 até 21.0
              CameraUpdateFactory.newLatLngZoom(ibirapuera,18)
        );

    }
}
