import java.util.ArrayList;

class ExpressionNode {
    private double Value;
    private ExpressionEvaluator.OPERATION Operation;
    private ExpressionNode Left;
    private ExpressionNode Right;

    public ExpressionNode(double Value) {
        this.Value = Value;
        this.Operation = ExpressionEvaluator.OPERATION.Equals;
        this.Left = null;
        this.Right = null;
    }
    public ExpressionNode(ExpressionEvaluator.OPERATION Operation) {
        this(Operation, null, null);
    }
    public ExpressionNode(ExpressionEvaluator.OPERATION Operation, ExpressionNode Left, ExpressionNode Right) {
        this.Value = 0;
        if (Operation == ExpressionEvaluator.OPERATION.Equals) {
            System.err.println("You cannot explicitly define an Equals node.");
            System.exit(1);
            //throw new Exception("You cannot explicitly define an Equals node. To define a value-only node, passing in a double");
        }
        this.Operation = Operation;
        this.Left = Left;
        this.Right = Right;
    }

    public void SetChildren(ExpressionNode Left, ExpressionNode Right) {
        this.Left = Left;
        this.Right = Right;
    }
    public double eval() {
        //determine and perform operation on children
        switch (this.Operation) {
            case Equals:
                break;
            case Power:
                this.Value = Math.pow(this.Left.eval(), this.Right.eval());
                break;
            case Division:
                this.Value = this.Left.eval() / this.Right.eval();
                break;
            case Multiplication:
                this.Value = this.Left.eval() * this.Right.eval();
                break;
            case Modulus:
                this.Value = (int)this.Left.eval() % (int)this.Right.eval();
                break;
            case Addition:
                this.Value = this.Left.eval() + this.Right.eval();
                break;
            case Subtraction:
                this.Value = this.Left.eval() - this.Right.eval();
                break;
            default:
                this.Value = 0;
        }
        return this.Value;
    }
    private String GetOperation() {
        return String.valueOf(ExpressionEvaluator.OPERATIONS.charAt(this.Operation.ordinal()));
    }

    public String dump() {
        if (this.Operation == ExpressionEvaluator.OPERATION.Equals) {
            return String.valueOf(this.Value);
        } else {
            //fully parenthesized
            return "(" + this.Left.dump()  + " " + this.GetOperation() + " " + this.Right.dump() + ")";
        }
    }

    public boolean IsOperation() {
        //if the node is not a value and the children are not assigned then we are a stand-alone operation
        return (this.Operation != ExpressionEvaluator.OPERATION.Equals) && (this.Left == null);
    }
    public String toString() {
        return (this.Operation != ExpressionEvaluator.OPERATION.Equals) ? this.GetOperation() : String.valueOf(this.Value);
    }
}

interface Expression{
    public double eval();
    public void dump();
}

public class ExpressionEvaluator implements Expression{

    public static void main(String []args){
        ExpressionEvaluator bt = new ExpressionEvaluator("(2^5+6)/2");
        bt.dump();
        System.out.print(bt.eval());
    }

    public static enum OPERATION {
        Subtraction, Addition, Modulus, Multiplication, Division, Power, LeftParenthesis, RightParenthesis, Equals
    }
    private static int Precedence(String Operation) {
        switch (Operation.charAt(0)) {
            case '(':
            case ')':
                return 4;
            case '^':
                return 3;
            case '/':
            case '*':
            case '%':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                System.err.println("Invalid operation.");
                return -1;
        }
    }
    private static OPERATION GetOperation(int i) {
        switch (i) {
            case 0:
                return OPERATION.Subtraction;
            case 1:
                return OPERATION.Addition;
            case 2:
                return OPERATION.Modulus;
            case 3:
                return OPERATION.Multiplication;
            case 4:
                return OPERATION.Division;
            case 5:
                return OPERATION.Power;
            case 6:
                return OPERATION.LeftParenthesis;
            case 7:
                return OPERATION.RightParenthesis;
            default:
                return OPERATION.Equals;
        }
    }

    public static final String OPERATIONS = "-+%*/^()";

    private ExpressionNode Root;

    public ExpressionEvaluator(String Expression) {
        this.Root = this.ConstructExpressionTree(Expression);
    }

    private ExpressionNode ConstructExpressionTree(String Expression) {
        int j = 0;
        for (int i = 0; i < Expression.length(); ++i) {
            if (ExpressionEvaluator.OPERATIONS.indexOf(Expression.charAt(i)) != -1) {
                if (Expression.charAt(i) == '(') ++j;
                if (Expression.charAt(i) == ')') --j;
                if (j < 0) {
                    System.err.println("Opening parenthesis expected. Reverting expression to zero.");
                    return new ExpressionNode(0);
                }
                if ((i > 0) && (Expression.charAt(i - 1) != ' ')) {
                    Expression = Expression.substring(0, i) + " " + Expression.substring(i);
                    ++i;
                }
                if ((i < Expression.length() - 1) && (Expression.charAt(i + 1) != ' ')) {
                    Expression = Expression.substring(0, i + 1) + " " + Expression.substring(i + 1);
                    ++i;
                }
            }
        }
        if (j > 0) {
            System.err.println("Closing parenthesis expected. Reverting expression to zero.");
            return new ExpressionNode(0);
        }

        String[] Terms = Expression.split(" ");
        ArrayList<ExpressionNode> Nodes = new ArrayList<ExpressionNode>();

        for (String Term : Terms) {
            if (ExpressionEvaluator.OPERATIONS.indexOf(Term) == -1) {
                try {
                    Nodes.add(new ExpressionNode(Double.valueOf(Term)));
                }
                catch (NumberFormatException e) {
                    System.err.println("Invalid operand '" + Term + "'. Reverting to zero.");
                    Nodes.add(new ExpressionNode(0));
                }
            } else {
                Nodes.add(new ExpressionNode(ExpressionEvaluator.GetOperation(ExpressionEvaluator.OPERATIONS.indexOf(Term))));
            }
        }

        ArrayList<ExpressionNode> OperationStack = new ArrayList<ExpressionNode>();
        ArrayList<ExpressionNode> ValueStack = new ArrayList<ExpressionNode>();

        try {
            for (int i = 0; i < Nodes.size(); ++i) {
                switch (Nodes.get(i).toString().charAt(0)) {
                    case '(':
                        OperationStack.add(0, Nodes.get(i));
                        break;
                    case ')':
                        //assign operations children until left parenthesis is encountered
                        while (OperationStack.get(0).toString().equals("(") == false)
                        {
                            ValueStack.add(0, OperationStack.remove(0));
                            ValueStack.get(0).SetChildren(ValueStack.remove(2), ValueStack.remove(1));
                        }
                        OperationStack.remove(0); //remove left parenthesis
                        break;
                    case '-':
                    case '+':
                    case '%':
                    case '*':
                    case '/':
                    case '^':
                        while ((OperationStack.size() > 0) && (OperationStack.get(0).toString().equals("(") == false) && (ExpressionEvaluator.Precedence(Nodes.get(i).toString()) <= ExpressionEvaluator.Precedence(OperationStack.get(0).toString()))) {
                            ValueStack.add(0, OperationStack.remove(0));
                            ValueStack.get(0).SetChildren(ValueStack.remove(2), ValueStack.remove(1));
                        }
                        OperationStack.add(0, Nodes.get(i));
                        break;
                    default: //case number:
                        ValueStack.add(0, Nodes.get(i));
                }
            }
            while (OperationStack.size() > 0) {
                ValueStack.add(0, OperationStack.remove(0));
                ValueStack.get(0).SetChildren(ValueStack.remove(2), ValueStack.remove(1));
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Too many/few operations. Reverting expression to zero.");
            ValueStack.clear();
            ValueStack.add(new ExpressionNode(0));
        }
        catch (Exception e) {
            System.err.println(e);
            System.err.println("Reverting expression to zero.");
            ValueStack.clear();
            ValueStack.add(new ExpressionNode(0));
        }
        return ValueStack.get(0);
    }

    public void dump() {
        System.out.println(this.Root.dump());
    }

    public double eval() {
        return this.Root.eval();
    }
}