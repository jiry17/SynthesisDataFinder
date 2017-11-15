import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;  
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;  
import org.eclipse.jdt.core.dom.MethodDeclaration;  
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class JdtAstUtil {  
    public static CompilationUnit getCompilationUnit(String javaFilePath){  
        byte[] input = null;  
        try {  
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));  
            input = new byte[bufferedInputStream.available()];  
                bufferedInputStream.read(input);  
                bufferedInputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
        ASTParser astParser = ASTParser.newParser(AST.JLS3);  
        astParser.setEnvironment(null, null, null, true);
        astParser.setUnitName("");
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        astParser.setResolveBindings(true);
        astParser.setBindingsRecovery(true);
        astParser.setSource(new String(input).toCharArray());  
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null)); 
       // System.out.println("hasResolvedBindings:" + result.getAST().hasResolvedBindings());
        return result;  
    }  
}  
class DemoVisitor extends ASTVisitor {  
	public int Methodnum,Classnum;
	public int APIknow,APIunknow;
	DemoVisitor(){
		Methodnum=Classnum=0;
		APIknow=APIunknow=0;
	}
    
    public boolean visit(MethodDeclaration node) {  
    	if (APIunknow!=0 && APIknow < 20 && APIknow >= 3){
    		Methodnum ++;
    	}
    	APIknow = 0;
    	APIunknow = 0;
        return true;  
    }  
  
    @Override  
    public boolean visit(TypeDeclaration node) {  
    	Classnum+=1;
        return true;  
    }  

	public boolean visit(MethodInvocation node){
		if (node.resolveMethodBinding() == null){
			APIunknow ++;
		} else {
			APIknow ++;
		}
		return true;
	}
}  
class DemoVisitorTest {  
    int flag;
    public DemoVisitorTest(String path) {  
        CompilationUnit comp = JdtAstUtil.getCompilationUnit(path);  
          
        DemoVisitor visitor = new DemoVisitor();  
        comp.accept(visitor);  
        if (visitor.APIunknow == 0 && visitor.APIknow >= 3 && visitor.APIknow < 20){
        	visitor.Methodnum ++;
        }
        if (visitor.Classnum>1||visitor.Methodnum == 0) flag=0; else flag=1; 
    }  
}  
public class Main{
	public static void main(String args[]){
		//System.out.println("start check");
		int len=args.length;
		String answer_path = "Check";
		if (len!=0){
			answer_path = "";
			for (int i=0; i < len; i++){
				answer_path = answer_path + args[i];
			}
		}
		File output=new File(answer_path + ".out");
		DemoVisitorTest now=new DemoVisitorTest(answer_path + ".java");
		if (!output.exists()){
			try{
				output.createNewFile();
			}catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
		}
		try {  
            FileWriter fileWriter = new FileWriter(output);  
           // System.out.println(now.flag);
            fileWriter.write(Integer.toString(now.flag));  
            fileWriter.close(); // ¹Ø±ÕÊý¾ÝÁ÷  
  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}
}