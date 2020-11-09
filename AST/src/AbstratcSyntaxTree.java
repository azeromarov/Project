import java.util.HashMap;
import java.util.Map;

class AbstractSyntaxTree {
    public static void main(String[] args){
        // ((2^2 + a + sin(30)) + (12 * 2 + log(13 - (-2)) / 5))
        Expression root = new Sum(new Sum(new Sum(new Power(new Value(2.0f),new Value(2.0f)),new Variable("a",5.0f)), new Sin(new Value(30.0f))), new Sum(new Multiply(new Value(12.0f),new Value(2.0f)), new Divide(new Log(new Subtract(new Value(13.0f),new Value(-2.0f))), new Value(5.0f))));
        root.dump();
        System.out.print(" = " + root.eval());
    }
}

interface Expression{
    public float eval();
    public void dump();
}

class Sum implements Expression{
    final Expression first;
    final Expression second;
    Sum(Expression first, Expression second){
        this.first = first;
        this.second = second;
    }
    public float eval(){
        return this.first.eval() + this.second.eval();
    }
    public void dump(){
        System.out.print("( ");
        this.first.dump();
        System.out.print(" + ");
        this.second.dump();
        System.out.print(" )");
    }
}

class Subtract implements Expression{
    final Expression first;
    final Expression second;
    Subtract(Expression first, Expression second){
        this.first = first;
        this.second = second;
    }
    public float eval(){
        return this.first.eval() - this.second.eval();
    }
    public void dump(){
        System.out.print("( ");
        this.first.dump();
        System.out.print(" - ");
        this.second.dump();
        System.out.print(" )");
    }
}

class Multiply implements Expression{
    final Expression first;
    final Expression second;
    Multiply(Expression first, Expression second){
        this.first = first;
        this.second = second;
    }
    public float eval(){
        return this.first.eval() * this.second.eval();
    }
    public void dump(){
        System.out.print("( ");
        this.first.dump();
        System.out.print(" * ");
        this.second.dump();
        System.out.print(" )");
    }
}

class Divide implements Expression{
    final Expression first;
    final Expression second;
    Divide(Expression first, Expression second){
        this.first = first;
        this.second = second;
    }
    public float eval(){
        return this.first.eval() / this.second.eval();
    }
    public void dump(){
        System.out.print("( ");
        this.first.dump();
        System.out.print(" / ");
        this.second.dump();
        System.out.print(" )");
    }
}

class Power implements Expression{
    final Expression value;
    final Expression power;
    Power(Expression value, Expression power){
        this.value = value;
        this.power = power;
    }
    public float eval(){
        return (float) Math.pow(this.value.eval(), this.power.eval());
    }
    public void dump(){
        System.out.print("( ");
        this.value.dump();
        System.out.print(" ^ ");
        this.power.dump();
        System.out.print(" )");
    }
}

class Sin implements Expression{
    final Expression value;
    Sin(Expression value){
        this.value = value;
    }
    public float eval(){
        return (float) Math.sin(Math.toRadians(this.value.eval()));
    }
    public void dump(){
        System.out.print("sin( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Cos implements Expression{
    final Expression value;
    Cos(Expression value){
        this.value = value;
    }
    public float eval(){
        return (float) Math.cos(Math.toRadians(this.value.eval()));
    }
    public void dump(){
        System.out.print("cos( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Tan implements Expression{
    final Expression value;
    Tan(Expression value){
        this.value = value;
    }
    public float eval(){
        return (float) Math.tan(Math.toRadians(this.value.eval()));
    }
    public void dump(){
        System.out.print("tan( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Cot implements Expression{
    final Expression value;
    Cot(Expression value){
        this.value = value;
    }
    public float eval(){
        return (float) ((float) 1.0f/Math.tan(Math.toRadians(this.value.eval())));
    }
    public void dump(){
        System.out.print("cot( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Log implements Expression{
    final Expression value;
    Log(Expression value){
        this.value = value;
    }
    public float eval(){
        return (float) Math.log(this.value.eval());
    }
    public void dump(){
        System.out.print("log( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Value implements Expression{
    final float value;
    Value(float value){
        this.value = value;
    }
    public float eval(){
        return this.value;
    }
    public void dump(){
        if(this.value < 0){
            System.out.print("( ");
            System.out.print(this.value);
            System.out.print(" )");
        } else
            System.out.print(this.value);
    }
}

class Variable implements Expression{
    Map variable = new HashMap();
    final String key;
    Variable(String variable,float value){
        this.variable.put(variable, value);
        key = variable;
    }
    public float eval(){
        return (float) this.variable.get(this.key);
    }
    public void dump(){
        System.out.print(this.key);
    }
}