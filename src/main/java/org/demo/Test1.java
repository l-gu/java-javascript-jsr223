package org.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Test1 {

	public static void main(String[] args) throws ScriptException {
		
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine jsEngine = factory.getEngineByName("javascript");
		
		printAvailableEngines() ;
		System.out.println("LANGUAGE : " + ScriptEngine.LANGUAGE );
		System.out.println("LANGUAGE_VERSION : " + ScriptEngine.LANGUAGE_VERSION );
		
		testBasicEval(jsEngine);
		testVariableSharing(jsEngine);
		testFunction(jsEngine);
		
		URL fileURL = Test1.class.getClassLoader().getResource("test1.js");
		System.out.println( "JS resource : " + fileURL.getFile() ) ;
		testEvalFile(jsEngine, fileURL.getFile() );
		
		System.out.println("invokeGetA : "+ invokeGetA((Invocable)jsEngine) );
		
		System.out.println("End.");
		
	}

	public static void printAvailableEngines() throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = mgr.getEngineFactories();

        for (ScriptEngineFactory factory : factories) {

            System.out.println("ScriptEngineFactory Info");

            String engName = factory.getEngineName();
            String engVersion = factory.getEngineVersion();
            String langName = factory.getLanguageName();
            String langVersion = factory.getLanguageVersion();

            System.out.printf("\tScript Engine: %s (%s)%n", engName, engVersion);

            List<String> extensions = factory.getExtensions();
            if (extensions.size() > 0) {
                System.out.println("\tEngine supports the following extensions:");
                for (String e : extensions) {
                    System.out.println("\t\t" + e);
                }
            }
            List<String> shortNames = factory.getNames();
            if (shortNames.size() > 0) {
                System.out.println("\tEngine has the following short names:");
                for (String n : factory.getNames()) {
                    System.out.println("\t\t" + n);
                }
            }
            System.out.printf("\tLanguage: %s (%s)%n", langName, langVersion);

        }
		
	}
	
	public static void testBasicEval(ScriptEngine jsEngine) throws ScriptException {
		
		Double month = (Double) jsEngine.eval("var date=new Date(); date.getMonth();"); 
		System.out.println("month = " + month );
		
		String s = (String) jsEngine.eval("var s1 = 'Hello'; s1 + ' world';"); 
		System.out.println("s = " + s );
		
	}

	public static void testVariableSharing(ScriptEngine jsEngine) throws ScriptException {
		jsEngine.put("msg", "Hello");
		jsEngine.put("a", 1);

		jsEngine.eval("var b = a + 2; var hello = msg + ' world' ; ");

		System.out.println("a = " + jsEngine.get("b") );
		System.out.println("hello = " + jsEngine.get("hello") );
		System.out.println("hello = " + jsEngine.get("hello") );
	}

	public static void testFunction(ScriptEngine jsEngine) throws ScriptException {

		jsEngine.eval( "function getHelloMessage(name) { return 'hello ' + name; };");
		jsEngine.eval( "function square(a) { println('-- square  '); return a * a; };");
		System.out.println("square = " + jsEngine.get("square") );
		System.out.println("xxx = " + jsEngine.get("xxx") ); // returns null if undefined
		System.out.println("hello = " + jsEngine.get("hello") );
		Invocable invocableEngine = (Invocable)jsEngine;
		try {
			System.out.println("getHelloMessage('foo') : " + invocableEngine.invokeFunction("getHelloMessage", "foo") );
			System.out.println("square(2) : " + invocableEngine.invokeFunction("square", 2) );
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void testEvalFile(ScriptEngine jsEngine, String fileName) throws ScriptException {

		Reader reader;
		try {
			reader = new FileReader( new File(fileName) );
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("File '" + fileName + "' not found.");
		}
		
		jsEngine.eval(reader);
		
		try {
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
	}

	public static String invokeGetA(Invocable invocableEngine)  {

		String functionName = "getA" ;
		Object result = null ;
		try {
			result = invocableEngine.invokeFunction(functionName) ;
		} catch (NoSuchMethodException e1) {
			throw new RuntimeException("Function '" + functionName + "' not found (NoSuchMethodException)", e1) ;
		} catch (ScriptException e1) {
			throw new RuntimeException("Error in function '" + functionName + "' (ScriptException)", e1) ;
		}
		return (String) result ;
	}
}
