def rank(p):
    n = len(p)
    if n == 1:
        return 0
    else:
        q = [0] * (n - 1)

        for i in range(1, n):
            if p[i] < p[0]:
                q[i - 1] = p[i]
            else:
                q[i - 1] = p[i] - 1

        factorial = 1
        for i in range(2, n):
            factorial *= i

        return rank(q) + (p[0] - 1) * factorial

# Example usage:
permutation = [1,3,2]
permutation_rank = rank(permutation)
print("Permutation:", permutation)
print("Rank:", permutation_rank)
