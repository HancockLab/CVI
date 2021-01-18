#!/bin/bash

# For if you want specific number IDs
#for num in 13573 13182 13181 12909 12768 12767 13580 13578 13179 13173 12914 12911
#do
#for rawFile in /srv/biodata/dep_coupland/grp_hancock/rawData/CVI/*${num}*bam
#do
# echo $rawFile   
#done 

# For Iberians
#for rawFile in /home/CIBIV/andreaf/rawDataBackup/refCut/plain/[0-9].fastq.b.gz
#do

# For all our samples
for rawFile in /srv/biodata/irg/grp_hancock/andrea/data/bamsToUpload_cvi/clean/*bam
	# /srv/biodata/dep_coupland/grp_hancock/rawData/cvi_Koelsch/*_R1_001.fastq.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/puschr_bams/final/J31370*bam
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane4073/final/*_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_18-12/lane3968/560/*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_18-12/lane3968/finalProj3968/*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/CVI/21225_AGTCACTA_C5E91ANXX_2_20140822B_20140822.bam
	# /srv/biodata/dep_coupland/grp_hancock/rawData/CVI/21225_AGTCACTA_C5E91ANXX_2_20140822B_20140822.bam
	# /srv/biodata/dep_coupland/grp_hancock/rawData/CVI/20682_GCTCGGTA_C5E91ANXX_3_20140822B_20140822.bam
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_11-17/final/2876_AH_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/CVI/20682_GCTCGGTA_C5E91ANXX_3_20140822B_20140822.bam
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_11-17/final/2876_AH_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_18-12/final/*_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane4073/final/*_R1_001.fastq.gz /srv/biodata/dep_coupland/grp_hancock/rawData/lane_18-12/final/*_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_11-17/final/2876_AL_R1_001.fastq.gz
	# 	Cvi-0:
	# /biodata/shared/DATABASES/1001Genomes/reads/6911.1.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_18-12/new/*_R1_001.fastq.gz
	# /biodata/shared/DATABASES/1001Genomes/reads/6911.1.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/Morocco/*bam
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_[2,4,6]*/final/*_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_6-18/final/*_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_6-18/final/*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/andrea/rawdata/newRel/*_1.fastq.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/rawdata/afr_final/*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/ChineseAccessions/*_1.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/ChineseAccessions/tibet0/SRR1756969.fastq
	# /srv/biodata/dep_coupland/grp_hancock/rawData/levant/final/3593_C*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/ChineseAccessions/*_1.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/levant/final/*R1_001.fastq.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/rawdata/afr_final/*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_6-18/final/3619_E_*R1_001.fastq.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/rawdata/afr_final/*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/AfricanGenomes/READS/*_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane_6-18/final/*_R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/lane1/lane_6-18/final/fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/ChineseAccessions/*_1.fastq
	# /srv/biodata/dep_coupland/grp_hancock/andrea/data/tibet/SRR1756969.fastq
	# /srv/biodata/dep_coupland/grp_hancock/andrea/data/tibet/*_1.fastq
	# /srv/biodata/dep_coupland/grp_hancock/andrea/afr8/*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/andrea/afr8/2891_H*R1_001.fastq.gz
	# /srv/biodata/dep_coupland/grp_hancock/rawData/CVI/3*
do

#bgzip -cd $brawFile > $brawFile".fastq"
#rawFile=$brawFile".fastq"

local_name=${rawFile##*/}
#fileCut="$(echo $rawFile | cut -d '_' -f 1)"_"$(echo $rawFile | cut -d '_' -f 2)"_"$(echo $rawFile | cut -d '_' -f 3)"
#local_name=${fileCut##*/}
echo $local_name

# For fastq from Koeln
numIDactual="$(echo $local_name | cut -d '_' -f 1)"_"$(echo $local_name | cut -d '_' -f 2)"

# For bams
# numIDactual="$(echo $local_name | cut -d '_' -f 1)"

# For 1001 data in Koeln and pogo:
# numIDactual="$(echo $local_name | cut -d '.' -f 1)"

echo $numIDactual

islandID="Cvi0"

# Pair numID to islandID

while read line
 do
  numID=$(echo "$line" | cut -f1)
  #echo $numID
  if [ "$numIDactual" == "$numID" ]
  then
   islandID=$(echo "$line" | cut -f2)
   echo "$line"
   echo "$islandID"
  fi
 done < /srv/biodata/irg/grp_hancock/VCF/idsToPlants_19-04-18.txt
 	# /srv/biodata/dep_coupland/grp_hancock/VCF/idsToPlants_19-02-08.txt
 	# /home/fulgione/africanIdsToPlants_29-10-18.txt
 	# ${HOME}/data/china_idsToSample.txt
 	# chineseIDs.txt
 	# ${HOME}/data/chineseIDs.txt
 	# /srv/biodata/dep_coupland/grp_hancock/VCF/idsToPlants_11-7-18.txt
 	# /home/fulgione/africanIdsToPlants_29-10-18.txt
 	# /srv/biodata/dep_coupland/grp_hancock/AfricanGenomes/Ethiopians_3611_metadata.txt
 	# /srv/biodata/dep_coupland/grp_hancock/VCF/idsToPlants_11-7-18.txt 
 	# $HOME/data/idsToPlants.txt
echo $islandID

# Get the new place to put files, whereWe, and $file
#islandID="$(echo $local_name | cut -d '_' -f 1)"

whereWeDir=/srv/biodata/irg/grp_hancock/andrea/data
mkdir -p "$whereWeDir"
workDir=/srv/netscratch/irg/grp_hancock/andrea/${numIDactual}
mkdir -p $workDir

# For our files
# $islandID=cvi
file=${workDir}/${islandID}.${numIDactual}

# For 1001:
#file="$workDir""$local_name"
echo "$file"

#short=$(echo ${islandID} | head -c1)
#echo $short

#	Just for .fastq from Cologne
# short names
fileCut="$(echo $rawFile | cut -d '_' -f 1)"_"$(echo $rawFile | cut -d '_' -f 2)"_"$(echo $rawFile | cut -d '_' -f 3)"_"$(echo $rawFile | cut -d '_' -f 4)"
# fileCut="$(echo $rawFile | cut -d '_' -f 1)"_"$(echo $rawFile | cut -d '_' -f 2)"_"$(echo $rawFile | cut -d '_' -f 3)"_"$(echo $rawFile | cut -d '_' -f 4)"_"$(echo $rawFile | cut -d '_' -f 5)"

# Long names
# fileCut="$(echo $rawFile | cut -d '_' -f 1)"_"$(echo $rawFile | cut -d '_' -f 2)"_"$(echo $rawFile | cut -d '_' -f 3)"_"$(echo $rawFile | cut -d '_' -f 4)"_"$(echo $rawFile | cut -d '_' -f 5)"_"$(echo $rawFile | cut -d '_' -f 6)"_"$(echo $rawFile | cut -d '_' -f 7)"_"$(echo $rawFile | cut -d '_' -f 8)"_"$(echo $rawFile | cut -d '_' -f 9)"
# _"$(echo $rawFile | cut -d '_' -f 9)"

#	For Chinese
# fileCut="$(echo $rawFile | cut -d '_' -f 1)"_"$(echo $rawFile | cut -d '_' -f 2)"_"$(echo $rawFile | cut -d '_' -f 3)"

#	For new relicts
# fileCut="$(echo $rawFile | cut -d '_' -f 1)"_"$(echo $rawFile | cut -d '_' -f 2)"_"$(echo $rawFile | cut -d '_' -f 3)"_"$(echo $rawFile | cut -d '_' -f 4)"

#	For 1001 fastqs in Cologne
# fileCut="$(echo $rawFile | cut -d '.' -f 1)"


echo $fileCut

# Only if fastq from Cologne:
rawFile=${fileCut}

#local_name=${fileCut##*/}
#file=$workDir/$islandID.$local_name

whereWe=${whereWeDir}/cviSlocusCalls
	# bamsToUpload_cvi
	# pogo_2020-02-04
	# cvi_19-05-22
	# partialRuns3968-560
	# cvi_19-04-26
	# cvi0_19-02-26
	# afr_30-10-
mkdir -p $whereWe
echo $whereWe


echo $rawFile $file $workDir $whereWe ${numIDactual}
# bsub -q multicore20 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./callSloci.command $rawFile $file $workDir $whereWe ${numIDactual}"


# bsub -q multicore20 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./fastqToBam.command $rawFile $file $workDir $whereWe ${numIDactual}"
# ./fastqToBam.command $rawFile $file $workDir $whereWe ${numIDactual}




# ./fulgiP_fromAlignedBams.command $rawFile $file $workDir $whereWe

# bsub -q multicore20 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./fulgiP.command $rawFile $file $workDir $whereWe"

# For bams only: IGV
# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./bam.command $rawFile $file $workDir $whereWe"

#bsub -q multicore40 -R "rusage[mem=10GB]" "./fulgiP.command $rawFile $file $workDir $whereWe"
#bsub -q multicore40 -R "rusage[mem=10000]" "./fulgiP.command $rawFile $file $workDir $whereWe"

done
