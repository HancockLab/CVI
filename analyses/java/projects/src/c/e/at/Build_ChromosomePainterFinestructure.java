package c.e.at;

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

public class Build_ChromosomePainterFinestructure {
	
	

	private BitSet[] mask = null;
	private BitSet[] combinedMask = null;
	private BitSet[] maskAnd = null;
	private BitSet[] maskOr = null;
	private BitSet[] maskXor = null;

	private BitSet[] meraviglia = null;
	
	String snp1=null;
	private String[] splitSnp1 = null;
	int accession = 0;

	
	private Build_ChromosomePainterFinestructure() {}

	public void setSnpFile(String filename){
		
		
		try {
			
			// accession = 0;
			accession = Integer.parseInt(filename);
			
			String whereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/chromoPainter/";
			
			mask =new BitSet[5];
			combinedMask =new BitSet[5];
			maskOr=new BitSet[5];
			maskAnd=new BitSet[5];
			maskXor=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
				combinedMask[c]= new BitSet();
				maskOr[c]= new BitSet();
				maskAnd[c]= new BitSet();
				maskXor[c]= new BitSet();
			}
			
			
			// Charge the combined mask
			
			File fileMask = new File(whereWe + "masks/combinedMask.txt");
			Scanner scannerMask = new Scanner(fileMask);
        	int chrCVI=0;
        	String lineMask=null;
        	for (int m=0; m<10; m++) {
	        	lineMask = scannerMask.nextLine();
	        	if (m%2 == 1) {
		        	for (int i=1; i<lineMask.length(); i++) {
		        		if (lineMask.charAt(i) == ('1')) {
		        			combinedMask[chrCVI].set(i);
		        		}
		        	}
		        	chrCVI=chrCVI+1;
	        	}
	        }
	        int genome =0;
	        for (int c=0; c<5; c++) {
	        	genome = genome + combinedMask[c].cardinality();
	        	System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("genome: " + genome);
			
			
			
        	
        	

			
        	// Dajje coooi nosstri CVIs!!
			
			File folder = new File(whereWe + "snp/");
			File[] listOfFiles = folder.listFiles();
			int numFiles = listOfFiles.length;
			
			System.out.println("numFiles: " + numFiles);
			
			for (int z=0; z<listOfFiles.length; z++) {
				System.out.println("listOfFiles" + z + " is: " + listOfFiles[z] );
			}
			
			// We got file names!!!
			
			
			
			// Now build a mask of segregating sites - maskXor
			
			for (int f=0; f<listOfFiles.length; f++) {
				
				System.out.println("File numero.... " + f);
				
				for (int c=0; c<5; c++) {
					mask[c]= new BitSet();
				}
				
				File snpFile = listOfFiles[f];
				Scanner scannerCvi = new Scanner(snpFile);
				
				while ( scannerCvi.hasNextLine() ) { 
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
			    		int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
			        	int posSNP = Integer.parseInt(splitSnp1[1]);
			        	if (combinedMask[chr-1].get(posSNP)) {
			        		mask[chr-1].set(posSNP);
			        	}
			        }
				}
			
				
				// Accumulate new SNPs in maskXor
				
				for (int c=0; c<5; c++) {
					if (f == 0) {
						maskAnd[c].or(mask[c]);
					}
					maskOr[c].or(mask[c]);
					maskAnd[c].and(mask[c]);
				}
			}
			for (int c=0; c<5; c++) {
				maskXor[c].or(maskOr[c]);
				maskXor[c].xor(maskAnd[c]);
			}
			
			// MaskXor contains all segregating sites
			
			
			
			int numSnps = 0;
			
			for (int c=0; c<5; c++) {
				numSnps = numSnps + maskXor[c].cardinality();
				System.out.println("maskXor[" + c + "].cardinality(): " + maskXor[c].cardinality());
			}
			System.out.println("numSnps: " + numSnps);
			
			
			
			
			
			

			
			int[] checkChr = new int[numSnps];
			int count = 0;
			for (int c=0; c<5; c++) {
				for (int pos =0; pos < maskXor[c].cardinality(); pos++) {
					checkChr[count] = (c+1);
					count = count+1;
				}
			}
			System.out.println("checkChr!");
			
			
			
			
			int[] positions = new int[numSnps];
			count =0;
			for (int c=0; c<5; c++) {
				for (int pos =0; pos < maskXor[c].length(); pos++) {
					if (maskXor[c].get(pos)) {
						positions[count] = pos;
						count = count +1;
					}
				}
			}
			System.out.println("positions!");
			
			
			if (accession == 0) {
			
				// Write positions for finestructure
				Writer fineWriter = new FileWriter(whereWe + "results/" + "positions.txt");
				PrintWriter outFine = new PrintWriter(fineWriter);
				
				outFine.print("P ");
				for (int pos = 0; pos < positions.length; pos++) {
					outFine.print(positions[pos]);
					outFine.print(" ");
				}
				outFine.print("\n");
				
				for (int pos = 0; pos < positions.length; pos++) {
					outFine.print("S");
				}
				outFine.close();
				System.out.println("positions printed");
				
				
				// Write parameters for PCA
				
				Writer writerP = new FileWriter(whereWe + "results/" + "PCA_params.txt");
				PrintWriter outP = new PrintWriter(writerP);
				
				outP.println(Arrays.toString(listOfFiles));
				outP.println(Arrays.toString(positions));
				outP.println(Arrays.toString(checkChr));
				
				outP.close();
				
				
				
				// Write parameters for ADMIXTURE
				

				Writer writerA = new FileWriter(whereWe + "results/" + "ADM_params.map");
				PrintWriter outA = new PrintWriter(writerA);
				
				for (int snp=0; snp<positions.length; snp++) {
					outA.print(checkChr[snp]);
					outA.print("\t");
					
					String snpName = checkChr[snp] + "_" + positions[snp];
					outA.print(snpName);
					outA.print("\t");
					
					outA.print("0");
					outA.print("\t");
					
					outA.print(positions[snp]);
					outA.print("\n");				
				}
				outA.close();
				
				
				
				// Write accession names
				
				Writer writerN = new FileWriter(whereWe + "results/" + "ADM_AccessionNames.txt");
				PrintWriter outN = new PrintWriter(writerN);
				
				for (int name=0; name<listOfFiles.length; name++) {
					outN.print(listOfFiles[name]);
					outN.print("\n");				
				}
				outN.close();
			}
			
			
			
			
			

			
			
			// Now fill the matrix Meraviglia!!!
			
			
			meraviglia = new BitSet[listOfFiles.length];
			
			for (int plants=0; plants<listOfFiles.length; plants++) {
				meraviglia[plants] = new BitSet(numSnps);
			}
			
			
			
			
			
			for (int f=accession; f<accession+1; f++) {
				
				String name = listOfFiles[f].getAbsolutePath();
				String[] nameSplit = name.split("\\/");
				String[] splitAgain = nameSplit[nameSplit.length-1].split("\\.");
				String shortName = splitAgain[0];
				
				System.out.println("We focus on plant: " + shortName + " " + f);
				
				
				
				
				
				Writer writer = new FileWriter(whereWe + "results/" +"PCA_" + f + ".txt", true);
				PrintWriter out = new PrintWriter(writer);
								
				File snpFile = listOfFiles[f];
				Scanner scannerCvi = new Scanner(snpFile);
				
				while ( scannerCvi.hasNextLine() ) {
					
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        		
	        		if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
		        		
	        			int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        		int posSNP = Integer.parseInt(splitSnp1[1]);
		        		
		        		if (maskXor[chr-1].get(posSNP)) {
		        			
		        			int scaledPos =0;
							for (int rr=0; rr<positions.length; rr++) {
								if ( ( posSNP == positions[rr] ) && ( chr == checkChr[rr]) ) {
									scaledPos = rr;
								}
							}
				        	meraviglia[f].set(scaledPos);
			        	}
	        		}
				}
				
				System.out.println("Writing...");
				
				for (int bit =0; bit< numSnps; bit++) {
					if (meraviglia[f].get(bit)) {
						out.print("1");
					}
					if (!meraviglia[f].get(bit)) {
						out.print("0");
					}
				}
				out.print("\n");
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

			
	public static void main(String[] args) {
		Build_ChromosomePainterFinestructure build_ChromosomePainterFinestructure = new Build_ChromosomePainterFinestructure();
		build_ChromosomePainterFinestructure.setSnpFile(args[0]); // args[0]
	}
}
