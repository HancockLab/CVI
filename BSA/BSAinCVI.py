#!/usr/bin/python

"""		  FILE: BSAinCVI.py
		 USAGE: ---
   DESCRIPTION: ---
	   OPTIONS: ---
  REQUIREMENTS: ---
		  BUGS: ---
		 NOTES: ---
		AUTHOR: Emmanuel Tergemina
  ORGANIZATION: Department of Plant Developmental Biology, Max Planck Institute for Plant Breeding Research
	   VERSION: 1.0
	   CREATED: 20200731
	  REVISION: ---
"""

import numpy as np

vcf = '/biodata/irg/grp_hancock/VCF/GATK_SNPs_Indels_For_Cape_Verde/NewVersion_withCVI-0s/CapeVerde_SNPs_Indels_Final_segregating.vcf'

gatk = raw_input('Which gatk file?\n   - a "BPresolution" file with the path has to be provided...\n   - Example: /srv/netscratch/irg/grp_hancock/Celia/BSA/S5-10xF13-8_F2/output/4572_A.BP_RESOLUTION.GATK.vcf\n ')
P1 = raw_input('Which parent 1?\n   - a sequencing ID present in the VCF file has to be provided...\n   - Example: 21226\n')
P2 = raw_input('Which parent 2?\n   - a sequencing ID present in the VCF file has to be provided...\n   - Example: 2876_AU\n')
stepSize = raw_input('Which step size?\n   - Example: 10000\n')
windowSize = raw_input('Which window size?\n   - Example: 500000\n')

genome = ([],[],[],[],[])
def chromParser(var, sample,p1,p2):
	index = int(var[0])-1
	nbr = [1, 3, 4, 5, 6, p1, p2]
	tmp = [var[i] for i in nbr]
	tmp.append(sample)
	genome[index].append(tmp)


#Process VCF file (CVI)
for line in open(vcf,'r'):
	if not '##' in line:
		tmp1 = line.strip().split()
		if '#CHROM' in line:
			for idx, accession in enumerate(tmp1):
				if accession == P1:
					idx_P1 = idx
				elif accession == P2:
					idx_P2 = idx
		else:
			if tmp1[idx_P1][0] == '1' and tmp1[idx_P2][0] != '1':
				chromParser(tmp1, P1, idx_P1, idx_P2)
			elif tmp1[idx_P2][0] == '1' and tmp1[idx_P1][0] != '1':
				chromParser(tmp1, P2, idx_P1, idx_P2)


# Process gatk file (BPresolution)
output_AF = open(P1 + '_' + P2 + '_alleleFrequency.txt','wt',0)
output_AF.write('CHROM\tPOSITION\tREF\tALT\tAF_{}\tAF_{}\tDP\tSAMPLE\n'.format(P1,P2))
output_ER = open(P1 + '_' + P2 + '_error.txt','wt',0)
AF = ([],[],[],[],[])
n = 0
chrom = 0
for row in open(gatk, 'r'):
	if '#' not in row and not 'chloroplast' in row and not 'mitochondria' in row:
		tmp2 = row.strip().split()
		if chrom != int(tmp2[0][3])-1:
			n = 0
			chrom = int(tmp2[0][3])-1
			info = tmp2[len(tmp2)-1].split(':')
				# print(int(tmp2[1]),int(genome[chrom][n][0]))
			if int(tmp2[1]) == int(genome[chrom][n][0]):
				alt = tmp2[4].split(',')
				if '*' not in tmp2[4] and len(alt) == 2 and float(tmp2[5]) > 30.0:
					allele = info[1].split(',')
					ecotype = genome[chrom][n][len(genome[chrom][n])-1]
					if ecotype == P1:
						try:
							output_AF.write('{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\n'.format(tmp2[0],tmp2[1],genome[chrom][n][1], genome[chrom][n][2],float(allele[1])/float(info[2]),float(allele[0])/float(info[2]),info[2],info[1]))
							AF[chrom].append([tmp2[1],float(allele[1])/float(info[2]),float(allele[0])/float(info[2])])
						except:
							output_ER.write(row)
					elif ecotype == P2:
						try:
							output_AF.write('{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\n'.format(tmp2[0],tmp2[1], genome[chrom][n][1], genome[chrom][n][2],float(allele[0])/float(info[2]),float(allele[1])/float(info[2]),info[2],info[1]))
							AF[chrom].append([tmp2[1],float(allele[0])/float(info[2]),float(allele[1])/float(info[2])])
						except:
							output_ER.write(row)
		else:
			if n < len(genome[chrom]):
				info = tmp2[len(tmp2)-1].split(':')
				if int(tmp2[1]) == int(genome[chrom][n][0]):
					alt = tmp2[4].split(',')
					if '*' not in tmp2[4] and len(alt) == 2 and float(tmp2[5]) > 30.0:
						allele = info[1].split(',')
						ecotype = genome[chrom][n][len(genome[chrom][n])-1]
						if ecotype == P1:
							try:
								output_AF.write('{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\n'.format(tmp2[0],tmp2[1],genome[chrom][n][1], genome[chrom][n][2],float(allele[1])/float(info[2]),float(allele[0])/float(info[2]),info[2],info[1]))
								AF[chrom].append([tmp2[1],float(allele[1])/float(info[2]),float(allele[0])/float(info[2])])
							except:
								output_ER.write(row)

						elif ecotype == P2:
							try:
								output_AF.write('{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\n'.format(tmp2[0],tmp2[1], genome[chrom][n][1], genome[chrom][n][2],float(allele[0])/float(info[2]),float(allele[1])/float(info[2]),info[2],info[1]))
								AF[chrom].append([tmp2[1],float(allele[0])/float(info[2]),float(allele[1])/float(info[2])])
							except:
								output_ER.write(row)
					n+=1
output_AF.close()
output_ER.close()


#Generate window-step
size = [30427671,19698289,23459830,18585056,26975502]
output_window = open(P1 + '_' + P2 + '_stepSize' + stepSize + '_windowSize' + windowSize + '.txt','wt',0)
output_window.write('Chrom\tWindow\t{}_Freq_Mean\t{}_Freq_Median\t{}_STD\t{}_Freq_Mean\t{}_Freq_Median\t{}_STD\n'.format(P1,P1,P1,P2,P2,P2))

for i in range(len(size)):
	x = 0
	for j in range(1,size[i]):
		if j % int(stepSize) == 0 and j < size[i]- int(windowSize):
			tmp_P1_AF = []
			tmp_P2_AF = []
			for idx, (position, P1_AF, P2_AF) in enumerate(AF[i], start = x):
				if int(position) < j:
					index = int(idx)
				elif j <= int(position) <= j + int(windowSize):
					tmp_P1_AF.append(float(P1_AF))
					tmp_P2_AF.append(float(P2_AF))
				elif int(position) > j + int(windowSize):
					break
			output_window.write('{}{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\n'.format('chr',str(i+1),j,np.mean(tmp_P1_AF),np.median(tmp_P1_AF),np.std(tmp_P1_AF),np.mean(tmp_P2_AF),np.median(tmp_P2_AF),np.std(tmp_P2_AF)))
		elif j > size[i]- int(windowSize):
			break
output_window.close()

