package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.vondear.rxtool.view.RxToast;

import java.util.List;

import butterknife.BindView;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/6
 */
public class SchoolMapActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_school_map_layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }
    MapView mMapView;
    private AMap aMap;
    private Marker marker;
    private MarkerOptions markerOption;
    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    private boolean isInstalled(String packageName) {
        PackageManager manager = getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }
    /**
     * 跳转高德地图
     */
    private void goToGaodeMap(String lat,String lon) {
        if (!isInstalled("com.autonavi.minimap")) {
            RxToast.normal("请先安装高德地图客户端");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("amapuri://route/plan/?dlat=").append(lat);
        stringBuffer
                .append("&dlon=").append(lon)
        ;
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        startActivity(intent);
    }
    @Override
    protected void initView() {
        //初始化地图控制器对象
        mMapView = findViewById(R.id.map);
        title.setText("中心定位");
        menu.setText("导航");
        Intent intent = getIntent();
        String lat= String.valueOf(intent.getDoubleExtra("lat", 0));
        String lon= String.valueOf(intent.getDoubleExtra("lon", 0));
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGaodeMap(lat,lon);
            }
        });

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.getUiSettings().setZoomControlsEnabled(false);
        markerOption = new MarkerOptions();
        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.ic_marker)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        LatLng latLng = new LatLng(intent.getDoubleExtra("lat", 0), intent.getDoubleExtra("lon", 0));//构造一个位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        marker = aMap.addMarker(markerOption.position(latLng));

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }
}
