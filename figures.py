# -*- coding: utf-8 -*-
"""
Figures for the poster
"""

import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

def plotVals(csvFile,xlab,ylab,title):
    dat = pd.read_csv(csvFile)
    x = dat.iloc[:,0]
    y = dat.iloc[:,1]
    
    sns.set_style("whitegrid",{"axes.facecolor":"#EEECE1","figure.facecolor":"#EEECE1"
                ,"xtick.color":".2","ytick.color":".2",
                "text.color":".1","axes.labelcolor":".1"})
                
    plt.plot(x,y,color = '#00274c')
    plt.xlabel(xlab)
    plt.ylabel(ylab)
    plt.title(title)
                
    

    
    