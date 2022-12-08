package com.jd.saas.pdacommon.printer_ble;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.databinding.BlueToothDataBinding;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.printer_ble.bean.BluePrintBean;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.printer_ble.utils.PrintUtil;
import com.jd.saas.pdacommon.toast.MyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlueToothPrintFragment extends SimpleFragment {
    private BlueToothPrintViewModel mViewModel;
    private BlueToothDataBinding mDataBinding;
    private ExecutorService executorService;
    private List<BluePrintBean> printBeanList = new ArrayList<>();//打印数据
    private LocationManager locationManager;//定位管理
    private AlertDialog alertDialog;
    private List<String> mPermissionList = new ArrayList<>();
    private final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!enabled) {
                        alertDialog.show();
                    } else {
                        alertDialog.dismiss();
                    }
                }
            });
        }
    };

    public static BlueToothPrintFragment newInstance() {
        return new BlueToothPrintFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(BlueToothPrintViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.handleBaseNetUI(this);
        ProgressDialog loadingDialog = new ProgressDialog(mContext);
        mViewModel.isShowDialog.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if (show) {
                    loadingDialog.show();
                } else {
                    loadingDialog.dismiss();
                }
            }
        });
        Bundle bundle = getActivity().getIntent().getExtras();
        if (null != bundle) {
            int type = getActivity().getIntent().getExtras().getInt("type");
            if (type == 0) {
                List<BluePrintBean> printList = (List<BluePrintBean>) bundle.get("list");
                printBeanList.addAll(printList);
                mViewModel.setPrintType(0);
            } else {
                DirectDeliveryOrderBean bean = (DirectDeliveryOrderBean) bundle.getSerializable("bean");
                mViewModel.setBean(bean);
                mViewModel.setPrintType(1);

            }
        }

        dialog();
        mDataBinding.rvDeviceList.setAdapter(mViewModel.getTaskListAdapter());
        mDataBinding.rvDeviceList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        registerFilter();
        initPermission();


    }

    // 动态申请权限
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 版本大于等于 Android12 时
            // 只包括蓝牙这部分的权限，其余的需要什么权限自己添加
            mPermissionList.add(Manifest.permission.BLUETOOTH_SCAN);
            mPermissionList.add(Manifest.permission.BLUETOOTH_ADVERTISE);
            mPermissionList.add(Manifest.permission.BLUETOOTH_CONNECT);
        } else {
            // Android 版本小于 Android12 及以下版本
            mPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            mPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (mPermissionList.size() > 0) {
            requestCameraPermission();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_bule_tooth_layout;
    }

    @Override
    protected void reload() {

    }


    private void useGps() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (!isGpsOPen()) {
                    alertDialog.show();
                } else {
                    mViewModel.startSearch();
                }
            } else {
                requestCameraPermission();
            }
        } else {
            mViewModel.startSearch();
        }

    }


    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), mPermissionList.toArray(new String[0]), 200);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults != null && grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 200);
                } else {
                    MyToast.show(R.string.common_blue_tooth_open_jurisdiction, false);
                    getActivity().finish();
                }
                return;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            useGps();
        } else {
            MyToast.show(R.string.common_blue_tooth_open_blu, false);
            getActivity().finish();
        }
    }

    private void dialog() {
        alertDialog = new SimpleAlertDialog.Builder(mContext, R.string.common_blue_tooth_open_gps)
                .setNegativeButton(v -> getActivity().finish()).setPositiveButton(v -> {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }

                ).build().getAlertDialog();
    }

    public void registerFilter() {
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        getActivity().registerReceiver(mViewModel.receiver, intentFilter);
        //注册线程池
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("connect_activity_pool_%d").build();
        executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        mViewModel.executorService = executorService;
        detectionStatus();
    }

    public void detectionStatus() {
        mViewModel.setPrintBeanList(printBeanList);
        executorService.submit(() -> {
            if (PrintUtil.isConnection() == 0) {//打印机已连接，直接打开始打印
                if (mViewModel.getPrintType() == 0) {
                    mViewModel.printLabel(printBeanList.size(), 1);
                } else {
                    mViewModel.printLabel(1, 1);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getContentResolver()
                .registerContentObserver(
                        Settings.Secure
                                .getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
                        false, mGpsMonitor);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            if (!isGpsOPen()) {
                alertDialog.show();
            } else {
                mViewModel.startSearch();
            }
        }
    }


    /**
     * Gps开启状态判断
     *
     * @return Gps开启与否
     */
    @SuppressLint("MissingPermission")
    private boolean isGpsOPen() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销广播
        getActivity().unregisterReceiver(mViewModel.receiver);
        getActivity().getContentResolver().unregisterContentObserver(mGpsMonitor);
    }

}

