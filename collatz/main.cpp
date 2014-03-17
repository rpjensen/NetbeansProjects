#include <iostream>
using namespace std;

int main(int argc, char** argv) {
  unsigned short n, x, r, t ;

  cout << "Enter your number" << endl ;
  cin >> n ;
    
  x = n ;
  r = 0 ;
   
   
  entrypnt:
  t = x>>1;
   
  if ((x&1)==0) goto elsepnt ;
  x = x + (t) + 1;
  r = r +2;
   
  goto looppnt ; 
  elsepnt:
  x = t ;
    
  r = r + 1 ;
   
   
  looppnt:
    if (x!=1) goto entrypnt ; 

  cout << r << " iterations required for " << n << endl ;

  return 0;
}
