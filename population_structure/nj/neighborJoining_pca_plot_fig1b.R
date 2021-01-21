
###
#
# Set working directory to the CVI directory from github
#
###
gitPath="path to CVI directory downloaded from github"
setwd(gitPath)

# Load required libraries 

library(data.table)
library(adegenet)
library(ape)

##########
#######
####      NJ - worldwide plus CVI: Fig. 1b
####      NJ - within CVI: EDF 1c
#######
##########

# 
plinkFile= "Add here path to *_nj.raw file created by ./scripts/population_structure/nj/neighborJoining_pca_fromVcf.command "
x <- read.PLINK(plinkFile, parallel = F)

x <- seploc(x, n.block=10, parallel=FALSE)
lD <- lapply(x, function(e) dist(as.matrix(e)))
D <- Reduce("+", lD)
t3 <- nj(D)

labsFile= "Add here path to *raw_names.txt_idsRegionColSymb.txt file created by ./scripts/population_structure/nj/neighborJoining_pca_fromVcf.command "
labs <- fread(labsFile, stringsAsFactors = T)
cols <- as.character(labs$V3)

###
#     New for colors and new samples
###

numIDs=as.vector(labs$V1)

#----
setEPS()
postscript("./results/fig1b.eps")
# Or this for NJ - within CVI: EDF 1c:
# postscript("./results/EDF1c.eps")
#----
plot.phylo(t3, tip.color=cols, type="unrooted", no.margin=T, show.tip.label=F)
tiplabels(pch=labs$V4,col=cols, cex=2)
# tiplabels(numIDs, pch="", bg=NULL, frame="none", cex=0.2, col="black")

#----
dev.off()
#----













