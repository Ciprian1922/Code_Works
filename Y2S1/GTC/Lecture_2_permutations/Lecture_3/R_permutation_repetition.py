def r_permutation_with_repetition_rank_k(A, r, k):
    n = len(A)

    # Compute the factorials
    factorial = [1]
    for i in range(1, n + r):
        factorial.append(factorial[-1] * i)

    result = []

    # Compute the r-permutation with repetition
    for i in range(r, 0, -1):
        idx = (k % factorial[n + i - 1]) // factorial[n + i - r - 1]
        result.append(A[idx])
        k %= factorial[n + i - r - 1]

    return result


# Example usage:
A = [1, 2, 3]
r = 2
k = 6

result = r_permutation_with_repetition_rank_k(A, r, k - 1)  # Subtract 1 from k since indexing starts from 0
print(f'The {r}-permutation with repetition at rank {k} is: {result}')
