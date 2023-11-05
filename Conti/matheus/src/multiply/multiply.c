#include <multiply/multiply.h>

int multiply(int a, int b)
{
    return a*b;
}

float multiply2(float a, float b)
{
	BAI_s_GetResetStatus();
    if (a > b)
    {
        return a + b;
    }
    else
    {
        return a * b;
    }
   
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