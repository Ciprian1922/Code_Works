def subset_to_bitstring(B, A):
    n = len(A)
    bit_string = [0] * n

    for i in range(n):
        if A[i] in B:
            bit_string[n - i - 1] = 1

    return bit_string

# Example usage:
A = ['1', '2', '3', '4']
B = ['1','2']
bit_string = subset_to_bitstring(B, A)
print(bit_string)
