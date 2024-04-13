from read import get_lines_from_file, get_list_lines_from_folder, get_last_numbers_and_median_from_folder, convert_to_ints
from grafics import line_plot, line_plot_variances, box_plot
import os
import math


file_path = "execution_outputs/SA_prova/sa_output.out"
lines = get_lines_from_file(file_path)
cost_in_iterations = convert_to_ints(lines)

line_plot(cost_in_iterations)