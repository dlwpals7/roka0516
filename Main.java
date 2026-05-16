import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1st Step
        Scanner primeNumber = new Scanner(System.in);
        // [pq] > 1
        long p = primeNumber.nextLong();
        long q = primeNumber.nextLong();
       
        long N = p * q, phiN = (p - 1) * (q - 1), e = 65537, d = 1;
        
        for(; (e * d) % phiN != 1; d++) {} 
        System.out.println("p: " + p + "\nq: " + q + "\nN: " + N + "\nphiN: " + phiN + "\ne: " + e + "\nd: " + d);
    }
}