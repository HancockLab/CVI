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


public class MaskBuild {
	
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

	int totSegDiff=0;
	
	

	
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
	private char[][] lyrataChar = null;
	private int[][] lyrataPos = null;
	private int checkChr =0;
	private int posSNP = 0;
	private File lyrataFile = null;
	private Scanner scanLyr = null;
	private File fileSalk = null;
	private File fileGmi = null;
	
	private BitSet[] maskGMI = null;
	private BitSet[] maskSALK = null;
	
	private BitSet[] maskCviZeros = null;
	private BitSet[] maskinvert = null;
	private BitSet[] maskSegr = null;
	
	private File fileMpileup = null;

	
	public MaskBuild() {}
	
	public void setSnpFile(String filename){
		
		try {
			
			
			
			mask=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			
			
			fileMpileup = new File(filename);
			Scanner scanner = new Scanner(fileMpileup);
			
			System.out.println("The game begins!!");
			
			while ( scanner.hasNextLine() ) { 
				
				snp1 = scanner.nextLine();
	        		splitSnp1 = snp1.split("\t");
	        	
	        	
	        		if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
		        		
	        			int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        		int posSNP = Integer.parseInt(splitSnp1[1]);
	        			int coverage = Integer.parseInt(splitSnp1[3]);
		        	
	        			if (coverage >= 5) {
		        			mask[chr-1].set(posSNP);
		        		}
	        		}
			}
			
			int genome =0;
			for (int c=0; c<5; c++) {
				System.out.println("Cardinality_" + (c+1) + ": " + mask[c].cardinality());
				genome = genome + mask[c].cardinality();
			}
			System.out.println("Cardinality: " + genome);


			System.out.println("Write that down!!");
			
			Writer writer = new FileWriter("/home/CIBIV/andreaf/canaries/rawData/java/Mask_cvi0_Gmi.txt");
			PrintWriter out = new PrintWriter(writer);
			
			for (int c=0; c<5; c++) {
				
				out.print(">Chr" + (c+1));
				out.print("\n");
				
				for (int bit =0; bit< mask[c].length(); bit++) {
					if (mask[c].get(bit)) {
						out.print("1");
					}
					if (!mask[c].get(bit)) {
						out.print("0");
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
		
		MaskBuild maskBuild = new MaskBuild();
		maskBuild.setSnpFile("/home/CIBIV/andreaf/canaries/rawData/CVI_0_gmi.11015_110_-1.realigned.sort.bam.sam.sam.sortP.sam.duprem.sam.bam.mq20.bam.mpileup"); 
		
		// /home/CIBIV/andreaf/canaries/rawData/CVI_0_gmi.11015_110_-1.realigned.sort.bam.sam.sam.sortP.sam.duprem.sam.bam.mq20.bam.mpileup");
		
	}

}
