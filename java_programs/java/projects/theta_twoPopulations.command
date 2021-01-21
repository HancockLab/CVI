#!/bin/bash

# Run as ./pi_cviFoSa.command 100000
# To compute summary statistics in 100 Kb windows

###
###
#		This script assumes that you have the ./java/ folder from GitHub in your home directory
# 		If not so, you will need to change some paths (e.g. to the libraries in ./java/lib/)
###
###

cd ./CVI/java_programs/java/projects/
windowSize=${1}

###
#		Compute theta pi, W and other statistics between two populations in windows across the genome of your choice
###

matrix= # Path to SNP matrix created by matrix_cvi.command 
results= # Path to results
mkdir -p ${results}_${windowSize}

echo "Go Pairwise cvi!"

pop1= "PLINK file for sample IDs in population 1"
pop2== "PLINK file for sample IDs in population 2"

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Pairwise_shore_cviMor ${matrix} ${results} ${windowSize} ${pop1} ${pop2}

