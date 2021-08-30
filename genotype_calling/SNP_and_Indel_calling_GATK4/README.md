# SNP_and_Indel_calling_Arabidopsis_GATK4
This is the pipeline that were used to identify SNPs and Indels with GATK4 in Arabidopsis thaliana. 
We followed germline short variant discovery (SNPs+Indels) in best practices workflows for GATK4 with some modifications. (https://gatk.broadinstitute.org/hc/en-us/articles/360035535932-Germline-short-variant-discovery-SNPs-Indels-)

## Requirements
    java (1.8)
    bwa (0.7.15)
    samtools (1.9) 
    picard (2.21.1)
    gatk (4.1.3.0)
    wget (1.18)
    
## Pipeline

```
# Retrive reference genome
wget https://www.arabidopsis.org/download_files/Genes/TAIR10_genome_release/TAIR10_chromosome_files/TAIR10_chr_all.fas

# Index reference genome
bwa index reference.fasta

# Aligning paired-end reads to reference genome
bwa mem -R "@RG\tID:id\tSM:sample\tLB:lib" reference.fasta sample.R1.fastq sample.R2.fastq > sample.sam
   
# Convert sam files to bam files
samtools view -Sb sample.sam -o sample.bam

# Sort bam file
samtools sort sample.bam -o sample.sorted.bam

# Mark duplicates with picard
java -jar picard.jar MarkDuplicates I=sample.sorted.bam O=sample.marked_duplicates.bam M=sample.marked_dup_metrics.txt

# Sorting marked duplicated bam files with picard
java -jar picard.jar SortSam I=sample.marked_duplicates.bam O=sample.final.bam SORT_ORDER=coordinate

# Index final bam files to use with gatk 
samtools index sample.final.bam

# Call variants for each sample in GVCF mode
gatk HaplotypeCaller --input sample.final.bam --reference reference.fasta --emit-ref-confidence GVCF --output sample.gvcfMode.vcf

# Create a database with all samples 
gatk GenomicsDBImport  --genomicsdb-workspace-path (putyourAbsolutePath)_DatabaseName --sample-name-map yourMapFile --intervals=yourIntervalsFile

# Genotype SNPs and Indels to create a merged vcf file with all your calls
gatk GenotypeGVCFs --reference reference.fasta -V gendb://(putyourAbsolutePath)_DatabaseName -o merged.vcf 

```


## Filtering Variants (SNPs)

```
#You can get more information about hard filtering from here: (https://gatkforums.broadinstitute.org/gatk/discussion/2806/howto-apply-hard-filters-to-a-call-set)

#Extract bi-allelic SNPs
gatk SelectVariants --variant merged.vcf --output only_SNPs.vcf --reference TAIR10.fa --select-type-to-include SNP --restrict-alleles-to BIALLELIC

#Adding filteres and marking heterozygous calls
gatk VariantFiltration --reference reference.fasta --variant only_SNPs.vcf -filter "QD < 2.0" --filter-name "QD2" -filter "QUAL < 30.0" --filter-name "QUAL30" -filter "SOR > 3.0" --filter-name "SOR3"  -filter "FS > 60.0" --filter-name "FS60" -filter "MQ < 40.0" --filter-name "MQ40" -filter "MQRankSum < -12.5" --filter-name "MQRankSum-12.5" -filter "ReadPosRankSum < -8.0" --filter-name "ReadPosRankSum-8" --genotype-filter-expression "isHet == 1" --genotype-filter-name "isHetFilter" -O only_SNPs_filters_added.vcf 

#Convert Heterozygous calls to missing data (0/1 to ./.)
gatk SelectVariants -variant only_SNPs_filters_added.vcf --reference reference.fasta --output snps_withoutHTs.vcf --set-filtered-gt-to-nocall 
 
#Finally we exclude filtered snps
gatk SelectVariants -variant snps_withoutHTs.vcf --reference reference.fasta --output only_SNPs_Final.vcf --exclude-filtered true --exclude-non-variants true
 
```

## Filtering Variants (INDELs)

```
#You can get more information about hard filtering from here: (https://gatkforums.broadinstitute.org/gatk/discussion/2806/howto-apply-hard-filters-to-a-call-set)

#Extract bi-allelic Indels
gatk SelectVariants --variant merged.vcf --output only_INDELs.vcf --reference reference.fasta --select-type-to-include INDEL --restrict-alleles-to BIALLELIC 

#Adding filteres and marking heterozygous calls
gatk VariantFiltration -V only_INDELs.vcf -filter "QD < 2.0" --filter-name "QD2" -filter "QUAL < 30.0" --filter-name "QUAL30" -filter "FS > 200.0" --filter-name "FS200" -filter "ReadPosRankSum < -20.0" --filter-name "ReadPosRankSum-20" --genotype-filter-expression "isHet == 1" --genotype-filter-name "isHetFilter" -O only_INDELs_filters_added.vcf 

#Convert Heterozygous calls to missing data (0/1 to ./.)
gatk SelectVariants -variant only_INDELs_filters_added.vcf --reference reference.fasta --output indels_withoutHTs.vcf --set-filtered-gt-to-nocall

#Finally we exclude filtered indels
gatk SelectVariants -variant indels_withoutHTs.vcf --reference reference.fasta --output only_INDELs_Final.vcf --exclude-filtered true --exclude-non-variants true

```
