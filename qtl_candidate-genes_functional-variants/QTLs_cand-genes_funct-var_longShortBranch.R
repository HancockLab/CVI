
# Run as:
# Rscript QTLs_cand-genes_funct-var_longShortBranch.R 

setwd("./CVI/qtl_candidate-genes_functional-variants")

####
##      Load QTLs, candidate genes and functional variants data
####

#
# Read tables
qtls_ls=as.data.frame(read.table("./data/percentagePrivate_smallPheno_2020-07-30.txt", header = F))
genes_ls=as.data.frame(read.table("./data/percentagePrivate_candGenes_2020-07-30.txt", header = F))


#   Organise QTLs data
# 
qtls_plot=qtls_ls$V4
stErr.qtls_plot = (1.96*sd(na.omit(qtls_plot)))/(sqrt(length(na.omit(qtls_plot))))

#   Organise candidate genes data
# 
genes_plot=genes_ls$V4
mean.genes_plot=mean(genes_plot)
stErr.genes_plot = (1.96*sd(na.omit(genes_plot)))/(sqrt(length(na.omit(genes_plot))))

#   Organise functional variants data
# 
variants_plot=c(1.0, 1.0, 1.0, 1.0, 1.0, # cry2, fri, mpk12, fls2, GI
                1.0, 1.0,                # The 2 single nucleotyde deletions: checked 
                0.0, 0.0,                # RPM1 and CBF2 deletions
                0.0, 0.0)                # cPGK2, ZTL
stErr.variants_plot = (1.96*sd(variants_plot))/(sqrt(length(variants_plot)))










###
#         Some stats on long/short branches
#          Poisson test to compare the rate of 2 poissons
gw.segr = 3214
gw.snps = 3214+416252

gw.prop = gw.segr/gw.snps
gw.prop

###
###
#     QTLs, 1st genome-wide then QTLs
###
###
qtl.segr = sum(qtls_ls[[1]])
qtl.snps = sum(qtls_ls[[1]])+sum(qtls_ls[[2]])

qtl.prop = qtl.segr/qtl.snps
qtl.prop

qtl.enrich=(qtl.prop) / (gw.prop) - 1
qtl.enrich


# Poisson test
#
events=c(gw.segr, qtl.segr)
trials=c(gw.snps, qtl.snps)
poisson.test(x=events, T=trials, r = 1, alternative = c("two.sided"))
mean(na.omit(qtls_ls[[3]]))


# Result:
# Comparison of Poisson rates

# data:  events time base: trials
# count1 = 3214, expected count1 = 3275.3, p-value = 0.2723
#alternative hypothesis: true rate ratio is not equal to 1
#95 percent confidence interval:
#  0.9456029 1.0156969
#sample estimates:
#  rate ratio 
#0.9801617 


###
###
#       Candidate genes
###
###
genes.segr = sum(genes_ls[[1]])
genes.snps = sum(genes_ls[[1]])+sum(genes_ls[[2]])

genes.prop = genes.segr/genes.snps
genes.prop

genes.enrich=(genes.prop) / (gw.prop) - 1
genes.enrich

events=c(gw.segr, genes.segr)
trials=c(gw.snps, genes.snps)

# Poisson test
#
poisson.test(x=events, T=trials, r = 1, alternative = c("two.sided"))

# Result: Comparison of Poisson rates

#data:  events time base: trials
#count1 = 3214, expected count1 = 3224.9, p-value = 0.07814
#alternative hypothesis: true rate ratio is not equal to 1
#95 percent confidence interval:
#  0.5750524 1.0454262
#sample estimates:
#  rate ratio 
# 0.7665383 




###
###
#       Functional variants
###
###
variants.segr = 6
variants.snps = 9

variants.prop = variants.segr/variants.snps
variants.prop

variants.enrich=(variants.prop) / (gw.prop) - 1
variants.enrich

events=c(gw.segr, variants.segr)
trials=c(gw.snps, variants.snps)


# Poisson test
#

poisson.test(x=events, T=trials, r = 1, alternative = c("two.sided"))

#Result: 	Comparison of Poisson rates

#data:  events time base: trials
#count1 = 3214, expected count1 = 3219.9, p-value = 1.417e-10
#alternative hypothesis: true rate ratio is not equal to 1
#95 percent confidence interval:
#  0.005274595 0.031341455
#sample estimates:
#  rate ratio 
# 0.01149318 




###
#         End of stats
###







####
#       Plot it on a log scale
####

offset=0.003
base=10

pdf("./fig_longShortBranch.pdf", height=1.8,width=2.7)
par(ps=7, mfrow=c(1,1), mar=c(2.,1.8,0.5,0.), mgp=c(3, -0.2, 3))

x=c(1.1, 1.3, 1.5)
x1 = jitter(rep(x[[1]], length(qtls_plot)))
x2 = jitter(rep(x[[2]], length(na.omit(genes_plot))))
x3 = jitter(rep(x[[3]], length(variants_plot)))
symb=c(21, 21, 21, 21)

colOr=c(rep(rgb(0/255,117/255,220/255, 0.8), 20))
col = c(rep(rgb(0/255,117/255,220/255, 0.9), 20))

colOr=c(rep("grey", 20))
col = c(rep(rgb(0/255,117/255,220/255, 0.9), 20))


minAxis=log(offset-0.00001, base=base)
plot(y=0, x=0, bg = col, col="black", type="n", lwd=0.3, cex=2, axes=F, xlim=c(1.0, 1.6), ylim=c(minAxis, log(1))) # log(c(0.00001, 1)))

title("", ylab=expression('% private variants'), line=0.9)
axis(1, at=c(1.0, 1.2, 1.4, 1.6), tick=TRUE, labels=F, cex.axis=1.2, line=0.)

mtext("QTL\nregions" , at=c(1.1), side=1, line=0.8)
mtext("Candidate\ngenes" , at=c(1.3), side=1, line=0.8)
mtext("Functional\nvariants" , at=c(1.5), side=1, line=0.8)

axis(2, at=c(log(0.01, base=base), log(0.1, base=base), log(1.0, base=base)), labels=F, tick=TRUE, las=1, tck=-0.07, line=-0.2)
axis(2, at=c(log(0.01, base=base), log(0.1, base=base), log(1.0, base=base)), labels=c(1, 10, 100), tick=F, las=1, lwd.ticks=-1, line=0.7)

smallTicks=c(seq(0.3, 0.9, 0.1), 2:9, seq(20, 90, 10))
smallTicks=smallTicks/100
smallTicks=log(smallTicks, base=base)
axis(2, at=smallTicks, labels=NA, tick=TRUE, las=1, lwd.ticks=0.3, tck=-0.05, line=-0.2)

segments(1.0, log(0.0076621227942193166, base=base), 1.6, log(0.0076621227942193166, base=base), lwd=0.5)

# Plot all Qtls
qtlMean=mean(na.omit(qtls_plot))
logQtlMean=log(qtlMean, base=base)

for (q in 1:length(qtls_plot)) {
  if (!is.na(qtls_plot[[q]]) && qtls_plot[[q]] == 0.0) {
    qtls_plot[[q]] = qtls_plot[[q]] + offset
  }
}
whyAxis=log(qtls_plot, base=base)
points(x=x1, y=whyAxis, bg = colOr[[1]], pch=symb[[1]], col="black", lwd=0.1, cex=0.8)

# And the mean
points(x=x[[1]], y=logQtlMean, bg = col[[1]], pch=symb[[1]], col="black", lwd=0.1, cex=2.)
arrows( x[[1]], log(qtlMean + stErr.qtls_plot, base=base), x[[1]], log(qtlMean - stErr.qtls_plot, base=base), angle=90, code=3, length=0.03) 


# Plot all candidate Genes

for (g in 1:length(genes_plot)) {
  if (!is.na(genes_plot[[g]]) && genes_plot[[g]] == 0.0) {
    genes_plot[[g]] = genes_plot[[g]] + offset
  }
}
whyAxis=log(genes_plot , base=base)
points(x=x2, y=whyAxis, bg = colOr[[2]], pch=symb[[2]], col="black", lwd=0.1, cex=0.8)

# And the mean
mean.genes_plot=mean(sum(genes_ls$V2)/(sum(genes_ls$V2)+sum(genes_ls$V3)))
meanGenes=log(mean.genes_plot, base=base)
points(x=x[[2]], y=meanGenes, bg = col[[2]], pch=symb[[2]], col="black", lwd=0.1, cex=2.)
arrows( x[[2]], log(mean.genes_plot + stErr.genes_plot, base=base), x[[2]], log(mean.genes_plot - stErr.genes_plot, base=base), angle=90, code=3, length=0.03) 


# Plot Variants
variants_plot_off=variants_plot
for (g in 1:length(variants_plot_off)) {
  if (!is.na(variants_plot_off[[g]]) && variants_plot_off[[g]] == 0.0) {
    variants_plot_off[[g]] = variants_plot_off[[g]] + offset
  }
}
whyAxis=log(variants_plot_off, base=base)
points(x=x3, y=whyAxis, bg = colOr[[3]], pch=symb[[3]], col="black", lwd=0.1, cex=0.8)

# And the mean
meanVar=log(mean(variants_plot), base=base)
points(x=x[[3]], y=meanVar, bg = col[[3]], pch=symb[[3]], col="black", lwd=0.1, cex=2.)
arrows( x[[3]], log(mean(variants_plot) + stErr.variants_plot, base=base), x[[3]], log(mean(variants_plot) - stErr.variants_plot, base=base), angle=90, code=3, length=0.03) 


dev.off()


