#!/bin/bash

###
#		For merging
###
vcf=/srv/netscratch/irg/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_19-05-15.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-05-15.vcf.b.gz_snps.vcf.b.gz_ann.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_S1-1_v2_19-06-15.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-06-19_cvis.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_cviClean_11-7-18_plusCvi0.vcf.b.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20.vcf.b.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/superVcf_11-7-18.vcf.b.gz

newData=
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_pogo_2020-01-07/
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_patagonia_19-07-17/
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShoreS1-1_v2_19-06-15_2/
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcfShore_check_19-07-02/
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_check1_19-06-17/
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-18/
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-02-08/
	# /netscratch/dep_coupland/grp_hancock/andrea/vcfShore_afr_18-11-24/

vcfNew=/srv/netscratch/irg/grp_hancock/andrea/vcf/cvi_africa_and1001.EVA.vcf.gz
	# /srv/netscratch/irg/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_pogoniulus_2020-01-11.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_S1-1_v2_19-06-15.vcf.b.gz_cvi.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-07-04_cvis.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-02-18.vcf.b.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20.vcf.b.gz



vcfAfr1001=/srv/biodata/irg/grp_hancock/andrea/vcf/final/africa_and1001.EVA.vcf.gz
vcfMad=/srv/biodata/irg/grp_hancock/andrea/vcf/final/madeira.vcf.gz
# tabix -p vcf ${vcfAfr1001}
# sleep 30s
# tabix -p vcf ${vcfMad}
# sleep 30s
# tabix -p vcf ${vcf}
# sleep 30s

samples=/srv/biodata/irg/grp_hancock/VCF/capeVerdeos_clean_final_2020-05-20_plusCvi0_plusLyrata.txt
	# /srv/biodata/irg/grp_hancock/VCF/capeVerdeos_clean_final_2020-05-20_plusCvi0.txt

# echo "go samples!"
# bcftools view -S ${samples} --force-samples -I ${vcf} | bgzip -c > ${vcf}_cvi_2.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcf}_cvi_2.vcf.b.gz
# sleep 30s
# echo "go Merge!"
# bcftools merge ${vcf}_cvi_2.vcf.b.gz ${vcfAfr1001} | bgzip -c > ${vcfNew}

#  bcftools merge ${newData}/*all.srt.vcf.b.gz | bgzip -c > ${vcf}
# sleep 30s
# tabix -p vcf ${vcfNew}
# sleep 30s
# cp ${vcf}* /srv/biodata/dep_coupland/grp_hancock/VCF/
# sleep 30s
# vcf=${vcfNew}
# bcftools view -v "snps" --force-samples -I ${vcf} | bgzip -c > ${vcf}_snps.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcf}_snps.vcf.b.gz
# sleep 30s
# bcftools view -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcf}_snps.vcf.b.gz | bgzip -c > ${vcf}_snps.vcf.b.gz_chrOnly.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcf}_snps.vcf.b.gz_chrOnly.vcf.b.gz

# cp ${vcf}_snps.vcf.b.gz* /srv/biodata/dep_coupland/grp_hancock/VCF/
#
# echo "go decompress!"

# bgzip -cd ${vcfNew} > /srv/netscratch/irg/grp_hancock/andrea/vcf/cvi_africa_and1001.EVA.vcf.gz.vcf




# Now with Madeira
bcftools merge ${vcfNew} ${vcfMad} | bgzip -c > ${vcfNew}_madera.vcf.b.gz
tabix -p vcf ${vcfNew}_madera.vcf.b.gz

# bgzip -cd ${vcfNew}_snps.vcf.b.gz > ${vcfNew}_snps.vcf

###
#		If you want to pull out CVIs
###
echo "Game begins!"
# 
cviClean=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt
# vcfNew=${vcf}_cviClean2-11-7-18.vcf.b.gz
morPaperSamples=${HOME}/moroccan_paper_clean_IDs_plink.txt
# bcftools view -S ${morPaperSamples} --force-samples -I ${vcf} | bgzip -c > ${vcf}_morPaper.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcf}_morPaper.vcf.b.gz
# sleep 30s
# bcftools view -v "snps" --force-samples -I ${vcfNew} | bgzip -c > ${vcfNew}_snps.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcfNew}_snps.vcf.b.gz
# cp ${vcfNew}* /srv/biodata/dep_coupland/grp_hancock/VCF/
# bgzip -cd ${vcfNew}_snps.vcf.b.gz > ${vcfNew}_snps.vcf.b.gz.vcf
# cat ${vcfNew}_snps.vcf.b.gz.vcf | wc -l


###
#		Just check S21-8
###
vcfCheck=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_checkS21-8.vcf
# bgzip -c /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run579/S21-8.4073_H579.all.srt.vcf > /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run579/S21-8.4073_H579.all.srt.vcf2.b.gz
# bgzip -c /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run583/S21-8.4073_H583.all.srt.vcf > /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run583/S21-8.4073_H583.all.srt.vcf2.b.gz
# tabix -p vcf /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run579/S21-8.4073_H579.all.srt.vcf2.b.gz
# tabix -p vcf /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run583/S21-8.4073_H583.all.srt.vcf2.b.gz

# bcftools merge /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run579/S21-8.4073_H579.all.srt.vcf2.b.gz /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run583/S21-8.4073_H583.all.srt.vcf2.b.gz /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-18/S21-8.4073_H.all.srt.vcf.b.gz /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-18/S21-9.4073_I.all.srt.vcf.b.gz /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-18/F13-301.3968_BP.all.srt.vcf.b.gz | bgzip -c > ${vcfCheck}.b.gz
# sleep 30s 
# tabix -p vcf ${vcfCheck}.b.gz
# sleep 30s
# bgzip -cd ${vcfCheck}.b.gz > ${vcfCheck}

# Merge it actually with all Cvis
#
# tabix -p vcf ${vcfNew}_cvi.b.gz
# bcftools merge ${vcfNew}_cvi.b.gz /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run579/S21-8.4073_H579.all.srt.vcf2.b.gz /srv/biodata/dep_coupland/grp_hancock/andrea/vcfShore_19-04-24_4073H_run583/S21-8.4073_H583.all.srt.vcf2.b.gz | bgzip -c > ${vcfNew}_cvi_plusPartS21-8.vcf.b.gz
# sleep 30s
# bgzip -cd ${vcfNew}_cvi_plusPartS21-8.vcf.b.gz > ${vcfNew}_cvi_plusPartS21-8.vcf

###
#		PCA
###
cvis=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_check_19-06-19_minus2.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-04-23_plusNames.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-04-23_plusCvi0_no1out.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-04-23_plusCvi0.txt
# 
# bcftools view -S ${cvis} --force-samples -I ${vcfNew}_snps.vcf.b.gz | bgzip -c > ${vcfNew}_snps.vcf.b.gz_cvi.vcf.b.gz
# sleep 30s
# bcftools view -v "snps" --force-samples -I ${vcfNew}_santos.b.gz | bgzip -c > ${vcfNew}_santos.b.gz_snps.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcfNew}_snps.vcf.b.gz_cvi.vcf.b.gz
# sleep 30s
# bcftools view -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcfNew}_snps.vcf.b.gz | bgzip -c > ${vcfNew}_snps.vcf.b.gz_chrOnly.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcfNew}_snps.vcf.b.gz_chrOnly.vcf.b.gz
# sleep 30s

# results=/srv/biodata/dep_coupland/grp_hancock/andrea/analysis/pca/cvi_s1-1_19-07-15
# mkdir -p ${results}

# ${HOME}/software/plink64/./plink --vcf ${vcfNew}_snps.vcf.b.gz_chrOnly.vcf.b.gz --pca --indep-pairwise 50 10 0.1 --geno 0 --out ${results}

# ${HOME}/software/plink64/./plink --vcf ${vcfNew}_snps.vcf.b.gz_cvi.vcf.b.gz_chrOnly.vcf.b.gz --pca --out ${results}

# bgzip -cd ${vcfNew} > ${vcfNew}.vcf

###
#		snpEff
###
vcf=${vcfNew}_snps.vcf.b.gz
vcfAnn=${vcf}_ann.vcf.b.gz
# java -Xmx4g -jar /home/fulgione/software/snpEff/snpEff/snpEff.jar -c /home/fulgione/software/snpEff/snpEff/snpEff.config -v TAIR10.26 ${vcf} | bgzip -c > ${vcfAnn}
# cp ${vcfAnn} /srv/biodata/dep_coupland/grp_hancock/VCF/




