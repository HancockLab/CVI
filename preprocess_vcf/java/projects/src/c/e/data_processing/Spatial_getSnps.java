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

public class Spatial_getSnps {
	
	public Spatial_getSnps() {}
	
	public void setFileToSubSet(String matrixName, String samples, String samples2){
		
		try {
			System.out.println("src/c/e/data_processing/spatial_getSnps.java");
			
			////
			//			Get samples from island 1
			////
			
			File sampleFile = new File(samples);
			Scanner scannerS = new Scanner(sampleFile);
	    	int fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] pop1 = new String[fil];

			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				pop1[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println(Arrays.toString(pop1));


			////
			//			Get samples from island 2, to exclude fixed in both
			////
			
			File sampleFile2 = new File(samples2);
			Scanner scannerS2 = new Scanner(sampleFile2);
	    	int fil2 = 0;
			while ( scannerS2.hasNextLine() ) {
				String snp = scannerS2.nextLine();
		       		fil2 = fil2 + 1;
			}
			System.out.println("files: " + fil2);
			String[] pop2 = new String[fil2];

			fil2= 0;
			scannerS2 = new Scanner(sampleFile2);
			while ( scannerS2.hasNextLine() ) {
				String snp = scannerS2.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				pop2[fil2] = splitSnp[0];
				fil2 = fil2 + 1;
			}
			System.out.println(Arrays.toString(pop2));

			
			////
			//			Get indexes
			////

			int[] p1ind = new int[pop1.length];
			
			File matrix = new File(matrixName);
	    		Scanner scannerMatrix = new Scanner(matrix);
	    	
			String idsUnsplit = scannerMatrix.nextLine();
		    String[] splitIDs = idsUnsplit.split("\t");
	    	
	    		// Indexes for pop1
			for (int f =0; f<p1ind.length; f++) {
            	for (int s=2; s<splitIDs.length; s++) {
                	if (splitIDs[s].equals(pop1[f])) {
                		p1ind[f] = s;
                	}
                }
			}
			System.out.println(Arrays.toString(p1ind));


			////
			//			Get indexes for pop 2
			////

			int[] p2ind = new int[pop2.length];
			for (int f =0; f<p2ind.length; f++) {
            	for (int s=2; s<splitIDs.length; s++) {
                	if (splitIDs[s].equals(pop2[f])) {
                   		p2ind[f] = s;
                	}
                }
			}
			System.out.println(Arrays.toString(p2ind));

			

			////
			//			Match to population, id, lat, long
			////
			String[] ids = new String[pop1.length];
			String[] subpops = new String[pop1.length];
			double[] lat = new double[pop1.length];
			double[] lon = new double[pop1.length];
			
			for (int p=0; p<pop1.length; p++) {
				Boolean thereis = false;
				File idsToPlants = new File("/srv/biodata/dep_coupland/grp_hancock/VCF/idsToPlants_11-7-18.txt");
				Scanner scannerIds = new Scanner(idsToPlants);
				while ( scannerIds.hasNextLine() ) {
					String snp = scannerIds.nextLine();
			       	String[] splitSnp = snp.split("\t");
			       	if (pop1[p].equals(splitSnp[0])) {
			       		ids[p] = splitSnp[1];
			       		subpops[p] = splitSnp[1].split("-")[0];
			       		thereis = true;
			       	}
				}
				if (!thereis) {
					System.out.println("Problem: there is not " + pop1[p]);
				}
				

	    		////
				//				Link to coord
		    	////
	    		
		    	File coordFile = new File("/home/fulgione/data/GPS_all_accessions_Hancock.txt");
		    	Scanner scannerCoord = new Scanner(coordFile);
		    	scannerCoord.nextLine();
		    	Boolean thereIsCoord = false;
		    	
		    	while (scannerCoord.hasNextLine()) {
					String name = scannerCoord.nextLine();
					String[] splitName = name.split("\t");
					
					String pop = splitName[0].split("-")[0];
					if (subpops[p].equals(pop)) {
						
						lat[p] = Double.parseDouble(splitName[2]);
						lon[p] = Double.parseDouble(splitName[3]);
						
						thereIsCoord = true;
				    	//System.out.println(IDs[a] + "\t" + islandIDs[a] + "\t"+ pop2);
					}
					
		    	}
		    	if (!thereIsCoord) {
		    		System.out.println("Problem at: " + subpops[p]);
		    	}
				
				
			}
			System.out.println(Arrays.toString(ids));
			System.out.println(Arrays.toString(subpops));
			System.out.println(Arrays.toString(lat));
			System.out.println(Arrays.toString(lon));

			

			


			///////
			////	 Open the big matrix
			///////

		    matrix = new File(matrixName);
	    	scannerMatrix = new Scanner(matrix);
			
			// Open also the writing file
			
			String name = samples.split("/")[6].split("_")[0] + "_" + samples.split("/")[6].split("_")[1] + "_" + samples.split("/")[6].split("_")[2];
			System.out.println("Island: " + name);

			Writer writer = new FileWriter(matrixName + "_" + name + "_snpsForSpatial2.txt");
			PrintWriter out = new PrintWriter(writer);
			
			Writer writer2 = new FileWriter(matrixName + "_" + name + "_fixedDerived2.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			
			// Print header
			String head = scannerMatrix.nextLine();
			String[] splitHead = head.split("\t");
	       		for (int h=0; h<6; h++) {
				out.print(splitHead[h] + "\t");
			}
	       	out.print("cviAllele\t" + "numDer\t" + "numAnc\t" + "numDerPolygon\t" + "numAncPolygon\t" + "hetAll\t" + "hetPolygon\t" + "subpops\t" + "hetPerSubpop\t" + "latDer\t" + "lonDer\t" + "latAnc\t" + "lonAnc\t" + "IDsDer\t" + "IDsAnc\t" + "numDerFogo\t" + "numAncFogo\t" + "IDsDerFogo" + "IDsAncFogo\n"); 
	       	/*
	       	for (int f =0; f<p1ind.length; f++) {
				out.print(splitHead[p1ind[f]]);
				if (f<p1ind.length-1) {
					out.print("\t");
				}
			}
			out.print("\n");
			*/
			
			
			// Go with contents
		    int snps = 0;
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
			 	String[] splitSnp = snp.split("\t");
		      	 	char cviBase = '.';
		       	
		 	    	// Get cvi derived base 
				boolean mutat = false;
		  		for (int f =0; f<p1ind.length; f++) {
					if (splitSnp[p1ind[f]].charAt(0) != 'N') {
						if (splitSnp[p1ind[f]].charAt(0) != splitSnp[4].charAt(0)) {
							mutat = true;
							cviBase = splitSnp[p1ind[f]].charAt(0);
						}
					}
				}
				// if (!n) {
			       		// Select sites that are mutated but not fixed
		  			//
					boolean segrega = false;
			       		for (int f =0; f<p1ind.length; f++) {
			       			if ( (splitSnp[p1ind[f]].charAt(0) != cviBase) && (splitSnp[p1ind[f]].charAt(0) != 'N') ) {
			       				segrega = true;
			       			}
			       		}
			       		// Not fixed in island 2
			       		//
			       		for (int f =0; f<p2ind.length; f++) {
			       			if ( (splitSnp[p2ind[f]].charAt(0) != cviBase) && (splitSnp[p2ind[f]].charAt(0) != 'N') ) {
			       				segrega = true;
			       			}
			       		}
			       		
				      	// If it is mutated but not fixed in the archipelago, output
			       		//
			       		if (segrega && mutat) {
			       			////
			       			// 			Now, calculate output
			       			////
			       			
			       			// Chr, pos, ancestors, cviAllele
			       			for (int h=0; h<6; h++) {
			       				out.print(splitSnp[h] + "\t");
			       			}
			       			out.print(cviBase + "\t");
			       			
			       			// numDer, numAnc
			       			int der = 0;
			       			int anc = 0;
			       			String[] derPops = {};
			       			
			       			for (int f =0; f<p1ind.length; f++) {
			       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
			       					if (splitSnp[p1ind[f]].charAt(0) == cviBase) {
				       					der = der + 1;
				       					// Build vector of pops with the derived allele
				       					// 
				       					Boolean isthere = false;
				       					for (int p =0; p<derPops.length; p++) {
				       						if (derPops[p].equals(subpops[f])) {
				       							isthere = true;
				       						}
				       					}
				       					if (!isthere) {
				       						String[] memPop = derPops;
				       						derPops = new String[memPop.length + 1];
				       						for (int old=0; old<memPop.length; old++) {
				       							derPops[old] = memPop[old];
				       						}
				       						derPops[derPops.length-1] = subpops[f];
				       					}
				       				} else {
				       					anc = anc + 1;
				       				}
			       				}
			       			}
			       			out.print(der + "\t" + anc + "\t");
			       			
			       			// "numDerPolygon\t" + "numAncPolygon\t" + hetAll + hetPolygon
			       			//
			       			int derPol = 0;
			       			int ancPol = 0;
			       			for (int f =0; f<p1ind.length; f++) {
			       				// Check if the pop has derived alleles
			       				Boolean popin = false;
			       				for (int p=0; p<derPops.length; p++) {
			       					if (derPops[p].equals(subpops[f])) {
			       						popin = true;
			       					}
			       				}
			       				if (popin) {
								// System.out.println("PopIn");
			       					if (splitSnp[p1ind[f]].charAt(0) != 'N') {
			       						if (splitSnp[p1ind[f]].charAt(0) == cviBase) {
			       							derPol = derPol + 1;
										// System.out.println("Der");
			       						} else {
			       							ancPol = ancPol + 1;
										// System.out.println("Anc");
					       				}
			       					}
			       				}
			       			}
			       			// System.out.println("");
						out.print(derPol + "\t" + ancPol + "\t" + 
			       					2*( (double)(der)/(double)(der + anc) )*( (double)(anc)/(double)(der + anc) ) + "\t" + 
			       					2*( (double)(derPol)/(double)(derPol + ancPol) ) * ( (double)(ancPol)/(double)(derPol + ancPol) ) + "\t");
			       			
			       			// subpops
			       			for (int p=0; p<derPops.length; p++) {
		       					out.print(derPops[p] + ",");
			       			}
			       			out.print("\t");
			       			
			       			// 	hetPerSubpop
		       				for (int p=0; p<derPops.length; p++) {
		       					int derPop = 0;
		       					int ancPop = 0;
				       			for (int f =0; f<p1ind.length; f++) {
				       				// Check if sample is in the right pop
			       					if (derPops[p].equals(subpops[f])) {
			       						if (splitSnp[p1ind[f]].charAt(0) != 'N') {
				       						if (splitSnp[p1ind[f]].charAt(0) == cviBase) {
				       							derPop = derPop + 1;
				       						} else {
				       							ancPop = ancPop + 1;
						       				}
				       					}
				       				}
				       			}
		       					out.print(2 * ( (double)(derPop)/(double)(derPop + ancPop) ) *( (double)(ancPop)/(double)(derPop + ancPop) ) + ",");
		       				}
			       			out.print("\t");
			       			
	       					// latDer	lonDer
			       			//
			       			for (int f =0; f<p1ind.length; f++) {
			       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       						if (splitSnp[p1ind[f]].charAt(0) == cviBase) {
		       							out.print(lat[f] + ",");
		       						}
			       				}
			       			}
			       			out.print("\t");
			       			for (int f =0; f<p1ind.length; f++) {
			       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       						if (splitSnp[p1ind[f]].charAt(0) == cviBase) {
		       							out.print(lon[f] + ",");
		       						}
			       				}
			       			}
			       			out.print("\t");
			       			
			       			// latAnc	lonAnc
			       			//
			       			for (int f =0; f<p1ind.length; f++) {
			       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       						if (splitSnp[p1ind[f]].charAt(0) != cviBase) {
		       							out.print(lat[f] + ",");
		       						}
			       				}
			       			}
			       			out.print("\t");
			       			for (int f =0; f<p1ind.length; f++) {
			       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       						if (splitSnp[p1ind[f]].charAt(0) != cviBase) {
		       							out.print(lon[f] + ",");
		       						}
			       				}
			       			}
			       			out.print("\t");
			       			
			       			// "IDsDer\t" + "IDsAnc\n"
			       			for (int f =0; f<p1ind.length; f++) {
			       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       						if (splitSnp[p1ind[f]].charAt(0) == cviBase) {
		       							out.print(ids[f] + ",");
		       						}
			       				}
			       			}
			       			out.print("\t");
			       			for (int f =0; f<p1ind.length; f++) {
			       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       						if (splitSnp[p1ind[f]].charAt(0) != cviBase) {
		       							out.print(ids[f] + ",");
		       						}
			       				}
						}
						out.print("\t");

						// Fogos: der, anc, ids
						int derF = 0;
			       			int ancF = 0;
			       			String[] derIDsFogo = {};
			       			String[] ancIDsFogo = {};

			       			for (int f =0; f<p2ind.length; f++) {
			       				if (splitSnp[p2ind[f]].charAt(0) != 'N') {
			       					if (splitSnp[p2ind[f]].charAt(0) == cviBase) {
				       					derF = derF + 1;
				       					// Build vector of ids with the derived allele
				       					// 
				       					String[] memIDs = derIDsFogo;
				       					derIDsFogo = new String[derIDsFogo.length + 1];
				       					for (int old=0; old<memIDs.length; old++) {
				       						derIDsFogo[old] = memIDs[old];
				       					}
				       					derIDsFogo[derIDsFogo.length-1] = splitHead[p2ind[f]];
				       				} else {
				       					ancF = ancF + 1;
									// Build vector of ids with the anc allele
				       					// 
				       					String[] memIDsA = ancIDsFogo;
				       					ancIDsFogo = new String[ancIDsFogo.length + 1];
				       					for (int old=0; old<memIDsA.length; old++) {
				       						ancIDsFogo[old] = memIDsA[old];
				       					}
				       					ancIDsFogo[ancIDsFogo.length-1] = splitHead[p2ind[f]];
				       				
				       				}
							}
			       			}
			       			out.print(derF + "\t" + ancF + "\t");
						for (int df=0; df<derIDsFogo.length; df++) {
							out.print(derIDsFogo[df] + ",");
						}
						out.print("\t");
						for (int df=0; df<ancIDsFogo.length; df++) {
							out.print(ancIDsFogo[df] + ",");
						}
			       			/*
			       			for (int f =0; f<p1ind.length; f++) {
			       				out.print(splitSnp[p1ind[f]]);
			       				if (f<p1ind.length-1) {
			       					out.print("\t");
			       				}
			       			}
			       			*/
			       			out.print("\n");
					     	snps = snps + 1;
	       				}
					if (mutat && !segrega) {
						out2.print(snp + "\n");
					}

				// }
			}
			out.close();
			out2.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Spatial_getSnps spatial_getSnps = new Spatial_getSnps();
		spatial_getSnps.setFileToSubSet(args[0], args[1], args[2]);
	}
}
