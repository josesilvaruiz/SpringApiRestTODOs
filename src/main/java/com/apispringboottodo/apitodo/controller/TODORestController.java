package com.apispringboottodo.apitodo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apispringboottodo.apitodo.entitiy.TODO;
import com.apispringboottodo.apitodo.entitiy.User;
import com.apispringboottodo.apitodo.service.ITODOService;
import com.apispringboottodo.apitodo.service.IUserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class TODORestController {

	@Autowired
	private ITODOService todoService;
	
	@Autowired
	private IUserService userService;

	@GetMapping("/todos")
	public List<TODO> index() {
		return todoService.findAll();
	}

	@GetMapping("/todos/page/{page}")
	public Page<TODO> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 5);
		return todoService.findAll(pageable);
	}

	@GetMapping("/todos/search/{term}/page/{page}")
	public  Page<TODO> todosByUserId(@PathVariable Integer page,@PathVariable Integer term){
		Pageable pageable = PageRequest.of(page, 5);
		return todoService.findTodoByUser(term, pageable);
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id) {
		TODO todo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			todo = todoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", " Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (todo == null) {
			response.put("mensaje", "El TODO ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TODO>(todo, HttpStatus.OK);

	}

	@PostMapping("/todos")
	public ResponseEntity<?> create(@Valid @RequestBody TODO todo, BindingResult result) {
		TODO TODONew = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			User todoUser = userService.findById(todo.getUser().getId()); 
			todo.setUser(todoUser);
			TODONew = todoService.save(todo);
		} catch (DataAccessException e) {
			response.put("mensaje", " Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido creado con exito");
		response.put("cliente", TODONew);
		return new ResponseEntity<Map>(response, HttpStatus.CREATED);
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody TODO todo, @PathVariable Integer id, BindingResult result) {
		TODO TODOActual = todoService.findById(id);
		TODO TODOActualizado = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (todo == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			TODOActual.setTitle(todo.getTitle());
			TODOActual.setCompleted(todo.getCompleted());
			TODOActual.setUser(todo.getUser());
			TODOActualizado = todoService.save(TODOActual);
		} catch (DataAccessException e) {
			response.put("mensaje", " Error al realizar el update en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido actualizado con exito");
		response.put("cliente", TODOActualizado);
		return new ResponseEntity<Map>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			todoService.delete(id);

		} catch (DataAccessException e) {
			response.put("mensaje", " Error al eliminar un cliente en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido eliminado con exito");
		return new ResponseEntity<Map>(response, HttpStatus.OK);
	}
}