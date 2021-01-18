package c.e.at;

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


public class combinedMask {
	
	private BitSet[] mask = null;
	private BitSet[] maskAnd = null;
	private File fileMask = null;
	private File folder=null;
	private File[] listOfMasks = null;
	private int numMasks = 0;

	

	
	public combinedMask() {};
	
	public void setSnpFile(String filename){
		
		try {
			

			mask=new BitSet[5];
			maskAnd=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
				maskAnd[c]= new BitSet();
			}
			
			
			
			
			

			// Dajje coooi nosstri CVIs!!
			
			folder = new File(filename);
			listOfMasks = folder.listFiles();
			numMasks = listOfMasks.length;
			
			for (int f=0; f<listOfMasks.length; f++) {
				System.out.println("listOfMasks[f]: " + listOfMasks[f]);
			}
			
			
			
			
			
			
			// THE MAsKSS
			
			
			// Ever seen a running mask? This mask runs through our Cvi islands!
			
			for (int f=0; f<listOfMasks.length; f++) {
				
				System.out.println("Yop the " + f + "th fun!");
				
				mask=new BitSet[5];
				
				for (int c=0; c<5; c++) {
					mask[c]= new BitSet();
				}
								
				fileMask = listOfMasks[f];
				
				Scanner scannerMask = new Scanner(fileMask);
	        	
				int chrCVI=0;
	        	String lineMask=null;
	        	
		        for (int m=0; m<10; m++) {
		        	lineMask = scannerMask.nextLine();
		        	
		        	if (m%2 == 1) {
			        	
			        	for (int i=1; i<lineMask.length(); i++) {
			        		if (lineMask.charAt(i) == ('1')) {
			        			mask[chrCVI].set(i);
			        		}
			        	}
			        	chrCVI=chrCVI+1;
		        	}
		        }
		        
		        
		        int genome =0;
		        System.out.println("Running on plant: " + listOfMasks[f]);
		        
		        for (int c=0; c<5; c++) {
		        	genome = genome + mask[c].cardinality();
		        	System.out.println("Cardinality: " + mask[c].cardinality());
		        }
	        	System.out.println("genome: " + genome);
				

				for (int c=0; c<5; c++) {
					if (f == 0) {
						maskAnd[c].or(mask[c]);
					}
					maskAnd[c].and(mask[c]);
				}
				

		        int combinedGenome =0;
		        
		        for (int c=0; c<5; c++) {
		        	combinedGenome = combinedGenome + maskAnd[c].cardinality();
		        	// System.out.println("Cardinality: " + maskAnd[c].cardinality());
		        }
	        	System.out.println("combinedGenome: " + combinedGenome);
				
				
			}
			
			

	        int combinedGenomeFinal =0;
	        
	        for (int c=0; c<5; c++) {
	        	combinedGenomeFinal = combinedGenomeFinal + maskAnd[c].cardinality();
	        	System.out.println("Cardinality: " + maskAnd[c].cardinality());
	        }
        	System.out.println("combinedGenomeFinal: " + combinedGenomeFinal);
			
			

			Writer writer = new FileWriter(filename + "/combinedMask.txt");
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
		combinedMask CombinedMask = new combinedMask();
		CombinedMask.setSnpFile("/home/CIBIV/andreaf/canaries/rawData/analyses/janeiro/polarMatrCVIonly/masks/"); 
		// /home/CIBIV/andreaf/canaries/rawData/analyses/janeiro/polarMatrCVIonly/masks
		// /Volumes/CVI_Seagate/rawData/bam/masksCVI
		// /home/CIBIV/andreaf/canaries/rawData/moroccans/masks
	}
}
