package filtering;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import metadata.MetadataManagerInterface;
import metadata.NaiveFileMetadataManager;

public class FilteringEngine implements FilteringEngineInterface{

	private Map<String, List<String>> atomicFilters;
	private NaiveFileMetadataManager metadataManager;
	
	public FilteringEngine(Map<String, List<String>> atomicFilters, NaiveFileMetadataManager metadataManager) {
		this.atomicFilters = atomicFilters;
		this.metadataManager = metadataManager;
	}
	

	@Override
	public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters,
			MetadataManagerInterface pMetadataManager) throws FileNotFoundException {
		for(int i=0;i<pMetadataManager.getColumnNames().length;i++) {
			if(pAtomicFilters.containsKey(pMetadataManager.getColumnNames()[i])){
				return 0;
			}
		}
		return -1;
	}

	@Override
	public List<String[]> workWithFile() throws FileNotFoundException {
		Scanner sc=new Scanner(this.metadataManager.getDataFile());
		ArrayList<String> myList=new ArrayList<String>();
		List<String[]> finalList=new ArrayList<String[]>();
		List<String[]> finalList2=new ArrayList<String[]>();
		List<String[]> finalList3=new ArrayList<String[]>();
		ArrayList<Integer> pointers=new ArrayList<Integer>();
		String[] befkeys;
		String[] keys = null ;//[year country]
		ArrayList<String> finalvalues=new ArrayList<String>();//2010 2011 2012 Aystralia
		List<String> values=new ArrayList<String>();
		
		sc.nextLine();
		while(sc.hasNextLine()) {
			myList.add(sc.nextLine());
		}
		
		for(String i:myList){
			finalList.add(i.split(this.metadataManager.getSeparator()));
		}
		
		for(Map.Entry<String, List<String>> atom: atomicFilters.entrySet()) {
			for(Map.Entry<String, Integer> pos: this.metadataManager.getFieldPositions().entrySet()) {
				if(atom.getKey().equals(pos.getKey())) {
					int x = pos.getValue();
					for(String[] i: finalList) {
						if(atom.getValue().contains(i[x])) {
							finalList2.add(i);
						}
					}
				}
			}
		}
		
		for(int i=0;i<this.metadataManager.getColumnNames().length;i++){
			if(this.atomicFilters.containsKey(this.metadataManager.getColumnNames()[i])){
				pointers.add(i);
			}
		}
		
		int counter=0;
		befkeys=metadataManager.getColumnNames();
		keys=new String[pointers.size()];
		for(Integer i:pointers) {
			keys[counter]=befkeys[i];
			counter++;
		}

		for(int i=0;i<keys.length;i++) {
			values=atomicFilters.get(keys[i]);
			for(int j=0;j<values.size();j++) {
				finalvalues.add(values.get(j));
			}
		}

		int countSame=0;
		for(int i=0;i<finalList2.size();i++) {
			for(int j=i;j<finalList2.size();j++) {
				if(finalList2.get(i) == finalList2.get(j)) {
					countSame++;
				}
			}
			if(countSame==keys.length) {
				finalList3.add(finalList2.get(i));
			}
			countSame=0;
		}
		
		return finalList3;
	}
}




/*
public class FilteringEngine{
	public static void main(String args[]) throws FileNotFoundException {
	String sAlias = "simple";
	String sSeparator = ",";
	File sFile = new File("./test/resources/input/simple.csv");
	NaiveFileMetadataManager metadataManager = new NaiveFileMetadataManager(sAlias, sFile, sSeparator);
	
	HashMap<String, List<String>> atomicFilters = new HashMap<String, List<String>>();
	List<String> countryFilter = new ArrayList<String>();
	countryFilter.add("AUS:Australia");
	atomicFilters.put("LOCATION:Country", countryFilter);
	
	Map<String, List<String>> multiCriteriaAtomicFilters = new HashMap<String, List<String>>(atomicFilters);
	List<String> timeFilter = new ArrayList<String>();
	timeFilter.add("2010");
	timeFilter.add("2011");
	timeFilter.add("2012");
	multiCriteriaAtomicFilters.put("TIME:Year", timeFilter);
	
	FilteringEngines myEngine = new FilteringEngines(multiCriteriaAtomicFilters, metadataManager);
	for(String[] i:myEngine.workWithFile()) {
		for(int y=0;y<i.length;y++) {
			System.out.println(i[y]);
		}
		System.out.println();
		}
	}
}
*/



