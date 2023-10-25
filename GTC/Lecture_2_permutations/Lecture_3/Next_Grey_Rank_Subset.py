from itertools import combinations

from Rank_Unrank_Grey import rank_grey


def next_grey_rank_subset(A, B):
    n = len(A)
    r = rank_grey(B, A)  # Calculate the rank of subset B

    # Initialize the list to store the next subset
    next_subset = []

    for i in range(n):
        count_combinations = len(list(combinations(A, i)))

        # Check if adding the next element from A would not exceed the rank
        if r + count_combinations <= 2 ** n:
            next_subset.append(A[i])
            r += count_combinations
        else:
            break

    return next_subset

# Example usage:
A = ['a', 'b', 'c', 'd']
B = ['a', 'c']

# Calculate the subset that immediately follows B in the enumeration
next_subset = next_grey_rank_subset(A, B)
print(f"The subset immediately following B in Grey's code enumeration: {next_subset}")
