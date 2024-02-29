#include <multiply/multiply.h>

int suma = 0;
int produs = 0;
int corect=E_OK;

int multiply(int a, int b)
{
    return a*b;
}

int multiply2(int a, int b)
{
	BAI_s_GetResetStatus();
    if (a > b)
    {
        suma = a + b;
    }
    else
    {
        produs = a * b;
    }
    if (suma > 1 && produs<5)
        corect = E_OK;
    else
        corect = E_NOT_OK;
   
}

double multiply3(double a, double b)
{
    if (a < b)
    {
        return a * b;
    }
    else
    {
        return a - b;
    }
    
}