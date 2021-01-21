#!/bin/bash

echo $1

plink=/srv/netscratch/irg/grp_hancock/software/plink64


vcf=/srv/netscratch/irg/grp_hancock/andrea/vcf/superVcf_2019-11-07.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07.vcf.b.gz
vcfNew=/srv/biodata/irg/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_chloroplastCviPaper_2020-09-21.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjWithinCviPaper_2020-05-28.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjCviPaper_all20.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjWithinCviPaper.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjCviPaper.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-28_africa360.vcf.b.gz

samples=/srv/biodata/irg/grp_hancock/VCF/capeVerdeos_clean_final_2020-05-20_plus2cvi0.txt
	# /home/fulgione/data/cviOnePerStandUpTo20_andRandom20_noMadeira.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_final_2020-05-20_plus2cvi0.txt
	# /home/fulgione/data/cviOnePerStand_andRandom20_noMadeira.txt
	# /home/fulgione/data/cviOnePerStand_andRandom20.txt
	# /home/fulgione/African360_plink.txt
	
# tabix -p vcf ${vcf}
# sleep 30s
# bcftools view -S ${samples} -v "snps" --force-samples -r "chloroplast" -I ${vcf} | bgzip -c > ${vcfNew}
# sleep 30s
# tabix -p vcf ${vcfNew}

# sleep 30s
# bcftools view -v "snps" --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcfNew} | bgzip -c > ${vcfNew}_snps.vcf.b.gz
# sleep 30s
bgzip -cd ${vcfNew} > ${vcfNew}_snps.vcf
sleep 30s

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Vcf_selectSegregatingSites ${vcfNew}_snps.vcf
sleep 30s
bgzip -c ${vcfNew}_snps.vcf_segregOnly.vcf > ${vcfNew}_snps.vcf_segregOnly.vcf.b.gz
sleep 30s
tabix -p vcf ${vcfNew}_snps.vcf_segregOnly.vcf.b.gz
sleep 30s 
# bgzip -cd ${vcfNew}_snps.vcf.b.gz > ${vcfNew}_snps.vcf
echo "num snps"
cat ${vcfNew}_snps.vcf_segregOnly.vcf | wc -l

