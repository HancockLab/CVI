#!/bin/bash


# fogo
# inFile=/srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/fogo_onePerStand_polyDFE.txt 

# santo
# infile=/srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/santo_onePerStand_polyDFE.txt 

infile=${1}

polyDfe=/srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master

# Run
#
####
###
# 	Fogo
###
######

# Classic
#
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${fogo} > ${polyDfe}/output/fogo04fold_onePerStand_full_C

# No Divergence data
# 
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -w -d ${fogo} > ${polyDfe}/output/fogo04fold_onePerStand_polyOnly_C

# Inferring a deleterious DFE using model C:
# 
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${fogo} -i ${polyDfe}/input/init_model_BandC.txt 2 -v 100 > ${polyDfe}/output/fogo04fold_onePerStand_del_C

# Model C without nuisance r parameters
# 
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${fogo} -i ${polyDfe}/input/init_model_BandC.txt 4 -j > ${polyDfe}/output/fogo04fold_onePerStand_noNuis_C

# Model C, allow for ancestral error
# 
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${fogo} -i ${polyDfe}/input/init_model_BandC.txt 5 > ${polyDfe}/output/fogo04fold_onePerStand_ancError_C



###
#	From chimp data
###




################################################
### Full model A
################################################

### without r, without eps_an
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b params_basinhop.txt 1 -m A -i init_A.txt 2 -e > ${inFile}_A_nor_noeps 
### without r, with eps_an
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b params_basinhop.txt 1 -m A -i init_A.txt 3 -e > ${inFile}_A_nor
### with r, without eps_an
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b params_basinhop.txt 1 -m A -i init_A.txt 4 -e > ${inFile}_A_noeps
### with r, with eps_an
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b params_basinhop.txt 1 -m A -i init_A.txt 1 -e > ${inFile}_A
### to easily enable processing of the results, 
### output files can be merged into one file
# cat ${inFile}_A_nor_noeps >  ${inFile}_A.txt 
# cat ${inFile}_A_nor       >> ${inFile}_A.txt
# cat ${inFile}_A_noeps     >> ${inFile}_A.txt
# cat ${inFile}_A           >> ${inFile}_A.txt







######
###
# 	Santo, old
###
####

${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${infile} > ${infile}_full_C

# No Divergence data
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -w -d ${santo} > ${polyDfe}/output/santo04fold_onePerStand_polyOnly_C
# Inferring a deleterious DFE using model C:
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${santo} -i ${polyDfe}/input/init_model_BandC.txt 2 -v 100 > ${polyDfe}/output/santo04fold_onePerStand_del_C
# Model C without nuisance r parameters
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${santo} -i ${polyDfe}/input/init_model_BandC.txt 4 -j > ${polyDfe}/output/santo04fold_onePerStand_noNuis_C
# Model C, allow for ancestral error
# 
# ${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${santo} -i ${polyDfe}/input/init_model_BandC.txt 5 > ${polyDfe}/output/santo04fold_onePerStand_ancError_C


