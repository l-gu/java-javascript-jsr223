package org.demo;

import java.net.URL;

import javax.script.ScriptException;

public class Test2 {

	public static void main(String[] args) throws ScriptException {
		
		URL fileURL = Test1.class.getClassLoader().getResource("rules.js");
		
		JavaScriptFunctions functions = new JavaScriptFunctions(fileURL.getFile());
		
		System.out.println("getEntityName('BOOK')    : " + functions.getEntityName("BOOK") ) ;
		System.out.println("getEntityName('COUNTRY') : " + functions.getEntityName("COUNTRY") ) ;
		
		System.out.println("isOK('BOOK')    : " + functions.isOK("BOOK") ) ;
		System.out.println("isOK('COUNTRY') : " + functions.isOK("COUNTRY") ) ;

		System.out.println("End.");
		
	}
}
