import sys
import math
import bisect
from scipy.interpolate import interp1d

class MSMCresult:
    """
    Simple class to read a MSMC result file. Constructor takes filename of MSMC result file
    """
    def __init__(self, filename):
        f = open(filename, "r")
        self.times_left = []
        self.times_right = []
        self.lambdas = []
        next(f) # skip header
        for line in f:
            fields = line.strip().split()
            nr_lambdas = len(fields) - 3
            if len(self.lambdas) == 0:
                self.lambdas = [[] for i in range(nr_lambdas)]
            time_left = float(fields[1])
            time_right = float(fields[2])
            self.times_left.append(time_left)
            self.times_right.append(time_right)
            for i in range(nr_lambdas):
                l = float(fields[3 + i])
                self.lambdas[i].append(l)
        self.T = len(self.times_left)
        self.times_left[0] = self.times_left[1] / 4.0
        self.times_right[-1] = self.times_right[-2] * 4.0

    def getInterp(self):
        x = [0.0] + [0.5 * (tl + tr) for tl, tr in zip(self.times_left, self.times_right)]
        y = [0.0] + self.lambdas[0]
        return (x[0], x[-1], interp1d(x, y))
def popSizeStepPlot(filename, mu=1.25e-8, gen=30.0):
    """
    to be used with a step-plot function, e.g. matplotlib.pyplot.steps.
    returns (x, y), where x contains the left point of each step-segment in years, and y contains the effective population size. Note that there are two ways to make a step-plot. You should make sure that your step-plot routine moves right and then up/down instead of the other way around.
    If plotted on a logarithmic x-axis, you should adjust x[0] = x[1] / 4.0, otherwise the leftmost segment will start at 0 and won't be plotted on a log-axis.

    Options:
        mu: Mutation rate per generation per basepair (default=1.25e-8)
        gen: generation time in years (default=30)
    """
    M = MSMCresult(filename)
    x = [(t/2) * gen / mu for t in M.times_left] # divide t by 2 because selfing
    y = [(1.0 / (l/2)) / (2.0 * mu) for l in M.lambdas[0]] # divide l by 2 because selfing
    return (x, y)

x =  popSizeStepPlot("results/"+sys.argv[1]+".self.final.txt", mu=0.7e-8, gen=1.0)
print(x[0])
print(x[1])
