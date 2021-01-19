#!/bin/bash

###
###
#		This script assumes that you have the ./java/ folder from GitHub in your home directory
# 		If not so, you will need to change some paths (e.g. to the libraries in ./java/lib/)
###
###

gitPath="path to CVI directory downloaded from github"
cd ${gitPath}

###
#		VCF to SNP matrix
###
matrix="Specify path to the SNP matrix created by matrix_cvi.command"
samples=./data/santos_clean_2021-01-18.txt
samples2=./data/1001plusMor_noCviCan.txt

for mask in ./data/*.gz
do
	gzip -cd ${mask} > ${mask}.mask.txt
	java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Qtl_toGenes_toVarians_wtIslands ${matrix} ${samples} ${samples2} ${mask}.mask.txt
	rm ${mask}_mask.txt
done

 
###
#		Now for the whole genome:
###

mask=./data/TAIR10_GFF3_genes_transposons.gff_wholeGenome2.txt.gz
gzip -cd ${mask} > ${mask}_mask.txt
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Qtl_toGenes_toVar    ians_wtIslands_wgPrivateToCvi ${matrix} ${samples} ${samples2} ${mask}_mask.txt
rm ${mask}_mask.txt

