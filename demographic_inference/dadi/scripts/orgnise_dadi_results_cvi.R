

###
#       Organise dadi results
###

# Unfolded
setwd("./CVI/demographic_inference/dadi/")
mu=7.1*10^(-9)
L=as.integer(as.matrix(read.table("./data/clean_2020-04-27_sub40_snps_nonN_inTheMask.txt", head=F))[1,1])
model_result_files=c("./resultspop-split_cvi_allRuns.txt", "splitExp_cvi_allRuns.txt", "im_cvi_allRuns.txt", "bottleneck_cvi_allRuns.txt")
writeFile="./results/summary_cvi.txt"

write("Organising dadi results", file=writeFile)
write.table(data.frame("Model", "Max_logLikelihood", "AIC", "Theta", "Reference_popSize", "n1_start", "n2_start", "n1_end", "n2_end", "time(split)", "m12", "m21", "time(bottleneck)"), 
            file=writeFile, append=TRUE, sep="\t", row.names=FALSE, col.names=FALSE, quote=FALSE)



######
###         Simple split
######
# 

nFile = 1
modelFile=model_result_files[[nFile]]

runs= as.matrix(read.table(modelFile, head=F))
ll = runs[,2]

# Get max ll run
# 
max_ll=ll[ll == max(ll)]
max_ll_index=which(ll == max(ll))

###
#           Convert parameters
#           N1,N2,T = params
###

theta = as.double(runs[max_ll_index, "V3"])
theta/L
Nref= theta/(2*mu*L)

N1=as.double(runs[max_ll_index,"V4"])*Nref
N2=as.double(runs[max_ll_index,"V5"])*Nref
Time=as.double(runs[max_ll_index,"V6"])*Nref

npar=3
AIC = 2*npar - 2*max_ll

print("N1,N2,T = params")
print(runs[max_ll_index,])
write.table(data.frame("simpleSplit", max_ll, AIC, theta, Nref, N1, N2, "x", "x", Time, "x", "x", "x"), 
            file=writeFile, append=TRUE, sep="\t", row.names=FALSE, col.names=FALSE, quote=FALSE)






######
###         Split and exponential growth
######

nFile = 2
modelFile=model_result_files[[nFile]]

runs= as.matrix(read.table(modelFile, head=F))
ll = runs[,2]

ll=runs[,2][runs[,2] < -300]
max_ll=ll[ll == max(ll)]

ll[ll >= quantile(ll, 0.75) ]

# plot(y=runs[,2][ll >= quantile(ll, quant) ], x=runs[,3][ll >= quantile(ll, quant) ], col="blue3", ylab = list("-logLikelihood", cex=1.2), xlab = list("N1", cex=1.2))
# text(y=runs[,2][ll >= quantile(ll, quant) ], x=runs[,3][ll >= quantile(ll, quant) ], labels=runs[,1][ll >= quantile(ll, quant) ], cex=0.8)

# Get max ll run
# 
max_ll=ll[ll == max(ll)]
max_ll_index=which(ll == max(ll))


###
#           Convert parameters
#           s,nu1,nu2,T
###

theta = as.double(runs[max_ll_index, "V3"])
theta/L
Nref= theta/(2*mu*L)

s=as.double(runs[max_ll_index,"V4"])
nStart1=s*Nref
nStart2=(1-s)*Nref
N1=as.double(runs[max_ll_index,"V5"])*Nref
N2=as.double(runs[max_ll_index,"V6"])*Nref
Time=as.double(runs[max_ll_index,"V7"])*Nref
# 
npar=4
AIC = 2*npar - 2*max_ll

print("s, nu1, nu2, T")
print(runs[max_ll_index,])
write.table(data.frame("Exp", max_ll, AIC, theta, Nref, nStart1, nStart2, N1, N2, Time, "x", "x", "x"), 
            file=writeFile, append=TRUE, sep="\t", row.names=FALSE, col.names=FALSE, quote=FALSE)






######
###         IM
######

nFile = 3
modelFile=model_result_files[[nFile]]

runs= as.matrix(read.table(modelFile, head=F))
ll = runs[,2]

# Get max ll run
# 
max_ll=ll[ll == max(ll)]
max_ll_index=which(ll == max(ll))

###
#           Convert parameters
#           s,nu1,nu2,T,m12,m21 = params
###

theta = as.double(runs[max_ll_index, "V3"])
theta/L
Nref= theta/(2*mu*L)
Nref

s=as.double(runs[max_ll_index,"V4"])
nStart1=s*Nref
nStart2=(1-s)*Nref
N1=as.double(runs[max_ll_index,"V5"])*Nref
N2=as.double(runs[max_ll_index,"V6"])*Nref
Time=as.double(runs[max_ll_index,"V7"])*Nref
m12=as.double(runs[max_ll_index,"V8"])/Nref
m21=as.double(runs[max_ll_index,"V9"])/Nref
# 
npar=6
AIC = 2*npar - 2*max_ll

print("s,nu1,nu2,T,m12,m21 = params")
print(runs[max_ll_index,])
write.table(data.frame("im", max_ll, AIC, theta, Nref, nStart1, nStart2, N1, N2, Time, m12, m21, "x"), 
            file=writeFile, append=TRUE, sep="\t", row.names=FALSE, col.names=FALSE, quote=FALSE)











######
###         Bottleneck
######

nFile = 4
modelFile=model_result_files[[nFile]]

runs= as.matrix(read.table(modelFile, head=F))
ll = runs[,2]

# Get max ll run
# 
max_ll=ll[ll == max(ll)]
max_ll_index=which(ll == max(ll))

# 
ll=runs[,2][runs[,2] < -280]
max_ll=ll[ll == max(ll)]
max_ll_index=which(ll == max(ll))


###
#           Convert parameters
#           nu1,nuB,nuF,Tb,Tf = params
###

theta = as.double(runs[max_ll_index, "V3"])
theta/L
Nref= theta/(2*mu*L)
Nref

N1=as.double(runs[max_ll_index,"V4"])*Nref
Nb=as.double(runs[max_ll_index,"V5"])*Nref
Nf=as.double(runs[max_ll_index,"V6"])*Nref

TimeB=as.double(runs[max_ll_index,"V7"])*Nref
Timef=as.double(runs[max_ll_index,"V8"])*Nref
Time=TimeB+Timef
# 


# AIC=2k-2ln(ll)
# 
npar=5
AIC = 2*npar - 2*max_ll

print("nu1,nuB,nuF,Tb,Tf = params")
print(runs[max_ll_index,])
write.table(data.frame("bottleneck", max_ll, AIC, theta, Nref, N1, Nb, N1, Nf, Time, "x", "x", TimeB), 
            file=writeFile, append=TRUE, sep="\t", row.names=FALSE, col.names=FALSE, quote=FALSE)


