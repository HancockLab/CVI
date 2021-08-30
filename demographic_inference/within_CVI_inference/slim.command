#!/bin/bash

slim= # Path to slim
params=./CVI/demographic_inference/within_CVI_inference/cvi_3.slim
results= # Path to results - Warning! This path has to match two paths in cvi_3.slim

mkdir -p ${results}

${slim}/slim -d repl=${1} -d Ns=${2} -d Nf=${3} -d Tf=${4} -d Ttot=${5} -d NsStart=${6} -d NfStart=${7} ${params}

#mv <something interesting> ${results}


####
##	Compute jsfs		
####

santo=./CVI/demographic_inference/within_CVI_inference/data/santos_simula.txt
fogo=./CVI/demographic_inference/within_CVI_inference/data/fogos_simula.txt
matrix=${results}/cvi3_Ttot${5}_nsStart${6}_nfStart${7}_ns${2}_nf${3}_tf${4}_rep${1}.vcf
#bgzip -cd ${matrix}.b.gz > ${matrix}

###
##	Now do it! Jsfs
# 
cd ${HOME}/java/projects
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Jsfs_fromVcf_simulaSlim ${matrix} ${results}/ttot${5}_nsStart${6}_nfStart${7}_nsStart${6}_nfStart${7}_ns${2}_nf${3}_tf${4}_rep${1} ${santo} ${fogo}

# Clean up
bgzip -c ${matrix} > ${matrix}.b.gz
rm ${matrix}

bgzip -c ${matrix}_colonisers.vcf > ${matrix}_colonisers.vcf.b.gz
rm ${matrix}_colonisers.vcf


