package org.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptFunctions {

	private final ScriptEngine jsEngine ;

	public JavaScriptFunctions(String fileName) {
		super();
		ScriptEngineManager factory = new ScriptEngineManager();
		this.jsEngine = factory.getEngineByName("javascript");
		evalFile(fileName);
	}
	
	private void evalFile(String fileName) {

		Reader reader;
		try {
			reader = new FileReader( new File(fileName) );
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File '" + fileName + "' not found.");
		}
		
		try {
			jsEngine.eval(reader);
		} catch (ScriptException e) {
			throw new RuntimeException("Javascript error in file '" + fileName + "' (ScriptException)", e);
		}
		
		try {
			reader.close();
		} catch (IOException e1) {
			// what can I do ?
		}
				
	}
	
	private Object invokeFuncion(String functionName, Object... args)  {
		Invocable invocableEngine = (Invocable) this.jsEngine ;
		Object result = null ;
		try {
			result = invocableEngine.invokeFunction(functionName, args) ;
		} catch (NoSuchMethodException e1) {
			throw new RuntimeException("Function '" + functionName + "' not found (NoSuchMethodException)", e1) ;
		} catch (ScriptException e1) {
			throw new RuntimeException("Error in function '" + functionName + "' (ScriptException)", e1) ;
		}
		return result ;
	}

	public String getEntityName(String tableName) {
		Object[] args = {tableName};
		return (String) invokeFuncion("getEntityName", args);
	}

	public boolean isOK(String tableName) {
		Object[] args = {tableName};
		return (Boolean) invokeFuncion("isOK", args);
	}
}
