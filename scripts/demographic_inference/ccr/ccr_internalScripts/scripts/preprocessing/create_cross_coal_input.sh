##!/bin/bash

#for file in `ls ~/msmc2-xcoal/data/merged_vcfs`;
while read p;
do
    a=`echo ${p} | cut -f 1 -d "x"`
    b=`echo ${p} | cut -f 2 -d "x"`
    a1=`echo ${a} | cut -f 1 -d "_"`
    a2=`echo ${a} | cut -f 2 -d "_"`
    b1=`echo ${b} | cut -f 1 -d "_"`
    b2=`echo ${b} | cut -f 2 -d "_"`

    for chrom in 1 2 3 4 5;
    do
       submit2sge -q cluster -N msmcgen${SAMPLE} "/usr/bin/python ~/msmc2-reloaded/scripts/preprocessing/msmc_gen.py ${chrom} 4 ~/msmc2-reloaded/data/merged_vcfs/${p}.vcf.gz ~/msmc2-reloaded/data/masks/${a1}.txt.gz ~/msmc2-reloaded/data/masks/${a2}.txt.gz ~/msmc2-reloaded/data/masks/${b1}.txt.gz ~/msmc2-reloaded/data/masks/${b2}.txt.gz"
    done
done</home/CIBIV/andreaf/msmc2-reloaded/prova
#asia_comparisons.txt
#done<herbarium_comparisons.txt
#done<qar_etna_comparisons.txt
#done<no_admix_comparisons.txt
#done<zin_comparisons.txt
#done<comparisons.txt


#for p in "35520_211399x22001_22000" "35520_211399x9764_21138" "22001_22000x9764_21138"
#for p in "9925_9935x9764_21138" "768_772x9764_21138"
#for p in  "768_772x9764_21138"
#do
#    a=`echo ${p} | cut -f 1 -d "x"`
#    b=`echo ${p} | cut -f 2 -d "x"`
#    a1=`echo ${a} | cut -f 1 -d "_"`
#    a2=`echo ${a} | cut -f 2 -d "_"`
#    b1=`echo ${b} | cut -f 1 -d "_"`
#    b2=`echo ${b} | cut -f 2 -d "_"`
#    echo $a1
#    echo $a2
#    echo $b1
#    echo $b2
#    for chrom in 1 2 3 4 5;
#    do
#       submit2sge -q cluster -N msmcgen${p} "/usr/bin/python ~/msmc2-final/scripts/preprocessing/msmc_gen.py ${chrom} 4 ~/msmc2-xcoal/data/merged_vcfs/${p}.vcf.gz ~/msmc2-final/data/masks/${a1}.txt.gz ~/msmc2-final/data/masks/${a2}.txt.gz ~/msmc2-final/data/masks/${b1}.txt.gz ~/msmc2-final/data/masks/${b2}.txt.gz"
#    done
#done
