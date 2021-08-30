###
# Java programs 
###

Java programs to preprocess the vcf, produce input files for analyses and compute summary statistics on the genomic data.

These java programs assume that you have the /java/ folder from GitHub in your home directory, and that the directory structure is the same as in /java/. 
If not so, you will need to change some paths (e.g. to the libraries in ./java/lib/)


To run these java programs, enter the ./java/projects directory and you will find:

###  matrix_cvi.command
Reads a VCF file (download from EVA) and creates a SNP matrix filtered for coverage and quality. Additionally, it creates a second matrix with the reconstructed ancestral state at every SNP, generated by ancestor_reconstruct.command

### theta_twoPopulations.command
Computes theta pi, W and other summary statistics in two populations based on the SNP matrix created by matrix_cvi.command

### sfs_lounch_2popsToLyrata.command
Computes Joint and marginal Site Frequency Spactra for two populations, polarised to A. lyrata

### snpable.command
Produces a mask of repetitive regions from SNPable

### ancestor_reconstruct.command
Reconstructs the most likely genomic sequence of the ancestor to CVI, based on variation in Morocco, the closest population found on the continents.

