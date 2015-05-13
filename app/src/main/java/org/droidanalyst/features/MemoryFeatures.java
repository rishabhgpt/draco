package org.droidanalyst.features;

import java.util.ArrayList;

public class MemoryFeatures {
	
	private int _active;
	private int _inactive;
	
	private int _mapped;
	
	
	private int _freePages;
	private int _anonPages;
	private int _filePages;
	private int _dirtyPages;
	private int _writebackPages;

	private FileParser _fp;

	public ArrayList<String> memFeatures=new ArrayList<>();
	public MemoryFeatures() {
		fetchData();
	}
	
	public void fetchData() {
		try
	    {
            _fp=new FileParser();
			_fp.load( "/proc/meminfo" );
			
			_active = _fp.readInt( 5, 1 );
			_inactive = _fp.readInt( 6, 1 );
			
			_mapped = _fp.readInt( 18, 1 );
			
			_fp.close();
			
			
			_fp.load( "/proc/vmstat" );
			
			_freePages = _fp.readInt( 0, 1 );
			_anonPages = _fp.readInt( 7, 1 );
			_filePages = _fp.readInt( 9, 1 );
			_dirtyPages = _fp.readInt( 10, 1 );
			_writebackPages = _fp.readInt( 11, 1 );
            memFeatures.add(_active+"");
            memFeatures.add(_inactive+"");
            memFeatures.add(_mapped+"");
            memFeatures.add(_freePages+"");
            memFeatures.add(_anonPages+"");
            memFeatures.add(_filePages+"");
            memFeatures.add(_dirtyPages+"");
            memFeatures.add(_writebackPages+"");
			_fp.close();
			
	    }
	    catch( Exception ex )
	    {
	        ex.printStackTrace();           
	    }
	}
	public ArrayList<String>getData(){
        return memFeatures;
    }
	
}
