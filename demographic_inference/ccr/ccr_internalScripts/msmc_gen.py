#!/usr/bin/python
# should be python 2
# usage: python msmc_gen.py <chromosome> <# masks> <multisample vcf> <mask1> <mask2>
# use to generate an MSMC input file.
import sys
import csv
import re
import gzip

csv.field_size_limit(sys.maxsize)

if "-h" in sys.argv:
    print "usage: python msmc_gen.py <chromosome> <# masks> <multisample vcf> <mask1> <mask2>"
    sys.exit(0)
chromosome = sys.argv[1]
vcf_in = sys.argv[3]
sample_name = vcf_in.split("/")[-1].split(".")[0]

def read_fasta(filename):
    fasta = {}
    #if not gzipped
#    with open(filename) as file_one:
    # if gzipped
    try:
        with gzip.open(filename) as file_one:
            for line in file_one:
                line = line.strip()
                if not line:
                    continue
                if line.startswith(">"):
                    active_sequence_name = line[1:]
                    if active_sequence_name not in fasta:
                        fasta[active_sequence_name] = []
                        continue
                sequence = line
                fasta[active_sequence_name].append(sequence)
    except IOError:
        with open(filename) as file_one:
            for line in file_one:
                line = line.strip()
                if not line:
                    continue
                if line.startswith(">"):
                    active_sequence_name = line[1:]
                    if active_sequence_name not in fasta:
                        fasta[active_sequence_name] = []
                        continue
                sequence = line
                fasta[active_sequence_name].append(sequence)

    return fasta


# 1. make composite mask
mask_list = []
for maskfile in sys.argv[4:]:
    mask_list.append(read_fasta(maskfile))

composite_mask = mask_list[0]['Chr'+chromosome][0]
for mask in mask_list:
    for index,site in enumerate(mask['Chr'+chromosome][0]):
        if site == "0":
            try:
                composite_mask[index] == "0"
            except IndexError:
#                print "index error on Chromosome: "+chromosome+":"+site
                pass
# 2. write out
with gzip.open(vcf_in,'r') as tsvin:
#with open(vcf_in, 'r') as tsvin:
    tsvin = csv.reader(tsvin, delimiter='\t')
#    msmc_out = csv.writer(open("/home/CIBIV/arun/msmc2-final/data/msmc_input/"+sample_name+".chr"+chromosome+".txt", 'w'), delimiter='\t', lineterminator="\n")
    msmc_out = csv.writer(open("/home/CIBIV/andreaf/msmc2-reloaded/data/msmc_input/"+sample_name+".chr"+chromosome+".txt", 'w'), delimiter='\t', lineterminator="\n")
    oldpos = 1
    for line in tsvin:
        if line[0] == str(chromosome):
            position = line[1]
            ref = line[3]
            alt = line[4]
            called_sites = composite_mask[int(oldpos)-1:int(position)-1].count("1")
            haplotypes = line[9:]
            replaced_haplotypes = []
            if len(alt) > 1:
                alt_list = alt.split(",")
                for h in haplotypes:
                    if h == "1":
                        replaced_haplotypes.append(alt_list[0])
                    elif h == "2":
                        replaced_haplotypes.append(alt_list[1])
                    elif h == "3":
                        replaced_haplotypes.append(alt_list[2])
                    elif h == ".":
                        replaced_haplotypes.append(".")
                haplotypes = replaced_haplotypes
            elif len(alt) == 1:
                haplotypes = [h.replace('1', alt) for h in haplotypes]

            if "." in haplotypes:
                mask_val="NA"
                list_of_haps = haplotypes
                indices = [i for i, x in enumerate(list_of_haps) if x == "."]
                for mask_to_check in indices:
                    try:
                        mask_val = mask_list[mask_to_check]['Chr'+chromosome][0][int(position)-1]
                    except IndexError:
                        #print "IndexError when assigning mask_val. Position may not exist in mask.\n\t-> Chrom: "+chr+ " Pos: "+pos+" mask: "+sys.argv[2+mask_to_check]+"\n\t-> Assigning N to that position."
                        list_of_haps[mask_to_check] = "?"
                    if mask_val == "0": #replace with ?
                        list_of_haps[mask_to_check] = "?"
                    if mask_val == "1": #replace with ref
                        list_of_haps[mask_to_check] = ref
                haplotypes = list_of_haps
            haps = "".join(haplotypes)
            oldpos = position
            if called_sites == 0:
                called_sites = 1
            haps = re.sub('[^ACGT?]', '', haps)

            if len(haps) != len(mask_list): # sometimes the reference has a K or Y and we just want to skip those haplotypes
                print "Wrong number of haplotypes. Check reference base: "+chromosome+"\t"+position+"\t"+haps
            else:
                msmc_out.writerow([chromosome, position, called_sites, haps])
