import os
from read import get_lines_from_file, convert_to_ints
from grafics import line2_plot
import matplotlib.pyplot as plt

def m(v):
    return sum(v)/len(v)

def getMinRepTimes(folder_name, heuristic = "1"):

    executionTimes = []
    totalServersTimes = []

    for i in range(5):
        executionTimesLOC = []
        totalServersTimesLOC = []
        for filename in os.listdir(folder_name):
            file_path = os.path.join(folder_name, filename)
            if filename[9] == str(heuristic):
                if filename[11] == str(i):
                    if os.path.isfile(file_path):
                        lines = get_lines_from_file(file_path)
                        for line in lines:
                            if "user" in line:
                                f = ""
                                for letter in lines[-2]:
                                    if letter == "u":
                                        break
                                    f += letter
                                executionTimesLOC.append(float(f))
                            else:
                                try:
                                    intg = int(line)
                                    if intg != 0:
                                        totalServersTimesLOC.append(intg)
                                except:
                                    pass
        executionTimes.append(m(executionTimesLOC))     
        totalServersTimes.append(m(totalServersTimesLOC))                   
    return executionTimes, totalServersTimes



folder_base = "execution_outputs/exp7/"

rep_files = [5, 10, 15, 20, 25]

executionTimesH1, totalServersTimeH1 = getMinRepTimes(folder_base , "1")

executionTimesH2, totalServersTimeH2 = getMinRepTimes(folder_base, "2")

print(executionTimesH1)
print(totalServersTimeH1)
print(executionTimesH2)
print(totalServersTimeH2)



print("_"*70)


numbers_list1 = executionTimesH1
numbers_list2 = executionTimesH2

x_values = range(len(numbers_list1))
x_values2 = range(len(numbers_list2))

# Ensure that both lists are plotted on the same x scale
max_length = max(len(numbers_list1), len(numbers_list2))
x_values = range(max_length)
if len(numbers_list1) < max_length:
    numbers_list1 += [None] * (max_length - len(numbers_list1))
if len(numbers_list2) < max_length:
    numbers_list2 += [None] * (max_length - len(numbers_list2))

plt.plot(x_values, numbers_list1, label="h1")  # You can specify marker='o' if needed
plt.plot(x_values, numbers_list2, label="h2")  # You can specify a different marker or linestyle

plt.xlabel("nombre minim de replicacions")
plt.ylabel("temps d'execució")
plt.xticks(x_values, rep_files)

plt.legend()

plt.show()