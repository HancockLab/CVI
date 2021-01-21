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

public class SnpEff_04foldDegenerate_masks {
	
	private SnpEff_04foldDegenerate_masks() {}

	public void setSnpFile(String filename, String wereWeResult){
		
		
		try {
			char[] bases = {'A', 'C', 'G', 'T'};
			// Get annotation from SNPeff file
			
			int numH = 0;
			int numM = 0;
			int numL = 0;
			int numS = 0;
			int numN = 0;

			BitSet[] fourFold = new BitSet[5];
			BitSet[] zeroFold = new BitSet[5];

			BitSet[] synMask = new BitSet[5];
			BitSet[] nonSynMask = new BitSet[5];
			BitSet[] highMask = new BitSet[5];
			BitSet[] modMask = new BitSet[5];
			BitSet[] lowMask = new BitSet[5];
			
			for (int c=0; c<5; c++) {
				fourFold[c] = new BitSet();
				zeroFold[c] = new BitSet();
				synMask[c] = new BitSet();
				nonSynMask[c] = new BitSet();
				highMask[c] = new BitSet();
				modMask[c] = new BitSet();
				lowMask[c] = new BitSet();
			}
			
			Writer writerSyn = new FileWriter(wereWeResult + "syn_breakDown.txt");
			PrintWriter outSyn = new PrintWriter(writerSyn);
			
			Writer writerNonS = new FileWriter(wereWeResult + "nonSyn_breakDown.txt");
			PrintWriter outNonS = new PrintWriter(writerNonS);
			
			Writer writerHigh = new FileWriter(wereWeResult + "high_breakDown.txt");
			PrintWriter outHigh = new PrintWriter(writerHigh);
			
			Writer writerMod = new FileWriter(wereWeResult + "mod_breakDown.txt");
			PrintWriter outMod = new PrintWriter(writerMod);
			
			Writer writerLow = new FileWriter(wereWeResult + "low_breakDown.txt");
			PrintWriter outLow = new PrintWriter(writerLow);
			
			outSyn.print("chr" + "\t" + "pos" + "\t" + "A" + "\t" + "C" + "\t" + "G" + "\t" + "T" + "\n");
			outNonS.print("chr" + "\t" + "pos" + "\t" + "A" + "\t" + "C" + "\t" + "G" + "\t" + "T" + "\n");
			outHigh.print("chr" + "\t" + "pos" + "\t" + "A" + "\t" + "C" + "\t" + "G" + "\t" + "T" + "\n");
			outMod.print("chr" + "\t" + "pos" + "\t" + "A" + "\t" + "C" + "\t" + "G" + "\t" + "T" + "\n");
			outLow.print("chr" + "\t" + "pos" + "\t" + "A" + "\t" + "C" + "\t" + "G" + "\t" + "T" + "\n");


			int chrMem = 0;
			File superVcf = new File(filename);
			Scanner scanner = new Scanner(superVcf);
			
			while ( scanner.hasNextLine() ) {
				String snpAnn = scanner.nextLine();
        			String[] splitSnpAnn = snpAnn.split("\t");
        		
				// Do something to the header!
	        		if (snpAnn.charAt(0) == '#') {
		        		if (snpAnn.charAt(1) != '#') {
		        			
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
				        if (chrMem != chr) {
		        			System.out.println("Chr: " + chr);
							chrMem = chr;
		        		}
				        char[] alt = {splitSnpAnn[4].split(",")[0].charAt(0), splitSnpAnn[4].split(",")[1].charAt(0), splitSnpAnn[4].split(",")[2].charAt(0)};
				        Boolean[] syn = {false, false, false};
				        Boolean[] nonSyn = {false, false, false};
				        
				        Boolean[] high = {false, false, false};
				        Boolean[] modify = {false, false, false};
				        Boolean[] low = {false, false, false};
						
				    	// Procede the game
				        String ann = splitSnpAnn[7];
				        // System.out.println(ann);
				        
				        
				        String[] annSplitSemicolon = ann.split(";");
		        		for (int semic=0; semic<annSplitSemicolon.length; semic++) {
		        			
		        			if ( (annSplitSemicolon[semic].charAt(0) == 'A' && annSplitSemicolon[semic].charAt(1) == 'N' && annSplitSemicolon[semic].charAt(2) == 'N') ) {			        			
			        			// System.out.println(annSplitSemicolon[semic]);
			        			String[] annSplitEffects =  annSplitSemicolon[semic].split("=")[1].split(",");
		        				
				        		for (int eff=0; eff<annSplitEffects.length; eff++) {
				        			//System.out.println(annSplitEffects[eff]);
				        			String[] annPipeSplit = annSplitEffects[eff].split("\\|");
						        	
				        			// Which alt base are we talking about?
				        			
				        			for (int al=0; al<alt.length; al++) {
				        				if (alt[al] == annPipeSplit[0].charAt(0)) {
							        		String[] annAndSplit = annPipeSplit[1].split("&");
							        		
							        		// Syn / non-syn
							        		//
							        		for (int a=0; a<annAndSplit.length; a++) {
								        		if (annAndSplit[a].equals("synonymous_variant") || annAndSplit[a].equals("start_retained_variant") || annAndSplit[a].equals("stop_retained_variant")) {
								        			syn[al] = true;
									        	}
								        		if (annAndSplit[a].equals("nonsynonymous_variant") || annAndSplit[a].equals("protein_altering_variant") || annAndSplit[a].equals("missense_variant") || annAndSplit[a].equals("conservative_missense_variant") || annAndSplit[a].equals("non_conservative_missense_variant") || annAndSplit[a].equals("start_lost") || annAndSplit[a].equals("stop_gained") || annAndSplit[a].equals("stop_lost") || annAndSplit[a].equals("frameshift_variant") || annAndSplit[a].equals("feature_ablation") ) {
								        			nonSyn[al] = true;
								        		}
								        	}
							        		// High / low impact
							        		//
							        		for (int a=0; a<annPipeSplit.length; a++) {
							        			if (annPipeSplit[a].equals("HIGH")) {
							        				high[al] = true;
							        			}
							        			if (annPipeSplit[a].equals("MODIFIER")) {
							        				modify[al] = true;
							        			}
							        			if (annPipeSplit[a].equals("LOW")) {
							        				low[al] = true;
							        			}
							        		}
				        				}
				        			}
				        		}
				        	}
		        		}
		        		// Synonymous
		        		//
					if ( (syn[0] && !nonSyn[0]) || (syn[1] && !nonSyn[1]) || (syn[2] && !nonSyn[2]) ) {
						synMask[chr].set(pos);
						//
						// Write out some details
						outSyn.print(chr + "\t" + pos);
						for (int b=0; b<bases.length; b++) {
							Boolean ref = true;
							for (int al=0; al<alt.length; al++) {
								if (bases[b] == alt[al]) {
									ref = false;
									if (syn[al]) {
										outSyn.print("\t" + "1");
									} else {
										outSyn.print("\t" + "0");
									}
								}
							}
							if (ref) {
								outSyn.print("\t" + "-");
							}
						}
						outSyn.print("\n");
					}
					// Non synonymous
					//
					if ( (nonSyn[0] && !syn[0]) || (nonSyn[1] && !syn[1]) || (nonSyn[2] && !syn[2]) ) {
						nonSynMask[chr].set(pos);
						//
						// Write out some details
						outNonS.print(chr + "\t" + pos);
						for (int b=0; b<bases.length; b++) {
							Boolean ref = true;
							for (int al=0; al<alt.length; al++) {
								if (bases[b] == alt[al]) {
									ref = false;
									if (nonSyn[al]) {
										outNonS.print("\t" + "1");
									} else {
										outNonS.print("\t" + "0");
									}
								}
							}
							if (ref) {
								outNonS.print("\t" + "-");
							}
						}
						outNonS.print("\n");
					}

					//
					// Add the mixed shit: alleles that are both syn and nonSyn, maybe due to splice variants
					// ... Or actually, dont do shit
					//

	        			// 0- 4- fold
					//
					if ( syn[0] && syn[1] && syn[2] && !nonSyn[0] && !nonSyn[1] && !nonSyn[2] ) {
	        				fourFold[chr].set(pos);
		        		}
		        		if ( nonSyn[0] && nonSyn[1] && nonSyn[2] && !syn[0] && !syn[1] && !syn[2]) {
		        			zeroFold[chr].set(pos);
			        	}
					// mixed shit
					// out

					
	        			// High / low impact
		        		//
					// Masks
					if ( (high[0] && !modify[0] && !low[0]) || (high[1] && !modify[1] && !low[1]) || (high[2] && !modify[2] && !low[2]) ) {
						highMask[chr].set(pos);
						//
						// Write out some details
						outHigh.print(chr + "\t" + pos);
						for (int b=0; b<bases.length; b++) {
							Boolean ref = true;
							for (int al=0; al<alt.length; al++) {
								if (bases[b] == alt[al]) {
									ref = false;
									if (high[al]) {
										outHigh.print("\t" + "1");
									} else {
										outHigh.print("\t" + "0");
									}
								}
							}
							if (ref) {
								outHigh.print("\t" + "-");
							}
						}
						outHigh.print("\n");

					}
					if ( (modify[0] && !high[0] && !low[0]) || (modify[1] && !high[1] && !low[1]) || (modify[2] && !high[2] && !low[2]) ) {
						modMask[chr].set(pos);
						//
						// Write out some details
						outMod.print(chr + "\t" + pos);
						for (int b=0; b<bases.length; b++) {
							Boolean ref = true;
							for (int al=0; al<alt.length; al++) {
								if (bases[b] == alt[al]) {
									ref = false;
									if (modify[al]) {
										outMod.print("\t" + "1");
									} else {
										outMod.print("\t" + "0");
									}
								}
							}
							if (ref) {
								outMod.print("\t" + "-");
							}
						}
						outMod.print("\n");

					}
					if ( (low[0] && !high[0] && !modify[0]) || (low[1] && !high[1] && !modify[1]) || (low[2] && !high[2] && !modify[2]) ) {
						lowMask[chr].set(pos);
						//
						// Write out some details
						outLow.print(chr + "\t" + pos);
						for (int b=0; b<bases.length; b++) {
							Boolean ref = true;
							for (int al=0; al<alt.length; al++) {
								if (bases[b] == alt[al]) {
									ref = false;
									if (low[al]) {
										outLow.print("\t" + "1");
									} else {
										outLow.print("\t" + "0");
									}
								}
							}
							if (ref) {
								outLow.print("\t" + "-");
							}
						}
						outLow.print("\n");

					}

					//
					// counts
	        			for (int al=0; al<alt.length; al++) {
	        				if (high[al]) {
	        					numH = numH + 1;
	        				}
	        				if (modify[al]) {
	        					numM = numM + 1;
	        				}
	        				if (low[al]) {
	        					numL = numL + 1;
	        				}
						if (syn[al]) {
							numS = numS + 1;
						}
						if (nonSyn[al]) {
							numN = numN + 1;
						}
	        			}
	        		}
	        	}
			}
			int synS = 0;
			int nonSynS = 0;
			for (int c=0;c<5; c++) {
				synS = synS + fourFold[c].cardinality();
				nonSynS = nonSynS + zeroFold[c].cardinality();
			}
			System.out.println("4, 0, h, m, l, s, n: " + synS + " " + nonSynS + " " + numH + " " + numM + " " + numL + " " + numS + " " + numN);
			// s, n, i: 4880332 21425414
			
			
			
			
			
			
			
			
			
			
			
			
			// Write out the masks
			
			// String wereWeResult = "/Volumes/CVI/final/cvi/snpeff/";
			
			////
			//	4-fold mask
			////
			Writer writerN = new FileWriter(wereWeResult + "fourFold_mask.txt");
			PrintWriter outN = new PrintWriter(writerN);
			for (int c=0; c<5; c++) {
				outN.print(">Chr" + (c+1));
				outN.print("\n");
				for (int bit =0; bit< fourFold[c].length(); bit++) {
					if (fourFold[c].get(bit)) {
						outN.print("1");
					}
					if (!fourFold[c].get(bit)) {
						outN.print("0");
					}
				}
				outN.print("\n");
			}
			outN.close();
			
			////
			//	0-fold mask
			////
			Writer writerS = new FileWriter(wereWeResult + "zeroFold_mask.txt");
			PrintWriter outS = new PrintWriter(writerS);
			for (int c=0; c<5; c++) {
				outS.print(">Chr" + (c+1));
				outS.print("\n");
				for (int bit =0; bit< zeroFold[c].length(); bit++) {
					if (zeroFold[c].get(bit)) {
						outS.print("1");
					}
					if (!zeroFold[c].get(bit)) {
						outS.print("0");
					}
				}
				outS.print("\n");
			}
			outS.close();
			

			////
			//	synMask
			////
			Writer writerSm = new FileWriter(wereWeResult + "syn_mask.txt");
			PrintWriter outSm = new PrintWriter(writerSm);
			for (int c=0; c<5; c++) {
				outSm.print(">Chr" + (c+1));
				outSm.print("\n");
				for (int bit =0; bit< synMask[c].length(); bit++) {
					if (synMask[c].get(bit)) {
						outSm.print("1");
					}
					if (!synMask[c].get(bit)) {
						outSm.print("0");
					}
				}
				outSm.print("\n");
			}
			outSm.close();
			
			////
			//	nonSynMask
			////
			Writer writerNm = new FileWriter(wereWeResult + "nonSyn_mask.txt");
			PrintWriter outNm = new PrintWriter(writerNm);
			for (int c=0; c<5; c++) {
				outNm.print(">Chr" + (c+1));
				outNm.print("\n");
				for (int bit =0; bit< nonSynMask[c].length(); bit++) {
					if (nonSynMask[c].get(bit)) {
						outNm.print("1");
					}
					if (!nonSynMask[c].get(bit)) {
						outNm.print("0");
					}
				}
				outNm.print("\n");
			}
			outNm.close();
			

			// BitSet[] highMask = new BitSet[5];
			// BitSet[] modMask = new BitSet[5];
			// BitSet[] lowMask = new BitSet[5];
			

			Writer writerHm = new FileWriter(wereWeResult + "high_mask.txt");
			PrintWriter outHm = new PrintWriter(writerHm);
			for (int c=0; c<5; c++) {
				outHm.print(">Chr" + (c+1));
				outHm.print("\n");
				for (int bit =0; bit< highMask[c].length(); bit++) {
					if (highMask[c].get(bit)) {
						outHm.print("1");
					}
					if (!highMask[c].get(bit)) {
						outHm.print("0");
					}
				}
				outHm.print("\n");
			}
			outHm.close();
			

			Writer writerMm = new FileWriter(wereWeResult + "modifier_mask.txt");
			PrintWriter outMm = new PrintWriter(writerMm);
			for (int c=0; c<5; c++) {
				outMm.print(">Chr" + (c+1));
				outMm.print("\n");
				for (int bit =0; bit< modMask[c].length(); bit++) {
					if (modMask[c].get(bit)) {
						outMm.print("1");
					}
					if (!modMask[c].get(bit)) {
						outMm.print("0");
					}
				}
				outMm.print("\n");
			}
			outMm.close();
			
			
			Writer writerLm = new FileWriter(wereWeResult + "low_mask.txt");
			PrintWriter outLm = new PrintWriter(writerLm);
			for (int c=0; c<5; c++) {
				outLm.print(">Chr" + (c+1));
				outLm.print("\n");
				for (int bit =0; bit< lowMask[c].length(); bit++) {
					if (lowMask[c].get(bit)) {
						outLm.print("1");
					}
					if (!lowMask[c].get(bit)) {
						outLm.print("0");
					}
				}
				outLm.print("\n");
			}
			outLm.close();
			






			Writer writerH = new FileWriter(wereWeResult + "wg_highLow.txt");
			PrintWriter outH = new PrintWriter(writerH);
			outH.print("High impact:" + "\t" + numH + "\n");
			outH.print("modify impact:" + "\t" + numM + "\n");
			outH.print("Low impact:" + "\t" + numL + "\n");
			outH.print("Syn:" + "\t" + numS + "\n");
			outH.print("Non syn:" + "\t" + numN + "\n");
			outH.close();
			
			outSyn.close();
			outNonS.close();
			outHigh.close();
			outMod.close();
			outLow.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		SnpEff_04foldDegenerate_masks snpEff_04foldDegenerate_masks = new SnpEff_04foldDegenerate_masks();
		snpEff_04foldDegenerate_masks.setSnpFile(args[0], args[1]);
	}
}
