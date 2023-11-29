package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.myclass.DataDownload;
import com.jnu.student.myclass.ShopLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaiduMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaiduMapFragment extends Fragment {

    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;

    public BaiduMapFragment() {
        // Required empty public constructor
    }


    public static BaiduMapFragment newInstance() {
        BaiduMapFragment fragment = new BaiduMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_baidu_map, container, false);

        mapView = rootView.findViewById(R.id.mapView);

        TencentMap tencentMap = mapView.getMap();

        // 将定位初始视角
        LatLng point1 = new LatLng(22.249751, 113.536790);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(point1, 12);
        tencentMap.moveCamera(cameraUpdate);

        // 创建一个Marker对象
        MarkerOptions markerOptions = new MarkerOptions(point1).title("暨南大学5栋宿舍楼");

        // 添加标记到地图上
        Marker my_marker = tencentMap.addMarker(markerOptions);

        // 设置Marker支持点击
        my_marker.setClickable(true);

        tencentMap.setOnMarkerClickListener(marker -> {
            if(marker.getId().equals(my_marker.getId())) {
                // 自定义Marker被点击
                System.out.println(my_marker.getTitle() + ",测试成功！");
            }
            return true;
        });


        // 创建一个异步线程
        new Thread(() -> {
            // 获得JSON数据
            String responseData = new DataDownload().download("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
            ArrayList<ShopLocation> shopLocations = new DataDownload().parseJsonObjects(responseData);

            // 切换回主线程（UI线程）
            requireActivity().runOnUiThread(() -> {
                TencentMap tencentMap1 = mapView.getMap();
                for (ShopLocation shopLocation : shopLocations) {
                    // 获得经纬度
                    LatLng point11 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                    MarkerOptions markerOptions1 = new MarkerOptions(point11)
                            .title(shopLocation.getName());
                    Marker my_marker02 = tencentMap1.addMarker(markerOptions1);

                }
            });
        }).start();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}