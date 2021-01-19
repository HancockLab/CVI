while read p;
do
    a=`echo ${p} | cut -f 1 -d "x"`
    b=`echo ${p} | cut -f 2 -d "x"`
    a1=`echo ${a} | cut -f 1 -d "_"`
    a2=`echo ${a} | cut -f 2 -d "_"`
    b1=`echo ${b} | cut -f 1 -d "_"`
    b2=`echo ${b} | cut -f 2 -d "_"`

    #/usr/bin/python3 scripts/combineCrossCoal.py across/${p}.final.txt within2/${a}.final.txt within2/${b}.final.txt > combined2/${p}.combined.txt
    #  source ~/anaconda3.1/anaconda3.1/bin/activate py35
    python scripts/preprocessing/combineCrossCoal.py across/${p}.final.txt within2/${a}.final.txt within2/${b}.final.txt > checkOut/${p}.combined.txt
done < prova
#asia_comparisons.txt
#done < herbarium_comparisons.txt
#done < no_admix_comparisons.txt
#done < zin_comparisons.txt
#done < comparisons.txt

#for p in "35520_211399x22001_22000" "35520_211399x9764_21138" "22001_22000x9764_21138"
#for p in "9925_9935x9764_21138" "768_772x9764_21138"
#do
#    a=`echo ${p} | cut -f 1 -d "x"`
#    b=`echo ${p} | cut -f 2 -d "x"`
#    a1=`echo ${a} | cut -f 1 -d "_"`
#    a2=`echo ${a} | cut -f 2 -d "_"`
#    b1=`echo ${b} | cut -f 1 -d "_"`
#    b2=`echo ${b} | cut -f 2 -d "_"`

#    python scripts/combineCrossCoal.py across/${p}.final.txt within2/${a}.final.txt within2/${b}.final.txt > combined2/${p}.combined.txt
#done
