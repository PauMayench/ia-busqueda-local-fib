import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(48)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(90)]



for i, seed in enumerate(seedsProblema):
    pool.add_HC(f'HC-exp5-{i+10}', 200, 5, 50, 5, seed, 'G', 'h2', seed)


pool.execute_parallel(7)
