import java.io.*;
import java.util.*;

public class firstTask {
    public static void main(String[] args) throws IOException{
        FileReader in = null;
        try {
            in = new FileReader(args[0]);
            BufferedReader bf = new BufferedReader(in);
            String line;

            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        }finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
