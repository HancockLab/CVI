#!/bin/bash
#

# Run as:
## Loop through focus samples:
# for i in {0..250}
# do
## Loop through replicates
#	for j in {0..99} 
#	do
# 		./chromoPainter.command ${i} ${j}
# 	done
# done
# Run as - above


###
###
#		This script assumes that you have the ./java/ folder from GitHub in your home directory
# 		If not so, you will need to change some paths (e.g. to the libraries in ./java/lib/)
###
###


###
#	ChromoPainter prepare the files
###

cd ./CVI/java_programs/java/projects/

matrix= "Path to SNP matrix created by matrix_cvi.command "
results=./results
focalSamples=./scripts/population_structure/chromopainter/data/chromop_samples_cviPaper.txt
mkdir -p ${results}${1}/${2}/

# For random 20 samples from ADM clusters
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Build_ChromoPainter_input ${matrix} ${1} ${2} ${results} ${focalSamples}

# Transpose the SNP matrix
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.TransposIt ${results}${1}/${2}/haplo.txt

# Build recom_rate_infile
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Build_recom_rate_infile_chromoPainter ${results}${1}/${2}/ADM_params.map

# Cat it all together
cat ${results}${1}/${2}/firstLines.txt ${results}${1}/${2}/positions.txt ${results}${1}/${2}/haplo.txt.transp.geno > ${results}${1}/${2}/haplotype.in.file

hap=${results}${1}/${2}/haplotype.in.file
rec=${results}${1}/${2}/ADM_params.map_recomRateInfile
don=${results}${1}/${2}/donor_list_infile.txt
out=${results}${1}/${2}/results/
mkdir -p $out


### Now run

chromoP= "Path to /chromopainter-0.0.4"
${chromoP}/./chromopainter -g $hap -r $rec -f $don -j -n 100 -i 1000 -in -im -ip -o ${out}100_1000



