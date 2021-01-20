# Welcome!

###
These java scripts assume that you have the ./java/ folder from GitHub in your home directory
And that the directory structure is the same as in ./java/
If not so, you will need to change some paths (e.g. to the libraries in ./java/lib/)


To run these java scripts, enter the ./java/projects directory and you will find:

1) matrix_cvi.command:			Reads a VCF file (download from EVA) and creates a SNP matrix filtered for coverage and quality. Additionally, it creates a second matrix with the reconstructed ancestral state at every SNP
2) theta_twoPopulations.command:	Computes theta pi, W and other summary statistics in two populations based on the SNP matrix created by matrix_cvi.command
3) chromoPainter.command:		Prepares input data for chromopainter and runs it on many samples in repicates
4) sfs_lounch_2popsToLyrata.command:	Computes Joint and marginal Site Frequency Spactra for two populations, polarised to A. lyrata
5) qtls_genes_var.command:		Computes the percentage of private variants for QTL regions, candidate genes and functional variants and genome wide
6) fulgiP.command:			Produces conservative genotype calling, useful for msmc-ccr analyses. Also calls S-locus haplogroups
7) snpable.command:			Produces a mask of repetitive regions from SNPable

