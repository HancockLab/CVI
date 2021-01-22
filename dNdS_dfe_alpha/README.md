###
# dN/dS, dfe and alpha
###
Testing for evidence of adaptive evolution by computing dN/dS, dfe and alpha.
The dN/dS ratio was computed with custom scripts. Dfe and alpha were inferred by polyDFE (Tataru et al. 2017). 

For the dN/dS ratio:
# 
	functionalMasks.command creates 0- and 4-folddegenerate masks. 
	Then you need to create Jsfs between Santo Antao and Fogo for 0- and 4-fold degenerate sites with java_programs/java/projects/sfs_lounch_2popsToLyrata.command 
	Then use the fixed derived mutations in each island compared to the other (in the jsfs, first row, last column and last row, first coumn)
	The jsfs script also produces the denominators, i.e. the number of sites in the genome at risk for each mutational category. 
	This is basically the 0- or 4-fold degenerate masks, discounted for sites that have many missing data in the actual samples (*snps_nonN_inTheMask.txt)

## Folders/files:

### data:				
Necessary input files
### functionalMasks.command:		
Creates masks for 0- and 4-fold degenerate sites and more masks based on estimated effects from snpEFF
### dnds_plot.py:			
Plotting and stats on the dN/dS ratios
### dfe_launch.command:			
Launches polyDFE on different input files for different populations. To run on bootstrapped data sets, first bootstrap the spectra with polyDfe as in discretised_dfe.R
### dfe_poly.command:			
Runs polyDFE. The first command uses defaults parameters, the following are to play around with parameters and models
### discretised_dfe.R:			
Plots and stats discretised dfe inferred by polyDFE. Also, bootstraps the sfs.


