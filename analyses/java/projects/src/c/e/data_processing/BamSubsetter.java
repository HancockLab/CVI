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

public class BamSubsetter {
	
	public BamSubsetter() {}
	
	public void setSamFile(String samName){
		
		try {
			
			// Open the sam file
			File samFile = new File(samName);
			Scanner scannerSam = new Scanner(samFile);
			
			String[] nameSplit = samName.split("\\/");
			String[] nameSplit2 = nameSplit[nameSplit.length-1].split("\\.");
			int id = Integer.parseInt(nameSplit2[1]);
			String sampleCode = "notWorking";
			
			// Change id to coded id	
			File idsCode = new File("/home/lv70590/Andrea/data/moroccan_IDsAndCodes.txt");
			Scanner scannerCode = new Scanner(idsCode);
                        while ( scannerCode.hasNextLine() ) {
	                        String snp = scannerCode.nextLine();
	                        String[] splitSnp = snp.split("\t");
				if (id == Integer.parseInt(splitSnp[0])) {
					sampleCode = splitSnp[2];
				}
			}
			
			
			// Now write out the nice one
			Writer writer = new FileWriter(samFile + "_subset.sam");
			PrintWriter out = new PrintWriter(writer);
			
			while ( scannerSam.hasNextLine() ) {
				String snp = scannerSam.nextLine();
				String[] splitSnp = snp.split("\t");
				
				if (snp.charAt(0) == '@') {
					if ( (snp.charAt(1) == 'R') && (snp.charAt(2) == 'G') ) {
						out.print("@RG" + "\t" + "ID:" + sampleCode + "\t" + "SM:" + sampleCode + "\n");
					} else {
						if ( (snp.charAt(1) == 'P') && (snp.charAt(2) == 'G') ) {
                                                	out.print("@PG" + "\t" + "ID:MarkDuplicates" + "\t" + "PN:MarkDuplicates" + "\t" + "VN:1.100(1571)" + "\t" + "CL:net.sf.picard.sam.MarkDuplicates INPUT= OUTPUT= METRICS_FILE=pe.dupstat.txt REMOVE_DUPLICATES=true VALIDATION_STRINGENCY=SILENT    PROGRAM_RECORD_ID=MarkDuplicates PROGRAM_GROUP_NAME=MarkDuplicates ASSUME_SORTED=false MAX_SEQUENCES_FOR_DISK_READ_ENDS_MAP=50000 MAX_FILE_HANDLES_FOR_READ_ENDS_MAP=8000 SORTING_COLLECTION_SIZE_RATIO=0.25 READ_NAME_REGEX=[a-zA-Z0-9]+:[0-9]:([0-9]+):([0-9]+):([0-9]+).* OPTICAL_DUPLICATE_PIXEL_DISTANCE=100 VERBOSITY=INFO QUIET=false COMPRESSION_LEVEL=5 MAX_RECORDS_IN_RAM=500000 CREATE_INDEX=false CREATE_MD5_FILE=false" + "\n");
        	                      		 } else {
                                        	        out.print(snp + "\n");
	                              	    	}
					}




				} else {

					for (int s=0; s<splitSnp.length; s++) {
						if ( (splitSnp[s].charAt(0) == 'R') && (splitSnp[s].charAt(1) == 'G') ) {
							splitSnp[s] = "RG:Z:" + sampleCode;
						}
					}
					
					if (splitSnp[2].equals("Chr4")) {
	                                        if ( (Integer.parseInt(splitSnp[3]) > 11370000) && (Integer.parseInt(splitSnp[3]) < 11400000) ) {
        
							out.print(splitSnp[0]);
		                                        for (int s=1; s<splitSnp.length; s++) {
								out.print("\t" + splitSnp[s]);
                	                        	}
							out.print("\n");
						}
                        	        } else {
                                	        if (splitSnp[2].charAt(0) != '*') {
                                      			out.print(splitSnp[0]);
                                                        for (int s=1; s<splitSnp.length; s++) {
                                                                out.print("\t" + splitSnp[s]);
                                                        }
                                                        out.print("\n");
						}
                               		}
				}
			}
			out.close();
			


// 7001253F:351:C8DE0ANXX:4:1304:6093:45170#CTTGTA 83      Chr4    11370012        60      125M    =       11369805        -332    ATTTGTAAAGTCGGTCCGCCCTTGAACACATACACACTTTTTTTGTGTTTCTCTGTTCACGAGTTATAGGGGTTTAGTTATAGCTTACAATTTTTTTTTTTTTTGTAACAAGTTATAGCTTACAT   /FFFFBFB77/7<FBB77/7/FB<FB7F//FF7</F7BBFBBBF7//FBB/F<//FBB<FF</FB<FFBFF/FFB</F<</FB<BFB<FFFFF<FFFFFFFFFFFFFFFFFFBFFBFFFBBB<BB   X0:i:1  X1:i:0  MD:Z:125        PG:Z:MarkDuplicates     RG:Z:M_ha-Elh-10.35523  XG:i:0  AM:i:37 NM:i:0  SM:i:37 XM:i:0  XO:i:0  XT:A:U



	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		BamSubsetter bamSubsetter = new BamSubsetter();
		bamSubsetter.setSamFile(args[0]);
	}
}
