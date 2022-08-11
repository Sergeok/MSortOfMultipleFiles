package util;

public class Utility {

    public static int findNextPowerOf2(int n) {
        if (n != 1) {
            n--;
        }

        while ((n & n - 1) != 0) {
            n = n & n - 1;
        }

        return n << 1;
    }
}
