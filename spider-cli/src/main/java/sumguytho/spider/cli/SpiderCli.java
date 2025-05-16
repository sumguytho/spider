package sumguytho.spider.cli;

import java.io.File;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

import sumguytho.asm.mod.deobfu.DeobfuscationOptions;

public class SpiderCli {

	public static final String PROGRAM_NAME = "spider-cli";

	private static DeobfuscationOptions parseLine(final CommandLine line, final Options opts) throws CliException, ParseException {
		if (line.hasOption("h")) {
			printHelp(PROGRAM_NAME, opts);
			System.exit(ExitCodes.HELP);
		}

		DeobfuscationOptions res = new DeobfuscationOptions();

		final boolean classesOnly = line.hasOption("c");
		if (line.hasOption("a") && classesOnly) {
			throw new CliException("Options -a and -c are exclusive. You should only choose one.");
		}
		// If option isn't set it automatically default to all files.
		res.classesOnly = !classesOnly;
		res.verbose = line.hasOption("v");

		if (line.hasOption("i")) {
			res.input = line.getParsedOptionValue("i");
		}
		else {
			throw new CliException("Option -i is mandatory.");
		}
		if (line.hasOption("o")) {
			res.output = line.getParsedOptionValue("o");
		}
		else {
			res.output = new File(res.input.getPath() + ".out");
		}

		return res;
	}

	private static void printHelp(String progName, Options opts) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp(progName, "Deobfuscates a jar file.", opts, "", true);
	}

	public static void main(String[] args) {
		Options opts = new Options();

		opts.addOption(
			Option.builder("h")
                .longOpt("help")
                .desc("display help message")
                .build()
		);
		opts.addOption(
				Option.builder("v")
	                .longOpt("verbose")
	                .desc("display information about each applied deobfuscation")
	                .build()
		);
		// If I specify -i as required arg It will be impossible to use -h without
		// generating an exception.
		opts.addOption(
			Option.builder("i")
                .longOpt("input")
                .desc("path to input jar (required)")
                .hasArg()
                .argName("input_jar")
                .type(File.class)
                .build()
		);
		opts.addOption(
			Option.builder("o")
                .longOpt("output")
                .desc("name of output jar (by default outputs to <input_jar>.out)")
                .hasArg()
                .argName("output_jar")
                .type(File.class)
                .build()
		);

		opts.addOption(
				Option.builder("a")
	                .longOpt("all-files")
	                .desc("passthrough non-class files as-is into output jar (default)")
	                .build()
			);
		opts.addOption(
				Option.builder("c")
	                .longOpt("classes-only")
	                .desc("output jar will only contain class files")
	                .build()
			);

		CommandLine line = null;
		DeobfuscationOptions spiderOptions = null;

		try {
			CommandLineParser parser = new DefaultParser();
			line = parser.parse(opts, args);
			spiderOptions = parseLine(line, opts);
		}
		catch (ParseException | CliException ex) {
			System.out.println(ex.getMessage() + "\nUse option -h for help.");
			System.exit(ExitCodes.ERR);
		}
		System.out.println(spiderOptions.toString());

		// Spider sp = new Spider();
		// System.out.println("Transforming " + args[0]);
		// sp.transform(args[0]);
	}
	private static final class CliException extends Exception {
		public CliException(String message) {
			super(message);
		}
		public CliException(Throwable throwable) {
			super(throwable);
		}
		public CliException(String message, Throwable throwable) {
			super(message, throwable);
		}
	}
}
