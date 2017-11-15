package myutil;

import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class Validator {
	public Vector<String> valid_code = new Vector<>();
	public Vector<String> method_call_string = new Vector<>();
	public Vector<String> type_use_string = new Vector<>();
	public void check(String code) {
		CompilationUnit current_AST = JdtAstUtil.getCompilationUnit(code);
		MethodInfoVisitor getinfo = new MethodInfoVisitor();
		current_AST.accept(getinfo);
		//System.out.println(getinfo.method_id_string.size() + " " + getinfo.method_info.size());
		for (int i = 0; i < getinfo.method_info.size(); ++i) {
			valid_code.add(getinfo.method_info.get(i).toString());
			method_call_string.add(getinfo.method_call_string.get(i).toString());
			type_use_string.add(getinfo.type_use_string.get(i).toString());
		}
	}
}
