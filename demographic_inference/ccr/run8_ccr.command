#!/bin/bash

## Warning: this will create many runs. Choose the right ./CVI/scripts/demographic_inference/ccr/data/*_comparisons.txt file to run a subset
## To run as:
#
# while read p; do
#	./run8_all_ccr.slrm ${p}
# done<./CVI/scripts/demographic_inference/ccr/data/*_comparisons.txt 


p=$1
a=`echo ${p} | cut -f 1 -d "x"`
b=`echo ${p} | cut -f 2 -d "x"`
a1=`echo ${a} | cut -f 1 -d "_"`
a2=`echo ${a} | cut -f 2 -d "_"`
a3=`echo ${a} | cut -f 3 -d "_"`
a4=`echo ${a} | cut -f 4 -d "_"`
a5=`echo ${a} | cut -f 5 -d "_"`
a6=`echo ${a} | cut -f 6 -d "_"`
a7=`echo ${a} | cut -f 7 -d "_"`
a8=`echo ${a} | cut -f 8 -d "_"`

b1=`echo ${b} | cut -f 1 -d "_"`
b2=`echo ${b} | cut -f 2 -d "_"`
b3=`echo ${b} | cut -f 3 -d "_"`
b4=`echo ${b} | cut -f 4 -d "_"`
b5=`echo ${b} | cut -f 5 -d "_"`
b6=`echo ${b} | cut -f 6 -d "_"`
b7=`echo ${b} | cut -f 7 -d "_"`
b8=`echo ${b} | cut -f 8 -d "_"`

echo  ${a1} ${a2} ${b1} ${b2}

TMPDIR= "Path to working directory"
work=${TMPDIR}/${p}
mkdir -p ${work}
RES= # path to results folder 
DIR=./CVI/scripts/demographic_inference/ccr/ccr_internalScripts
WORK= # Path to input files folder

mkdir -p ${WORK}
mkdir -p ${RES}
cp -r ${DIR}/* ${work}/

# Merge vcf
bash ${work}/scripts/preprocessing/merge_16_vcf.sh ${a1} ${a2} ${a3} ${a4} ${a5} ${a6} ${a7} ${a8} ${b1} ${b2} ${b3} ${b4} ${b5} ${b6} ${b7} ${b8}

# create input
for i in {1..5}
do	
	/usr/bin/python ${DIR}/scripts/preprocessing/msmc_gen.py ${i} 16 ${WORK}/data/merged_vcfs/${p}.vcf.gz ${WORK}/data/masks/${a1}.txt.gz ${WORK}/data/masks/${a2}.txt.gz ${WORK}/data/masks/${a3}.txt.gz ${WORK}/data/masks/${a4}.txt.gz ${WORK}/data/masks/${a5}.txt.gz ${WORK}/data/masks/${a6}.txt.gz ${WORK}/data/masks/${a7}.txt.gz ${WORK}/data/masks/${a8}.txt.gz ${WORK}/data/masks/${b1}.txt.gz ${WORK}/data/masks/${b2}.txt.gz ${WORK}/data/masks/${b3}.txt.gz ${WORK}/data/masks/${b4}.txt.gz ${WORK}/data/masks/${b5}.txt.gz ${WORK}/data/masks/${b6}.txt.gz ${WORK}/data/masks/${b7}.txt.gz ${WORK}/data/masks/${b8}.txt.gz
done

mkdir -p ${work}/data
cp ${WORK}/data/msmc_input/${p}.chr?_noRC.txt ${work}/data/

# Run Msmc
mkdir -p ${work}/within
mkdir -p ${work}/across
${work}/msmc2 -I 0,1,2,3,4,5,6,7 -t 10 -p 1*2+20*1+2*5 -o ${work}/within/${a}_mask ${work}/data/${p}.chr?_noRC.txt
${work}/msmc2 -I 8,9,10,11,12,13,14,15 -t 10 -p 1*2+20*1+2*5 -o ${work}/within/${b}_mask ${work}/data/${p}.chr?_noRC.txt
${work}/msmc2 -I 4,5,6,7,12,13,14,15 -P 0,0,0,0,1,1,1,1 -t 10 -p 1*2+20*1+2*5 -o ${work}/across/${p}_mask ${work}/data/${p}.chr?_noRC.txt

# Run decode, if ncessary
#for i in {1..5}
#do
#	$HOME/software/msmc2-2.0.0/build/./decode -I 9,12 -m 0.000026 -r 0.0000026 $WORK/data/msmc_input/${p}.chr$i.txt_noCentrom.txt > $WORK/data/msmcTmrca_fogo1_noCentrom_${p}.chr$i.txt
#done

# run combine
mkdir -p ${work}/results
python ${work}/combineCrossCoal.py ${work}/across/${p}_mask.final.txt ${work}/within/${a}_mask.final.txt ${work}/within/${b}_mask.final.txt > ${work}/results/${p}_mask.combined.txt

# get pop size for within and between
python ${work}/scripts/postprocessing/get_popsize.py ${work}/across/${p}_mask > ${work}/results/${p}_mask.popsize.txt
python ${work}/scripts/postprocessing/get_popsize.py ${work}/within/${a}_mask > ${work}/results/${a}_mask.popsize.txt
python ${work}/scripts/postprocessing/get_popsize.py ${work}/within/${b}_mask > ${work}/results/${b}_mask.popsize.txt


# Save results

mkdir -p ${RES}/results/
mkdir -p ${RES}/across/
mkdir -p ${RES}/within/

cp -r ${work}/* ${RES}/
rm -rf ${work}/*

