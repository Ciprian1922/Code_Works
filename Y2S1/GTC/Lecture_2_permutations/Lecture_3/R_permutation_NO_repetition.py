def r_permutation_without_repetition_rank_k(A, r, k):
    n = len(A)

    # Compute the factorial
    factorial = [1]
    for i in range(1, n + 1):
        factorial.append(factorial[-1] * i)

    result = []

    # Create a list of available elements
    available_elements = A[:]

    # Compute the r-permutation without repetition
    for i in range(r, 0, -1):
        idx = k // factorial[i - 1]
        result.append(available_elements.pop(idx))
        k %= factorial[i - 1]

    return result


# Example usage:
A = [1, 2, 3]
r = 2
k = 2

result = r_permutation_without_repetition_rank_k(A, r, k - 1)  # Subtract 1 from k since indexing starts from 0
print(f'The {r}-permutation without repetition at rank {k} is: {result}')
