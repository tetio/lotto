package com.buzzfactory.lotto.parser;

import com.buzzfactory.lotto.entity.LottoDraw;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tetio
 */
public class Lotto649Parser {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public List<LottoDraw> parse() {
        List<LottoDraw> res = new ArrayList<LottoDraw>();
        try {
            SimpleDateFormat parserSDF = new SimpleDateFormat("dd/MM/yyyy");

            Document doc = Jsoup.connect("http://www.lotocatalunya.net/jsps/cat/l64990.jsp").timeout(10000).get();
            String title = doc.title();
            Element content = doc.getElementsByClass("taula_verd_contingut").first();
            Elements tds = content.getElementsByClass("texte_normal_negre");
            int i = 0;
            LottoDraw ld = new LottoDraw();
            for (Element td : tds) {
                String tdText = td.text();
                if (!"".equals(tdText)) {
                    if (i == 0) {
                        ld = new LottoDraw();
                        ld.setDrawDate(parserSDF.parse(tdText));
                    } else if (i == 1) {
                        String[] a = tdText.split(" ");
                        String[] balls = a[0].split(",");
                        ld.setBall1(Integer.parseInt(balls[0]));
                        ld.setBall2(Integer.parseInt(balls[1]));
                        ld.setBall3(Integer.parseInt(balls[2]));
                        ld.setBall4(Integer.parseInt(balls[3]));
                        ld.setBall5(Integer.parseInt(balls[4]));
                        ld.setBall6(Integer.parseInt(balls[5]));
                        ld.setPlus(Integer.parseInt(a[2]));
                        ld.setComplementari(Integer.parseInt(a[4]));
                        ld.setReintegrament(Integer.parseInt(a[6]));
                    } else {
                        ld.setJoker(Integer.parseInt(tdText));
                        res.add(ld);
                    }
                    i = (i + 1) % 3;
                }
            }
        } catch (Exception e) {
            logger.error("Lotto649Parser.parse", e);
        }
        return res;
    }

}
