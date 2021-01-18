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

public class VcfCombined_to_snpMatrix {
	
	public VcfCombined_to_snpMatrix() {}
	
	public void setFileToConvert(String filename, String chromosome){
		
		try {
			
			// Open combined vcf
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
			
			// Open also the writing file
			Writer writer = new FileWriter(filename + "_superMatrix_qual0cov1_chr" + chromosome + ".txt");
			PrintWriter out = new PrintWriter(writer);
			
			int checkChr = 0;
			int checkPos=0;	
			int nSample =0;
			Boolean doublePos = false;			
			
			while ( scannerVcf.hasNextLine() ) {
				String snp1 = scannerVcf.nextLine();
			       	String[] splitSnp = snp1.split("\t");
		       	
				// Mind the header now
				if (snp1.charAt(0) == '#') {
					if (snp1.charAt(1) != '#') {
						
						// Count the samples
					       	nSample = splitSnp.length - 9;
					       	System.out.println("Nsamples: " + nSample);
				       	
				       		// Print out header and samples
				       		if (chromosome.charAt(0) == '1' ) {
							out.print("chromosome" + "\t" + "position" + "\t" + "6909"); // Last is col-0
				       			for (int sample=0; sample<nSample; sample++) {
				       				out.print( "\t" + splitSnp[9 + sample]);
				       			}
		       					out.print("\n");
						}
					}
				}
				// Header is over
				
				
				// mind proper stuff now
				
				if (snp1.charAt(0) != '#') {
					if (splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' && splitSnp[0].charAt(3) == chromosome.charAt(0) ) {
					
						// Indels out
						if (splitSnp[3].length() == 1) {
							String[] check = splitSnp[4].split(",");
							boolean inDel=false;
							for (int c=0; c< check.length; c++) {
								if (check[c].length() > 1) {
									inDel = true;
								}
							}
							
							// Also chlorop and mytoc out
							if (!inDel) {
								
								if ( checkChr == Character.getNumericValue(splitSnp[0].charAt(3))) {
									if ( checkPos >= Integer.parseInt(splitSnp[1])) {
										doublePos = true;
									}
								} else {
									checkChr = Character.getNumericValue(splitSnp[0].charAt(3));
									checkPos = 0;
								}
							
								
								// For every line, print chromosome and position and Col-0
                    					        out.print(splitSnp[0].charAt(3) + "\t" + splitSnp[1] + "\t" + splitSnp[3]);
						       	
							       	// For every sample, print a base
							       	for (int sample=0; sample<nSample; sample++) {
					       			
					       				// Is there any call at all?
					     	  			if ( (splitSnp[9 + sample].split(":")[0].charAt(0) != '.') && (splitSnp[9 + sample].split(":")[1].charAt(0) != '.') && (splitSnp[9 + sample].split(":")[2].charAt(0) != '.') ){
						       				// Check coverage and quality
						       			//	int cov=Integer.parseInt(splitSnp[9 + sample].split(":")[2]);
						       			//	int qual=Integer.parseInt(splitSnp[9 + sample].split(":")[1]);
						    	   		
						      		 		// Print out something reasonable
						       			//	if ( (cov >=3) && (qual >= 25) ) {
							       				if (Integer.parseInt(splitSnp[9 + sample].split(":")[0]) == 0) {
							       					out.print("\t" + splitSnp[3].charAt(0));
									       		} else {
									       			if (splitSnp[4].length() == 1) {
								       					out.print("\t" + splitSnp[4].charAt(0));
								       				} else {
								       					out.print("\t" + splitSnp[4].split(",")[Integer.parseInt(splitSnp[9 + sample].split(":")[0]) - 1].charAt(0));
							       					}
							       				}
						       			//	} else {
						       			//		out.print("\t" + "N");
						     	  		//	}
					       				} else {
					       					out.print("\t" + "N");
					       				}
							       	}
							       	out.print("\n");
							       	// Next line!
							}
						}	
					}
       				}	
			}
			out.close();
			
			
				
			
			
		
			
			
			
			// 
			// Now eliminate double records: bcftools merge leaves 1 line every base at a position, 
			// so SNPs are recorded in 2 lines: one with reference allele, one with alternative... 
			// Crazy eh? :)
			// 
			
			
//			if (doublePos) {
				System.out.println("doublePos problem :(");
				
				// Open the matrix file we just created
				
				File matrix = new File(filename + "_superMatrix_qual0cov1_chr" + chromosome + ".txt");
				Scanner scannerMatrix = new Scanner(matrix);
			
				// Open also the NEW writing file
				Writer writer2 = new FileWriter(filename + "_superMatrix_clean_qual0cov1_chr" + chromosome + ".txt");
				PrintWriter out2 = new PrintWriter(writer2);
				
				String head = scannerMatrix.nextLine();
				
				if (chromosome.charAt(0) == '1') {
					// Print the header
					out2.println(head);
				}
				
				// Initialize cleanLine, posMemory and chr
	       	
				String[] cleanLine = scannerMatrix.nextLine().split("\t");
				int posMemory = Integer.parseInt(cleanLine[1]);
				int chr = Integer.parseInt(cleanLine[0]);
				System.out.println("Chr: " + chr);
				
				//
				// Now the real stuff
				// 
				
				while ( scannerMatrix.hasNextLine() ) {
					String snp = scannerMatrix.nextLine();
				       	String[] splitSnp = snp.split("\t");
		       	
					// Just print where we are
        				if (chr != Integer.parseInt(splitSnp[0])) {
        					System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
        					posMemory = 0;
   		     			}
        			
        				chr = Integer.parseInt(splitSnp[0]);
        				int pos = Integer.parseInt(splitSnp[1]);

		        		// Check duplicate positions
        				if (pos > posMemory) {
        					// Check that they are not all Ns
        					boolean allN = true;
       						for (int p=3; p<cleanLine.length; p++) {
      	 						if (cleanLine[p].charAt(0) != 'N') {
       								allN = false;
       							}
       						}
       						if (!allN) {
  	         					for (int p=0; p<cleanLine.length; p++) {
        	   						out2.print(cleanLine[p] + "\t"); 
           						}
           						out2.print("\n"); 
       						}
   	     					cleanLine = splitSnp;
        				} else {
        					if (pos == posMemory) {
        						for (int p=3; p<cleanLine.length; p++) {
        							// IF this line is called, and the last one is N... Merge cleanLine
        							if ( (splitSnp[p].charAt(0) != 'N') && (cleanLine[p].charAt(0) == 'N') ) {
        								cleanLine[p] = splitSnp[p];
        							} else {
   		     	    						// If both are called, problem
            								if ( (splitSnp[p].charAt(0) != 'N') && (cleanLine[p].charAt(0) != 'N') ) {
        									System.out.println("Problem at: " + snp);
        								}
        							}
        						}
	        				} else {
        		    				if (pos < posMemory) {
								System.out.println("Problem at: " + snp);
            						}
        					}
        				}
    		    			posMemory = pos;
				}
				out2.close();
				
				
//			} else {
//				System.out.println("ok with double positions! :)");
//			}
		
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		VcfCombined_to_snpMatrix vcfCombined_to_snpMatrix = new VcfCombined_to_snpMatrix();
		vcfCombined_to_snpMatrix.setFileToConvert(args[0], args[1]);
	}
}
