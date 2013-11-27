package com.example.webdictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDictionary {
	
	public static void main(String[] args) {
		List<String[]> list1 = new ArrayList<String[]>();
		List<String[]> list2 = new ArrayList<String[]>();
		boolean ok = true;
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to Web Dictionary System .");
		while (ok) {
			System.out.print("dictionary > ");
			String c = s.nextLine();
			if (c.equals("hello")) {
				System.out.println("Welcome to Web Dictionary System .");
			} else if (c.startsWith("search")) {
				list2.clear();
				BufferedReader r = null;
				String plW = null;
				String enW = null;
				int i = 1;
				try {
					r = new BufferedReader(new InputStreamReader(new URL(
							"http://www.dict.pl/dict?word=" + c.split(" ")[1]
									+ "&words=&lang=PL").openStream()));
					boolean w1 = true;
					String t = null;
					Pattern pat = Pattern
							.compile(".*<a href=\"dict\\?words?=(.*)&lang.*");
					while ((t = r.readLine()) != null) {
						Matcher matcher = pat.matcher(t);
						if (matcher.find()) {
							String t2 = matcher.group(matcher.groupCount());
							if (w1) {
								System.out.print(i + ") " + t2 + " => ");
								plW = new String(t2.getBytes(), "UTF8");
								w1 = false;
							} else {
								System.out.println(t2);
								w1 = true;
								enW = new String(t2.getBytes(), "UTF8");
								list2.add(new String[] {plW, enW});
								i++;
							}
						}
					}
				} catch (MalformedURLException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (r != null) {
							r.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			} else if (c.startsWith("save")) {
				int num = new Integer(c.split(" ")[1]);
				list1.add(list2.get(num - 1));
			} else if (c.equals("showFound")) {
				int i = 1;
				for (String[] el : list2) {
					System.out.println(i++ + ") " + Arrays.toString(el));
				}
			} else if (c.equals("showSaved")) {
				for (String[] el : list1) {
					System.out.println(Arrays.toString(el));
				}
			} else if (c.equals("exit")) {
				ok = false;
			}
		}
		s.close();
	}
}
