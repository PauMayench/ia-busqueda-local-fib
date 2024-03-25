import matplotlib.pyplot as plt

#fa un plot amb els nombres que li passes
def line_plot(numbers_list, title="", name_x="", name_y=""):
    
    x_values = range(len(numbers_list))

    plt.plot(x_values, numbers_list) # , marker='o'

    plt.xlabel(name_x)
    plt.ylabel(name_y)
    plt.title(title)

    plt.ylim(bottom=0) # fa que l'inici sigui 0
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
