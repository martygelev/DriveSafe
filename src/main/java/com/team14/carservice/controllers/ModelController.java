package com.team14.carservice.controllers;


import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.models.vehicles.Model;
import com.team14.carservice.service.common.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/model")
public class ModelController {

    private final ModelService modelService;
    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public List<Model> getAllByManufacturer(Make make){
        return modelService.getAllByManufacturer(make);
    }

    @GetMapping("/{name}")
    public List<Model> getAllByManufacturer(@PathVariable String name){
        return modelService.getAllByManufacturerName(name);
    }


}
