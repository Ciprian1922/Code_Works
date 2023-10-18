def compute_inverse(permutation):
    n = len(permutation)
    inverse = [0] * n

    for i in range(n):
        inverse[permutation[i] - 1] = i + 1

    return inverse

# Input: Read a permutation of n elements
n = int(input("Enter the number of elements in the permutation: "))
permutation = list(map(int, input(f"Enter a permutation of {n} elements: ").split()))

if len(permutation) != n or set(permutation) != set(range(1, n + 1)):
    print("Invalid input. Please ensure it's a valid permutation.")
else:
    inverse_permutation = compute_inverse(permutation)
    print("Inverse permutation:", inverse_permutation)
