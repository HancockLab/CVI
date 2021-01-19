#!/bin/bash

###
#	2 haps
###

while read p;
do
    ONE=`echo ${p} | cut -d "x" -f 1`
    TWO=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 2`      
    SAMPLE=`echo ${p} | cut -d "." -f 1`
   for chrom in 1 2 3 4 5;
    do
       submit2sge -q cluster -N msmcgen${SAMPLE} "/usr/bin/python preprocessing/msmc_gen.py ${chrom} 2 "$HOME"/msmc2-reloaded/data/merged_vcfs/${SAMPLE}.vcf.gz "$HOME"/msmc2-reloaded/data/masks/${ONE}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${TWO}.txt.gz"
    done
done</home/CIBIV/andreaf/msmc2-final/georgia_comparisons.txt



###
#	8 haps
###

#while read p;
#do
#    ONE=`echo ${p} | cut -d "x" -f 1`
#    TWO=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 2`
#    THR=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 3`
#    FOU=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 4`
#    FIV=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 5`
#    SIX=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 6`
#    SEV=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 7`
#    EIG=`echo ${p} | cut -d "." -f 1 | cut -d "x" -f 8`
#    SAMPLE=`echo ${p} | cut -d "." -f 1`
#   for chrom in 1 2 3 4 5;
#    do
#       submit2sge -q cluster -N msmcgen${SAMPLE} "/usr/bin/python preprocessing/msmc_gen.py ${chrom} 8 "$HOME"/msmc2-reloaded/data/merged_vcfs/${SAMPLE}.vcf.gz "$HOME"/msmc2-reloaded/data/masks/${ONE}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${TWO}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${THR}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${FOU}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${FIV}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${SIX}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${SEV}.txt.gz "$HOME"/msmc2-reloaded/data/masks/${EIG}.txt.gz"
#    done
#done</home/CIBIV/andreaf/msmc2-final/asia8_comparisons.txt















