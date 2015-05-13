package org.droidanalyst.features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CPUFeatures  {
    private int _cpuUser = 0;
    private int _cpuSystem = 0;
    private int _cpuIdle = 0;
    private int _cpuOther = 0;
    private ArrayList<String> cpuFeatures = new ArrayList<>();


    public CPUFeatures() {
		fetchData();
	}
	
	public void fetchData() {
	    String topOut = executeTop();
	    topOut = cleanTopStr(topOut);
	    int[] cpuUsage = extractTokens(topOut);
	    
	    _cpuUser = cpuUsage[0];
	    _cpuSystem = cpuUsage[1];
	    _cpuIdle = cpuUsage[2];
	    _cpuOther = cpuUsage[3];
        cpuFeatures.add(_cpuUser+"");
        cpuFeatures.add(_cpuSystem+"");
        cpuFeatures.add(_cpuIdle+"");
        cpuFeatures.add(_cpuOther+"");
	}

	

	private String executeTop() {
	    java.lang.Process p = null;
	    BufferedReader in = null;
	    String topOut = "";
	    try {
	        p = Runtime.getRuntime().exec("top -n 1");
	        in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        while (topOut == null || topOut.contentEquals("")) {
	            topOut = in.readLine();
	        }
	    } catch (IOException e) {
	    } finally {
	        try {
	            in.close();
	            p.destroy();
	        } catch (IOException e) { }
	    }
	    return topOut;
	}
	
	private String cleanTopStr(String str) {
		str = str.replaceAll(",", "");
	    str = str.replaceAll("User", "");
	    str = str.replaceAll("System", "");
	    str = str.replaceAll("IOW", "");
	    str = str.replaceAll("IRQ", "");
	    str = str.replaceAll("%", "");
	    for (int i = 0; i < 10; i++) {
	        str = str.replaceAll("  ", " ");
	    }
	    str = str.trim();
	    return str;
	}
	
	private int[] extractTokens(String str) {
		int[] cpuUsage = new int[4];
		String[] topOutToks = str.split(" ");
	    
	    for (int i = 0; i < 4; i++) {
	        topOutToks[i] = topOutToks[i].trim();
	        cpuUsage[i] = Integer.parseInt(topOutToks[i]);
	    }
	    return cpuUsage;
	}
    public ArrayList<String>getData() {
        return cpuFeatures;
    }
}
