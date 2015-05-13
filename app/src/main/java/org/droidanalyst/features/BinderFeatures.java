package org.droidanalyst.features;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class BinderFeatures  {
	int _transaction;
	int _reply;
	int _acquire;
	int _release;
	
	int _activeNodes;
	int _totalNodes;
	
	int _activeRef;
	int _totalRef;
	
	int _activeDeath;
	int _totalDeath;
	
	 int _activeTransaction;
	int _totalTransaction;
	
	int _activeTransactionComplete;
	int _totalTransactionComplete;
	
	int _totalNodesDiff;
	int _totalRefDiff;
	int _totalDeathDiff;
	int _totalTransactionDiff;
	int _totalTransactionCompleteDiff;
	ArrayList<String> binderFeatures= new ArrayList<>();

	FileParser _fp = new FileParser();

	public BinderFeatures() {
    fetchData();
    }
	public void fetchData() {
		try
	    {
			int totalNodes = 0;
			int totalRef = 0;
			int totalDeath = 0;
			int totalTransaction = 0;
			int totalTransactionComplete = 0;
			
			
			try {
				_fp.load("/sys/kernel/debug/binder/stats");
			} catch (FileNotFoundException e) {
				_fp.load( "/proc/binder/stats" );
			}
			
			_transaction = _fp.readInt( 1, 1 );
            binderFeatures.add(_transaction+"");
			_reply = _fp.readInt( 2, 1 );
			binderFeatures.add(_reply+"");
			_acquire = _fp.readInt( 5, 1 );
            binderFeatures.add(_acquire+"");
			_release = _fp.readInt( 6, 1 );
			binderFeatures.add(_release+"");
			_activeNodes = _fp.readInt( 24, 2 );
            binderFeatures.add(_activeNodes+"");
			totalNodes = _fp.readInt( 24, 4 );
            binderFeatures.add(totalNodes+"");
			
			_activeRef = _fp.readInt( 25, 2 );
            binderFeatures.add(_activeRef+"");
			totalRef = _fp.readInt( 25, 4 );
			binderFeatures.add(totalRef+"");
			_activeDeath = _fp.readInt( 26, 2 );
            binderFeatures.add(_activeDeath+"");
			totalDeath = _fp.readInt( 26, 4 );
			binderFeatures.add(totalDeath+"");
			_activeTransaction = _fp.readInt( 27, 2 );
            binderFeatures.add(_activeTransaction+"");
			totalTransaction = _fp.readInt( 27, 4 );
			binderFeatures.add(totalTransaction+"");
			_activeTransactionComplete = _fp.readInt( 28, 2 );
			totalTransaction = _fp.readInt( 28, 4 ); 
			binderFeatures.add(_activeTransactionComplete+"");
			_fp.close();
        	
        	_totalNodesDiff = totalNodes - _totalNodes;
        	_totalNodes = totalNodes;
        	
        	_totalRefDiff = totalRef - _totalRef;
        	_totalRef = totalRef;
        	
        	_totalDeathDiff = totalDeath - _totalDeath;
        	_totalDeath = totalDeath;
        	
        	_totalTransactionDiff = totalTransaction - _totalTransaction;
        	_totalTransaction = totalTransaction;
        	
        	_totalTransactionCompleteDiff = 
        			totalTransactionComplete - _totalTransactionComplete; 
        	_totalTransactionComplete = totalTransactionComplete;
            binderFeatures.add(_totalTransactionComplete+"");
            binderFeatures.add(_totalNodesDiff+"");
            binderFeatures.add(_totalRefDiff+"");
            binderFeatures.add(_totalDeathDiff+"");
            binderFeatures.add(_totalTransactionDiff+"");
            binderFeatures.add(_totalTransactionCompleteDiff+"");
	    }
	    catch( Exception ex )
	    {
	        ex.printStackTrace();           
	    }

	}
    public ArrayList<String> getData(){
       return binderFeatures;
    }
	


}
