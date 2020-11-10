import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class AbstractSyntaxTree {
    public static void main(String[] args){
        Map m = new HashMap();
        Set keys;
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter the value for: a = ");
        m.put("a",scan.nextFloat());
        keys = m.keySet();
        for(Object key : keys) {
            // ((2^2 + a + sin(30)) + (12 * 2 + log(13 - (-2)) / 5))
            Expression root = new Sum(new Sum(new Sum(new Power(new Value(2.0f),new Value(2.0f)),new Variable(key.toString())), new Sin(new Value(30.0f))), new Sum(new Multiply(new Value(12.0f),new Value(2.0f)), new Divide(new Log(new Subtract(new Value(13.0f),new Value(-2.0f))), new Value(5.0f))));
            root.dump();
            System.out.print(" = " + root.eval(m));
        }
    }
}

interface Expression{
    public float eval(Map<String, Float> map);
    public void dump();
}

class Sum implements Expression{
    final Expression first;
    final Expression second;
    Sum(Expression first, Expression second){
        this.first = first;
        this.second = second;
    }
    public float eval(Map<String, Float> map){
        return this.first.eval(map) + this.second.eval(map);
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
    public float eval(Map<String, Float> map){
        return this.first.eval(map) - this.second.eval(map);
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
    public float eval(Map<String, Float> map){
        return this.first.eval(map) * this.second.eval(map);
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
    public float eval(Map<String, Float> map){
        return this.first.eval(map) / this.second.eval(map);
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
    public float eval(Map<String, Float> map){
        return (float) Math.pow(this.value.eval(map), this.power.eval(map));
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
    public float eval(Map<String, Float> map){
        return (float) Math.sin(Math.toRadians(this.value.eval(map)));
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
    public float eval(Map<String, Float> map){
        return (float) Math.cos(Math.toRadians(this.value.eval(map)));
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
    public float eval(Map<String, Float> map){
        return (float) Math.tan(Math.toRadians(this.value.eval(map)));
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
    public float eval(Map<String, Float> map){
        return (float) ((float) 1.0f/Math.tan(Math.toRadians(this.value.eval(map))));
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
    public float eval(Map<String, Float> map){
        return (float) Math.log(this.value.eval(map));
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
    public float eval(Map<String, Float> map){
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

class Variable implements Expression {
    final String variable;

    Variable(String variable) {
        this.variable = variable;
    }

    public float eval(Map<String, Float> map) {
        return (float) map.get(this.variable);
    }

    public void dump() {
        System.out.print(this.variable);
    }
}