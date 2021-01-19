
# Run as:
# Rscript discretised_dfe.R

setwd("./scripts/dNdS_and_discretised_dfe/")
###
#       Get discretised dfe
###

###
#         Compare dnds cvi to Mor
###

dnds_fo=read.table("./data/fo_bootDnDs.txt")[,1]
dnds_sa=read.table("./data/sa_bootDnDs.txt")[,1]

dnds_ha=read.table("./data/mor-ha_bootDnDs.txt")[,1]
dnds_nma=read.table("./data/mor-nma_bootDnDs.txt")[,1]
dnds_sma=read.table("./data/mor-sma_bootDnDs.txt")[,1]
dnds_rif=read.table("./data/mor-rif_bootDnDs.txt")[,1]

wilcox.test(x=c(dnds_sa, dnds_fo), y=c(dnds_ha, dnds_nma, dnds_sma, dnds_rif), paired = F)

# 

# Fogo, Santo
est = list()

polyDFEfiles = c(
  "./data/highAtlas_04fold_polyDFE.txt_full_C",
  "./data/smAtlas_04fold_polyDFE.txt_full_C",
  "./data/nmAtlas_04fold_polyDFE.txt_full_C",
  "./data/rif_04fold_polyDFE.txt_full_C",
  "./data/santo_onePerStand_polyDFE.txt_full_C",
  "./data/fogo_onePerStand_polyDFE.txt_full_C"
)

for (filename in polyDFEfiles)
{
  est = c(est, parseOutput(filename))
}
print(length(est))




###########################
## summarize the DFE
###########################

dfe = t(sapply(est, getDiscretizedDFE, sRanges = c(-10, -1, 0, 1, 10)))
rownames(dfe) = sapply(est, getModelName)
print(dfe)


###
#       Plot dfe for all pops
###

pdf("./figures/fig2b.pdf", height=1.7, width=2.2)
par(ps=7, mfrow=c(1,1), mar=c(2,1.4,0.5,0.), mgp=c(3, -0.2, 3))

sa_col= "#0075DC"
fogo_col= "#FFA405"
mor_col= "#2BCE48"
eur_col= "#F0A3FF"

colors=c(rep(mor_col, 4), sa_col, fogo_col) #, rep(eur_col, 9))
colnames(dfe) = c( "<-10", "-10,-1", "-1,0", "0,1", "1,10", "10<")

par(ps=5)
dfe.bar=barplot(dfe, beside = TRUE, legend.text = F, col=colors, ylim = c(0, 1.), axes=F, axisnames=T)
par(ps=7)
# legend("topright", legend = c("Fogo", "S.Antão", "Morocco"), fill = colors)

# dev.off()



# All dircetories for the bootstrap:

bootDirs=c(
  "./data/mor_ha/",
  "./data/mor_sma/",
  "./data/mor_nma/",
  "./data/mor_rif/", 
  "./data/santo/",
  "./data/fogo/"
)

jit = 5
col=gray.colors(10, start = 0.6, end = 0.9, gamma = 2.2, 0.05)[[1]]

for (dd in 1:length(bootDirs)) {
  d = bootDirs[[dd]]
  dfeFiles=list.files(d, pattern="*_full_C")
  # print(dfeFiles)

  for (f in dfeFiles) {
    estBoot=parseOutput(paste(d, f, sep=""))

    # we can also change the binning
    dfeBoot = t(sapply(estBoot, getDiscretizedDFE, sRanges = c(-10, -1, 0, 1, 10)))
    # we can give names to the dfe matrix that partialy reflect which model was ran
    rownames(dfeBoot) = sapply(estBoot, getModelName)
    colnames(dfeBoot) = toNames(c(-10, -1, 0, 1, 10))
    # print(dfeBoot)

    points(x=(dfe.bar[dd,] - jit + jitter(jit)), y=dfeBoot[1,], bg=col, col=NULL, pch=22, cex=0.3)
    # points(x=(dfe.bar[dd,]), y=dfeBoot[1,], bg=col, col=NULL, type="-", cex=0.3)
  }
}


title(main="")
par(ps=7, mfrow=c(1,1), mar=c(2,1.4,0.5,0.), mgp=c(3, 0.5, 3))

axis(2, at=c(0, 0.5, 1.), labels=c("0.0", "0.5", "1.0"), tick=F, las=1, line=-0.5, lwd.ticks=-1)
axis(2, at=c(0, 1.), labels=c("", ""), tick=T, las=1, line=-0.2, lwd.ticks=-1)

title("", xlab=expression('Selection coefficient (S = 4N'[e]*'s)'), line=0.7)
title("", ylab=expression('Proportion of variation'), line=0.6)
#




dev.off()

