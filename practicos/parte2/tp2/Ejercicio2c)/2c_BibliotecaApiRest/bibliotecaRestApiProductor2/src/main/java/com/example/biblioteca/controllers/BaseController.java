package com.example.biblioteca.controllers;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.biblioteca.entities.Base;

public interface BaseController <E extends Base, ID extends Serializable>{
	
	public ResponseEntity<?> getAll();
	public ResponseEntity<?> getAll(Pageable pageable);
	public ResponseEntity<?> getOne(@PathVariable ID id);
	public ResponseEntity<?> save(@RequestBody E entity);
	public ResponseEntity<?> update(@PathVariable ID is, @RequestBody E entity);
	public ResponseEntity<?> delete(@PathVariable ID id);
}
