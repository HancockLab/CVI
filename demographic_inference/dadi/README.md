###
#	Demographic inference with dadi: 
###

Dadi is acomposite likelihood approach based on the diffusion approximation 
which uses the Joint Site Frequency Spectrum to fit a demographic model to the data
Gutenkunst RN , Hernandez RD, Williamson SH, Bustamante CD. 2009. Inferring the joint demographic history of multiple populations from multidimensional SNP frequency data. PLoS Genet.5(10):e1000695.

These scripts are based on joint site frequency spectra produced in: 
	./CVI/java_programs/java/projects/sfs_lounch_2popsToLyrata.command
###

## Folders

### data
The input files used for dadi

	clean_2020-04-27_sub40_snps_nonN_inTheMask.txt: the total number of base pairs used for the analysis. This include intergenic variant and non-variant sites (non-missing matches to the reference). We excluded sites with more than 5% missing data, hypermutable CpG sites, pericentromeric regions rich in satellite repeats and general repeat regions identified with Heng Li’s SNPable approach. The JSFS is polarised to Morocco, the best modern representative of the original coloniser of CVI.
	sfs_interg_2020-06-03_5percN_clean_2020-04-27_sub40_dadiJsfsX.txt: the JSFS in dadi format	

### scripts
Scripts to submit to the cluster, running dadi and process results

	lounch_dadi.command submits the script simple_unfolded.py, for the dadi inference
	simple_unfolded.py runs the actual dadi inference
	demographic_models.py: the demographic models used
	uncert_dadi.py computes confidence intervals for parameters using 100000 bootstrapped data sets and the Godambe Information Matrix implemented in dadi. 
	orgnise_dadi_results_cvi.R: reads the results from dadi, selects the run with highest likelihood for each model, outputs a table (summary_cvi.txt, tab delimited) with rescaled parameters 
