import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(48)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(10)] 

rep_files = [5, 10, 15, 20, 25]

for i, rep in enumerate(rep_files):
    if i == 4:
        for j, seed in enumerate(seedsProblema):
            pool.add_HC(f'HC-exp7-h1-{i}-{j}', 200, 5, 50, rep, seed, 'G', 'h1')


pool.execute_parallel(1)
