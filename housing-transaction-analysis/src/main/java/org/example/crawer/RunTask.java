package org.example.crawer;

import us.codecraft.webmagic.Spider;
public class RunTask {
    public static void main(String[] args) {
        Spider.create(new MainTask())
                .addUrl("https://beijing.anjuke.com/sale/p5")
                .thread(5)
                .run();
    }
}
