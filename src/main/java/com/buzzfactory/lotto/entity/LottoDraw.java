package com.buzzfactory.lotto.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.buzzfactory.lotto.JsonViews;

import org.codehaus.jackson.map.annotate.JsonView;


@javax.persistence.Entity
public class LottoDraw implements Entity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
    private Date drawDate;

	@Column
	private Integer ball1;

	@Column
	private Integer ball2;

	@Column
	private Integer ball3;

    @Column
	private Integer ball4;

    @Column
	private Integer ball5;

    @Column
	private Integer ball6;
    
	@Column
	private Integer plus;
        
	@Column
	private Integer complementari;

	@Column
	private Integer reintegrament;
    
	@Column
	private Integer joker;
    
    
    
	public LottoDraw() {

		this.drawDate = new Date();
	}


	@JsonView(JsonViews.Admin.class)
	public Long getId() {

		return this.id;
	}


	@JsonView(JsonViews.User.class)
	public Date getDrawDate() {

		return this.drawDate;
	}


	public void setDrawDate(Date date) {

		this.drawDate = date;
	}


	@JsonView(JsonViews.User.class)
	public Integer getBall1() {

		return this.ball1;
	}

    
	public void setBall1(Integer ball1) {

		this.ball1 = ball1;
	}

    
    @JsonView(JsonViews.User.class)
    public Integer getBall2() {
        return ball2;
    }


    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }


    @JsonView(JsonViews.User.class)
    public Integer getBall3() {
        return ball3;
    }


    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }


    @JsonView(JsonViews.User.class)
    public Integer getBall4() {
        return ball4;
    }


    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    @JsonView(JsonViews.User.class)
    public Integer getBall5() {
        return ball5;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    @JsonView(JsonViews.User.class)
    public Integer getBall6() {
        return ball6;
    }

    public void setBall6(Integer ball6) {
        this.ball6 = ball6;
    }

    @JsonView(JsonViews.User.class)
    public Integer getComplementari() {
        return complementari;
    }

    public void setComplementari(Integer complementari) {
        this.complementari = complementari;
    }

    @JsonView(JsonViews.User.class)
    public Integer getReintegrament() {
        return reintegrament;
    }

    public void setReintegrament(Integer reintegrament) {
        this.reintegrament = reintegrament;
    }

    @JsonView(JsonViews.User.class)
    public Integer getJoker() {
        return joker;
    }

    public void setJoker(Integer joker) {
        this.joker = joker;
    }

    @JsonView(JsonViews.User.class)
    public Integer getPlus() {
        return plus;
    }

    public void setPlus(Integer plus) {
        this.plus = plus;
    }

    
    

	@Override
	public String toString() {
		return String.format("LottoDraw[%d, %d, %d, %d, %d, %d, %d]", this.id, this.ball1, this.ball2, this.ball3, this.ball4, this.ball5, this.ball6);
	}



}
