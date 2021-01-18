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

public class ForPcaAndAdmixture {
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

	



	
	
	private ForPcaAndAdmixture() {}

	public void setSnpFile(String filename){
		
		
		try {
			
			accession = Integer.parseInt(filename);
			whereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/PCA_ADM/";
			
			mask =new BitSet[5];
			combinedMask =new BitSet[5];
			maskOr=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
				combinedMask[c]= new BitSet();
				maskOr[c]= new BitSet();
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
	        
	        
	        int genome =0;
	        
	        for (int c=0; c<5; c++) {
	        	genome = genome + combinedMask[c].cardinality();
	        	System.out.println("Cardinality: " + combinedMask[c].cardinality());
	        }
        	System.out.println("genome: " + genome);
			
			
			
			
			
			
			
			
        	// Dajje coooi nosstri CVIs!!
			
			folder = new File(whereWe + "snp/");
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
				if (extension.equals("StrictSnp")) {
					numSNPFiles = numSNPFiles + 1;
				}
			}
			listOfSNPFiles = new File[numSNPFiles];			
			
			System.out.println("numSNPFiles: " + numSNPFiles);

			
			// Take all files, create subset of SNP2 files
			
			s=0;
			for (int f=0; f<numFiles; f++) {
				int i=0;
				fileName = listOfFiles[f].getName();
				i = fileName.lastIndexOf('.');
				if (i > 0) {
				    extension = fileName.substring(i+1);
				}
				if (extension.equals("StrictSnp")) {
					listOfSNPFiles[s] = listOfFiles[f];
					s=s+1;
				}
			}
        	
			for (int z=0; z<listOfSNPFiles.length; z++) {
				System.out.println("listOfSNPFiles" + z + " is: " + listOfSNPFiles[z] );
			}
			
			// We got file names!!!
			
			
			
			
			
			
			
			
			// Now build a mask of segregating sites - maskOr
			
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
				
				// Accumulate new SNPs in maskOr
				
				for (int c=0; c<5; c++) {
					maskOr[c].or(mask[c]);
				}
			}
			
			// MaskOr contains all segregating sites
			
			
			
			
			
			for (int c=0; c<5; c++) {
				numSnps = numSnps + maskOr[c].cardinality();
				System.out.println("maskOr[" + c + "].cardinality(): " + maskOr[c].cardinality());
			}
			System.out.println("numSnps: " + numSnps);
			
			
			
			checkChr = new int[numSnps];
			int count = 0;
			
			for (int c=0; c<5; c++) {
				for (int pos =0; pos < maskOr[c].cardinality(); pos++) {
					checkChr[count] = (c+1);
					count = count+1;
				}
			}
			
			// System.out.println("checkChr: " + Arrays.toString(checkChr));
			System.out.println("checkChr!");
			
			
			
			
			positions = new int[numSnps];
			count =0;
			
			for (int c=0; c<5; c++) {
				for (int pos =0; pos < maskOr[c].length(); pos++) {
					if (maskOr[c].get(pos)) {
						
						positions[count] = pos;
						count = count +1;
					}
				}
			}
			
			
			// System.out.println("positions: " + Arrays.toString(positions));
			System.out.println("positions!");
			
			
			
			// Write out parameters only once.....
			
			if (accession == 0) {
				

				
				// Write parameters for PCA
				
				Writer writerP = new FileWriter(whereWe + "analyses/PCA/PCA_params.txt");
				PrintWriter outP = new PrintWriter(writerP);

				outP.println(Arrays.toString(listOfSNPFiles));
				outP.println(Arrays.toString(positions));
				outP.println(Arrays.toString(checkChr));
				
				outP.close();
				
				
				
				// Write parameters for ADMIXTURE
				

				Writer writerA = new FileWriter(whereWe + "analyses/PCA/ADM_params.map");
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
				
				Writer writerN = new FileWriter(whereWe + "analyses/PCA/ADM_AccessionNames.txt");
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
			
			
			Writer writer = new FileWriter(whereWe + "analyses/PCA/PCA_" + accession + ".txt", true);
			PrintWriter out = new PrintWriter(writer);
			
			
			
			
			
			
			
			
			for (int f=accession; f<accession+1; f++) {
				
				System.out.println("File numero.... " + f);
				
				File snpFile = listOfSNPFiles[f];
				
				Scanner scannerCvi = new Scanner(snpFile);
				
				while ( scannerCvi.hasNextLine() ) {
					
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        	
		        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
			        	
		        		int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
			        	int posSNP = Integer.parseInt(splitSnp1[1]);
			        	
			        	
			        	// Use indexBig to put in one dimension 5 chromosomes, in a row. 
			        	// IndexBig is for every chromosome, the first position in a bitset pertaining to such chromosome
			        	
		        		int indexBig = 0;
		        		
		        		if (chr != 1) {
		        			for (int c=0; c < (chr-1); c++) {
				        		indexBig = indexBig + maskOr[c].cardinality();
				        	}
		        		}
		        		
		        		int index = indexBig;

			        	
		        		// index, on the other hand, runs within a chromosome, in a bitset made of all chromosomes in a row
		        		
		        		for (int p=indexBig; p< (indexBig + posSNP); p++) {
		        			if (maskOr[chr-1].get(p)) {
		        				
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
		        		
			        	meraviglia[f].set(index);
			        }
				}
				
				
				System.out.println("Writing...");

				for (int bit =0; bit< numSnps; bit++) {
					if (meraviglia[f].get(bit)) {
						out.print("2");
						// out.print("\t");
					}
					if (!meraviglia[f].get(bit)) {
						out.print("0");
						// out.print("\t");
					}
				}
				
				out.print("\n");
				
				
				
				
				
				
			}
			
			for (int plant=0; plant<meraviglia.length; plant++) {
				System.out.println("meraviglia[plant].cardinality() : " + meraviglia[plant].cardinality());
			}
			
			
			
			
			
			out.close();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		ForPcaAndAdmixture forPcaAndAdmixture = new ForPcaAndAdmixture();
		forPcaAndAdmixture.setSnpFile(args[0]);
	}
}
