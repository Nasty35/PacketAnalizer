package com.habbo.swf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Utils {
	
	public static String readInt;
	public static String composer, event;
	public static String release;
	
	public static void init(File script) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(script));
		for(String line = ""; (line = in.readLine()) != null; ) {
			line = line.trim();
			if(line.equals("function readString():String;")) {
				readInt = in.readLine().trim().replace("function ", "").replace("():int;", "").concat("();");
			} else if(line.contains("] = ") && line.endsWith("MessageComposer;")) {
				composer = new StringTokenizer(line, "[").nextToken();
			} else if(composer != null && line.contains("[4000] =") && !line.startsWith(composer)) {
				event = new StringTokenizer(line, "[").nextToken();
			} else if(line.contains(":String = \"RELEASE")) {
				release = line.split("\"")[1];
			}
		}
		System.out.println("Version: ".concat(release));
		in.close();
	}
	
}
