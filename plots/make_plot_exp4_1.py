from grafics import line_plot, line_plot_variances, box_plot, bar_3d
from read import get_lines_from_file, convert_to_ints
import os
import pprint
import matplotlib.pyplot as plt
import numpy as np

folder_name = "execution_outputs/exp4_1b/"

    
times = []
for n_us in range(9):
    for filename in os.listdir(folder_name):
        file_path = os.path.join(folder_name, filename)

        if filename[3] == str(n_us):

            if os.path.isfile(file_path):
                lines = get_lines_from_file(file_path)
                userLines = [elem for elem in lines if "user" in elem]
                floats = []
                for line in userLines:
                    f = ""
                    for letter in line:
                        if letter == "u":
                            break
                        f += letter
                    floats.append(float(f))

                median = sum(floats)/len(floats)

                times.append(median)

pprint.pprint(times)


x_values = range(len(times))

plt.plot(x_values, times) # , marker='o'

plt.xlabel("nombre d'usuaris")
plt.ylabel("time (s)")

plt.xticks(x_values, [100, 200, 300, 400 ,500, 600, 700, 800, 900])


plt.show()
