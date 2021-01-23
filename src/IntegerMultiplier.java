import java.math.BigDecimal;
import java.math.BigInteger;

//Course: Divide and Conquer, Sorting and Searching, and Randomized Algorithms
//Assignment: Week 1 Big Integer Multiplication
//@author: Zitao He

public class IntegerMultiplier {

    public BigInteger calculateProduct(BigInteger num1, BigInteger num2){
        if(num1.compareTo(BigInteger.valueOf(10)) < 0 || num2.compareTo(BigInteger.valueOf(10)) < 0){
            return num1.multiply(num2);
        }

        BigInteger product;

        int numDigits1 = String.valueOf(num1).length();
        int numDigits2 = String.valueOf(num2).length();

        BigInteger a = divideInt(num1)[0];
        BigInteger b = divideInt(num1)[1];
        BigInteger c = divideInt(num2)[0];
        BigInteger d = divideInt(num2)[1];

        int halfNumDigits1 = (int) Math.ceil((double) numDigits1 / (double) 2);
        int halfNumDigits2 = (int) Math.ceil((double) numDigits2 / (double) 2);

        BigInteger term1 = calculateProduct(a, c).multiply(
                BigDecimal.valueOf(Math.pow(10, (numDigits1 - halfNumDigits1) + (numDigits2 - halfNumDigits2))).toBigInteger());

        BigInteger term2 = calculateProduct(a, d).multiply(
                BigDecimal.valueOf(Math.pow(10, numDigits1 - halfNumDigits1)).toBigInteger());

        BigInteger term3 = calculateProduct(b, c).multiply(

                BigDecimal.valueOf(Math.pow(10, numDigits2 - halfNumDigits2)).toBigInteger());

        BigInteger term4 = calculateProduct(b ,d);

        product = term1.add(term2.add(term3.add(term4)));
        return product;
    }

    public BigInteger[] divideInt(BigInteger num){

        BigInteger[] divided = new BigInteger[2];
        int numDigits = String.valueOf(num).length();
        int halfIndex = (int) Math.ceil((double) numDigits / (double) 2);
        divided[0] = new BigInteger(num.toString().substring(0, halfIndex));
        divided[1] = new BigInteger(num.toString().substring(halfIndex));
        return divided;
    }

    public static void main(String[] args){
        IntegerMultiplier tester = new IntegerMultiplier();
        BigInteger num1 = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
        BigInteger num2 = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");
        System.out.println(tester.calculateProduct(num1, num2));

    }
}
