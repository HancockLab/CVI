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
vcf="path to the vcf"
bgzip -cd ${vcf} > ${vcf}.vcf
matrix="Specify path to the SNP matrix to be created"

###
###
#	Matrixing and eliminatinging positions with >2*average coverage
# 	This can be parallelised per chromosome (modify the for loop)
###
###

for chr in {1..5}
do
	java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.VcfCombined_to_snpMatrix_outHighCov ${vcf}.vcf ${chr} ${matrix}
	sleep 30s
done

# Concatenate the 5 chromosomes:
#
cat ${matrix}_c5q30_chr1.txt ${matrix}_c5q30_chr2.txt ${matrix}_c5q30_chr3.txt ${matrix}_c5q30_chr4.txt ${matrix}_c5q30_chr5.txt > ${matrix}
sleep 30s

# 
anc=./data/estSFS_s1-1_2020-09-25_ancestralState.txt_mlK.txt_toIntegrate.txt

###
# 		Add the reconstructed ancestral sequence to the matrix

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix} ${anc}
sleep 30s

echo "Added!"

