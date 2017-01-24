package com.example.dinghao.message;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private TextView mTvBuild;
    private TextView mTvImei;
    private TextView mTvAndroidId;
    private TextView mTvWifi;
    private TextView mTvBluetooth;
    private TextView mTvIp;
    private TextView mTvResolution;
    private LinearLayout mActivityMain;
    private TextView mTvMemory;
    private TextView mTvAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mTvImei.setText("TelephonyManager信息：" + getimeiInfo());
        mTvAndroidId.setText("android_id信息：" + getAndroidId());
        mTvBluetooth.setText("蓝牙 Mac地址：" + getBluetoothMacInfo());
        mTvWifi.setText("Wifi Mac地址：" + getWifiMacInfo());
        mTvIp.setText("ipv6地址：" + getLocalIPAddressv6() + "\nipv4地址：" + getLocalIPAddressv4());
        mTvResolution.setText("屏幕尺寸：" + getResolution());
        mTvMemory.setText("内存信息：\n" + FileSizeUtil.getTotalMemorySize(MainActivity.this) + "\n" + FileSizeUtil.getAvailableMemory(MainActivity.this)
                + "\n" + FileSizeUtil.getTotalInternalMemorySize(MainActivity.this) + "\n" + FileSizeUtil.getAvailableInternalMemorySize(MainActivity.this));
        mTvAgent.setText("UserAgent信息："+getUserAgent());
        mTvBuild.setText("build信息：" + getBuildInfo());

    }

    private void initView() {
        mTvBuild = (TextView) findViewById(R.id.tv_build);
        mTvImei = (TextView) findViewById(R.id.tv_imei);
        mTvAndroidId = (TextView) findViewById(R.id.tv_android_id);
        mTvWifi = (TextView) findViewById(R.id.tv_wifi);
        mTvBluetooth = (TextView) findViewById(R.id.tv_bluetooth);
        mTvIp = (TextView) findViewById(R.id.tv_ip);
        mTvResolution = (TextView) findViewById(R.id.tv_resolution);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);

        mTvMemory = (TextView) findViewById(R.id.tv_memory);
        mTvAgent = (TextView) findViewById(R.id.tv_agent);
    }

    public String getUserAgent() {
        WebView mWebView = new WebView(this);
        return mWebView.getSettings().getUserAgentString();
    }

    private String getResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = displayMetrics.density; //得到密度
        float width = displayMetrics.widthPixels;//得到宽度
        float height = displayMetrics.heightPixels;//得到高度
        return "密度" + density + "宽度" + width + "高度" + height;
    }

    private String getLocalIPAddressv4() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "null";
    }

    private String getLocalIPAddressv6() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "null";
    }


    public String getWifiMacInfo() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public String getBluetoothMacInfo() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.getAddress();
    }

    public String getAndroidId() {
        return Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
    }

    public String getimeiInfo() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        StringBuffer sb = new StringBuffer();


        sb.append("\nIMEI唯一的设备硬件ID(GSM手机的 IMEI 和 CDMA手机的 MEID)：" + tm.getDeviceId());//String

        sb.append("\nIMSI储存在SIM卡中，可用于区别移动用户的有效信息：" + tm.getSubscriberId());//String

        sb.append("\nSIM卡的设备Sim Serial Number(对于CDMA设备，返回的是一个空值)：" + tm.getSimSerialNumber());//String

        return sb.toString();
    }

    public String getBuildInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\nID：" + Build.ID);
        sb.append("\n系统定制商：" + Build.BRAND);
        sb.append("\n设置参数：" + Build.DEVICE);
        sb.append("\n版本：" + Build.MODEL);
        sb.append("\ncpu指令集 " + Build.CPU_ABI);
        sb.append("\n硬件名称：" + Build.HARDWARE);
        sb.append("\n硬件识别码：" + Build.FINGERPRINT);
        sb.append("\n硬件制造商：" + Build.MANUFACTURER);
        sb.append("\n硬件序列号：" + Build.SERIAL);
        sb.append("\n手机制造商：" + Build.PRODUCT);
        sb.append("\n显示屏参数：" + Build.DISPLAY);
        sb.append("\n无线电固件版本：" + Build.getRadioVersion());


        sb.append("\n主板：" + Build.BOARD);
        sb.append("\n系统启动程序版本号：" + Build.BOOTLOADER);
        sb.append("\ncpu指令集2 " + Build.CPU_ABI2);
        sb.append("\n描述Build的标签：" + Build.TAGS);
        sb.append("\nbuilder类型：" + Build.TYPE);
        sb.append("\nTIME: " + Build.TIME);
        sb.append("\nHOST: " + Build.HOST);
        sb.append("\nUSER：" + Build.USER);
        return sb.toString();
    }
}
