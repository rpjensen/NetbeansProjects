/* 
 * File:   main.c
 * Author: brock
 *
 * Created on August 20, 2013, 5:28 PM
 */

#include <stdio.h>
#include <stdlib.h>
// Added late in lab
#include <string.h>
#include <math.h>

/*
 * Transform the raw exponent into a floating point number.
 * That is: computes 2 raised to (expBits-127) power.
 * Three ways to do this:
 *   1)  call powf
 *   2)  use iteration and multiply by 2.0 or 0.5
 *   3)  move expBits into a properly formatted IEEE754 number.
 */
float expbits2real(unsigned int expBits) {
    float exponent = powf(2.0f, (float)((int)expBits-127)) ;
    
    return exponent ;
}

void printIEEE754(float f) {
    unsigned int rawBits, signBits, expBits, fracBits ;
    int sign ;
    float exponent, fraction ;
    
    /* Magical use of C pointers. Secret to be revealed later in course*/
    memcpy((void *)&rawBits, (void *)&f, sizeof f) ;
    
    /* Use >> and & to set the bit fields here. */
    /* FIX THIS! */
    signBits = (rawBits>>31) & 0x1 ;
    expBits = (rawBits>>23)& 0xFF ;
    fracBits = rawBits & 0x7FFFFF ;
    printf("\n%12.3e -> %08X -> %d, %d, %d\n", 
            f, rawBits, signBits, expBits, fracBits);
    
    /* Use arithmetic to set the "real" value of these fields */
    /* FIX THIS! */
    sign = signBits == 0 ? +1.0 : -1.0;
    fraction = 1.0 + fracBits / (float)(1<<23) ;
    exponent = expbits2real(expBits) ;
    /* To make this really correct, you must test the expBits
     * and have special cases when expBits are 255 or 0.*/
    
    printf("%d * %g * %g =~ %g\n", 
             sign, exponent, fraction, sign*exponent*fraction) ; 
}


int main(int argc, char** argv) {
    puts("Looking at floating point numbers") ;
    printIEEE754(5.0) ;
    printIEEE754(2013.0) ;
    printIEEE754(7.5) ;
    printIEEE754(0.01) ;
    printIEEE754(-1.0) ;
    printIEEE754(2e+38) ;
    printIEEE754(2e-38) ;
    printIEEE754(0.0) ;                   /* special handling */
    printIEEE754(-0.0) ;                  /* needed with expBits */
    printIEEE754(2e-40) ;                 /* are zero */
    printIEEE754(1.0/0.0) ;               /* infinity */
    printIEEE754(-1.0/0.0) ;              /* - infinity */
    printIEEE754(1.0/0.0 + -1.0/0.0) ;    /* NaN -- not a number */
    return (EXIT_SUCCESS);
}

