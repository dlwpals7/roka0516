import java.util.Scanner;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        // 1st Step
        Scanner scanner = new Scanner(System.in);
        // [pq] > 1
        BigInteger p = new BigInteger(scanner.next());
        BigInteger q = new BigInteger(scanner.next());

        // [pq] == prime_number
        if (!p.isProbablePrime(10) || !q.isProbablePrime(10)) {
            System.out.println("Composite Number Detected!");
            return;
        }
        
        // N = p * q
        BigInteger N = p.multiply(q);
        // phi(N) = (p - 1) * (q - 1)
        BigInteger phiN = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1))); 
        BigInteger e = new BigInteger("65537");
        BigInteger d = e.modInverse(phiN);
        
        System.out.println("p: " + p + "\nq: " + q + "\nN: " + N + "\nphiN: " + phiN + "\ne: " + e + "\nd: " + d);

        // 2nd Step
        BigInteger M = new BigInteger(scanner.next());
        if(M.compareTo(N) >= 1) {
            System.out.println("Message Out Of Bounds!");
            return;
        }
        
        // C = M^e (mod N) 
        BigInteger C = M.modPow(e, N);
        System.out.println("M: " + M + "\nC: " + C);

        // 3rd Step
        // M = C^d (mod N)
        BigInteger R = C.modPow(d, N);
        System.out.println("R: " + R);
    }
}