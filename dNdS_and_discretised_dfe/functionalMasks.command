#!/bin/bash

echo $1

###
#		Get functional masks
###

vcf= # Path to vcf
vcfAnn=${vcf}_ann.vcf.b.gz

###
#	Just annotate the VCF
###
java -Xmx4g -jar "Path to "/snpEff.jar -c "Path to "/snpEff.config -v TAIR10.26 ${vcf} | bgzip -c > ${vcfAnn}

workVcfAnn=${vcfAnn}.vcf

#bgzip -cd ${vcfAnn} > ${workVcfAnn}
samples=./data/capeVerdeos_clean_final_2021-01-18.txt
results= # Path to results folder
mkdir -p ${results}

###
#	Mainly for the fake whole-genome vcf with all possible variants
###

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SnpEff_masksFromSuperVcf ${workVcfAnn} ${samples} ${results}
sleep 30s

bgzip -c ${workVcfAnn}_for0-4degenerate_wg.vcf > ${workVcfAnn}_for0-4degenerate_wg.vcf.b.gz
sleep 30s

###
#	Annotate the fake vcf with all possible Single Nucleotide variants
###

vcf=${workVcfAnn}_for0-4degenerate_wg.vcf.b.gz
vcfAnn=${vcf}_ann.vcf.b.gz
# 
java -Xmx4g -jar "Path to "/snpEff.jar -c "Path to "/snpEff.config -v TAIR10.26 ${vcf} | bgzip -c > ${vcfAnn}
sleep 30s

echo "breakpoint!"

results= # Path to masks folder 

bgzip -cd ${vcfAnn} > ${vcfAnn}.vcf
mkdir -p ${results}
sleep 30s

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SnpEff_04foldDegenerate_masks ${vcfAnn}.vcf ${results}
sleep 30s

