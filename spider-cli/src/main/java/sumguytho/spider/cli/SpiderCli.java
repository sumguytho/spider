package sumguytho.spider.cli;

import sumguytho.spider.Spider;

public class SpiderCli {

	public static void main(String[] args) {
		System.out.println("spider-cli");
		Spider sp = new Spider();
		System.out.println(String.format("spider: %d", sp.getInt()));
	}

}
