class Solution {
    public boolean checkDivisibility(int n) {
        int sumOfDig = 0;
        int prodOfDig = 1;
        int x = n;
        while(n>0){
            int digit = n%10;
            n /= 10;

            sumOfDig += digit;
            prodOfDig *= digit;
        }
        if(x % (sumOfDig + prodOfDig) == 0){
            return true;
        }
        return false;
    }
}