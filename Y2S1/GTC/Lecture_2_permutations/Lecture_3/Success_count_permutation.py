from math import factorial
from collections import Counter

def count_permutations_with_calculation(s):
    n = len(s)
    
    # Calculate the factorials
    total_factorial = factorial(n)
    
    # Calculate the denominator
    denominator = 1
    denominator_str = ''
    
    for count in sorted(Counter(s).values(), reverse=True):
        denominator *= factorial(count)
        denominator_str += f'{count}!'

    # Display the calculation
    calculation_str = f'{n}!\n{"-" * len(str(n))}\n{denominator_str}'

    # Calculate the result
    result = total_factorial // denominator

    # Display the final equation
    final_equation = f'{n}! / {denominator}'

    return result, calculation_str, final_equation

# Example usage:
input_string = "SUCCESS"
result, calculation, final_equation = count_permutations_with_calculation(input_string)
print(f'Calculation:\n{calculation}\nFinal Equation: {final_equation} = {result}')
