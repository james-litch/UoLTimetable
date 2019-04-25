package com.group51.uoltimetable.fragments;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.group51.uoltimetable.Model.LectureViewModel;
import com.group51.uoltimetable.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LectureInfoFragment extends Fragment implements OnMapReadyCallback {
    View view;
    GoogleMap map;
    SupportMapFragment mapFrag;
    TextView lectureName;
    TextView lecturerName;
    TextView location;
    Button attendanceButton;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LectureViewModel viewModel;


    public LectureInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.lecture_info_fragment, container, false);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(LectureViewModel.class);
        lectureName = view.findViewById(R.id.info_lecture_name);
        lecturerName = view.findViewById(R.id.info_lecturer_name);
        location = view.findViewById(R.id.info_location);
        attendanceButton = view.findViewById(R.id.register_attendance_button);

        displayInfo(viewModel.getLectureInfo());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeMap();

        attendanceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO register attendance method.
                Toast.makeText(getContext(), "attendance button test", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayInfo(JSONObject lectureInfo) {
        try {
            lectureName.setText(lectureInfo.getString("lectureName"));
            lecturerName.setText(lectureInfo.getString("lecturerName"));
            location.setText(lectureInfo.getString("location"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initializeMap() {
        if (map == null) {
            mapFrag = new SupportMapFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.info_map_frame, mapFrag).commit();

            mapFrag.getMapAsync(this);
        }

    }

    private void setUpMap() {
        LatLng liverpool = new LatLng(53.408371, -2.991573);
        MarkerOptions options = new MarkerOptions();
        options.position(liverpool).title("liverpool");
        map.addMarker(options);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(liverpool, 15));
        map.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        map.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setUpMap();
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private void showDefaultLocation() {
        Toast.makeText(getContext(), "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng liverpoolGuild = new LatLng(53.405389, -2.966077);
        map.moveCamera(CameraUpdateFactory.newLatLng(liverpoolGuild));
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    LatLng currentLocation = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(currentLocation);
                    map.addCircle(circleOptions);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

                }
            };


}

