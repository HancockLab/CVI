
# Run as:
# Rscript fixDerived_vsIsland.R

setwd("CVI directory here")

######
###
#      Looking at N at the split, Santo
#       This one is for paper extended data figure
###
######


# jsfs = as.matrix(read.table("/Users/andrea/git/CVI/demographic_inference/dadi/data/sfs_interg_2020-06-03_5percN_clean_2020-04-27_sub40_jsfsX.txt", head=F, sep="\t"))
jsfs = as.matrix(read.table("./scripts/demographic_inference/dadi/data/sfs_wg_2020-06-03_5percN_clean_2020-04-27_sub40_jsfsX.txt", head=F, sep="\t"))
sumJsfs = 0

for (row in 1:length(jsfs[,1])) {
  for (col in 1:(length(jsfs[1,])-1) ) {
    if ( !(row == 1 && col == 1) && !(row == length(jsfs[,1]) && col == length(jsfs[1,])-1 ) ) {
      sumJsfs = sumJsfs + jsfs[row,col]
    }
  }
}
sumJsfs # 

fixFSegrS = sum(jsfs[,41][2:(length(jsfs[,41])-1)])
fixSSegrF = sum(jsfs[41,][2:(length(jsfs[,41])-2)])

stat = (fixFSegrS/sumJsfs) - (fixSSegrF/sumJsfs)
stat



ymax=0.005

pdf(paste("./scripts/demographic_inference/slim_simulations/figures/edf4d.pdf", sep=""), height=2.5,width=3)

par(ps=7, mfrow=c(1,1), mar=c(2.,2.,0.5,0.)) 
plot(0, 0, type="n", axes=F, ylab=NULL, xlab=NULL, xlim=c(0, 2501), ylim=c(-ymax, ymax))

axis(1, at=c(0, 2550), tick=T, labels=c("", ""), las=1, line=-0.2, lwd.ticks=-1)
axis(1, at=c(100, 500, 1000, 2000), tick=F, labels=c(0.1, 0.5, 1, 2), las=1, line=-1.2, lwd.ticks=-1)
title("", xlab=expression(paste(N[SA], " (", 10^"3", ")", sep="")), cex=1.5, line=0.7)

axis(2, at=c(-ymax, ymax), labels=c("", ""), tick=T, las=1, line=-0.5, lwd.ticks=-1)
axis(2, at=c(-0.004, -0.002, 0, 0.002, 0.004), labels=c(-4, -2, 0, 2, 4), tick=F, las=1, line=-1.2, lwd.ticks=-1)
title("", ylab=expression(paste("Percent fix"[(Fogo)] - "percent fix"[(SA)], " (", x10^"-3", ")", sep = "")), cex=1.5, line=0.6)




santo.col<- rgb(46/255, 156/255, 253/255, 0.2)
fogo.col<- rgb(255/255, 164/255, 5/255, 0.2)

polygon(x=c(0, 2550, 2550, 0), y=c(-ymax, -ymax, 0.0, 0.0), col = fogo.col, border = fogo.col)
polygon(x=c(0, 2550, 2550, 0), y=c(ymax, ymax, 0.0, 0.0), col = santo.col, border = santo.col)


medians=vector()


for (ns in c(100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1050, 1100, 1150, 1200, 1250, 1300, 1350, 1400, 1450, 1500, 1550, 1600, 1650, 1700, 1750, 1800, 1850, 1900, 1950, 2000, 2050, 2100, 2150, 2200, 2250, 2300, 2350, 2400, 2450, 2500) ) {
  for (nf in c(400) ) {
    for (tFogo in c(1000) ) {
      
      distrib=vector()
      
      for (rep in 0:30) {
        fileName=paste("./scripts/demographic_inference/slim_simulations/results/ttot7000_nsStart1_nfStart400_nsStart1_nfStart400_ns", ns, "_nf", nf, "_tf", tFogo, "_rep", rep, "_jsfs_fixSegrega.txt", sep="")
        
        if (file.exists(fileName)) {
          file <- file(fileName)
          fileStr <- strsplit(readLines(file), "\t")
          close(file)
          
          # distrib = append(distrib, as.double(fileStr[[1]][6]))
          # print(as.double(fileStr[[1]][5]))
          # points(x=rep(ns, length(as.double(fileStr[[1]][5]))), y=as.double(fileStr[[1]][5]), type="p", col=rgb(20/255, 40/255,   (( 155+100*((tFogo/nf)/20)))/255, (tFogo/nf)/20), ylab="", xlab="", cex=1.)
          # points(x=rep(ns, length(as.double(fileStr[[1]][5]))), y=-as.double(fileStr[[1]][5]), type="p", col=rgb( (( 155+100*((tFogo/nf)/20)))/255, 40/255, 50/255, (tFogo/nf)/20), ylab="", xlab="", cex=1.)
          points(x=rep(ns, length(as.double(fileStr[[1]][5]))), y=as.double(fileStr[[1]][5]), type="p", col=rgb(46/255,156/255,253/255, (tFogo/nf)/20), ylab="", xlab="", cex=1.)
          points(x=rep(ns, length(as.double(fileStr[[1]][5]))), y=-as.double(fileStr[[1]][5]), type="p", col=rgb(255/255, 164/255, 5/255, (tFogo/nf)/20), ylab="", xlab="", cex=1.)
        }
      }
    }
  }
}

segments(0, stat, 2550, stat, col="black")
dev.off()



