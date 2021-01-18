#!/bin/bash

#lsfenv

#for file in /biodata/dep_coupland/grp_hancock/1001/iberians/*.1.fastq.bz2
for file in /biodata/dep_coupland/grp_hancock/rawData/Madeira/*bam
do
	echo $file
	#./process_samples.command $file
	bsub -q multicore20 -R "rusage[mem=1000]" -M 12000 "./process_samples.command $file"
done

#for i in 1
#do
#	bsub -q multicore20 -R "rusage[mem=1000]" -M 12000 "./javaRun.command 1"
#done


