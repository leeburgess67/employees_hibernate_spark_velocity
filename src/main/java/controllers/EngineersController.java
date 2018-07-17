package controllers;

import com.sun.xml.internal.ws.api.pipe.Engine;
import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import org.omg.CORBA.PUBLIC_MEMBER;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.externalStaticFileLocation;

public class EngineersController {

    public EngineersController() {
        this.setUpEndpoints();
    }

    public void setUpEndpoints(){
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        //CREATE

        get("/engineers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/engineers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/engineers", (req, res) -> {
            String firstName = req.queryParams("first-name");

            String lastName = req.queryParams("last-name");

            int salary = Integer.parseInt(req.queryParams("salary"));

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            Engineer newEngineer = new Engineer(firstName, lastName, salary, department);
            DBHelper.save(newEngineer);

            res.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);


        //READ
        get("/engineers", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/index.vtl");

            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineers", engineers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        get("/engineers/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/id.vtl");

            int engineerId = Integer.parseInt(req.params(":id"));
            Engineer engineer= DBHelper.find(engineerId, Engineer.class);

            model.put("engineer", engineer);
            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);


        //UPDATE
        get("/engineers/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);

            int engineerId = Integer.parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(engineerId, Engineer.class);

            model.put("engineer", engineer);
            model.put("departments", departments);
            model.put("template", "templates/engineers/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/engineers/:id", (req, res) -> {
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = (req.queryParams("first-name"));
            String lastName = (req.queryParams("last-name"));
            int salary = Integer.parseInt(req.queryParams("salary"));

            int engineerId = Integer.parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(engineerId, Engineer.class);
            engineer.setFirstName(firstName);
            engineer.setLastName(lastName);
            engineer.setSalary(salary);
            engineer.setDepartment(department);
            DBHelper.update(engineer);

            res.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);



        //DELETE
        post("/engineers/:id/delete", (req, res) -> {
            int engineerId = Integer.parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(engineerId, Engineer.class);
            DBHelper.delete(engineer);

            res.redirect("/engineers");

            return null;
        }, velocityTemplateEngine);

























    }














}
