import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {
    public static void main(String[] args){
        MathExpressionLexer lexer = new MathExpressionLexer(CharStreams.fromString("10"));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MathExpressionParser parser = new MathExpressionParser(tokenStream);
        MathExpressionParser.ExpressionContext expressionContext = parser.expression();

        parser.addParseListener(new Listener());


        /*DumpVisitor v = new DumpVisitor();
        System.out.print(v.visit(expressionContext) + " = ");
        EvaluateVisitor visitor = new EvaluateVisitor();
        System.out.println(visitor.visit(expressionContext));*/
    }
}

