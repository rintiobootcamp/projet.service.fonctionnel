package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.AxeCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Projet;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */

@Component
public class ProjetService implements DatabaseConstants{

    ProjetCRUD projetCRUD;

    @PostConstruct
    public void init(){
        projetCRUD = new ProjetCRUD();
    }

    public List<Projet> findAll(HttpServletRequest request) throws SQLException {
        return projetCRUD.read();
    }
    
    public int getCountProject() throws SQLException{
        return projetCRUD.read().size();
    }

}
