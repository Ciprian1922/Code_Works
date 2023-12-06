def count_primes_up_to_n(n):
    # Initialize a boolean array "prime[0..n]" and initialize all entries as true.
    # A value in prime[i] will finally be false if i is Not a prime, else true.
    primes = [True] * (n + 1)
    primes[0] = primes[1] = False  # 0 and 1 are not prime numbers.

    # Mark multiples of each prime number starting from 2 as not prime.
    for i in range(2, int(n**0.5) + 1):
        if primes[i]:
            for j in range(i*i, n + 1, i):
                primes[j] = False

    # Count the number of primes.
    count_primes = sum(primes)

    return count_primes

# Example usage:
n = int(input("Enter a number (n): "))
result = count_primes_up_to_n(n)
print(f'There are {result} prime numbers between 1 and {n}.')
