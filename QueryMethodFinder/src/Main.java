import myutil.*;

import java.io.*;
import java.util.HashSet;

class RecursiveChecker {
	int method_number = 0;
	HashSet<String> method_id_set = new HashSet<>();
	
	public RecursiveChecker(int _method_number) {
		method_number = _method_number;
	}
	
	private String readcode(String path) {
		byte input[] = null;
		try {
			BufferedInputStream bufferedinputstream = new BufferedInputStream(new FileInputStream(path));
			input = new byte[bufferedinputstream.available()];
			bufferedinputstream.read(input);
			bufferedinputstream.close();
		} catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		return new String(input);
	}
	
	public void checkfile(File java_file) {
		Validator validator = new Validator();
		String code = readcode(java_file.getPath());
		//System.out.println(java_file.getPath());
		//System.out.println(code);
		validator.check(code);
		for (int method_id = 0; method_id < validator.valid_code.size(); ++method_id) {
			String code_snippet = validator.valid_code.elementAt(method_id);
			String method_call = validator.method_call_string.elementAt(method_id);
			String type_use = validator.type_use_string.elementAt(method_id);
			String code_id_string = method_call + "#" + type_use;
			if (method_id_set.contains(code_id_string)) {
				continue;
			}
			method_id_set.add(code_id_string);
			method_number++;
			try {
				FileWriter output = new FileWriter("data/" + method_number + ".java");
				output.write("// " + method_call + "\n");
				output.write("// " + type_use + "\n");
				BufferedReader bufferedreader = new BufferedReader(new FileReader(java_file.getPath()));
				String first_line = bufferedreader.readLine();
				output.write(first_line + "\n");
				bufferedreader.close();
				output.write(code_snippet);
				output.write("\n\n" + code);
				output.close();
			} catch (IOException e) {   
				e.printStackTrace();  
			}
		}
	}
	
	public void checkall(File current_file) {
		File file_list[] = current_file.listFiles();
		for (File son_file: file_list) {
			if (son_file.isDirectory()) {
				checkall(son_file);
			} else if (son_file.getName().contains(".java")){
				checkfile(son_file);
			}
		}
	}
}

public class Main {
    public static void main(String argv[]) {
        int candidate_min_id = 0;
        int candidate_max_id = 0;
        candidate_min_id = Integer.parseInt(argv[0]);
        candidate_max_id = Integer.parseInt(argv[1]);
        
        int pre_method_number = (new File("data")).listFiles().length;
        //System.out.println(candidate_min_id + " " + candidate_max_id);
        RecursiveChecker recursive_checker = new RecursiveChecker(pre_method_number);
        for (int id = candidate_min_id; id <= candidate_max_id; id++) {
        		recursive_checker.checkfile(new File("Query/" + id + ".java"));
        }
        int new_method_number = (new File("data")).listFiles().length;
        
        try {
        		FileWriter output_log = new FileWriter("new_data.log");
        		output_log.write(pre_method_number + " " + new_method_number + "\n");
        		output_log.close();
        } catch (IOException e) {
        		e.printStackTrace();
        }
    }
}