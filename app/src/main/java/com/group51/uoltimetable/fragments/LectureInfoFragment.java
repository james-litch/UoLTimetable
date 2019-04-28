package com.group51.uoltimetable.fragments;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.group51.uoltimetable.Model.LectureInfoViewModel;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.DateTimeHelper;
import com.group51.uoltimetable.utilities.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LectureInfoFragment extends Fragment implements OnMapReadyCallback {
    private FusedLocationProviderClient locationClient;
    private final int MAX_DISTANCE_METERS = 100;
    LatLng targetLocation;
    View view;
    GoogleMap map;
    SupportMapFragment mapFrag;
    TextView lectureName;
    TextView lecturerName;
    TextView location;
    TextView time;
    Button attendanceButton;
    DateTimeHelper dateTimeHelper;
    String startTime;
    String endTime;
    boolean inRange = false;
    Button changeRoomButton;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LectureInfoViewModel viewModel;
    SessionManager sessionManager;
    Spinner spinner;


    public LectureInfoFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sessionManager = new SessionManager(Objects.requireNonNull(getContext()));

        if (sessionManager.isLecturer()) {
            view = inflater.inflate(R.layout.lecturers_info_fragment, container, false);
            changeRoomButton = view.findViewById(R.id.change_room_button);
            spinner = view.findViewById(R.id.spinner_building);


        } else {
            view = inflater.inflate(R.layout.lecture_info_fragment, container, false);
            lecturerName = view.findViewById(R.id.info_lecturer_name);
            attendanceButton = view.findViewById(R.id.register_attendance_button);
            dateTimeHelper = new DateTimeHelper();


        }

        lectureName = view.findViewById(R.id.info_lecture_name);
        location = view.findViewById(R.id.info_location);
        time = view.findViewById(R.id.info_time);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(LectureInfoViewModel.class);
        locationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));


        displayInfo(viewModel.getLectureInfo());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initialiseMap();

        if (sessionManager.isLecturer()) {
            roomChangeButtonClick();

        } else {
            attendanceButtonClick();

        }


    }

    private void roomChangeButtonClick() {
        changeRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "clicked room change", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void attendanceButtonClick() {
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closeToLecture();
                boolean onTime = dateTimeHelper.inTimeRange(startTime, endTime, viewModel.getDate());
                //TODO register attendance method.
                if (inRange && onTime) {
                    registerAttendance();

                } else if (!onTime) {
                    Toast.makeText(getContext(), "Didn't register in time", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void closeToLecture() {

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            enableMyLocationIfPermitted();
        }
        locationClient.getLastLocation().addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Location endPoint = new Location("Target");
                    endPoint.setLatitude(targetLocation.latitude);
                    endPoint.setLongitude(targetLocation.longitude);
                    double distance = location.distanceTo(endPoint);

                    if (distance < MAX_DISTANCE_METERS) {
                        inRange = true;
                    } else {
                        Toast.makeText(getContext(), "You are not close enough.", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }


    private void registerAttendance() {
        Toast.makeText(getContext(), "registered attendance", Toast.LENGTH_SHORT).show();


    }


    private void displayInfo(JSONObject lectureInfo) {
        try {
            if (!sessionManager.isLecturer()) {
                lecturerName.setText(lectureInfo.getString("lecturerName"));
            }

            lectureName.setText(lectureInfo.getString("lectureName"));
            location.setText(lectureInfo.getString("location"));
            time.setText(String.format("%s - %s", lectureInfo.getString("startTime"), lectureInfo.getString("endTime")));

            targetLocation = new LatLng(lectureInfo.getDouble("latitude"), lectureInfo.getDouble("longitude"));

            startTime = viewModel.getLectureInfo().getString("startTime");
            endTime = viewModel.getLectureInfo().getString("endTime");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initialiseMap() {
        if (map == null) {
            mapFrag = new SupportMapFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.info_map_frame, mapFrag).commit();

            mapFrag.getMapAsync(this);
        }

    }

    private void setUpMap() {
        MarkerOptions options = new MarkerOptions();
        options.position(targetLocation).title("Lecture");
        map.addMarker(options);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLocation, 15));
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
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
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
                    attendanceButton.setEnabled(false);
                    showDefaultLocation();
                }
            }

        }
    }

    private void showDefaultLocation() {
        Toast.makeText(getContext(), "Location permission not granted, " +
                        "showing default location for the Guild",
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
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(currentLocation);
                    map.addCircle(circleOptions);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

                }
            };


}

