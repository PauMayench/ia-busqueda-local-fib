import os
from read import get_lines_from_file, convert_to_ints

def getTimes(folder_name, heuristic = "1"):

    executionTimes = []
    totalServersTimes = []

    for filename in os.listdir(folder_name):
        file_path = os.path.join(folder_name, filename)

        if filename[9] == str(heuristic):
            if os.path.isfile(file_path):
                lines = get_lines_from_file(file_path)
                
                f = ""
                for letter in lines[-2]:
                    if letter == "u":
                        break
                    f += letter

                executionTimes.append(float(f))
                totalServersTimes.append(int(lines[-3]))

    return executionTimes, totalServersTimes

def m(v):
    return sum(v)/len(v)


folder_base = "execution_outputs/"

executionTimesH1, totalServersTimeH1 = getTimes(folder_base + "exp6/", 1)


executionTimesH2, totalServersTimeH2 = getTimes(folder_base + "exp6/", 2)

print("SA")
print( "                    \t\t h1            h2")
print(f"Execution time:     \t\t  {m(executionTimesH1):0.2f}          {m(executionTimesH2):0.2f}")
print(f"Total Servers time: \t\t  {m(totalServersTimeH1):0.2f}     {m(totalServersTimeH2):0.2f}")

print("_"*70)


print(m(totalServersTimeH1) - m(totalServersTimeH2))
print(m(executionTimesH1) - m(executionTimesH2))