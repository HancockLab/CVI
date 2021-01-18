package c.e.calling;

import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Collections;



public class Coverage {
	
	public Coverage() {}
	
	

	public void setSnpFile(String filename){
		
		try {
			
			File folder = new File(filename);
			File[] listOfFolders = folder.listFiles();
			int numALLFiles = 0;
			
			for (int f=0; f<listOfFolders.length; f++) {
				System.out.println("listOfFolders[f]: " + listOfFolders[f]);
				String name = listOfFolders[f].getAbsolutePath();
				String[] nameSplit = name.split("\\.");
				if (nameSplit[nameSplit.length-1].equals("all")) {
					numALLFiles = numALLFiles +1;
				}
			}
			
			File[] listALLFiles = new File[numALLFiles];
			numALLFiles = 0;
			for (int f=0; f<listOfFolders.length; f++) {
				String name = listOfFolders[f].getAbsolutePath();
				String[] nameSplit = name.split("\\.");
				if (nameSplit[nameSplit.length-1].equals("all")) {
					listALLFiles[numALLFiles] = listOfFolders[f];
					numALLFiles = numALLFiles +1;
				}
			}
			
			Writer writer = new FileWriter(filename + "coverageAverage.txt"); 
			PrintWriter out = new PrintWriter(writer);
			
			for (int f=0; f<listALLFiles.length; f++) {
				
				long pos = 0;
				long covSum = 0;
				
				Scanner scanner = new Scanner(listALLFiles[f]);
				while ( scanner.hasNextLine() ) { 
					String snp1 = scanner.nextLine();
					String[] splitSnp1 = snp1.split("\t");
					
					covSum = covSum + Long.parseLong(splitSnp1[3]);
					pos = pos + 1;
				}
				
				System.out.println(listALLFiles[f].getAbsolutePath());
				System.out.println("Average coverage: " + (double)covSum/pos);
				System.out.println("\n");
				
				out.print(listALLFiles[f].getAbsolutePath());
				out.print("Average coverage: " + (double)covSum/pos + "\n");
			}
			out.close();			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Coverage coverage = new Coverage();
		coverage.setSnpFile("/home/CIBIV/andreaf/canaries/rawData/bam/newOktober/output/"); // args[0]
	}
}
