import java.lang.Math;
import java.util.Arrays;

public class polynomial {

    private float[] coefficients;

    public polynomial(float[] coefficients){
        this.coefficients = coefficients;
    }

    public int getdegree(){
        return this.coefficients.length - 1;
    }

    public float evaluate_value(float value){
        float result = 0;
        for(int i=0;i<coefficients.length;i++){
            result += coefficients[i] * Math.pow(value, i);
        }
        return result;
    }

    public polynomial sumOfPolynomials(polynomial poly){
        int m = Math.max(getdegree(),poly.getdegree());
        float[] sum = new float[m+1];
        for(int i=0;i<m+1;i++){
            if ( i > getdegree() ) {
                sum[i] = poly.coefficients[i];
            } else if (i > poly.getdegree()) {
                sum[i] = this.coefficients[i];
            } else {
                sum[i] = this.coefficients[i] + poly.coefficients[i];
            }
        }
        polynomial resultofsum = new polynomial(sum);
        return resultofsum;
    }

    public polynomial derivativeOfPolynomial(){
        float[] der = new float[getdegree()];
        for(int i=1;i<coefficients.length;i++){
            der[i-1] = i*coefficients[i];
        }
        polynomial resultofder = new polynomial(der);
        return resultofder;
    }

    public polynomial productOfPolynomials(polynomial poly){
        float[] product = new float[getdegree() + poly.getdegree() + 1];
        for(int i=0;i<getdegree()+1;i++){
            for(int j=0;j<poly.getdegree()+1;j++){
                product[j+i] += coefficients[i]*poly.coefficients[j];
            }
        }
        polynomial resultofproduct = new polynomial(product);
        return resultofproduct;
    }

    public static void main(String[] args){
        float[] c1={2.5f,1f,3f,4f,8.2f},c2={1f,2f,2f};
        polynomial p1 = new polynomial(c1);
        polynomial p2 = new polynomial(c2);
        System.out.println("Evaluation of polynomial in a given point: " + p1.evaluate_value(2));
        System.out.println("Sum of 2 different size polynomial coefficients: " + Arrays.toString(p1.sumOfPolynomials(p2).coefficients));
        System.out.println("Coefficients of derivative of given polynomial: " + Arrays.toString(p1.derivativeOfPolynomial().coefficients));
        System.out.println("Product of 2 different size polynomials: " + Arrays.toString(p1.productOfPolynomials(p2).coefficients));
    }
}
