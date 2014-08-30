package com.habbo;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.habbo.swf.Analize;
import com.habbo.swf.Utils;

public class Core {
	
	public static void main(String[] args) throws IOException {
		File script = new File(args.length > 0 ? args[0] : "script.txt");
		System.out.println("Script to analize: ".concat(script.getName()));
		Scanner in = new Scanner(System.in);
		Utils.init(script);
		String read = "";
		while(!read.equals("exit")) {
			System.out.print("Id: ");
			read = in.next();
			Analize.init(script, read);
		}
		in.close();
	}
	
}
