package com.buzzfactory.lotto.dao.lottodraw;

import com.buzzfactory.lotto.dao.Dao;
import com.buzzfactory.lotto.entity.LottoDraw;
import java.util.Date;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link LottoDraw}s.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public interface LottoDrawDao extends Dao<LottoDraw, Long> {
    Boolean exists(Date drawDate);
}
