package pl.tomek.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.tomek.rest.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {

	private List<Student> students;
 
	@PostConstruct
	public void loadData() {
		students = new ArrayList<>();
		students.add(new Student("Johny", "Cash"));
		students.add(new Student("Chuck", "Norris"));
		students.add(new Student("Johny", "Smith"));
	}
	@GetMapping("/student")
	public List<Student> getStudent() {
		return students;
	}
	@GetMapping("/student/{studentId}")
	public Student getStudent(@PathVariable int studentId) {
		if((studentId >= students.size()) || (studentId < 0)) {
			throw new StudentNotFoundException("Student id not found - "+studentId);
		}
		return students.get(studentId);
	}
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handlerException(StudentNotFoundException e){
		StudentErrorResponse error = new StudentErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(e.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	//another exception handler
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleExceptionBadRequest(Exception e){
		StudentErrorResponse error = new StudentErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(e.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
}
