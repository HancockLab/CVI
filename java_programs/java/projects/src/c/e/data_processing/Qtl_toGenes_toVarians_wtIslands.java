package c.e.data_processing;

import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
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

public class Qtl_toGenes_toVarians_wtIslands {
	
	public Qtl_toGenes_toVarians_wtIslands() {}
	
	public void setFileToSubSet(String matrixName, String sampleFile, String sampleFile2, String maskName){
		
		try {
			System.out.println("Start the game!");	
			
			////
			//      Load the specific mask
			////
			
			
			System.out.println("load the mask!");

			BitSet[] mask = new BitSet[5];
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			File fileMask = new File(maskName);
			Scanner scannerMask = new Scanner(fileMask);
			int chrCVI=0;
			String lineMask=null;
			for (int m=0; m<10; m++) {
				lineMask = scannerMask.nextLine();
				if (m%2 == 1) {
					for (int i=0; i<lineMask.length(); i++) {
						if (lineMask.charAt(i) == ('1')) {
							mask[chrCVI].set(i);
						}
					}
					chrCVI=chrCVI+1;
				}
			}
			int cardin = 0;
			for (int c=0; c<5; c++) {
				cardin = cardin + mask[c].cardinality();
			}
			
			
			
			////	Get focus samples
			//
			System.out.println("Get samples!");
            File fileS = new File(sampleFile);
            Scanner scannerS = new Scanner(fileS);
            int fil = 0;
            while ( scannerS.hasNextLine() ) {
            	String snp = scannerS.nextLine();
                if (snp.charAt(0) != '#') {
                	fil = fil + 1;
                }
            }
            System.out.println("samples: " + fil);
            
            String[] pop1 = new String[fil];
            fil = 0;
            scannerS = new Scanner(fileS);
            while ( scannerS.hasNextLine() ) {
            	String snp = scannerS.nextLine();
                String[] splitSnp = snp.split("\t");
                if (snp.charAt(0) != '#') {
                	pop1[fil] = splitSnp[0];
                    fil = fil + 1;
                }
                
            }
            System.out.println(Arrays.toString(pop1));
 
	
			////	Get focus samples 2
			//
			System.out.println("Get samples!");
            File fileS2 = new File(sampleFile2);
            Scanner scannerS2 = new Scanner(fileS2);
            fil = 0;
            while ( scannerS2.hasNextLine() ) {
            	String snp = scannerS2.nextLine();
                if (snp.charAt(0) != '#') {
                	fil = fil + 1;
                }
            }
            System.out.println("samples: " + fil);
            
            String[] pop2 = new String[fil];
            fil = 0;
            scannerS2 = new Scanner(fileS2);
            while ( scannerS2.hasNextLine() ) {
            	String snp = scannerS2.nextLine();
                String[] splitSnp = snp.split("\t");
                if (snp.charAt(0) != '#') {
                	pop2[fil] = splitSnp[0];
                    fil = fil + 1;
                }
                
            }
            System.out.println(Arrays.toString(pop2));
                       
           

        		////
			//      Get indexes
			
        		int[] p1ind = new int[pop1.length];
			int[] p2ind = new int[pop2.length];
			
			//// Open the big matrix
			//
			System.out.println("Run the matrix!");
			File matrix = new File(matrixName);
			Scanner scannerMatrix = new Scanner(matrix);
			
			String idsUnsplit = scannerMatrix.nextLine();
			String[] splitIDs = idsUnsplit.split("\t");
			 
			//// Indexes for pop1
			//
			for (int f =0; f<p1ind.length; f++) {
			        for (int s=2; s<splitIDs.length; s++) {
			       	 	if (splitIDs[s].equals(pop1[f])) {
			                        p1ind[f] = s;
			                }
			        }
			}
			System.out.println(Arrays.toString(p1ind));
            
			//// Indexes for pop2
			//
			for (int f =0; f<p2ind.length; f++) {
			        for (int s=2; s<splitIDs.length; s++) {
			       	 	if (splitIDs[s].equals(pop2[f])) {
			                        p2ind[f] = s;
			                }
			        }
			}
            		
			//// Index cvi-0
			//
			String cvi0Id = "6911";		// "4073_M";		// 6911";
			int cvi0Index = 0;
			for (int s=2; s<splitIDs.length; s++) {
				if (splitIDs[s].equals(cvi0Id) ) {
			        	cvi0Index = s;
			       	}
			}
			System.out.println("Cvi-0: " + cvi0Index + " " + splitIDs[cvi0Index]);
			
			//// Index Ler
			//
			String lerId = "7213";
			int lerIndex = 0;
			for (int s=2; s<splitIDs.length; s++) {
				if (splitIDs[s].equals(lerId) ) {
			        	lerIndex = s;
			       	}
			}
			System.out.println("Ler: " + lerIndex + " " + splitIDs[lerIndex]);
			


			////	Take care of missing samples
			//
			int s2 = 0;
			for (int c=0; c<p2ind.length; c++) {
				if (p2ind[c] != 0) {
					s2 = s2 + 1;
				}
			}
			int[] p2indNew = new int[s2];
			s2 = 0;
			for (int c=0; c<p2ind.length; c++) {
				if (p2ind[c] != 0) {
					p2indNew[s2] = p2ind[c];
					s2 = s2 + 1;
				}
			}
			p2ind = p2indNew;
			System.out.println(Arrays.toString(p2ind));



			int[] sfs = new int[p1ind.length + 1];
			for (int i=0; i<sfs.length; i++) {
				sfs[i] = 0;
			}







			
		Writer writer = new FileWriter(maskName + "_longShortBranch05ns.txt");
		PrintWriter out = new PrintWriter(writer);
		
            
	       	////	Go with contents
	    	//
			int fix = 0;
			int segrega = 0;
			int priv = 0;
			int share = 0;
			int maxN = (int) Math.round(0.05*p1ind.length);

	       	while ( scannerMatrix.hasNextLine() ) {
			String snp = scannerMatrix.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	int chr = Integer.parseInt(splitSnp[0]);
		       	int pos = Integer.parseInt(splitSnp[1]);
		       	char derBase = '.';
			
	       	if (mask[chr-1].get(pos) && (splitSnp[lerIndex].charAt(0) != 'N' &&  splitSnp[cvi0Index].charAt(0) != 'N') ) {
		       		
				char cvi0Base = splitSnp[cvi0Index].charAt(0);
				char lerBase = splitSnp[lerIndex].charAt(0);
				
				if (cvi0Base != lerBase) {
					// Check where it is in Santo
					//
					int der = 0;
			       		int anc = 0;
					int ns = 0;
					for (int f=0; f<p1ind.length; f++) {
						char base = splitSnp[p1ind[f]].charAt(0);
						if (base != 'N') {
							if (base == lerBase) {
								anc = anc + 1;
							}
							if (base == cvi0Base) {
								der = der + 1;
							}
						} else {
							ns = ns + 1;
						}
					}
					if (ns < maxN) {
						sfs[der] = sfs[der] + 1;
						if (der != 0) {
							if (anc == 0) {
								fix = fix + 1;
							} else {
								segrega = segrega + 1;
							}
						}
					}	
					// out.print(der + "\t" + anc + "\t" + ns);
					out.print(chr + "\t" + pos + "\t" + splitSnp[lerIndex].charAt(0) + "\t" + splitSnp[cvi0Index].charAt(0) + "\t" + der + "\t" + anc + "\t" + ns + "\t" + segrega + "\t" + fix + "\t" + (double)segrega/(double)(fix + segrega) + "\n");
				}
			}
			if (mask[chr-1].get(pos)) {
				//out.print(chr + "\t" + pos + "\t" + splitSnp[lerIndex].charAt(0) + "\t" + splitSnp[cvi0Index].charAt(0) + "\t" + der + "\t" + anc + "\t" + ns + "\t" + segrega + "\t" + fix + "\t" + (double)segrega/(double)(fix + segrega) + "\n");
			}
		
			//if (mask[chr-1].get(pos)) {
			//	out.print("\n");
			//}
		}
			
			
			// Writer writer = new FileWriter(maskName + "_longShortBranch.txt");
			// PrintWriter out = new PrintWriter(writer);
			
			// out.print("\n");
			out.print(segrega + "\t" + fix + "\t" + (double)segrega/(double)(fix + segrega) + "\n");
			// out.print("to the world:\t" + priv + "\t" + share + "\t" (double)priv/(double)(priv + share)  + "\n");
			// for (int i=0; i<sfs.length; i++) {
			//	out.print(sfs[i]+ ",");
			// }
			// out.print("\n");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Qtl_toGenes_toVarians_wtIslands qtl_toGenes_toVarians_wtIslands = new Qtl_toGenes_toVarians_wtIslands();
		qtl_toGenes_toVarians_wtIslands.setFileToSubSet(args[0], args[1], args[2], args[3]);
	}
}
