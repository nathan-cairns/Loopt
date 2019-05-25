package SoftEng751.SoftEng751;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import SoftEng751.SoftEng751.io.DefaultOutParser;
import SoftEng751.SoftEng751.io.LoopParser;
import SoftEng751.SoftEng751.io.OutputParser;
import SoftEng751.SoftEng751.io.SpoonLoopParser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import SoftEng751.SoftEng751.testMethods.DependencyVector;

import SoftEng751.SoftEng751.testMethods.AffineTransformation;
import SoftEng751.SoftEng751.testMethods.LoopVar;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	// CLI
    	Options options = new Options();
    	options.addOption("inString", true, "Code snippet as string input");
    	options.addOption("inFilePath", true, "File path to code snippet");
    	options.addOption("outFilePath", true, "Save output to specified filepath (default is print output)");
    	CommandLineParser cliParser = new DefaultParser();
    	CommandLine cmd = cliParser.parse(options, args);
    	
    	String input = "";
    	if (cmd.hasOption("inFilePath")) {
    		input = new String(Files.readAllBytes(Paths.get(cmd.getOptionValue("inFilePath"))));
    	}
    	else if (cmd.hasOption("inString")) {
    		input = cmd.getOptionValue("inString");
    	}
    	else {
    		System.out.println("Please specify a filepath to the code snippet, or input it as a string.");
    		HelpFormatter formatter = new HelpFormatter();
    		formatter.printHelp("Loopt", options);
    		System.exit(1);
    	}
    	
    	// Input parsing
    	System.out.println("Parsing loop dependencies...");
		LoopParser loopParser = new SpoonLoopParser(input);
		List<LoopVar> loopvariables = loopParser.getLoopVars();
		
		// Transformation
		System.out.println("Creating affine transformation...");
		List<DependencyVector> dependencyVectors = loopParser.getDependencyVectors();
    	AffineTransformation m = new AffineTransformation();
		List<LoopVar> transformedVariables = m.method(loopvariables);
		
		// Output generation
    	OutputParser out = new DefaultOutParser();
    	String outString = out.output(transformedVariables, loopParser.getOutermostLoop());
    	
    	if (cmd.hasOption("outFilePath")) {
    		try (PrintWriter pWriter = new PrintWriter(cmd.getOptionValue("outFilePath"))) {
    			pWriter.println(outString);
    		}
    		System.out.println("Code output saved to " + cmd.getOptionValue("outFilePath"));
    	} 
    	else {
    		System.out.println("Code output: ");
    		System.out.print(outString);
    	}
    }
    

}
