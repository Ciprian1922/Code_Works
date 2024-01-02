#include <iostream>
#include <fstream>

using namespace std;

bool isPrime(int number) {
    /// 0 and 1 are not prime numbers
    if (number <= 1) {
        return false;
    }

    /// Check for divisibility from 2 to the square root of the number
    for (int i = 2; i * i <= number; ++i) {
        if (number % i == 0) {
            return false; /// The number is divisible, so it's not prime
        }
    }

    return true; /// The number is prime
}

int main() {
    // Open the input file
    ifstream inputFile("in.txt");
    ofstream outputFile("out.txt");

    // Read numbers from the file and print the even ones
    int number;
    while (inputFile >> number) { ///fin >> n
        if (number % 2 != 0) {
            //cout << number << " is odd." << endl;
            outputFile << number << " is even." <<endl;
        }

        if (isPrime(number) == true){
            //cout << number << " is also prime." << endl;
            outputFile << number << " is also prime." <<endl;
        }
    }
    //for(int a = 0; a<100 ; a++)
    //    outputFile << a<< " ";
    // Close the file
    inputFile.close();
    outputFile.close();
    return 0; // Return 0 for successful execution
}
