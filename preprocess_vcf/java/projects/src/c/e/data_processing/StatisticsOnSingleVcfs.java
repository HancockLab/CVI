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

public class StatisticsOnSingleVcfs {
	
	public StatisticsOnSingleVcfs() {}
	
	public void setFileToConvert(String filename, String sampStr){
		
		try {
			int sample = Integer.parseInt(sampStr);
			
			// Open folder
			File fold = new File(filename);
			
			
			// Get Real SNPs
			
			BitSet[] realSnp = new BitSet[5];
			for (int c=0; c<5; c++) {
				realSnp[c] = new BitSet();
			}
			//File realFile = new File(fold + "/realSameMuts.txt");
			File realFile = new File(fold + "/realSnps/" + sample + "_realMuts.txt");			// "/realSameMuts.txt"); //  "/realSnps/" + sample + "_realMuts.txt");
			Scanner scannerR = new Scanner(realFile);
			
			while ( scannerR.hasNextLine() ) {
				String snp1 = scannerR.nextLine();
			       	String[] splitSnp = snp1.split("\t");
			       	int chr = Character.getNumericValue(splitSnp[0].charAt(3)) - 1;
			       	int pos = Integer.parseInt(splitSnp[1]) + 1;
		       // System.out.print(pos);	
		       	realSnp[chr].set(pos);
			}	

			// Got real SNPs
			
//refCut_sameMut.48.all.srt.vcf.b.gz.txt			
			
			// refCut_mut.96.all.srt.vcf
			// Get Shore calls
                        File shoreFile = new File(fold + "/shore/refCut_mut." + sample + ".all.srt.vcf");
			//File shoreFile = new File(fold + "/shore/refCut_sameMut." + sample + ".all.srt.vcf.b.gz.txt");
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

			// Got Shore
			
			
			
			// refCut_mut.65_mut.fastq.mpileup.all.snp.b.gz.snp 
			// Get my calls
			File myCallsSnp = new File(fold + "/myCalls/refCut_mut." + sample + "_mut.fastq.mpileup.all.snp.b.gz.snp");
			//File myCallsSnp = new File(fold + "/myCalls/refCut_sameMut." + sample + "_sameMut.fastq.mpileup.all.snp");
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
			File myCallsMask = new File(fold + "/myCalls/refCut_mut." + sample + "_mut.fastq.mpileup.mask.txt.b.gz.mask.txt");
			//File myCallsMask = new File(fold + "/myCalls/refCut_sameMut." + sample + "_sameMut.fastq.mpileup.mask.txt");
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
			
			
			
	        
	        
	        
	        
	        
	        // Now compare them :)
	        //
	        // I want to know for each pipeline:
	        // CoveredBases	Tot_realSnps	totCalledSNps	#realSNPs_inCoveredRegions	#right_SNPs	#realSNPs_calledAsMatches	#MatchesCalledAsSnps	#SNPs_inMissingData	density_of_mismatches	density_of_right_mismatches
	        // 
			
	        long[] numShore = new long[8];
	        long[] numMyPip = new long[8];
	        BitSet jolly = new BitSet();
	        
	        for (int c=0; c<5; c++) {
	        	numShore[c] = 0;
	        	numMyPip[c] = 0;
	        }
	        
	        
			// Open also the writing file
	        Writer writer = new FileWriter(fold + "/results/" + sample + "_artefactMismatches_shore.txt");
		PrintWriter out = new PrintWriter(writer);
		
		Writer writerM = new FileWriter(fold + "/results/" + sample + "_artefactMatches_shore.txt");
                PrintWriter outM = new PrintWriter(writerM);
		
		Writer writerE = new FileWriter(fold + "/results/" + sample + "_artefactErrors_shore.txt");
                PrintWriter outE = new PrintWriter(writerE);
		
		Writer writer2 = new FileWriter(fold + "/results/" + sample + "_artefactMismatches_myPip.txt");
		PrintWriter out2 = new PrintWriter(writer2);
	        
                Writer writerM2 = new FileWriter(fold + "/results/" + sample + "_artefactMatches_myPip.txt");
                PrintWriter outM2 = new PrintWriter(writerM2);
		
                Writer writerE2 = new FileWriter(fold + "/results/" + sample + "_artefactErrors_myPip.txt");
                PrintWriter outE2 = new PrintWriter(writerE2);
	
                Writer writer5 = new FileWriter(fold + "/results/" + sample + "_myPip_background.txt");
                PrintWriter out5 = new PrintWriter(writer5);
	        
		for (int c=0; c<5; c++) {
	        	
	        	// Shore:
	        	// Cov
	        	numShore[0] = numShore[0] + shoreCov[c].cardinality();
	        	
	        	// Real SNPs
	        	numShore[1] = numShore[1] + realSnp[c].cardinality();
	        	
	        	// Called SNPs
	        	numShore[2] = numShore[2] + shoreSnp[c].cardinality();
	        	
	        	// Covered SNPs 
	        	jolly = new BitSet();
	        	jolly.or(shoreCov[c]);
	        	jolly.and(realSnp[c]);
	        	numShore[3] = numShore[3] + jolly.cardinality();
	        	
	        	// Good SNPs
	        	jolly.and(shoreSnp[c]);
	        	numShore[4] = numShore[4] + jolly.cardinality();
	        	
	        	// real SNPs called as matches
	 		jolly = new BitSet(); 	
			jolly.or(shoreCov[c]);
			jolly.and(realSnp[c]);
			jolly.andNot(shoreSnp[c]);
			numShore[5] = numShore[5] + jolly.cardinality();
			
			// Output those for gowinda
	        	for (int p=0; p<=jolly.length(); p++) {
                                if (jolly.get(p)) {
                                        outM.print("Chr" + (c+1) + "\t" + p + "\n");
				}
                        }
			BitSet mem = new BitSet();
			mem.or(jolly);
			
	        	// Real Matches called as SNPs
	        	jolly = new BitSet();
	        	//jolly.or(shoreCov[c]);
	        	jolly.or(shoreSnp[c]);
	        	jolly.andNot(realSnp[c]);
	        	numShore[6] = numShore[6] + jolly.cardinality();
	        	
	        	// Output those for gowinda
	        	for (int p=0; p<=jolly.length(); p++) {
	        		if (jolly.get(p)) {
	        			out.print("Chr" + (c+1) + "\t" + p + "\n");
	        		}
	        	}
	        	
			jolly.or(mem);
			// Output those for gowinda
			for (int p=0; p<=jolly.length(); p++) {
                                if (jolly.get(p)) {
                                        outE.print("Chr" + (c+1) + "\t" + p + "\n");
                                }
                        }
			
	        	// Real SNPs in missing data
	        	jolly = new BitSet();
	        	jolly.or(realSnp[c]);
	        	jolly.andNot(shoreCov[c]);
	        	numShore[7] = numShore[7] + jolly.cardinality();
	        	


	        	// Mypip:
	        	// Cov
	        	numMyPip[0] = numMyPip[0] + myCov[c].cardinality();
	        	// Out background for gowinda
	        	for (int p=0; p<myCov[c].length(); p++) {
				if (myCov[c].get(p)) {
					out5.print("Chr" + (c+1) + "\t" + p + "\n");
				}
			}
			
	        	// Real SNPs
	        	numMyPip[1] = numMyPip[1] + realSnp[c].cardinality();
	        	
	        	// Called SNPs
	        	numMyPip[2] = numMyPip[2] + mySnp[c].cardinality();
	        	
	        	// Covered SNPs 
	        	jolly = new BitSet();
	        	jolly.or(myCov[c]);
	        	jolly.and(realSnp[c]);
	        	numMyPip[3] = numMyPip[3] + jolly.cardinality();
	        	
	        	// Good SNPs
	        	jolly.and(mySnp[c]);
	        	numMyPip[4] = numMyPip[4] + jolly.cardinality();
	        	
	        	// real SNPs called as matches
	        	jolly = new BitSet();
                        jolly.or(myCov[c]);
                        jolly.and(realSnp[c]);
                        jolly.andNot(mySnp[c]);
                        numMyPip[5] = numMyPip[5] + jolly.cardinality();
	        	
			// Output those for gowinda
			for (int p=0; p<=jolly.length(); p++) {
                                if (jolly.get(p)) {
                                        outM2.print("Chr" + (c+1) + "\t" + p + "\n");
                                }
                        }
                        BitSet mem2 = new BitSet();
                        mem2.or(jolly);
			
	        	// Real Matches called as SNPs
	        	jolly = new BitSet();
	        	//jolly.or(myCov[c]);
	        	jolly.or(mySnp[c]);
	        	jolly.andNot(realSnp[c]);
	        	numMyPip[6] = numMyPip[6] + jolly.cardinality();
	        	
	        	// Output those for gowinda
	        	for (int p=0; p<=jolly.length(); p++) {
	        		if (jolly.get(p)) {
	        			out2.print("Chr" + (c+1) + "\t" + p + "\n");
	        		}
	        	}
	        	jolly.or(mem2);
			// Output those for gowinda
			for (int p=0; p<=jolly.length(); p++) {
                                if (jolly.get(p)) {
                                        outE2.print("Chr" + (c+1) + "\t" + p + "\n");
                                }
                        }
	        	// Real SNPs in missing data
	        	jolly = new BitSet();
	        	jolly.or(realSnp[c]);
	        	jolly.andNot(myCov[c]);
	        	numMyPip[7] = numMyPip[7] + jolly.cardinality();
	        }
	        out.close();
		outM.close();
		outE.close();
	        out2.close();
	        outM2.close();
	        outE2.close();
		out5.close();
		
	        Writer writer3 = new FileWriter(fold + "/results/" + sample + "_shore_snpsComparisons.txt");
			PrintWriter out3 = new PrintWriter(writer3);
	        for (int s=0; s<numShore.length; s++) {
	        	out3.print(numShore[s] + "\t");
	        }
	        out3.print("\n");
	        out3.close();

	        Writer writer4 = new FileWriter(fold + "/results/" + sample + "_myPip_snpsComparisons.txt");
			PrintWriter out4 = new PrintWriter(writer4);
	        for (int s=0; s<numMyPip.length; s++) {
	        	out4.print(numMyPip[s] + "\t");
	        }
	        out4.print("\n");
	        out4.close();
	        
	        
			


				
	
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		StatisticsOnSingleVcfs statisticsOnSingleVcfs = new StatisticsOnSingleVcfs();
		statisticsOnSingleVcfs.setFileToConvert(args[0], args[1]);
	}
}
