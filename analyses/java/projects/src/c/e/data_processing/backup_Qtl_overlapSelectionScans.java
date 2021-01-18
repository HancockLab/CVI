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

public class Qtl_overlapSelectionScans {
	
	public Qtl_overlapSelectionScans() {}
	
	public void setFileToSubSet(String matrixName, String numPh, String scanName, String bigSmall){
		
		try {
			
			boolean bigPheno = false;
			if (bigSmall.equals("big")) {
				bigPheno = true;
			}
			int[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};
			
			// Build selection masks:
			//
			// Xp-Clr
			// 
			int selChunkNum = 0;
			
			BitSet[] maskXpClr = new BitSet[5];
			for (int c=0; c<maskXpClr.length; c++) {
				maskXpClr[c] = new BitSet();
			}
			
			File xpClrFile= new File("/home/fulgione/selScans_final_santo_5percTail.txt");
	    	Scanner scannerXp = new Scanner(xpClrFile);
	    	
	    	while ( scannerXp.hasNextLine() ) {
				String snp = scannerXp.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	if (snp.charAt(0) != '#') {
		       		String[] splitComment = splitSnp[8].split(";");
			       	for (int spl=0; spl<splitComment.length; spl++) {
			       		if (splitComment[spl].split("=")[0].equals("scan")) {
			       			String scan = splitComment[spl].split("=")[1];
			       			
			       			// Is it the right scan?
			       			// if (scan.equals(scanName)) {
			       				int start = Integer.parseInt(splitSnp[3]);
				       			int end = Integer.parseInt(splitSnp[4]);
				       			for (int pos=start; pos<=end; pos++) {
				       				// System.out.println(pos + "\t" + snp);
								maskXpClr[Character.getNumericValue(splitSnp[0].charAt(3)) - 1].set(pos);
			       				}
				       			selChunkNum = selChunkNum + 1;
			       			// }
			       		}
			       	}
		       	}
	    	}
	    	System.out.println("selChunkNum: " + selChunkNum);
	    	
	    	////
	    	//			Now record them to randomise
	    	////
	    	
			int[] selLengths = new int[selChunkNum];
			selChunkNum = 0;
			
			scannerXp = new Scanner(xpClrFile);
	    	while ( scannerXp.hasNextLine() ) {
				String snp = scannerXp.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	if (snp.charAt(0) != '#') {
		       		String[] splitComment = splitSnp[8].split(";");
			       	for (int spl=0; spl<splitComment.length; spl++) {
			       		if (splitComment[spl].split("=")[0].equals("scan")) {
			       			String scan = splitComment[spl].split("=")[1];
			       			
			       			// Is it the right scan?
			       			// if (scan.equals(scanName)) {
			       				int start = Integer.parseInt(splitSnp[3]);
				       			int end = Integer.parseInt(splitSnp[4]);
				       			if (end < start) {
				       				System.out.println("Problem at: " + snp);
				       			}
				       			// +1 for counting start and end included
				       			selLengths[selChunkNum] = (end - start) + 1;
				       			selChunkNum = selChunkNum + 1;
			       			// }
			       		}
			       	}
		       	}
	    	}
	    	System.out.println("selLengths: " + Arrays.toString(selLengths));
	    	
			
			
			
			
			
			
			
			
			
			
			
			
			
				
			File qtlFile= new File(matrixName);
	    	Scanner scanner = new Scanner(qtlFile);
	    	
	    	/////
	    	//		Get Phenotypes
	    	/////
	    	String[] phenotypes = null;
		if (bigPheno) {
			phenotypes = new String[1];
			phenotypes[0] = "Fitness";
		} else {
			phenotypes = new String[1];
			phenotypes[0] = "Axillary_fruits";
		}
	    	int line = 0;
	    	while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	String[] splitComment = splitSnp[8].split(";");
	       		// System.out.println(Arrays.toString(splitComment));
	       		
		       	for (int spl=0; spl<splitComment.length; spl++) {
		       		/////
		       		//			Do big phenotypes
		       		/////
		       		String targetPheno = null;
				if (bigPheno) {
					targetPheno = "Phenotype";
				} else {
					targetPheno = "phenoSpec";
				}
				
				if (splitComment[spl].split("=")[0].equals(targetPheno)) {
		       			String pheno = splitComment[spl].split("=")[1];
		       			
		       			// Is it there already?
		       			Boolean in = false;
		       			for (int p =0; p<phenotypes.length; p++) {
		       				if (phenotypes[p].equals(pheno)) {
		       					in = true;
		       				}
		       			}
		       			if (!in) {
		       				String[] phenotypesMem = phenotypes;
		       				phenotypes = new String[phenotypesMem.length + 1];
		       				for (int m=0; m<phenotypesMem.length; m++) {
		       					phenotypes[m] = phenotypesMem[m];
		       				}
		       				phenotypes[phenotypesMem.length] = pheno;
		       			}
		       			//System.out.println(pheno);
		       		}
		       		
		       		/////
		       		//			Do Specific phenotypes
		       		/////
		       		/*
		       		if (splitComment[spl].split("=")[0].equals("phenoSpec")) {
		       			String pheno = splitComment[spl].split("=")[1];
		       			
		       			// Is it there already?
		       			Boolean in = false;
		       			for (int p =0; p<specificPhenotypes.length; p++) {
		       				if (specificPhenotypes[p].equals(pheno)) {
		       					in = true;
		       				}
		       			}
		       			if (!in) {
		       				String[] phenotypesMem = specificPhenotypes;
		       				specificPhenotypes = new String[phenotypesMem.length + 1];
		       				for (int m=0; m<phenotypesMem.length; m++) {
		       					specificPhenotypes[m] = phenotypesMem[m];
		       				}
		       				specificPhenotypes[phenotypesMem.length] = pheno;
		       			}
		       			// System.out.println(pheno);
		       		}
				*/
		       	}
			line = line + 1;
	    	}
	    	System.out.println(line + "\t" + Arrays.toString(phenotypes));
	    	System.out.println("Pheno length: " + phenotypes.length);
	    	
	    	// Got all phenotypes
	    	
	    	
	    	
	    	
	    	

		    /////
		    //		Output something for every phenotype
	    	/////
		//

		String outFold = "/srv/biodata/dep_coupland/grp_hancock/andrea/qtls_bigPheno_2019-11-26";
	    	
		int phenoNum = Integer.parseInt(numPh);
		
	    	String FileName = outFold + "/qtlsBig_overlap_" + phenotypes[phenoNum] + "_allScans.txt";  //"  + scanName + ".txt";
		Writer writer = new FileWriter(FileName);
			PrintWriter out = new PrintWriter(writer);
			out.print("# Here General Phenotypes:\n");
			out.print("#\n");
   			
   			for (int p =phenoNum; p<phenoNum+1; p++) { //phenotypes.length; p++) {
   				
   				// Build a mask per phenotype
   				//
   				BitSet[] mask = new BitSet[5];
   				for (int c=0; c<mask.length; c++) {
   					mask[c] = new BitSet();
   				}
   				
   				scanner = new Scanner(qtlFile);
   				while ( scanner.hasNextLine() ) {
   					String snp = scanner.nextLine();
   			       	String[] splitSnp = snp.split("\t");
   			       	
   			       	String[] splitComment = splitSnp[8].split(";");
   			       	for (int spl=0; spl<splitComment.length; spl++) {
   			String targetPheno = null;
			if (bigPheno) {
				targetPheno = "Phenotype";
			} else {
				targetPheno = "phenoSpec";
			}
   			       		if (splitComment[spl].split("=")[0].equals(targetPheno)) {
   			       			String pheno = splitComment[spl].split("=")[1];
   			       			
   			       			if (phenotypes[p].equals(pheno)) {
   			       				int start = Integer.parseInt(splitSnp[3]);
   			       				int end = Integer.parseInt(splitSnp[4]);
   			       				for (int pos=start; pos<=end; pos++) {
			       					mask[Character.getNumericValue(splitSnp[0].charAt(3)) - 1].set(pos);
			       				}
   			       			}
   			       		}
   			       	}
   			    }
   			


				////	Out the mask
				//   				//
				//
				Writer writerM = new FileWriter(outFold + "/" + phenotypes[phenoNum] + "_mask.txt");
				PrintWriter outM = new PrintWriter(writerM);
				for (int c=0; c<5; c++) {
					outM.print(">Chr" + (c+1));
					outM.print("\n");
					for (int bit =0; bit< mask[c].length(); bit++) {
						if (mask[c].get(bit)) {
							outM.print("1");
						}
						if (!mask[c].get(bit)) {
							outM.print("0");
						}
					}
					outM.print("\n");
				}
				outM.close();








   				////
   				//			Record Qtls for randomisation
   				////
   				
   				int qtlChunkNum = 0;
   				boolean inHap = false;
   				
   				for (int c=0; c<5; c++) {
   					for (int pos=0; pos<mask[c].length()+1; pos++) {
   	   					if (mask[c].get(pos)) {
   	   						if (!inHap) {
   	   							// Enter hap
   	   							qtlChunkNum = qtlChunkNum + 1;
   	   						}
   	   						inHap = true; 
   	   					} else {
   	   						inHap = false;
   	   					}
   	   				}
   				}
   				System.out.println("qtlChunkNum: " + qtlChunkNum);
   				
   				int[] qtlLengths = new int[qtlChunkNum];
   				qtlChunkNum = 0;
   				inHap = false;
   				int start  = 0;
   				int end = 0;
   				
   				for (int c=0; c<5; c++) {
   					for (int pos=0; pos<mask[c].length()+1; pos++) {
   	   					if (mask[c].get(pos)) {
   	   						if (!inHap) {
   	   							// Enter hap
   	   							start = pos;
   	   							qtlChunkNum = qtlChunkNum + 1;
   	   						}
   	   						inHap = true; 
   	   					} else {
   	   						if (inHap) {
   	   							end = pos - 1;
   	   							qtlLengths[qtlChunkNum-1] = end - start;
   	   						}
   	   						inHap = false;
   	   					}
   	   				}
   				}
   				System.out.println("qtlLengths: " + Arrays.toString(qtlLengths));
   		    	
   		    	
   		    	
   		    	int cardQtl = 0;
   		    	int cardXpClr = 0;
   		    	int overlapXpClr = 0;
   		    	int genome = 0;
   		    	
   		    	BitSet[] maskAnd = new BitSet[5];
   				
   		    	for (int c=0; c<mask.length; c++) {
   		    		maskAnd[c] = new BitSet();
   		    		
   		    		cardQtl = cardQtl + mask[c].cardinality();
   					cardXpClr = cardXpClr + maskXpClr[c].cardinality();
   					genome = genome + chrL[c];
   					
   					maskAnd[c].or(mask[c]);
   					maskAnd[c].and(maskXpClr[c]);
   					overlapXpClr = overlapXpClr + maskAnd[c].cardinality(); 
   				}
   		    	
   		    	// Now calculate overlap and enrichment
   		    	double expectOverlap = ((double)cardQtl/(double)genome)*((double)cardXpClr/(double)genome)*(double)genome;
   		    	
   		    	System.out.print(phenotypes[p] + "\t" + cardQtl + "\t" + cardXpClr + "\t" + expectOverlap + "\t" + overlapXpClr + "\t" + (double)overlapXpClr/(double)expectOverlap + "\n");
   		    	out.print(phenotypes[p] + "\t" + expectOverlap + "\t" + overlapXpClr + "\t" + (double)overlapXpClr/(double)expectOverlap + "\n");
   		    	
   		    	
   		    	//////
   		    	////
   		    	//			Now randomise both Qtl regions and selection scan peaks 
   		    	////
   		    	/////

		    	Random randomChr = new Random();
		    	Random randompos = new Random();
		    	
		    	int[][] checkMb = new int[5][];
		    	for (int c=0; c<5; c++) {
		    		checkMb[c] = new int[31];
		    		for (int m=0; m<31; m++) {
				    	checkMb[c][m] = 0;
		    		}
		    	}
		    	
		    	int reps = 10000;
		    	double[] enrich = new double[reps];
		    	
		    	for (int rep=0; rep<reps; rep++) {
		    		System.out.println(rep);
		    		
	   		    	BitSet[] maskRanSel = new BitSet[5];
	   		    	BitSet[] maskRanQtl = new BitSet[5];
	   				for (int c=0; c<maskRanSel.length; c++) {
	   					maskRanSel[c] = new BitSet();
	   					maskRanQtl[c] = new BitSet();
	   	   		    }
	   				
	   				////
	   				// 		Randomise sel scans
	   				////
	   				// System.out.println("Sel");
	   				
	   				randomChr = new Random();
	   		    	randompos = new Random();
	   	




	   		    	for (int s=0; s<selChunkNum;s++) {
	   		    		int c = randomChr.nextInt(5);
	   		    		while (maskRanSel[c].cardinality() + selLengths[s] >= chrL[c]) {
	   		    			System.out.println("Chrom finished: " + c);
	   		    			c = randomChr.nextInt(5);
	   		    		}
	   		    		int pos = randompos.nextInt(chrL[c]);
	   		    		int over = 0;
	   		    		int halfL = (int)selLengths[s]/2;
	   		    		for (int pp=pos-halfL; pp<=pos + halfL; pp++) {
	   		    			////
	   		    			//		Now starts the fun: circularise chromosome to avoid edge effect at the telomeres
	   		    			////
	   		    			
	   		    			int ppp = pp;
	   		    			// If you get out of the chromosome on the left (0), jump on the other side (elongate from the end back)
	   		    			if (pp < 0) {
	   		    				ppp = chrL[c] + pp;
	   		    			}
	   		    			// If you get out of the chromosome on the right (end), jump on the other side (elongate from 0 on)
	   		    			if (pp >= chrL[c]) {
	   		    				ppp = pp - chrL[c];
	   		    			}
	   		    			// Now really build the mask
	   		    			//
	   		    			if (!maskRanSel[c].get(ppp)) {
	   		    				maskRanSel[c].set(ppp);
	   		    			} else {
	   		    				over = over + 1;
	   		    			}
	   		    		}
	   		    		// System.out.println("Overlap\tchr " + c);
	   		    		
	   		    		////
	   		    		//			If there was an overlap to other qtls...
	   		    		
	   		    		
		   		    	int run = 1;
		   		    	int overStart = pos - halfL;
		   		    	int overHang = pos + halfL;
		   		    	
	   		    		while (over > 0) {
	   		    			// Find  the edges of the region already covered
	   		    			
	   		    			if (overStart < 0) {
	   		    				int overMem = chrL[c] + overStart;
   		    					overStart = overMem;
	   		    			}
	   		    			
	   		    			if (overHang >= chrL[c]) {
	    						int overMem = overHang - chrL[c];
	    						overHang = overMem;
	   		    			}
	   		    			// Run to the edges of the 2 overlapping qtls
	   		    			//
	   		    			while (overStart >= 0 && maskRanSel[c].get(overStart)) {
	   		    				int overMem = overStart;
	   		    				overStart = overMem - 1;
	   		    			}
	   		    			// System.out.println("1.5 check: " + over + "\t" + (int)over/2 + "\t" + overStart + "\t" + overHang + "\t" + chrL[c] + "\t" + maskRanQtl[c].cardinality());
	   		    			if (overStart < 0) {
	   		    				int overMem = chrL[c] + overStart;
   		    					overStart = overMem;
   		    					while (overStart >= 0 && maskRanSel[c].get(overStart)) {
   		    						overMem = overStart;
   		   		    				overStart = overMem - 1;
   		   		    			}
	   		    			}
	   		    			while (overHang < chrL[c] && maskRanSel[c].get(overHang)) {
	   		    				int overMem = overHang;
		   		    			overHang = overMem + 1;
	   		    			}
	   		    			if (overHang >= chrL[c]) {
		    						int overMem = overHang - chrL[c];
		    						overHang = overMem;
		    						while (overHang < chrL[c] && maskRanSel[c].get(overHang)) {
		    							overMem = overHang;
				   		    			overHang = overMem + 1;
				   		    		}
	   		    			}
	   		    			// From the edges, elongate them
	   		    			// 
	   		    			// Paint the left side
	    					//
		    				for (int ppp =overStart; ppp >= overStart - (int)over/2; ppp--) {
		    					// System.out.print(ppp + "\t");
	    						if (ppp >= 0 && !maskRanSel[c].get(ppp)) {
	    							if (over > 0) {
	    								maskRanSel[c].set(ppp);
	    								over = over - 1;
	    							}
	    						}
	    					}
		    				// Paint the right side
    						//
	    					for (int ppp =overHang; ppp<=overHang + (int)over/2; ppp++) {
	    						if (ppp < chrL[c] && !maskRanSel[c].get(ppp)) {
	    							if (over > 0) {
	    								maskRanSel[c].set(ppp);
	    								over = over - 1;
	    							}
	    						}
	    					}
	    					if ( (overStart - (int)over/2) < 0) {
	    						overStart = chrL[c] - 1;
	    					} else {
	    						int overMem = overStart;
	   		    				overStart = overMem - (int)over/2;
	    					}
	    					if ( (overHang + (int)over/2) >= chrL[c]) {
	    						overHang = 0;
	    					} else {
	    						int overMemHan = overHang;
	   		    				overHang = overMemHan + (int)over/2;
	    					}
	    					run = run + 1;
	   		    		}
	   		    	}
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	   		    	
	



























	   		    	
	   		    	////
	   		    	//		Randomise Qtls
	   		    	////
	   		    	// System.out.println("Qtls");
	   		    	
	   		    	randomChr = new Random();
	   		    	randompos = new Random();
	   		    	
	   		    	for (int s=0; s<qtlChunkNum; s++) {
	   		    		int c = randomChr.nextInt(5);
	   		    		while (maskRanQtl[c].cardinality() + qtlLengths[s] >= chrL[c]) {
	   		    			// System.out.println("Chrom finished: " + c);
	   		    			c = randomChr.nextInt(5);
	   		    		}
	   		    		int pos = randompos.nextInt(chrL[c]);
	   		    		int over = 0;
	   		    		int halfL = (int)qtlLengths[s]/2;
	   		    		for (int pp=pos-halfL; pp<=pos + halfL; pp++) {
	   		    			////
	   		    			//		Now starts the fun: circularise chromosome to avoid edge effect at the telomeres
	   		    			////
	   		    			
	   		    			int ppp = pp;
	   		    			// If you get out of the chromosome on the left (0), jump on the other side (elongate from the end back)
	   		    			if (pp < 0) {
	   		    				ppp = chrL[c] + pp;
	   		    			}
	   		    			// If you get out of the chromosome on the right (end), jump on the other side (elongate from 0 on)
	   		    			if (pp >= chrL[c]) {
	   		    				ppp = pp - chrL[c];
	   		    			}
	   		    			// Now really build the mask
	   		    			//
	   		    			if (!maskRanQtl[c].get(ppp)) {
	   		    				maskRanQtl[c].set(ppp);
	   		    			} else {
	   		    				over = over + 1;
	   		    			}
	   		    		}
	   		    		// System.out.println("Overlap\tchr " + c);
	   		    		
	   		    		////
	   		    		//			If there was an overlap to other qtls...
	   		    		
	   		    		
		   		    	int run = 1;
		   		    	int overStart = pos - halfL;
		   		    	int overHang = pos + halfL;
		   		    	
	   		    		while (over > 0) {
	   		    			// System.out.println(over + "\t" + (int)over/2 + "\t" + overStart + "\t" + overHang + "\t" + chrL[c] + "\t" + maskRanQtl[c].cardinality());
	   		    			// if (overHang < chrL[c]) {
	   		    				// System.out.println(maskRanQtl[c].get(overHang));
	   		    			// }
	   		    			// if (overStart >= 0) {
	   		    				// System.out.println(maskRanQtl[c].get(overStart));
	   		    			// }
	   		    			// Find  the edges of the region already covered
	   		    			
	   		    			if (overStart < 0) {
	   		    				int overMem = chrL[c] + overStart;
   		    					overStart = overMem;
	   		    			}
	   		    			
	   		    			if (overHang >= chrL[c]) {
	    						int overMem = overHang - chrL[c];
	    						overHang = overMem;
	   		    			}
	   		    			// System.out.println("1 check: " + over + "\t" + (int)over/2 + "\t" + overStart + "\t" + overHang + "\t" + chrL[c] + "\t" + maskRanQtl[c].cardinality());
	   		    			// System.out.println("Run to the edges");
		   		    		// Run to the edges of the 2 overlapping qtls
	   		    			//
	   		    			while (overStart >= 0 && maskRanQtl[c].get(overStart)) {
	   		    				int overMem = overStart;
	   		    				overStart = overMem - 1;
	   		    			}
	   		    			// System.out.println("1.5 check: " + over + "\t" + (int)over/2 + "\t" + overStart + "\t" + overHang + "\t" + chrL[c] + "\t" + maskRanQtl[c].cardinality());
	   		    			if (overStart < 0) {
	   		    				int overMem = chrL[c] + overStart;
   		    					overStart = overMem;
   		    					while (overStart >= 0 && maskRanQtl[c].get(overStart)) {
   		    						overMem = overStart;
   		   		    				overStart = overMem - 1;
   		   		    			}
	   		    			}
	   		    			while (overHang < chrL[c] && maskRanQtl[c].get(overHang)) {
	   		    				int overMem = overHang;
		   		    			overHang = overMem + 1;
	   		    			}
	   		    			if (overHang >= chrL[c]) {
		    						int overMem = overHang - chrL[c];
		    						overHang = overMem;
		    						while (overHang < chrL[c] && maskRanQtl[c].get(overHang)) {
		    							overMem = overHang;
				   		    			overHang = overMem + 1;
				   		    		}
	   		    			}
	   		    			// System.out.println("2 check: " + over + "\t" + (int)over/2 + "\t" + overStart + "\t" + overHang + "\t" + chrL[c] + "\t" + maskRanQtl[c].cardinality());
	   		    			// System.out.println("Elongate");
	   		    			// From the edges, elongate them
	   		    			// 
	   		    			
	   		    			
	   		    			
	   		    			// Paint the left side
	    					//
		    				for (int ppp =overStart; ppp >= overStart - (int)over/2; ppp--) {
		    					// System.out.print(ppp + "\t");
	    						if (ppp >= 0 && !maskRanQtl[c].get(ppp)) {
	    							if (over > 0) {
	    								maskRanQtl[c].set(ppp);
	    								over = over - 1;
	    							}
	    						}
	    					}
		    				// Paint the right side
    						//
	    					for (int ppp =overHang; ppp<=overHang + (int)over/2; ppp++) {
	    						if (ppp < chrL[c] && !maskRanQtl[c].get(ppp)) {
	    							if (over > 0) {
	    								maskRanQtl[c].set(ppp);
	    								over = over - 1;
	    							}
	    						}
	    					}
	    					// System.out.println("3 check: " + over + "\t" + (int)over/2 + "\t" + overStart + "\t" + overHang + "\t" + chrL[c] + "\t" + maskRanQtl[c].cardinality());
	    					if ( (overStart - (int)over/2) < 0) {
	    						overStart = chrL[c] - 1;
	    					} else {
	    						int overMem = overStart;
	   		    				overStart = overMem - (int)over/2;
	    					}
	    					if ( (overHang + (int)over/2) >= chrL[c]) {
	    						overHang = 0;
	    					} else {
	    						int overMemHan = overHang;
	   		    				overHang = overMemHan + (int)over/2;
	    					}
	    					// System.out.println("4 check: " + over + "\t" + (int)over/2 + "\t" + overStart + "\t" + overHang + "\t" + chrL[c] + "\t" + maskRanQtl[c].cardinality());
	   		    			/*
   		    				if (overStart - (int)over/2 > 0) {
   		    					if (overHang + (int)over/2 < chrL[c]) {
   		    						// Paint the left side
   		    						//
	   		    					for (int ppp =overStart; ppp >= overStart - (int)over/2; ppp--) {
	   		    						if (!maskRanQtl[c].get(ppp)) {
	   		    							if (over > 0) {
	   		    								maskRanQtl[c].set(ppp);
		   		    							over = over - 1;
	   		    							}
	   		    						}
	   		    					}
	   		    					// Paint the right side
   		    						//
	   		    					for (int ppp =overHang; ppp<=overHang + (int)over/2; ppp++) {
	   		    						if (!maskRanQtl[c].get(ppp)) {
	   		    							if (over > 0) {
	   		    								maskRanQtl[c].set(ppp);
	   		    								over = over - 1;
	   		    							}
	   		    						}
	   		    					}
	   		    				}
   		    				}
   		    				*/
   		    				// int overMem = overStart;
   		    				// overStart = overMem - (int)over/2;
   		    				// int overMemHan = overHang;
   		    				// overHang = overMemHan + (int)over/2;
   		    				run = run + 1;
	   		    		}
	   		    	}
	   		    	int card =0;
   		    		for (int chr=0; chr<5; chr++) {
   		    			card = card + maskRanQtl[chr].cardinality();
   		    		}
   		    		System.out.println("Card: "+card);
	   		    	
	   		    	////
	   		    	//		Check randomness in the qtls
	   		    	/////
	   		    	// System.out.println("Check qtls");
	   		    	
	   		    	for (int c=0; c<5; c++) {
	   			    	for (int m=0; m<checkMb[c].length; m++) {
	   			    		Boolean covered =  false;
	   			    		for (int ppp = m*1000000; ppp < (m+1)*1000000; ppp++) {
	   			    			if (maskRanQtl[c].get(ppp)) {
	   			    				covered = true;
		   			    		}
	   			    		}
	   			    		if (covered) {
	   			    			checkMb[c][m] = checkMb[c][m] + 1;
	   			    		}
	   		    		}
	   		    	}
	   		    	
	   		    	
	   		    	////
	   		    	//		Compute overlap
	   		    	// System.out.println("Overlaps");
	   		    	int overlap = 0;
	   		    	int cardQ = 0;
	   		    	int cardS = 0;
	   		    	
	   		    	BitSet[] maskAndRand = new BitSet[5];
	   				
	   		    	for (int c=0; c<maskAndRand.length; c++) {
	   		    		maskAndRand[c] = new BitSet();

	   		    		cardQ = cardQ + maskRanQtl[c].cardinality();
	   		    		cardS = cardS + maskRanSel[c].cardinality();
	   					
	   		    		maskAndRand[c].or(maskRanSel[c]);
	   		    		maskAndRand[c].and(maskRanQtl[c]);
	   					overlap = overlap + maskAndRand[c].cardinality(); 
	   				}
	   		    	
	   		    	// Now calculate overlap and enrichment
	   		    	// 
	   		    	enrich[rep] = (double)overlap/(double)expectOverlap;
	   		    	double xOverlap = ((double)cardQ/(double)genome)*((double)cardS/(double)genome)*(double)genome;
	   		    	
	   		    	// System.out.print("Cards: " + cardQ + "\t" + cardQtl + "\t" + cardS + "\t" + cardXpClr + "\n");
	   		    	// System.out.print("Overlaps: " + overlap + "\t" + xOverlap + "\t" + expectOverlap + "\n");
	   		    }
		    	////
		    	//			Output
		    	
		    	System.out.println("Expected overlap: " + expectOverlap);
		    	for (int o=0; o<enrich.length; o++) {
		    		out.print(enrich[o] + "\t");
		    	}
		    	System.out.print("\n");
		    	out.print("\n");
		    	
		    	
		    	
		    	
		    	

	   			
	   			
	   			////
	   			//			Out check Mb
	   			////
	   			
	   			for (int c=0; c<5; c++) {
	   				int chrlength = (int)(chrL[c]/1000000);
	   				for (int m=0; m<chrlength; m++) {
	   					System.out.print(checkMb[c][m] + "\t");
					}
	   				System.out.print("\n");
	   			}
	   			
	   			
	   			
	   			
	   			
		    	
   		    }
   			
   			
   			
   			
   			
   			
   			
   			
   			
   			
   			
   			

	    	

		    /////
		    //		Now do Specific phenotypes
	    	/////
		/*
   			out.print("#\n");
   			out.print("# Here more specific Phenotypes:\n");
   			out.print("#\n");
   			
   			for (int p =0; p<specificPhenotypes.length; p++) {
   				
   				// Build a mask per phenotype
   				//
   				BitSet[] mask = new BitSet[5];
   				for (int c=0; c<mask.length; c++) {
   					mask[c] = new BitSet();
   				}
   				
   				scanner = new Scanner(qtlFile);
   				
   		    	while ( scanner.hasNextLine() ) {
   					String snp = scanner.nextLine();
   			       	String[] splitSnp = snp.split("\t");
   			       	
   			       	String[] splitComment = splitSnp[8].split(";");
   			       	for (int spl=0; spl<splitComment.length; spl++) {
   			       		
   			       		if (splitComment[spl].split("=")[0].equals("phenoSpec")) {
   			       			String pheno = splitComment[spl].split("=")[1];
   			       			
   			       			if (specificPhenotypes[p].equals(pheno)) {
   			       				int start = Integer.parseInt(splitSnp[3]);
   			       				int end = Integer.parseInt(splitSnp[4]);
   			       				for (int pos=start; pos<=end; pos++) {
			       					mask[Character.getNumericValue(splitSnp[0].charAt(3)) - 1].set(pos);
			       				}
   			       			}
   			       		}
   			       	}
   			    }
   		    	
   		    	
   		    	
   		    	
   		    	int cardQtl = 0;
   		    	int cardXpClr = 0;
   		    	int overlapXpClr = 0;
   		    	int genome = 0;
   		    	
   		    	BitSet[] maskAnd = new BitSet[5];
   				
   		    	for (int c=0; c<mask.length; c++) {
   		    		maskAnd[c] = new BitSet();
   		    		
   		    		cardQtl = cardQtl + mask[c].cardinality();
   					cardXpClr = cardXpClr + maskXpClr[c].cardinality();
   					genome = genome + chrL[c];
   					
   					maskAnd[c].or(mask[c]);
   					maskAnd[c].and(maskXpClr[c]);
   					overlapXpClr = overlapXpClr + maskAnd[c].cardinality(); 
   				}
   		    	
   		    	// Now calculate overlap and enrichment
   		    	double expectOverlap = ((double)cardQtl/(double)genome)*((double)cardXpClr/(double)genome)*(double)genome;
   		    	
   		    	// System.out.print(specificPhenotypes[p] + "\t" + cardQtl + "\t" + cardXpClr + "\t" + expectOverlap + "\t" + overlapXpClr + "\t" + (double)overlapXpClr/(double)expectOverlap + "\n");
   		    	out.print(specificPhenotypes[p] + "\t" + expectOverlap + "\t" + overlapXpClr + "\t" + (double)overlapXpClr/(double)expectOverlap + "\n");
   		    }

		    */
   			out.close();
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Qtl_overlapSelectionScans qtl_overlapSelectionScans = new Qtl_overlapSelectionScans();
		// qtl_overlapSelectionScans.setFileToSubSet("/home/fulgione/qtls_threeMerged.gff", args[0], args[1]);
		qtl_overlapSelectionScans.setFileToSubSet("/srv/biodata/dep_coupland/grp_hancock/andrea/backup_qtls_fromCelia_sortedPheno_allTablesMerged.gff_overlapOut.txt_2019-11-26_overlapOut.txt_doublesOut.txt", args[0], args[1], args[2]);
	}
}
