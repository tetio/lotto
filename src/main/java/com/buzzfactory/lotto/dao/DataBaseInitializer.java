package com.buzzfactory.lotto.dao;

import java.util.Calendar;
import java.util.Date;
import com.buzzfactory.lotto.dao.lottodraw.LottoDrawDao;
import com.buzzfactory.lotto.dao.newsentry.NewsEntryDao;
import com.buzzfactory.lotto.dao.user.UserDao;
import com.buzzfactory.lotto.entity.LottoDraw;
import com.buzzfactory.lotto.entity.NewsEntry;
import com.buzzfactory.lotto.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Initialize the database with some test entries.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class DataBaseInitializer {

    private NewsEntryDao newsEntryDao;

    private UserDao userDao;

    private LottoDrawDao lottoDrawDao;

    private PasswordEncoder passwordEncoder;

    protected DataBaseInitializer() {

        /* Default constructor for reflection instantiation */
    }

    public DataBaseInitializer(UserDao userDao, NewsEntryDao newsEntryDao, LottoDrawDao lottoDrawDao, PasswordEncoder passwordEncoder) {

        this.userDao = userDao;
        this.newsEntryDao = newsEntryDao;
        this.lottoDrawDao = lottoDrawDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void initDataBase() {

        User userUser = new User("user", this.passwordEncoder.encode("user"));
        userUser.addRole("user");
        this.userDao.save(userUser);

        User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
        adminUser.addRole("user");
        adminUser.addRole("admin");
        this.userDao.save(adminUser);

        long timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
        for (int i = 0; i < 10; i++) {
            NewsEntry newsEntry = new NewsEntry();
            newsEntry.setContent("This is example content " + i);
            newsEntry.setDate(new Date(timestamp));
            this.newsEntryDao.save(newsEntry);
            timestamp += 1000 * 60 * 60;
        }

//        for (int i = 0; i < 20; i++) {
//            LottoDraw ld = new LottoDraw();
//            Calendar cal = Calendar.getInstance();
//            cal.set(2014, 1, i + 1);
//            ld.setDrawDate(cal.getTime());
//            ld.setBall1((int) (Math.random() * 49));
//            ld.setBall2((int) (Math.random() * 49));
//            ld.setBall3((int) (Math.random() * 49));
//            ld.setBall4((int) (Math.random() * 49));
//            ld.setBall5((int) (Math.random() * 49));
//            ld.setBall6((int) (Math.random() * 49));
//            ld.setComplementari((int) (Math.random() * 49));
//            ld.setReintegrament((int) (Math.random() * 49));
//            ld.setJoker((int) (Math.random() * 10000));
//            this.lottoDrawDao.save(ld);
//        }

    }

}
