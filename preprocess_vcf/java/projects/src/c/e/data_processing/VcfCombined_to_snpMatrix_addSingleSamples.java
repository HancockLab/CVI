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

public class VcfCombined_to_snpMatrix_addSingleSamples {
	
	public VcfCombined_to_snpMatrix_addSingleSamples() {}
	
	public void setFileToConvert(String matrixName, String vcfToAdd){
		
		try {


 			// Open matrix
			//
			System.out.println("Now the big matrix...");
                        File matrixFile = new File(matrixName);
                        Scanner scannerMatrix = new Scanner(matrixFile);
                        scannerMatrix.nextLine();
			
                        // Record in a bitSet what positions are already in the big matrix
                        BitSet[] inTheMatrix = new BitSet[5];
                        for (int c=0; c<5; c++) {
                                inTheMatrix[c] = new BitSet();
                        }

                        while ( scannerMatrix.hasNextLine() ) {
                                String snp1 = scannerMatrix.nextLine();
                                String[] splitSnp = snp1.split("\t");

                                int chr = Integer.parseInt(splitSnp[0]);
                                int pos = Integer.parseInt(splitSnp[1]);

                                inTheMatrix[chr - 1].set(pos);
                        }












			// Get a bitSet of positions called in the new vcf
			// And a bitSet of mismatches to the reference
			// And the new positions, output to a temporary file
			
			BitSet[] newCalls = new BitSet[5];
                        BitSet[] newMism = new BitSet[5];
			
			for (int c=0; c<5; c++) {
				newCalls[c] = new BitSet();
				newMism[c] = new BitSet();
			}
			
			Writer writerN = new FileWriter(vcfToAdd +  "_newPos.txt");
                        PrintWriter outN = new PrintWriter(writerN);
			
			File vcfFile = new File(vcfToAdd);
                        Scanner scannerVcf = new Scanner(vcfFile);
			int name = 0;
			
                        while ( scannerVcf.hasNextLine() ) {
                                String snp1 = scannerVcf.nextLine();
                                String[] splitSnp = snp1.split("\t");
				
                                // Mind the header now
                                if (snp1.charAt(0) == '#') {
                                	if (snp1.charAt(1) != '#') {
						name = Integer.parseInt(splitSnp[9]);
						System.out.println("name: " + name);
					}
				} else {
					if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') && (splitSnp[3].length() == 1) && (splitSnp[4].length() == 1) && (splitSnp[9].split(":")[0].charAt(0) != '.') && (splitSnp[9].split(":")[1].charAt(0) != '.') && (splitSnp[9].split(":")[2].charAt(0) != '.') ) {
						
						// the genome now
						int cov=Integer.parseInt(splitSnp[9].split(":")[2]);
                                	        int qual=Integer.parseInt(splitSnp[9].split(":")[1]);
						if ( (cov >=3) && (qual >= 25) ) {
							newCalls[Character.getNumericValue(splitSnp[0].charAt(3)) - 1].set(Integer.parseInt(splitSnp[1]));
							
							// Is it a mismatch?
							if (splitSnp[4].charAt(0) != '.') {
								newMism[Character.getNumericValue(splitSnp[0].charAt(3)) - 1].set(Integer.parseInt(splitSnp[1]));
							}

							// If not in the big matrix, output in temporary file
							if ( !inTheMatrix[Character.getNumericValue(splitSnp[0].charAt(3)) - 1].get(Integer.parseInt(splitSnp[1])) ) {
                                                        	char alt = '.';
								if (splitSnp[4].charAt(0) == '.') {
                                                                	alt = splitSnp[3].charAt(0);
                                                                } else {
                                                                	alt = splitSnp[4].charAt(0);
                                                                }
								outN.print((Character.getNumericValue(splitSnp[0].charAt(3)) - 1) + "\t" + splitSnp[1] + "\t" + splitSnp[3].charAt(0) + "\t" + alt + "\n");
							}
						}
					}
				}
			}
			outN.close();

			// Count mismatches, record bases
			int mism = 0;
                        for (int c=0; c<5; c++) {
				mism = mism + newMism[c].cardinality();
			}
			System.out.println("mism: " + mism);
			
			int[] mismChr = new int[mism];
                        int[] mismPos = new int[mism];
			char[] mismBase = new char[mism];
			
			mism = 0;
			
			scannerVcf = new Scanner(vcfFile);

                        while ( scannerVcf.hasNextLine() ) {
                                String snp1 = scannerVcf.nextLine();
                                String[] splitSnp = snp1.split("\t");
				
				// Mind the header now
				if (snp1.charAt(0) == '#') {

                                } else {
					if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') && (splitSnp[3].length() == 1) && (splitSnp[4].length() == 1) && (splitSnp[9].split(":")[0].charAt(0) != '.') && (splitSnp[9].split(":")[1].charAt(0) != '.') && (splitSnp[9].split(":")[2].charAt(0) != '.') ) {
						
						// the genome now
						int cov=Integer.parseInt(splitSnp[9].split(":")[2]);
                                                int qual=Integer.parseInt(splitSnp[9].split(":")[1]);
                                                if ( (cov >=3) && (qual >= 25) ) {
                                                        // Is it a mismatch?
							if (splitSnp[4].charAt(0) != '.') {
                                                         if ((splitSnp[4].charAt(0) != 'A') && (splitSnp[4].charAt(0) != 'T') && (splitSnp[4].charAt(0) != 'C') && (splitSnp[4].charAt(0) != 'G')) {
                                                                	System.out.println("Problem at: " + splitSnp[4]);
                                                                }
                                                                mismChr[mism] = Character.getNumericValue(splitSnp[0].charAt(3)) - 1;
								mismPos[mism] = Integer.parseInt(splitSnp[1]);
                                                       		mismBase[mism] = splitSnp[4].charAt(0);
								mism = mism + 1;
							}
                                                }
                                        }
                                }
                        }
			System.out.println("Recorded mism bases!");







			
			// Get a BitSet with those positions that are in the new vcf but not in the matrix
			// Problematic ones
			BitSet[] problem = new BitSet[5];
			Boolean[] noProblem = new Boolean[5];
                        Boolean problemAtAll = false;
			int totalProb = 0;
			
			for (int c=0; c<5; c++) {
				noProblem[c] = false;
				problem[c] = new BitSet();
				problem[c].or(newCalls[c]);
                                problem[c].andNot(inTheMatrix[c]);
				if ( problem[c].cardinality() == 0 ) {
					noProblem[c] = true;
				} else {
					problemAtAll = true;
					totalProb = totalProb + problem[c].cardinality();
				}
			}
                        int[] probChr = new int[totalProb];
                        int[] probPos = new int[totalProb];
                        // char[] probRef = new char[totalProb];
                        // char[] probAlt = new char[totalProb];

			System.out.println("totalProb: " + totalProb);	
			// get problematic pos and chr
			for (int c=0; c<5; c++) {
				int prob = 0;
                        	for (int p=0; p<problem[c].length(); p++) {
                                        if (problem[c].get(p)) {
                                                probChr[prob] = c;
                                                probPos[prob] = p;
                                        	prob = prob + 1;
                                	}
                        	}
			}
			System.out.println("totalProb, also in detail ");
			
			// if necessary, open the vcf again and record ref and alt bases at new positions
			/*
 * 			System.out.println("Solving problems now...");
			if (problemAtAll) {
				int prob = 0;
				scannerVcf = new Scanner(vcfFile);
		                while ( scannerVcf.hasNextLine() ) {
                			String snp1 = scannerVcf.nextLine();
                                	String[] splitSnp = snp1.split("\t");
                                	// Mind the header now
                             		if (snp1.charAt(0) == '#') {
						
        		                } else {
	                                        if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') && (splitSnp[3].length() == 1) && (splitSnp[4].length() == 1) && (splitSnp[9].split(":")[0].charAt(0) != '.') && (splitSnp[9].split(":")[1].charAt(0) != '.') && (splitSnp[9].split(":")[2].charAt(0) != '.') ) {
							
							// the genome now
							int cov=Integer.parseInt(splitSnp[9].split(":")[2]);
                        		                int qual=Integer.parseInt(splitSnp[9].split(":")[1]);
                                        	        if ( (cov >=3) && (qual >= 25) ) {
                                                       		// Is it new pos?
                                                       		for (int n=0; n<probChr.length;n++) {
									if ( (Character.getNumericValue(splitSnp[0].charAt(3)) == probChr[n]) && (Integer.parseInt(splitSnp[1]) == probPos[n]) ) {
										probRef[prob] = splitSnp[3].charAt(0);
										if (splitSnp[4].charAt(0) == '.') {
											probAlt[prob] = splitSnp[3].charAt(0);
										} else {
											probAlt[prob] = splitSnp[4].charAt(0);
											if ( (probAlt[prob] != 'A') && (probAlt[prob] != 'T') && (probAlt[prob] != 'C') && (probAlt[prob] != 'G') ) {
												System.out.println("Shit at: " + snp1);
											}
										}
										prob = prob + 1;
									}
								}
                                             		}
                                	        }
                		        }
		                }
			}	

			// Check
			for (int n=0; n<probChr.length;n++) {
				if ( ( (probAlt[n] != 'A') && (probAlt[n] != 'T') && (probAlt[n] != 'C') && (probAlt[n] != 'G') ) || ( (probRef[n] != 'A') && (probRef[n] != 'T') && (probRef[n] != 'C') &&  (probRef[n] != 'G') ) ) {
					System.out.println("Problem at problematic pos: " + probChr[n] + " " + probPos[n]);
				}
			}

			*/




















			// Writer writerN = new FileWriter(vcfToAdd +  "_newPos.txt");





                        // Open matrix again
                        scannerMatrix = new Scanner(matrixFile);

			System.out.println("Write it out now!");
			
                        // Open writing file
			Writer writer = new FileWriter(matrixName +  "_" + name + "_3.txt");
                        PrintWriter out = new PrintWriter(writer);
                        
			String head = scannerMatrix.nextLine();
                        out.print(head + "\t" + name + "\n");
			int prob = 0;
			
			while ( scannerMatrix.hasNextLine() ) {
                                String snp1 = scannerMatrix.nextLine();
                                String[] splitSnp = snp1.split("\t");
				
                                int chr = Integer.parseInt(splitSnp[0]) - 1;
                                int pos = Integer.parseInt(splitSnp[1]);
				
				// Are there new positions on this chromosome?
                        	if (noProblem[chr]) {
					out.print(snp1 + "\t");
					// Is there any data here?
					if (newCalls[chr].get(pos)) {
						if (!newMism[chr].get(pos)) {
							// It is a match to the reference
							out.print(splitSnp[2] + "\n");
						} else {
							// It is a mismatch
							Boolean seen = false;
							for (int m=0; m<mismChr.length;m++) {
								if ( (chr == mismChr[m]) && (pos == mismPos[m]) ) {
									if (!seen) {
										out.print(mismBase[m]+ "\n");
										seen = true;
									} else {
										System.out.println("Problem of double new positions at: " + chr + " " + pos);
									}
								}
							}
						}
					} else {
						// Missing data
						out.print('N' + "\n");
					}
				} else {
					// We got a problem: new positions
					if ( ( (probChr[prob] == chr) && (probPos[prob] > pos) ) || (probChr[prob] > chr) ) {
						out.print(snp1 + "\t");
                                        	// Is there any data here?
	                                        if (newCalls[chr].get(pos)) {
                                             		if (!newMism[chr].get(pos)) {
                                                        	// It is a match to the reference
								out.print(splitSnp[2] + "\n");
                                                	} else {
                                                	        // It is a mismatch
                                                	        Boolean seen = false;
                                         	        	for (int m=0; m<mismChr.length;m++) {
                                                                	if ( (chr == mismChr[m]) && (pos == mismPos[m]) ) {
                                                                        	if (!seen) {
											out.print(mismBase[m]+ "\n");
                                                                			seen = true;
                	                                                        } else {
        	                                                                        System.out.println("Problem of double new positions at: " + chr + " " + pos);
	                                                                        }
									}
                                                        	}
                                                	}
                                        	} else {
                                                	// Missing data
                                                	out.print('N' + "\n");
                                        	}
					} else {
						while ((probChr[prob] == chr) && (probPos[prob] < pos)) {
							// We just passed the new pos
							// First output the new position
							// And if there is more than one in a row... While statement
							//
							// Open the temporary file with new positions
							File probFile = new File(vcfToAdd +  "_newPos.txt");
							Scanner scannerProb = new Scanner(probFile);
							
							Boolean found = false;
							while ( scannerMatrix.hasNextLine() ) {
				                                String vcf = scannerMatrix.nextLine();
                                				String[] splitVcf = vcf.split("\t");
								if ( (Integer.parseInt(splitVcf[0]) == probChr[prob]) && (Integer.parseInt(splitVcf[1]) == probPos[prob]) ) {
									if (!found) {
										out.print(probChr[prob] + "\t" + probPos[prob] + "\t" + splitVcf[2].charAt(0) + "\t");
        		                                        	        for (int s=3; s<splitSnp.length; s++) {
                        		                               	        	out.print('N' + "\t");
                                        		               		}
                                                        			out.print(splitVcf[3].charAt(0) + "\n");
                                                     		 		System.out.print(prob + "\t");
                                                       	 			found = true;
										prob = prob + 1;
									} else {
										System.out.println("Problem, double problematic pos at: " + probChr[prob - 1] + " " + probPos[prob - 1]);
									}
								}
							}
						}
						
						// Then output what we are reading now
						
						out.print(snp1 + "\t");
	                                        // Is there any data here?
        	                                if (newCalls[chr].get(pos)) {
                	                        	if (!newMism[chr].get(pos)) {
                        	                        	// It is a match to the reference
                                                                out.print(splitSnp[2] + "\n");
                         	 	                } else {
                                        	        	// It is a mismatch
                                                                Boolean seen = false;
                                                	        for (int m=0; m<mismChr.length;m++) {
                                                        		if ( (chr == mismChr[m]) && (pos == mismPos[m]) ) {
                                                                		if (!seen) {
                                                                        		out.print(mismBase[m]+ "\n");
                                                                                	seen = true;
                                                                         	} else {
                                                                        		System.out.println("Problem of double new positions at: " + chr + " " + pos);
										}
                                                                        }
                                                                }
                                                        }
                                               	} else {
                                                       	// Missing data
                                                        out.print('N' + "\n");
						}
					}
				}
			}
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		VcfCombined_to_snpMatrix_addSingleSamples vcfCombined_to_snpMatrix_addSingleSamples = new VcfCombined_to_snpMatrix_addSingleSamples();
		vcfCombined_to_snpMatrix_addSingleSamples.setFileToConvert(args[0], args[1]);
	}
}
