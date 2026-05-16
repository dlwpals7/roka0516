import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1st Step
        Scanner scanner = new Scanner(System.in);
        // [pq] > 1
        long p = scanner.nextLong();
        long q = scanner.nextLong();
       
        long N = p * q, phiN = (p - 1) * (q - 1), e = 65537, d = 1;
        
        for(; (e * d) % phiN != 1; d++) {} 
        System.out.println("p: " + p + "\nq: " + q + "\nN: " + N + "\nphiN: " + phiN + "\ne: " + e + "\nd: " + d);

        // 2nd Step
        long M = scanner.nextLong(), C = M % N;
        for(long i = 0; i < e - 1; i++) {
            C = (C * M) % N;
        }
        System.out.println("M: " + M + "\nC: " + C);

        // 3rd Step
        long R = C % N;
        for(long i = 0; i < d - 1; i++) {
            R = (R * C) % N;
        }
        System.out.println("R: " + R);
    }
}