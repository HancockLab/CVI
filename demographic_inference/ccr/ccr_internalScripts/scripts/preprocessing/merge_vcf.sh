#!/bin/bash
# script to merge two VCFs that are tabix'd and bgzipped
# usage: merge_vcf.sh <#> <#>
set -e 
set -u

vcf-merge data/andrea_vcfs/${1}.vcf.gz data/andrea_vcfs/${2}.vcf.gz | bgzip -c > data/merged_vcfs/${1}x${2}.vcf.gz
