import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(48)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(20)] #90



for i, seed in enumerate(seedsProblema):
    pool.add_SA(f'HC-exp6-h1-{i+10}', 200, 5, 50, 5, seed, 'G', 'h1', 8000, 1, 50, 0.1)

for i, seed in enumerate(seedsProblema):
    pool.add_SA(f'HC-exp6-h2-{i+10}', 200, 5, 50, 5, seed, 'G', 'h2', 8000, 1, 50, 0.1)


pool.execute_parallel(7)
