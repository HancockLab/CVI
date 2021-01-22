###
# Genotype calling 
###

Pipelines for genotype calling including calling SNPs, indels and matches to the reference genome. 

### SNP_and_Indel_calling_GATK4

Pipeline to call SNPs and indels based on a modified version of the GATK 4 (62) best practices workflow for germline short variant discovery. Used for GWAS to be able to analyse indels.

### SNP_calling_SHORE

Pipeline to call SNPs and matches to the reference as in Durvasula and Fulgione et al. 2017. Used for most analyses in the paper, excluding GWAS and msmc-ccr.

### fulgiP.command 

Pipeline to conservatively call SNPs and matches to the refence, used for msmc-ccr analyses. Also calls S-locus haplogroups

