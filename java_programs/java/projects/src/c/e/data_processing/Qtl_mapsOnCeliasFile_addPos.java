package c.e.data_processing;

import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
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

public class Qtl_mapsOnCeliasFile_addPos {
	
	public Qtl_mapsOnCeliasFile_addPos() {}
	
	public void setFileToSubSet(String matrixName){
		
		try {
			
			File qtlFile= new File(matrixName);
	    	Scanner scanner = new Scanner(qtlFile);
	    	
	    	Writer writer = new FileWriter(matrixName + "_withPos.txt");
			PrintWriter out = new PrintWriter(writer);
			
			int[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};
			double[] mapL = {151.0, 102.0, 86.0, 128.0, 142.0};
			
	    	while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	int chr = Integer.parseInt(splitSnp[1]);
		       	// System.out.println(chr);
		       	// if (chr == 0) {
		       	//	System.out.println(snp);
		       	// }
		       	double posCm = Double.parseDouble(splitSnp[2]);
		       	
		       	// Opoen the map file
		       	//
		       	File map = new File("/home/fulgione/data/mapFrom-mapsKHCF.xls-Haldane_crossP10_chr" + (chr-1) + ".txt");
				
				int brakes =0;
				Scanner scannerM = new Scanner(map);
				scannerM.nextLine();
				while ( scannerM.hasNextLine() ) { 
					scannerM.nextLine();
					brakes = brakes+1;
				}
				
				int[] brakeChr = new int[brakes];
				int[] brakePos = new int[brakes + 1];						//  + 1 for the end of chromosome
				Double[] brakeDist = new Double[brakes + 1];
				
				brakes = 0;
				scannerM = new Scanner(map);
				scannerM.nextLine();
				
				while ( scannerM.hasNextLine() ) { 
					String snp2 = scannerM.nextLine();
		        	String[] splitSnp2 = snp2.split("\t");
		        	
		        	brakeChr[brakes] = Integer.parseInt(splitSnp2[0].split("-")[0]);
		        	brakePos[brakes] = Integer.parseInt(splitSnp2[0].split("-")[1]);
		        	brakeDist[brakes] = Double.parseDouble(splitSnp2[2]);					// 1: Kosambi 2: Haldane 3: Carter Falconer Distances
		        	
		        	brakes = brakes+1;
				}
				brakePos[brakePos.length-1] = chrL[chr-1];
				brakeDist[brakeDist.length-1] = mapL[chr-1];
				
		    	System.out.println(Arrays.toString(brakeDist));
				
				// Got breaks
				//
				// Find the surrounding 2
				int pos = 0;
				for (int b=0; b<brakeDist.length-1; b++) {
					if (posCm >= brakeDist[b] && posCm <= brakeDist[b+1]) {
						double propDist = (double)(posCm-brakeDist[b])/(double)(brakeDist[b+1]-brakeDist[b])*(double)(brakePos[b+1] - brakePos[b]);
						pos = (int)((double)(brakePos[b] + propDist));
					}
				}
				out.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + splitSnp[2] + "\t" + pos + "\n");
			}
	    	out.close();
	    	
	    	
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Qtl_mapsOnCeliasFile_addPos qtl_mapsOnCeliasFile_addPos = new Qtl_mapsOnCeliasFile_addPos();
		qtl_mapsOnCeliasFile_addPos.setFileToSubSet("/home/fulgione/markerPositions_withNull_finalCelia_extraMarkers_2020-10-22.txt");
		// qtl_mapsOnCeliasFile_addPos.setFileToSubSet("/Volumes/CVI/final/files/qtl/markerPositions_withNull_finalCelia_3.txt");
	}
}
