package com.buzzfactory.lotto.dao.lottodraw;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.buzzfactory.lotto.dao.JpaDao;
import com.buzzfactory.lotto.entity.LottoDraw;

import org.springframework.transaction.annotation.Transactional;


/**
 * JPA Implementation of a {@link LottoDrawDao}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class JpaLottoDrawDao extends JpaDao<LottoDraw, Long> implements LottoDrawDao {

	public JpaLottoDrawDao() {

		super(LottoDraw.class);
	}


	@Override
	@Transactional(readOnly = true)
	public List<LottoDraw> findAll() {

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<LottoDraw> criteriaQuery = builder.createQuery(LottoDraw.class);

		Root<LottoDraw> root = criteriaQuery.from(LottoDraw.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<LottoDraw> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
