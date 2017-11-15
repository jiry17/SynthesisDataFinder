package myutil;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.internal.compiler.codegen.StackMapFrameCodeStream;

import java.util.Stack;
import java.util.Vector;

class SecondVisitor extends ASTVisitor {
    int unknown_method = 0, known_method = 0;
    int unknown_var = 0;
    String method_pattern = "";
    String class_pattern = "";
    public SecondVisitor(String _method_pattern, String _class_pattern){
    		method_pattern = ";." + _method_pattern;
    		class_pattern = _class_pattern;
    		//System.out.println(_method_pattern + " " + _class_pattern + "");
    }
    public boolean visit(MethodInvocation node){
    		if (node.resolveMethodBinding() == null || node.resolveMethodBinding().getKey().contains(class_pattern)){
            //System.out.println("unknown");
        		unknown_method ++;
        } else {
            known_method ++;
        }
        return true;
    }
    public boolean visit(SimpleName node){
        if (node.resolveBinding() == null){
            unknown_var ++;
            return true;
        }
        String node_key = node.resolveBinding().getKey();
        int node_kind = node.resolveBinding().getKind();
        //node_kind == 3 means this node is the name of a variable
        if (!node_key.contains(method_pattern) && node_kind == 3){
        		//System.out.println("unknown");
            unknown_var ++;
        }
        return true;
    }
    public boolean check_accept(){
        return unknown_var + unknown_method == 0 && known_method >= 2;
    }
}

public class MethodInfoVisitor extends ASTVisitor {
    public Vector<MethodDeclaration> method_info;
    Stack<String> class_pattern;
    public MethodInfoVisitor(){
        method_info = new Vector<>();
        class_pattern = new Stack<String>();
        class_pattern.push("");
    }
    public boolean visit(TypeDeclaration node) {
    		class_pattern.push(node.resolveBinding().getKey());
    		return true;
    }
    public void endVisit(TypeDeclaration node) {
    		class_pattern.pop();
    }
    public boolean visit(MethodDeclaration node){
        SecondVisitor sec_visitor = 
        		new SecondVisitor(node.getName().toString(), class_pattern.peek());
        node.accept(sec_visitor);
        if (sec_visitor.check_accept()){
            method_info.add(node);
        }
        return false;
    }
}
