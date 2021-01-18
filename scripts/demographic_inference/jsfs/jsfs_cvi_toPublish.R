
# Run as:
# Rscript jsfs_cvi_toPublish.R

###
#
# Set working directory to the CVI directory from github
#
###
setwd("/Users/fulgione/git/CVI")

# Load required libraries 
library("spam")
library("fields")

par(ps=7)
colors <- c("white", "#00009F", "#0000AF", "#0000BF", "#0000CF", "#0000DF", "#0000EF", "#0000FF", "#0010FF", "#0020FF", "#0030FF", "#0040FF", "#0050FF", "#0060FF", "#0070FF", "#0080FF", "#008FFF", "#009FFF", "#00AFFF", "#00BFFF", "#00CFFF", "#00DFFF", "#00EFFF", "#00FFFF", "#10FFEF", "#20FFDF", "#30FFCF", "#40FFBF", "#50FFAF", "#60FF9F", "#70FF8F", "#80FF80", "#8FFF70", "#9FFF60", "#AFFF50", "#BFFF40", "#CFFF30", "#DFFF20", "#EFFF10", "#FFFF00", "#FFEF00", "#FFDF00", "#FFCF00", "#FFBF00", "#FFAF00", "#FF9F00", "#FF8F00", "#FF8000", "#FF7000", "#FF6000", "#FF5000", "#FF4000", "#FF3000", "#FF2000", "#FF1000", "#FF0000", "#EF0000", "#DF0000", "#CF0000", "#BF0000", "#AF0000", "#9F0000", "#8F0000", "#800000")

#
#	Cvi islands:
#

jsfs = as.matrix(read.table("./data/sfs_interg_2020-06-08_5percN_noLastLanes_clean_2020-04-27_sub40_jsfsX.txt", head=F))


# Cvi to Morocco:
#
# jsfs = as.matrix(read.table("./data/sfs_cvi-mor_2020-06-08_jsfs.txt", head=F))


# Fixed in Cvi:
jsfs[nrow(jsfs), ncol(jsfs)]


jsfs[nrow(jsfs), ncol(jsfs)] = 0
jsfs[1,1] = 0

# All SNPs:
allSnps = sum(jsfs)
allSnps

# Fixed in Fogo, absent from Santo:
jsfs[1,ncol(jsfs)]

# Fixed in Santo, absent from Fogo:
jsfs[nrow(jsfs),1]


# Segregating in Santo:
segS=0
for (row in 2:(nrow(jsfs)-1)) {
  for (col in 1:ncol(jsfs)) {
    segS = segS + jsfs[row,col]
  }
}
segS

# Segregating in Fogo:
segF=0
for (col in 2:(ncol(jsfs)-1)) {
  for (row in 1:nrow(jsfs)) {
    segF = segF + jsfs[row,col]
  }
}
segF

# Shared polymorphisms
sharedPolim=0
for (col in 2:(ncol(jsfs)-1)) {
  for (row in 2:(nrow(jsfs)-1)) {
    sharedPolim = sharedPolim + jsfs[row,col]
  }
}
sharedPolim

# % shared variation:
sharedPolim/allSnps
# wg: 1-0.002510078         # 0.9974899
# interg: 1-0.006310319     # 0.9936897
# Morocco: 1-0.0006144124   # 0.9993856

jsfs = jsfs + 1
jsfs=log10(jsfs)

ticks<- c(0, 10, 100, 1000, 10000, 100000, 1000000)
labelS=c(expression(0), expression(10), expression(10^"2"), expression(10^"3"), expression(10^"4"), expression(10^"5"), expression(10^"6"))

###
# Whole genome
# 
# pdf("/Volumes/CVI/final/cvi/sfs/jsfs_wg_clean_2020-06-03_5percN.pdf", width=8, height=7)

# 
# CVI islands:
#
pdf("./results/fig1c.pdf", width=2.2, height=1.6)
# 

# Cvi to Morocco:
# 
# pdf("./results/fig1d.pdf", width=2.2, height=1.6)

# 
par(ps=7, mfrow=c(1,1), mar=c(1.1,1.1,0.5,0.5))


# Cvi islands:
#
image.plot(jsfs, xlab=list(expression(paste("Santo Ant", tilde(a), "o (40*freq.)"), ), cex=1.5), ylab=list(expression(paste("Fogo (40*freq.)"), ), cex=1.5), axis.args=list(at=c(0, log10(ticks[2:length(ticks)])), labels=labelS, cex.axis=1.5), col=colors, axes=F)

# Cvi to Morocco:
#
# image.plot(jsfs, axis.args=list(line=-0.8, at=c(0, log10(ticks[2:length(ticks)])), labels=labelS, lwd=-0, lwd.ticks=-0.5, tck=-0.5), col=colors, axes=F, legend.width=0.5, smallplot = c(.8, .84, .2, .8))

# 
# To Morocco
# image.plot(t(jsfs), axis.args=list(line=-0.8, at=c(0, log10(ticks[2:length(ticks)])), labels=labelS, lwd=-0, lwd.ticks=-0.5, tck=-0.5), col=colors, axes=F, legend.width=0.5, smallplot = c(.8, .84, .2, .8))
# image.plot(t(jsfs), ylab=list(expression(paste("Cape Verde (40*freq.)"), )), xlab=list(expression(paste("Morocco (54*freq.)"), )), axis.args=list(at=c(0, log10(ticks[2:length(ticks)])), labels=labelS), col=colors, axes=F)

title(main="")
axis(2, at=c(-01, 1.1), tick=T, labels=c("a", "a"), lwd.ticks=-1, lwd=1)          # , cex.axis=1.5
axis(2, las=2, at=c(0, 1), tick=F, labels=c("0", dim(jsfs)[[1]]-1), line=-0.9)
axis(1, at=c(-01, 1.1), tick=T, labels=c("a", "a"), lwd.ticks=-1, lwd=1)
axis(1, at=c(0, 1), tick=F, labels=c("0", dim(jsfs)[[2]]-1), line=-1.3)
axis(4, at=c(-01, 1.1), tick=T, labels=c("a", "a"), lwd.ticks=-1, lwd=1)
axis(3, at=c(-01, 1.1), tick=T, labels=c("a", "a"), lwd.ticks=-1, lwd=1)

#
title("", xlab=expression(paste("Santo Antão")), ylab=expression(paste("Fogo")), line=0.2)
#
# title("", xlab=expression(paste("Morocco")), ylab=expression(paste("Cape Verde")), line=0.2)

dev.off()


