#!/bin/bash

echo $1

###
#		Get functional masks
###

vcf=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_s1-1v2_2020-07-03.vcf.b.gz
	# 
	# The actual one aligned to TAIR10:
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_11-7-18.vcf.b.gz
vcfAnn=${vcf}_ann.vcf.b.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/superVcf_11-7-18.vcf.b.gz_ann.vcf.b.gz

###
#	Just annotate the VCF
###

# To TAIR10
# java -Xmx4g -jar /home/fulgione/software/snpEff/snpEff/snpEff.jar -c /home/fulgione/software/snpEff/snpEff/snpEff.config -v TAIR10.26 ${vcf} | bgzip -c > ${vcfAnn}
# To s1-1
# java -Xmx4g -jar /home/fulgione/software/snpEff/snpEff/snpEff.jar -c /home/fulgione/software/snpEff/snpEff/snpEff.config -v s1-1.v2 ${vcf} | bgzip -c > ${vcfAnn}

#sleep 30s

workVcfAnn=${vcfAnn}.vcf
	# /netscratch/dep_coupland/grp_hancock/andrea/superVcf_11-7-18_noIndel.vcf.b.gz_ann.vcf

#cp /netscratch/dep_coupland/grp_hancock/andrea/superVcf_11-7-18.vcf.b.gz_ann.vcf ${workVcfAnn}

#bgzip -cd ${vcfAnn} > ${workVcfAnn}
#sleep 30s

samples=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_final_2020-05-20.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-04-23_plusCvi0.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-2-18.txt

results=/srv/netscratch/dep_coupland/grp_hancock/andrea/cviMask_s1-1_2020-07-06/
	# The results on TAIR10:
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/cviMask_2019-11-19/
	# /netscratch/dep_coupland/grp_hancock/andrea/cviMask_10-9-18/
	# 21-7-18/
mkdir -p ${results}

###
#	Mainly for the fake whole-genome vcf with all possible variants
###

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SnpEff_masksFromSuperVcf ${workVcfAnn} ${samples} ${results}
sleep 30s

cp -r ${results} /srv/biodata/dep_coupland/grp_hancock/andrea/

bgzip -c ${workVcfAnn}_for0-4degenerate_wg.vcf > ${workVcfAnn}_for0-4degenerate_wg.vcf.b.gz
sleep 30s

###
#	Annotate the fake vcf with all possible Single Nucleotide variants
###

vcf=${workVcfAnn}_for0-4degenerate_wg.vcf.b.gz
vcfAnn=${vcf}_ann.vcf.b.gz
# 
# java -Xmx4g -jar /home/fulgione/software/snpEff/snpEff/snpEff.jar -c /home/fulgione/software/snpEff/snpEff/snpEff.config -v TAIR10.26 ${vcf} | bgzip -c > ${vcfAnn}
# 
java -Xmx4g -jar /home/fulgione/software/snpEff/snpEff/snpEff.jar -c /home/fulgione/software/snpEff/snpEff/snpEff.config -v s1-1.v2 ${vcf} | bgzip -c > ${vcfAnn}
sleep 30s

cp ${vcfAnn} /srv/biodata/dep_coupland/grp_hancock/andrea/vcf

echo "breakpoint!"




results=/srv/netscratch/dep_coupland/grp_hancock/andrea/zeroFourfold_s1-1_2020-07-06/
	# To Tair10
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/zeroFourfold_2019-11-19/
	# /netscratch/dep_coupland/grp_hancock/andrea/zeroFourfold_7-10-18/
	# zeroFourfold_28-7-18/
	# zeroFourfold_25-7-18/

bgzip -cd ${vcfAnn} > ${vcfAnn}.vcf
mkdir -p ${results}
sleep 30s

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SnpEff_04foldDegenerate_masks ${vcfAnn}.vcf ${results}
sleep 30s

cp -r ${results} /srv/biodata/dep_coupland/grp_hancock/andrea/
