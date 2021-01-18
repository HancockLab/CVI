import matplotlib
matplotlib.use('Agg')
import sys
import dadi
import numpy
import pylab
import demographic_models

from numpy import array

res0=sys.argv[2]

###
#		Cape Verde
###

data = dadi.Spectrum.from_file(sys.argv[1])
ns = data.sample_sizes
pts_l = [40, 50, 60]

data1 = data.fold()
data=data1
data.mask[0,0]

print('data:')
print(data)

all_boot=[]

for i in range(100000):
    sample = data.sample()
    all_boot.append(sample)
    # print(sample)

print("Now play")



###
#           Uncertaintly analysis
###
func = demographic_models.bottleneck
func_ex = dadi.Numerics.make_extrap_log_func(func)
pts_l = [40, 50, 60]
p0=[4.4840916, 0.2769054, 6.6288414, 0.6486232, 1.9196475]

#
uncert = dadi.Godambe.GIM_uncert(func_ex, pts_l, all_boot, p0, data, log = False, multinom = True, return_GIM = False)
print("p0:")
print(p0)

print("uncert")
print(uncert)

f=open(res0 + "uncert.txt", 'w+')

f.write(str(p0) + "\n")
f.write(str(uncert) + "\n")
f.close()




