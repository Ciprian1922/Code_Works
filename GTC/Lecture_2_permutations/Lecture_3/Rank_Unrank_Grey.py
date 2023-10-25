from itertools import combinations

# calculate the rank of a subset B based on Grey's code
def rank_grey(B, A):
    rank = 0

    # Iterate through the elements of B
    for i in range(len(B)):
        # Calculate the number of combinations of A with fewer elements than B
        count_combinations = len(list(combinations(A, i)))
        # Add the count to the rank
        rank += count_combinations
    return rank

# enumerate a subset based on its rank r
def unrank_grey(A, r):
    B = []  # Initialize an empty subset
    n = len(A)

    # Iterate through the elements of A
    for i in range(n):
        # Calculate the number of combinations of A with fewer elements than A[i]
        count_combinations = len(list(combinations(A, i)))

        # If r is greater than or equal to the count, add the element to the subset
        if r >= count_combinations:
            B.append(A[i])
            r -= count_combinations

    return B

# Example usage:
A = ['a', 'b', 'c', 'd']
B = ['a', 'c']

# Calculate the rank of subset B
r = rank_grey(B, A)
print(f"Rank of B: {r}")

# Enumerate the subset for a given rank r
subset = unrank_grey(A, r)
print(f"Subset for rank {r}: {subset}")
