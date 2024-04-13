from grafics import line_plot, line_plot_variances, box_plot, bar_3d
from read import get_lines_from_file, convert_to_ints
import os
import pprint
import matplotlib.pyplot as plt
import numpy as np

folder_name = "execution_outputs/exp4_2c/"

    
times = []
for n_us in range(10, 16 + 10 ):
    for filename in os.listdir(folder_name):
        file_path = os.path.join(folder_name, filename)

        if filename[3:5] == str(n_us):

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

print(len(times))
x_values = range(len(times))

plt.plot(x_values, times) # , marker='o'

plt.xlabel("servidors")
plt.ylabel("time (s)")


labels = [50, 100, 150, 200 ,250 , 300, 350, 400, 450, 500]
labels = [50, 100, 150, 200 ,250 , 300, 350, 400, 450, 500, 550, 600, 650, 700 ,750, 800]

plt.xticks(x_values, labels)


plt.show()
