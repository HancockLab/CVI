
###
#
# Set working directory to the CVI directory from github
#
###
setwd("")

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








##########
#######
####      Compute distribution of pairwise differences within and between islands
#######
##########


# On Tair10
# m = as.matrix(read.table("/Volumes/CVI/final/cvi/pi_20-7-2018/piMatrix.txt_cleanCvi.txt", head=F))
# names = as.matrix(read.table("/Volumes/CVI/final/cvi/pi_20-7-2018/piMatrix.txt_cleanCvi_names.txt", head=F))

m = as.matrix(read.table("/Volumes/CVI/final/cvi/pi/cviPaper_all20_2020-06-10/pairwDiff_cviPaper_2020-06-10_matrix.txt_cviPaper_2020-06-22_matrix.txt", head=F))
names = as.matrix(read.table("/Volumes/CVI/final/cvi/pi/cviPaper_all20_2020-06-10/pairwDiff_cviPaper_2020-06-10_matrix.txt_cviPaper_2020-06-22_names.txt", head=F))

# Cvi-0 reference from Mehmet
# m = as.matrix(read.table("/Volumes/CVI/final/cvi/pi/pi_cvi0_19-01-12/matrix_cvi0.txt", head=F))
# names = as.matrix(read.table("/Volumes/CVI/final/cvi/pi/pi_cvi0_19-01-12/names.txt", head=F))


# S1-1 reference from Mehmet
# m = as.matrix(read.table("/Volumes/CVI/final/cvi/pi_s1-1_19-04-15/s1-1_matrix.txt", head=F))
# names = as.matrix(read.table("/Volumes/CVI/final/cvi/pi_s1-1_19-04-15/names.txt", head=F))


numIDs = names[,1]
islIDs = vector()

labs <- fread("/Volumes/CVI/final/cvi/nj/cviPaperNj_withinCVI_2020-06-10_nj.raw_names.txt_idsRegionColSymb.txt", stringsAsFactors = T)
for (i in 1:length(numIDs)) {
  num = numIDs[[i]]
  for (s in 1:length(labs$V1)) {
    if (num == labs$V1[[s]]) {
      island= labs$V2[[s]]
      if (island == "CVI") {
        islIDs[[i]] = "C"
      }
      if (island == "santoAntao") {
        islIDs[[i]] = "S"
      }
      if (island == "fogo") {
        islIDs[[i]] = "F"
      }
    }
  }
}
islIDs


###
#     Organise pairwise differences into within SA, within FO and between islands
###

# SA
santo_matrix=(m[islIDs == "S", islIDs == "S"])
diag(santo_matrix) <- NA

# Fo
fogo_matrix=(m[islIDs == "F", islIDs == "F"])
diag(fogo_matrix) <- NA

# Between
between_matrix=(m[islIDs == "S", islIDs == "F"])



pdf("/Volumes/CVI/final/cvi/pi/cviPaper_all20_2020-06-10/cvi_pairwise_differences_2020-06-28.pdf", height=6,width=6)

par(mar=c(4.5,4.5,3,3), mfrow=c(1,1))
#dev.new(width=10, height=5)

# x <- seq(0.0, 0.00012, 0.000002)
x <- seq(0.0, 0.007, 0.000002)

san.col<- rgb(0, 117/255, 220/255, 0.9)
fog.col = rgb(255/255, 164/255, 5/255, 0.8)
bet.col= rgb(255/255, 0/255, 0/255, 0.8)


# Santo
hist(santo_matrix, breaks = x, xlim=c(0.0, 0.0001), ylim=c(0, 4000), col = san.col, border=san.col, main=c(""), xlab=list("Pairwise differences (per base pair)", cex=1.5), ylab=list("Pairs (x1000)", cex=1.5), axes=F)
# Fogo
hist(fogo_matrix, breaks = x, col = fog.col, add=TRUE, border=fog.col)
# between islands
hist(between_matrix, breaks = x, col = bet.col, add=TRUE, border=bet.col)

axis(1, at=c(0.000, 0.00005, 0.00010), tick=TRUE, labels=c("0.0", expression("5" %.% 10^"-5"), expression("1" %.% 10^"-4")), cex=1.8)
axis(2, at=c(0, 1000, 2000, 3000), labels=c("0", "1", "2", "3"), tick=TRUE, las=1, cex=1.2)

legend("topleft", legend = c("Santo Antão", "Fogo", "Between"), fill = c(san.col, fog.col, bet.col), border= c(san.col, fog.col, bet.col),cex = 1.5, bty="n")

dev.off()




















