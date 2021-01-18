package c.e.at;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

public class ResolveCalling {
	
	private File folder=null;
	File[] listOfSNPFiles=null;
	String snp1=null;
	private String[] splitSnp1 = null;
	private String[] variant = null;
	private String[] quality = null;
	int ref =0;
	int a =0;
	int t =0;
	int c =0;
	int g =0;
	int indelSign =0;
	int indelBlind =0;
	int jump = 0;
	int r =0;
	int run =0;
	


	private ResolveCalling() {}

	public void setSnpFile(String filename){
		
		
		try {
			

			folder = new File(filename);
			listOfSNPFiles = folder.listFiles();			
			
			for (int f=0; f<listOfSNPFiles.length; f++) {
				
				System.out.println("File.... " + listOfSNPFiles[f]);
				
				File snpFile = listOfSNPFiles[f];
				
				Writer writer = new FileWriter(listOfSNPFiles[f] + ".snp2");
    			PrintWriter out = new PrintWriter(writer);
    			
				Scanner scannerCvi = new Scanner(snpFile);
				
				while ( scannerCvi.hasNextLine() ) { 
					
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        	
	        		String var = splitSnp1[4];
        			variant = var.split("");
        			
	        		int coverage = Integer.parseInt(splitSnp1[3]);
	        		
        			String qual = splitSnp1[5];
        			quality = qual.split("");
        			
        			ref =0;
    				a =0;
    				t =0;
    				c =0;
    				g =0;
    				indelSign =0;
    				indelBlind =0;
        			
    				r =1;
    				
        			for (int run=1; run<variant.length; run++) {
        				
        					
    					if ( variant[run].charAt(0) != '$' ) {
        					if (variant[run].charAt(0) == '^') {
        						run =run+2;
        						// System.out.println(snp1);
        						// System.out.println(run);
        						// System.out.println(variant[run].charAt(0));
        						coverage = coverage -1;
        					} else {
        						// System.out.println(snp1);			        							
    							if ( (variant[run].charAt(0) == '-') || (variant[run].charAt(0) == '+') ) {
	        						
	        						// System.out.println(snp1);
	        						// System.out.println(run);
	        						// System.out.println(variant[run].charAt(0));
	        						// System.out.println(r);
	        						
	        						run = run +1;
	        						int size = Integer.parseInt(variant[run]);
	        						run = run + size;
	        						indelSign = indelSign+1;
	        						r =r-1;
    							} else {
    								if ( (variant[run].charAt(0) == '*') ) {
    									indelBlind = indelBlind+1;
		        					}
    								
    								if ( ((int)(quality[r].charAt(0)) - 33) >= 25) {
    									if ( (variant[run].charAt(0) == '.') || (variant[run].charAt(0) == ',') ) {
				        					ref = ref + 1;
				        				} else {
				        					if ( (variant[run].charAt(0) == 'a') || (variant[run].charAt(0) == 'A') ) {
				        						a = a+1;
				        					}
				        					if ( (variant[run].charAt(0) == 't') || (variant[run].charAt(0) == 'T') ) {
				        						t = t+1;
				        					}
				        					if ( (variant[run].charAt(0) == 'c') || (variant[run].charAt(0) == 'C') ) {
				        						c = c+1;
				        					}
				        					if ( (variant[run].charAt(0) == 'g') || (variant[run].charAt(0) == 'G') ) {
				        						g = g+1;
				        					}
				        				}
        							} else {
			        					coverage = coverage -1;
			        					if ((int)(quality[r].charAt(0) - 33) < 0) {
			        						System.out.println("Danger! Wrong quality encoding at: " + snp1);
			        					}
			        				}
    							}
    						}
        				} else {
        					r = r-1;
        				}
        				
    					r = r +1;
        			}
        			
        			
					out.print(snp1);
					out.print("\t");
					
        			if (a > coverage/2) {
    					out.print("A");
    					out.print("\n");
        			}
        			if (t > coverage/2) {
    					out.print("T");
    					out.print("\n");
        			}
        			if (c > coverage/2) {
    					out.print("C");
    					out.print("\n");
        			}
        			if (g > coverage/2) {
    					out.print("G");
    					out.print("\n");
        			}
				}
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
	
	public static void main(String[] args) {
		ResolveCalling resolveCalling = new ResolveCalling();
		resolveCalling.setSnpFile("/home/CIBIV/andreaf/canaries/rawData/analyses/PCA_ADM/snp/");
	}
}
