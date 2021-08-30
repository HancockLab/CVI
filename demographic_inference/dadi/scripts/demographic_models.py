import numpy
from dadi import Numerics, PhiManip, Integration, Spectrum



def pop_split(params, ns, pts):
    N1,N2,T = params
    """
    
    """
    xx = Numerics.default_grid(pts)

    phi = PhiManip.phi_1D(xx)
    phi = PhiManip.phi_1D_to_2D(xx, phi)
    phi = Integration.two_pops(phi, xx, T, N1, N2, m12=0, m21=0)

    fs = Spectrum.from_phi(phi, ns, (xx,xx))
    return fs


def pop_splitExp(params, ns, pts):
    s,nu1,nu2,T = params
    """
    
    """
    xx = Numerics.default_grid(pts)

    phi = PhiManip.phi_1D(xx)
    phi = PhiManip.phi_1D_to_2D(xx, phi)
    
    nu1_func = lambda t: s * (nu1/s)**(t/T)
    nu2_func = lambda t: (1-s) * (nu2/(1-s))**(t/T)
    
    phi = Integration.two_pops(phi, xx, T, nu1_func, nu2_func, m12=0, m21=0) 
    
    fs = Spectrum.from_phi(phi, ns, (xx,xx)) 
    return fs



def bottleneck(params, ns, pts):
    nu1,nuB,nuF,Tb,Tf = params
    
    xx = Numerics.default_grid(pts)

    phi = PhiManip.phi_1D(xx)
    phi = PhiManip.phi_1D_to_2D(xx, phi)
    
    phi = Integration.two_pops(phi, xx, Tb, nu1, nuB, m12=0, m21=0)
    phi = Integration.two_pops(phi, xx, Tf, nu1, nuF, m12=0, m21=0)
    
    fs = Spectrum.from_phi(phi, ns, (xx,xx))
    return fs


def bottleneck2(params, ns, pts):
    nu1,nuB,nuF,T,Tb,Tf = params
    
    xx = Numerics.default_grid(pts)
    
    phi = PhiManip.phi_1D(xx)
    phi = PhiManip.phi_1D_to_2D(xx, phi)
    
    phi = Integration.two_pops(phi, xx, T, nu1, nuF, m12=0, m21=0)
    
    phi = Integration.two_pops(phi, xx, Tb, nu1, nuB, m12=0, m21=0)
    phi = Integration.two_pops(phi, xx, Tf, nu1, nuF, m12=0, m21=0)
    
    fs = Spectrum.from_phi(phi, ns, (xx,xx))
    return fs

def IM(params, ns, pts): 
    s,nu1,nu2,T,m12,m21 = params
    
    xx = Numerics.default_grid(pts)
    
    phi = PhiManip.phi_1D(xx)
    phi = PhiManip.phi_1D_to_2D(xx, phi)

    nu1_func = lambda t: s * (nu1/s)**(t/T)
    nu2_func = lambda t: (1-s) * (nu2/(1-s))**(t/T)

    phi = Integration.two_pops(phi, xx, T, nu1_func, nu2_func,m12=m12, m21=m21)
    
    fs = Spectrum.from_phi(phi, ns, (xx,xx)) 
    return fs




