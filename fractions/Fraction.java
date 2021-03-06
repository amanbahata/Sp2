package fractions;

import java.math.*;

/**
 * Immutable fractions of whole numbers, also known as rational numbers,
 * with their usual arithmetic operations.
 * 
 * Similar to java.lang.BigInteger, the documentation uses pseudo-code
 * throughout the descriptions of Fraction methods. The pseudo-code
 * expression (i + j) is shorthand for "a Fraction whose value is that
 * of the Fraction plus that of the Fraction j". Other pseudo-code
 * expressions are interpreted similarly. 
 *
 * @see java.math.BigInteger
 *
 * @author Aman Enghida
 */
public class Fraction {

    /* Insert your instance variables here. */
    private BigInteger numerator;
    private BigInteger denominator;



    
    /**
     * Constructs a Fraction taking the value of its parameter.
     *
     * @param val  non-null; the value the Fraction is supposed to take
     */
    public Fraction(BigInteger val) {
        this(val, BigInteger.ONE);
    }

    /**
     * Constructs a new Fraction taking the value of its parameter.
     *
     * @param val  the value the Fraction is supposed to take
     */
    public Fraction(long val) { this(BigInteger.valueOf(val),BigInteger.ONE);}

    /**
     * Constructs a Fraction corresponding to numerator / denominator.
     * The value is 0 if denominator is 0.
     *
     * @param numerator  non-null; value of the numerator
     * @param denominator  non-null; value of the denominator
     */
    public Fraction(BigInteger numerator, BigInteger denominator) {
        if(denominator.signum() < 0){             // move the negative sign to the denominator is negative
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        if (numerator.compareTo(BigInteger.ZERO) == 0 || denominator.compareTo(BigInteger.ZERO) == 0){
            //for the purposes of this coursework a/0 or 0/b will result in 0
            this.numerator = BigInteger.ZERO;
            this.denominator = BigInteger.ZERO;
        }else{
            // simplify fraction before storing
            this.numerator = numerator.divide(getGcd(numerator,denominator));
            this.denominator = denominator.divide(getGcd(numerator,denominator));
        }

    }

    /**
     * Constructs a Fraction corresponding to numerator / denominator.
     * The value is 0 if denominator is 0.
     *
     * @param numerator  the numerator of the Fraction
     * @param denominator  the denominator of the Fraction
     */
    public Fraction(long numerator, long denominator) { this(BigInteger.valueOf(numerator),BigInteger.valueOf(denominator));}

    /**
     * Returns a Fraction whose value is (this + val).
     * (Note that a/b + c/d = (a*d + b*c)/(b*d).)
     * 
     * @param val  non-null; to be added to this
     * @return this + val
     */
    public Fraction add(Fraction val) {
        BigInteger num1 = numerator.multiply(val.denominator);
        BigInteger num2 = denominator.multiply(val.numerator);
        BigInteger num = num1.add(num2);
        BigInteger denom = denominator.multiply(val.denominator) ;
        Fraction sum = new Fraction(num,denom);
        return sum ;
    }

    /**
     * Returns the sum of all elements of vals.
     *
     * @param fractions  array of Fractions to be summed up; may be or contain null
     * @return null if vals is or contains null; the sum of all elements of
     *  vals otherwise
     */
    public static Fraction sumAll(Fraction[] fractions) {
        //check the first element of fractions[] is not null
        if (fractions[0] == null) {
            return null;
        //check fractions[] contains at least one element
        }else if (fractions.length == 0){
            return new Fraction(0,0);
        }
        Fraction sumOfAllFractions = new Fraction(fractions[0].numerator,fractions[0].denominator);
        for (int i = 1; i < fractions.length; i++) {
            if (fractions[i] == null) {return null;}
            else {sumOfAllFractions = sumOfAllFractions.add(fractions[i]);}
        }
        return sumOfAllFractions;
    }

    /**
     * Returns a Fraction whose value is (this - val).
     *
     * @param val  non-null; to be subtracted from this Fraction
     * @return this - val
     */
    public Fraction subtract(Fraction val) {
        val = val.abs();
        return this.add(val.negate());
    }

    /**
     * Returns a Fraction whose value is (this * val).
     * (Note that a/b * c/d = (a*c)/(b*d).)
     *
     * @param val  non-null; to be multiplied to this Fraction
     * @return this * val
     */
    public Fraction multiply(Fraction val) {
        BigInteger num = numerator.multiply(val.numerator);
        BigInteger denom = denominator.multiply(val.denominator) ;
        return new Fraction(num,denom);
    }

    /**
     * Returns a Fraction whose value is (this / val).  
     * 
     * @param val  non-null; value by which this Fraction is to be divided
     * @return this / val
     */
    public Fraction divide(Fraction val) {
        val = val.invert();
        return multiply(val);
    }

    /**
     * Returns a Fraction whose value is (-this).
     *
     * @return -this
     */
    public Fraction negate() {
        return new Fraction(numerator.negate(), denominator);
    }

    /**
     * Returns the inverse of this Fraction, i.e., the Fraction 1 / this.
     * 
     * @return 1 / this
     */
    public Fraction invert() {
        return new Fraction(denominator,numerator);
    }

    /**
     * Returns the sign of this Fraction: 1 if its value is positive,
     * 0 if it is zero, -1 if it is negative.
     *
     * @return the sign of this Fraction (1 if its value is positive,
     *  0 if it is zero, -1 if it is negative) 
     */
    public int signum() {
        return this.numerator.signum();
    }

    /**
     * Returns the absolute value of this Fraction, i.e.,
     * the value of the Fraction itself if it is non-negative,
     * otherwise the negated value.
     * 
     * @return the absolute value of this Fraction
     */
    public Fraction abs() {
        return new Fraction(numerator.abs(),denominator);
    }

    /**
     * Returns the maximum of this Fraction and val.
     *
     * @param val  non-null; the value with which the maximum is to be computed
     * @return the maximum of this Fraction and val
     */
    public Fraction max(Fraction val) {
       if (numerator.multiply(val.denominator).compareTo(val.numerator.multiply(denominator)) == 1){
           return this;
       }
       else if (numerator.multiply(val.denominator).compareTo(val.numerator.multiply(denominator)) == -1){
           return val;
       }
       return val;
    }



    /**
     * Returns the minimum of this Fraction and val.
     *
     * @param val  non-null; the value with which the minimum is to be computed
     * @return the minimum of this Fraction and val
     */
    public Fraction min(Fraction val) {
        if (this.max(val).equals(val)){return this;}
        return val;
    }

    /**
     * Returns this Fraction taken to the power of exponent. Here
     * exponent may also be zero or negative. Note that a^0 = 1 and
     * a^b = (1/a)^(-b) if b < 0. 
     * 
     * @param exponent  the exponent to which we want to take this
     * @return this Fraction taken to the power of exponent
     */
    public Fraction pow(int exponent) {
        if (exponent == 0) return new Fraction(BigInteger.valueOf(1));
        else if (exponent < 0)  return new Fraction(denominator.pow(-exponent),numerator.pow(-exponent));

        return new Fraction(numerator.pow(exponent), denominator.pow(exponent));
    }

    /**
     * Compares this Fraction with the specified Fraction.
     * 
     * @param val  non-null; value with which this Fraction is to be compared 
     * @return -1, 0 or 1 as this Fraction is numerically less than,
     *         equal to, or greater than val
     * @see java.math.BigInteger#compareTo(BigInteger)
     */
    public int compareTo(Fraction val) {
        if ((numerator.multiply(val.denominator)).compareTo(denominator.multiply(val.numerator)) == 0){
            return 0;
        }
        if (numerator.multiply(val.denominator).compareTo(denominator.multiply(val.numerator)) > 0){
            return 1;
        }
        return -1;
    }

    /**
     * Checks if this Fraction and val represent equal values.
     *
     * @param val  potentially null (in this case the method returns false);
     *  the value with which this Fraction is to be compared for equality
     * @return true if this Fraction and other represent the same value;
     *  false otherwise
     */
    public boolean isEqualTo(Fraction val) {
        if (val == null) {
            return false;
        }
        if (this.numerator.equals(val.numerator) && this.denominator.equals(val.denominator)){
            return true;
        }
        return false;
    }

    /**
     * Returns a normalised String representation of this Fraction.
     * For example, new Fraction(5,3) and new Fraction(-10,-6) will
     * be represented as "(5 / 3)". The String representation of
     * new Fraction(5,-10) and new Fraction(-12,24) is "(-1 / 2)".
     *
     * In case this Fraction has an integer value, just the String
     * representation of the integer value is returned. For example,
     * new Fraction(-2) has the String representation "-2"; and
     * new Fraction(0), new Fraction(0,3), and new Fraction(4,0)
     * all have the String representation "0".
     *
     * @return a normalised String representation of this Fraction
     */
    public String toString() {
        if(numerator.equals(BigInteger.valueOf(0))){
            return "0";
        }else if (denominator.equals(BigInteger.valueOf(1))){
            return numerator.toString();
        }
        return numerator.toString() + " / " + denominator.toString();
    }


    /**
     * Finds the greatest common divisor of the fraction
     *  @param numerator numerator of the fraction
     *  @param denominator denominator of the fraction
     *  @return a gcd of the fraction
     */
    public BigInteger getGcd(BigInteger numerator, BigInteger denominator){
        BigInteger gcd = numerator.gcd(denominator);

        return gcd;
    }
}
