k = 0

def next_permutation(p):
    n = len(p)
    i = n - 2

    # Find the first index i where p[i] < p[i+1]
    while i >= 0 and p[i] > p[i + 1]:
        i -= 1

    # If no such index exists, p is the last permutation
    if i == -1:
        return None  # No next permutation

    j = n - 1

    # Find the largest index j such that p[j] > p[i]
    while p[j] < p[i]:
        j -= 1

    # Swap p[i] with p[j]
    p[i], p[j] = p[j], p[i]

    # Reverse the elements from p[i+1] to p[n-1]
    k = 0
    while i + 1 + k < n - 1 - k:
        p[i + 1 + k], p[n - 1 - k] = p[n - 1 - k], p[i + 1 + k]
        k += 1

    return p

def all_permutations(arr):
    while arr is not None:
        yield arr
        arr = next_permutation(arr)

# Example usage:
initial_permutation = [1, 2, 3]

for perm in all_permutations(initial_permutation):
    print(perm)
    k += 1

print("Number of permutations performed is:", k)
