from read import get_lines_from_file, get_list_lines_from_folder, get_last_numbers_and_median_from_folder, convert_to_ints
from grafics import line_plot, line_plot_variances, box_plot
import os




def get_minims_folder_exp1(folder_name):
    #exp1-execucio-9-2
    minims = []
    for i in range(10):
        all_lines_i = []
        for filename in os.listdir(folder_name):
            file_path = os.path.join(folder_name, filename)
            if filename[-7] == str(i):
                if os.path.isfile(file_path):
                    lines = get_lines_from_file(file_path)  
                    all_lines_i.append(lines[-1]) 
        minims.append(min(convert_to_ints(all_lines_i)))
    return minims



execution_outputs_exp2 = "execution_outputs/exp2/"
randm = get_minims_folder_exp1(execution_outputs_exp2 )
print(randm)
greedy = int(get_lines_from_file(execution_outputs_exp2 + "exp2-execucio-G-1.out"  )[-1])

boxplot_values = [randm, greedy]

boxplot_labels = ["Random", "Greedy",]

box_plot(boxplot_values, boxplot_labels, "Solucio inicial", "","temps servidor màxim")