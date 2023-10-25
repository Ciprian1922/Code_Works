from itertools import permutations
import math

def permutation_with_rank(n, r):
    if r < 0 or r >= math.factorial(n):
        return None

    # Generate all permutations of {1, 2, ..., n}
    perms = list(permutations(range(1, n + 1)))

    # Get the permutation at rank r
    result_permutation = perms[r]

    return result_permutation

# Input: Read values for n and r
n = int(input("Enter n: "))
r = int(input("Enter r (0 <= r < n!): "))

permutation = permutation_with_rank(n, r)

if permutation is not None:
    print(f"Permutation with rank {r}: {permutation}")
else:
    print("Invalid input. Please ensure 0 <= r < n!")
