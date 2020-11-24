import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class Listener implements MathExpressionListener{

    Stack<Object> stack = new Stack();
    Object root = null;

    public void append(Object node){
        this.stack.push(node);
        if (this.root == null)
            this.root = node;
    }

    public void enterNumericAtomExp(MathExpressionParser.NumericAtomExpContext ctx){
        Value node = new Value(ctx);
        this.append(node);
    }

    public void exitNumericAtomExp(MathExpressionParser.NumericAtomExpContext ctx){
        Value node = (Value) this.stack.pop();
        node.value = Float.parseFloat(ctx.NUMBER().getText());
    }

    public void enterPowerExp(MathExpressionParser.PowerExpContext ctx){
        Power node = new Power(ctx);
        this.append(node);
    }

    public void exitPowerExp(MathExpressionParser.PowerExpContext ctx){
        Power node = (Power) this.stack.pop();
    }

    public void enterMulDivExp(MathExpressionParser.MulDivExpContext ctx){
        if(ctx.ASTERISK().getText() == "*"){
            Multiply node = new Multiply(ctx);
            this.append(node);
        }
        else if(ctx.SLASH().getText() == "/"){
            Divide node = new Divide(ctx);
            this.append(node);
        }
    }

    public void exitMulDivExp(MathExpressionParser.MulDivExpContext ctx){
        if(ctx.ASTERISK().getText() == "*"){
            Multiply node = (Multiply) this.stack.pop();
        }
        else if(ctx.SLASH().getText() == "/"){
            Divide node = (Divide) this.stack.pop();
        }
    }

    public void enterParenthesisExp(MathExpressionParser.ParenthesisExpContext ctx){
        Paranthesis node = new Paranthesis(ctx);
        this.append(node);
    }

    public void exitParenthesisExp(MathExpressionParser.ParenthesisExpContext ctx){
        Paranthesis node = (Paranthesis) this.stack.pop();
    }

    public void enterFunExp(MathExpressionParser.FunExpContext ctx){
        if(ctx.ID().getText() == "sin") {
            Sin node = new Sin(ctx);
            this.append(node);
        } else if(ctx.ID().getText() == "cos") {
            Cos node = new Cos(ctx);
            this.append(node);
        } else if(ctx.ID().getText() == "log") {
            Log node = new Log(ctx);
            this.append(node);
        } else if(ctx.ID().getText() == "tan") {
            Tan node = new Tan(ctx);
            this.append(node);
        } else if(ctx.ID().getText() == "cot") {
            Cot node = new Cot(ctx);
            this.append(node);
        }
    }

    public void exitFunExp(MathExpressionParser.FunExpContext ctx){
        if(ctx.ID().getText() == "sin") {
            Sin node = (Sin) this.stack.pop();
        } else if(ctx.ID().getText() == "cos") {
            Cos node = (Cos) this.stack.pop();
        } else if(ctx.ID().getText() == "log") {
            Log node = (Log) this.stack.pop();
        } else if(ctx.ID().getText() == "tan") {
            Tan node = (Tan) this.stack.pop();
        } else if(ctx.ID().getText() == "cot") {
            Cot node = (Cot) this.stack.pop();
        }
    }

    public void enterIdAtomExp(MathExpressionParser.IdAtomExpContext ctx){
        Variable node = new Variable(ctx);
        this.append(node);
    }

    public void exitIdAtomExp(MathExpressionParser.IdAtomExpContext ctx){
        Variable node = (Variable) this.stack.pop();
        node.variable = ctx.ID().getText();
    }

    public void enterAddSubExp(MathExpressionParser.AddSubExpContext ctx){
        if(ctx.PLUS().getText() == "+"){
            Sum node = new Sum(ctx);
            this.append(node);
        }
        else if(ctx.MINUS().getText() == "/"){
            Subtract node = new Subtract(ctx);
            this.append(node);
        }
    }

    public void exitAddSubExp(MathExpressionParser.AddSubExpContext ctx){
        if(ctx.PLUS().getText() == "+"){
            Sum node = (Sum) this.stack.pop();
        }
        else if(ctx.MINUS().getText() == "/"){
            Subtract node = (Subtract) this.stack.pop();
        }
    }

    public void visitTerminal(TerminalNode terminalNode) {

    }

    public void visitErrorNode(ErrorNode errorNode) {

    }

    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}

interface Expression{
    public void dump();
}

class Sum implements Expression{
    final Expression first;
    final Expression second;

    Sum(MathExpressionParser.AddSubExpContext ctx){
        this.first = (Expression) ctx.expression(0);
        this.second = (Expression) ctx.expression(1);
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
    Subtract(MathExpressionParser.AddSubExpContext ctx){
        this.first = (Expression) ctx.expression(0);
        this.second = (Expression) ctx.expression(1);
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
    Multiply(MathExpressionParser.MulDivExpContext ctx){
        this.first = (Expression) ctx.expression(0);
        this.second = (Expression) ctx.expression(1);
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
    Divide(MathExpressionParser.MulDivExpContext ctx){
        this.first = (Expression) ctx.expression(0);
        this.second = (Expression) ctx.expression(1);
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
    Power(MathExpressionParser.PowerExpContext ctx){
        this.value = (Expression) ctx.expression(0);
        this.power = (Expression) ctx.expression(1);
    }

    public void dump(){
        System.out.print("( ");
        this.value.dump();
        System.out.print(" ^ ");
        this.power.dump();
        System.out.print(" )");
    }
}

class Paranthesis implements Expression{
    final Expression exp;
    Paranthesis(MathExpressionParser.ParenthesisExpContext ctx){
        this.exp = (Expression) ctx.expression();
    }

    public void dump() {
        System.out.print("( ");
        this.exp.dump();
        System.out.print(" )");
    }
}

class Sin implements Expression{
    final Expression value;
    Sin(MathExpressionParser.FunExpContext ctx){
        this.value = (Expression) ctx.expression();
    }

    public void dump(){
        System.out.print("sin( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Cos implements Expression{
    final Expression value;
    Cos(MathExpressionParser.FunExpContext ctx){
        this.value = (Expression) ctx.expression();
    }

    public void dump(){
        System.out.print("cos( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Tan implements Expression{
    final Expression value;
    Tan(MathExpressionParser.FunExpContext ctx){
        this.value = (Expression) ctx.expression();
    }

    public void dump(){
        System.out.print("tan( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Cot implements Expression{
    final Expression value;
    Cot(MathExpressionParser.FunExpContext ctx){
        this.value = (Expression) ctx.expression();
    }

    public void dump(){
        System.out.print("cot( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Log implements Expression{
    final Expression value;
    Log(MathExpressionParser.FunExpContext ctx){
        this.value = (Expression) ctx.expression();
    }

    public void dump(){
        System.out.print("log( ");
        this.value.dump();
        System.out.print(" )");
    }
}

class Value implements Expression{
    float value;
    Value(MathExpressionParser.NumericAtomExpContext ctx){
        this.value = Float.parseFloat(ctx.getText());
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
    String variable;

    Variable(MathExpressionParser.IdAtomExpContext ctx) {
        this.variable = ctx.getText();
    }

    public void dump() {
        System.out.print(this.variable);
    }
}