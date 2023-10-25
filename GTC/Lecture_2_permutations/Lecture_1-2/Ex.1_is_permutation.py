def is_permutation(arr):
    n = len(arr)

    # Check if the length of the sequence is less than 1 or greater than n
    if n < 1 or n != len(set(arr)):
        return "not a permutation"

    # Check if the sequence contains all values from 1 to n
    for i in range(1, n + 1):
        if i not in arr:
            return "not a permutation"

    return "a permutation"

# Input: Read a sequence of numbers separated by spaces
sequence = input("Enter a sequence of numbers: ").split()

# Convert input to integers
sequence = [int(num) for num in sequence]

result = is_permutation(sequence)
print(f"The sequence is {result}.")
