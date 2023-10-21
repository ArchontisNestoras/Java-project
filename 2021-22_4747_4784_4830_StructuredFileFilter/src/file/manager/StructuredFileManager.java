package file.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import filtering.FilteringEngine;
import metadata.NaiveFileMetadataManager;

public class StructuredFileManager implements StructuredFileManagerInterface{
	private String pAlias;
	private String pPath;
	private String pSeparator;

	@Override
	public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException {
		File pFile = new File(pPath);
		this.pAlias = pAlias;
		this.pSeparator = pSeparator;
		this.pPath = pPath;
		return pFile;
	}

	@Override
	public String[] getFileColumnNames(String pAlias) throws NullPointerException, IOException {
		if(pAlias==null) {
			//String[] y = {};
			//return y;
			return null;
		}
		if(pAlias.equals(this.pAlias)) {
			NaiveFileMetadataManager metadataManager = new NaiveFileMetadataManager(pAlias, this.registerFile(this.pAlias, pPath, pSeparator), this.pSeparator);
			return metadataManager.getColumnNames();
		}else {
			String[] y = {};
			return y;
			//return null;
		}
	}

	@Override
	public List<String[]> filterStructuredFile(String pAlias, Map<String, List<String>> pAtomicFilters) throws NullPointerException, IOException {
		NaiveFileMetadataManager metadata = new NaiveFileMetadataManager(pAlias, this.registerFile(this.pAlias, pPath, pSeparator), this.pSeparator);
		FilteringEngine filtering = new FilteringEngine(pAtomicFilters, metadata);
		return filtering.workWithFile();
	}

	@Override
	public int printResultsToPrintStream(List<String[]> recordList, PrintStream pOut) {
		for(String[] i:recordList) {
			for(int j=0; j<i.length; j++) {
				pOut.println(i[j]);
			}
		}
		if(recordList==null) {
			return -1;
		}
		return recordList.size();
	}

}
