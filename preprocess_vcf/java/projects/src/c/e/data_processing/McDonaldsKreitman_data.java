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


public class McDonaldsKreitman_data {
	public McDonaldsKreitman_data() {}
	
	public void setMatrixFile(String vcfFile, String chrom){
		
		try {
			int chromosome = Integer.parseInt(chrom);
			int genes = 0;
			
			// Get annotation file
			String gffFile = "/global/lv70590/Andrea/analyses/sharedStuff/TAIR10_GFF3_genes_transposons.gff";
			Scanner scanner = new Scanner(new File(gffFile));
			while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
	        	String[] splitSnp = snp.split("\t");
	        	if ( splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' && (splitSnp[0].charAt(3) == '1' || splitSnp[0].charAt(3) == '2' || splitSnp[0].charAt(3) == '3' || splitSnp[0].charAt(3) == '4' || splitSnp[0].charAt(3) == '5') ) {
		    		
	        		if (splitSnp[2].equals("gene") ) {
		        		genes = genes + 1;
		        	}
		        }
			}
			
			System.out.println(genes + " genes");
			
			String[] geneIDs = new String[genes];
			int[] chrG = new int[genes];
			int[] startG = new int[genes];
			int[] endG = new int[genes];
			
			genes = 0;
			scanner = new Scanner(new File(gffFile));
			while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
	        	String[] splitSnp = snp.split("\t");
	        	if ( splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' && (splitSnp[0].charAt(3) == '1' || splitSnp[0].charAt(3) == '2' || splitSnp[0].charAt(3) == '3' || splitSnp[0].charAt(3) == '4' || splitSnp[0].charAt(3) == '5') ) {
		    		
		        	if (splitSnp[2].equals("gene") ) {
		        		chrG[genes] = Character.getNumericValue(splitSnp[0].charAt(3)) - 1;
		        		startG[genes] = Integer.parseInt(splitSnp[3]);
		        		endG[genes] = Integer.parseInt(splitSnp[4]);
		        		geneIDs[genes] = splitSnp[8].split(";")[0].split("=")[1];
		    			// System.out.println(geneIDs[genes]);
		        		genes = genes + 1;
		        	}
		        }
			}
			
			// Got genes
			
			
			
			
			
			
			int classes = 4;
			
			int[] divS = new int[genes];
			int[] divN = new int[genes];
			
			//int[][] polS = new int[genes][classes];
			//int[][] polN = new int[genes][classes];
			int[] polS = new int[genes];
			int[] polN = new int[genes]; 	

        	for (int g=0; g<geneIDs.length; g++) {
        		divS[g] = 0;
        		divN[g] = 0;
        		//for (int cs=0; cs<classes; cs++) {
        			polS[g] = 0;
        			polN[g] = 0;
        		//}
			}
			
			
			int[] madeirans = {27153, 22638, 22022, 22019, 22017, 12908, 12763, 12762, 12761};
            int[] relicts = {9871, 9887, 9600, 9554, 9832, 9542, 9598, 9555, 9543, 9533, 9869, 9549, 9837, 9879, 9545};

			int[] madeiranIndexes = new int[madeirans.length];
			int[] relictIndexes = new int[relicts.length];
			
			
			
			int chrMem = 0;
			
			String[] splitIDs = null;
			
			File vcf_comb = new File(vcfFile);
            Scanner scannerVcf = new Scanner(vcf_comb);
            while ( scannerVcf.hasNextLine() ) {
                String snp1 = scannerVcf.nextLine();
                String[] splitSnp = snp1.split("\t");
                
                // Mind the header now
                if (snp1.charAt(0) == '#') {
                	if (snp1.charAt(1) != '#') {
                		splitIDs = snp1.split("\t");
                		int a =0;
                		int r=0;
                		for (int are=0; are<madeirans.length; are++) {
                			for (int i=9; i<splitIDs.length; i++) {
                				if (Integer.parseInt(splitIDs[i]) == madeirans[are]) {
                					madeiranIndexes[a] = i;
                					a = a + 1;
                				}
                			}
                		}
                		for (int rel=0; rel<relicts.length; rel++) {
                			for (int i=9; i<splitIDs.length; i++) {
                				if (Integer.parseInt(splitIDs[i]) == relicts[rel]) {
                					relictIndexes[r] = i;
                					r = r + 1;
                				}
                			}
                		}
				System.out.println("Madeirans: " + Arrays.toString(madeiranIndexes));
                                System.out.println("Relictss: " + Arrays.toString(relictIndexes));
                	}
                }
                // Got header
                
                if (splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' && Character.getNumericValue(splitSnp[0].charAt(3)) - 1 == chromosome) {
                	int chr = Character.getNumericValue(splitSnp[0].charAt(3)) - 1;
                    int pos = Integer.parseInt(splitSnp[1]);
                    if (chrMem != chr) {
                    	System.out.println("Chr: " + chr);
                        chrMem = chr;
                    }
                    
                	// Find the gene, if at all
                	for (int g=0; g<geneIDs.length; g++) {
                		if (chr == chrG[g] && pos >= startG[g] && pos <= endG[g]) {
                			
                			// Got the gene!
                			
                        	// Indels out - ref length == 1
                            if (splitSnp[3].length() == 1) {
                            	// check also alt length == 1
                            	String[] check = splitSnp[4].split(",");
                            	boolean inDel=false;
                            	for (int c=0; c< check.length; c++) {
                            		if (check[c].length() > 1) {
                            			inDel = true;
                                    }
                                }
                            	if (!inDel) {
                            		//
                            		// Look madeirans
                            		int[] bases = new int[4];
                            		for (int are=0; are<madeiranIndexes.length; are++) {
                                        if ( (splitSnp[madeiranIndexes[are]].split(":")[0].charAt(0) != '.') && (splitSnp[madeiranIndexes[are]].split(":")[1].charAt(0) != '.') && (splitSnp[madeiranIndexes[are]].split(":")[2].charAt(0) != '.') ){
                                        	int cov=Integer.parseInt(splitSnp[madeiranIndexes[are]].split(":")[2]);
                                            int qual=Integer.parseInt(splitSnp[madeiranIndexes[are]].split(":")[1]);
                                            
                                            if ( (cov >=3) && (qual >= 25) ) {
                                            	bases[Integer.parseInt(splitSnp[madeiranIndexes[are]].split(":")[0])] = bases[Integer.parseInt(splitSnp[madeiranIndexes[are]].split(":")[0])] + 1;
                                            }
                                        }
                            		}
                            		
                            		// Is it not just fix match?
                            		int mism = 0;
                            		for (int b=1; b<4; b++) {
                            			if (bases[b] != 0) {
                            				mism = mism + 1;
                            			}
                            		}
                            		int noN = 0;
                                        for (int b=0; b<4; b++) {
                                                noN = noN + bases[b];
                                        }
                        			if (mism == 1 && noN > 4) {
                            			
                        				char baseA = '.';
                            			if (bases[1] != 0) {
                            				baseA = splitSnp[4].charAt(0);
                            			} else {
                            				for (int b=2; b<4; b++) {
                            					if (bases[b] != 0) {
                            						baseA = splitSnp[4].split(",")[b-1].charAt(0);
                            					}
                            				}
                                		}
                        				// BaseA has madeiran base
                        				
                                		// Get annotation
                                		boolean syn = false;
                                		boolean nonSyn = false;
                                		
                                		String ann = splitSnp[7];
                                        String[] annSplitSemicolon = ann.split(";");
                                        for (int semic=0; semic<annSplitSemicolon.length; semic++) {

                                            if ( (annSplitSemicolon[semic].charAt(0) == 'A' && annSplitSemicolon[semic].charAt(1) == 'N' && annSplitSemicolon[semic].charAt(2) == 'N') ) {
                                                String[] annSplitEffects =  annSplitSemicolon[semic].split("=")[1].split(",");

                                                for (int eff=0; eff<annSplitEffects.length; eff++) {
                                                    String[] annPipeSplit = annSplitEffects[eff].split("\\|");
                                                    
                                                    // Is it the right base?
                                                	if ( (annPipeSplit[0].length() == 1) && annPipeSplit[0].charAt(0) == baseA) {
                                                		
                                                        String[] annAndSplit = annPipeSplit[1].split("&");
                                                        for (int a=0; a<annAndSplit.length; a++) {
                                                        	if (annAndSplit[a].equals("synonymous_variant") || annAndSplit[a].equals("start_retained_variant") || annAndSplit[a].equals("stop_retained_variant")) {
                                                        		syn = true;
                                                        	} else {
                                                        		if (annAndSplit[a].equals("nonsynonymous_variant") || annAndSplit[a].equals("protein_altering_variant") || annAndSplit[a].equals("missense_variant") || annAndSplit[a].equals("conservative_missense_variant") || annAndSplit[a].equals("non_conservative_missense_variant") || annAndSplit[a].equals("start_lost") || annAndSplit[a].equals("stop_gained") || annAndSplit[a].equals("stop_lost")) {
                                                                	nonSyn = true;
                                                                } else {
                                                                	if (annAndSplit[a].equals("intergenic_region") || annAndSplit[a].equals("downstream_gene_variant") || annAndSplit[a].equals("5KB_downstream_variant") || annAndSplit[a].equals("500B_downstream_variant") || annAndSplit[a].equals("downstream_transcript_variant") || annAndSplit[a].equals("upstream_gene_variant") || annAndSplit[a].equals("5KB_upstream_variant") || annAndSplit[a].equals("2KB_upstream_variant") || annAndSplit[a].equals("upstream_transcript_variant") || annAndSplit[a].equals("intron_variant")) {
                                                                	}
                                                                }
                                                            }
                                                        }
                                                    }  
                                                }
                                            }
                                        }
                                		// Now finally out
                                		int segr = 0;
                                		for (int b=0; b<4; b++) {
                                			if (bases[b] != 0) {
                                				segr = segr + 1;
                                			}
                                		}
                                		// segregating;
                                		if (segr > 1) {
                                			// Separate in classes
                                			//double freq = 0.0;
                                    		//for (int b=1; b<4; b++) {
                                    		//	if (bases[b] != 0) {
                                    		//		freq = (double)bases[b]/(double)(bases[b] + bases[0]);
                                    		//	}
                    				//		}
                                    		//for (int cs=0; cs<classes; cs++) {
                                    			//if ( freq > (double)cs*1.0/(double)classes && freq <= (double)(cs+1)*1.0/(double)classes) {
                                        			if (syn) {
                                        				polS[g] = polS[g] + 1;	//polS[g][cs] = polS[g][cs] + 1;
                                        			}
                                        			if (nonSyn) {
                                        				polN[g] = polN[g] + 1;	//polN[g][cs] = polN[g][cs] + 1;
                                        			}
                                    			//}
                                    		//}
                                		}
                                		// divergence;
                                		if (segr == 1 && bases[0] == 0) {
                                			if (syn) {
                                				divS[g] = divS[g] + 1;
                                			}
                                			if (nonSyn) {
                                				divN[g] = divN[g] + 1;
                                			}
                                		}	
                            		}
                            	}
                            }	
                		}
                	}
                }
            }
			
			
			// Output
            String res = "/global/lv70590/Andrea/analyses/mcDonKreit/";

        	for (int g=0; g<geneIDs.length; g++) {
 if (chrG[g] == chromosome) {       		
         		Writer writerD = new FileWriter(res + geneIDs[g] + "_mdk2_div.txt");
	            PrintWriter outD = new PrintWriter(writerD);
	            outD.print(divN[g] + "\t" + divS[g] + "\n");
	            outD.close();
	            
	            
				Writer writer = new FileWriter(res + geneIDs[g] + "_mdk2_pol.txt");
	            PrintWriter out = new PrintWriter(writer);
	            
	            out.print("x" + "\t" + "pN" + "\t" + "pS" + "\n");
	            
        		//for (int cs=0; cs<classes; cs++) {
        			out.print("0.5" + "\t" + polN[g] + "\t" + polS[g] + "\n");
        			//out.print( ((double)cs*1.0 + (double)0.5*1.0 )/(double)classes + "\t" + polN[g][cs] + "\t" + polS[g][cs] + "\n");
        		//}
	            
	            out.close();
}
        	}
			
			
			
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		McDonaldsKreitman_data mcDonaldsKreitman_data = new McDonaldsKreitman_data();
		mcDonaldsKreitman_data.setMatrixFile(args[0], args[1]);
	}
}
