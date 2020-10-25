package com.thikthak.app.rest;

import com.thikthak.app.domain.institute.Institute;
import com.thikthak.app.service.institute.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InstituteRestController {

    private InstituteService instituteService;

    @Autowired
    public void setInjectedBean(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @RequestMapping("/institute")
    List<Institute> getInstitute(){
        return instituteService.getAll();
    }



}
