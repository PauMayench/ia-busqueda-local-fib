from grafics import line_plot, line_plot_variances, box_plot, bar_3d
from read import get_lines_from_file, convert_to_ints
import os
import pprint
folder_name = "execution_outputs_shared/exp3/"

    
lambdaKs = []
for l in range(5):
    lit = []
    for filename in os.listdir(folder_name):
        file_path = os.path.join(folder_name, filename)

        if filename[4] == str(l):

            if os.path.isfile(file_path):
                lines = get_lines_from_file(file_path)  
                vals  = convert_to_ints(lines)
                median = sum(vals)/len(vals)
                lit.append(median)

    lambdaKs.append(lit)

pprint.pprint(lambdaKs)
bar_3d(lambdaKs)

#[10, 50, 500, 5000, 50000], [ 0.001, 0.01, 0.1, 0.5, 1]