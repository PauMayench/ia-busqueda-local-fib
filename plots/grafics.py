import matplotlib.pyplot as plt
import numpy as np
#from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
from matplotlib import cm

#fa un plot amb els nombres que li passes
def line_plot(numbers_list, title="", name_x="", name_y=""):
    
    x_values = range(len(numbers_list))

    plt.plot(x_values, numbers_list) # , marker='o'

    plt.xlabel(name_x)
    plt.ylabel(name_y)
    plt.title(title)

    #plt.ylim(bottom=0) # fa que l'inici sigui 0
    #plt.xticks([])     # amaga els nombres de l'eix x
 
    plt.show()


#integers_list = [47000, 35000, 33000, 28000, 21000, 19000, 18900, 18600, 18550]
#line_plot(integers_list, title="Cost evolution", name_x="Cost", name_y="iterations")
    


#la primera llista son els valors de la grafica, la segona llista ha de tenir la mateixa mida i son les variances
def line_plot_variances(numbers_list, variances, title="", name_x="", name_y=""):
    
    x_values = range(len(numbers_list))

    plt.errorbar(x_values, numbers_list, yerr=variances, ecolor='orange')

    plt.xlabel(name_x)
    plt.ylabel(name_y)
    plt.title(title)

    plt.ylim(bottom=0) # fa que l'inici sigui 0
    #plt.xticks([])     # amaga els nombres de l'eix x

    plt.show()


#integers_list = [47000, 35000, 33000, 28000, 21000, 19000, 18900, 18600, 18550] * 10  # Example with more data points
#variances = [1000, 1500, 1200, 1100, 1800, 1300, 1400, 1600, 1700] * 10  # Example variances
#line_plot_variances(integers_list, variances, "Sample Line Plot", "Index", "Value")


# fa un boxplot, cada list en la llista es un box
def box_plot(list_lists, labels=None, title="", name_x="", name_y=""):

    plt.boxplot(list_lists, labels=labels)
    plt.title(title)
    plt.xlabel(name_x)
    plt.ylabel(name_y)
    plt.show()
    


#data_lists = [
#    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
#    [2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
#    [3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
#]
#labels = ["Move", "Swap", "Move i Swap"]
#box_plot(data_lists, labels, "Variances solucions segons operadors", "Operadors", "Cost")

#-------------------------------------------------------------------
def bar_3d(result):

    result = np.array(result, dtype=np.int)
    colors = ['r','b','g','y','b','p']
    fig=plt.figure(figsize=(8, 8), dpi=250)
    ax1=fig.add_subplot(111, projection='3d')
    ax1.set_xlabel('k', labelpad=10)
    ax1.set_ylabel('Lambda', labelpad=10)
    ax1.set_zlabel('Cost (temps servidor pitjor)')


    xlabels = np.array([10, 50, 500, 5000, 50000])
    xpos = np.arange(xlabels.shape[0])
    ylabels = np.array([0.0001, 0.001, 0.01, 0.1, 1])
    ypos = np.arange(ylabels.shape[0])

    xposM, yposM = np.meshgrid(xpos, ypos, copy=False)

    zpos=result
    zpos = zpos.ravel()

    dx=0.5
    dy=0.5
    dz=zpos

    ax1.w_xaxis.set_ticks(xpos + dx/2.)
    ax1.w_xaxis.set_ticklabels(xlabels)

    ax1.w_yaxis.set_ticks(ypos + dy/2.)
    ax1.w_yaxis.set_ticklabels(ylabels)

    values = np.linspace(0.2, 1., xposM.ravel().shape[0])
    colors = cm.rainbow(values)
    ax1.bar3d(xposM.ravel(), yposM.ravel(), dz*0, dx, dy, dz, color=colors)
    plt.show()



import matplotlib.pyplot as plt

# Fa un bar plot amb els nombres que li passes
def bar_plot(numbers_list,labels,  title="", name_x="", name_y=""):
    
    x_values = range(len(numbers_list))

    plt.bar(x_values, numbers_list) # Això crea un bar plot

    plt.xlabel(name_x)
    plt.ylabel(name_y)
    plt.title(title)

    #plt.ylim(bottom=0) # fa que l'inici sigui 0
    #plt.xticks()     # mostra els nombres de l'eix x
    plt.xticks(x_values, labels)
    plt.show()


import matplotlib.pyplot as plt

def line2_plot(numbers_list1, numbers_list2, title="", name_x="", name_y="", label1="Data 1", label2="Data 2"):
    
    x_values = range(len(numbers_list1))
    x_values2 = range(len(numbers_list2))

    # Ensure that both lists are plotted on the same x scale
    max_length = max(len(numbers_list1), len(numbers_list2))
    x_values = range(max_length)
    if len(numbers_list1) < max_length:
        numbers_list1 += [None] * (max_length - len(numbers_list1))
    if len(numbers_list2) < max_length:
        numbers_list2 += [None] * (max_length - len(numbers_list2))

    plt.plot(x_values, numbers_list1, label=label1)  # You can specify marker='o' if needed
    plt.plot(x_values, numbers_list2, label=label2)  # You can specify a different marker or linestyle

    plt.xlabel(name_x)
    plt.ylabel(name_y)
    plt.title(title)
    
    plt.legend()

    plt.show()
