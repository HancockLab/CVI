# Demographic inference with msmc-ccr

In order to run msmc and ccr, you need to go through some preliminary steps:

1) Call SNP and mask files with /java_programs/java/projects/fulgiP.command

2) Preprocess SNP files with: ./ccr_internalScripts/scripts/preprocessing/./run_prepare_vcf.sh 
	Mind that there are paths that you need to specify in these scripts

3) Preprocess mask files with: ./ccr_internalScripts/scripts/preprocessing/./run_symlink_masks.sh
	Mind that there are paths that you need to specify in these scripts

4) Now use run8_ccr.command to actually run msmc-ccr

5) And ./plotting/ for plotting the three ccr figures in the paper



