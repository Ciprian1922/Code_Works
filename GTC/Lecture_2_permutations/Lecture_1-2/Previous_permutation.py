def previous_permutation(arr):
    prev_perm = list(arr)
    if not next((i for i in range(len(arr) - 2, -1, -1) if prev_perm[i] > prev_perm[i + 1]), None):
        return None

    i = next((i for i in range(len(arr) - 2, -1, -1) if prev_perm[i] > prev_perm[i + 1]))
    j = next((j for j in range(len(arr) - 1, i, -1) if prev_perm[j] < prev_perm[i]))

    prev_perm[i], prev_perm[j] = prev_perm[j], prev_perm[i]
    prev_perm[i+1:] = reversed(prev_perm[i+1:])
    return prev_perm

# Input: Read a permutation as a list of integers
permutation = list(map(int, input("Enter a permutation: ").split()))

previous_permutation_result = previous_permutation(permutation)

if previous_permutation_result is not None:
    print("Previous permutation in lexicographic order:", previous_permutation_result)
else:
    print("No previous permutation exists (first permutation in lexicographic order).")
