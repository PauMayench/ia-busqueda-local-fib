import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(48)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(2)]
seed = seedsProblema[0]

n_usuarisList = [100, 200, 300, 400 ,500, 600, 700, 800, 900]
for i,n_usuaris in enumerate(n_usuarisList):
        for r in seedsProblema:
            pool.add_HC(f'HC-{i}', n_usuaris, 5, 50, 5, r, 'G', 'h1')



pool.execute_parallel(1)
