package application.naive.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import filtering.FilteringEngine;
import metadata.NaiveFileMetadataManager;

public class Final {
	public static void main(String args[]) throws NullPointerException, IOException {
		
		//pAlias
		Scanner sc = new Scanner(System.in);
		System.out.println("Dwste ena pAlias: ");
		String pAlias = sc.nextLine();
		
		//pPath - pFile
		System.out.println("Dwste to input File(olo to path): ");
		String pPath = sc.nextLine();
		File pFile = new File(pPath);
		
		//pSeparator
		System.out.println("Dwste ena separator(pithanotata ',')");
		String pSeparator=sc.next();
		
		//atomicFilter
		String x;
		Map<String, List<String>> finaleAtomicFilter = new HashMap<String, List<String>>();
		List<String> myList;
		while(!(x=sc.nextLine()).equals(".")) {
			myList = new ArrayList<String>();
			System.out.println("Dwste to kleidi kai tis times xwrismena ola me ',' kai gia na termatisei pathste '.'");
			String[] y;
			y = x.split(",");
			for(int i=1;i<y.length;i++) {
				myList.add(y[i]);
			}
			finaleAtomicFilter.put(y[0], myList);
		}
		
		//xLabel
		System.out.println("Dwste to onoma tou ajona x");
		String xLabel = sc.nextLine();
		
		//yLabel
		System.out.println("Dwste to onoma tou ajona y");
		String yLabel = sc.nextLine();
		
		NaiveFileMetadataManager metadataManager = new NaiveFileMetadataManager(pAlias,pFile,pSeparator);
		FilteringEngine filteringEngine=new FilteringEngine(finaleAtomicFilter,metadataManager);
		NaiveApplicationController appController = new NaiveApplicationController();
		
		appController.registerFile(pAlias,pPath,pSeparator);
		String outputFilePathCSV = "./resources/Output/JTable.csv";
		String outputFilePathBAR = "./resources/Output/BarChart.png";
		String outputFilePathLINE = "./resources/Output/LineChart.png";
		appController.saveToResultTextFile(outputFilePathCSV, appController.executeFilterAndShowJTable(pAlias,finaleAtomicFilter,outputFilePathCSV));
		appController.showSingleSeriesBarChart(pAlias, filteringEngine.workWithFile(), xLabel, yLabel, outputFilePathBAR);
		appController.showSingleSeriesLineChart(pAlias, filteringEngine.workWithFile(), xLabel, yLabel, outputFilePathLINE);
	}
}