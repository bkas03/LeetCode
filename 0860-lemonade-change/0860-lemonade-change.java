class Solution {
    public boolean lemonadeChange(int[] bills) {
        int a = 0;
        int b = 0;

        for (int i = 0; i < bills.length; i++) {

            if (bills[i] == 5) {
                a++;
            }

            else if (bills[i] == 10) {
                if (a == 0) {
                    return false;
                }

                a--;
                b++;
            }

            else { 

                if (b > 0 && a > 0) {
                    b--;
                    a--;
                }

                else if (a >= 3) {
                    a -= 3;
                }

                else {
                    return false;
                }
            }
        }

        return true;
    }
}