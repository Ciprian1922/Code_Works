def bitstring_to_subset(bit_string, A):
    n = len(A)
    B = set()

    for i in range(n):
        if bit_string[i] == 1:
            B.add(A[n - i - 1])

    return B

# Example usage:
A = ['a1', 'a2', 'a3', 'a4', 'a5']
bit_string = [1, 0, 1, 0, 1]
subset = bitstring_to_subset(bit_string, A)
print(subset)
