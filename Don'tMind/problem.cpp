#include <iostream>
#include <fstream>

using namespace std;

int main() {
    // Open the input file
    ifstream fin("in.txt");
    ofstream fout("out.txt");

    int a[50];
    int n, i = 0, size = 0, poz;

    // Read numbers from the file and store them in the array
    while (fin >> n) {
        a[i++] = n;
        size++;
    }

    // Find the position of the first element divisible by 3
    poz = -1;
    for (i = 0; i < size; ++i) {
        if (a[i] % 3 == 0) {
            poz = i;
            //break; ///or first element
        }
    }

    // If an element divisible by 3 is found, remove it from the array
    if (poz != -1) {
        for (i = poz; i < size - 1; ++i) {
            a[i] = a[i + 1];
        }
        size--;
    }

    // Write the modified array to the output file
    for (i = 0; i < size; ++i) {
        fout << a[i] << " ";
    }

    fin.close();
    fout.close();

    return 0; // Return 0 for successful execution
}
