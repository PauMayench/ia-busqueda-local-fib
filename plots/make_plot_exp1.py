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



execution_outputs = "execution_outputs_shared/exp1_v2/"
moves = get_minims_folder_exp1(execution_outputs + "moves")
swaps = get_minims_folder_exp1(execution_outputs + "swaps")
moves_i_swaps = get_minims_folder_exp1(execution_outputs + "move_i_swap")


# Bool per si es vol fer output dels operadors, per despres copiar-ho i enganxar-ho a R
tstudent = True

if (tstudent):
    # Fer t-student al R:
    # tenim els valors minims, dels swaps i moves_i_swaps
    print(swaps)
    print(moves_i_swaps)

    # Obrir el R:
    # swaps = c(46341, 46344,    )   # copiar el output
    # swaps_moves = c(....)

    # Fer t-student
    # result <- t.test(swaps, swaps_moves)
    # print(result)

# =======================

boxplot_values = [ swaps, moves_i_swaps]

boxplot_labels = [ "swaps", "moves_i_swaps"]

box_plot(boxplot_values, boxplot_labels, "Operadors", "","temps servidor màxim")