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


###
#	The working one:
###

#while read p;
#do
#    comp=`echo ${p} | cut -d "." -f 1`
#    ONE=`basename ${comp} | cut -d "x" -f 1`
#    TWO=`basename ${comp} | cut -d "x" -f 2`
#    submit2sge -N size${ONE}x${TWO} -q cluster "python postprocessing/get_popsize.py ${comp} > results/${comp}.popsize.txt"
#done</home/CIBIV/andreaf/msmc2-final/georgia_comparisons.txt



while read p;
do
    comp=`echo ${p} | cut -d "." -f 1`
    ONE=`basename ${comp} | cut -d "x" -f 1`
    TWO=`basename ${comp} | cut -d "x" -f 2`
    python get_popsize.py ./within/${comp} > results/${comp}.popsize.txt
done</home/lv70590/Andrea/analyses/msmc2/eurasia8hap_comparisons.txt





