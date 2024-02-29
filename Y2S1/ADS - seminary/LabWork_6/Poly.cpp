#include <iostream>

using namespace std;

class Poly {
public:
    double c; //coefficient
    Poly* next; //next node in dense list representation

    Poly(double c, Poly* next) : c(c), next(next) {}

    //evaluate the polynomial at a given value c using Horner's rule
    static double eval(double c, Poly* p) {
        double result = 0;
        while (p != nullptr) {
            result = result * c + p->c;
            p = p->next;
        }
        return result;
    }

    //adding up two polynomials
    static Poly* add(Poly* p, Poly* q) {
        Poly* result = nullptr;
        Poly* current = nullptr;
        while (p != nullptr || q != nullptr) {
            double coefP = (p != nullptr) ? p->c : 0;
            double coefQ = (q != nullptr) ? q->c : 0;
            double sum = coefP + coefQ;

            if (result == nullptr) {
                result = new Poly(sum, nullptr);
                current = result;
            } else {
                current->next = new Poly(sum, nullptr);
                current = current->next;
            }

            if (p != nullptr) p = p->next;
            if (q != nullptr) q = q->next;
        }
        return result;
    }

    //multiplying a polynomial by x
    static Poly* xmult(Poly* p) {
        Poly* result = nullptr;
        Poly* current = nullptr;
        while (p != nullptr) {
            if (result == nullptr) {
                result = new Poly(0, nullptr);
                current = result;
            } else {
                current->next = new Poly(0, nullptr);
                current = current->next;
            }
            current->next = new Poly(p->c, nullptr);
            current = current->next;
            p = p->next;
        }
        return result;
    }

    //multiplying a polynomial by a constant c
    static Poly* cmult(double c, Poly* p) {
        Poly* result = nullptr;
        Poly* current = nullptr;
        while (p != nullptr) {
            if (result == nullptr) {
                result = new Poly(p->c * c, nullptr);
                current = result;
            } else {
                current->next = new Poly(p->c * c, nullptr);
                current = current->next;
            }
            p = p->next;
        }
        return result;
    }

    //multiply two polynomials
    static Poly* mult(Poly* p, Poly* q) {
        Poly* result = nullptr;
        while (p != nullptr) {
            double coefP = p->c;
            Poly* term = q;
            Poly* temp = nullptr;
            while (term != nullptr) {
                if (temp == nullptr) {
                    temp = new Poly(coefP * term->c, nullptr);
                    result = add(result, temp);
                } else {
                    temp->next = new Poly(coefP * term->c, nullptr);
                    result = add(result, temp);
                    temp = temp->next;
                }
                term = term->next;
            }
            p = p->next;
        }
        return result;
    }

    //converting polynomial to string representation
    static string toString(Poly* p) {
        string result;
        while (p != nullptr) {
            if (!result.empty()) {
                result += (p->c >= 0) ? "+" : "-";
            }
            if (abs(p->c) != 1 || p->next == nullptr) {
                result += to_string(abs(p->c));
            }
            if (p->next != nullptr) {
                result += "*x^" + to_string(p->next->c);
            }
            p = p->next;
        }
        return result;
    }
};

int main() {
    //usecase
    Poly* p = new Poly(9, new Poly(-1, new Poly(2, new Poly(1, nullptr))));
    Poly* q = new Poly(2, new Poly(1, new Poly(-3, nullptr)));

    cout << "Polynomial p(x): " << Poly::toString(p) <<endl;
    cout << "Polynomial q(x): " << Poly::toString(q) <<endl;

    Poly* sum = Poly::add(p, q);
    cout << "Sum of p and q: " << Poly::toString(sum) <<endl;

    Poly* product = Poly::mult(p, q);
    cout << "Product of p and q: " << Poly::toString(product) <<endl;

    Poly* xMultP = Poly::xmult(p);
    cout << "Product of x and p: " << Poly::toString(xMultP) <<endl;

    Poly* cMultP = Poly::cmult(3, p);
    cout << "Product of 3 and p: " << Poly::toString(cMultP) <<endl;

    double evalResult = Poly::eval(2, p);
    cout << "Evaluation of p(2): " << evalResult <<endl;

    //clean up the dynamically allocated memory
    delete p;
    delete q;
    delete sum;
    delete product;
    delete xMultP;
    delete cMultP;

    return 0;
}
