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

public class PiOnSingleVcfs {
	
	public PiOnSingleVcfs() {}
	
	public void setFileToConvert(String filename, String sampStr, String numID, String results){
		
		try {
			
			int covThresh = 3;
			int qualThresh = 20;
			
			// int sample = Integer.parseInt(sampStr);
			
			// Open folder
			File fold = new File(filename);
			File[] files = fold.listFiles();
			int numF = 0;
			for (int f=0; f<files.length; f++) {
				if (files[f].getName().split("\\.")[files[f].getName().split("\\.").length-1].equals("vcf")) {
					numF = numF + 1;
				}
			}
			File[] vcfFiles = new File[numF];
			numF = 0;
			
			int[] order = {21135, 21137, 37472, 37468, 35520, 211399, 21139, 37471, 37470, 37469, 35625, 35624, 35623, 35622, 35621, 35620, 35619, 35618, 35617, 35616, 35615, 35614, 35613, 35612, 35611, 35610, 35609, 35608, 35607, 35606, 35605, 35604, 35603, 35602, 35601, 35600, 35599, 35598, 35596, 35595, 35594, 35593, 35523, 35522, 35521, 35513, 35512, 22011, 22010, 22009, 22008, 22007, 22006, 22005, 22004, 22003, 22002, 22001, 22000, 21999, 18516, 18515, 18514, 18513, 18512, 18511, 18510, 18509};
			
			for (int o = 0; o<order.length; o++) {
				for (int f=0; f<files.length; f++) {
					if (files[f].getName().split("\\.")[files[f].getName().split("\\.").length-1].equals("vcf") && Integer.parseInt(files[f].getName().split("\\.")[1]) == order[o]) {
						vcfFiles[numF] = files[f];
                                        	numF = numF + 1;
                                	}
                        	}
			}
		for (int f=0; f<vcfFiles.length; f++) {
                                        System.out.print(vcfFiles[f] + "\t");
                                }
                                System.out.print("\n");	
		System.out.println("files: " + vcfFiles.length);	
			
			// Get Shore calls
			File shoreFile = new File(sampStr); // vcfFiles[sample];
			Scanner scannerS = new Scanner(shoreFile);

			BitSet[] shoreSnp = new BitSet[5];
			BitSet[] shoreCov = new BitSet[5];
			for (int c=0; c<5; c++) {
				shoreSnp[c] = new BitSet();
				shoreCov[c] = new BitSet();
			}
			
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		    	// Jump the header
		    	if (snp.charAt(0) != '#') {
		    		
					if (splitSnp[3].length() == 1 && splitSnp[4].length() == 1) {
						// Just see chromosomes
			    		if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') ) {
			    			int chr = Character.getNumericValue(splitSnp[0].charAt(3) - 1);
			    			int pos = Integer.parseInt(splitSnp[1]);
			    			
			    			// See if there is any reasonable call - from VcfCombined_to_SnpMatrix.java
			    			if ( (splitSnp[9].split(":")[0].charAt(0) != '.') && (splitSnp[9].split(":")[1].charAt(0) != '.') && (splitSnp[9].split(":")[2].charAt(0) != '.') ){
			    				
			    				// Check coverage and quality
			    				int cov=Integer.parseInt(splitSnp[9].split(":")[2]);
			    				int qual=Integer.parseInt(splitSnp[9].split(":")[1]);
			    				
			    				if ( (cov >=covThresh) && (qual >= qualThresh) ) {
			    					shoreCov[chr].set(pos);
			    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 0) {
			    						shoreSnp[chr].set(pos);
			    						
			    						// Just to check...
				    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 1) {
				    						System.out.println("Strange stuff at: " + snp);
				    					}
			    					}
			    				}
			    			}
			    		}
					}
		    	} 	
			}

			// Got Shore
			
			
	        
			
			
			
			//////
			///		Get all other samples
			//////
			
		
			
	        Writer writer = new FileWriter(results + "pi_" + numID + ".txt");
			PrintWriter out = new PrintWriter(writer);
			
	       for (int s=0; s<vcfFiles.length; s++) {
	        	
				// Get Shore calls
				System.out.println("file: " + vcfFiles[s].getName());
				File shoreFile2 = vcfFiles[s];
				Scanner scannerS2 = new Scanner(shoreFile2);

				BitSet[] shoreSnp2 = new BitSet[5];
				BitSet[] shoreCov2 = new BitSet[5];
				for (int c=0; c<5; c++) {
					shoreSnp2[c] = new BitSet();
					shoreCov2[c] = new BitSet();
				}
				
				while ( scannerS2.hasNextLine() ) {
					String snp = scannerS2.nextLine();
			       	String[] splitSnp = snp.split("\t");
			       	
			    	// Jump the header
			    	if (snp.charAt(0) != '#') {
			    		
						if (splitSnp[3].length() == 1 && splitSnp[4].length() == 1) {
							// Just see chromosomes
				    		if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') ) {
				    			int chr = Character.getNumericValue(splitSnp[0].charAt(3) - 1);
				    			int pos = Integer.parseInt(splitSnp[1]);
				    			
				    			// See if there is any reasonable call - from VcfCombined_to_SnpMatrix.java
				    			if ( (splitSnp[9].split(":")[0].charAt(0) != '.') && (splitSnp[9].split(":")[1].charAt(0) != '.') && (splitSnp[9].split(":")[2].charAt(0) != '.') ){
				    				
				    				// Check coverage and quality
				    				int cov=Integer.parseInt(splitSnp[9].split(":")[2]);
				    				int qual=Integer.parseInt(splitSnp[9].split(":")[1]);
				    				
				    				if ( (cov >=covThresh) && (qual >= qualThresh) ) {
				    					shoreCov2[chr].set(pos);
				    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 0) {
				    						shoreSnp2[chr].set(pos);
				    						
				    						// Just to check...
					    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 1) {
					    						System.out.println("Strange stuff at: " + snp);
					    					}
				    					}
				    				}
				    			}
				    		}
						}
			    	} 	
				}

				// Got Shore
				
				
		        //////
		        ///			calculate pi 
		        //////

		        BitSet orr = new BitSet();
		        BitSet andd = new BitSet();
		        BitSet jolly = new BitSet();
		        
		        long diffShore = 0;
		        long bpShore = 0;
		        
		        for (int c=0; c<5; c++) {
		        	
		        	orr = new BitSet();
		        	andd = new BitSet();
		        	
		        	// Charge overlap of masks
		        	jolly = new BitSet();
		        	jolly.or(shoreCov[c]);
		        	jolly.and(shoreCov2[c]);
		        	bpShore = bpShore + jolly.cardinality();
		        	
		        	// Charge SNPs1
		        	jolly.and(shoreSnp[c]);
		        	orr.or(jolly);
		        	andd.or(jolly);
		        	
		        	// Charge SNPs2
		        	jolly = new BitSet();
		        	jolly.or(shoreCov[c]);
		        	jolly.and(shoreCov2[c]);
		        	jolly.and(shoreSnp2[c]);
		        	
		        	orr.or(jolly);
		        	andd.and(jolly);
		        	orr.andNot(andd);
		        	
		        	diffShore = diffShore + orr.cardinality();

				//System.out.println("shoreCov " + c + " " + shoreCov[c].cardinality());
                                //System.out.println("shoreCov2 " + c + " " + shoreCov2[c].cardinality());
				//System.out.println("bpShore: " + c + " " + bpShore);
                        	//System.out.println("diffShore: " + c + " " + diffShore);
		        }
			System.out.println("bpShore: " + bpShore);
			System.out.println("diffShore: " + diffShore);
		        double piShore = (double)diffShore/(double)bpShore;
		        
		        // Output
		        out.print(piShore + "\t");
		}
	        out.print("\n");
		out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PiOnSingleVcfs piOnSingleVcfs = new PiOnSingleVcfs();
		piOnSingleVcfs.setFileToConvert(args[0], args[1], args[2], args[3]);
	}
}
