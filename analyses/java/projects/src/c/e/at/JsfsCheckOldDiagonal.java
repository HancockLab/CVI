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

public class JsfsCheckOldDiagonal {
	private File file=null;
	private File fileNames=null;
	private Boolean foldUnfold=null;
//	private Boolean region = null;
	private int numSNPs =0;
	private int numReps =0;
	private String[] lineElements = null;
	private String accessions = null;
	private char[] charLE = null;
	private String[] lineElementsFalse = null;
	private ArrayList<char[]> ArrayLines = new ArrayList<char[]>();
	private String[] AccessionsNames =null;
	private int countLineComment =0;
	private int numLines = 0;
	private int posA1 = 0;
	private int posA2 = 0;
	private String line = null;
	private String cov = null;
	private int[] samples = null;
	private int nA1=0;
	private int nA2=0;
	private int sum = 0;
	private int[][] arrNA1=null;
	private int[][] arrNA2=null;
	private String out=null;
	private int numCol=0;
	private int demeCount = 0;
	private int y=0;
	private int indexCVI=0;
	private int indexCOL=0;
	File folder=null;
	File[] listOfFiles=null;
	File[] listOfFolders=null;
	File chromosomeFile=null;
	private int[] bases = null;
	private int numBasesOverlap=0;
	private int numBasesNordborgOnly=0;
	private int numDiffOverlapNordVsCol = 0;
	private int numDiffNordborgOnly = 0;
	private int[] numBasesPerWind = null;
	private int numSnpsPerWind = 0;
	private char baseBefore;
	private double propDiffPerBases[] = null;
	private double propDiffPerSNPs[] = null;
	private ArrayList<int[]> totNumDiff = new ArrayList<int[]>();
	private ArrayList<int[]> totNumBasesPerWind = new ArrayList<int[]>();
	private ArrayList<Integer> totNumSnpsPerWind = new ArrayList<Integer>();
	private ArrayList<double[]> totPropDiffPerBases = new ArrayList<double[]>();
	private ArrayList<double[]> totPropDiffPerSNPs = new ArrayList<double[]>();
	private ArrayList<BitSet[]> TotalMaskCvi = new ArrayList<BitSet[]>();
	private BitSet[] privateChromosome = null;
	
	private File fileMask=null;
	private BitSet[] maskChromosome = null;
	private BitSet[] maskCvi = null;
	private BitSet[] maskChromosomeNordborgControl = null;
	private BitSet[] maskChromosomeOverlap = null;
	private BitSet[] NordOverlapSNPsVsCol = null;
	private BitSet[] Cvi0SalkOverlapSNPsVsCol = null;
	
	private BitSet[] maskPairwise = null;
	private BitSet[] maskSNPsOnlyNGS = null;
	private BitSet[] maskSNPsNGSandN05 = null;


	private int pos=0;
	
	
	
	
	private int[] perRegionNordDiffCol1Chr = null;
	private int[] perRegionCVIDiffCol1Chr = null;
	private int[] perRegionBP1Chr = null;
	private Double[] coverageArray = null;
	
	
	private ArrayList<int[]> perRegionNordDiffColAllChr = new ArrayList<int[]>();
	private ArrayList<int[]> perRegionCVIDiffColAllChr = new ArrayList<int[]>();
	private ArrayList<int[]> perRegionBPAllChr = new ArrayList<int[]>();
	private ArrayList<Double[]> coverageArrayAllChr = new ArrayList<Double[]>();
	
	private BitSet maskRegion = null;
	Writer writer = null;

	
	

	
	// From bam.java... If they mess up, me idiot
	
	int numFiles=0;
	String fileName=null;
	String extension="";
	File[] listOfSNPFiles=null;
	int numSNPFiles=0;
	String snp1=null;
	String snp2=null;
	private String[] splitSnp1 = null;
	private String[] splitSnp2 = null;
	int pairDiff=0;
	int totSnps=0;
	int s=0;
	int lines=0;
	String[] chr1 =null;
	String[] chr2 =null;
	private int line1=0;
	private int line2=0;
	private int position=0;
	File[] listOfFilesNordborg = null;
	private String[] splitCoverage = null;
	private String nameCoverage = null;

	private String[] splitCov = null;
	int posCov = 0;
	private double doubleCov = 0.0;
	Double sumCovRegion = 0.0;
	
	private int maskInt = 0;
	private String[] splitMask = null;
	
	private ArrayList<int[]> maskFull = new ArrayList<int[]>();
	private BitSet[] mask = null;
	private BitSet[] combinedMask = null;
	private String lineMask=null;

	private BitSet[] maskSNPa = null;
	private BitSet[] maskSNPb = null;
	private BitSet[] maskAnd = null;
	private BitSet[] maskOr = null;
	private BitSet[] maskTheta = null;
	private BitSet[] maskXor = null;
	private BitSet[] maskCombinedFiltered = null;
	
	private int sumDiff =0;
	private int numPairs =0;
	private Double averagePair =0.0;
	
	private char baseA;
	private char baseB;
	
	private ArrayList<Character> basesA = new ArrayList<Character>();
	private ArrayList<Character> basesB = new ArrayList<Character>();
	
	private Double[] differences = null;
	private ArrayList<Double[]> differencesMatrix = new ArrayList<Double[]>();
	
	private int totalBP = 0;
	private int totalSNP =0;
	
	double a1 =0.0;
	double a2 =0.0;
	
	private BitSet[] meraviglia = null;
	private int numSnps =0;
	private int[] checkChr = null;
	private int[] positions = null;
	private int accession =0;
	private String whereWe = null;

	private BitSet[] maskLyrata = null;
	private char[][] lyrataChar = null;
	private int[][] lyrataPos = null;
	private File folderLyrata = null; 
	private File[] listOfLyrataFiles=null;
	private File lyrataFile = null;
	private Scanner scanLyr = null;
	
	private BitSet[] diagonalMask = null;
	
	
	private JsfsCheckOldDiagonal() {};

	public void setSnpFile(String filename){
		
		
		try {
			
			System.out.println("Reading Lyrata... ");
			
			maskLyrata=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				maskLyrata[c]= new BitSet();
			}


			
			// Read Lyrata to polarize
			
			folderLyrata = new File("/home/CIBIV/andreaf/lyrata_refseq"); // /home/CIBIV/andreaf/lyrata_refseq
			listOfLyrataFiles = folderLyrata.listFiles();
			numFiles = listOfLyrataFiles.length;
			
			// for (int f=0; f<listOfFiles.length; f++) {
				// System.out.println("listOfFiles[f]: " + listOfFiles[f]);
			// }
			
			
			
			for (int c=0; c<5; c++) {
				
				lyrataFile = listOfLyrataFiles[c];
				scanLyr = new Scanner(lyrataFile);
				
				while ( scanLyr.hasNextLine() ) {
					
					snp1 = scanLyr.nextLine();
		        	splitSnp1 = snp1.split(" ");
		        	
			        if (splitSnp1[1].charAt(0) != 'N') {
		        		
			        	int posSNP = Integer.parseInt(splitSnp1[0]);
			        	maskLyrata[c].set(posSNP);
			        }
				}
			}
			
			
			int genomeLyrata = 0; 
			
			for (int c=0; c<5; c++) {
				genomeLyrata = genomeLyrata + maskLyrata[c].cardinality();
				System.out.println("maskLyrata[c].cardinality(): " + maskLyrata[c].cardinality());
			}
			System.out.println("genomeLyrata: " + genomeLyrata);
			
			
			
			
			
			lyrataChar = new char[5][];
			lyrataPos = new int[5][];
			
			for (int l=0; l<5; l++) {
				
				lyrataChar[l] = new char[maskLyrata[l].cardinality()];
				lyrataPos[l] = new int[maskLyrata[l].cardinality()];
			}
			
			// System.out.println(Arrays.deepToString(lyrata));

			
			// Now specify bases, positions:
			
			
			for (int c=0; c<5; c++) {
				
				int snps =0;
				
				lyrataFile = listOfLyrataFiles[c];
				scanLyr = new Scanner(lyrataFile);
				
				while ( scanLyr.hasNextLine() ) {
					
					snp1 = scanLyr.nextLine();
		        	splitSnp1 = snp1.split(" ");
		        	
			        if (splitSnp1[1].charAt(0) != 'N') {
		        		
			        	int posSNP = Integer.parseInt(splitSnp1[0]);
			        	char base = splitSnp1[1].charAt(0);
			        	
			        	lyrataChar[c][snps] = base;
			        	lyrataPos[c][snps] = posSNP;
			        	
			        	snps = snps +1;
			        	
			        }
				}
			}
			
			
			for (int l=0; l<5; l++) {
				for (int a =0; a<15; a++) {
					System.out.println(lyrataChar[l][a]);
					System.out.println(lyrataPos[l][a]);
				}
			}
			
			
			System.out.println("We got lyrata!!");
			
			






			accession = 2; // 
			// accession = Integer.parseInt(filename); // 

			if (accession == 1) {
				for (int c=0; c<5; c++) {
					for (int s=0; s<lyrataChar[c].length; s++) {
						if ( (lyrataChar[c][s] != 'A') && (lyrataChar[c][s] != 'T') && (lyrataChar[c][s] != 'C') && (lyrataChar[c][s] != 'G') ) {
							System.out.println("Danger! " + lyrataChar[c][s]);
						}
					}
				}
				System.out.println("Lyrata is ok! ");
			}

			whereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/janeiro/polarMatrCVIonly/";
			
					// /Volumes/Aner/cvi_data/data/newCallsOldSet_checkJsfs/";	// "/home/CIBIV/andreaf/canaries/rawData/analyses/janeiro/polarMatrCVIonly/";
			
			// /Volumes/Aner/cvi_data/data/analyses/polarizedMatrixMoroccoLaneDec/
			
			// /home/CIBIV/andreaf/canaries/rawData/analyses/PCA_ADM/analyses/polarized_matrix
			// /Volumes/Aner/cvi_data/data/analyses/polarizedMatrixMorocco/
			// /home/CIBIV/andreaf/canaries/rawData/analyses/PCA_ADM/";
			// /Volumes/Aner/cvi_data/data/analyses/polarizedMatrix/
			
			mask =new BitSet[5];
			combinedMask =new BitSet[5];
			maskOr=new BitSet[5];
			maskAnd=new BitSet[5];
			maskXor=new BitSet[5];
			diagonalMask=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
				combinedMask[c]= new BitSet();
				maskOr[c]= new BitSet();
				maskAnd[c]= new BitSet();
				maskXor[c]= new BitSet();
				diagonalMask[c]= new BitSet();
			}
			
			
			
			
			
			
			
			// Charge the combined mask
			
			fileMask = new File(whereWe + "masks/combinedMask.txt");
			
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
	        
	        for (int c=0; c<5; c++) {
	        	combinedMask[c].and(maskLyrata[c]);
	        }

	        
	        int genome =0;
	        
	        for (int c=0; c<5; c++) {
	        	genome = genome + combinedMask[c].cardinality();
	        	// System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("genome: " + genome);
			
			
			
			

			// ONLY DFOR JSFS CHECK		
				
			// Charge the DIAGONAL mask
			
			File fileDiagonal = new File(whereWe + "masks/reference_mask_diagonal_sfs_withCombinedMask.txt");
			
			Scanner scannerDiag = new Scanner(fileDiagonal);
        	
			chrCVI=0;
        	lineMask=null;
        	
	        for (int m=0; m<10; m++) {
	        	lineMask = scannerDiag.nextLine();
	        	
	        	if (m%2 == 1) {
		        	
		        	for (int i=1; i<lineMask.length(); i++) {
		        		if (lineMask.charAt(i) == ('1')) {
		        			diagonalMask[chrCVI].set(i);
		        		}
		        	}
		        	chrCVI=chrCVI+1;
	        	}
	        }

	        int diagonal =0;
	        
	        for (int c=0; c<5; c++) {
	        	diagonal = diagonal + diagonalMask[c].cardinality();
	        	// System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("diagonal: " + diagonal);
			
        	
        	
	        for (int c=0; c<5; c++) {
	        	combinedMask[c].and(diagonalMask[c]);
	        }


	        int combinedDiagonal =0;
	        
	        for (int c=0; c<5; c++) {
	        	combinedDiagonal = combinedDiagonal + combinedMask[c].cardinality();
	        	// System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("combinedDiagonal: " + combinedDiagonal);
			
			
			// END
			
			
        	
        	
        	
        	
        	
        	
        	
			
			
			
        	// Dajje coooi nosstri CVIs!!
			
			folder = new File(whereWe + "snp2/");
			listOfFiles = folder.listFiles();
			numFiles = listOfFiles.length;
			
			System.out.println("numFiles: " + numFiles);
			// System.out.println("listOfFiles[1]: " + listOfFiles[1]);
			
			
			// Take all files, look for .SNP2
        	
			for (int f=0; f<numFiles; f++) {
				int i=0;
				fileName = listOfFiles[f].getName();
				i = fileName.lastIndexOf('.');
				if (i > 0) {
				    extension = fileName.substring(i+1);
				}
				if (extension.equals("snp2")) {
					numSNPFiles = numSNPFiles + 1;
				}
			}
			
			
			listOfSNPFiles = new File[numFiles]; // numSNPFiles	
			
			// System.out.println("numSNPFiles: " + numSNPFiles);

			
			// Take all files, create subset of SNP2 files
			
			s=0;
			for (int f=0; f<numFiles; f++) {
				int i=0;
				fileName = listOfFiles[f].getName();
				i = fileName.lastIndexOf('.');
				if (i > 0) {
				    extension = fileName.substring(i+1);
				}
				if (extension.equals("snp2")) {
					listOfSNPFiles[s] = listOfFiles[f];
					s=s+1;
				}
			}
        	
        	
        	for (int r=0; r<numFiles; r++) {
				listOfSNPFiles[r] = listOfFiles[r];
			}
			
			for (int z=0; z<listOfSNPFiles.length; z++) {
				System.out.println("listOfSNPFiles" + z + " is: " + listOfSNPFiles[z] );
			}
			
			
			
			
			// We got file names!!!
			
			
			
			
			
			
			// Now build a mask of segregating sites - maskXor
			
			for (int f=0; f<listOfSNPFiles.length; f++) {
				
				System.out.println("File numero.... " + f);
				
				for (int c=0; c<5; c++) {
					mask[c]= new BitSet();
				}
				
				File snpFile = listOfSNPFiles[f];
				
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
			
			
			
			
			for (int c=0; c<5; c++) {
				numSnps = numSnps + maskXor[c].cardinality();
				System.out.println("maskXor[" + c + "].cardinality(): " + maskXor[c].cardinality());
			}
			System.out.println("numSnps: " + numSnps);
			
			/*			
			
			checkChr = new int[numSnps];
			int count = 0;
			
			for (int c=0; c<5; c++) {
				for (int pos =0; pos < maskXor[c].cardinality(); pos++) {
					checkChr[count] = (c+1);
					count = count+1;
				}
			}
			
			// System.out.println("checkChr: " + Arrays.toString(checkChr));
			System.out.println("checkChr!");
			
			
			
			
			positions = new int[numSnps];
			count =0;
			
			for (int c=0; c<5; c++) {
				for (int pos =0; pos < maskXor[c].length(); pos++) {
					if (maskXor[c].get(pos)) {
						
						positions[count] = pos;
						count = count +1;
					}
				}
			}
			
			
			// System.out.println("positions: " + Arrays.toString(positions));
			System.out.println("positions!");
			
			
			
			// Write out parameters only once.....
			
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

				outP.println(Arrays.toString(listOfSNPFiles));
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
				
				for (int name=0; name<listOfSNPFiles.length; name++) {
					outN.print(listOfSNPFiles[name]);
					outN.print("\n");				
				}
				outN.close();
			}
			
*/

			
			
			
			// Now fill the matrix Meraviglia!!!
			
			
			
			
			
			
			
			
			File folderAll = new File(whereWe + "all/");
			File[] listOfMpileupAll = folderAll.listFiles();
			int numFilesMpileupAll = listOfMpileupAll.length;
			
			for (int z=0; z<listOfMpileupAll.length; z++) {
				System.out.println("listOfMpileupAll" + z + " is: " + listOfMpileupAll[z] );
			}
			
			
			

			meraviglia = new BitSet[listOfMpileupAll.length];
			
			for (int plants=0; plants<listOfMpileupAll.length; plants++) {
				meraviglia[plants] = new BitSet(combinedDiagonal);
			}
			
			
			for (int f=0; f<listOfMpileupAll.length; f++) { // accession+1 // listOfMpileupAll.length
				
				
				System.out.println("File numero.... " + f);
				
				File snpFile = listOfMpileupAll[f];
				
				Scanner scannerCvi = new Scanner(snpFile);
				
				while ( scannerCvi.hasNextLine() ) {
					
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        		
	        		if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
		        	
	        			int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        		int posSNP = Integer.parseInt(splitSnp1[1]);
		        	
		        		if (combinedMask[chr-1].get(posSNP)) { // combinedMask??????
		        			

				        	// Use indexBig to put in one dimension 5 chromosomes, in a row. 
				        	// IndexBig is for every chromosome, the first position in a bitset pertaining to such chromosome
				        	
		        			int indexBig = 0;
			        		
		        			if (chr != 1) {
			        			for (int c=0; c < (chr-1); c++) {
					        		indexBig = indexBig + combinedMask[c].cardinality();
			        			}
			       		 	}
			        		
			        		int index = indexBig;
		
					        	
				        	// index, on the other hand, runs within a chromosome, in a bitset made of all chromosomes in a row
				        		
				        	for (int p=indexBig; p< (indexBig + posSNP); p++) {
				        		if (combinedMask[chr-1].get(p)) {
				        				
				        			index = index+1;
							        // System.out.println("index: " + index);
				        		}
				        	}
			        		
			        		
			        		/*
			        		
			        		// Check!
		        			if (positions[index] == posSNP) {
		        				System.out.println("yeaaaa");
		        			}
		        			if (positions[index] != posSNP) {
		        				System.out.println("Danger at:" + index + " "+ positions[index] + " " + posSNP);
		        			}
		        			// Check end
		        			
		        			
		        			if (chr == 2) {
		        				System.out.println("Gotcha!");
		        				break;
		        			}
		        			
			        		*/
		
		
							char base = splitSnp1[6].charAt(0);
					        		
				        	int indexx =0;
				        		
				        	for (int p=0; p<posSNP; p++) {
				        		if (maskLyrata[chr-1].get(p)) {
				        			indexx = indexx+1;
				        		}
				        	}




				        	if ( base != lyrataChar[chr-1][indexx] ) {
				        		meraviglia[f].set(index);
				        	}
		        			
		        		}
	        		}
				}
			
			// System.out.println(meraviglia[f]);
				
		}
		
		// for (int plant=0; plant<meraviglia.length; plant++) {
			
		// System.out.println("meraviglia[" + accession + "].cardinality() : " + meraviglia[accession].cardinality());
		
		// }
			
		
			
			
			

		System.out.println("Writing...");
		

		Writer writer = new FileWriter(whereWe + "resultscheckJSFS/" + "polarizedDiagonal_2.txt");
		PrintWriter out = new PrintWriter(writer);
		
		
		for (int f=0; f<listOfMpileupAll.length; f++) {
			

			for (int bit =0; bit< combinedDiagonal; bit++) {
				if (meraviglia[f].get(bit)) {
					out.print("1");
					// out.print("\t");
				}
				if (!meraviglia[f].get(bit)) {
					out.print("0");
					// out.print("\t");
				}
			}
			
			out.print("\n");
			
		}
		
		
		out.close();
		
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		JsfsCheckOldDiagonal jsfsCheckOldDiagonal = new JsfsCheckOldDiagonal();
		jsfsCheckOldDiagonal.setSnpFile(""); // args[0]
	}
}








/*

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

import com.esotericsoftware.yamlbeans.YamlWriter;

public class JsfsCheckOldDiagonal {
	
	private File file=null;
	private File fileNames=null;
	private Boolean foldUnfold=null;
//	private Boolean region = null;
	private int numSNPs =0;
	private int numReps =0;
	private String[] lineElements = null;
	private String accessions = null;
	private char[] charLE = null;
	private String[] lineElementsFalse = null;
	private ArrayList<char[]> ArrayLines = new ArrayList<char[]>();
	private String[] AccessionsNames =null;
	private int countLineComment =0;
	private int numLines = 0;
	private int posA1 = 0;
	private int posA2 = 0;
	private String line = null;
	private String cov = null;
	private int[] samples = null;
	private int nA1=0;
	private int nA2=0;
	private int sum = 0;
	private int[][] arrNA1=null;
	private int[][] arrNA2=null;
	private String out=null;
	private int numCol=0;
	private int demeCount = 0;
	private int y=0;
	private int indexCVI=0;
	private int indexCOL=0;
	File folder=null;
	File[] listOfFiles=null;
	File[] listOfFolders=null;
	File chromosomeFile=null;
	private int[] bases = null;
	private int numBasesOverlap=0;
	private int numBasesNordborgOnly=0;
	private int numDiffOverlapNordVsCol = 0;
	private int numDiffNordborgOnly = 0;
	private int[] numBasesPerWind = null;
	private int numSnpsPerWind = 0;
	private char baseBefore;
	private double propDiffPerBases[] = null;
	private double propDiffPerSNPs[] = null;
	private ArrayList<int[]> totNumDiff = new ArrayList<int[]>();
	private ArrayList<int[]> totNumBasesPerWind = new ArrayList<int[]>();
	private ArrayList<Integer> totNumSnpsPerWind = new ArrayList<Integer>();
	private ArrayList<double[]> totPropDiffPerBases = new ArrayList<double[]>();
	private ArrayList<double[]> totPropDiffPerSNPs = new ArrayList<double[]>();
	private ArrayList<BitSet[]> TotalMaskCvi = new ArrayList<BitSet[]>();
	private BitSet[] privateChromosome = null;
	
	private File fileMask=null;
	private BitSet[] maskChromosome = null;
	private BitSet[] maskCvi = null;
	private BitSet[] maskChromosomeNordborgControl = null;
	private BitSet[] maskChromosomeOverlap = null;
	private BitSet[] NordOverlapSNPsVsCol = null;
	private BitSet[] Cvi0SalkOverlapSNPsVsCol = null;
	
	private BitSet[] maskPairwise = null;
	private BitSet[] maskSNPsOnlyNGS = null;
	private BitSet[] maskSNPsNGSandN05 = null;


	private int pos=0;
	private ArrayList<Integer> positions = new ArrayList<Integer>();
	
	
	
	
	private int[] perRegionNordDiffCol1Chr = null;
	private int[] perRegionCVIDiffCol1Chr = null;
	private int[] perRegionBP1Chr = null;
	private Double[] coverageArray = null;
	
	
	private ArrayList<int[]> perRegionNordDiffColAllChr = new ArrayList<int[]>();
	private ArrayList<int[]> perRegionCVIDiffColAllChr = new ArrayList<int[]>();
	private ArrayList<int[]> perRegionBPAllChr = new ArrayList<int[]>();
	private ArrayList<Double[]> coverageArrayAllChr = new ArrayList<Double[]>();
	
	private BitSet maskRegion = null;
	Writer writer = null;

	
	

	
	// From bam.java... If they mess up, me idiot
	
	int numFiles=0;
	String fileName=null;
	String extension="";
	File[] listOfSNPFiles=null;
	int numSNPFiles=0;
	String snp1=null;
	String snp2=null;
	private String[] splitSnp1 = null;
	private String[] splitSnp2 = null;
	int pairDiff=0;
	int totSnps=0;
	int s=0;
	int lines=0;
	String[] chr1 =null;
	String[] chr2 =null;
	private int line1=0;
	private int line2=0;
	private int position=0;
	File[] listOfFilesNordborg = null;
	private String[] splitCoverage = null;
	private String nameCoverage = null;

	private String[] splitCov = null;
	int posCov = 0;
	private double doubleCov = 0.0;
	Double sumCovRegion = 0.0;
	
	private int maskInt = 0;
	private String[] splitMask = null;
	
	private ArrayList<int[]> maskFull = new ArrayList<int[]>();
	private BitSet[] mask = null;
	private String lineMask=null;

	private BitSet[] maskSNPa = null;
	private BitSet[] maskSNPb = null;
	private BitSet[] maskAnd = null;
	private BitSet[] maskOr = null;
	private BitSet[] maskTheta = null;
	private BitSet[] maskXor = null;
	private BitSet[] maskCombinedFiltered = null;
	
	private int sumDiff =0;
	private int numPairs =0;
	private Double averagePair =0.0;
	
	private char baseA;
	private char baseB;
	
	private ArrayList<Character> basesA = new ArrayList<Character>();
	private ArrayList<Character> basesB = new ArrayList<Character>();
	
	private Double[] differences = null;
	private ArrayList<Double[]> differencesMatrix = new ArrayList<Double[]>();
	
	private int totalBP = 0;
	private int totalSNP =0;
	
	double a1 =0.0;
	double a2 =0.0;
	
	File[] listOfFO=null;
	File[] listOfSA=null;
	
	int numFogos =0;
	int numSantos =0;

	
	private BitSet[][] superMaskFO = null;
	private BitSet[][] superMaskSA = null;
	
	Double[][] FST = null;
	int[][] Positions = null;
	
	Double s2 = 0.0;
	private int SNP =0;
	
	private int[] sfsFO = null;
	private int[] sfsSA = null;
	private int[][] sfsJoint = null;
	
	private BitSet[] maskLyrata = null;
	private BitSet[] maskDiagonal = null;
	private BitSet[] maskDerivedSA = null;
	private BitSet[] maskDerivedFO = null;
	private BitSet[] maskHighDerivedSA = null;
	private BitSet[] maskHighDerivedFO = null;
	private BitSet[] diagonalMask = null;
	
	private BitSet[] combinedMask = null;
	private char[][] lyrataChar = null;
	private int[][] lyrataPos = null;
	private int checkChr =0;
	private int posSNP = 0;
	private File lyrataFile = null;
	private Scanner scanLyr = null;
	
	private char[][] base1diagonal = null;
	private char[][] base2diagonal = null;
	private int[][] posDiagonal = null;
	
	
	
	public JsfsCheckOldDiagonal() {}
	
	
	public void setSnpFile(String filename){
		
		try {
			
			
			
			
			
			
			// FROM Sfs_lyrata_modified_reference.java until // END
			
			

			int[] lines = new int[5];
			
			for (int l=0; l<5; l++) {
				lines[l] =0;
			}
			
			
			
			int chromosome =0;
			
			File indexCvi0 = new File("/Volumes/Aner/dataAugust/Index_CVI_0_coll1.dat");
			
			Scanner scannerRef = new Scanner(indexCvi0);
			
			while ( scannerRef.hasNextLine() ) { 
				
				line = scannerRef.nextLine();
				if ( line.charAt(0) == '>') {
					chromosome = chromosome + 1;
				}
				
				if ( line.charAt(0) != '>') {
					lines[chromosome - 1] = lines[chromosome -1] +1;
	        	}
			}
			
			System.out.println("lines[]: " + Arrays.toString(lines));
			
			
			
			int[][] refConverter = new int[5][];
			
			for (int l=0; l<5; l++) {
				refConverter[l] = new int[lines[l]];
			}
			
			chromosome =0;
			int countPos = 0;
			
			scannerRef = new Scanner(indexCvi0);
			
			while ( scannerRef.hasNextLine() ) { 
				
				line = scannerRef.nextLine();
				if ( line.charAt(0) == '>') {
					chromosome = chromosome + 1;
					countPos = 0;
				}
				
				if ( line.charAt(0) != '>') {
					if (line.charAt(0) != '-') {
		        		pos = Integer.parseInt(line);
					}
					if (line.charAt(0) == '-') {
						pos =0;
					}
					
					refConverter[chromosome-1][countPos] = pos;
					countPos = countPos+1;
	        	}
			}
			
			
			
			
			
			
			// END

			maskOr=new BitSet[5];
			maskAnd=new BitSet[5];
			maskXor=new BitSet[5];
			maskLyrata=new BitSet[5];
			maskDiagonal=new BitSet[5];
			combinedMask=new BitSet[5];
			maskDerivedSA=new BitSet[5];
			maskDerivedFO=new BitSet[5];
			maskHighDerivedSA=new BitSet[5];
			maskHighDerivedFO=new BitSet[5];
			diagonalMask=new BitSet[5];
			
			superMaskFO = new BitSet[19][5];
			superMaskSA = new BitSet[21][5];
			
			for (int c=0; c<5; c++) {
				maskOr[c]= new BitSet();
				maskAnd[c]= new BitSet();
				maskXor[c]= new BitSet();
				maskLyrata[c]= new BitSet();
				maskDiagonal[c]= new BitSet();
				combinedMask[c]= new BitSet();
				maskDerivedSA[c]= new BitSet();
				maskDerivedFO[c]= new BitSet();
				maskHighDerivedSA[c]= new BitSet();
				maskHighDerivedFO[c]= new BitSet();
				diagonalMask[c]= new BitSet();
			}
			
			
			
			
			// Read Lyrata to polarize
			
			folder = new File("/Volumes/Aner/dataAugust/lyrata_refseq"); // /home/CIBIV/andreaf/lyrata_refseq
			listOfFiles = folder.listFiles();
			numFiles = listOfFiles.length;
			
			// for (int f=0; f<listOfFiles.length; f++) {
				// System.out.println("listOfFiles[f]: " + listOfFiles[f]);
			// }
			
			
			
			for (int c=0; c<5; c++) {
				
				lyrataFile = listOfFiles[c];
				scanLyr = new Scanner(lyrataFile);
				
				while ( scanLyr.hasNextLine() ) {
					
					snp1 = scanLyr.nextLine();
		        	splitSnp1 = snp1.split(" ");
		        	
			        if (splitSnp1[1].charAt(0) != 'N') {
		        		
			        	posSNP = Integer.parseInt(splitSnp1[0]);
			        	maskLyrata[c].set(posSNP);
			        }
				}
			}
			
			for (int c=0; c<5; c++) {
				System.out.println("maskLyrata[c].cardinality(): " + maskLyrata[c].cardinality());
			}
			
			
			
			
			
			
			lyrataChar = new char[5][];
			lyrataPos = new int[5][];
			
			for (int l=0; l<5; l++) {
				
				lyrataChar[l] = new char[maskLyrata[l].cardinality()];
				lyrataPos[l] = new int[maskLyrata[l].cardinality()];
			}
			
			// System.out.println(Arrays.deepToString(lyrata));

			
			// Now specify bases, positions:
			
			
			for (int c=0; c<5; c++) {
				
				int snps =0;
				
				lyrataFile = listOfFiles[c];
				scanLyr = new Scanner(lyrataFile);
				
				while ( scanLyr.hasNextLine() ) {
					
					snp1 = scanLyr.nextLine();
		        	splitSnp1 = snp1.split(" ");
		        	
			        if (splitSnp1[1].charAt(0) != 'N') {
		        		
			        	posSNP = Integer.parseInt(splitSnp1[0]);
			        	char base = splitSnp1[1].charAt(0);
			        	
			        	lyrataChar[c][snps] = base;
			        	lyrataPos[c][snps] = posSNP;
			        	
			        	snps = snps +1;
			        	
			        }
				}
			}
			
			
			for (int l=0; l<5; l++) {
				for (int a =0; a<15; a++) {
					System.out.println(lyrataChar[l][a]);
					System.out.println(lyrataPos[l][a]);
				}
			}
			
			
			// We got lyrata!!
			
			
			
			
			
			

			// Charge the combined mask
			
			fileMask = new File("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/combinedMask.dat");
					// /Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_mask_diagonal_sfs_withCombinedMask.txt");
					// /Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/combinedMask.dat");
			
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
	        
	        for (int c=0; c<5; c++) {
	        	maskLyrata[c].and(combinedMask[c]);
	        }

	        
	        int genome =0;
	        
	        for (int c=0; c<5; c++) {
	        	genome = genome + combinedMask[c].cardinality();
	        	System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("genome: " + genome);
			
			
			
        	
        	
        	// Only to check diagonal
        	


			// Charge the combined mask
			
			fileMask = new File("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_mask_diagonal_sfs_withCombinedMask.txt");
					// /Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/combinedMask.dat");
			
			Scanner scannerMaskD = new Scanner(fileMask);
        	
			int chrCVID=0;
        	String lineMaskD=null;
        	
	        for (int m=0; m<10; m++) {
	        	lineMaskD = scannerMaskD.nextLine();
	        	
	        	if (m%2 == 1) {
		        	
		        	for (int i=1; i<lineMaskD.length(); i++) {
		        		if (lineMaskD.charAt(i) == ('1')) {
		        			diagonalMask[chrCVID].set(i);
		        		}
		        	}
		        	chrCVID=chrCVID+1;
	        	}
	        }
	        
	        int diagonal =0;
	        
	        for (int c=0; c<5; c++) {
	        	diagonal = diagonal + diagonalMask[c].cardinality();
	        	System.out.println("diagonalMask: " + diagonalMask[c].cardinality());
	        }
        	System.out.println("diagonal: " + diagonal);
			
        	
        	
        	
        	
        	
        	
        	
			char[][] base1diagonal = new char[5][];
			char[][] base2diagonal = new char[5][];
			int[][] posDiagonal = new int[5][];
						
			for (int c=0; c<5; c++) {
				
				base1diagonal[c] = new char[diagonalMask[c].cardinality()];
				base2diagonal[c] = new char[diagonalMask[c].cardinality()];
				posDiagonal[c] = new int[diagonalMask[c].cardinality()];
				
				int diag=0;
				
				for (int pos=0; pos<diagonalMask[c].length(); pos++) {
					if (diagonalMask[c].get(pos)) {
						
						posDiagonal[c][diag] = pos;
						base1diagonal[c][diag] = ' ';
						base2diagonal[c][diag] = ' ';
						
						diag = diag+1;
					}
				}
			}
			
			
			for (int c=0; c<5; c++) {
				System.out.println(Arrays.toString(posDiagonal[c]));
			}
			
			System.out.println("got posdiagonals!");
			
			

			
			// Dajje coooi nosstri CVIs!!
			
			folder = new File(filename);
			listOfFolders = folder.listFiles();
			numFiles = listOfFolders.length;
			
			File[] listOfFO = listOfFolders[0].listFiles();
			File[] listOfSA = listOfFolders[1].listFiles();
			
			numFogos = listOfFO.length;
			numSantos = listOfSA.length;
			
			System.out.println("numFogos: " + numFogos);
			System.out.println("numSantos: " + numSantos);
			
			for (int p=0; p<numFogos; p++) {
				for (int c=0; c<5; c++) {
					superMaskFO[p][c] = new BitSet();
				}
			}
			
			for (int p=0; p<numSantos; p++) {
				for (int c=0; c<5; c++) {
					superMaskSA[p][c] = new BitSet();
				}
			}
			
			
			

			int r = 2;
			int[] n = new int[2];
			n[0] = numFogos;
			n[1] = numSantos;
			
			Double nMean = (double)(n[0] + n[1]) / n.length;
			
			
			
			
			
			
			
			

			
			// Build the super masks!!
			
			// The FOGO one...
			
			for (int f=0; f<numFogos; f++) {
				
				System.out.println("Fogo numero.... " + f);
				
				File snpFile = listOfFO[f];
				Scanner scannerFo = new Scanner(snpFile);
				
				while ( scannerFo.hasNextLine() ) {
					
					snp1 = scannerFo.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        	
		        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
			        	
		        		int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
			        	int posSNP = Integer.parseInt(splitSnp1[1]);
			        	
			        	
			        	
			        	
			        	
			        	
			        	int modifiedPos = refConverter[chr-1][posSNP];
			        	
			        	if (modifiedPos != 0) {
			        		
			        		if ( combinedMask[chr-1].get(modifiedPos) ) {
				        		
				        		char base = splitSnp1[4].charAt(0);
				        		
				        		int indexx =0;
				        		
				        		
				        		// Modified from sfs_lyrata_modified_reference
				        		
				        		for (int s=0; s<lyrataPos[chr-1].length; s++) {
				        			
				        			if ( lyrataPos[chr-1][s] == posSNP ) {
				        				indexx = s;
				        			}
				        		}
				        		// Until here
				        		
				        		
				        		
				        		if (diagonalMask[chr-1].get(posSNP)) {
				        			
					        		int diagonalIndex =0;
					        							        		
					        		for (int s=0; s<posDiagonal[chr-1].length; s++) {
					        			
					        			if ( posDiagonal[chr-1][s] == posSNP ) {
					        				diagonalIndex = s;
					        			}
					        		}
			        				base1diagonal[chr-1][diagonalIndex] = splitSnp1[3].charAt(0);
			        				base2diagonal[chr-1][diagonalIndex] = splitSnp1[4].charAt(0);
				        		}
				        		
				        		
				        		
				        		
				        		if ( (base != lyrataChar[chr-1][indexx]) && (diagonalMask[chr-1].get(posSNP)) ) { // no second statement
				        			
					        		superMaskFO[f][chr-1].set(posSNP);
				        		}
				        		
				        	}
			        	}
			        	
			        	
			        	
			        	
			        	
			        	if ( maskLyrata[chr-1].get(posSNP) ) {
			        		
			        		char base = splitSnp1[4].charAt(0);
			        		
			        		int indexx =0;
			        		
			        		for (int s=0; s<lyrataPos[chr-1].length; s++) {
			        			
			        			if ( lyrataPos[chr-1][s] == posSNP ) {
			        				indexx = s;
			        			}
			        		}
			        		
			        		if ( base != lyrataChar[chr-1][indexx] ) {
			        			
				        		superMaskFO[f][chr-1].set(posSNP);
			        		}
			        		
			        	}
			        	
			        }
				}
				
				
				
				
				
				// Accumulate new SNPs in maskOr and maskAnd
				
				
				
				for (int c=0; c<5; c++) {
					maskOr[c].or(superMaskFO[f][c]);
					if (f == 0) {
						maskAnd[c].or(superMaskFO[f][c]);
					}
					maskAnd[c].and(superMaskFO[f][c]);
				}
			}
			
			
			
			// The SANTOS one...
			
			for (int f=0; f<numSantos; f++) {
				
				System.out.println("Santo numero.... " + f);
				
				File snpFile = listOfSA[f];
				Scanner scannerSa = new Scanner(snpFile);
				
				while ( scannerSa.hasNextLine() ) {
					
					snp1 = scannerSa.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        	
		        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
			        	
		        		int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
			        	int posSNP = Integer.parseInt(splitSnp1[1]);
			        	
			        	
			        	
			        	//
			        	
			        	int modifiedPos = refConverter[chr-1][posSNP];
			        	
			        	if (modifiedPos != 0) {
			        		
			        		if ( maskLyrata[chr-1].get(modifiedPos) ) {
				        		
				        		char base = splitSnp1[4].charAt(0);
				        		
				        		
				        		int indexx =0;
				        		
				        		
				        		// Modified from sfs_lyrata_modified_reference
				        		
				        		for (int s=0; s<lyrataPos[chr-1].length; s++) {
				        			
				        			if ( lyrataPos[chr-1][s] == posSNP ) {
				        				indexx = s;
				        			}
				        		}
				        		// Until here
				        		
				        		
				        		
				        		

				        		if (diagonalMask[chr-1].get(posSNP)) {
				        			

					        		int diagonalIndex =0;
					        							        		
					        		for (int s=0; s<posDiagonal[chr-1].length; s++) {
					        			
					        			if ( posDiagonal[chr-1][s] == posSNP ) {
					        				diagonalIndex = s;
					        			}
					        		}
					        		
			        				base1diagonal[chr-1][diagonalIndex] = splitSnp1[3].charAt(0);
			        				base2diagonal[chr-1][diagonalIndex] = splitSnp1[4].charAt(0);
					        	}
				        		
				        		
				        		
				        		
				        		
				        		
				        		if ( (base != lyrataChar[chr-1][indexx]) && (diagonalMask[chr-1].get(posSNP)) ) { // no second statement 
				        			
					        		superMaskSA[f][chr-1].set(posSNP);
				        		}
				        	}
			        	}
			        	
			        	
			        	
			        	
			        	
			        	
			        	
			        	
			        	
			        	
			        	if ( maskLyrata[chr-1].get(posSNP) ) {
			        		
			        		char base = splitSnp1[3].charAt(0);
			        		
			        		int indexx =0;
			        		
			        		for (int s=0; s<lyrataPos[chr-1].length; s++) {
			        			
			        			if ( lyrataPos[chr-1][s] == posSNP ) {
			        				indexx = s;
			        			}
			        		}
			        		
			        		if ( base != lyrataChar[chr-1][indexx] ) {
			        			
				        		superMaskSA[f][chr-1].set(posSNP);
			        		}
			        		
			        	}
		        	}
				}
				
				// superMaskSA[f] = mask;
				
				
				// Accumulate new SNPs in maskOr and maskAnd
				
				for (int c=0; c<5; c++) {
					maskOr[c].or(superMaskSA[f][c]);
					maskAnd[c].and(superMaskSA[f][c]);
				}
			}
			
			
			
			
			
			// These are all segregating SNPs... maskXor
			
			for (int c=0; c<5; c++) {
				maskXor[c].or(diagonalMask[c]); // maskOr
				// maskXor[c].xor(maskAnd[c]);
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			// Calculate SFS now...
			
			sfsFO = new int[numFogos + 1];
			sfsSA = new int[numSantos + 1];
			sfsJoint = new int[numSantos + 1][numFogos + 1];
			
			
			for (int s=0; s<sfsFO.length; s++) {
				sfsFO[s] = 0;
			}
			
			for (int s=0; s<sfsSA.length; s++) {
				sfsSA[s] = 0;
			}
			
			for (int sa=0; sa<numSantos + 1; sa++) {
				for (int fo=0; fo<numFogos + 1; fo++) {
					sfsJoint[sa][fo] = 0;
				}
			}
			
			
			
			
			// Per Chromosome c
			
			for (int c=0; c<5; c++) {
				
				SNP =0;
				
				
				// Per SNP on chromosomes
				
				for (int pos =0; pos < maskXor[c].length(); pos++) { // maskXor[c].cardinality()
					
					if (maskXor[c].get(pos)) {
						
						Positions[c][SNP] = pos;
						
						
						
						// Get subPop frequencies FoP, SaP, and Meta frequency MetaP
						
						// FoP
						int numFo =0; 
						for (int plantsFO=0; plantsFO < numFogos; plantsFO++) {
							
							// System.out.println("plantsFO: " + plantsFO);
							// System.out.println("c: " + c);
							// System.out.println("pos: " + pos);
							
							if (superMaskFO[plantsFO][c].get(pos)) {
								numFo = numFo + 1;
							}
						}
						
						sfsFO[numFo] = sfsFO[numFo] + 1;
						
						
						
						Double FoP = (double)(numFo) / (double)(numFogos);
						
						
						// SaP
						int numSa =0; 
						for (int plantsSA=0; plantsSA < numSantos; plantsSA++) {
							
							if (superMaskSA[plantsSA][c].get(pos)) {
								numSa = numSa + 1;
							}
						}
						
						sfsSA[numSa] = sfsSA[numSa] + 1;
						
						
						
						Double SaP = (double)(numSa) / (double)(numSantos);
						
						
						
						
						// MetaP
						
						sfsJoint[numSa][numFo] = sfsJoint[numSa][numFo] + 1;
						
						Double MetaP = (double)(numFo + numSa) / (double)(numFogos + numSantos);
						
						
						
						
						// MAsk out suspicious regions
						
						if ( (numSa != 0) && (numFo != 0) && (numSa != numSantos) && (numFo != numFogos) ) {
							maskDiagonal[c].set(pos);
						}
						
						if ( (numSa == numSantos) && (numFo != 0)  ) {
							maskDerivedFO[c].set(pos);
						}
						if ( (numSa != 0) && (numFo == numFogos)  ) {
							maskDerivedSA[c].set(pos);
						}
						
						if ( (numSa == numSantos) && (numFo == numFogos-1) ) {
							maskHighDerivedSA[c].set(pos);
						}
						if ( (numSa == numSantos-1) && (numFo == numFogos) ) {
							maskHighDerivedFO[c].set(pos);
						}
						
						
						
						
						
						
						
						
						
						
						// Calculate Fst per SNP
						
						Double[] pTil = new Double[2];
						pTil[0] = FoP;
						pTil[1] = SaP;
						
						s2 = 0.0;
						for (int i=0; i<n.length; i++) {
							
							s2 = s2 + (n[i] * (pTil[i]-MetaP)*(pTil[i]-MetaP) ) / (double)( (r) * nMean );
							
						}
						
						Double Fst = (double)s2 / (double)(MetaP * (1 - MetaP) );
						Double Fst2 = 1.0 - ( ( (FoP*(1-FoP)) + (SaP*(1-SaP)) ) / (MetaP*(1-MetaP)) );
						
						
						System.out.println("numFo: " + numFo);
						System.out.println("numSa: " + numSa);
						System.out.println("FoP: " + FoP);
						System.out.println("SaP: " + SaP);
						System.out.println("MetaP: " + MetaP);
						System.out.println("pos: " + pos);
						System.out.println("s2: " + s2);
						System.out.println("nMean: " + nMean);
						
						
						
						
						FST[c][SNP] = Fst;
						
						
						
						
						
						SNP = SNP+1;
					}
					
				}
			}
			
			int numDiagonal =0;
			
			for (int c=0; c<5; c++) {
				numDiagonal = numDiagonal + maskDiagonal[c].cardinality(); 
			}

			int[] positions = new int[numDiagonal];
			int count =0;
			
			for (int c=0; c<5; c++) {
				for (int pos =0; pos < maskDiagonal[c].length(); pos++) {
					if (maskDiagonal[c].get(pos)) {
						
						positions[count] = pos;
						count = count +1;
					}
				}
			}
			
			System.out.println("positions!");
			
			
			
			// Sfs results:
			
			System.out.println("sfsFO: " + Arrays.toString(sfsFO));
			System.out.println("sfsSA: " + Arrays.toString(sfsSA));
			System.out.println("sfsJoint: " + Arrays.deepToString(sfsJoint));
			
			
			for (int s=0; s<sfsJoint.length; s++) {
				System.out.println(Arrays.toString(sfsJoint[s]));
			}
			
			
			Writer writerBase = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/pos_bases_Diagonal.txt");
			PrintWriter outBase = new PrintWriter(writerBase);
			
			for (int c=0; c<5 ; c++) {
				for (int pos=0; pos< posDiagonal[c].length; pos++) {
					outBase.print(c+1);
					outBase.print("\t");
					outBase.print(posDiagonal[c][pos]);
					outBase.print("\t");
					outBase.print(refConverter[c][posDiagonal[c][pos]]);
					outBase.print("\t");
					outBase.print(base1diagonal[c][pos]);
					outBase.print("\t");
					outBase.print(base2diagonal[c][pos]);
					outBase.print("\n");
				}
			}
			
			outBase.close();
            
            
            
			

            
			
			Writer writer = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_sfs_withDiagonalMask.txt");
					// /Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_sfs_withCombinedMask.txt"); // .SaFoCviCanAreEu.txt
            PrintWriter out = new PrintWriter(writer);
            
            out.println("sfsFO: " + Arrays.toString(sfsFO));
            out.println("sfsSA: " + Arrays.toString(sfsSA));
            out.println("sfsJoint: " + Arrays.deepToString(sfsJoint));
            
            for (int s=0; s<sfsJoint.length; s++) {
				out.println(Arrays.toString(sfsJoint[s]));
			}
            out.close();
			
			
            
			
            
            // The DIAGONAL
            
            Writer writerMask = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_mask_diagonal_sfs_withCombinedMask.txt");
			PrintWriter outM = new PrintWriter(writerMask);
			
			for (int c=0; c<5; c++) {
				
				outM.print(">Chr" + (c+1));
				outM.print("\n");
				
				for (int bit =0; bit< maskDiagonal[c].length(); bit++) {
					if (maskDiagonal[c].get(bit)) {
						outM.print("1");
					}
					if (!maskDiagonal[c].get(bit)) {
						outM.print("0");
					}
				}
				outM.print("\n");
			}
			outM.close();
			
            
			Writer fineWriter = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_Diagonal_positions.txt");
			PrintWriter outFine = new PrintWriter(fineWriter);
						
			for (int pos = 0; pos < positions.length; pos++) {
				outFine.print(positions[pos]);
				outFine.print(" ");
			}
			outFine.print("\n");
			
			outFine.close();
			
			System.out.println("positions printed");
			
			
			
			
			
			System.out.println("PrintING EVERYTHING!");
			
			// 
			

            Writer writerHighSA = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_mask_HighSA_sfs_withCombinedMask.txt");
			PrintWriter outHS = new PrintWriter(writerHighSA);
			
			Writer writerHighSAPos = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_highSA_positions.txt");
			PrintWriter outHSP = new PrintWriter(writerHighSAPos);
			
			
			for (int c=0; c<5; c++) {
				
				outHS.print(">Chr" + (c+1));
				outHS.print("\n");
				
				outHSP.print(">Chr" + (c+1));
				outHSP.print("\n");
				
				for (int bit =0; bit< maskHighDerivedSA[c].length(); bit++) {
					if (maskHighDerivedSA[c].get(bit)) {
						outHSP.print(bit);
						outHSP.print(" ");
						outHS.print("1");
					}
					if (!maskHighDerivedSA[c].get(bit)) {
						outHS.print("0");
					}
				}
				outHS.print("\n");
				outHSP.print("\n");
			}
			outHS.close();
			outHSP.close();
            
			
			

            Writer writerHighFO = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_mask_HighFO_sfs_withCombinedMask.txt");
			PrintWriter outHF = new PrintWriter(writerHighFO);
			
			Writer writerHighFOPos = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_highFO_positions.txt");
			PrintWriter outHFP = new PrintWriter(writerHighFOPos);
			
			
			for (int c=0; c<5; c++) {
				
				outHF.print(">Chr" + (c+1));
				outHF.print("\n");
				
				outHFP.print(">Chr" + (c+1));
				outHFP.print("\n");
				
				for (int bit =0; bit< maskHighDerivedFO[c].length(); bit++) {
					if (maskHighDerivedFO[c].get(bit)) {
						outHFP.print(bit);
						outHFP.print(" ");
						outHF.print("1");
					}
					if (!maskHighDerivedFO[c].get(bit)) {
						outHF.print("0");
					}
				}
				outHF.print("\n");
				outHFP.print("\n");
			}
			outHF.close();
			outHFP.close();
            
			
			


            Writer writerDerSA = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_mask_DerivedSA_sfs_withCombinedMask.txt");
			PrintWriter outDSA = new PrintWriter(writerDerSA);
			
			Writer writerDerSAPos = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_DerivedSA_positions.txt");
			PrintWriter outDSAP = new PrintWriter(writerDerSAPos);
			
			
			for (int c=0; c<5; c++) {
				
				outDSA.print(">Chr" + (c+1));
				outDSA.print("\n");
				
				outDSAP.print(">Chr" + (c+1));
				outDSAP.print("\n");
				
				for (int bit =0; bit< maskDerivedSA[c].length(); bit++) {
					if (maskDerivedSA[c].get(bit)) {
						outDSAP.print(bit);
						outDSAP.print(" ");
						outDSA.print("1");
					}
					if (!maskDerivedSA[c].get(bit)) {
						outDSA.print("0");
					}
				}
				outDSA.print("\n");
				outDSAP.print("\n");
			}
			outDSA.close();
			outDSAP.close();
            
			
			


            Writer writerDerFO = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_mask_DerivedFO_sfs_withCombinedMask.txt");
			PrintWriter outDFA = new PrintWriter(writerDerFO);
			
			Writer writerDerFOPos = new FileWriter("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/results/modif_DerivedFO_positions.txt");
			PrintWriter outDFAP = new PrintWriter(writerDerFOPos);
			
			
			for (int c=0; c<5; c++) {
				
				outDFA.print(">Chr" + (c+1));
				outDFA.print("\n");
				
				outDFAP.print(">Chr" + (c+1));
				outDFAP.print("\n");
				
				for (int bit =0; bit< maskDerivedFO[c].length(); bit++) {
					if (maskDerivedFO[c].get(bit)) {
						outDFAP.print(bit);
						outDFAP.print(" ");
						outDFA.print("1");
					}
					if (!maskDerivedFO[c].get(bit)) {
						outDFA.print("0");
					}
				}
				outDFA.print("\n");
				outDFAP.print("\n");
			}
			outDFA.close();
			outDFAP.close();
            
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		
		JsfsCheckOldDiagonal jsfsCheckOldDiagonal = new JsfsCheckOldDiagonal();
		jsfsCheckOldDiagonal.setSnpFile("/Volumes/Aner/dataAugust/filtering_006_withCVI_0_ref_invalid/fosa");
				// /Users/andrea/Desktop/dataAugust/Data_Fst"); // /home/CIBIV/andreaf/Data_Fst
	}
}
*/