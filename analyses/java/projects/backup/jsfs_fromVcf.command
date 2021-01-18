#!/bin/bash


###
#		VCF combined to SNP matrix
###
matrix=/srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/abc/s5/s5s1_sims/2wg_migration25.txt
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/abc/s5/2wg_migration913.txt
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/abc/s11/s11_f/2wg_migration.txt
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/abc/s11/s11s7_vcf1.txt
	# s11s7_vcf518.txt
results=/srv/netscratch/dep_coupland/grp_hancock/andrea/jsfs_slim6/
mkdir -p ${results}
santo=/srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/andrea/s5_simula.txt
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/andrea/s5_simula.txt
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/andrea/s11_simula.txt

fogo=/srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/andrea/s1_simula.txt
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/andrea/s1_simula.txt
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/andrea/s7_simula.txt

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Jsfs_fromVcf_simulaSlim_plusFst ${matrix} ${results} ${santo} ${fogo}

