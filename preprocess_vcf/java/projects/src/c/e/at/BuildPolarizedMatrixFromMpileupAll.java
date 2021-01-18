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

public class BuildPolarizedMatrixFromMpileupAll {
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

	
	
	private BuildPolarizedMatrixFromMpileupAll() {}

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
			
			/*
			for (int l=0; l<5; l++) {
				for (int a =0; a<15; a++) {
					System.out.println(lyrataChar[l][a]);
					System.out.println(lyrataPos[l][a]);
				}
			}
			*/
			
			System.out.println("We got lyrata!!");
			
			






			// accession = 0; // 
			accession = Integer.parseInt(filename); // 
/*
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
*/
			whereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/janeiro/polarMatrCVIonly/";
			
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
		        	
		        	for (int i=0; i<lineMask.length(); i++) {
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
	        	System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("genome: " + genome);
			
			
			
			
			
			
			
			/*
			// ONLY FOR JSFS CHECK		
				
			// Charge the DIAGONAL mask
			
			File fileDiagonal = new File(whereWe + "masks/modif_mask_diagonal_sfs_withCombinedMask.txt");
			
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
	        
	        for (int c=0; c<5; c++) {
	        	combinedMask[c].and(diagonalMask[c]);
	        }

	        
	        int diagonal =0;
	        
	        for (int c=0; c<5; c++) {
	        	diagonal = diagonal + combinedMask[c].cardinality();
	        	System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("diagonal: " + diagonal);
			
			
			// END
			*/
			
			
			
			
			
			
        	// Dajje coooi nosstri CVIs!!
			
			folder = new File(whereWe + "snp2/");
			listOfFiles = folder.listFiles();
			numFiles = listOfFiles.length;
			
			System.out.println("numFiles: " + numFiles);
			// System.out.println("listOfFiles[1]: " + listOfFiles[1]);
			
			/*
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
			*/
			
			listOfSNPFiles = new File[numFiles]; // numSNPFiles	
			
			// System.out.println("numSNPFiles: " + numSNPFiles);

			/*
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
        	*/
        	
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
				
				Writer fineWriter = new FileWriter(whereWe + "results_14Feb/" + "positions.txt");
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
				
				Writer writerP = new FileWriter(whereWe + "results_14Feb/" + "PCA_params.txt");
				PrintWriter outP = new PrintWriter(writerP);

				outP.println(Arrays.toString(listOfSNPFiles));
				outP.println(Arrays.toString(positions));
				outP.println(Arrays.toString(checkChr));
				
				outP.close();
				
				
				
				// Write parameters for ADMIXTURE
				

				Writer writerA = new FileWriter(whereWe + "results_14Feb/" + "ADM_params.map");
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
				
				Writer writerN = new FileWriter(whereWe + "results_14Feb/" + "ADM_AccessionNames.txt");
				PrintWriter outN = new PrintWriter(writerN);
				
				for (int name=0; name<listOfSNPFiles.length; name++) {
					outN.print(listOfSNPFiles[name]);
					outN.print("\n");				
				}
				outN.close();
			}
			
			
			
			
			
			// Now fill the matrix Meraviglia!!!
			
			
			meraviglia = new BitSet[listOfSNPFiles.length];
			
			for (int plants=0; plants<listOfSNPFiles.length; plants++) {
				meraviglia[plants] = new BitSet(numSnps);
			}
			
			
			
			
			
			
			
			File folderAll = new File(whereWe + "all/");
			File[] listOfMpileupAll = folderAll.listFiles();
			int numFilesMpileupAll = listOfMpileupAll.length;
			
			for (int z=0; z<listOfMpileupAll.length; z++) {
				System.out.println("listOfMpileupAll" + z + " is: " + listOfMpileupAll[z] );
			}
			
			Writer writerFirstC = new FileWriter(whereWe + "results_14Feb/" + "firstCol.txt");
                        PrintWriter outFirstCol = new PrintWriter(writerFirstC);
			

			
			for (int f=accession; f<accession+1; f++) { // accession+1 // listOfMpileupAll.length
				
				
				Writer writer = new FileWriter(whereWe + "results_14Feb/" + "PCA_" + f + ".txt", true);
				PrintWriter out = new PrintWriter(writer);
				
				System.out.println("File numero.... " + f);
				
				File snpFile = listOfMpileupAll[f];
				
				Scanner scannerCvi = new Scanner(snpFile);
				
				while ( scannerCvi.hasNextLine() ) {
					
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        		
	        		if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
		        	
	        			int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        		int posSNP = Integer.parseInt(splitSnp1[1]);
		        	
		        		if (maskXor[chr-1].get(posSNP)) { // combinedMask??????
		        			

				        	// Use indexBig to put in one dimension 5 chromosomes, in a row. 
				        	// IndexBig is for every chromosome, the first position in a bitset pertaining to such chromosome
				        	
		        			int indexBig = 0;
			        		
		        			if (chr != 1) {
			        			for (int c=0; c < (chr-1); c++) {
					        		indexBig = indexBig + maskXor[c].cardinality();
			        			}
			       		 	}
			        		
			        		int index = indexBig;
		
					        	
				        	// index, on the other hand, runs within a chromosome, in a bitset made of all chromosomes in a row
				        		
				        	for (int p=0; p< posSNP; p++) {
				        		if (maskXor[chr-1].get(p)) {
				        				
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


						// Check if this works better:

						for (int p=0; p<posSNP+10; p++) {
							if ( lyrataPos[chr-1][p] == posSNP ) {
								indexx = p;
							}
						}

						// This is the old one
				        	/*	
				        	for (int p=0; p<posSNP; p++) {
				        		if (maskLyrata[chr-1].get(p)) {
				        			indexx = indexx+1;
				        		}
				        	}
						*/



						String snpName = chr + "_" + posSNP;
						outFirstCol.print(snpName);
						outFirstCol.print("\t");
						

				        	if ( base != lyrataChar[chr-1][indexx] ) {
				        		meraviglia[f].set(index);
				        	}
		        			
		        		}
	        		}
				}
			
			outFirstCol.close();
			
			System.out.println("Writing...");

			for (int bit =0; bit< numSnps; bit++) {
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
			
			
			
			out.close();
			
				
		}
		
		// for (int plant=0; plant<meraviglia.length; plant++) {
			
		System.out.println("meraviglia[" + accession + "].cardinality() : " + meraviglia[accession].cardinality());
		
		// }
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		BuildPolarizedMatrixFromMpileupAll buildPolarizedMatrixFromMpileupAll = new BuildPolarizedMatrixFromMpileupAll();
		buildPolarizedMatrixFromMpileupAll.setSnpFile(args[0]); // args[0]
	}
}
