import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(42)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(10)]


pool.add_SA(f'max_iter', 200, 5, 50, 5, 1, 'G', 'h1', 7000, 1, 50, 0.1)



pool.execute_parallel(8)