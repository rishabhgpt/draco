package org.droidanalyst.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DScore extends Fragment {

    static String[] perms;
    static String dscore ;
    static String [] featuresForWeights;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_results, null);
        HashMap<String,String> wt = new HashMap<>();
        wt.put("android.app.action.DEVICE_ADMIN_ENABLED" ,"1.9697");
        wt.put("android.bluetooth.adapter.action.SCAN_MODE_CHANGED" ,"2.0157");
        wt.put("android.bluetooth.adapter.action.STATE_CHANGED" ,"2.5909");
        wt.put("android.bluetooth.device.action.ACL_CONNECTED" ,"2.4285");
        wt.put("android.bluetooth.device.action.ACL_DISCONNECTED" ,"2.4285");
        wt.put("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED" ,"2.5949");
        wt.put("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED" ,"2.5949");
        wt.put("android.intent.action.ACTION_POWER_CONNECTED" ,"2.4431");
        wt.put("android.intent.action.ACTION_POWER_DISCONNECTED" ,"2.4285");
        wt.put("android.intent.action.ACTION_SHUTDOWN" ,"3.0422");
        wt.put("android.intent.action.AIRPLANE_MODE" ,"1.9697");
        wt.put("android.intent.action.BATTERY_LOW" ,"2.3296");
        wt.put("android.intent.action.BATTERY_OKAY" ,"1.8689");
        wt.put("android.intent.action.BOOT_COMPLETED" ,"0.7499");
        wt.put("android.intent.action.CAMERA_BUTTON" ,"1.9697");
        wt.put("android.intent.action.CONFIGURATION_CHANGED" ,"2.7991");
        wt.put("android.intent.action.DATE_CHANGED" ,"1.9697");
        wt.put("android.intent.action.DEVICE_STORAGE_LOW" ,"2.3112");
        wt.put("android.intent.action.DEVICE_STORAGE_OK" ,"1.9697");
        wt.put("android.intent.action.DOCK_EVENT" ,"1.9697");
        wt.put("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE" ,"1.9697");
        wt.put("android.intent.action.GTALK_CONNECTED" ,"1.2412");
        wt.put("android.intent.action.INPUT_METHOD_CHANGED" ,"0.4442");
        wt.put("android.intent.action.LOCALE_CHANGED" ,"2.0741");
        wt.put("android.intent.action.MEDIA_BAD_REMOVAL" ,"2.3903");
        wt.put("android.intent.action.MEDIA_BUTTON" ,"2.7225");
        wt.put("android.intent.action.MEDIA_CHECKING" ,"2.0862");
        wt.put("android.intent.action.MEDIA_EJECT" ,"2.5068");
        wt.put("android.intent.action.MEDIA_MOUNTED" ,"2.2921");
        wt.put("android.intent.action.MEDIA_REMOVED" ,"1.5068");
        wt.put("android.intent.action.MEDIA_SCANNER_FINISHED" ,"2.0472");
        wt.put("android.intent.action.MEDIA_UNMOUNTED" ,"1.5068");
        wt.put("android.intent.action.MY_PACKAGE_REPLACED" ,"2.315");
        wt.put("android.intent.action.NEW_OUTGOING_CALL" ,"2.2256");
        wt.put("android.intent.action.PACKAGE_ADDED" ,"2.33");
        wt.put("android.intent.action.PACKAGE_CHANGED" ,"1.3246");
        wt.put("android.intent.action.PACKAGE_DATA_CLEARED" ,"1.9697");
        wt.put("android.intent.action.PACKAGE_FULLY_REMOVED" ,"2.1703");
        wt.put("android.intent.action.PACKAGE_INSTALL" ,"1.3238");
        wt.put("android.intent.action.PACKAGE_REMOVED" ,"1.609");
        wt.put("android.intent.action.PACKAGE_REPLACED" ,"2.1218");
        wt.put("android.intent.action.PACKAGE_RESTARTED" ,"1.2818");
        wt.put("android.intent.action.PHONE_STATE" ,"1.0986");
        wt.put("android.intent.action.PROVIDER_CHANGED" ,"1.9697");
        wt.put("android.intent.action.REBOOT" ,"2.764");
        wt.put("android.intent.action.SCREEN_OFF" ,"1.9697");
        wt.put("android.intent.action.SCREEN_ON" ,"2.273");
        wt.put("android.intent.action.TIMEZONE_CHANGED" ,"2.7012");
        wt.put("android.intent.action.TIME_SET" ,"2.4352");
        wt.put("android.intent.action.TIME_TICK" ,"2.3112");
        wt.put("android.intent.action.USER_PRESENT" ,"2.4391");
        wt.put("android.intent.action.WALLPAPER_CHANGED" ,"1.9697");
        wt.put("android.media.AUDIO_BECOMING_NOISY" ,"1.9697");
        wt.put("android.media.RINGER_MODE_CHANGED" ,"2.8455");
        wt.put("android.net.wifi.RSSI_CHANGED" ,"2.0066");
        wt.put("android.net.wifi.SCAN_RESULTS" ,"2.0066");
        wt.put("android.net.wifi.STATE_CHANGE" ,"2.1563");
        wt.put("android.net.wifi.WIFI_STATE_CHANGED" ,"2.1563");
        wt.put("android.net.wifi.supplicant.CONNECTION_CHANGE" ,"1.9697");
        wt.put("android.net.wifi.supplicant.STATE_CHANGE" ,"2.0066");
        wt.put("android.permission.ACCESS_CHECKIN_PROPERTIES" ,"2.8876");
        wt.put("android.permission.ACCESS_COARSE_LOCATION" ,"1.913");
        wt.put("android.permission.ACCESS_FINE_LOCATION" ,"1.9905");
        wt.put("android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" ,"1.9739");
        wt.put("android.permission.ACCESS_MOCK_LOCATION" ,"2.8881");
        wt.put("android.permission.ACCESS_NETWORK_STATE" ,"2.7788");
        wt.put("android.permission.ACCESS_WIFI_STATE" ,"1.5589");
        wt.put("android.permission.AUTHENTICATE_ACCOUNTS" ,"2.0441");
        wt.put("android.permission.BATTERY_STATS" ,"3.9697");
        wt.put("android.permission.BIND_APPWIDGET" ,"2.8739");
        wt.put("android.permission.BIND_INPUT_METHOD" ,"2.6816");
        wt.put("android.permission.BIND_WALLPAPER" ,"2.878");
        wt.put("android.permission.BLUETOOTH" ,"1.8969");
        wt.put("android.permission.BLUETOOTH_ADMIN" ,"2.8562");
        wt.put("android.permission.BROADCAST_SMS" ,"2.9697");
        wt.put("android.permission.BROADCAST_STICKY" ,"1.2069");
        wt.put("android.permission.BROADCAST_WAP_PUSH" ,"0.9697");
        wt.put("android.permission.CALL_PHONE" ,"2.4427");
        wt.put("android.permission.CALL_PRIVILEGED" ,"2.3242");
        wt.put("android.permission.CAMERA" ,"2.4184");
        wt.put("android.permission.CHANGE_COMPONENT_ENABLED_STATE" ,"2.0926");
        wt.put("android.permission.CHANGE_CONFIGURATION" ,"2.6408");
        wt.put("android.permission.CHANGE_NETWORK_STATE" ,"1.9456");
        wt.put("android.permission.CHANGE_WIFI_MULTICAST_STATE" ,"2.4586");
        wt.put("android.permission.CHANGE_WIFI_STATE" ,"0.7943");
        wt.put("android.permission.CLEAR_APP_CACHE" ,"1.7024");
        wt.put("android.permission.CLEAR_APP_USER_DATA" ,"2.1438");
        wt.put("android.permission.CONTROL_LOCATION_UPDATES" ,"1.8118");
        wt.put("android.permission.DELETE_CACHE_FILES" ,"0.8528");
        wt.put("android.permission.DELETE_PACKAGES" ,"1.6414");
        wt.put("android.permission.DEVICE_POWER" ,"2.0023");
        wt.put("android.permission.DIAGNOSTIC" ,"2.0245");
        wt.put("android.permission.DISABLE_KEYGUARD" ,"2.2739");
        wt.put("android.permission.DUMP" ,"1.0245");
        wt.put("android.permission.EXPAND_STATUS_BAR" ,"1.4586");
        wt.put("android.permission.FLASHLIGHT" ,"2.324");
        wt.put("android.permission.GET_ACCOUNTS" ,"2.4805");
        wt.put("android.permission.GET_PACKAGE_SIZE" ,"1.9281");
        wt.put("android.permission.GET_TASKS" ,"1.9366");
        wt.put("android.permission.HARDWARE_TEST" ,"1.5038");
        wt.put("android.permission.INJECT_EVENTS" ,"1.2246");
        wt.put("android.permission.INSTALL_PACKAGES" ,"0");
        wt.put("android.permission.INTERNET" ,"1.5907");
        wt.put("android.permission.KILL_BACKGROUND_PROCESSES" ,"2.5556");
        wt.put("android.permission.MANAGE_ACCOUNTS" ,"3.0441");
        wt.put("android.permission.MODIFY_AUDIO_SETTINGS" ,"2.6879");
        wt.put("android.permission.MODIFY_PHONE_STATE" ,"2.5385");
        wt.put("android.permission.MOUNT_UNMOUNT_FILESYSTEMS" ,"1.8097");
        wt.put("android.permission.NFC" ,"2.9697");
        wt.put("android.permission.PERSISTENT_ACTIVITY" ,"2.2124");
        wt.put("android.permission.PROCESS_OUTGOING_CALLS" ,"2.4003");
        wt.put("android.permission.READ_CALENDAR" ,"3.1406");
        wt.put("android.permission.READ_CALL_LOG" ,"2.6237");
        wt.put("android.permission.READ_CONTACTS" ,"2.0723");
        wt.put("android.permission.READ_EXTERNAL_STORAGE" ,"1.3305");
        wt.put("android.permission.READ_INPUT_STATE" ,"1.9697");
        wt.put("android.permission.READ_LOGS" ,"1.9648");
        wt.put("android.permission.READ_PHONE_STATE" ,"1.0848");
        wt.put("android.permission.READ_SMS" ,"0.6417");
        wt.put("android.permission.READ_SYNC_SETTINGS" ,"2.6329");
        wt.put("android.permission.READ_SYNC_STATS" ,"2.0441");
        wt.put("android.permission.READ_USER_DICTIONARY" ,"2.1406");
        wt.put("android.permission.REBOOT" ,"2.6093");
        wt.put("android.permission.RECEIVE_BOOT_COMPLETED" ,"1.5662");
        wt.put("android.permission.RECEIVE_MMS" ,"2.3797");
        wt.put("android.permission.RECEIVE_SMS" ,"2.0717");
        wt.put("RECEIVE_WAP_PUSH" ,"1.4215");
        wt.put("RECORD_AUDIO" ,"1.9689");
        wt.put("REORDER_TASKS" ,"1.8436");
        wt.put("RESTART_PACKAGES" ,"1.3885");
        wt.put("SEND_SMS" ,"1.4372");
        wt.put("SET_ORIENTATION" ,"2.4305");
        wt.put("SET_PREFERRED_APPLICATIONS" ,"0.6093");
        wt.put("SET_TIME_ZONE" ,"1.9697");
        wt.put("SET_WALLPAPER" ,"1.4581");
        wt.put("SET_WALLPAPER_HINTS" ,"1.7663");
        wt.put("STATUS_BAR" ,"2.6093");
        wt.put("SYSTEM_ALERT_WINDOW" ,"3.6732");
        wt.put("UPDATE_DEVICE_STATS" ,"3.1962");
        wt.put("USE_CREDENTIALS" ,"2.7322");
        wt.put("USE_SIP" ,"1.9697");
        wt.put("VIBRATE" ,"2.3962");
        wt.put("WAKE_LOCK" ,"2.2474");
        wt.put("WRITE_APN_SETTINGS" ,"0.6181");
        wt.put("WRITE_CALENDAR" ,"2.5905");
        wt.put("WRITE_CALL_LOG" ,"2.6237");
        wt.put("WRITE_CONTACTS" ,"1.8007");
        wt.put("WRITE_EXTERNAL_STORAGE" ,"1.9027");
        wt.put("WRITE_SECURE_SETTINGS" ,"1.4657");
        wt.put("WRITE_SETTINGS" ,"2.7509");
        wt.put("WRITE_SMS" ,"1.4172");
        wt.put("WRITE_SYNC_SETTINGS" ,"2.0828");
        wt.put("WRITE_USER_DICTIONARY" ,"2.1406");
        wt.put("android.hardware.bluetooth" ,"2.1866");
        wt.put("android.hardware.camera" ,"2.7584");
        wt.put("android.hardware.camera.autofocus" ,"1.2483");
        wt.put("android.hardware.camera.flash" ,"1.2108");
        wt.put("android.hardware.camera.front" ,"2.8692");
        wt.put("android.hardware.location" ,"2.7838");
        wt.put("android.hardware.location.network" ,"1.9177");
        wt.put("android.hardware.location.gps" ,"1.902");
        wt.put("android.hardware.nfc" ,"2.2718");
        wt.put("android.hardware.sensor.accelerometer" ,"1.4581");
        wt.put("android.hardware.sensor.compass" ,"1.3692");
        wt.put("android.hardware.sensor.proximity" ,"1.9697");
        wt.put("android.hardware.telephony" ,"3.0734");
        wt.put("android.hardware.touchscreen" ,"2.5266");
        wt.put("android.hardware.touchscreen.multitouch" ,"1.9697");
        wt.put("android.hardware.touchscreen.multitouch.distinct" ,"1.9697");
        wt.put("android.hardware.wifi" ,"1.9697");
        wt.put("android.software.live_wallpaper" ,"2.0381");
        wt.put("android.hardware.microphone" ,"2.6921");
        ((MainActivity)getActivity()).setActionBarTitle("Results");
            TextView tv = (TextView) root.findViewById(R.id.resultText);
            if (perms == null) {
                tv.setText("Choose APK file, final result will be shown here");
            } else {
                tv.setText("App's D-Score is " + dscore);
            }


                ListView lv = (ListView) root.findViewById(R.id.explanation);
                List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                if(featuresForWeights!=null) {
                    for (int i = 0; i < featuresForWeights.length; i++) {
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("txt", "" + featuresForWeights[i]);
                        hm.put("cur", "" + wt.get(featuresForWeights[i]));
                        if (wt.get(featuresForWeights[i]) != null) {
                            aList.add(hm);
                        }
                    }
                    String[] from = {"txt", "cur"};
                    int[] to = {R.id.txt, R.id.cur};
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.listview_layout, from, to);
                    // Setting the adapter to the listView
                    lv.setAdapter(adapter);
                }

        return root;
    }

}
