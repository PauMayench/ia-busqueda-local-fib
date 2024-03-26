from read import get_lines_from_file, get_list_lines_from_folder, get_last_numbers_and_median_from_folder, convert_to_ints
from grafics import line_plot, line_plot_variances, box_plot
import os
import math



def get_minims_folder_exp2(folder_name):
    #exp1-execucio-9-2
    minims = []
    for i in range(10, 60):
        all_lines_i = []
        for filename in os.listdir(folder_name):
            file_path = os.path.join(folder_name, filename)
            try:
                if filename[14:16] == str(i):
                    if os.path.isfile(file_path):
                        lines = get_lines_from_file(file_path)  
                        all_lines_i.append(lines[-1]) 
            except:
                pass
        minims.append(min(convert_to_ints(all_lines_i)))
    return minims

def get_greedys_exp2(folder_name):
    minims = []

    for filename in os.listdir(folder_name):
        file_path = os.path.join(folder_name, filename)
        try:
            if filename[14:16] == "GG":
                if os.path.isfile(file_path):
                    lines = get_lines_from_file(file_path)  
                    minims.append(lines[-1])
        except:
            pass
            
    return minims

execution_outputs_exp2 = "execution_outputs/exp2/"
randm = get_minims_folder_exp2(execution_outputs_exp2 )
print(randm)
greedys = convert_to_ints( get_greedys_exp2(execution_outputs_exp2))


print(greedys)

boxplot_labels = ["Random", "Greedy",]
boxplot_values = [randm, greedys]
box_plot(boxplot_values, boxplot_labels, "Solucio inicial", "","temps servidor màxim")


#randmLog = [math.log(elem,10) for elem in randm]
#greedyLog = [math.log(elem,10) for elem in greedys]
#boxplot_values = [randm, greedys]



#box_plot(boxplot_values, boxplot_labels, "Solucio inicial", "","temps servidor màxim")