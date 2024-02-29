def next_permutation(arr):
    next_perm = list(arr)
    if not next((i for i in range(len(arr)-2, -1, -1) if next_perm[i] < next_perm[i+1]), None):
        return None

    i = next((i for i in range(len(arr)-2, -1, -1) if next_perm[i] < next_perm[i+1]))
    j = next((j for j in range(len(arr)-1, i, -1) if next_perm[j] > next_perm[i]))

    next_perm[i], next_perm[j] = next_perm[j], next_perm[i]
    next_perm[i+1:] = reversed(next_perm[i+1:])
    return next_perm

# Input: Read a permutation as a list of integers
permutation = list(map(int, input("Enter a permutation: ").split()))

next_permutation_result = next_permutation(permutation)

if next_permutation_result is not None:
    print("Next permutation in lexicographic order:", next_permutation_result)
else:
    print("No next permutation exists (last permutation in lexicographic order).")
