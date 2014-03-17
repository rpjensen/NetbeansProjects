/* 
 * File:   main.c
 * Author: jensenrp
 *
 * Created on August 22, 2013, 7:00 PM
 */

#include <stdio.h>
#include <stdlib.h>

/*
 * 
 */
int main(int argc, char** argv) {
    int i;
    for(i = 0; i<10; ++i){
           int a = random();
           int b = random();
           printf("%08X %08X\n", a, b);
           printf("ADD(%08X, %08X) -> %08X\n", a, b, a+b);
           printf("AND(%08X,%08X) -> %08X\n", a, b, a&b);
           printf("OR(%08X,%08X) -> %08X\n", a, b, a|b);
           printf("NOT(%08X) -> %08X\n", a, ~a);
           putchar('\n');
           int sb = b&31;
           printf("SHL(%08X, %2d)  -> %08X\n", a, sb, a>>sb);
           printf("SHR(%08X, %2d)  -> %08X\n", a, sb, a<<sb);
           putchar('\n');
           printf("PKTLEN(%08X) -> %08X\n", a, a&0XFFFF);
           printf("HDRLEN(%08X) -> %08X\n", a, (a>>24)&0XF);
           putchar('\n');
       }
    }


