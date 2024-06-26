import subprocess, os, time
from datetime import datetime

'''
Example as documentation:


# add_HC(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic):
# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

#initial_state = "G" or "R"
#heuristic = "h1" or "h2"

#lambd = (float) 0.001





pool = LSexecutionPool() # if optional parameter is False, it wont ask for the name of the folder

#example of simple calls, they are not executed until pool.execute_parallel is called

pool.add_HC("execution-g-h2", 200, 5, 50, 5, 123, 'G', 'h2')

pool.add_SA('sa_output', 200, 5, 50, 5, 0, 'G', 'h2', 1000, 100, 5, 0.95)



#example of loop


lambds = [0.1, 0.001, 0.0001]

for lambd in lambds:
    #IMPORTANT: remember to change the name of each output file, if not they will overwritte eachother
    pool.add_SA(f'sa_output_lambd_{lambd}', 200, 5, 50, 5, 0, 'G', 'h2', 1000, 100, 5, lambd)




#pool.execute_sequential()

pool.execute_parallel() #optional integer parameter, max number of cores to execute in parallel


'''

class LSexecutionPool():
    execution_outputs_location = "."
#this will create a folder called execution_outputs on the directory that you are calling the python script
    def __init__(self, ask_folder_name=True):
        
        self.pendent_executions = []

        name = ""
        if ask_folder_name:
            name = input("Name of the new execution folder (d-name to add the Y-m-H-M) (press enter to leave blank):")
        if (name[0] == "d" and name[1] == "-") or not name:
            folder_name = datetime.now().strftime("%Y-%m-%d_%H_%M") + (("_" + name[2::]) if name else "")
        else:
            folder_name = name

        full_folder_path = f"{self.execution_outputs_location}/execution_outputs/{folder_name}/"
        os.makedirs(full_folder_path, exist_ok=True) 

        self.full_folder_path = full_folder_path
    
    def add_HC(self, name_file, number_of_users, max_number_files_user_can_request, number_servers, minimum_replications_per_file, seed, initial_state, heuristic, random_seed=None):
        
        output_path = self.full_folder_path + name_file
        output_path.replace(".", "_")
        
        command = ['time','make', 'run'] + [str(number_of_users), str(max_number_files_user_can_request), str(number_servers), str(minimum_replications_per_file), str(seed), 'HC', initial_state, heuristic]
        if random_seed:
            command.append(str(random_seed))
        command.append(output_path + ".out")
        self.pendent_executions.append(command)

    def add_SA(self, name_file, number_of_users, max_number_files_user_can_request, number_servers, minimum_replications_per_file, seed, initial_state, heuristic, steps, stiter, k, lambd, random_seed=None):
        
        output_path = self.full_folder_path + name_file
        output_path.replace(".", "_")

        command = ['time','make', 'run'] + [str(number_of_users), str(max_number_files_user_can_request), str(number_servers), str(minimum_replications_per_file), str(seed), 'SA', initial_state, heuristic, str(steps), str(stiter), str(k), str(lambd)]
        
        if random_seed:
            command.append(str(random_seed))
            
        command.append(output_path + ".out")

        self.pendent_executions.append(command)


    def execute_sequential(self):
        self.execute_parallel(1)
    
    #parameter
    def execute_parallel(self, n_processors_max=4):

        active_processes = []
        l = len(self.pendent_executions)
        for i, exec in enumerate(self.pendent_executions):
            if len(active_processes) >= n_processors_max:

                while len(active_processes) >= n_processors_max:
                    for p in active_processes:
                        if p.poll() is not None:
                            active_processes.remove(p)
                            break 
                    time.sleep(0.1)

            try:
                output_file_path = exec[-1]
                with open(output_file_path, 'a') as output_file:
                    #p = subprocess.Popen(exec[0:-1], stdout=output_file, stderr=subprocess.STDOUT)
                    p = subprocess.Popen(' '.join(exec[0:-1]), stdout=output_file, stderr=subprocess.STDOUT, shell=True)
                    active_processes.append(p)
                    print(f'\rNow executing: {i+1}/{l}', end='')
            except Exception as e:
                print(f"Some error occurred: {e}, please see the output file: {output_file_path}")

        for p in active_processes:
            p.wait()

        for exec in self.pendent_executions:
            self.filter_output_file(exec[-1])

        print(f"\nAll the executions have finished, check the output files on the folder: {self.full_folder_path}")


    def filter_output_file(self, output_file_path):
        with open(output_file_path, 'r') as file:
            lines = file.readlines()
        with open(output_file_path, 'w') as file:
            for line in lines:
                if "make[" not in line:
                    file.write(line)