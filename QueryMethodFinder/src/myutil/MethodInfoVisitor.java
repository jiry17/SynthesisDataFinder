package myutil;

import org.eclipse.jdt.core.dom.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

class SecondVisitor extends ASTVisitor {
    int unknown_method = 0;
    int known_method = 0;
    int unknown_var = 0;
    String method_pattern = "";
    String class_pattern = "";
    int constant_number = 0;
    int unknown_type = 0;
    HashSet<String> unique_method = new HashSet<String>();
    HashSet<String> unique_type = new HashSet<String>();
    Vector<String> method_call = new Vector<String>();
    Vector<String> type_use = new Vector<String>();
    
    public SecondVisitor(String _method_pattern, String _class_pattern){
    		method_pattern = ";." + _method_pattern;
    		class_pattern = _class_pattern;
    }
    
    void AddMethodCall(String method_name) {
    		if (!unique_method.contains(method_name)) {
    			unique_method.add(method_name);
    			method_call.add(method_name);
    		}
    }
    
    void AddTypeUse(String type_name) {
    		if (!unique_type.contains(type_name)) {
    			unique_type.add(type_name);
    			type_use.add(type_name);
    		}
    }
    
    public boolean visit(SimpleType node) {
    	//	System.out.println(node.getName());
    		if (node.resolveBinding() == null) {
    			unknown_type ++;
    			return true;
    		}
    		String type_key = node.resolveBinding().getKey();
    		if (node.resolveBinding().isFromSource() || type_key.contains("Recovered")) {
    			unknown_type ++;
    		}
    		AddTypeUse(node.getName().toString());
    		return true;
    }
    
    public boolean visit(MethodInvocation node){
    		if (node.resolveMethodBinding() == null || node.resolveMethodBinding().getKey().contains(class_pattern)){
        		unknown_method ++;
        } else {
            known_method ++;
            AddMethodCall(node.getName().toString());
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
        if (!node_key.contains(method_pattern) && node_kind == 3){
            unknown_var ++;
        }
        return true;
    }
    
    public boolean visit(NumberLiteral node) {
    		if (node.toString() != "0") {
    			constant_number ++;
    		}
    		return true;
    }
    
    public boolean visit(StringLiteral node) {
    		if (node.toString() != "\"\"") {
    			constant_number ++;
    		}
    		return true;
    }
    
    public boolean visit(CharacterLiteral node) {
    		constant_number ++;
    		return true;
    }
    
    public boolean visit(ThrowStatement node) {
    		return false;
    }
    
    public boolean visit(TryStatement node) {
    		return false;
    }
    
    public String GetMethodCall() {
    		Collections.sort(method_call);
    		if (method_call.size() == 0) {
    			return "";
    		}
    		String method_call_string = "";
    		for (String method_name : method_call) {
    			method_call_string += method_name + "+";
    		}
    		//System.out.println(method_call_string);
    		return method_call_string.substring(0, method_call_string.length() - 1);
    }
    
    public String GetTypeUse() {
    		Collections.sort(type_use);
    		if (type_use.size() == 0) {
    			return "";
    		}
    		String type_use_string = "";
    		for (String type_name : type_use) {
    			type_use_string += type_name + "+";
    		}
    		return type_use_string.substring(0, type_use_string.length() - 1);
    }
    
    public boolean check_accept(){
        return unknown_var + unknown_method == 0 && known_method >= 3 
        		&& unknown_type == 0 && constant_number <= 0 && known_method <= 8;
    }
    
    public void preVisit(ASTNode node) {
    		//System.out.println("\n\n\n" + node.getNodeType());
    		//System.out.println(node.toString());
    }
    
    public void postVisit(ASTNode node) {
    		//System.out.println("End search " + node.getNodeType());
    }
}

public class MethodInfoVisitor extends ASTVisitor {
	
    public Vector<MethodDeclaration> method_info;
    public Vector<String> method_call_string;
    public Vector<String> type_use_string;
    Stack<String> class_pattern;
    
    public MethodInfoVisitor(){
        method_info = new Vector<>();
        method_call_string = new Vector<>();
        type_use_string = new Vector<>();
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
    		//System.out.println("start the second level search in " + node.getName());
        SecondVisitor sec_visitor = 
        		new SecondVisitor(node.getName().toString(), class_pattern.peek());
        node.accept(sec_visitor);
        if (sec_visitor.check_accept()){
            method_info.add(node);
            method_call_string.add(sec_visitor.GetMethodCall());
            type_use_string.add(sec_visitor.GetTypeUse());
        }
        //System.out.println("finish the second level search in " + node.getName());
        return false;
    }
}
