package controllers;

import db.DBHelper;
import models.Engineer;
import models.Manager;
import org.omg.CORBA.PUBLIC_MEMBER;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;

public class EngineersController {

    public EngineersController() {
        this.setUpEndpoints();
    }

    public void setUpEndpoints(){
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        //CREATE


        //READ
        get("/engineers", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/index.vtl");

            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineers", engineers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        //UPDATE


        //DELETE

























    }














}
