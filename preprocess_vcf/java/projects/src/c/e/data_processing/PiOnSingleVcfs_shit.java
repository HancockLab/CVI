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

public class PiOnSingleVcfs_shit {
	
	public PiOnSingleVcfs_shit() {}
	
	public void setFileToConvert(String filename, String sampStr){
		
		try {
			int sample = Integer.parseInt(sampStr);
			
			// Open folder
			File fold = new File(filename);
			
			
			
			
			
			
			//////
			///		Get sample 1
			//////
			
			
			
			
			
			
			
			
			
			// Get Real SNPs
			
			BitSet[] realSnp = new BitSet[5];
			for (int c=0; c<5; c++) {
				realSnp[c] = new BitSet();
			}
			
			File realFile = new File(fold + "/realSameMuts.txt"); // "/realSnps/" + sample + "_realMuts.txt");
			Scanner scannerR = new Scanner(realFile);
			
			while ( scannerR.hasNextLine() ) {
				String snp1 = scannerR.nextLine();
		       	String[] splitSnp = snp1.split("\t");
		       	int chr = Character.getNumericValue(splitSnp[0].charAt(3)) - 1;
		       	int pos = Integer.parseInt(splitSnp[1]) + 1;
		       	
		       	realSnp[chr].set(pos);
			}	
			int rs = 0;
			for (int c=0; c<5; c++) {
				rs = rs + realSnp[c].cardinality();
			}
			System.out.println("realsnps: " + rs);
			// Got real SNPs
			
			
			
			
			// Get Shore calls
	
			File shoreFile = new File(fold + "/shore/refCut_sameMut." + sample + ".all.srt.vcf.b.gz.txt");
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
			    				
			    				if ( (cov >=2) && (qual >= 17) ) {
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
			int shS = 0;
                        for (int c=0; c<5; c++) {
                                shS = shS + shoreSnp[c].cardinality();
                        }
                        System.out.println("shore snps: " + shS);
			// Got Shore
			
			
			
			
			// Get my calls
                        File myCallsSnp = new File(fold + "/myCalls/refCut_sameMut." + sample + "_sameMut.fastq.mpileup.all.snp");
			// File myCallsSnp = new File(fold + "/myCalls/refCut_mut." + sample + "_mut.fastq.mpileup.all.snp.b.gz.snp");
			Scanner scannerM = new Scanner(myCallsSnp);

			BitSet[] mySnp = new BitSet[5];
			BitSet[] myCov = new BitSet[5];
			for (int c=0; c<5; c++) {
				mySnp[c] = new BitSet();
				myCov[c] = new BitSet();
			}
			
			while ( scannerM.hasNextLine() ) { 
				String snp1 = scannerM.nextLine();
				String[] splitSnp1 = snp1.split("\t");
	        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
		        	int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        	int posSNP = Integer.parseInt(splitSnp1[1]);
		        	char base = splitSnp1[6].charAt(0);
		        	char refBase = splitSnp1[2].charAt(0);
		        	if ( (base != 'S') && (base != 'N') && ( (refBase == 'A') || (refBase == 'T') || (refBase == 'C') || (refBase == 'G') ) ) {
		        		mySnp[chr-1].set(posSNP);
		        	}
		        }
	        }

		int myS = 0;
                        for (int c=0; c<5; c++) {
                                myS = myS + mySnp[c].cardinality();
                        }
                        System.out.println("my snps: " + myS);




                File myCallsMask = new File(fold + "/myCalls/refCut_sameMut." + sample + "_sameMut.fastq.mpileup.mask.txt");
                //File myCallsMask = new File(fold + "/myCalls/refCut_mut." + sample + "_mut.fastq.mpileup.mask.txt.b.gz.mask.txt");
		Scanner scannerMm = new Scanner(myCallsMask);
			int chrCVI=0;
        	String lineMask=null;
	        for (int m=0; m<10; m++) {
	        	lineMask = scannerMm.nextLine();
	        	if (m%2 == 1) {
		        	for (int i=1; i<lineMask.length(); i++) {
		        		if (lineMask.charAt(i) == ('1')) {
		        			myCov[chrCVI].set(i);
		        		}
		        	}
		        	chrCVI=chrCVI+1;
	        	}
	        }
			
			// Got my calls
			
			
			
	        
	        
	        

			//////
			///		Get sample 2
			//////

	        Writer writer = new FileWriter(fold + "/results/" + sample + "_pairwDiff_shore.txt");
			PrintWriter out = new PrintWriter(writer);

	        Writer writer2 = new FileWriter(fold + "/results/" + sample + "_pairwDiff_myPip.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			
	        for (int sample2=0; sample2<111; sample2++) {
	        	
	        	// Get Real SNPs
			/*	
				BitSet[] realSnp2 = new BitSet[5];
				for (int c=0; c<5; c++) {
					realSnp2[c] = new BitSet();
				}
        
				File realFile2 = new File(fold + "/realSnps/" + sample2 + "_realMuts.txt");
				Scanner scannerR2 = new Scanner(realFile2);
				
				while ( scannerR2.hasNextLine() ) {
					String snp1 = scannerR2.nextLine();
			       	String[] splitSnp = snp1.split("\t");
			       	int chr = Character.getNumericValue(splitSnp[0].charAt(3)) - 1;
			       	int pos = Integer.parseInt(splitSnp[1]);
			       	
			       	realSnp2[chr].set(pos);
				}	
			 int rs2 = 0;
                        for (int c=0; c<5; c++) {
                                rs2 = rs2 + realSnp2[c].cardinality();
                        }
                        System.out.println("realsnps2: " + rs2);
*/

				// Got real SNPs
				
				
				
				
				// Get Shore calls
                        	File shoreFile2 = new File(fold + "/shore/refCut_sameMut." + sample2 + ".all.srt.vcf.b.gz.txt");
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
				    				
				    				if ( (cov >=2) && (qual >= 17) ) {
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
			int shS2 = 0;
                        for (int c=0; c<5; c++) {
                                shS2 = shS2 + shoreSnp2[c].cardinality();
                        }
                        System.out.println("shore snps2: " + shS2);


				// Got Shore
				
				
				
				
				// Get my calls
	                        File myCallsSnp2 = new File(fold + "/myCalls/refCut_sameMut." + sample2 + "_sameMut.fastq.mpileup.all.snp");
				//File myCallsSnp2 = new File(fold + "/myCalls/refCut_mut." + sample2 + "_mut.fastq.mpileup.all.snp.b.gz.snp");
				Scanner scannerM2 = new Scanner(myCallsSnp2);

				BitSet[] mySnp2 = new BitSet[5];
				BitSet[] myCov2 = new BitSet[5];
				for (int c=0; c<5; c++) {
					mySnp2[c] = new BitSet();
					myCov2[c] = new BitSet();
				}
				
				while ( scannerM2.hasNextLine() ) { 
					String snp1 = scannerM2.nextLine();
					String[] splitSnp1 = snp1.split("\t");
		        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
			        	int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
			        	int posSNP = Integer.parseInt(splitSnp1[1]);
			        	char base = splitSnp1[6].charAt(0);
			        	char refBase = splitSnp1[2].charAt(0);
			        	if ( (base != 'S') && (base != 'N') && ( (refBase == 'A') || (refBase == 'T') || (refBase == 'C') || (refBase == 'G') ) ) {
			        		mySnp2[chr-1].set(posSNP);
			        	}
			        }
		        }
			int myS2 = 0;
                        for (int c=0; c<5; c++) {
                                myS2 = myS2 + mySnp2[c].cardinality();
                        }
                        System.out.println("my snps2: " + myS2);

	                        File myCallsMask2 = new File(fold + "/myCalls/refCut_sameMut." + sample2 + "_sameMut.fastq.mpileup.mask.txt");
				//File myCallsMask2 = new File(fold + "/myCalls/refCut_mut." + sample2 + "_mut.fastq.mpileup.mask.txt.b.gz.mask.txt");
				Scanner scannerMm2 = new Scanner(myCallsMask2);
				chrCVI=0;
	        	lineMask=null;
		        for (int m=0; m<10; m++) {
		        	lineMask = scannerMm2.nextLine();
		        	if (m%2 == 1) {
			        	for (int i=1; i<lineMask.length(); i++) {
			        		if (lineMask.charAt(i) == ('1')) {
			        			myCov2[chrCVI].set(i);
			        		}
			        	}
			        	chrCVI=chrCVI+1;
		        	}
		        }
				
				// Got my calls
				
		        
		        
		        
		        
		        
		        
		        
		        //////
		        ///			Now pi of artefacts for each pipeline
		        //////

		        BitSet orr = new BitSet();
		        BitSet andd = new BitSet();
			BitSet orr2 = new BitSet();
                        BitSet andd2 = new BitSet();
		        BitSet jolly = new BitSet();
		        
		        long diffShore = 0;
		        long bpShore = 0;
		        
		        long diffMypip = 0;
		        long bpMypip = 0;
		        
			Writer writerSs = new FileWriter(fold + "/results/" + sample + "_" + sample2 + "_shoreSegrega.txt");
                	PrintWriter outSs = new PrintWriter(writerSs);

			Writer writerSb = new FileWriter(fold + "/results/" + sample + "_" + sample2 + "_shoreBackground.txt");
                        PrintWriter outSb = new PrintWriter(writerSb);

			Writer writerMs = new FileWriter(fold + "/results/" + sample + "_" + sample2 + "_myPipSegrega.txt");
                        PrintWriter outMs = new PrintWriter(writerMs);

                        Writer writerMb = new FileWriter(fold + "/results/" + sample + "_" + sample2 + "_myPipBackground.txt");
                        PrintWriter outMb = new PrintWriter(writerMb);

		        for (int c=0; c<5; c++) {
		        	
		        	// Shore first
		        	orr = new BitSet();
		        	andd = new BitSet();
		        	
		        	// Real Matches called as SNPs
		        	jolly = new BitSet();
		        	jolly.or(shoreCov[c]);
		        	jolly.and(shoreCov2[c]);
				bpShore = bpShore + jolly.cardinality();
				// Out shore background
                                for (int p=0; p<=jolly.length(); p++) {
                                        if (jolly.get(p)) {
                                                outSb.print("Chr" + (c+1) + "\t" + p + "\n");
                                        }
                                }
		        	jolly.and(shoreSnp[c]);
		        	jolly.andNot(realSnp[c]);
				// Record false mismatches 
		        	orr.or(jolly);
		        	andd.or(jolly);
		        	
				// Real Mismatches called as a match
				jolly = new BitSet();
                                jolly.or(shoreCov[c]);
                                jolly.and(shoreCov2[c]);
				jolly.and(realSnp[c]);
                                jolly.andNot(shoreSnp[c]);
				
				// Record false matches
				orr.or(jolly);
                                andd.or(jolly);



				// Second sample
				orr2 = new BitSet();
                                andd2 = new BitSet();
				
		        	jolly = new BitSet();
		        	jolly.or(shoreCov[c]);
		        	jolly.and(shoreCov2[c]);
		        	jolly.and(shoreSnp2[c]);
		        	jolly.andNot(realSnp[c]);		//2
		        	// Record false mismatches 
		        	orr2.or(jolly);
		        	andd2.or(jolly);
		        	
				// Real Mismatches called as a match
				jolly = new BitSet();
                                jolly.or(shoreCov[c]);
                                jolly.and(shoreCov2[c]);
                                jolly.and(realSnp[c]);
                                jolly.andNot(shoreSnp2[c]);
				
                                // Record false matches
                                orr2.or(jolly);
                                andd2.or(jolly);

				
				// Now combine the 2

				orr.or(orr2);
				andd.and(andd2);
				
		        	orr.andNot(andd);
		        	
		        	diffShore = diffShore + orr.cardinality();
		        	
		        	for (int p=0; p<=orr.length(); p++) {
                                        if (orr.get(p)) {
                                                outSs.print("Chr" + (c+1) + "\t" + p + "\n");
                                        }
                                }
		        	
		        	
		        	
		        	
		        	
		        	
		        	
		        	// MyPip now
		        	
		        	orr = new BitSet();
		        	andd = new BitSet();
		        	
		        	// Real Matches called as SNPs
		        	jolly = new BitSet();
		        	jolly.or(myCov[c]);
		        	jolly.and(myCov2[c]);
                                bpMypip = bpMypip + jolly.cardinality();
				
                                for (int p=0; p<=jolly.length(); p++) {
                                        if (jolly.get(p)) {
                                                outMb.print("Chr" + (c+1) + "\t" + p + "\n");
                                        }
                                }
				jolly.and(mySnp[c]);
		        	jolly.andNot(realSnp[c]);
                                
				// Record false mismatches 
		        	orr.or(jolly);
		        	andd.or(jolly);
		        	
                                // Real Mismatches called as a match
                                jolly = new BitSet();
                                jolly.or(myCov[c]);
                                jolly.and(myCov2[c]);
                                jolly.and(realSnp[c]);
                                jolly.andNot(mySnp[c]);
		        	
                                // Record false matches
                                orr.or(jolly);
                                andd.or(jolly);

 				
				// Second sample
				
				orr2 = new BitSet();
                                andd2 = new BitSet();

                                jolly = new BitSet();
                                jolly.or(myCov[c]);
                                jolly.and(myCov2[c]);
                                jolly.and(mySnp2[c]);
                                jolly.andNot(realSnp[c]);               //2
                                // Record false mismatches 
                                orr2.or(jolly);
                                andd2.or(jolly);
				
                                // Real Mismatches called as a match
                                jolly = new BitSet();
                                jolly.or(myCov[c]);
                                jolly.and(myCov2[c]);
                                jolly.and(realSnp[c]);
                                jolly.andNot(mySnp2[c]);
                                
				// Record false matches
				orr2.or(jolly);
                                andd2.or(jolly);


                                // Now combine the 2

                                orr.or(orr2);
                                andd.and(andd2);
				
                                orr.andNot(andd);
				
		        	diffMypip = diffMypip + orr.cardinality();
				
				for (int p=0; p<=orr.length(); p++) {
                                        if (orr.get(p)) {
                                                outMs.print("Chr" + (c+1) + "\t" + p + "\n");
                                        }
                                }
		        }
		        outSs.close();
			outSb.close();
			outMs.close();
			outMb.close();
			
		        double piShore = (double)diffShore/(double)bpShore;
		        double piMypip = (double)diffMypip/(double)bpMypip;
		    	
		        // Output
		        out.print(piShore + "\t");
		        out2.print(piMypip + "\t");
		}
	       	out.print("\n");
		out.close();
		out2.print("\n");
		out2.close();
	        	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PiOnSingleVcfs_shit piOnSingleVcfs_shit = new PiOnSingleVcfs_shit();
		piOnSingleVcfs_shit.setFileToConvert(args[0], args[1]);
	}
}
