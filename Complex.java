/** A class to represent Complex Numbers. A Complex object is
 * immutable once created; the add, subtract and multiply routines
 * return newly-created Complex objects containing the results.
 *
 * @author Ian F. Darwin, inspired by David Flanagan.
 * @version $Id: Complex.java,v 1.3 2004/05/13 22:28:59 ian Exp $
 */
public class Complex {
  /** The real part */
  private double r;
  /** The imaginary part */
  private double i;

  /** Construct a Complex */
  Complex(double rr, double ii) {
    r = rr;
    i = ii;
  }

  /** Display the current Complex as a String, for use in
   * println() and elsewhere.
   */
  public String toString() {
    StringBuffer sb = new StringBuffer().append(r);
    if (i>0)
      sb.append('+');  // else append(i) appends - sign
    return sb.append(i).append('i').toString();
  }

  /** Return just the Real part */
  public double getReal() {
    return r;
  }
  
  public void setReal(double r) {
	  this.r = r;
  }
  
  /** Return just the Real part */
  public double getImaginary() {
    return i;
  }
  
  public void setImaginary(double i) {
	  this.i = i;
  }
  /** Return the magnitude of a complex number */
  public double magnitude() {
    return Math.sqrt(r*r + i*i);
  }

  /** Add another Complex to this one
   */
  public Complex add(Complex other) {
    return add(this, other);
  }

  /** Add two Complexes
   */
  public static Complex add(Complex c1, Complex c2) {
    return new Complex(c1.r+c2.r, c1.i+c2.i);
  }

  /** Subtract another Complex from this one
   */
  public Complex subtract(Complex other) {
    return subtract(this, other);
  }

  /** Subtract two Complexes
   */
  public static Complex subtract(Complex c1, Complex c2) {
    return new Complex(c1.r-c2.r, c1.i-c2.i);
  }

  /** Multiply this Complex times another one
   */
  public Complex multiply(Complex other) {
    return multiply(this, other);
  }

  /** Multiply two Complexes
   */
  public static Complex multiply(Complex c1, Complex c2) {
    return new Complex(c1.r*c2.r - c1.i*c2.i, c1.r*c2.i + c1.i*c2.r);
  }

  /** Divide c1 by c2.
   * @author Gisbert Selke.
   */
  public static Complex divide(Complex c1, Complex c2) {
    return new Complex(
      (c1.r*c2.r+c1.i*c2.i)/(c2.r*c2.r+c2.i*c2.i),
      (c1.i*c2.r-c1.r*c2.i)/(c2.r*c2.r+c2.i*c2.i));
  }
  
  /* Compare this Complex number with another
   */
  public boolean equals(Object o) {
    if (!(o instanceof Complex))
      throw new IllegalArgumentException(
          "Complex.equals argument must be a Complex");
    Complex other = (Complex)o;
    return r == other.r && i == other.i;
  }
  
  /* Generate a hashCode; not sure how well distributed these are.
   */
  public int hashCode() {
    return (int)( r) |  (int)i;
  }

  public static void main(String[] args) {
    Complex c = new Complex(3,  5);
    Complex d = new Complex(2, -2);
    System.out.println(c);
    System.out.println(c + ".getReal() = " + c.getReal());
    System.out.println(c + " + " + d + " = " + c.add(d));
    System.out.println(c + " + " + d + " = " + Complex.add(c, d));
    System.out.println(c + " * " + d + " = " + c.multiply(d));
    System.out.println(Complex.divide(c, d));
  }
}