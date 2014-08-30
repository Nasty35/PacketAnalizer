package com.habbo.swf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Parser {
	
	private static File file;
	
	private static int loop = 0;
	
	private static boolean isif = false;
	private static boolean empty = true;
	private static boolean checkLoop = false;
	
	public static void get(File script, String parseClass) throws IOException {
		System.out.println("Parser class: ".concat(parseClass));
		file = script;
		BufferedReader in = new BufferedReader(new FileReader(script));
		for(String line = ""; (line = in.readLine()) != null; ) {
			line = line.trim();
			if(line.startsWith("public class ".concat(parseClass))) {
				readParse(in, "}//package");
			}
		}
		in.close();
		if(empty) {
			write("The structure hasn't params");
		}
		loop = 0;
		isif = checkLoop = false;
		empty = true;
	}
	
	private static void readParse(BufferedReader in, String end) throws IOException {
		while(!in.readLine().contains("public function parse"));
		String line = "";
		while(true) {
			line = in.readLine().trim();
			if(line.startsWith("}") && !line.contains(";"))
				break;
			checkParam(line);
		}
	}
	
	private static void readConstruct(String name) throws IOException {
		write("new class: ".concat(name));
		BufferedReader in = new BufferedReader(new FileReader(file));
		while(!in.readLine().contains("public function ".concat(name)));
		String line = "";
		while(true) {
			line = in.readLine().trim();
			if(line.startsWith("};")) {
				if(checkLoop) {
					checkLoop = false;
				} else {
					loop--;
				}
			}
			if(line.startsWith("}") && !line.contains(";")) {
				if(isif) {
					isif = false;
				} else {
					break;
				}
			}
			checkParam(line);
		}
		in.close();
	}
	
	private static void checkParam(String line) throws IOException {
		if(line.contains(Utils.readInt)) {
			write("int > \"" + line + "\"");
		} else if(line.contains("while")) {
			write("while > \"" + line + "\"");
			loop++;
		} else if(line.contains("readString()")) {
			write("string > \"" + line + "\"");
		} else if(line.contains("readBoolean()")) {
			write("boolean > \"" + line + "\"");
		} else if(line.contains("push(new") && line.contains("(_arg1));")) {
			StringTokenizer token = new StringTokenizer(line, "(");
			token.nextToken();
			readConstruct(token.nextToken().replace("new ", ""));
		} else if(line.contains(" = new ") && line.contains("(_arg1);")) {
			StringTokenizer token = new StringTokenizer(line, "=");
			token.nextToken();
			readConstruct(token.nextToken().replace(" new ", "").replace("(_arg1);", ""));
		} else if(line.startsWith("switch")) {
			write("switch > \"" + line + "\"");
			loop++;
		} else if(line.startsWith("case ")) {
			loop--;
			write("case > \"" + line + "\"");
			loop++;
		} else if(line.startsWith("default:")) {
			loop--;
			write("default > \"" + line + "\"");
			loop++;
		} else if(line.equals("if (_arg1 == null)")) {
			checkLoop = true;
		} else if(line.startsWith("if")) {
			write("if > \"" + line + "\"");
			loop++;
			isif = true;
		} else if(line.startsWith("else")) {
			loop--;
			write("else");
			loop++;
		}
	}
	
	private static void write(String line) {
		empty = false;
		for(int a = 0; a < loop; a++)
			line = "\t".concat(line);
		System.out.println(line);
	}
	
}
