


###
#		2 haps mode
###
#while read p;
#do
#    a=`echo ${p} | cut -f 1 -d "x"`
#    b=`echo ${p} | cut -f 2 -d "x"`
#    a1=`echo ${a} | cut -f 1 -d "_"`
#    a2=`echo ${a} | cut -f 2 -d "_"`
#    b1=`echo ${b} | cut -f 1 -d "_"`
#    b2=`echo ${b} | cut -f 2 -d "_"`
#
#    submit2sge -q cluster "/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -P 0,0,1,1 -o across/${p} data/msmc_input/${p}.chr?.txt"
#    submit2sge -q cluster "/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -I 0,1 -o within2/${a} data/msmc_input/${p}.chr?.txt"
#    submit2sge -q cluster "/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -I 2,3 -o within2/${b} data/msmc_input/${p}.chr?.txt"
#done < prova




###
#		8 haps
###
while read p;
do
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

    # Run Msmc
    #/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -P 0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1 -o across/${p} data/msmc_input/${p}.chr?.txt
    #/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -I 0,1,2,3,4,5,6,7 -o within2/${a} data/msmc_input/${p}.chr?.txt
    #/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -I 8,9,10,11,12,13,14,15 -o within2/${b} data/msmc_input/${p}.chr?.txt
       
    submit2sge -q cluster "/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -P 0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1 -o across/${p} data/msmc_input/${p}.chr?.txt"
    submit2sge -q cluster "/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -I 0,1,2,3,4,5,6,7 -o within2/${a} data/msmc_input/${p}.chr?.txt"
    submit2sge -q cluster "/home/CIBIV/andreaf/software/msmc2-test/build/release/msmc2 -I 8,9,10,11,12,13,14,15 -o within2/${b} data/msmc_input/${p}.chr?.txt"
done< rel-all8_comparisons.txt
















#for p in "35520_211399x22001_22000" "35520_211399x9764_21138" "22001_22000x9764_21138"
#for p in "9925_9935x9764_21138" "768_772x9764_21138"
#for p in "768_772x9764_21138" 
#do
#    a=`echo ${p} | cut -f 1 -d "x"`
#    b=`echo ${p} | cut -f 2 -d "x"`
#    a1=`echo ${a} | cut -f 1 -d "_"`
#    a2=`echo ${a} | cut -f 2 -d "_"`
#    b1=`echo ${b} | cut -f 1 -d "_"`
#    b2=`echo ${b} | cut -f 2 -d "_"`

#    submit2sge -q cluster "/home/CIBIV/arun/software/msmc2-test/build/release/msmc2 -P 0,0,1,1 -o across/${p} data/msmc_input/${p}.chr?.txt"
#    submit2sge -q cluster "/home/CIBIV/arun/software/msmc2-test/build/release/msmc2 -I 0,1 -o within2/${a} data/msmc_input/${p}.chr?.txt"
#    submit2sge -q cluster "/home/CIBIV/arun/software/msmc2-test/build/release/msmc2 -I 2,3 -o within2/${b} data/msmc_input/${p}.chr?.txt"
#done
