import os

def get_lines_from_file(file_name):

    with open(file_name, 'r') as file:
        return [line.strip() for line in file.readlines()]



def get_list_lines_from_folder(folder_name):

    all_lines = []
    for filename in os.listdir(folder_name):
        file_path = os.path.join(folder_name, filename)

        if os.path.isfile(file_path):
            lines = get_lines_from_file(file_path)  
            all_lines.append(lines) 

    return all_lines

def convert_to_ints(list_strings):
    return [int(elem) for elem in list_strings if elem.isdigit()]


def get_last_numbers_and_median_from_folder(folder_name):

    index_element = -1
    list_lines = get_list_lines_from_folder(folder_name)
    last_elements = []
    for line in list_lines:
        last_elements.append(line[index_element])

    numbers = convert_to_ints(last_elements)
    return numbers, sum(numbers)/len(numbers)

