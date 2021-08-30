#!/bin/bash

gitPath="path to CVI directory downloaded from github"
cd ${gitPath}

plink="path to plink"
vcf="path to the large .vcf available in EVA"
vcfNew="insert full path for the new subset vcf"
out=./data/cviPaperNj

# If you want NJ within CVi:
# 
# samples=./data/capeVerdeos_clean_final_2020-05-20_plus2cvi0.txt

# If you want NJ of worldwide and CVI samples:
# 
samples=./data/cviOnePerStandUpTo20_andRandom20_noMadeira.txt

tabix -p vcf ${vcf}
sleep 30s
bcftools view -S ${samples} --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcf} | bgzip -c > ${vcfNew}
sleep 30s
tabix -p vcf ${vcfNew}
sleep 30s
bcftools view -v "snps" --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcfNew} | bgzip -c > ${vcfNew}_snps.vcf.b.gz
sleep 30s
bgzip -cd ${vcfNew}_snps.vcf.b.gz > ${vcfNew}_snps.vcf
sleep 30s
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Vcf_selectSegregatingSites ${vcfNew}_snps.vcf
sleep 30s
bgzip -c ${vcfNew}_snps.vcf_segregOnly.vcf > ${vcfNew}_snps.vcf_segregOnly.vcf.b.gz
sleep 30s
tabix -p vcf ${vcfNew}_snps.vcf_segregOnly.vcf.b.gz
sleep 30s 


############
# filtered #
############

vcfNew=${vcfNew}_snps.vcf_segregOnly.vcf.b.gz

$plink/./plink --vcf ${vcfNew} --biallelic-only --snps-only --make-bed --keep-allele-order --out ${out}

# get only unlinked SNPs
$plink/./plink --bfile ${out} --indep-pairwise 50 10 0.1 --keep-allele-order --out ${indep}.filter10

# extract unlinked SNPs
$plink/./plink --bfile ${out} --extract ${indep}.filter10.prune.in --make-bed --keep-allele-order --out ${out}.filter10.pruned

# use only non-missing SNPs
$plink/./plink --bfile ${out}.filter10.pruned --geno 0.000 --make-bed --keep-allele-order --out ${out}.filter10.pruned.nomissing

# Get raw file for NJ
$plink/./plink --bfile ${out}.filter10.pruned.nomissing --recodeA --out ${out}_nj --keep-allele-order

# Modify names in the ${out}_nj.raw file: "_" gets misinterpreted and split into columns 1 and 2
# 
while read line;do id1=$(echo ${line} | cut -d " " -f 1); id2=$(echo ${line} | cut -d " " -f 2); if (( "${id1}" == ${id2} ));then id=${id1}; else id=${id1}_${id2};fi; echo ${id}; done< ${out}_nj.raw > ${out}_nj.raw_names.txt

1001file=./data/1001genomes-accessions.txt
moroccan_paper_IDs=./data/moroccan_paper_IDs.txt
idsToPlants=./data/idsToPlants_19-04-18.txt

###
#	Assign samples to populations, islands, colors, symbols...
###

# For NJ with worldwide and CVI samples:
# 
while read line;do id=$(echo ${line}); while read line2;do id2=$(echo ${line2} | cut -d " " -f 1); if [[ "${id}" == "${id2}" ]];then col="#F0A3FF"; symb=0; region=eurasia; fi; done < ${1001file}; while read line2;do id2=$(echo ${line2} | cut -d " " -f 1); if [[ "${id}" == "${id2}" ]];then col="#2BCE48"; symb=18; region=morocco; fi; done < ${moroccan_paper_IDs}; while read line2;do id2=$(echo ${line2} | cut -d " " -f 1); if [[ "${id}" == "${id2}" ]];then name=$(echo ${line2} | cut -d " " -f 2 | head -c 1); if [[ "${name}" == "S" ]]; then col="#0075DC"; symb=16; region=santoAntao; elif [[ "${name}" == "F" ]];then col="#FFA405"; symb=17; region=fogo; fi; fi; done < ${idsToPlants}; echo -e ${id},${region},${col},${symb}; done < ${out}_nj.raw_names.txt > ${out}_nj.raw_names.txt_idsRegionColSymb.txt


# For NJ within CVI:
#
# while read line;do id=$(echo ${line}); while read line2;do id2=$(echo ${line2} | cut -d " " -f 1); if [[ "${id}" == "${id2}" ]];then name=$(echo ${line2} | cut -d " " -f 2 | head -c 1); if [[ "${name}" == "S" ]]; then col="#0075DC"; symb=16; region=santoAntao; elif [[ "${name}" == "F" ]];then col="#FFA405"; symb=17; region=fogo; fi; echo ${line2}; subpop=$(echo ${line2} | cut -d " " -f 2 | cut -d "-" -f 1); fi; done < ${idsToPlants};  echo -e ${id},${region},${col},${symb},${subpop}; done < ${out}_nj.raw_names.txt > ${out}_nj.raw_names.txt_idsRegionColSymb.txt




###
#	 Now plot them
###

Rscript neighborJoining_pca_plot_fig1b.R 


echo "The end!"





