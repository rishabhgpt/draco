package org.droidanalyst.features;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {
	private String _fileName;
	private BufferedReader _reader;
	
	private int _currentLine;
	private String[] _tokens;
	
	
	public void load( String file )
			throws FileNotFoundException, IOException {
		_fileName = file;
		
		if ( _reader != null )
			close();
		
		_reader = new BufferedReader( new FileReader( _fileName ) );
		_currentLine = -1;
		_tokens = null;
	}
	
	public int readInt( int lineNum, int tokenNum )
			throws FileNotFoundException, IOException {
		return Integer.parseInt( read( lineNum, tokenNum ) );
	}
	
	public String read( int lineIndex, int tokenIndex )
			throws FileNotFoundException, IOException {
		// If the lines are passed out of order, reload the file
		if ( lineIndex < _currentLine )
			load(_fileName);
		
		if ( lineIndex == _currentLine )
			return _tokens[ tokenIndex ];
		
		while ( _currentLine < lineIndex ) {
			String _lineStr = _reader.readLine();
			_tokens = _lineStr.split("\\s+"); // Whitespace
			_currentLine++;
		}
			
		return _tokens[ tokenIndex ];
	}
	
	public void close() throws IOException {
		if ( _reader != null ) {
			_reader.close();
			_reader = null;
		}
	}
	
	public boolean fileExists(String fileName) {
		try {
			FileReader fileReader = new FileReader( fileName );
			fileReader.close();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			// Don't worry
		}
		
		return true;
	}
}
