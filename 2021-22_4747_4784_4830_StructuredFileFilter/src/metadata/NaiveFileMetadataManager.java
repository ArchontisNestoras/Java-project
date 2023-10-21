package metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NaiveFileMetadataManager implements MetadataManagerInterface{

	private String pAlias;
	private File pFile;
	private String pSeparator;
	
	public NaiveFileMetadataManager(String pAlias, File pFile, String pSeparator) {
		this.pAlias = pAlias;
		this.pFile = pFile;
		this.pSeparator = pSeparator;
	}
	

	@Override
	public Map<String, Integer> getFieldPositions() throws FileNotFoundException {
		String[] y = this.getColumnNames();
		Map <String,Integer> myMap=new HashMap <String,Integer>();
		for(int i=0;i<y.length;i++) {
			myMap.put(y[i],i);
		}
		return myMap ;
	}

	@Override
	public File getDataFile() {
		return this.pFile;
	}

	@Override
	public String getSeparator() {
		return this.pSeparator;
	}

	@Override
	public String[] getColumnNames() throws FileNotFoundException {
		Scanner sc = new Scanner(this.pFile);
		String x=sc.nextLine();
		String [] y=x.split(pSeparator);
		return y;
	}

	public String getAlias() {
		return this.pAlias;
	}

}
