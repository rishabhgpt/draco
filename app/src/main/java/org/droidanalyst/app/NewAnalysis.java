package org.droidanalyst.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.Receiver;
import net.dongliu.apk.parser.bean.Service;
import net.dongliu.apk.parser.bean.UseFeature;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.droidanalyst.features.BatteryFeatures;
import org.droidanalyst.features.BinderFeatures;
import org.droidanalyst.features.CPUFeatures;
import org.droidanalyst.features.MemoryFeatures;
import org.droidanalyst.features.NetworkFeatures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class NewAnalysis extends Fragment implements AdapterView.OnItemSelectedListener {


   String [] Perms={"ACCESS_CHECKIN_PROPERTIES","ACCESS_COARSE_LOCATION","ACCESS_FINE_LOCATION","ACCESS_LOCATION_EXTRA_COMMANDS","ACCESS_MOCK_LOCATION","ACCESS_NETWORK_STATE","ACCESS_SURFACE_FLINGER","ACCESS_WIFI_STATE","ACCOUNT_MANAGER","ADD_VOICEMAIL","AUTHENTICATE_ACCOUNTS","BATTERY_STATS","BIND_ACCESSIBILITY_SERVICE","BIND_APPWIDGET","BIND_CARRIER_MESSAGING_SERVICE","BIND_DEVICE_ADMIN","BIND_DREAM_SERVICE","BIND_INPUT_METHOD","BIND_NFC_SERVICE","BIND_NOTIFICATION_LISTENER_SERVICE","BIND_PRINT_SERVICE","BIND_REMOTEVIEWS","BIND_TEXT_SERVICE","BIND_TV_INPUT","BIND_VOICE_INTERACTION","BIND_VPN_SERVICE","BIND_WALLPAPER","BLUETOOTH","BLUETOOTH_ADMIN","BLUETOOTH_PRIVILEGED","BODY_SENSORS","BRICK","BROADCAST_PACKAGE_REMOVED","BROADCAST_SMS","BROADCAST_STICKY","BROADCAST_WAP_PUSH","CALL_PHONE","CALL_PRIVILEGED","CAMERA","CAPTURE_AUDIO_OUTPUT","CAPTURE_SECURE_VIDEO_OUTPUT","CAPTURE_VIDEO_OUTPUT","CHANGE_COMPONENT_ENABLED_STATE","CHANGE_CONFIGURATION","CHANGE_NETWORK_STATE","CHANGE_WIFI_MULTICAST_STATE","CHANGE_WIFI_STATE","CLEAR_APP_CACHE","CLEAR_APP_USER_DATA","CONTROL_LOCATION_UPDATES","DELETE_CACHE_FILES","DELETE_PACKAGES","DEVICE_POWER","DIAGNOSTIC","DISABLE_KEYGUARD","DUMP","EXPAND_STATUS_BAR","FACTORY_TEST","FLASHLIGHT","FORCE_BACK","GET_ACCOUNTS","GET_PACKAGE_SIZE","GET_TASKS","GET_TOP_ACTIVITY_INFO","GLOBAL_SEARCH","HARDWARE_TEST","INJECT_EVENTS","INSTALL_LOCATION_PROVIDER","INSTALL_PACKAGES","INSTALL_SHORTCUT","INTERNAL_SYSTEM_WINDOW","INTERNET","KILL_BACKGROUND_PROCESSES","LOCATION_HARDWARE","MANAGE_ACCOUNTS","MANAGE_APP_TOKENS","MANAGE_DOCUMENTS","MASTER_CLEAR","MEDIA_CONTENT_CONTROL","MODIFY_AUDIO_SETTINGS","MODIFY_PHONE_STATE","MOUNT_FORMAT_FILESYSTEMS","MOUNT_UNMOUNT_FILESYSTEMS","NFC","PERSISTENT_ACTIVITY","PROCESS_OUTGOING_CALLS","READ_CALENDAR","READ_CALL_LOG","READ_CONTACTS","READ_EXTERNAL_STORAGE","READ_FRAME_BUFFER","READ_HISTORY_BOOKMARKS","READ_INPUT_STATE","READ_LOGS","READ_PHONE_STATE","READ_PROFILE","READ_SMS","READ_SOCIAL_STREAM","READ_SYNC_SETTINGS","READ_SYNC_STATS","READ_USER_DICTIONARY","READ_VOICEMAIL","REBOOT","RECEIVE_BOOT_COMPLETED","RECEIVE_MMS","RECEIVE_SMS","RECEIVE_WAP_PUSH","RECORD_AUDIO","REORDER_TASKS","RESTART_PACKAGES","SEND_RESPOND_VIA_MESSAGE","SEND_SMS","SET_ACTIVITY_WATCHER","SET_ALARM","SET_ALWAYS_FINISH","SET_ANIMATION_SCALE","SET_DEBUG_APP","SET_ORIENTATION","SET_POINTER_SPEED","SET_PREFERRED_APPLICATIONS","SET_PROCESS_LIMIT","SET_TIME","SET_TIME_ZONE","SET_WALLPAPER","SET_WALLPAPER_HINTS","SIGNAL_PERSISTENT_PROCESSES","STATUS_BAR","SUBSCRIBED_FEEDS_READ","SUBSCRIBED_FEEDS_WRITE","SYSTEM_ALERT_WINDOW","TRANSMIT_IR","UNINSTALL_SHORTCUT","UPDATE_DEVICE_STATS","USE_CREDENTIALS","USE_SIP","VIBRATE","WAKE_LOCK","WRITE_APN_SETTINGS","WRITE_CALENDAR","WRITE_CALL_LOG","WRITE_CONTACTS","WRITE_EXTERNAL_STORAGE","WRITE_GSERVICES","WRITE_HISTORY_BOOKMARKS","WRITE_PROFILE","WRITE_SECURE_SETTINGS","WRITE_SETTINGS","WRITE_SMS","WRITE_SOCIAL_STREAM","WRITE_SYNC_SETTINGS","WRITE_USER_DICTIONARY","WRITE_VOICEMAIL"};
   String [] features={"android.hardware.audio.low_latency","android.hardware.bluetooth","android.hardware.bluetooth_le","android.hardware.camera","android.hardware.camera.autofocus","android.hardware.camera.flash","android.hardware.camera.front","android.hardware.camera.any","android.hardware.camera.external","android.hardware.camera.level.full","android.hardware.camera.capability.manual_sensor","android.hardware.camera.capability.manual_post_processing","android.hardware.camera.capability.raw","android.hardware.consumerir","android.hardware.location","android.hardware.location.network","android.hardware.location.gps","android.hardware.nfc","android.hardware.nfc.hce","android.hardware.sensor.accelerometer","android.hardware.sensor.barometer","android.hardware.sensor.compass","android.hardware.sensor.gyroscope","android.hardware.sensor.light","android.hardware.sensor.proximity","android.hardware.sensor.stepcounter","android.hardware.sensor.stepdetector","android.hardware.screen.landscape","android.hardware.screen.portrait","android.hardware.telephony","android.hardware.telephony.cdma","android.hardware.telephony.gsm","android.hardware.type.television","android.hardware.faketouch","android.hardware.faketouch.multitouch.distinct","android.hardware.faketouch.multitouch.jazzhand","android.hardware.touchscreen","android.hardware.touchscreen.multitouch","android.hardware.touchscreen.multitouch.distinct","android.hardware.touchscreen.multitouch.jazzhand","android.hardware.usb.host","android.hardware.usb.accessory","android.hardware.wifi","android.hardware.wifi.direc","android.software.app_widgets","android.software.device_admin","android.software.home_screen","android.software.input_methods","android.software.live_wallpaper","android.software.sip","android.software.sip.voip","android.hardware.microphone"};
   String [] intent_actions={"android.app.action.ACTION_PASSWORD_CHANGED","android.app.action.ACTION_PASSWORD_EXPIRING","android.app.action.ACTION_PASSWORD_FAILED","android.app.action.ACTION_PASSWORD_SUCCEEDED","android.app.action.DEVICE_ADMIN_DISABLED","android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED","android.app.action.DEVICE_ADMIN_ENABLED","android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED","android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED","android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED","android.bluetooth.adapter.action.DISCOVERY_FINISHED","android.bluetooth.adapter.action.DISCOVERY_STARTED","android.bluetooth.adapter.action.LOCAL_NAME_CHANGED","android.bluetooth.adapter.action.SCAN_MODE_CHANGED","android.bluetooth.adapter.action.STATE_CHANGED","android.bluetooth.device.action.ACL_CONNECTED","android.bluetooth.device.action.ACL_DISCONNECTED","android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED","android.bluetooth.device.action.BOND_STATE_CHANGED","android.bluetooth.device.action.CLASS_CHANGED","android.bluetooth.device.action.FOUND","android.bluetooth.device.action.NAME_CHANGED","android.bluetooth.device.action.UUID","android.bluetooth.devicepicker.action.DEVICE_SELECTED","android.bluetooth.devicepicker.action.LAUNCH","android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT","android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED","android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED","android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED","android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED","android.hardware.action.NEW_PICTURE","android.hardware.action.NEW_VIDEO","android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS","android.intent.action.ACTION_POWER_CONNECTED","android.intent.action.ACTION_POWER_DISCONNECTED","android.intent.action.ACTION_SHUTDOWN","android.intent.action.AIRPLANE_MODE","android.intent.action.BATTERY_CHANGED","android.intent.action.BATTERY_LOW","android.intent.action.BATTERY_OKAY","android.intent.action.BOOT_COMPLETED","android.intent.action.CAMERA_BUTTON","android.intent.action.CONFIGURATION_CHANGED","android.intent.action.DATE_CHANGED","android.intent.action.DEVICE_STORAGE_LOW","android.intent.action.DEVICE_STORAGE_OK","android.intent.action.DOCK_EVENT","android.intent.action.DREAMING_STARTED","android.intent.action.DREAMING_STOPPED","android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE","android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE","android.intent.action.FETCH_VOICEMAIL","android.intent.action.GTALK_CONNECTED","android.intent.action.GTALK_DISCONNECTED","android.intent.action.HEADSET_PLUG","android.intent.action.INPUT_METHOD_CHANGED","android.intent.action.LOCALE_CHANGED","android.intent.action.MANAGE_PACKAGE_STORAGE","android.intent.action.MEDIA_BAD_REMOVAL","android.intent.action.MEDIA_BUTTON","android.intent.action.MEDIA_CHECKING","android.intent.action.MEDIA_EJECT","android.intent.action.MEDIA_MOUNTED","android.intent.action.MEDIA_NOFS","android.intent.action.MEDIA_REMOVED","android.intent.action.MEDIA_SCANNER_FINISHED","android.intent.action.MEDIA_SCANNER_SCAN_FILE","android.intent.action.MEDIA_SCANNER_STARTED","android.intent.action.MEDIA_SHARED","android.intent.action.MEDIA_UNMOUNTABLE","android.intent.action.MEDIA_UNMOUNTED","android.intent.action.MY_PACKAGE_REPLACED","android.intent.action.NEW_OUTGOING_CALL","android.intent.action.NEW_VOICEMAIL","android.intent.action.PACKAGE_ADDED","android.intent.action.PACKAGE_CHANGED","android.intent.action.PACKAGE_DATA_CLEARED","android.intent.action.PACKAGE_FIRST_LAUNCH","android.intent.action.PACKAGE_FULLY_REMOVED","android.intent.action.PACKAGE_INSTALL","android.intent.action.PACKAGE_NEEDS_VERIFICATION","android.intent.action.PACKAGE_REMOVED","android.intent.action.PACKAGE_REPLACED","android.intent.action.PACKAGE_RESTARTED","android.intent.action.PACKAGE_VERIFIED","android.intent.action.PHONE_STATE","android.intent.action.PROVIDER_CHANGED","android.intent.action.PROXY_CHANGE","android.intent.action.REBOOT","android.intent.action.SCREEN_OFF","android.intent.action.SCREEN_ON","android.intent.action.TIMEZONE_CHANGED","android.intent.action.TIME_SET","android.intent.action.TIME_TICK","android.intent.action.UID_REMOVED","android.intent.action.USER_PRESENT","android.intent.action.WALLPAPER_CHANGED","android.media.ACTION_SCO_AUDIO_STATE_UPDATED","android.media.AUDIO_BECOMING_NOISY","android.media.RINGER_MODE_CHANGED","android.media.SCO_AUDIO_STATE_CHANGED","android.media.VIBRATE_SETTING_CHANGED","android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION","android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION","android.net.conn.BACKGROUND_DATA_SETTING_CHANGED","android.net.nsd.STATE_CHANGED","android.net.wifi.NETWORK_IDS_CHANGED","android.net.wifi.RSSI_CHANGED","android.net.wifi.SCAN_RESULTS","android.net.wifi.STATE_CHANGE","android.net.wifi.WIFI_STATE_CHANGED","android.net.wifi.p2p.CONNECTION_STATE_CHANGE","android.net.wifi.p2p.DISCOVERY_STATE_CHANGE","android.net.wifi.p2p.PEERS_CHANGED","android.net.wifi.p2p.STATE_CHANGED","android.net.wifi.p2p.THIS_DEVICE_CHANGED","android.net.wifi.supplicant.CONNECTION_CHANGE","android.net.wifi.supplicant.STATE_CHANGE","android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED","android.speech.tts.engine.TTS_DATA_INSTALLED"};
   String  phoneState="@relation relation\n" +
           "\n" +
           "@attribute battIsCharging {true,false}\n" +
           "@attribute battVoltage numeric\n" +
           "@attribute battTemp numeric\n" +
           "@attribute battLevel numeric\n" +
           "@attribute battLevelDiff numeric\n" +
           "@attribute binderTransaction numeric\n" +
           "@attribute binderReply numeric\n" +
           "@attribute binderAcquire numeric\n" +
           "@attribute binderRelease numeric\n" +
           "@attribute binderActiveNodes numeric\n" +
           "@attribute binderTotalNodes numeric\n" +
           "@attribute binderActiveRef numeric\n" +
           "@attribute binderTotalRef numeric\n" +
           "@attribute binderActiveDeath numeric\n" +
           "@attribute binderTotalDeath numeric\n" +
           "@attribute binderActiveTransaction numeric\n" +
           "@attribute binderTotalTransaction numeric\n" +
           "@attribute binderActiveTransactionComplete numeric\n" +
           "@attribute binderTotalTransactionComplete numeric\n" +
           "@attribute binderTotalNodesDiff numeric\n" +
           "@attribute binderTotalRefDiff numeric\n" +
           "@attribute binderTotalDeathDiff numeric\n" +
           "@attribute binderTotalTransactionDiff numeric\n" +
           "@attribute binderTotalTransactionCompleteDiff numeric\n" +
           "@attribute cpuUser numeric\n" +
           "@attribute cpuSystem numeric\n" +
           "@attribute cpuIdle numeric\n" +
           "@attribute cpuOther numeric\n" +
           "@attribute memActive numeric\n" +
           "@attribute memInactive numeric\n" +
           "@attribute memMapped numeric\n" +
           "@attribute memFreePages numeric\n" +
           "@attribute memAnonPages numeric\n" +
           "@attribute memFilePages numeric\n" +
           "@attribute memDirtyPages numeric\n" +
           "@attribute memWritebackPages numeric\n" +
           "@attribute networkTotalTXPackets numeric\n" +
           "@attribute networkTotalTXBytes numeric\n" +
           "@attribute networkTotalRXPackets numeric\n" +
           "@attribute networkTotalRXBytes numeric\n" +
           "@attribute networkTotalTXPacketsDiff numeric\n" +
           "@attribute networkTotalTXBytesDiff numeric\n" +
           "@attribute networkTotalRXPacketsDiff numeric\n" +
           "@attribute networkTotalRXBytesDiff numeric\n" +
           "@attribute classification {positive,negative}\n" +
           "\n" +
           "@data"+"\n";

   String head="@relation intents_permissions\n" +
           "@attribute android.app.action.ACTION_PASSWORD_CHANGED numeric\n" +
           "@attribute android.app.action.ACTION_PASSWORD_EXPIRING numeric\n" +
           "@attribute android.app.action.ACTION_PASSWORD_FAILED numeric\n" +
           "@attribute android.app.action.ACTION_PASSWORD_SUCCEEDED numeric\n" +
           "@attribute android.app.action.DEVICE_ADMIN_DISABLED numeric\n" +
           "@attribute android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED numeric\n" +
           "@attribute android.app.action.DEVICE_ADMIN_ENABLED numeric\n" +
           "@attribute android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.adapter.action.DISCOVERY_FINISHED numeric\n" +
           "@attribute android.bluetooth.adapter.action.DISCOVERY_STARTED numeric\n" +
           "@attribute android.bluetooth.adapter.action.LOCAL_NAME_CHANGED numeric\n" +
           "@attribute android.bluetooth.adapter.action.SCAN_MODE_CHANGED numeric\n" +
           "@attribute android.bluetooth.adapter.action.STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.device.action.ACL_CONNECTED numeric\n" +
           "@attribute android.bluetooth.device.action.ACL_DISCONNECTED numeric\n" +
           "@attribute android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED numeric\n" +
           "@attribute android.bluetooth.device.action.BOND_STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.device.action.CLASS_CHANGED numeric\n" +
           "@attribute android.bluetooth.device.action.FOUND numeric\n" +
           "@attribute android.bluetooth.device.action.NAME_CHANGED numeric\n" +
           "@attribute android.bluetooth.device.action.UUID numeric\n" +
           "@attribute android.bluetooth.devicepicker.action.DEVICE_SELECTED numeric\n" +
           "@attribute android.bluetooth.devicepicker.action.LAUNCH numeric\n" +
           "@attribute android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT numeric\n" +
           "@attribute android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED numeric\n" +
           "@attribute android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED numeric\n" +
           "@attribute android.hardware.action.NEW_PICTURE numeric\n" +
           "@attribute android.hardware.action.NEW_VIDEO numeric\n" +
           "@attribute android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS numeric\n" +
           "@attribute android.intent.action.ACTION_POWER_CONNECTED numeric\n" +
           "@attribute android.intent.action.ACTION_POWER_DISCONNECTED numeric\n" +
           "@attribute android.intent.action.ACTION_SHUTDOWN numeric\n" +
           "@attribute android.intent.action.AIRPLANE_MODE numeric\n" +
           "@attribute android.intent.action.BATTERY_CHANGED numeric\n" +
           "@attribute android.intent.action.BATTERY_LOW numeric\n" +
           "@attribute android.intent.action.BATTERY_OKAY numeric\n" +
           "@attribute android.intent.action.BOOT_COMPLETED numeric\n" +
           "@attribute android.intent.action.CAMERA_BUTTON numeric\n" +
           "@attribute android.intent.action.CONFIGURATION_CHANGED numeric\n" +
           "@attribute android.intent.action.DATE_CHANGED numeric\n" +
           "@attribute android.intent.action.DEVICE_STORAGE_LOW numeric\n" +
           "@attribute android.intent.action.DEVICE_STORAGE_OK numeric\n" +
           "@attribute android.intent.action.DOCK_EVENT numeric\n" +
           "@attribute android.intent.action.DREAMING_STARTED numeric\n" +
           "@attribute android.intent.action.DREAMING_STOPPED numeric\n" +
           "@attribute android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE numeric\n" +
           "@attribute android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE numeric\n" +
           "@attribute android.intent.action.FETCH_VOICEMAIL numeric\n" +
           "@attribute android.intent.action.GTALK_CONNECTED numeric\n" +
           "@attribute android.intent.action.GTALK_DISCONNECTED numeric\n" +
           "@attribute android.intent.action.HEADSET_PLUG numeric\n" +
           "@attribute android.intent.action.INPUT_METHOD_CHANGED numeric\n" +
           "@attribute android.intent.action.LOCALE_CHANGED numeric\n" +
           "@attribute android.intent.action.MANAGE_PACKAGE_STORAGE numeric\n" +
           "@attribute android.intent.action.MEDIA_BAD_REMOVAL numeric\n" +
           "@attribute android.intent.action.MEDIA_BUTTON numeric\n" +
           "@attribute android.intent.action.MEDIA_CHECKING numeric\n" +
           "@attribute android.intent.action.MEDIA_EJECT numeric\n" +
           "@attribute android.intent.action.MEDIA_MOUNTED numeric\n" +
           "@attribute android.intent.action.MEDIA_NOFS numeric\n" +
           "@attribute android.intent.action.MEDIA_REMOVED numeric\n" +
           "@attribute android.intent.action.MEDIA_SCANNER_FINISHED numeric\n" +
           "@attribute android.intent.action.MEDIA_SCANNER_SCAN_FILE numeric\n" +
           "@attribute android.intent.action.MEDIA_SCANNER_STARTED numeric\n" +
           "@attribute android.intent.action.MEDIA_SHARED numeric\n" +
           "@attribute android.intent.action.MEDIA_UNMOUNTABLE numeric\n" +
           "@attribute android.intent.action.MEDIA_UNMOUNTED numeric\n" +
           "@attribute android.intent.action.MY_PACKAGE_REPLACED numeric\n" +
           "@attribute android.intent.action.NEW_OUTGOING_CALL numeric\n" +
           "@attribute android.intent.action.NEW_VOICEMAIL numeric\n" +
           "@attribute android.intent.action.PACKAGE_ADDED numeric\n" +
           "@attribute android.intent.action.PACKAGE_CHANGED numeric\n" +
           "@attribute android.intent.action.PACKAGE_DATA_CLEARED numeric\n" +
           "@attribute android.intent.action.PACKAGE_FIRST_LAUNCH numeric\n" +
           "@attribute android.intent.action.PACKAGE_FULLY_REMOVED numeric\n" +
           "@attribute android.intent.action.PACKAGE_INSTALL numeric\n" +
           "@attribute android.intent.action.PACKAGE_NEEDS_VERIFICATION numeric\n" +
           "@attribute android.intent.action.PACKAGE_REMOVED numeric\n" +
           "@attribute android.intent.action.PACKAGE_REPLACED numeric\n" +
           "@attribute android.intent.action.PACKAGE_RESTARTED numeric\n" +
           "@attribute android.intent.action.PACKAGE_VERIFIED numeric\n" +
           "@attribute android.intent.action.PHONE_STATE numeric\n" +
           "@attribute android.intent.action.PROVIDER_CHANGED numeric\n" +
           "@attribute android.intent.action.PROXY_CHANGE numeric\n" +
           "@attribute android.intent.action.REBOOT numeric\n" +
           "@attribute android.intent.action.SCREEN_OFF numeric\n" +
           "@attribute android.intent.action.SCREEN_ON numeric\n" +
           "@attribute android.intent.action.TIMEZONE_CHANGED numeric\n" +
           "@attribute android.intent.action.TIME_SET numeric\n" +
           "@attribute android.intent.action.TIME_TICK numeric\n" +
           "@attribute android.intent.action.UID_REMOVED numeric\n" +
           "@attribute android.intent.action.USER_PRESENT numeric\n" +
           "@attribute android.intent.action.WALLPAPER_CHANGED numeric\n" +
           "@attribute android.media.ACTION_SCO_AUDIO_STATE_UPDATED numeric\n" +
           "@attribute android.media.AUDIO_BECOMING_NOISY numeric\n" +
           "@attribute android.media.RINGER_MODE_CHANGED numeric\n" +
           "@attribute android.media.SCO_AUDIO_STATE_CHANGED numeric\n" +
           "@attribute android.media.VIBRATE_SETTING_CHANGED numeric\n" +
           "@attribute android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION numeric\n" +
           "@attribute android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION numeric\n" +
           "@attribute android.net.conn.BACKGROUND_DATA_SETTING_CHANGED numeric\n" +
           "@attribute android.net.nsd.STATE_CHANGED numeric\n" +
           "@attribute android.net.wifi.NETWORK_IDS_CHANGED numeric\n" +
           "@attribute android.net.wifi.RSSI_CHANGED numeric\n" +
           "@attribute android.net.wifi.SCAN_RESULTS numeric\n" +
           "@attribute android.net.wifi.STATE_CHANGE numeric\n" +
           "@attribute android.net.wifi.WIFI_STATE_CHANGED numeric\n" +
           "@attribute android.net.wifi.p2p.CONNECTION_STATE_CHANGE numeric\n" +
           "@attribute android.net.wifi.p2p.DISCOVERY_STATE_CHANGE numeric\n" +
           "@attribute android.net.wifi.p2p.PEERS_CHANGED numeric\n" +
           "@attribute android.net.wifi.p2p.STATE_CHANGED numeric\n" +
           "@attribute android.net.wifi.p2p.THIS_DEVICE_CHANGED numeric\n" +
           "@attribute android.net.wifi.supplicant.CONNECTION_CHANGE numeric\n" +
           "@attribute android.net.wifi.supplicant.STATE_CHANGE numeric\n" +
           "@attribute android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED numeric\n" +
           "@attribute android.speech.tts.engine.TTS_DATA_INSTALLED numeric\n" +
           "@attribute ACCESS_CHECKIN_PROPERTIES numeric\n" +
           "@attribute ACCESS_COARSE_LOCATION numeric\n" +
           "@attribute ACCESS_FINE_LOCATION numeric\n" +
           "@attribute ACCESS_LOCATION_EXTRA_COMMANDS numeric\n" +
           "@attribute ACCESS_MOCK_LOCATION numeric\n" +
           "@attribute ACCESS_NETWORK_STATE numeric\n" +
           "@attribute ACCESS_SURFACE_FLINGER numeric\n" +
           "@attribute ACCESS_WIFI_STATE numeric\n" +
           "@attribute ACCOUNT_MANAGER numeric\n" +
           "@attribute ADD_VOICEMAIL numeric\n" +
           "@attribute AUTHENTICATE_ACCOUNTS numeric\n" +
           "@attribute BATTERY_STATS numeric\n" +
           "@attribute BIND_ACCESSIBILITY_SERVICE numeric\n" +
           "@attribute BIND_APPWIDGET numeric\n" +
           "@attribute BIND_CARRIER_MESSAGING_SERVICE numeric\n" +
           "@attribute BIND_DEVICE_ADMIN numeric\n" +
           "@attribute BIND_DREAM_SERVICE numeric\n" +
           "@attribute BIND_INPUT_METHOD numeric\n" +
           "@attribute BIND_NFC_SERVICE numeric\n" +
           "@attribute BIND_NOTIFICATION_LISTENER_SERVICE numeric\n" +
           "@attribute BIND_PRINT_SERVICE numeric\n" +
           "@attribute BIND_REMOTEVIEWS numeric\n" +
           "@attribute BIND_TEXT_SERVICE numeric\n" +
           "@attribute BIND_TV_INPUT numeric\n" +
           "@attribute BIND_VOICE_INTERACTION numeric\n" +
           "@attribute BIND_VPN_SERVICE numeric\n" +
           "@attribute BIND_WALLPAPER numeric\n" +
           "@attribute BLUETOOTH numeric\n" +
           "@attribute BLUETOOTH_ADMIN numeric\n" +
           "@attribute BLUETOOTH_PRIVILEGED numeric\n" +
           "@attribute BODY_SENSORS numeric\n" +
           "@attribute BRICK numeric\n" +
           "@attribute BROADCAST_PACKAGE_REMOVED numeric\n" +
           "@attribute BROADCAST_SMS numeric\n" +
           "@attribute BROADCAST_STICKY numeric\n" +
           "@attribute BROADCAST_WAP_PUSH numeric\n" +
           "@attribute CALL_PHONE numeric\n" +
           "@attribute CALL_PRIVILEGED numeric\n" +
           "@attribute CAMERA numeric\n" +
           "@attribute CAPTURE_AUDIO_OUTPUT numeric\n" +
           "@attribute CAPTURE_SECURE_VIDEO_OUTPUT numeric\n" +
           "@attribute CAPTURE_VIDEO_OUTPUT numeric\n" +
           "@attribute CHANGE_COMPONENT_ENABLED_STATE numeric\n" +
           "@attribute CHANGE_CONFIGURATION numeric\n" +
           "@attribute CHANGE_NETWORK_STATE numeric\n" +
           "@attribute CHANGE_WIFI_MULTICAST_STATE numeric\n" +
           "@attribute CHANGE_WIFI_STATE numeric\n" +
           "@attribute CLEAR_APP_CACHE numeric\n" +
           "@attribute CLEAR_APP_USER_DATA numeric\n" +
           "@attribute CONTROL_LOCATION_UPDATES numeric\n" +
           "@attribute DELETE_CACHE_FILES numeric\n" +
           "@attribute DELETE_PACKAGES numeric\n" +
           "@attribute DEVICE_POWER numeric\n" +
           "@attribute DIAGNOSTIC numeric\n" +
           "@attribute DISABLE_KEYGUARD numeric\n" +
           "@attribute DUMP numeric\n" +
           "@attribute EXPAND_STATUS_BAR numeric\n" +
           "@attribute FACTORY_TEST numeric\n" +
           "@attribute FLASHLIGHT numeric\n" +
           "@attribute FORCE_BACK numeric\n" +
           "@attribute GET_ACCOUNTS numeric\n" +
           "@attribute GET_PACKAGE_SIZE numeric\n" +
           "@attribute GET_TASKS numeric\n" +
           "@attribute GET_TOP_ACTIVITY_INFO numeric\n" +
           "@attribute GLOBAL_SEARCH numeric\n" +
           "@attribute HARDWARE_TEST numeric\n" +
           "@attribute INJECT_EVENTS numeric\n" +
           "@attribute INSTALL_LOCATION_PROVIDER numeric\n" +
           "@attribute INSTALL_PACKAGES numeric\n" +
           "@attribute INSTALL_SHORTCUT numeric\n" +
           "@attribute INTERNAL_SYSTEM_WINDOW numeric\n" +
           "@attribute INTERNET numeric\n" +
           "@attribute KILL_BACKGROUND_PROCESSES numeric\n" +
           "@attribute LOCATION_HARDWARE numeric\n" +
           "@attribute MANAGE_ACCOUNTS numeric\n" +
           "@attribute MANAGE_APP_TOKENS numeric\n" +
           "@attribute MANAGE_DOCUMENTS numeric\n" +
           "@attribute MASTER_CLEAR numeric\n" +
           "@attribute MEDIA_CONTENT_CONTROL numeric\n" +
           "@attribute MODIFY_AUDIO_SETTINGS numeric\n" +
           "@attribute MODIFY_PHONE_STATE numeric\n" +
           "@attribute MOUNT_FORMAT_FILESYSTEMS numeric\n" +
           "@attribute MOUNT_UNMOUNT_FILESYSTEMS numeric\n" +
           "@attribute NFC numeric\n" +
           "@attribute PERSISTENT_ACTIVITY numeric\n" +
           "@attribute PROCESS_OUTGOING_CALLS numeric\n" +
           "@attribute READ_CALENDAR numeric\n" +
           "@attribute READ_CALL_LOG numeric\n" +
           "@attribute READ_CONTACTS numeric\n" +
           "@attribute READ_EXTERNAL_STORAGE numeric\n" +
           "@attribute READ_FRAME_BUFFER numeric\n" +
           "@attribute READ_HISTORY_BOOKMARKS numeric\n" +
           "@attribute READ_INPUT_STATE numeric\n" +
           "@attribute READ_LOGS numeric\n" +
           "@attribute READ_PHONE_STATE numeric\n" +
           "@attribute READ_PROFILE numeric\n" +
           "@attribute READ_SMS numeric\n" +
           "@attribute READ_SOCIAL_STREAM numeric\n" +
           "@attribute READ_SYNC_SETTINGS numeric\n" +
           "@attribute READ_SYNC_STATS numeric\n" +
           "@attribute READ_USER_DICTIONARY numeric\n" +
           "@attribute READ_VOICEMAIL numeric\n" +
           "@attribute REBOOT numeric\n" +
           "@attribute RECEIVE_BOOT_COMPLETED numeric\n" +
           "@attribute RECEIVE_MMS numeric\n" +
           "@attribute RECEIVE_SMS numeric\n" +
           "@attribute RECEIVE_WAP_PUSH numeric\n" +
           "@attribute RECORD_AUDIO numeric\n" +
           "@attribute REORDER_TASKS numeric\n" +
           "@attribute RESTART_PACKAGES numeric\n" +
           "@attribute SEND_RESPOND_VIA_MESSAGE numeric\n" +
           "@attribute SEND_SMS numeric\n" +
           "@attribute SET_ACTIVITY_WATCHER numeric\n" +
           "@attribute SET_ALARM numeric\n" +
           "@attribute SET_ALWAYS_FINISH numeric\n" +
           "@attribute SET_ANIMATION_SCALE numeric\n" +
           "@attribute SET_DEBUG_APP numeric\n" +
           "@attribute SET_ORIENTATION numeric\n" +
           "@attribute SET_POINTER_SPEED numeric\n" +
           "@attribute SET_PREFERRED_APPLICATIONS numeric\n" +
           "@attribute SET_PROCESS_LIMIT numeric\n" +
           "@attribute SET_TIME numeric\n" +
           "@attribute SET_TIME_ZONE numeric\n" +
           "@attribute SET_WALLPAPER numeric\n" +
           "@attribute SET_WALLPAPER_HINTS numeric\n" +
           "@attribute SIGNAL_PERSISTENT_PROCESSES numeric\n" +
           "@attribute STATUS_BAR numeric\n" +
           "@attribute SUBSCRIBED_FEEDS_READ numeric\n" +
           "@attribute SUBSCRIBED_FEEDS_WRITE numeric\n" +
           "@attribute SYSTEM_ALERT_WINDOW numeric\n" +
           "@attribute TRANSMIT_IR numeric\n" +
           "@attribute UNINSTALL_SHORTCUT numeric\n" +
           "@attribute UPDATE_DEVICE_STATS numeric\n" +
           "@attribute USE_CREDENTIALS numeric\n" +
           "@attribute USE_SIP numeric\n" +
           "@attribute VIBRATE numeric\n" +
           "@attribute WAKE_LOCK numeric\n" +
           "@attribute WRITE_APN_SETTINGS numeric\n" +
           "@attribute WRITE_CALENDAR numeric\n" +
           "@attribute WRITE_CALL_LOG numeric\n" +
           "@attribute WRITE_CONTACTS numeric\n" +
           "@attribute WRITE_EXTERNAL_STORAGE numeric\n" +
           "@attribute WRITE_GSERVICES numeric\n" +
           "@attribute WRITE_HISTORY_BOOKMARKS numeric\n" +
           "@attribute WRITE_PROFILE numeric\n" +
           "@attribute WRITE_SECURE_SETTINGS numeric\n" +
           "@attribute WRITE_SETTINGS numeric\n" +
           "@attribute WRITE_SMS numeric\n" +
           "@attribute WRITE_SOCIAL_STREAM numeric\n" +
           "@attribute WRITE_SYNC_SETTINGS numeric\n" +
           "@attribute WRITE_USER_DICTIONARY numeric\n" +
           "@attribute WRITE_VOICEMAIL numeric\n" +
           "@attribute android.hardware.audio.low_latency numeric\n" +
           "@attribute android.hardware.bluetooth numeric\n" +
           "@attribute android.hardware.bluetooth_le numeric\n" +
           "@attribute android.hardware.camera numeric\n" +
           "@attribute android.hardware.camera.autofocus numeric\n" +
           "@attribute android.hardware.camera.flash numeric\n" +
           "@attribute android.hardware.camera.front numeric\n" +
           "@attribute android.hardware.camera.any numeric\n" +
           "@attribute android.hardware.camera.external numeric\n" +
           "@attribute android.hardware.camera.level.full numeric\n" +
           "@attribute android.hardware.camera.capability.manual_sensor numeric\n" +
           "@attribute android.hardware.camera.capability.manual_post_processing numeric\n" +
           "@attribute android.hardware.camera.capability.raw numeric\n" +
           "@attribute android.hardware.consumerir numeric\n" +
           "@attribute android.hardware.location numeric\n" +
           "@attribute android.hardware.location.network numeric\n" +
           "@attribute android.hardware.location.gps numeric\n" +
           "@attribute android.hardware.nfc numeric\n" +
           "@attribute android.hardware.nfc.hce numeric\n" +
           "@attribute android.hardware.sensor.accelerometer numeric\n" +
           "@attribute android.hardware.sensor.barometer numeric\n" +
           "@attribute android.hardware.sensor.compass numeric\n" +
           "@attribute android.hardware.sensor.gyroscope numeric\n" +
           "@attribute android.hardware.sensor.light numeric\n" +
           "@attribute android.hardware.sensor.proximity numeric\n" +
           "@attribute android.hardware.sensor.stepcounter numeric\n" +
           "@attribute android.hardware.sensor.stepdetector numeric\n" +
           "@attribute android.hardware.screen.landscape numeric\n" +
           "@attribute android.hardware.screen.portrait numeric\n" +
           "@attribute android.hardware.telephony numeric\n" +
           "@attribute android.hardware.telephony.cdma numeric\n" +
           "@attribute android.hardware.telephony.gsm numeric\n" +
           "@attribute android.hardware.type.television numeric\n" +
           "@attribute android.hardware.faketouch numeric\n" +
           "@attribute android.hardware.faketouch.multitouch.distinct numeric\n" +
           "@attribute android.hardware.faketouch.multitouch.jazzhand numeric\n" +
           "@attribute android.hardware.touchscreen numeric\n" +
           "@attribute android.hardware.touchscreen.multitouch numeric\n" +
           "@attribute android.hardware.touchscreen.multitouch.distinct numeric\n" +
           "@attribute android.hardware.touchscreen.multitouch.jazzhand numeric\n" +
           "@attribute android.hardware.usb.host numeric\n" +
           "@attribute android.hardware.usb.accessory numeric\n" +
           "@attribute android.hardware.wifi numeric\n" +
           "@attribute android.hardware.wifi.direc numeric\n" +
           "@attribute android.software.app_widgets numeric\n" +
           "@attribute android.software.device_admin numeric\n" +
           "@attribute android.software.home_screen numeric\n" +
           "@attribute android.software.input_methods numeric\n" +
           "@attribute android.software.live_wallpaper numeric\n" +
           "@attribute android.software.sip numeric\n" +
           "@attribute android.software.sip.voip numeric\n" +
           "@attribute android.hardware.microphone numeric\n" +
           "@attribute class {malware,benign}\n" +
           "\n" +
           "@data\n";
    private static final int FILE_SELECT_CODE = 0;
    HashMap <String,String> wt = new HashMap<>();
    String appname = "";
    ArrayList<String> permissions = new ArrayList();
    ArrayList<String> activities = new ArrayList();
    ArrayList<String> services = new ArrayList();
    ArrayList<String> brs = new ArrayList();
    ArrayList<String> intents = new ArrayList();
    ArrayList<String> uses_feature= new ArrayList();
    ListView lv;
    File myFile;
    static String path;
    double [] score;
    Spinner sp ;
    TextView tv ;
    private String url="http://droidanalyst.org/droidanalyst/andrologs";

    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select APK File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void updateUI(String prediction) {
        sp.setSelection(0);
        ArrayAdapter ad = new ArrayAdapter(getActivity(), R.layout.list_item, permissions);
        lv.setAdapter(ad);
        TextView tv = (TextView)getActivity().findViewById(R.id.result);
        tv.setText(appname+" App is probably "+prediction);
        String[] stockArr = new String[permissions.size()];
        stockArr = permissions.toArray(stockArr);
        DScore.perms=stockArr;
        DScore.dscore=score[0]+"";
        File nf = new File(Environment.getExternalStorageDirectory(), "Draco");
        File fn = new File(nf,"server.arff");
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(nf,"server.arff"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.write(phoneState.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getActivity().registerReceiver(null, ifilter);
        BatteryFeatures btf = new BatteryFeatures();
        btf.setIntent(batteryStatus);
        btf.fetchData();
        BinderFeatures bdf = new BinderFeatures();
        MemoryFeatures mf = new MemoryFeatures();
        CPUFeatures cpf = new CPUFeatures();
        NetworkFeatures netf = new NetworkFeatures();
        ArrayList<String>networkData = netf.getData();
        Log.i("nd size",networkData.size()+"");
        ArrayList<String> cpuData= cpf.getData();
        Log.i("cpusize",cpuData.size()+"");
        ArrayList<String>memoryData= mf.getData();
        Log.i("mem size",memoryData.size()+"");
        ArrayList<String>binderData=bdf.getData();
        /*We don;t have binder stats file on this android ,may be 4.x ?*/
        Log.i("bind size",binderData.size()+"");
        if(binderData.size()<19) {
            binderData=new ArrayList<>(19);
            for(int i=0;i<19;i++) {
                binderData.add("?");
            }
        }
        ArrayList<String> batteryData = btf.getData();
        Log.i("bt size", batteryData.size()+"");
        ArrayList<String> allData=new ArrayList<>();
        allData.addAll(batteryData);
        allData.addAll(binderData);
        allData.addAll(cpuData);
        allData.addAll(memoryData);
        allData.addAll(networkData);

        for(String str: allData) {
            try {
                writer.write(str);
                writer.write(",");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.write("?");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        try {
            params.put("deviceid", Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID));
            params.put("logs", fn);
        } catch(FileNotFoundException e) {}
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("Success ",""+statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Failure ",""+statusCode+" "+responseBody+" "+error);

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // Get the path
                    path = null;
                    try {
                        path = NewAnalysis.getPath(getActivity(), uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    try {
                        new AnalyzeAPK().execute(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_upload, null);
        String p=null;

        ((MainActivity)getActivity()).setActionBarTitle("DraCo Analysis");
        try{
            p = this.getArguments().getString("path");
        }
        catch (NullPointerException npe) {
            Log.i("fragment created","");
        }

        Context context = getActivity();
        Signature[] sigs = new Signature[0];
        try {
            sigs = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for (Signature sig : sigs)
        {
            Log.i("App", "Signature : " + sig.hashCode());
        }
        sp= (Spinner)root.findViewById(R.id.choice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.choices_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        tv=(TextView)root.findViewById(R.id.result);
        lv = (ListView)root.findViewById(R.id.items);
        sp.setOnItemSelectedListener(NewAnalysis.this);
        Button uploadButton =(Button)root.findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(path==null) {
                    Toast.makeText(getActivity(),"APK Path empty!",Toast.LENGTH_SHORT).show();
                }
                else {
                    RequestParams params = new RequestParams();
                    try {
                        Log.i("regidNN",""+MainActivity.regid);
                        params.put("regid",MainActivity.regid);
                        params.put("deviceid", Settings.Secure.getString(getActivity().getContentResolver(),
                                Settings.Secure.ANDROID_ID));
                        File serverFile=new File(path);
                        params.put("apk",serverFile);
                    } catch(FileNotFoundException e) {}
                    AsyncHttpClient client = new AsyncHttpClient();
                    Toast.makeText(getActivity(),"Upload Started",Toast.LENGTH_LONG).show();
                    client.post(url, params, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            Log.i("Success ",""+statusCode);
                            Toast.makeText(getActivity(),"APK Uploaded Successfully",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.i("Failure ",""+statusCode+" "+responseBody+" "+error);
                            Toast.makeText(getActivity(),"Not Connected to Internet!!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        Button chooser = (Button)root.findViewById(R.id.chooser);
        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        if(p!=null) {
            path=p;
            try {
                Log.i("path is",path);
                new AnalyzeAPK().execute(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return root;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(myFile!=null) {
        if(position==0) {
            ArrayAdapter ad = new ArrayAdapter(getActivity(),R.layout.list_item,permissions);
            lv.setAdapter(ad);
        }
        else if(position==1) {
            ArrayAdapter ad = new ArrayAdapter(getActivity(),R.layout.list_item,activities);
            lv.setAdapter(ad);
        }
        else if(position==2) {
            ArrayAdapter ad = new ArrayAdapter(getActivity(),R.layout.list_item,brs);
            lv.setAdapter(ad);
        }
        else if(position==3) {
            ArrayAdapter ad = new ArrayAdapter(getActivity(),R.layout.list_item,intents);
            lv.setAdapter(ad);
        }
        else if(position==4) {
            ArrayAdapter ad = new ArrayAdapter(getActivity(),R.layout.list_item,services);
            lv.setAdapter(ad);
        }
        else if(position==5) {
            ArrayAdapter ad = new ArrayAdapter(getActivity(),R.layout.list_item,uses_feature);
            lv.setAdapter(ad);
        }
        }
        else {
            Toast.makeText(getActivity(),"Choose APK first",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if(myFile!=null) {
            ArrayAdapter ad = new ArrayAdapter(getActivity(), R.layout.list_item, permissions);
            lv.setAdapter(ad);
        }
    }


    private class AnalyzeAPK extends AsyncTask<String,Void,String> {


        ProgressDialog waiting = new ProgressDialog(getActivity());
        @Override
        protected String doInBackground(String ...params) {

            try {
                return uploadFile(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
               return  "";
        }

        @Override
        protected void onPreExecute() {
            this.waiting.setMessage("Analyzing APK");
            this.waiting.show();
            this.waiting.setIndeterminate(true);
        }

        @Override
        protected void onPostExecute(String result) {
            if(this.waiting.isShowing()) {
                this.waiting.dismiss();
            }
            if(result.length()>1) {
                Toast.makeText(getActivity(),"APK Analyzed Successfully",Toast.LENGTH_SHORT).show();
                updateUI(result);
            }
            else{
                Toast.makeText(getActivity(),"Failure Occured",Toast.LENGTH_SHORT).show();
            }
        }

        public String uploadFile(String path) throws IOException {
            Log.i("path is","uploadFile  "+path);
            String result ="";
            myFile = new File(path);
            permissions.clear();
            services.clear();
            intents.clear();
            activities.clear();
            uses_feature.clear();
            ApkParser apkParser = new ApkParser(myFile) ;
            String mxml=null;
            try {
                mxml = apkParser.getManifestXml();
            }
            catch (Exception e) {
                Toast.makeText(getActivity(),"Invalid Apk File",Toast.LENGTH_SHORT).show();
            }
            if(mxml==null){
                Toast.makeText(getActivity(),"Invalid Apk File",Toast.LENGTH_SHORT).show();
            }
            else {
                appname = apkParser.getApkMeta().getLabel();
                ApkMeta apkMeta = apkParser.getApkMeta();
                for (String perms : apkMeta.getUsesPermissions()) {
                    permissions.add(perms);
                }
                for (net.dongliu.apk.parser.bean.Activity perms : apkMeta.getActivities()) {
                    activities.add(perms.toString());
                }
                for (Service perms : apkMeta.getServices()) {
                    services.add(perms.toString());
                }
                for (Receiver perms : apkMeta.getReceivers()) {
                    brs.add(perms.toString());
                }
                for (net.dongliu.apk.parser.bean.IntentFilter perms : apkMeta.getIntentFilters()) {
                    if(perms.getActions().size()>0) {
                        intents.add(perms.getActions().get(0));
                        System.out.println("Intents" + perms.toString());
                    }

                }
                for (UseFeature feature: apkMeta.getUsesFeatures()) {
                    uses_feature.add(feature.getName());
                    System.out.println(feature.getName());
                }
                ArrayList<String> allFeatures=new ArrayList<>();
                allFeatures.addAll(permissions);
                allFeatures.addAll(intents);
                allFeatures.addAll(uses_feature);
                DScore.featuresForWeights=allFeatures.toArray(new String[allFeatures.size()]);
                result = createarff();
            }
            return result;
        }
        public String createarff() {
            File file = null;
            String prediction="";
            try {
                File newFolder = new File(Environment.getExternalStorageDirectory(), "Draco");
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                    File []a=newFolder.listFiles();
                    a[0].delete();
                }
                try {
                    file = new File(newFolder,"server"+".arff");
                    file.createNewFile();
                    file = new File(newFolder, "test" + ".arff");
                    file.createNewFile();
                } catch (Exception ex) {
                    System.out.println("ex: " + ex);
                }
            } catch (Exception e) {
                System.out.println("e: " + e);
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int flag=0;
            String tr="1,";
            String fl="0,";
            String qm="?";
            try {
                fos.write(head.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
           for(int i=0;i<intent_actions.length;i++) {
                flag=0;
                for(int j=0;j<intents.size();j++) {
                    String temp=intents.get(j);
                        if (intent_actions[i].equals(temp)) {
                            flag = 1;
                            break;
                    }
                }
                if(flag==1) {
                    try {
                        fos.write(tr.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        fos.write(fl.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            for(int i=0;i<Perms.length;i++) {
                flag=0;
                for(int j=0;j<permissions.size();j++) {
                    String temp=permissions.get(j);
                    String [] tempp= StringUtils.split(temp,".");
                    if(tempp.length>2) {
                        temp = tempp[2];
                        if (Perms[i].equals(temp)) {
                            flag = 1;
                            break;

                        }
                    }
                }
                if(flag==1) {
                    try {
                        fos.write(tr.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        fos.write(fl.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            for(int i=0;i<features.length;i++) {
                flag=0;
                for(int j=0;j<uses_feature.size();j++) {
                    String temp=uses_feature.get(j);

                        if (features[i].equals(temp)) {
                            flag = 1;
                            break;

                    }
                }
                if(flag==1) {
                    try {
                        fos.write(tr.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        fos.write(fl.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                fos.write(qm.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File nf = new File(Environment.getExternalStorageDirectory(), "Draco");
            File fn = new File(nf,"test.arff");

        BufferedReader reader;
            InputStream is = null;
            try {
                is = getActivity().getAssets().open("allthreeNB.model");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Classifier cls = (Classifier) weka.core.SerializationHelper.read(is);

                reader = new BufferedReader(new FileReader(fn));
                Instances test = new Instances(reader);
                test.setClassIndex(test.numAttributes() - 1);
                int s1 = 0;
                score = cls.distributionForInstance(test.instance(s1));
                Log.i("D-Score", "" + score[0] + "," + score.length);
                double val = cls.classifyInstance(test.instance(s1));
                prediction = test.classAttribute().value((int) val);

            } catch (Exception e) {

                e.printStackTrace();
            }
            return prediction;
        }
    }

}
