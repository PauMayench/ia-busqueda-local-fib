import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(42)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):
pool.add_SA('sa_output', 200, 5, 50, 5, 1, 'G', 'h1', 10000, 10, 100, 0.95)



pool.execute_parallel(8)