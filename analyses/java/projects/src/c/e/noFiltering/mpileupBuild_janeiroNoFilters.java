package c.e.noFiltering;

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



public class mpileupBuild_janeiroNoFilters {
	

	private BitSet[] mask = null;
	private File fileMpileup = null;
	String snp1=null;
	private String[] splitSnp1 = null;
	private String[] variant = null;
	private String[] quality = null;
	int ref =0;
	int a =0;
	int t =0;
	int c =0;
	int g =0;
	int indelSign =0;
	int indelBlind =0;
	int jump = 0;
	int r =0;
	int run =0;
	String[] splitName = null;
		
	public mpileupBuild_janeiroNoFilters() {}
	
	

	public void setSnpFile(String filename){
		
		try {
			mask=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			
			String wereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/checkFiltering/noFilters";

			fileMpileup = new File(filename);
			Scanner scanner = new Scanner(fileMpileup);
			
			
            String wereWeResults = "/scratch/noFilters/";
			splitName = filename.split("/");
			String localName = splitName[splitName.length-1];
			

			// /home/CIBIV/andreaf/canaries/rawData/
			
			Writer writer1 = new FileWriter(wereWeResults + localName + ".hets"); //  filename + ".hets"
			PrintWriter out1 = new PrintWriter(writer1);
			
			Writer writer2 = new FileWriter(wereWeResults + localName + ".snp"); //  filename + ".snp"
			PrintWriter out2 = new PrintWriter(writer2);
			
			Writer writer3 = new FileWriter(wereWeResults + localName + ".indel"); //  filename + ".indel"
			PrintWriter out3 = new PrintWriter(writer3);
			
			Writer writer4 = new FileWriter(wereWeResults + localName + ".strangeSites"); //  filename + ".strangeSites"
			PrintWriter out4 = new PrintWriter(writer4);
			
			Writer writer5 = new FileWriter(wereWeResults + localName + ".all_intermediate2");
			PrintWriter out5 = new PrintWriter(writer5);
			
			
			int posLastSnp = 0;
			int posLastStrangeSite = 0;
			int chromosome =0;
			int basesInARow = 0;
			char lastBase = '\0';
			Boolean duplication = false;
			
			while ( scanner.hasNextLine() ) { 
				
				snp1 = scanner.nextLine();
	        	splitSnp1 = snp1.split("\t");
	        	
	        	
	        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
		        	
	        		int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        	int posSNP = Integer.parseInt(splitSnp1[1]);
	        		int coverage = Integer.parseInt(splitSnp1[3]);
		        	char base = splitSnp1[2].charAt(0);
		        	
		        	
		        	/*
		        	if (base == lastBase) {
		        		basesInARow = basesInARow +1;
		        	} else {
		        		basesInARow = 0;
		        	}
		        	
		        	if (basesInARow >= 5) {
		        		for (int clear=0; clear<=10; clear++) {
    		        		mask[chr-1].clear(posSNP-clear);
						}
		        		jump = 5;
		        	}
	        		
	        		*/
	        		
	        		// if (jump == 0) {
	        			if ( (coverage >= 5) && (coverage <= 50) ) {
			        		
			        		mask[chr-1].set(posSNP);
			        		
			        		String var = splitSnp1[4];
		        			variant = var.split("");
		        			
		        			String qual = splitSnp1[5];
		        			quality = qual.split("");
		        			
		        			ref =0;
	        				a =0;
	        				t =0;
	        				c =0;
	        				g =0;
	        				indelSign =0;
	        				indelBlind =0;
		        			
	        				r =1;
	        				
		        			for (int run=1; run<variant.length; run++) {
		        				
		        					
	        					if ( variant[run].charAt(0) != '$' ) {
	        						
		        					if (variant[run].charAt(0) == '^') {
		        						run =run+2;
		        						coverage = coverage -1;
		        					} else {
		        						if ( (variant[run].charAt(0) == '-') || (variant[run].charAt(0) == '+') ) {
			        						
			        						run = run +1;
			        						int size = Integer.parseInt(variant[run]);
			        						run = run + size;
			        						indelSign = indelSign+1;
			        						r =r-1;
	        							} else {
	        								if ( (variant[run].charAt(0) == '*') ) {
	        									indelBlind = indelBlind+1;
				        					}
	        								
	        								if ( ((int)(quality[r].charAt(0)) - 33) >= 30) {
	        									if ( (variant[run].charAt(0) == '.') || (variant[run].charAt(0) == ',') ) {
						        					ref = ref + 1;
						        				} else {
						        					if ( (variant[run].charAt(0) == 'a') || (variant[run].charAt(0) == 'A') ) {
						        						a = a+1;
						        					}
						        					if ( (variant[run].charAt(0) == 't') || (variant[run].charAt(0) == 'T') ) {
						        						t = t+1;
						        					}
						        					if ( (variant[run].charAt(0) == 'c') || (variant[run].charAt(0) == 'C') ) {
						        						c = c+1;
						        					}
						        					if ( (variant[run].charAt(0) == 'g') || (variant[run].charAt(0) == 'G') ) {
						        						g = g+1;
						        					}
						        				}
		        							} else {
					        					coverage = coverage -1;
					        					if ((int)(quality[r].charAt(0) - 33) < 0) {
					        						System.out.println("Danger! Wrong quality encoding at: " + snp1);
					        					}
					        				}
	        							}
	        						}
		        				} else {
		        					r = r-1;
		        				}
		        				
	        					r = r +1;
		        			}
		        			
		        			
		        			
		        			
		        			
		        			
		        			
		        			
		        			
		        			// Now write!
		        			
		        			
		        			if  ( (indelSign >= 5) ) {
		        				
		        				/*
		        				if ( (posSNP <= (posLastSnp +5) ) && (chromosome == chr) ) {
	        						for (int clear=0; clear<=5; clear++) {
	            		        		mask[chr-1].clear(posSNP-clear);
	        						}
	        		        		jump = 5;

	        						posLastSnp = posSNP;
	        			        	chromosome = chr;
	        		        		
	        					} else {
	        						*/
	        						out3.println(snp1);
	    	        				/*
	        						posLastSnp = posSNP;
	        			        	chromosome = chr;
	            				} */
    	        			}
		        			
		        			if (coverage >= 5)  {
		        				// if indelblind
	        					Double propRef = (double) ref / coverage;
			        			
		        				if (propRef < 0.8) {
		        					
		        					/*
		        					if ( (posSNP <= (posLastSnp +5) ) && (chromosome == chr) ) {
		        						for (int clear=0; clear<=5; clear++) {
		            		        		mask[chr-1].clear(posSNP-clear);
		        						}
		        		        		jump = 5;
		        		        		
		        					} else { 
		        						
		        						posLastSnp = posSNP;
		        			        	chromosome = chr;
		            					*/
		            					
		            					int[] calling = {ref, a, t, c, g, indelBlind};
	            						// int[] callingStatic = {ref, a, t, c, g};
	            						
	                    				Arrays.sort(calling);
	                    				
	                    				Double ratio = (double)(calling[calling.length-1]) / (double)(coverage);
	                    				
	            	        			if ( (ratio >= 0.8) ) {// && (calling[calling.length-2] == ref) ) {
	            	        				
	            	        				duplication = false;
	            	        				
	            	        				out2.print(snp1);
	            	        				out5.print(snp1);
	            	        				
	            	        				// New, inserted in a working thing... Until // End
                                            out2.print("\t");
                                            out5.print("\t");
                                            
                                            if (a > coverage/2) {
                                                    out2.print("A");
                                                    out2.print("\n");

                                                    out5.print("A");
                                                    out5.print("\n");

                                            }
                                            if (t > coverage/2) {
                                                    out2.print("T");
                                                    out2.print("\n");
                                                    
                                                    out5.print("T");
                                                    out5.print("\n");
                                            }
                                            if (c > coverage/2) {
                                                    out2.print("C");
                                                    out2.print("\n");
                                                    
                                                    out5.print("C");
                                                    out5.print("\n");
                                            }
                                            if (g > coverage/2) {
                                                    out2.print("G");
                                                    out2.print("\n");
                                                    
                                                    out5.print("G");
                                                    out5.print("\n");
                                            }
                                            if (indelBlind > coverage/2) {
                                                    out2.print("-");
                                                    out2.print("\n");

                                                    out5.print("-");
                                                    out5.print("\n");

                                            }
                                            // End

	            	        			} else {
	            	        				
	            	        				if (duplication) {
	            	        					for (int dupl=posLastStrangeSite; dupl <posSNP; dupl++) {
			            	        				mask[chr-1].clear(dupl);
	            	        					}
	            	        				}
	            	        				mask[chr-1].clear(posSNP);
	            	        				
	            	        				out5.print(snp1);
	                                        out5.print("\t");
	                                        out5.print("N");
	                                        out5.print("\n");
	                                        
	                                        duplication = true;
	                                        posLastStrangeSite = posSNP;
			        			        	chromosome = chr;
			        			        	
	            	        				if ( (ratio >= 0.4) && (ratio <= 0.6) && (coverage <= 30) ) {
                	        					out1.println(snp1);
	                	        			} else {
                    	        				out4.println(snp1);
	                	        			}
	            	        			}
	            	        		// }
		    			        } else {
                                    out5.print(snp1);
                                    out5.print("\t");
                                    out5.print(splitSnp1[2].charAt(0));
                                    out5.print("\n");
		    			        }
		        			} else {
    	        				mask[chr-1].clear(posSNP);
    	        				
    	        				out5.print(snp1);
                                out5.print("\t");
                                out5.print("N");
                                out5.print("\n");
		        			}
			        	} else {
			        		out5.print(snp1);
	                        out5.print("\t");
	                        out5.print("N");
	                        out5.print("\n");
			        	}
	        		/* } else {
		        		jump = jump -1;
		        		
		        		out5.print(snp1);
                        out5.print("\t");
                        out5.print("N");
                        out5.print("\n");
		        	} */
	        		
	        		lastBase = base;
	        	}
			}			
			
			
			
			
			
			
			
			
			
			
			
			


			Writer writer = new FileWriter(wereWeResults + localName + ".mask.txt"); //  filename + ".mask.txt"
			PrintWriter out = new PrintWriter(writer);
			
			for (int c=0; c<5; c++) {
				
				out.print(">Chr" + (c+1));
				out.print("\n");
				
				// System.out.println(mask[c]);
				System.out.println(mask[c].cardinality());
				
				for (int bit =0; bit< mask[c].length(); bit++) {
					if (mask[c].get(bit)) {
						out.print("1");
					}
					if (!mask[c].get(bit)) {
						out.print("0");
					}
				}
				out.print("\n");
			}
			
			out.close();
			out1.close();
			out2.close();
			out3.close();
			out4.close();
			out5.close();
			
			
			
			// now revise zeroes in the mask, erase them from mpileup.all
			
			// Writer writerAll = new FileWriter(wereWeResults + localName + ".all");
			// PrintWriter outAll = new PrintWriter(writerAll);
			
			Writer writerAllVar = new FileWriter(wereWeResults + localName + ".all.Var");
			PrintWriter outAllVar = new PrintWriter(writerAllVar);
			
			Writer writerSnp = new FileWriter(wereWeResults + localName + ".all.snp");
			PrintWriter outSnp = new PrintWriter(writerSnp);
			
			File fileIntermediate = new File(wereWeResults + localName + ".all_intermediate2"); // filename + ".all_intermediate2"
			Scanner scannerIntermediate = new Scanner(fileIntermediate);
			

			while ( scannerIntermediate.hasNextLine() ) { 
				
				snp1 = scannerIntermediate.nextLine();
	        		splitSnp1 = snp1.split("\t");
	        	
	    		    	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
	        		
	        			int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        		int posSNP = Integer.parseInt(splitSnp1[1]);
		        			
		     	   		if (mask[chr-1].get(posSNP)) {
		        			
		       		 		// outAll.println(snp1);

						
		        			int refBase = splitSnp1[2].charAt(0);
			        		int base = splitSnp1[6].charAt(0);
			        	
			       		 	if (base != 'N') {
			        		
			        			if (base != refBase) {
				        			outAllVar.println(snp1);
				        		
				   		     		if ( (base == 'A') || (base == 'C') || (base == 'T') || (base == 'G') ) {
					        			outSnp.println(snp1);
				        			}
					        	}
				        	} else {
			        			System.out.println("Danger at: " + snp1);
			        		}	
						
		        		} /* else {
		        			
		        			for (int tab = 0; tab<splitSnp1.length-1; tab++) {
		        				
		        				outAll.print(splitSnp1[tab]);
                    				        outAll.print("\t");
		        			}
		        			outAll.print("N");
                        			outAll.print("\n");
		       		 	} */
	        		}
			}
			
			// outAll.close();
			outSnp.close();
			outAllVar.close();
			// End
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		mpileupBuild_janeiroNoFilters MpileupBuild_janeiroNoFilters = new mpileupBuild_janeiroNoFilters();
		MpileupBuild_janeiroNoFilters.setSnpFile(args[0]); // args[0]
		
		// /Volumes/CVI_Seagate/rawData/bam/cvi-0/CVI_0_salk.12001_110_-1.realigned.sort.bam.mpileup
		// /CVI_0_gmi.11015_110_-1.realigned.sort.bam.mpileup
		
		// /Volumes/CVI_Seagate/Canaries/cvi-0/Cvi-0_salk.sam.sortP.sam.duprem.sam.bam.mq20.bam.sam_unique.sam.bam.mpileup"
		// Cvi-0_salk.sam.sortP.sam.duprem.sam.bam.mq20.bam.sam_unique.sam.bam.mpileup
		// CVI_0_gmi.11015_110_-1.realigned.sort.bam.sam.sam.sortP.sam.duprem.sam.bam.mq20.bam.sam_unique.sam.bam.mpileup
		// "/home/CIBIV/andreaf/canaries/rawData/CVI_0_gmi.11015_110_-1.realigned.sort.bam.sam.sam.sortP.sam.duprem.sam.bam.mq20.bam_-gq_noQD.vcf");  
	}
}
