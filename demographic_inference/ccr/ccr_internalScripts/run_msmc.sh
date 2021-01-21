#!/bin/bash

###
#	2 haps
###
while read p;
do
    comp=`echo ${p} | cut -d "." -f 1`
    ONE=`basename ${comp} | cut -d "x" -f 1`
    TWO=`basename ${comp} | cut -d "x" -f 2`
    submit2sge -N msmc${ONE}x${TWO} -q cluster "bash msmc/msmc.sh ${comp}"
done</home/CIBIV/andreaf/msmc2-final/georgia_comparisons.txt



###
#	8 haps
###

#while read p;
#do
#    comp=`echo ${p} | cut -d "." -f 1`
#    ONE=`basename ${comp} | cut -d "x" -f 1`
#    TWO=`basename ${comp} | cut -d "x" -f 2`
#    submit2sge -N msmc${ONE}x${TWO} -q cluster "bash msmc/msmc.sh ${ONE}x${TWO}"
#done</home/CIBIV/andreaf/msmc2-final/are8_comparisons.txt


