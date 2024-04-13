from grafics import line_plot, line_plot_variances, box_plot, bar_3d, bar_plot
from read import get_lines_from_file, convert_to_ints
import os
import pprint
folder_name = "execution_outputs/exp3_2/"

    
v = []
for i in range(4):
    for filename in os.listdir(folder_name):
        file_path = os.path.join(folder_name, filename)

        if filename[5] == str(i):

            if os.path.isfile(file_path):
                lines = get_lines_from_file(file_path)  
                vals = convert_to_ints(lines)
                median = sum(vals)/len(vals)
                v.append(median)


pprint.pprint(v)
bar_plot(v, [1, 10, 50 ,100], name_x="stiter", name_y="cost (temps servidor max)" )

#[10, 50, 500, 5000, 50000], [ 0.001, 0.01, 0.1, 0.5, 1]