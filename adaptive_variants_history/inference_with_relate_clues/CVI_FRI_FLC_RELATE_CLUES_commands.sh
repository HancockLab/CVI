################ Aim: Inferring genealogical tree (using RELATE) for SNPs of interest and infer their selection (using CLUES)

################ Target SNP(s): FRI SNP variant (Chr4:269719)
################                FLC SNP variant (Chr5: 3179333)

################ Contents shown in this file:
# (1) Processing the VCF file
# (2) Infer the genome-wide genealogies using RELATE
# (2) Infer the local genealogy for FRI (Chr4:269719) and FLC (Chr5: 3179333) using RELATE
# (3) Estimate the selection and allele frequency trajectory using CLUES

################
################################################ Paths to used tools
################

bcftools="/path_to_bcftools/bcftools"
RELATE="/path_to_RELATE/Relate"
RelateFileFormats="/path_to_RELATE/RelateFileFormats"
PrepareInputFiles="/path_to_RELATE/scripts/PrepareInputFiles/PrepareInputFiles.sh"
EstimatePopulationSize="/path_to_RELATE/scripts/EstimatePopulationSize/EstimatePopulationSize.sh"
SampleBranchLengths="/path_to_RELATE/scripts/SampleBranchLengths/SampleBranchLengths.sh"
TreeViewSample="/path_to_RELATE/scripts/TreeView/TreeViewSample.sh"
infer_clues="/path_to_CLUES/inference.py"
plot_traj_clues="/path_to_CLUES/plot_traj.py"

################
################################################ Paths for input files
################

rec_map="/path_to_directory/"
poplabels_SA="/path_to_directory/SA.poplabels"
poplabels_FO="/path_to_directory/FO.poplabels"
clues_timebins="/path_to_directory/"

#######################################################################################################################################

##############################################################
################# (1) Processing the VCF file ################
##############################################################

################### Remove non-biallelic SNPs, filtering for quality, retained segregating sites, and filtered out missing data
vcf="path to vcf file"
bgzip -cd ${vcf} > CVI_SHORE.vcf
$bcftools view -m2 -M2 -v snps –min-ac=1 -i 'MIN(FMT/DP)>3 & MIN(FMT/GQ)>25 & F_MISSING=0' CVI_SHORE.vcf > CVI_SHORE_filtered.vcf

################### gzip VCF file
bgzip -c CVI_SHORE_filtered.vcf > CVI_SHORE_filtered.vcf.gz 
tabix -p vcf CVI_SHORE_filtered.vcf.gz

################### Split by Chromosome
for i in {1..5}; do $bcftools view -r Chr${i} CVI_SHORE_filtered.vcf.gz > CVI_SHORE_filtered_Chr${i}.vcf; done

#######################################################################################################################################

###################################################################
############## (2) RELATE (Genome-wide genealogies) ###############
###################################################################

################### For Santo Antão
## Convert from VCF to haplotype and sample files
for i in {1..5}; do \
$RelateFileFormats \
--mode ConvertFromVcf \
--haps SA_Prepared_chr${i}.haps \
--sample SA_Prepared_chr${i}.sample \
-i SA_filtered_Chr${i}; done

## Run RELATE
for i in {1..5}; do \
$RELATE --mode All \
--haps SA_Prepared_chr${i}.haps \
--sample SA_Prepared_chr${i}.sample \
--map ${rec_map}/chr${i}_recmap.map \
--memory 20 -N 13000 -m 2.512032e-9  \
-o SA_relate_chr${i}; done

## Estimate Population Size
$EstimatePopulationSize \
-i SA_relate \
-m 2.512032e-9 \
--poplabels $poplabels_SA \
--years_per_gen 1 \
--first_chr 1 \
--last_chr 5 \
--threads 6 \
--num_iter 10 \
--threshold 0 \
-o SA_gw_popsize

################### For Fogo
## Convert from VCF to haplotype and sample files
for i in {1..5}; do \
$RelateFileFormats \
--mode ConvertFromVcf \
--haps FO_Prepared_chr${i}.haps \
--sample FO_Prepared_chr${i}.sample \
-i FO_filtered_Chr${i}; done

## Run RELATE
for i in {1..5}; do \
$RELATE --mode All \
--haps SA_Prepared_chr${i}.haps \
--sample SA_Prepared_chr${i}.sample \
--map ${rec_map}/chr${i}_recmap.map \
--memory 20 -N 13000 -m 2.1034e-9  \
-o SA_relate_chr${i}; done

## Estimate Population Size
$EstimatePopulationSize \
-i FO_relate \
-m 2.1034e-9 \
--poplabels $poplabels_FO \
--years_per_gen 1 \
--first_chr 1 \
--last_chr 5 \
--threads 6 \
--num_iter 10 \
--threshold 0 \
-o FO_gw_popsize

######################################################################################
################ (3) RELATE - infer local genealogies for FRI and FLC ################
######################################################################################

################### Run RELATE
## For FRI (Chr4:269719) in Santo Antão
$RELATE \
--mode All \
--haps SA_Prepared_chr4.haps \
--sample SA_Prepared_chr4.sample \
--map ${rec_map}/chr4_recmap.map \
--memory 20 --coal SA_gw_popsize.coal -m 2.512032e-9  \
-o  chr4_SA_relate

## For FLC (Chr5: 3179333) in Fogo + S1-1 from S_Fig subpopulation in Santo Antão
$RELATE \
--mode All \
--haps FO_Prepared_chr5.haps \
--sample FO_Prepared_chr5.sample \
--map ${rec_map}/chr5_recmap.map \
--memory 20 --coal FO_gw_popsize.coal -m 2.1034e-9  \
-o  chr5_FO_relate

################### Plotting FRI genealogical tree with confidence intervals (CI)
################### (1) Sample branch lengths (--format a)
## For FRI (Chr4:269719) in Santo Antão
$SampleBranchLengths \
-i chr4_SA_relate \
-o chr4_SA_relate_resample \
-m 2.512032e-9 \
--coal SA_gw_popsize.coal \
--format a \
--num_samples 200 \
--first_bp 269719 \
--last_bp 269719 \
--seed 1 

## For FLC (Chr5: 3179333) in Fogo + S1-1 from Lombo de Figueira in Santo Antão
$SampleBranchLengths \
-i chr5_FO_relate \
-o chr5_FO_relate_resample \
-m 2.1034e-9  \
--coal FO_gw_popsize.coal \
--format a \
--num_samples 200 \
--first_bp 3179333 \
--last_bp 3179333 \
--seed 1 

################### (1) Plot the genealogical tree with CI
## For FRI (Chr4:269719) in Santo Antão
$TreeViewSample \
--haps SA_Prepared_chr4.haps \
--sample SA_Prepared_chr4.sample \
--anc chr4_SA_relate.anc \
--mut chr4_SA_relate.mut \
--dist chr4_SA_relate.dist \
--poplabels $poplabels_SA \
--bp_of_interest 269719 \
--years_per_gen 1 \
-o FRI_genealogical_tree_CI

## For FLC (Chr5: 3179333) in Fogo + S1-1 from Lombo de Figueira in Santo Antão
$TreeViewSample \
--haps FO_Prepared_chr5.haps \
--sample FO_Prepared_chr5.sample \
--anc chr5_FO_relate.anc \
--mut chr5_FO_relate.mut \
--dist chr5_FO_relate.dist \
--poplabels $poplabels_FO \
--bp_of_interest 3179333 \
--years_per_gen 1 \
-o FLC_genealogical_tree_CI

#######################################################################################################################################

############################################
################ (4) CLUES  ################
############################################

### Note: For FRI 232X, we integrated a pseudo-ancestor individual (from our inferred CVI ancestral states) as an outgroup 
### for the Santo Antão population. Then, we inferred the genome-wide and local genealogies and the importance sampling in RELATE as 
### shown before with adjustment of mutation rate (-m) to 3.24e-10 and 4.193e-10 for the genome-wide and local genealogies, respectively

################### (1) Sample branch lengths (--format b)
## For FRI (Chr4:269719) in Santo Antão + a pseudo-ancestor individual
$SampleBranchLengths \
-i chr4_SA_relate \
-o chr4_SA_relate_clues \
-m 2.512032e-9 \
--coal SA_Ancestor_gw_popsize.coal \
--format b \
--num_samples 200 \
--first_bp 269719 \
--last_bp 269719 \
--seed 1 

## For FLC (Chr5: 3179333) in Fogo + S1-1 from Lombo de Figueira in Santo Antão
$SampleBranchLengths \
-i chr5_FO_relate \
-o chr5_FO_relate_clues \
-m 2.1034e-9  \
--coal FO_gw_popsize.coal \
--format b \
--num_samples 200 \
--first_bp 3179333 \
--last_bp 3179333 \
--seed 1 

################### (2) Inference of selection
## For FRI (Chr4:269719) in Santo Antão  + a pseudo-ancestor individual
python $infer_clues \
--times chr4_SA_relate_clues \
--popFreq 0.751322 \
--tCutoff 5000 \
--timeBins ${clues_timebins}/timebins_2epochs.txt \
--coal SA_Ancestor_gw_popsize.coal \
--sMax 1 \
--df 100 \
--dom 0 \
--out chr4_SA_FRI_inference

## For FLC (Chr5: 3179333) in Fogo + S1-1 from Lombo de Figueira in Santo Antão
python $infer_clues \
--times chr5_FO_relate_clues \
--popFreq 0.992958 \
--tCutoff 7000 \
--timeBins ${clues_timebins}/timebins_3epochs.txt \
--coal FO_gw_popsize.coal \
--sMax 1 \
--df 100 \
--dom 0 \
--out chr5_FO_FRI_inference

################### (3) Plotting the posterior allele frequency trajectory
## For FRI (Chr4:269719) in Santo Antão
python $plot_traj chr4_SA_FRI_inference chr4_SA_FRI_inference

## For FLC (Chr5: 3179333) in Fogo + S1-1 from S_Fig subpopulation in Santo Antão
python $plot_traj chr5_FO_FRI_inference chr5_FO_FRI_inference


#######################################################################################################################################

################### Citations for used tools
# (1) RELATE: Speidel, L., Forest, M., Shi, S. & Myers, S. R. A method for genome-wide genealogy estimation for thousands of samples. Nat. Genet. 2019 519 51, 1321–1329 (2019).
# (2) CLUES: Stern, A. J., Wilton, P. R. & Nielsen, R. An approximate full-likelihood method for inferring selection and allele frequency trajectories from DNA sequence data. PLOS Genet. 15, e1008384 (2019).
# (3) BCFTOOLS: Li, H. A statistical framework for SNP calling, mutation discovery, association mapping and population genetical parameter estimation from sequencing data. Bioinformatics 27, 2987 (2011).

                                           #--------------------- END ---------------------#
