while read p; do
    a=`echo ${p} | cut -f 1 -d "x"`
    b=`echo ${p} | cut -f 2 -d "x"`
    a1=`echo ${a} | cut -f 1 -d "_"`
    a2=`echo ${a} | cut -f 2 -d "_"`
    b1=`echo ${b} | cut -f 1 -d "_"`
    b2=`echo ${b} | cut -f 2 -d "_"`
    echo  ${a1} ${a2} ${b1} ${b2}
    submit2sge -q cluster "bash scripts/preprocessing/merge_4_vcf.sh ${a1} ${a2} ${b1} ${b2}"
done<prova
#asia_comparisons.txt
#qar_bik_comparisons.txt
#done<herbarium_comparisons.txt
#done<qar_etna_comparisons.txt
#done<no_admix_comparisons.txt
#done<zin_comparisons.txt
#done<comparisons.txt


#submit2sge -q cluster "bash scripts/merge_4_vcf.sh 35520 211399 22001 22000"
#submit2sge -q cluster "bash scripts/merge_4_vcf.sh 35520 211399 9764 21138"
#submit2sge -q cluster "bash scripts/merge_4_vcf.sh 22001 22000 9764 21138"

#submit2sge -q cluster "bash scripts/merge_4_vcf.sh 9925 9935 9764 21138"
#submit2sge -q cluster "bash scripts/merge_4_vcf.sh 768 772 9764 21138"
