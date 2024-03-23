
from LSexecutionPool import LSexecutionPool

'''
# add_HC(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic):
# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):


pool.add_HC("execution-g-h2", 200, 5, 50, 5, 123, 'G', 'h2')

pool.add_SA('sa_output', 200, 5, 50, 5, 0, 'G', 'h2', 1000, 100, 5, 0.95, 33)



'''

pool = LSexecutionPool() 


pool.add_HC("execution-g-h2", 200, 5, 50, 5, 123, 'G', 'h2')

pool.add_SA('sa_output', 200, 5, 50, 5, 0, 'G', 'h2', 1000, 100, 5, 0.95, 33)


lambds = [0.1, 0.001, 0.0001]

for lambd in lambds:
    pool.add_SA(f'sa_output_lambd_{lambd}', 200, 5, 50, 5, 0, 'G', 'h2', 1000, 100, 5, lambd)


pool.execute_parallel(8)