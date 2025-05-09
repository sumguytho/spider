package sumguytho.spider.cli;

import sumguytho.spider.Spider;

public class SpiderCli {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: spider-cli <path-to-jar>");
			System.out.println("Outputs deobfuscated jar to <path-to-jar>.out.");
			System.exit(1);
			
		}
		Spider sp = new Spider();
		System.out.println("Transforming " + args[0]);
		sp.transform(args[0]);
	}

}
