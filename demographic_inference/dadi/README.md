###
#	Demographic inference with dadi: 
###

Dadi is acomposite likelihood approach based on the diffusion approximation 
which uses the Joint Site Frequency Spectrum to fit a demographic model to the data
Gutenkunst RN , Hernandez RD, Williamson SH, Bustamante CD. 2009. Inferring the joint demographic history of multiple populations from multidimensional SNP frequency data. PLoS Genet.5(10):e1000695.
###

Folders:

data:
The input files used for dadi:
	clean_2020-04-27_sub40_snps_nonN_inTheMask.txt: the total number of base pairs used for the analysis. This include intergenic variant and non-variant sites (non-missing matches to the reference). We excluded sites with more than 5% missing data, hypermutable CpG sites, pericentromeric regions rich in satellite repeats and general repeat regions identified with Heng Li’s SNPable approach. The JSFS is polarised to Morocco, the best modern representative of the original coloniser of CVI.
	sfs_interg_2020-06-03_5percN_clean_2020-04-27_sub40_dadiJsfsX.txt: the JSFS in dadi format	

scripts:
	lounch_dadi.command and lounch_dadi_cviMor.command submit the scripts simple_unfolded.py and simple_unfolded_mor.py to the cluster, respectively for inference between CVI islands and between cvi and morocco
	simple_unfolded.py and simple_unfolded_mor.py run the actual dadi inference
	demographic_models.py: the demographic models used
	lounch_dadi_uncert.command: computes confidence intervals for parameters using 100000 bootstrapped data sets and the Godambe Information Matrix implemented in dadi. 
	orgnise_dadi_results_cvi.R: reads the results from dadi, selects the run with highest likelihood for each model, outputs a table (summary_cvi_2020-06-08.txt, tab delimited) with rescaled parameters 

results: 
includes text files with results from 1000 replicated dadi runs with different starting values for all parameters for each of four demographic models.

	pop-split_cvi_allRuns.txt: Results from a simple two-population split with no migration and static population size (Ne) 
	bottleneck_cvi_allruns.txt: The same model with a bottleneck at the split 
	splitexp_cvi_allruns.txt: A split with exponential changes in Ne after the split and no migration 
	im_cvi_allruns.txt: An isolation with migration model (IM): a split with exponential changes in Ne and asymmetric migration
	summary_cvi_2020-06-08.txt: tab delimited table with rescaled parameters from the run with highest likelihood for each model


figures:
three figures per model: 2d JSFS and the two marginal sfs from data and model with the highest likelihood parameters:
	pop-split_cvi_2d_rep436.png
	pop-split_cvi_marginal0_rep436.png
	pop-split_cvi_marginal1_rep436.png

	splitExp_cvi_2d_rep47.png
	splitExp_cvi_marginal0_rep47.png
	splitExp_cvi_marginal1_rep47.png
	
	bottleneck_cvi_2d_rep23.png
	bottleneck_cvi_marginal0_rep23.png
	bottleneck_cvi_marginal1_rep23.png
	
	im_cvi_2d_rep283.png
	im_cvi_marginal0_rep283.png
	im_cvi_marginal1_rep283.png





