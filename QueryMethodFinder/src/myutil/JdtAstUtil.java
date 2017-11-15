package myutil;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class JdtAstUtil {
    public static CompilationUnit getCompilationUnit(String source_code){
        ASTParser astParser = ASTParser.newParser(AST.JLS4);
        astParser.setEnvironment(null, null, null, true);
        astParser.setUnitName("MethodInfoVisitor");
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        astParser.setResolveBindings(true);
        astParser.setBindingsRecovery(true);
        astParser.setSource(source_code.toCharArray());
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
        return result;
    }
}
