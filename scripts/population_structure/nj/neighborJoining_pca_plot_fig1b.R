
###
#
# Set working directory to the CVI directory from github
#
###
setwd("/Users/fulgione/git/CVI")

# Load required libraries 

library(data.table)
library(adegenet)
library(ape)

##########
#######
####      NJ - worldwide plus CVI: Fig. 1b
#######
##########

# x <- read.PLINK("/Volumes/CVI/final/cvi/nj/cviPaperNj_2020-04-29_nj.raw_namesRight.raw", parallel = F)
x <- read.PLINK("./data/cviPaperNj_all20_2020-05-21_nj.raw", parallel = F)

x <- seploc(x, n.block=10, parallel=FALSE)
lD <- lapply(x, function(e) dist(as.matrix(e)))
D <- Reduce("+", lD)
t3 <- nj(D)

# labs <- fread("/Volumes/CVI/final/cvi/nj/cviPaperNj_2020-04-29_idsRegionColSymb.txt", stringsAsFactors = T)
labs <- fread("./data/cviPaperNj_all20_2020-05-21_nj.raw_names.txt_idsRegionColSymb.txt", stringsAsFactors = T)
cols <- as.character(labs$V3)

###
#     New for colors and new samples
###

numIDs=as.vector(labs$V1)

#----
setEPS()
postscript("./results/fig1b.eps")
#----
plot.phylo(t3, tip.color=cols, type="unrooted", no.margin=T, show.tip.label=F)
tiplabels(pch=labs$V4,col=cols, cex=2)
# tiplabels(numIDs, pch="", bg=NULL, frame="none", cex=0.2, col="black")

#----
dev.off()
#----
















##########
#######
####      NJ - within CVI: EDF 1c
#######
##########

x <- read.PLINK("./data/cviPaperNj_withinCVI_2020-06-10_nj.raw_namesRight.raw", parallel = F)

x <- seploc(x, n.block=10, parallel=FALSE)
lD <- lapply(x, function(e) dist(as.matrix(e)))
D <- Reduce("+", lD)
t3 <- nj(D)

# labs <- fread("/Volumes/CVI/final/cvi/nj/cviPaperNj_2020-04-29_idsRegionColSymb.txt", stringsAsFactors = T)
labs <- fread("./data/cviPaperNj_withinCVI_2020-06-10_nj.raw_names.txt_idsRegionColSymb.txt", stringsAsFactors = T)
cols <- as.character(labs$V3)
sym <- labs$V6
###
#     New for colors and new samples
###

numIDs=as.vector(labs$V1)

#----
setEPS()
postscript("./results/EDF1c.eps")
#----
plot.phylo(t3, tip.color=cols, type="unrooted", no.margin=T, show.tip.label=F)
tiplabels(pch=sym[3:length(labs$V4)], col=cols[3:length(labs$V4)], tip=c(3:length(labs$V4)), cex=2)
tiplabels(pch=sym[2], col=cols[2], tip=c(2), cex=2)
tiplabels(pch=sym[1], col=cols[1], tip=c(1), cex=2)
# tiplabels(numIDs, pch="", bg=NULL, frame="none", cex=0.2, col="black")

#----
dev.off()
#----



