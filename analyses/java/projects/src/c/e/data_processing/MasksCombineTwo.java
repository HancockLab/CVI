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


public class MasksCombineTwo {
	
	public MasksCombineTwo() {};
	
	public void setSnpFile(String maskFile1, String maskFile2){
		
		try {
			BitSet[] mask1=new BitSet[5];
			BitSet[] mask2=new BitSet[5];
			BitSet[] maskAnd=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask1[c]= new BitSet();
				mask2[c]= new BitSet();
				maskAnd[c]= new BitSet();
			}
			
			// Go mask 1

			File fileMask = new File(maskFile1);
			Scanner scannerMask = new Scanner(fileMask);
	        	
			int chrCVI=0;
	        	String lineMask=null;
	        	
		        for (int m=0; m<10; m++) {
		        	lineMask = scannerMask.nextLine();
		        	if (m%2 == 1) {
			        	for (int i=1; i<lineMask.length(); i++) {
			        		if (lineMask.charAt(i) == ('1')) {
			        			mask1[chrCVI].set(i);
			        		}
			        	}
			        	chrCVI=chrCVI+1;
		        	}
		        }

			for (int c=0; c<5; c++) {
				maskAnd[c].or(mask1[c]);
			}
			

			// Go mask 2
			//
			fileMask = new File(maskFile2);
			scannerMask = new Scanner(fileMask);
	        	
			chrCVI=0;
	        	lineMask=null;
	        	
		        for (int m=0; m<10; m++) {
		        	lineMask = scannerMask.nextLine();
		        	if (m%2 == 1) {
			        	for (int i=1; i<lineMask.length(); i++) {
			        		if (lineMask.charAt(i) == ('1')) {
			        			mask2[chrCVI].set(i);
			        		}
			        	}
			        	chrCVI=chrCVI+1;
		        	}
		        }

			for (int c=0; c<5; c++) {
				maskAnd[c].and(mask2[c]);
			}
			

	        	int combinedGenomeFinal =0;
	        
	        	for (int c=0; c<5; c++) {
	        		combinedGenomeFinal = combinedGenomeFinal + maskAnd[c].cardinality();
	        		System.out.println("Cardinality: " + maskAnd[c].cardinality());
	        	}
        		System.out.println("combinedGenomeFinal: " + combinedGenomeFinal);
			
			

			Writer writer = new FileWriter(maskFile1 + "_noRepCent.txt");
			PrintWriter out = new PrintWriter(writer);
			
			for (int c=0; c<5; c++) {
				out.print(">Chr" + (c+1));
				out.print("\n");
				for (int bit =0; bit< maskAnd[c].length(); bit++) {
					if (maskAnd[c].get(bit)) {
						out.print("1");
					}
					if (!maskAnd[c].get(bit)) {
						out.print("0");
					}
				}
				out.print("\n");
			}
			out.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MasksCombineTwo masksCombineTwo = new MasksCombineTwo();
		masksCombineTwo.setSnpFile(args[0], args[1]); 
	}
}
