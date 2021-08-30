#
# Run as:
# Rscript plotProportions_chromoP_cvi.R

setwd("./CVI/population_structure/chromopainter/")
 
# 

st.err= function(x) sd(x)/sqrt(length(x))
means = vector()
stErr = vector()

###
#        Continental groups
###

m = as.matrix(read.table("path_to_first_result_file", head=T))
cviNames = as.String(as.matrix(read.table("./data/chromop_samples_cviPaper.txt", head=F))[,1])

sa_col= "#0075DC"
fogo_col= "#FFA405"
mor_col= "#2BCE48"
eur_col= "#F0A3FF"

colors=c(rep(mor_col, 4*148), rep(eur_col, 9*148) )
names= vector()
order = c(10, 13, 11, 12, 9, 1, 8, 2, 5, 7, 3, 4, 6)
for (c in 1:length(order)) {
  col=order[[c]]	
  #print(col)
  means[[c]] = mean(m[,col])
  stErr[[c]] = st.err(m[,col])
  names[[c]] = dimnames(m)[[2]][[col]]
}

matrMean= as.matrix(t(means))
matrStErr= as.matrix(t(stErr))

s=c(1:147)

for (c in 1:length(s)) {
  are= s[[c]]
  means = vector()
  stErr = vector()
  # Continents
  m = as.matrix(read.table( paste("path_to_results_files", are, "_propIterate.txt", sep=""), head=T))
  for (c in 1:length(order)) {
    col=order[[c]]	
    means[[c]] = mean(m[,col])
    stErr[[c]] = st.err(m[,col])
    #print(m[,col])
  }
  matrMean = rbind(matrMean, means)
  matrStErr = rbind(matrStErr, stErr)
}
rownames(matrMean)=NULL
rownames(matrStErr)=NULL


arrow.start <- matrMean +(matrStErr*1.96)
arrow.end <- matrMean-(matrStErr*1.96)


setEPS()
postscript("./figure_chromoP.eps", width=7, height=3)
par(ps=7, mfrow=c(1,1), mar=c(2,1.4,0.5,0.)) # , mgp=c(3, -0.2, 3))

par(mfrow=c(1,1), mar=c(5,5,3,3))
fig = barplot(matrMean, beside=T, ylim=c(0.0, 0.7), names.arg= NULL, las=2, horiz=F, col= colors, density=NULL, axes=FALSE, border=NA, ylab=list("Percent genomic ancestry")) # , xlim=c(0,(max(privatSnps)/1000)+5))

arrows(fig, arrow.start, fig, arrow.end, angle=90, code=3, length=0.002) 
names=c("Mha", "Msma", "Mnma", "Mrif", "Ir", "Weu", "Inr", "Ibc", "Ssw", "Ger", "Ceu", "Cas", "Nsw")

axis(1, at=seq(148/2, ((148/2)+(148+1)*12), by=(149)), labels=names, tick=F, cex=1.5, las=2)
axis(1, at=seq(0.5, (13*(148+1))+0.5, by=(149)), labels=F, tick=T, cex=1.4, las=1)
axis(2, at=c(0, 0.2, 0.4, 0.6), labels= c("0", "20", "40", "60"),  tick=TRUE, las=1, cex=1.2)

dev.off()




