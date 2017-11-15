import myutil.*;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.*;

public class Main {
    public static void main(String argv[]) {
        int precode_numbers = 0;
        String project_name = "";
        if (argv.length > 0) {
            precode_numbers = Integer.parseInt(argv[0]);
            if (argv.length > 1) {
            		project_name = argv[1];
            }
        }
        int new_numbers = precode_numbers;
        byte[] input = null;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("Check.java"));
            input = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(input);
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompilationUnit current_AST = JdtAstUtil.getCompilationUnit(new String(input));
        MethodInfoVisitor getinfo = new MethodInfoVisitor();
        current_AST.accept(getinfo);
        for (int i = 0; i < getinfo.method_info.size(); i++) {
            new_numbers++;
            System.out.println("get a valid method " + getinfo.method_info.get(i).getName());
            try {
                FileWriter output = new FileWriter(new File("Query/" + new_numbers + ".java"));
                output.write("// " + project_name + "\n");
                output.write(new String(input));
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter output = new FileWriter(new File("Check.out"));
            output.write("" + (new_numbers - precode_numbers));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}