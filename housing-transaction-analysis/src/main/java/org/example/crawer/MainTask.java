package org.example.crawer;

import org.example.dao.MongoDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class MainTask implements PageProcessor {
    @Override
    public void process(Page page) {
        Document doc = Jsoup.parse(page.getHtml().toString());
        System.out.println((page.getUrl().toString()));
        if (page.getUrl().toString()
                .matches("https://beijing.anjuke.com/sale/.*")) {
            System.out.println(doc.text());
            System.out.println(doc.getElementsByClass("list-left").first().getElementsByClass("property").first().text());
            Elements houseList = doc.getElementsByClass("list-left").first().getElementsByClass("property");
            System.out.println("fuck1");
            for (Element house : houseList) {
                String infoLink = house
//                        .getElementsByClass("property-content")
//                        .first().getElementsByClass("property-content-title").first()
                        .getElementsByTag("a").first().attr("href");
                page.addTargetRequest(infoLink);
            }

            if (doc.getElementsByClass("pagination").size() == 0) {
                String nextPageLink = doc.getElementsByClass("next next-active")
                        .first().attr("href");
                page.addTargetRequest(nextPageLink);
            }
        } else if (page.getUrl().toString()
                .matches("https://beijing.anjuke.com/prop/view/.*")) {
            System.out.println("heyhey");
            //实例化实体类HouseInfo创建对象houseInfo
            HouseInfo houseInfo = new HouseInfo();
            //获取标题名称
            String text = doc.getElementsByClass("title")
                    .first().ownText();
            //将标题名称保存到实体类对象houseInfo
            houseInfo.setTitle(text);
            //获取房屋总价并保存到实体类对象houseInfo
            houseInfo.setTotal_price(
                    getNum(doc
                            .getElementsByClass("maininfo-price-wrap").first()
                            .getElementsByClass("maininfo-price-num").first()
//                            .getElementsByTag("em")
                            .text()));

            //获取房屋核心卖点数据
            String housedesc = doc
                    .getElementsByClass("houseInfo-desc").first()
                    .getElementsByClass("houseInfo-item").first()
                    .getElementsByClass("houseInfo-item-desc").text();
            //将房屋核心卖点数据保存到实体类对象houseInfo
            houseInfo.setSelling_point(housedesc);
            //将房屋url链接保存到实体类对象houseInfo
            houseInfo.setLink(page.getUrl().toString());
            new MongoDao("mongoproject", "house")
                    .insert(houseInfo.toString());

            //获取基本数据
            //获取所有房屋详情内容保存到Elements类对象infoTable
            Elements community = doc.getElementsByClass("maininfo-community-item");
            for (Element el : community) {
                String infoType = el.getElementsByClass("maininfo-community-item-label").first().text();
                String infoContent = el.getElementsByClass("maininfo-community-item-name").first().text();
                switch (infoType) {
                    case "所属小区":
                        houseInfo.setCommunity(infoContent); //所属小区
                        break;
                    case "所属区域":
                        houseInfo.setDistrict(el.getElementsByTag("a").first().text());
                        //所在片区
                        houseInfo.setStreet_district(el.getElementsByTag("a").last().text());
                        //所在街道
                        houseInfo.setLocation("哈利波特骑着扫帚飞");
                        break;
                }
            }

            Element hxNum = doc
                    .getElementsByClass("maininfo-model-item maininfo-model-item-1").first()
                    .getElementsByClass("maininfo-model-strong-num").first();
            Element hxUnit =  doc
                    .getElementsByClass("maininfo-model-item maininfo-model-item-1").first()
                    .getElementsByClass("maininfo-model-strong-unit").first();
            String hx = "";
            for(int i=0; i<3; i++) {
                hx = hx + hxNum.text() + hxUnit.text();
                hxNum = hxNum.nextElementSibling();
                hxUnit = hxUnit.nextElementSibling();
            }
            houseInfo.setHouse_type(hx); // 房屋户型
            houseInfo.setFloor(doc
                    .getElementsByClass("maininfo-model-item maininfo-model-item-1").first()
                    .getElementsByClass("maininfo-model-weak").first().text()); // 所在楼层


            houseInfo.setArea(getNum(doc.getElementsByClass("maininfo-model-item maininfo-model-item-2")
                    .first().getElementsByClass("maininfo-model-strong-num").first().text())); // 建筑面积
            houseInfo.setDecorate(doc.getElementsByClass("maininfo-model-item maininfo-model-item-2")
                    .first().getElementsByClass("maininfo-model-weak").first().text()); // 装修程度

            houseInfo.setDirection(doc.getElementsByClass("maininfo-model-item maininfo-model-item-3")
                    .first().getElementsByClass("maininfo-model-strong-text").first().text()); //房屋朝向
            houseInfo.setTime((int) getNum(doc.getElementsByClass("maininfo-model-item maininfo-model-item-3")
                    .first().getElementsByClass("maininfo-model-weak").first().text())); // 建造年代
            String typeinfo = doc.getElementsByClass("maininfo-model-item maininfo-model-item-3")
                    .first().getElementsByClass("maininfo-model-weak").first().text();
            houseInfo.setType(typeinfo.substring(typeinfo.indexOf("/")+1)); // 房屋类型

            houseInfo.setDown_payment(getNum("99")); //参考首付
            houseInfo.setMonthly_payment(0L); // 参考月供

            Elements houseMainInfo = doc.getElementsByClass("houseInfo-main").first()
                            .getElementsByClass("houseInfo-main-item-first");
            for(Element el: houseMainInfo) {
                String infoType = el.getElementsByClass("houseInfo-main-item-label")
                        .first().text();
                String infoContent = el.getElementsByClass("houseInfo-main-item-name")
                        .first().text();
                switch(infoType) {
                    case "产权性质":
                        houseInfo.setOwnership(infoContent); // 产权性质
                        break;
                    case "产权年限":
                        houseInfo.setProperty((int) getNum(infoContent)); // 产权年限
                        break;
                    case "房本年限":
                        houseInfo.setUse_time(infoContent); // 房本年限
                        break;
                    case "唯一住房":
                        houseInfo.setOnly(infoContent); // 唯一住房
                        break;
                }
            }
            houseInfo.setUnit_price((int) getNum(doc.getElementsByClass("maininfo-avgprice-price").first().text())); // 房屋单价
            houseInfo.setElevator("无"); // 配套电梯

            //遍历infoTable对象获取房屋详情中每一类信息，保存到实体类对象houseInfo
            /*for (Element el : infoTable){
                //获取房屋信息中每一类信息的标签
                String infoType = el
                        .getElementsByClass("houseInfo-label text-overflow")
                        .first().text();
                //获取标签对应的数据
                String infoContent = el
                        .getElementsByClass("houseInfo-content")
                        .first().text();
                switch (infoType){
                    case "所属小区：" :
                        houseInfo.setCommunity(infoContent);
                        break;
                    case "房屋户型：" :
                        houseInfo.setHouse_type(infoContent);
                        break;
                    case "房屋单价：":
                        houseInfo.setUnit_price((int)getNum(infoContent));
                        break;
                    case  "所在位置：":
                        //获取所在区
                        houseInfo.setDistrict(el
                                .getElementsByClass("houseInfo-content").first()
                                .getElementsByTag("a").first().text());
                        //所在片区
                        houseInfo.setStreet_district(el
                                .getElementsByClass("houseInfo-content").first()
                                .getElementsByTag("a").last().text());
                        //所在街道
                        houseInfo.setLocation(infoContent
                                .replaceAll(".*－","").trim());
                        break;
                    case "建筑面积：":
                        houseInfo.setArea(getNum(infoContent));
                        break;
                    case  "参考首付：":
                        houseInfo.setDown_payment(getNum(infoContent));
                        break;
                    case "建造年代：":
                        houseInfo.setTime((int)getNum(infoContent));
                        break;
                    case  "房屋朝向：":
                        houseInfo.setDirection(infoContent);
                        break;
                    case "参考月供：":
                        houseInfo.setMonthly_payment(0L);
                        break;
                    case  "房屋类型：":
                        houseInfo.setType(infoContent);
                        break;
                    case "所在楼层：":
                        houseInfo.setFloor(infoContent);
                        break;
                    case  "装修程度：":
                        houseInfo.setDecorate(infoContent);
                        break;
                    case "产权年限：":
                        houseInfo.setProperty((int) getNum(infoContent));
                        break;
                    case  "配套电梯：":
                        houseInfo.setElevator(infoContent);
                        break;
                    case "房本年限：":
                        houseInfo.setUse_time(infoContent);
                        break;
                    case  "产权性质：":
                        houseInfo.setOwnership(infoContent);
                        break;
                    case "唯一住房：":
                        houseInfo.setOnly(infoContent);
                        break;
                }
            }*/
            page.putField("item", houseInfo);
        }
    }

    //传入一个字符串 提取其中的数字
    public double getNum(String string) {
        String s = string
                .replaceAll("[\\u4e00-\\u9fa5]|[/m²]", "")
                .trim();
        double i = Double.valueOf(s).doubleValue();
        return i;
    }

    @Override
    public Site getSite() {
        Site site = Site.me()
                //编码格式
                .setCharset("utf8")
                //超时时间
                .setTimeOut(30000)
                //重试休眠时间
                .setRetrySleepTime(10000)
                //重试次数
                .setRetryTimes(100)
                //线程休眠时间
                .setSleepTime(20000)
                //用户代理软件
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36 Edg/96.0.1054.62");
        return site;
    }
}
