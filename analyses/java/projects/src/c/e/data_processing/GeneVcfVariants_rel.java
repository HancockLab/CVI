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
import java.text.DecimalFormat;

public class GeneVcfVariants_rel {
	public GeneVcfVariants_rel() {}
	
	public void setFold(String foldName, String res){
		
		try {
			
			File[] listF = new File(foldName).listFiles();
			
			for (int f=0; f<listF.length; f++) {
				int nonSynD = 0;
				int synD = 0;
				int nonSynP = 0;
				int synP = 0;
				
				int areNum = 9;
				
				Scanner scanner = new Scanner(listF[f]);
				while ( scanner.hasNextLine() ) {
					String snp = scanner.nextLine();
		        		String[] splitSnp = snp.split("\t");
		        	
		        		if (snp.charAt(0) == '#') {
			        		if (snp.charAt(1) != '#') {
			        			for (int s=9; s<9+areNum; s++) {
									System.out.print(splitSnp[s] + "\t");
							}
		        				System.out.print("\n");
			        			for (int r=9+areNum; r<splitSnp.length; r++) {
	                                                        System.out.print(splitSnp[r] + "\t");
                                                        }
                                                        System.out.print("\n");
						}
		        		} else {
		        		// Real stuff here
		        		// 
		        		boolean match = false;
		        		boolean mism = false;
		        		boolean ns = false;
		        		char base = '.';
		        		
					int[] relBaseInt = new int[10];
					for (int r=9+areNum; r<splitSnp.length; r++) {
						if (splitSnp[r].charAt(0) != '.') {
							relBaseInt[Character.getNumericValue(splitSnp[r].charAt(0))] = relBaseInt[Character.getNumericValue(splitSnp[r].charAt(0))] + 1;
						}
					}
					// Find the majoritarian base in relicts
					int max = 0;
					char relBase = '.';
					for (int b=0; b<relBaseInt.length; b++) {
						if ( relBaseInt[b] > max) {
							relBase = (char)(b + '0');
							max = relBaseInt[b];
						}
					}
					//System.out.println(relBase);
					
					for (int s=9; s<9+areNum; s++) {

						// System.out.println(splitSnp[s].charAt(0));
	    					if (splitSnp[s].charAt(0) != '.') {
	        					if (splitSnp[s].charAt(0) != relBase) {
	            						mism = true;
	    	        				} else {
	    		       					match = true;
	    		        			}
	    					} else {
		        				ns = true;
		    				}
			        	}
	        			//System.out.println(ns );
	        			//System.out.println(mism);
	        			//System.out.println(match);
		        		// 
		        		if (mism) {
		        			if (!match) {
							// Diverrgence!
				        		for (int s=9; s<9+areNum; s++) {
								if (splitSnp[s].charAt(0) != '.') {
			    						if (Character.getNumericValue(splitSnp[s].charAt(0)) == 0) {
			    		        				base = splitSnp[3].charAt(0);	        				
			    		        			} else {
			    		        				base = splitSnp[4].split(",")[Character.getNumericValue(splitSnp[s].charAt(0))-1].charAt(0);
			    	        				}
				        			}
				        		}
			        			
			        			String[] ann = splitSnp[7].split(";")[4].split("=")[1].split(",");
			        			boolean mis = false;
		        				boolean syn = false;
		        				for (int a=0; a<ann.length; a++) {
			        				if (ann[a].charAt(0) == base) {
			        					String annO = ann[a].split("\\|")[1];
			        					if (annO.equals("missense_variant")) {
			        						mis = true;
			        					}
			        					if (annO.equals("synonymous_variant")) {
			        						syn = true;
			        					}
			        				}
			        			}
			        			if (mis && !syn) {
			        				nonSynD = nonSynD + 1;
			        			}
			        			if (syn && !mis) {
		    						synD = synD + 1;
			        			}
		        			} else {
		        				// Polymorphism
		        				base = '.';
				        		for (int s=9; s<9+areNum; s++) {
								if (splitSnp[s].charAt(0) != '.') {
			    						if (splitSnp[s].charAt(0) != relBase) {
			    	        					
										if (Character.getNumericValue(splitSnp[s].charAt(0)) == 0) {	
											// Only mismatches here
			    								base = splitSnp[3].charAt(0);   				
			    	        					} else {
			    	        						base = splitSnp[4].split(",")[Character.getNumericValue(splitSnp[s].charAt(0))-1].charAt(0);
			    	        					}
									}
				        			}
				        		}
			        			
			        			String[] ann = splitSnp[7].split(";")[4].split("=")[1].split(",");
			        			boolean mis = false;
		        				boolean syn = false;
		        				for (int a=0; a<ann.length; a++) {
			        				if (ann[a].charAt(0) == base) {
			        					String annO = ann[a].split("\\|")[1];
			        					if (annO.equals("missense_variant")) {
			        						mis = true;
			        					}
			        					if (annO.equals("synonymous_variant")) {
			        						syn = true;
			        					}
			        				}
			        			}
			        			if (mis && !syn) {
			        				nonSynP = nonSynP + 1;
			        			}
			        			if (syn && !mis) {
		    						synP = synP + 1;
			        			}
		        			}
		        		}
		        	}
				}
				System.out.println(nonSynP + "\t" + synP + "\t" + nonSynD + "\t" + synD);
				
				Writer writer = new FileWriter(res + listF[f].getName().split("\\.")[0] + ".txt");
	            		PrintWriter out = new PrintWriter(writer);
	            		out.print(listF[f].getName().split("\\.")[0] + "\t" + nonSynP + "\t" + synP + "\t" + nonSynD + "\t" + synD + "\n");
	            		out.close();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GeneVcfVariants_rel geneVcfVariants_rel = new GeneVcfVariants_rel();
		geneVcfVariants_rel.setFold(args[0], args[1]);
	}
}
