package org.droidanalyst.features;

import android.content.Intent;
import android.os.BatteryManager;

import java.util.ArrayList;

public class BatteryFeatures {
	private Intent _intent;
	
	private boolean _isCharging = false;
	private int _voltage = 0;
	private int _temp = 0;
	private double _level = 0;
	private double _levelDiff = 0;
    ArrayList<String>batteryFeatures= new ArrayList();
	public BatteryFeatures() {

	}
    public void setIntent(Intent intent) {
        _intent = intent;
    }

    public void fetchData() {
		boolean isCharging = false;
		int voltage = -1;
		
		int temp = -1;
		double level = -1;
		int status = _intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		
		isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
				status == BatteryManager.BATTERY_STATUS_FULL;
		
		voltage = _intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
		temp = _intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
		
		
		int scale = _intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		level = (double) _intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)/scale * 100;
		
		_isCharging = isCharging;
        batteryFeatures.add(_isCharging+"");
		_voltage = voltage;
        batteryFeatures.add(_voltage+"");
		_temp = temp;
        batteryFeatures.add(_temp+"");
		_levelDiff = level - _level;
        batteryFeatures.add(_levelDiff+"");
		_level = level;
        batteryFeatures.add(_level+"");
	}
    public ArrayList<String>getData(){
        return batteryFeatures;
    }

}
