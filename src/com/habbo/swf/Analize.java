package com.habbo.swf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Analize {
	
	public static void init(File script, String id) throws IOException {
		System.out.println("Analizing id ".concat(id));
		String variable = "", parseClass = "";
		BufferedReader in = new BufferedReader(new FileReader(script));
		for(String line = ""; (line = in.readLine()) != null; ) {
			line = line.trim();
			if(line.startsWith(Utils.event.concat("[").concat(id).concat("] = "))) {
				StringTokenizer token = new StringTokenizer(line, " = ");
				token.nextToken();
				variable = token.nextToken().replace(";", "");
			}
		}
		in.close();
		if(variable.isEmpty()) {
			System.out.println("The id ".concat(id).concat(" not exists"));
		} else {
			System.out.println("Packet class: ".concat(variable));
			in = new BufferedReader(new FileReader(script));
			for(String line = ""; (line = in.readLine()) != null; ) {
				line = line.trim();
				if(line.startsWith("public function ".concat(variable))) {
					String tmp = in.readLine().trim();
					parseClass = (tmp.equals("{") ? in.readLine().trim() : tmp).replace("super(_arg1, ", "").replace(");", "");
				}
			}
			in.close();
			Parser.get(script, parseClass);
		}
	}
	
}
