#!/bin/bash

# For all our samples
#for rawFile in /home/lv70590/Andrea/runningShore/allSort/*gz
#do

#for i in {0..20}
#do
	#sbatch java.slrm $i
#done
#for j in 0 1 2
#do
	# for i in 6933 6971 7077 7327 8357 9506 9509 9510 9511 9512 9513 9514 9515 9517 9519 9520 9521 9522 9523 9524 9525 9526 9527 9529 9531 9532 9534 9535 9537 9539 9540 9541 9544 9546 9547 9548 9551 9552 9553 9556 9557 9558 9559 9560 9561 9562 9564 9565 9567 9568 9569 9571 9573 9574 9576 9577 9578 9579 9581 9582 9584 9585 9586 9587 9588 9589 9590 9591 9592 9593 9594 9595 9596 9597 9599 9601 9602 9820 9821 9822 9823 9824 9825 9826 9827 9828 9830 9831 9833 9834 9835 9836 9838 9839 9840 9841 9843 9844 9845 9846 9847 9848 9849 9850 9851 9852 9853 9854 9855 9856 9858 9859 9861 9864 9866 9867 9868 9870 9873 9874 9875 9876 9877 9878 9880 9881 9882 9885 9886 9888 9890 9891 9892 9894 9897 9898 9899 9900 9901 9902 9903 9904 9906
	
	# Total, there are: 1541 samples
	# for i in {0..1545}
	
	# Rotten pi
	#for i in 930 1001 1007 1054 
	
	# For chromosomes...
	#for i in {1..5}
	
	# do
	#  for j in {0..35}
	# We have: [56, 62, 145] samples in the 3 refugia
	# for j in 0 # 1 2
	#  do
	#  for i in {0..980}
	
	# We have: 13 unique ADMIXTURE groups
	#for i in {0..13} 
	# do
	# How many samples per cluster: [117, 92, 184, 79, 156, 64, 171, 25, 110, 16, 14, 9, 25]
	#  for j in {0..185}
	
	# For single runs
	#for file in /netscratch/dep_coupland/grp_hancock/andrea/superVcf_12-7-18_santo_Chr1.vcf.gz /netscratch/dep_coupland/grp_hancock/andrea/superVcf_12-7-18_santo_Chr2.vcf.gz /netscratch/dep_coupland/grp_hancock/andrea/superVcf_12-7-18_santo_Chr3.vcf.gz /netscratch/dep_coupland/grp_hancock/andrea/superVcf_12-7-18_santo_Chr4.vcf.gz /netscratch/dep_coupland/grp_hancock/andrea/superVcf_12-7-18_santo_Chr5.vcf.gz # {0..5}
	#do
# scan=xpClr_5percent_tail
# 187 scan=xpEhh_5percent_tail
# 188 # scan=wihs_5percent_tail
# 189 scan=clr_5percent_tail

# for scan in all wihs_5percent_tail xpClr_5percent_tail xpEhh_5percent_tail clr_5percent_tail
# do

# for i in {27..41}
	# 5 6 7 10 22 23  26 27 29 30
	# {0..25}
# do
#  	bsub -q multicore20 -n 10 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./qtls_genes_var2.command ${i} ${scan} big"
# done
# done
for i in {0..5}
	# /home/fulgione/qtls/candGenes_masks_19-10-25/functionalVariants_qtl.txt_variants_mask.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/qtls_small_xpClr_5percent_tail_2019-11-29/*_mask.txt
	# 8 23
	# /srv/biodata/dep_coupland/grp_hancock/andrea/qtls_small_xpClr_5percent_tail_2019-11-29/*_mask.txt
	# 5 6 7 10 22 23  26 27 29 30
	# 
	# {0..130}
	# 5 6 20
	# 1000000 500000 100000 50000
	# /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_phenotype_masks_2019-11-06/spec_*mask.txt.b.gz.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_phenotype_masks/*
	# /home/fulgione/qtls/candGenes_masks_19-10-25/*_mask.txt
	# {0..90} 
	# Qtl masks in: /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_phenotype_masks
	# 
	# /home/fulgione/qtls/candGenes_masks_19-10-25/*_mask.txt
	# /home/fulgione/qtls/candGenes_masks_19-10-25/functionalVariants_qtl.txt_variants_mask.txt
	# /home/fulgione/qtls/candGenes_masks_19-10-25/*
	# /home/fulgione/qtls/qtl_masks_19-10-25/*
	# /home/fulgione/candidate_masks/* 
	# {0..55} # {0..90} # 1000  5000 10000 50000 100000 200000 500000 1000000
# for file in /srv/netscratch/dep_coupland/grp_hancock/andrea/mehmet_samples/*Ids
do
	# echo ${i}
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./balancing_selection_stats.command ${file}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cviFoSa.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cviMor.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cviHa.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cviCan.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cviSma.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cviNma.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cviRif.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cvi1001andMor.command ${i}" 
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pi_cvi1001.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./pairwDiff.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./matrix_cvi.command ${i}"
	bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./matrix_s1-1.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./matrix_alpina.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./piRun.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./piRun2.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./piRun3.command ${i}"
	# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./piRun4.command ${i}"
	# bsub -q multicore40 -n 10 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./runOne.command ${i} ${scan}"
	# bsub -q multicore40 -n 10 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./qtls_genes_var.command ${i}"
	# bsub -q multicore20 -n 10 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./qtls_genes_var2.command ${i} all small"
	# bsub -q multicore40  -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./popSizeAbc_file.command ${i}"
	# echo ${file}
	# bsub -q multicore40 "./twoRun.command ${file}"
done
# done
