package com.buzzfactory.lotto.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.buzzfactory.lotto.JsonViews;
import com.buzzfactory.lotto.dao.lottodraw.LottoDrawDao;
import com.buzzfactory.lotto.entity.LottoDraw;
import com.buzzfactory.lotto.parser.Lotto649Parser;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author casa
 */
@Component
@Path("/lottoDraw")
public class LottoDrawResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LottoDrawDao lottoDrawDao;

    @Autowired
    private ObjectMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() throws JsonGenerationException, JsonMappingException, IOException {
        this.logger.info("list()");
        List<LottoDraw> allEntries = lottoDrawDao.findAll();
        ObjectWriter viewWriter;
        if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {
            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
        }
        return viewWriter.writeValueAsString(allEntries);
    }

    @Path("shortList")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String shortList(@FormParam("nElems") Integer nElems) throws JsonGenerationException, JsonMappingException, IOException {
        this.logger.info("listElem()");
        //Integer numElems = Integer.parseInt(nElems);
        List<LottoDraw> entries = lottoDrawDao.findAll().subList(0, nElems);
        ObjectWriter viewWriter;
        if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {
            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
        }
        return viewWriter.writeValueAsString(entries);
    }

    
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LottoDraw create(LottoDraw lottoDraw) {

		this.logger.info("create(): " + lottoDraw);

		return this.lottoDrawDao.save(lottoDraw);
	}    
    
    
    @Path("init")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String initDB() throws JsonGenerationException, JsonMappingException, IOException {
        this.logger.info("initDB()");
        
        Lotto649Parser parser = new Lotto649Parser();
        List<LottoDraw> lds = parser.parse();
        for (LottoDraw ld: lds) {
            if(!lottoDrawDao.exists(ld.getDrawDate())) {
                lottoDrawDao.save(ld);
            }
        }
        lds.clear();
        lds = this.lottoDrawDao.findAll();
        
        ObjectWriter viewWriter;
        if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {
            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
        }

        return viewWriter.writeValueAsString(lds);
    }    
    
    
    private boolean isAdmin() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.toString().equals("admin")) {
                return true;
            }
        }

        return false;
    }
}
