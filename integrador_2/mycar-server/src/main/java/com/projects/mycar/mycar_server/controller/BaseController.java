package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.BaseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface BaseController<D extends BaseDTO, ID extends Serializable> {

    ResponseEntity<?> getAll();

    //public ResponseEntity<?> getAll(Pageable pageable);
    ResponseEntity<?> getOne(@PathVariable ID id);

    ResponseEntity<?> save(@RequestBody D dto);

    ResponseEntity<?> update(@PathVariable ID id, @RequestBody D dto);

    ResponseEntity<?> delete(@PathVariable ID id);

}
