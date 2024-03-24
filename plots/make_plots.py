from read import get_lines_from_file, get_list_lines_from_folder, get_last_numbers_and_median_from_folder
from plots import line_plot, line_plot_variances, box_plot

file_name = "execution_outputs/prova"

foldernums = ["2", "3", "4"]

all_lines = []
for foldernum in foldernums:

    lines, _ = get_last_numbers_and_median_from_folder(file_name+foldernum)
    all_lines.append(lines)


box_plot(all_lines)