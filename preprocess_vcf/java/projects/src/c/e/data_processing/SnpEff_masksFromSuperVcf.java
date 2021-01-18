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

public class SnpEff_masksFromSuperVcf {
	
	private SnpEff_masksFromSuperVcf() {}

	public void setSnpFile(String filename, String samplesFile, String wereWeResult){
		
		
		try {
			int[] iberianIndexes = null;
			int[] canarianIndexes = null;
			int[] capeVerdeanIndexes = null;
			int[] moroccanIndexes = null;
			int[] madeiranIndexes = null;
			int[] restOfWorldIndexes = null;
			int[] allButCanCviIndexes = null;
			String[] splitIDs = null;
			int chrMem = 10;
			
			// get clean samples from a file
			////
			//		SANTO
			////

			File sampleFile = new File(samplesFile);
	    		Scanner scannerS = new Scanner(sampleFile);
	    		int fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] idsToUse = new String[fil];

			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				idsToUse[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println(Arrays.toString(idsToUse));



			BitSet[] maskG=new BitSet[5];
			for (int c=0; c<5; c++) {
				maskG[c]= new BitSet();
			}

			File fileMask = new File("/srv/biodata/dep_coupland/grp_hancock/andrea/masks_cvi_func/TAIR10_GFF3_genes_transposons.gff_genes.txt");
			Scanner scannerMask = new Scanner(fileMask);
			int chrCVI=0;
			String lineMask=null;
			for (int m=0; m<10; m++) {
			      	lineMask = scannerMask.nextLine();
				if (m%2 == 1) {
				       	for (int i=0; i<lineMask.length(); i++) {
						if (lineMask.charAt(i) == ('1')) {
							maskG[chrCVI].set(i);
						}
			        	}
			        	chrCVI=chrCVI+1;
	        		}	
		        }
																        
			
			
			
			int[] indexesToUse = new int[idsToUse.length];

			
			// Get annotation from SNPeff file
			
			BitSet[] intergenic = new BitSet[5];
			BitSet[] nonSynonimous = new BitSet[5];
			BitSet[] synonimous = new BitSet[5];
			BitSet[] highImpact = new BitSet[5];
			BitSet[] moderateImpact = new BitSet[5];
			BitSet[] lowImpact = new BitSet[5];
			
			for (int c=0; c<5; c++) {
				intergenic[c] = new BitSet();
				nonSynonimous[c] = new BitSet();
				synonimous[c] = new BitSet();
				highImpact[c] = new BitSet();
				moderateImpact[c] = new BitSet();
				lowImpact[c] = new BitSet();
			}
			


			int inter = 0;
			int interM = 0;

			Writer writerD = new FileWriter(filename + "_for0-4degenerate.vcf");
			PrintWriter outD = new PrintWriter(writerD);
			
			Writer writerWG = new FileWriter(filename + "_for0-4degenerate_wg.vcf");
			PrintWriter outWG = new PrintWriter(writerWG);
			
			Writer writerG = new FileWriter(filename + "_for0-4degenerate_genes.vcf");
			PrintWriter outG = new PrintWriter(writerG);
			

			File superVcf = new File(filename);
			Scanner scanner = new Scanner(superVcf);
			
			while ( scanner.hasNextLine() ) {
				String snpAnn = scanner.nextLine();
	        		String[] splitSnpAnn = snpAnn.split("\t");
 			       	// System.out.println(snpAnn);	
				
			// Do something to the header!
	        	if (snpAnn.charAt(0) == '#') {

		        	//System.out.println(snpAnn);
				if (snpAnn.charAt(1) != '#') {
		        		splitIDs = splitSnpAnn;
					System.out.println(snpAnn);
					
					// To use them all
					// indexesToUse = new int[splitIDs.length-9];
		    			// Get samples of interest
					// 
		                        for (int s=0; s<idsToUse.length; s++) {
                		                for (int i=9; i<splitIDs.length; i++) {
	                              		        if (idsToUse[s].equals(splitIDs[i])) {
                                                		indexesToUse[s] = i;
                                 		 	}
	                              		}
	                      		}
                        		System.out.println(Arrays.toString(indexesToUse));
		    			
					for (int i=0; i<9; i++) {
						outD.print(splitIDs[i] + "\t");
						outG.print(splitIDs[i] + "\t");
						outWG.print(splitIDs[i] + "\t");
					}
					outD.print("s1" + "\t" + "s2" + "\t" + "s3" + "\t" + "s4" + "\n");
					outG.print("s1" + "\t" + "s2" + "\t" + "s3" + "\t" + "s4" + "\n");
					outWG.print("s1" + "\t" + "s2" + "\t" + "s3" + "\t" + "s4" + "\n");
				} else {
					outG.println(snpAnn);
					outD.println(snpAnn);
					outWG.println(snpAnn);
				}
	        	}
				
				// Header finished
				
				
				
				
			

				
				
				// Now the real deal
				
	        	if (snpAnn.charAt(0) != '#') {
	        		
				// If it is an indel, what to do?
				//
				boolean indel = false;
				if (splitSnpAnn[3].length() != 1) {
					indel=true;
				}
				for (int i=0; i<splitSnpAnn[4].split(",").length; i++ ) {
					if (splitSnpAnn[4].split(",")[i].length() != 1) {
						indel=true;
					}
				}
					
	        		if ( (splitSnpAnn[0].charAt(0) == 'C') && (splitSnpAnn[0].charAt(1) == 'h') && (splitSnpAnn[0].charAt(2) == 'r') && !indel ) {
	        			
						// Just print where we are
				    	int chr = Character.getNumericValue(splitSnpAnn[0].charAt(3)) - 1;
				        int pos = Integer.parseInt(splitSnpAnn[1]);
				        char refBase =  splitSnpAnn[3].charAt(0);
					
					if (chrMem != chr) {
		        			if ( (chrMem == 0) || (chrMem == 1) || (chrMem == 2) || (chrMem == 3) || (chrMem == 4) ) {
		        				System.out.println("Intergenic Cardinality: " + intergenic[chrMem].cardinality());
		        			}
		        			System.out.println("Chr: " + chr);
							chrMem = chr;
		        		}
				        
				        // Get a char[] of bases that appear in canaries
				        char[] canBases = {'0'};
						Boolean allN = true;
						Boolean mism = false;

						for (int can=0; can<indexesToUse.length; can++) {
							char canBase = 'N';

				       			// Is there any call at all?
							if ( (splitSnpAnn[indexesToUse[can]].split(":")[0].charAt(0) != '.') && (splitSnpAnn[indexesToUse[can]].split(":")[1].charAt(0) != '.') && (splitSnpAnn[indexesToUse[can]].split(":")[2].charAt(0) != '.')) {
							       	// Check coverage and quality
							       	int cov=Integer.parseInt(splitSnpAnn[indexesToUse[can]].split(":")[2]);
							       	int qual=Integer.parseInt(splitSnpAnn[indexesToUse[can]].split(":")[1]);
						       		
							       	// Retain something reasonable
							       	if ( (cov >=3) && (qual >= 25) ) {
							       		if (Integer.parseInt(splitSnpAnn[indexesToUse[can]].split(":")[0]) == 0) {
							       			canBase = splitSnpAnn[3].charAt(0);
							       		} else {
							       			if ( (splitSnpAnn[4].length() == 1) && (Integer.parseInt(splitSnpAnn[indexesToUse[can]].split(":")[0]) == 1) ) {
								       			canBase = splitSnpAnn[4].charAt(0);
							       			} else {
							       				canBase = splitSnpAnn[4].split(",")[Integer.parseInt(splitSnpAnn[indexesToUse[can]].split(":")[0]) - 1].charAt(0);
							       			}
							       		}
								}
					       		}
		        				// System.out.println("char: " + canBase);
								
							if (canBase != 'N') {
								allN = false;
									
				    				// Check if it is in the collection
				    				Boolean thereIs = false;
								for (int chars=0; chars<canBases.length; chars++) {
									if (canBase == canBases[chars]) {
										thereIs = true;
									}
								}
								if (!thereIs) {
									char[] memory = canBases;
									canBases = new char[canBases.length +1];
									for (int c=0; c<memory.length; c++) {
										canBases[c] = memory[c];
									}
									canBases[canBases.length-1] = canBase;
								}
								if (canBase != refBase) {
									mism = true;
								}
							}
						}
						char[] bases = {'A', 'C', 'G', 'T'};
						int cb = 0;
						
						if (mism) {
							// 14  q25 NS=1;DP=40913;AN=1684;AC=11 GT:GQ:DP    0:10:32

							outD.print(splitSnpAnn[0] + "\t" + splitSnpAnn[1] + "\t" + splitSnpAnn[2] + "\t" + splitSnpAnn[3] + "\t");
								       
							cb = 0;
							for (int b=0; b<bases.length; b++) {
								if (bases[b] != refBase) {
									outD.print(bases[b]);
									if (cb < 2) {
										outD.print(",");
									}
									cb = cb +1;
								}
							}	
							outD.print("\t" + "35" + "\t" + "PASS" + "\t" + "NS=4;DP=40;AN=40;AC=10" + "\t" + "GT:GQ:DP" + "\t" + "0:31:20" + "\t" + "1:31:20" + "\t" + "2:31:20" + "\t" + "3:31:20" + "\n");
						}
					

						// Whole genome
						//

						outWG.print(splitSnpAnn[0] + "\t" + splitSnpAnn[1] + "\t" + splitSnpAnn[2] + "\t" + splitSnpAnn[3] + "\t");
		       
						cb = 0;
						for (int b=0; b<bases.length; b++) {
							if (bases[b] != refBase) {
								outWG.print(bases[b]);
								if (cb < 2) {
									outWG.print(",");
								}
								cb = cb +1;
							}
						}	
						outWG.print("\t" + "35" + "\t" + "PASS" + "\t" + "NS=4;DP=40;AN=40;AC=10" + "\t" + "GT:GQ:DP" + "\t" + "0:31:20" + "\t" + "1:31:20" + "\t" + "2:31:20" + "\t" + "3:31:20" + "\n");
						
						// For genes only
						//

						if (maskG[chr].get(pos)) {
							
							outG.print(splitSnpAnn[0] + "\t" + splitSnpAnn[1] + "\t" + splitSnpAnn[2] + "\t" + splitSnpAnn[3] + "\t");
		       
							cb = 0;
							for (int b=0; b<bases.length; b++) {
								if (bases[b] != refBase) {
									outG.print(bases[b]);
									if (cb < 2) {
										outG.print(",");
									}
									cb = cb +1;
								}
							}	
							outG.print("\t" + "35" + "\t" + "PASS" + "\t" + "NS=4;DP=40;AN=40;AC=10" + "\t" + "GT:GQ:DP" + "\t" + "0:31:20" + "\t" + "1:31:20" + "\t" + "2:31:20" + "\t" + "3:31:20" + "\n");
						
						}


						//if (!allN) {
							
							// Eliminate the first base, it was just for convenience
							char[] memory = canBases;
							canBases = new char[canBases.length - 1];
							for (int c=1; c<memory.length; c++) {
								canBases[c - 1] = memory[c];
							}
		        				// System.out.println("char[]: " + Arrays.toString(canBases));
								
						        // Got char[] of canarian bases
					        
						    	// Procede the game
						        String ann = splitSnpAnn[7];
					        
						        String[] annSplitSemicolon = ann.split(";");
			        			for (int semic=0; semic<annSplitSemicolon.length; semic++) {
			        				
			        				if ( (annSplitSemicolon[semic].charAt(0) == 'A' && annSplitSemicolon[semic].charAt(1) == 'N' && annSplitSemicolon[semic].charAt(2) == 'N') ) {
			        					String[] annSplitEffects =  annSplitSemicolon[semic].split("=")[1].split(",");
			        				
						        		for (int eff=0; eff<annSplitEffects.length; eff++) {
						        			String[] annPipeSplit = annSplitEffects[eff].split("\\|");
								        	
								        	// Is this base in canaries?
								        	Boolean inCan = false;
										for (int base=0; base<canBases.length; base++) {
								        		if (annPipeSplit[0].charAt(0) == canBases[base]) {
								        			inCan = true;
								        		}
								        	}
								        	//
							        	
								        	if ( (annPipeSplit[0].length() == 1) && inCan) {
											////
											//		First check classic syn-nonSyn
											////

								        		String[] annAndSplit = annPipeSplit[1].split("&");
									        	for (int a=0; a<annAndSplit.length; a++) {
									        		if (annAndSplit[a].equals("synonymous_variant") || annAndSplit[a].equals("start_retained_variant") || annAndSplit[a].equals("stop_retained_variant")) {
										        		synonimous[chr].set(pos);
										        	} else {
										        		if (annAndSplit[a].equals("nonsynonymous_variant") || annAndSplit[a].equals("protein_altering_variant") || annAndSplit[a].equals("missense_variant") || annAndSplit[a].equals("conservative_missense_variant") || annAndSplit[a].equals("non_conservative_missense_variant") || annAndSplit[a].equals("start_lost") || annAndSplit[a].equals("stop_gained") || annAndSplit[a].equals("stop_lost") || annAndSplit[a].equals("frameshift_variant") || annAndSplit[a].equals("feature_ablation") ) {
										        			nonSynonimous[chr].set(pos);
										        		} else {
										        			if (annAndSplit[a].equals("intergenic_region") || annAndSplit[a].equals("downstream_gene_variant") || annAndSplit[a].equals("5KB_downstream_variant") || annAndSplit[a].equals("500B_downstream_variant") || annAndSplit[a].equals("downstream_transcript_variant") || annAndSplit[a].equals("upstream_gene_variant") || annAndSplit[a].equals("5KB_upstream_variant") || annAndSplit[a].equals("2KB_upstream_variant") || annAndSplit[a].equals("upstream_transcript_variant") || annAndSplit[a].equals("intron_variant")) {
										        				intergenic[chr].set(pos);

															inter = inter + 1;
															if (splitSnpAnn[4].charAt(0) == '.') {
																interM = interM + 1;
															}
															// System.out.println(inter + "\t" + interM + "\t" + splitSnpAnn[4].charAt(0));
										        			} else {
										        				String[] splitCheck = annAndSplit[a].split("_");
										        				if (annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("5_prime_UTR_variant") || annAndSplit[a].equals("3_prime_UTR_variant") || annAndSplit[a].equals("5_prime_UTR_premature_start_codon_gain_variant") || splitCheck[0].equals("splice") || annAndSplit[a].equals("initiator_codon_variant")) { // || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || ) {
										        				} else{
										        				}
										        			}
										        		}
										        	}
									        	}

											////
											//		Now look at high/moderate/low impact
											////

											for (int a=0; a<annPipeSplit.length; a++) {
												if (annPipeSplit[a].equals("HIGH")) {
													highImpact[chr].set(pos);
												}
												if (annPipeSplit[a].equals("MODIFIER")) {
													moderateImpact[chr].set(pos);
												}
												if (annPipeSplit[a].equals("LOW")) {
													lowImpact[chr].set(pos);
												}
											}
							       		 	}
						        		}
						        	}
				        		}
						//}
		        		}
		        	}
			}
			int syn = 0;
			int nonSyn = 0;
			int interg = 0;
			int high = 0;
			int mod = 0;
			int low = 0;
			for (int c=0;c<5; c++) {
				syn = syn + synonimous[c].cardinality();
				nonSyn = nonSyn + nonSynonimous[c].cardinality();
				interg = interg + intergenic[c].cardinality();
				high = high + highImpact[c].cardinality();
				mod = mod + moderateImpact[c].cardinality();
				low = low + lowImpact[c].cardinality();
			}
			System.out.println("s, n, i, h, m, l: " + syn + " " + nonSyn + " " + interg + " " + high + " " + mod + " " + low);
		
			
			
			
			
			
			
			
			
			
			
			
			
			// Write out the 3 masks
			
			
			
			// Intergenic
			Writer writer = new FileWriter(wereWeResult + "intergenicMask.txt");
			PrintWriter out = new PrintWriter(writer);
			for (int c=0; c<5; c++) {
				out.print(">Chr" + (c+1));
				out.print("\n");
				for (int bit =0; bit< intergenic[c].length(); bit++) {
					if (intergenic[c].get(bit)) {
						out.print("1");
					}
					if (!intergenic[c].get(bit)) {
						out.print("0");
					}
				}
				out.print("\n");
			}
			out.close();
			
			// Sysnonimous
			Writer writerS = new FileWriter(wereWeResult + "synonimousMask.txt");
			PrintWriter outS = new PrintWriter(writerS);
			for (int c=0; c<5; c++) {
				outS.print(">Chr" + (c+1));
				outS.print("\n");
				for (int bit =0; bit< synonimous[c].length(); bit++) {
					if (synonimous[c].get(bit)) {
						outS.print("1");
					}
					if (!synonimous[c].get(bit)) {
						outS.print("0");
					}
				}
				outS.print("\n");
			}
			outS.close();
			
			
			// Non Sysnonimous
			Writer writerN = new FileWriter(wereWeResult + "nonSynonimousMask.txt");
			PrintWriter outN = new PrintWriter(writerN);
			for (int c=0; c<5; c++) {
				outN.print(">Chr" + (c+1));
				outN.print("\n");
				for (int bit =0; bit< nonSynonimous[c].length(); bit++) {
					if (nonSynonimous[c].get(bit)) {
						outN.print("1");
					}
					if (!nonSynonimous[c].get(bit)) {
						outN.print("0");
					}
				}
				outN.print("\n");
			}
			outN.close();



			// High impact
			writerN = new FileWriter(wereWeResult + "highImpactMask.txt");
			outN = new PrintWriter(writerN);
			for (int c=0; c<5; c++) {
				outN.print(">Chr" + (c+1));
				outN.print("\n");
				for (int bit =0; bit< highImpact[c].length(); bit++) {
					if (highImpact[c].get(bit)) {
						outN.print("1");
					}
					if (!highImpact[c].get(bit)) {
						outN.print("0");
					}
				}
				outN.print("\n");
			}
			outN.close();
	

			// Moderate impact
			writerN = new FileWriter(wereWeResult + "modifierMask.txt");
			outN = new PrintWriter(writerN);
			for (int c=0; c<5; c++) {
				outN.print(">Chr" + (c+1));
				outN.print("\n");
				for (int bit =0; bit< moderateImpact[c].length(); bit++) {
					if (moderateImpact[c].get(bit)) {
						outN.print("1");
					}
					if (!moderateImpact[c].get(bit)) {
						outN.print("0");
					}
				}
				outN.print("\n");
			}
			outN.close();
	

		
		
			// Low impact
			writerN = new FileWriter(wereWeResult + "lowImpactMask.txt");
			outN = new PrintWriter(writerN);
			for (int c=0; c<5; c++) {
				outN.print(">Chr" + (c+1));
				outN.print("\n");
				for (int bit =0; bit< lowImpact[c].length(); bit++) {
					if (lowImpact[c].get(bit)) {
						outN.print("1");
					}
					if (!lowImpact[c].get(bit)) {
						outN.print("0");
					}
				}
				outN.print("\n");
			}
			outN.close();

			outG.close();
			outD.close();
			outWG.close();



		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		SnpEff_masksFromSuperVcf snpEff_masksFromSuperVcf = new SnpEff_masksFromSuperVcf();
		snpEff_masksFromSuperVcf.setSnpFile(args[0], args[1], args[2]);
	}
}
