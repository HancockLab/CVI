#!/bin/bash

echo $1

RUN=/srv/biodata/dep_coupland/grp_hancock/andrea

vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_28-3-18.vcf.b.gz
vcfNew=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_28-3-18_pnasPlusEastasia.vcf.b.gz
out=/srv/biodata/dep_coupland/grp_hancock/VCF/pnasAndAsia
indep=/srv/biodata/dep_coupland/grp_hancock/VCF/indepAfrAs

# plink=$HOME/software/plink64
plink=/srv/netscratch/dep_coupland/grp_hancock/software/plink64

#bcftools view -S $HOME/listOfAccessions -i 'FMT/GQ>=25 & DP>=3' -r Chr1,Chr2,Chr3,Chr4,Chr5 -m2 -M2 -v snps ${vcf} | bgzip -c > ${vcfNew}
#tabix -p vcf $vcfNew

# $plink/./plink --vcf ${vcfNew} --biallelic-only --snps-only --make-bed --keep-allele-order --out ${out}
#/srv/biodata/dep_coupland/grp_hancock/VCF/pnasAndAsia # data/Morocco1001.filtered







vcf=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07.vcf.b.gz
vcfNew=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjWithinCviPaper_2020-05-28.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjCviPaper_all20.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjWithinCviPaper.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjCviPaper.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-28_africa360.vcf.b.gz

samples=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_final_2020-05-20_plus2cvi0.txt
	# /home/fulgione/data/cviOnePerStandUpTo20_andRandom20_noMadeira.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_final_2020-05-20_plus2cvi0.txt
	# /home/fulgione/data/cviOnePerStand_andRandom20_noMadeira.txt
	# /home/fulgione/data/cviOnePerStand_andRandom20.txt
	# /home/fulgione/African360_plink.txt
	
# tabix -p vcf ${vcf}
# sleep 30s
# bcftools view -S ${samples} --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcf} | bgzip -c > ${vcfNew}
# sleep 30s
# tabix -p vcf ${vcfNew}
# sleep 30s
# bcftools view -v "snps" --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcfNew} | bgzip -c > ${vcfNew}_snps.vcf.b.gz
# sleep 30s
bgzip -cd ${vcfNew}_snps.vcf.b.gz > ${vcfNew}_snps.vcf
sleep 30s
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Vcf_selectSegregatingSites ${vcfNew}_snps.vcf
sleep 30s
bgzip -c ${vcfNew}_snps.vcf_segregOnly.vcf > ${vcfNew}_snps.vcf_segregOnly.vcf.b.gz
sleep 30s
tabix -p vcf ${vcfNew}_snps.vcf_segregOnly.vcf.b.gz
sleep 30s 
# bgzip -cd ${vcfNew}_snps.vcf.b.gz > ${vcfNew}_snps.vcf
# echo "num snps"
# cat ${vcf}_snps.vcf.b.gz | wc -l




############
# filtered #
############
vcfNew=${vcfNew}_snps.vcf_segregOnly.vcf.b.gz
out=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/cviPaperNj_withinCVI_2020-06-10
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/cviPaperNj_all20_2020-05-21
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/cviPaperNj_withinCVI_2020-05-20
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/cviPaperNj_2020-04-29
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/africa360_2019-11-28

$plink/./plink --vcf ${vcfNew} --biallelic-only --snps-only --make-bed --keep-allele-order --out ${out}

get only unlinked SNPs
$plink/./plink --bfile ${out} --indep-pairwise 50 10 0.1 --keep-allele-order --out ${indep}.filter10

# extract unlinked SNPs
$plink/./plink --bfile ${out} --extract ${indep}.filter10.prune.in --make-bed --keep-allele-order --out ${out}.filter10.pruned

# use only non-missing SNPs
$plink/./plink --bfile ${out}.filter10.pruned --geno 0.000 --make-bed --keep-allele-order --out ${out}.filter10.pruned.nomissing

# Get raw file for NJ
$plink/./plink --bfile ${out}.filter10.pruned.nomissing --recodeA --out ${out}_nj --keep-allele-order

# while read line;do id1=$(echo ${line} | cut -d " " -f 1); id2=$(echo ${line} | cut -d " " -f 2); if (( "${id1}" == ${id2} ));then id=${id1}; else id=${id1}_${id2};fi; echo ${id}; done< ${out}_nj > ${out}_nj.raw_names.txt


#1001file=
#moroccan_paper_IDs=
#idsToPlants=
# while read line;do id=$(echo ${line}); while read line2;do id2=$(echo ${line2} | cut -d " " -f 1); if [[ "${id}" == "${id2}" ]];then col=#F0A3FF; symb=0; region=eurasia; fi; done < ${1001file}; while read line2;do id2=$(echo ${line2} | cut -d " " -f 1); if [[ "${id}" == "${id2}" ]];then col=#2BCE48; symb=18; region=morocco; fi; done < ${moroccan_paper_IDs}; while read line2;do id2=$(echo ${line2} | cut -d " " -f 1); if [[ "${id}" == "${id2}" ]];then name=$(echo ${line2} | cut -d " " -f 2 | head -c 1); if [[ "${name}" == "S" ]]; then col=#0075DC; symb=16; region=santoAntao; elif [[ "${name}" == "F" ]];then col=#FFA405; symb=17; region=fogo; fi; fi; done < ${idsToPlants}; echo -e ${id},${region},${col},${symb}; done < ${out}_nj.raw_names.txt > ${out}_nj.raw_names.txt_idsRegionColSymb.txt

###
# 	pca
###

# outF=${HOME}/analysis/pca/cviPaper_all20_2020-05-21
outF=${HOME}/analysis/pca/cviPaper_wtCvi_2020-06-10
mkdir -p ${outF}
$plink/./plink --vcf ${vcfNew} --biallelic-only --snps-only --pca --indep-pairwise 50 10 0.1 --geno 0 --out ${HOME}/analysis/pca/cviPaper_all20_2020-06-10





# bgzip -cd ${vcfNew}_snps.vcf.b.gz > ${vcfNew}_snps.vcf
# echo "num snps"
# cat ${vcf}_snps.vcf.b.gz | wc -l






#bcftools view -i 'FMT/GQ>=25 & DP>=3' -r Chr1,Chr2,Chr3,Chr4,Chr5 -m2 -M2 -v snps data/superVcf_29-7-16.vcf.b.gz | bgzip -c > data/superVcf_29-7-16.chronly.biallelic.nomissing.quality.depth.vcf.b.gz

#plink --vcf data/superVcf_29-7-16.chronly.biallelic.nomissing.quality.depth.vcf.b.gz --biallelic-only --snps-only --keep data/labels-plink5.csv --indiv-sort f data/labels-plink5.csv --make-bed --keep-allele-order --out data/Morocco1001.filtered
###########
# fullset #
###########
## get only unlinked SNPs
#bsub -q normal "plink --bfile data/Morocco1001.filtered --indep-pairwise 50 10 0.1 --keep-allele-order --out data/indep.filtered"
# extract unlinked SNPs
#bsub -q normal "plink --bfile data/Morocco1001.filtered --extract data/indep.filtered.prune.in --make-bed --keep-allele-order --out data/Morocco1001.filtered.pruned"
# use only non-missing SNPs
#bsub -q normal "plink --bfile data/Morocco1001.filtered.pruned --geno 0.000 --make-bed --keep-allele-order --out data/Morocco1001.filtered.pruned.nomissing"
#plink --bfile data/Morocco1001.filtered.pruned.nomissing --recodeA --out data/Morocco1001.filtered.pruned.nomissing --keep-allele-order


############
# filtered #
############
# filter individuals
#bsub -q normal "plink --bfile data/Morocco1001.filtered --keep data/filter10.csv --indiv-sort f data/filter10.csv --make-bed --keep-allele-order --out data/Morocco1001.filtered.filter10"
# get only unlinked SNPs
#bsub -q normal "plink --bfile data/Morocco1001.filtered.filter10 --indep-pairwise 50 10 0.1 --keep-allele-order --out data/indep.filtered.filter10"
# extract unlinked SNPs
#bsub -q normal "plink --bfile data/Morocco1001.filtered.filter10 --extract data/indep.filtered.filter10.prune.in --indiv-sort f data/filter10.csv --make-bed --keep-allele-order --out data/Morocco1001.filtered.filter10.pruned"
# use only non-missing SNPs
#bsub -q normal "plink --bfile data/Morocco1001.filtered.filter10.pruned --geno 0.000 --indiv-sort f data/filter10.csv --make-bed --keep-allele-order --out data/Morocco1001.filtered.filter10.pruned.nomissing"

# Try PCA
#plink --bfile data/Morocco1001.filtered.filter10.pruned.nomissing --pca --out data/Morocco1001.filtered.filter10.pruned.nomissing
# Get raw file for NJ
#plink --bfile data/Morocco1001.filtered.filter10.pruned.nomissing --recodeA --out data/Morocco1001.filtered.filter10.pruned.nomissing --keep-allele-order




















#bcftools view -S ^$HOME/data/dwarves.txt --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I $vcfCvi4outNew | bgzip -c > $vcfCvi4outNewNoDw
#tabix -p vcf $vcfCvi4outNewNoDw

#bcftools merge $HOME/vcf/*all.srt.vcf.b.gz $vcfCvi3out | bgzip -c > $vcfCvi3out_final
#tabix -p vcf $vcfCvi3out_final

#cd $HOME/analysis/pca
#$HOME/software/plink64/./plink --vcf $vcfCviFinal --pca --indep-pairwise 50 10 0.1 --geno 0 --out $HOME/analysis/pca/cviFinal

#bgzip -cd $vcfCvi3out > $vcfCvi3out.vcf



vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_20-3-18.vcf.b.gz
vcfNewPart=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_28-3-18.vcf.b.gz


newData=/netscratch/dep_coupland/grp_hancock/andrea/vcfShore
vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_28-3-18.vcf.b.gz
vcfNew=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_19-4-18.vcf.b.gz
final=/srv/biodata/dep_coupland/grp_hancock/VCF

#bcftools view -S ^${HOME}/data/50out.txt --force-samples -I $vcf | bgzip -c > ${vcfNewPart}_part.vcf.b.gz
#bcftools view -s ^"AHLB" --force-samples -I $vcf | bgzip -c > ${vcfNew}_part.vcf.b.gz
#sleep 1m
#tabix -p vcf ${vcfNew}_part.vcf.b.gz
#sleep 1m
#bcftools merge ${vcfNew}_part.vcf.b.gz ${newData}/*all.srt.vcf.b.gz | bgzip -c > ${vcfNew}
#sleep 1m
#tabix -p vcf ${vcfNew}
#sleep 1m

#cp ${vcfNew}* ${final}/

#bgzip -cd $vcfNew > ${vcfNew}.vcf
#cp ${vcfNew}.vcf ${final}/
#rm ${vcfNew}.vcf

