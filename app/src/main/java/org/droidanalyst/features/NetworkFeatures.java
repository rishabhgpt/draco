package org.droidanalyst.features;

import android.net.TrafficStats;

import java.util.ArrayList;


public class NetworkFeatures {
	private long _totalTXPackets;
	private long _totalTXBytes;
	private long _totalRXPackets;
	private long _totalRXBytes;

	private long _totalTXPacketsDiff;
	private long _totalTXBytesDiff;
	private long _totalRXPacketsDiff;
	private long _totalRXBytesDiff;

    private ArrayList<String> networkFeatures = new ArrayList<>();


    public NetworkFeatures() {
	fetchData();
	}
	
	public void fetchData() {
		
		long totalTXPackets = TrafficStats.getTotalTxPackets();
		long totalTXBytes = TrafficStats.getTotalTxBytes();
		long totalRXPackets = TrafficStats.getTotalRxPackets();
		long totalRXBytes = TrafficStats.getTotalRxBytes();
		
		_totalTXPacketsDiff = totalTXPackets - _totalTXPackets;
		_totalTXBytesDiff = totalTXBytes - _totalTXBytes;
		_totalRXPacketsDiff = totalRXPackets - _totalRXPackets;
		_totalRXBytesDiff = totalRXBytes - _totalRXBytes;
		
		_totalTXPackets = totalTXPackets;
		_totalTXBytes = totalTXBytes;
		_totalRXPackets = totalRXPackets;
		_totalRXBytes = totalRXBytes;
        networkFeatures.add(totalTXBytes+"");
        networkFeatures.add(totalTXBytes+"");
        networkFeatures.add(totalRXPackets+"");
        networkFeatures.add(totalRXBytes+"");
        networkFeatures.add(_totalTXPacketsDiff+"");
        networkFeatures.add(_totalTXBytesDiff+"");
        networkFeatures.add(_totalRXPacketsDiff+"");
        networkFeatures.add(_totalTXBytesDiff+"");

	}
	

    public ArrayList<String>getData(){
        return networkFeatures;
    }
}
