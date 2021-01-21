#!/bin/bash
# script to run the script "merge_vcf.sh"
# uses sample combinations from data/w_m.txt etc.
# should use submit2sge for this.
# usage: bash run_merge_vcf.sh

loop () {
    while read comb; do
	ONE=`echo ${comb} | cut -d "x" -f 1`
	TWO=`echo ${comb} | cut -d "x" -f 2`
	submit2sge -N merge${ONE}x${TWO} -q cluster "bash /scripts/preprocessing/merge_vcf.sh ${ONE} ${TWO}"
    done <"data/"${1}".txt"
}

#loop "w_m"
#loop "w_i"
#loop "w_w"
#loop "b_i_g"
#loop "b_m_i"
#loop "b_m_r"
#loop "b_m_w"
#loop "7025.7354.772.w_w"
#loop "9550"
#loop "cvi_can"
#loop "caucasus"
loop "w_sah"
loop "w_tanzh"
