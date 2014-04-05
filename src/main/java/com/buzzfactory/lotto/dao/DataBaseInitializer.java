package com.buzzfactory.lotto.dao;

import java.util.Calendar;
import java.util.Date;
import com.buzzfactory.lotto.dao.lottodraw.LottoDrawDao;
import com.buzzfactory.lotto.dao.user.UserDao;
import com.buzzfactory.lotto.entity.Configuration;
import com.buzzfactory.lotto.entity.LottoDraw;
import com.buzzfactory.lotto.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Initialize the database with some test entries.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class DataBaseInitializer {

    private UserDao userDao;

    private LottoDrawDao lottoDrawDao;

    private PasswordEncoder passwordEncoder;

    protected DataBaseInitializer() {

        /* Default constructor for reflection instantiation */
    }

    public DataBaseInitializer(UserDao userDao, LottoDrawDao lottoDrawDao, PasswordEncoder passwordEncoder) {

        this.userDao = userDao;
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
        
        
        Configuration usc = new Configuration();
        usc.setBall1(1);
        usc.setBall2(2);
        usc.setBall3(5);
        usc.setBall4(12);
        usc.setBall5(24);
        usc.setBall6(36);
        usc.setCreationDate(new Date(System.currentTimeMillis()));
        User sergiUser = new User("sergi", this.passwordEncoder.encode("sergi"));
        sergiUser.addRole("user");
        sergiUser.addRole("admin");
        sergiUser.addConfiguration(usc);
        this.userDao.save(sergiUser);        

        Configuration umc = new Configuration();
        umc.setBall1(2);
        umc.setBall2(3);
        umc.setBall3(4);
        umc.setBall4(5);
        umc.setBall5(6);
        umc.setBall6(7);
        umc.setCreationDate(new Date(System.currentTimeMillis()));
        User martaUser = new User("marta", this.passwordEncoder.encode("marta"));
        martaUser.addRole("user");
        martaUser.addRole("admin");
        martaUser.addConfiguration(umc);
        this.userDao.save(martaUser);         
        

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
