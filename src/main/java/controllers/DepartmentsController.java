package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {

    public DepartmentsController() {
        this.setUpEndpoints();
    }

    private void setUpEndpoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        //CREATE


        //READ
        get("/departments", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/index.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


// DONT REALLY NEED TO VIEW INDIVIDUALLY AS NO MORE INFO
//        get("/departments/:id", (req, res) -> {
//            HashMap<String, Object> model = new HashMap<>();
//            model.put("template", "templates/departments/id.vtl");
//
//            int departmentId = Integer.parseInt(req.params(":id"));
//            Department department= DBHelper.find(departmentId, Department.class);
//
//            model.put("department", department);
//            return new ModelAndView(model, "templates/layout.vtl");
//
//        }, velocityTemplateEngine);


        //UPDATE
        get("/departments/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();

            int departmentId = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(departmentId, Department.class);

            model.put("department", department);
            model.put("template", "templates/departments/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/departments/:id", (req, res) -> {
            int departmentId = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(departmentId, Department.class);
            String deptName = (req.queryParams("name"));

            department.setTitle(deptName);
            DBHelper.update(department);

            res.redirect("/departments");
            return null;
        }, velocityTemplateEngine);



        //DELETE


























    }




}






