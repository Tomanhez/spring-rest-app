package pl.tomek.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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
	
}
