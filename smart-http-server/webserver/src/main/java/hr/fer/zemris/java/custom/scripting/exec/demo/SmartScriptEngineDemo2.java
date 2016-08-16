package hr.fer.zemris.java.custom.scripting.exec.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program demonstrates execution of zbrajanje.smscr smart script using SmartScriptEngine.
 * @author Tomislav
 *
 */

public class SmartScriptEngineDemo2 {
	
	/**
	 * Method called at program start.
	 * @param args Command line arguments, not used.
	 * @throws IOException If error occurs while reading file path.
	 */
	public static void main(String[] args) throws IOException {

		String filepath = "lib/SmartScriptEngineTests/zbrajanje.smscr";
		String documentBody = new String(
				Files.readAllBytes(Paths.get(filepath)),StandardCharsets.UTF_8);
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");

		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)
				).execute();
	}
}
