
from LSexecutionPool import LSexecutionPool

'''
#python3 example.py   
#this will create a folder called execution_outputs on the directory that you are calling the python script


# add_HC(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic):
# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):


initial_state = "G" or "R"
heuristic = "h1" or "h2"

lambd = (float) 0.001


'''

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
