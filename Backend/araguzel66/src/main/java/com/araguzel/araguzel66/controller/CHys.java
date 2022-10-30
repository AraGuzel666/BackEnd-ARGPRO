/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.araguzel.araguzel66.controller;

import com.araguzel.araguzel66.Security.Controller.Mensaje;
import com.araguzel.araguzel66.Security.Dto.DtoHys;
import com.araguzel.araguzel66.entity.Hys;
import com.araguzel.araguzel66.service.SHys;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AraGuzel
 */
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = {"https://frontendaraguzel.web.app", "http://localhost:4200"})
@RequestMapping("/skill")
public class CHys {

    @Autowired
    SHys shys;

    @GetMapping("/lista")
    public ResponseEntity<List<Hys>> list() {
        List<Hys> list = shys.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Hys> getById(@PathVariable("id") int id) {
        if (!shys.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }
        Hys hys = shys.getOne(id).get();
        return new ResponseEntity(hys, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoHys dtoHys) {
        if (StringUtils.isBlank(dtoHys.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (shys.existsByNombre(dtoHys.getNombre())) {
            return new ResponseEntity(new Mensaje("Esa Skill ya existe"), HttpStatus.BAD_REQUEST);
        }
        Hys hys = new Hys(dtoHys.getNombre(), dtoHys.getPorcentaje());
        shys.save(hys);

        return new ResponseEntity(new Mensaje("Skill agregada"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoHys dtoHys) {
        if (!shys.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
        if (shys.existsByNombre(dtoHys.getNombre()) && shys.getByNombre(dtoHys.getNombre()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("Esa skill ya Existe"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(dtoHys.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es Obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Hys hys = shys.getOne(id).get();
        hys.setNombre(dtoHys.getNombre());
        hys.setPorcentaje(dtoHys.getPorcentaje());

        shys.save(hys);
        return new ResponseEntity(new Mensaje("Skill Actualizada"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!shys.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        shys.delete(id);

        return new ResponseEntity(new Mensaje("Skill Eliminada"), HttpStatus.OK);
    }

}
