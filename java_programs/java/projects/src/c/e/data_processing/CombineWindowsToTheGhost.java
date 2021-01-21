package c.e.data_processing;

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

public class CombineWindowsToTheGhost {
	
	public CombineWindowsToTheGhost() {}
	
	public void setFileToSubSet(String firstName, String lastName, String numberWin){
		
		try {
			System.out.println("src/c/e/data_processing/CombineChr_windowsToTheGhost");
			
			int col = 0;

			// Open also the writing file
			Writer writer = new FileWriter(firstName + "All" + lastName);
			PrintWriter out = new PrintWriter(writer);
			int numWin = Integer.parseInt(numberWin);
			
			for (int c=0; c<numWin; c++) {
				
		    		// Begin with the big matrix
			    	File matrix = new File(firstName + c + lastName);
		    		Scanner scannerMatrix = new Scanner(matrix);
				
				// Go with contents
			       	int snps = 0;
				while ( scannerMatrix.hasNextLine() ) {
					String snp = scannerMatrix.nextLine();
				       	String[] splitSnp = snp.split("\t");
			       		col = splitSnp.length;
					
					// Print header
					if (snp.charAt(0) == '#') {
						if (c == 0) {
							out.print(snp);
							if (snp.charAt(1) == '#') {
								out.print("\n");
							}
						}
					} else {	
				       		// Do the rest
				       		out.print("\n" + (int)(c+1) );
			       			for (int sub=1; sub<splitSnp.length; sub++) {
				       			out.print("\t" + splitSnp[sub]);
						}
					}
				}
			}
			out.print("\n" + "1430" );
			for (int sub=1; sub<col; sub++) {
				out.print("\t" + "0");
			}
			out.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CombineWindowsToTheGhost combineWindowsToTheGhost = new CombineWindowsToTheGhost();
	        combineWindowsToTheGhost.setFileToSubSet(args[0], args[1], args[2]);
	}
}
