options(scipen=5)
mu <- 7e-9
gen <- 1

HAxNMA<-read.table("results/HAxNMA.combined.txt", header=TRUE)
HAxR<-read.table("results/HAxR.combined.txt", header=TRUE)
RxNMA<-read.table("results/RxNMA.combined.txt", header=TRUE)
SMAxHA<-read.table("results/SMAxHA.combined.txt", header=TRUE)
SMAxNMA<-read.table("results/SMAxNMA.combined.txt", header=TRUE)
SMAxR<-read.table("results/SMAxR.combined.txt", header=TRUE)

plot(HAxNMA$left_time_boundary/mu*gen, 2 * HAxNMA$lambda_01 / (HAxNMA$lambda_00 + HAxNMA$lambda_11), xlim=c(10000,120000),ylim=c(0,1), type="n", xlab="Time in years; mu=7e-9, g=1", ylab="Relative Cross-Coalescence Rate")

path = "combined"
file.names <- dir(path, pattern =".txt")
for(i in 1:length(file.names)){
  file <- read.table(file.names[i],header=TRUE)
  lines(HAxNMA$left_time_boundary/mu*gen, 2 * HAxNMA$lambda_01 / (HAxNMA$lambda_00 + HAxNMA$lambda_11), type="s", col="black")
}



lines(HAxNMA$left_time_boundary/mu*gen, 2 * HAxNMA$lambda_01 / (HAxNMA$lambda_00 + HAxNMA$lambda_11), type="s", col="black")
lines(HAxR$left_time_boundary/mu*gen, 2 * HAxR$lambda_01 / (HAxR$lambda_00 + HAxR$lambda_11), type="s", col="red")
lines(RxNMA$left_time_boundary/mu*gen, 2 * RxNMA$lambda_01 / (RxNMA$lambda_00 + RxNMA$lambda_11), type="s", col="blue")
lines(SMAxHA$left_time_boundary/mu*gen, 2 * SMAxHA$lambda_01 / (SMAxHA$lambda_00 + SMAxHA$lambda_11), type="s", col="orange")
lines(SMAxNMA$left_time_boundary/mu*gen, 2 * SMAxNMA$lambda_01 / (SMAxNMA$lambda_00 + SMAxNMA$lambda_11), type="s", col="green")
lines(SMAxR$left_time_boundary/mu*gen, 2 * SMAxR$lambda_01 / (SMAxR$lambda_00 + SMAxR$lambda_11), type="s", col="purple")

legend("bottomright", c("HAxNMA", "HAxR", "RxNMA", "SMAxHA", "SMAxNMA", "SMAxR"), col=c("black", "red", "blue", "orange", "green", "purple"), lty=c(1,1,1,1,1,1), bty="n") 