package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTMLUtils {

    public static void displayHTMLInfo() {
        String html = FileUtils.readFromFile("example.html");
        Document doc = Jsoup.parse(html);

        String titleContent = doc.select("title").html();
        if (titleContent.isEmpty())
            System.out.println("No title found");
        else
            System.out.println("Title - " + titleContent);

        Elements metaHtml = doc.select("meta[name=keywords], meta[name=description], meta[name=robots]");
        System.out.println("\nMeta info:");
        metaHtml.forEach(el -> System.out.println("Found: " + el.toString() + "\nName attr value: " + el.attr("name") + "\nContent attr value: " + el.attr("content") + "\n"));

        System.out.println("\nText content of html: " + doc.text());

        doc.select("a").forEach(el -> {
            if (el.hasAttr("href")) {
                String href = el.attr("href");
                if (href.startsWith("/")) {
                    System.out.println("Intern link: " + href);
                    el.attr("href", "http://www.example.com" + href);
                    System.out.println("Rebuilded intern link: " + el.absUrl("href"));
                } else
                    System.out.println("Extern link: " + href);
            }
        });

        System.out.println("HTML document text: " + doc.text());
    }

}
