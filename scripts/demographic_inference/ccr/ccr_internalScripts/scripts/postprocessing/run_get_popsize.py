#!/bin/bash
#for file in `ls results/*.final.txt`;
#while read p;
#do
#    comp=`echo ${p} | cut -d "." -f 1`
#    ONE=`basename ${comp} | cut -d "x" -f 1`
#    TWO=`basename ${comp} | cut -d "x" -f 2`
#    submit2sge -N size${ONE}x${TWO} -q cluster "python scripts/postprocessing/get_popsize.py ${ONE}x${TWO} > results/${ONE}x${TWO}.popsize.txt"
#done<data/caucasus.txt
#
#while read p;
##do
#    comp=`echo ${p} | cut -d "." -f 1`
#    ONE=`basename ${comp} | cut -d "x" -f 1`
#    TWO=`basename ${comp} | cut -d "x" -f 2`
#    submit2sge -N size${ONE}x${TWO} -q cluster "python scripts/postprocessing/get_popsize.py ${ONE}x${TWO} > results/${ONE}x${TWO}.popsize.txt"
#done<data/w_sah.txt


#while read p;
#do
#    comp=`echo ${p} | cut -d "." -f 1`
#    ONE=`basename ${comp} | cut -d "x" -f 1`
#    TWO=`basename ${comp} | cut -d "x" -f 2`
#    submit2sge -N size${ONE}x${TWO} -q cluster "python postprocessing/get_popsize.py ${ONE}x${TWO} > results/${ONE}x${TWO}.popsize.txt"
#done</home/CIBIV/andreaf/msmc2-final/are8_comparisons.txt




while read p;
do
    comp=`echo ${p} | cut -d "." -f 1`
    ONE=`basename ${comp} | cut -d "x" -f 1`
    TWO=`basename ${comp} | cut -d "x" -f 2`
    submit2sge -N size${ONE}x${TWO} -q cluster "python postprocessing/get_popsize.py ${comp} > results/${comp}.popsize.txt"
done</home/CIBIV/andreaf/msmc2-final/eurasia_comparisons.txt


