#!/bin/bash
# script to merge two VCFs that are tabix'd and bgzipped
# usage: merge_vcf.sh <#> <#>
set -e 
set -u

DIR=/netscratch/irg/grp_hancock/andrea/msmc/

vcf-merge ${DIR}data/andrea_vcfs/${1}.vcf.gz ${DIR}data/andrea_vcfs/${2}.vcf.gz ${DIR}data/andrea_vcfs/${3}.vcf.gz ${DIR}data/andrea_vcfs/${4}.vcf.gz ${DIR}data/andrea_vcfs/${5}.vcf.gz ${DIR}data/andrea_vcfs/${6}.vcf.gz ${DIR}data/andrea_vcfs/${7}.vcf.gz ${DIR}data/andrea_vcfs/${8}.vcf.gz ${DIR}data/andrea_vcfs/${9}.vcf.gz ${DIR}data/andrea_vcfs/${10}.vcf.gz ${DIR}data/andrea_vcfs/${11}.vcf.gz ${DIR}data/andrea_vcfs/${12}.vcf.gz ${DIR}data/andrea_vcfs/${13}.vcf.gz ${DIR}data/andrea_vcfs/${14}.vcf.gz ${DIR}data/andrea_vcfs/${15}.vcf.gz ${DIR}data/andrea_vcfs/${16}.vcf.gz | bgzip -c > ${DIR}data/merged_vcfs/${1}_${2}_${3}_${4}_${5}_${6}_${7}_${8}x${9}_${10}_${11}_${12}_${13}_${14}_${15}_${16}.vcf.gz
