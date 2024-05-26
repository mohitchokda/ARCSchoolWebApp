package com.example.arc.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.arc.constants.ARCConstants;
import com.example.arc.model.ArcClass;
import com.example.arc.model.Contact;
import com.example.arc.model.Course;
import com.example.arc.model.Person;
import com.example.arc.repositories.ArcClassRepository;
import com.example.arc.repositories.CourseRepository;
import com.example.arc.repositories.PersonRepository;
import com.example.arc.services.ContactService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	ContactService contactService;
	
	@Autowired
	ArcClassRepository arcClassRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	
	@RequestMapping("/displayMessages")
	public ModelAndView displayMessages() {
		List<Contact> msgList= contactService.getAllOpenMessages();
		//System.out.println("Contact List : "+msgList);
		ModelAndView mv=new ModelAndView("messages.html");
		mv.addObject("msgCount",msgList.size());
		mv.addObject("msgList", msgList);
		return mv;
	}
 	
 	@RequestMapping("/closeMsg") //Query Param => /closeMsg?id=1  
 	public String closeMsgByID(@RequestParam int id){
 		contactService.closeMsg(id);//,auth.getName());//No need anymore
 		return "redirect:/admin/displayMessages";
 	}
 	
 	@RequestMapping("/displayClasses")
 	public ModelAndView displayClasses() {
 		ModelAndView mv=new ModelAndView();
 		mv.setViewName("classes.html");
 		
 		List<ArcClass> allClasses = arcClassRepository.findAll();
 		mv.addObject("arcClass",new ArcClass());
 		mv.addObject("arcClasses", allClasses);
 		return mv;
 	}
 	
 	
 	//Add new Class
 	@RequestMapping("/addNewClass")
 	public String addNewClass(@Valid @ModelAttribute("arcClass") ArcClass myclass) {
 		if(arcClassRepository.findByName(myclass.getName())==null) {
 			arcClassRepository.save(myclass);
 		}
 		return "redirect:/admin/displayClasses";
 	}
 	
 	//Delete Class
 	@RequestMapping("/deleteClass")
 	public String deleteClass(@RequestParam int id) {
 		//TODO:Set Class Id for all persons belonging to this class before deleting
 		Optional<ArcClass> currClass = arcClassRepository.findById(id);
 		
 		for(Person p : currClass.get().getPersons()) {
 			p.setArcClass(null);
 			//save/update to DB
 			personRepository.save(p);
 		}
 		arcClassRepository.deleteById(id);
 		return "redirect:/admin/displayClasses";
 	}
 	
 	//Display students page
 	@RequestMapping("/displayStudents")
 	public ModelAndView displayStudents(@RequestParam int classId, @RequestParam(required = false) boolean error
 			,HttpSession httpSession) {
 		ModelAndView mv=new ModelAndView();
 		Optional<ArcClass> currClass = arcClassRepository.findById(classId);
 		mv.setViewName("students.html");
 		mv.addObject("arcClass",currClass.get());
 		mv.addObject("person",new Person());
 		
 		if(error) {
 			mv.addObject("errorMsg","Invalid Student Email");
 			return mv;
 		}
 		//Add class details to Session
 		httpSession.setAttribute("arcClass",currClass.get());
 		return mv;
 	}
 	
 	//Add Student to this class
 	@RequestMapping("/addStudent")
 	public String addStudent(@ModelAttribute("person") Person p,HttpSession session) {
 		p = personRepository.findByEmail(p.getEmail());
 		ArcClass currClass = (ArcClass) session.getAttribute("arcClass");
 		
 		if(p==null || !(p.getPersonId()>0)) {
 			return "redirect:/admin/displayStudents?error=true&classId="+currClass.getClassId();
 		}
 		//TODO : Add Students in Class Set and update class Id for Student
 		p.setArcClass(currClass);
 		personRepository.save(p);
 		currClass.getPersons().add(p);
 		arcClassRepository.save(currClass);
 		return "redirect:/admin/displayStudents?classId="+currClass.getClassId();
 	}
 	
 	//Remove or Delete Student 
 	@RequestMapping("/deleteStudent")
 	public String deleteStudent(@RequestParam int personId,HttpSession session) {
 		
 		ArcClass currClass = (ArcClass) session.getAttribute("arcClass");
 		Optional<Person> person = personRepository.findById(personId);
 		//Remove curr class from person
 		person.get().setArcClass(null);
 		personRepository.save(person.get());
 		//Remove person from persons set of curr class
 		currClass.getPersons().remove(person.get());
 		currClass = arcClassRepository.save(currClass);
 		//Update in session
 		session.setAttribute("arcClass", currClass);
 		
 		return "redirect:/admin/displayStudents?classId="+currClass.getClassId();
 	}
 	
 	//display courses Page
 	@RequestMapping("/displayCourses/{pageNo}")
 	public ModelAndView displayCourses(@PathVariable(required = false) int pageNo,@RequestParam(required = false)String sortField) {
 		ModelAndView mv=new ModelAndView();
 		mv.setViewName("admin_courses.html");
 		if(sortField==null){
 			sortField = "courseId";
 		}
 		
 		Pageable pageable = PageRequest.of(pageNo-1,ARCConstants.MAX_PAGES,Sort.by(sortField).ascending());
 		
 		if(pageable==null) System.out.println("Pagable is NULL");
 		
 		Page<Course> coursePages = courseRepository.findAll(pageable);
 		int totalPages = coursePages.getTotalPages();
 		List<Course> currPageCourses = coursePages.getContent();
 		
 		//System.out.println("Pagable: Pages : "+totalPages+"\n"+"Content : "+currPageCourses);
 		
 		mv.addObject("currPageNo", pageNo);
 		mv.addObject("sortField",sortField);
 		mv.addObject("totalPages", totalPages);
 		mv.addObject("course",new Course());
 		mv.addObject("allcourses", currPageCourses);
 		return mv;
 	}
 	
 	//Add new Course
 	@RequestMapping("/addNewCourse")
 	public String addNewCourse(@ModelAttribute("course") Course c) {
 		//save course
 		Course course = courseRepository.findByCourseName(c.getCourseName());
 		if(course!=null) {
 			course.setFees(c.getFees());
 			courseRepository.save(course);
 		}else {
 			courseRepository.save(c);
 		}
 		return "redirect:/admin/displayCourses/1";
 	}
 	
 	//View Students page
 	@RequestMapping("/viewStudents")
 	public ModelAndView viewStudents(@RequestParam int courseId, @RequestParam(required = false) String error
 			,HttpSession httpSession) {
 		ModelAndView mv=new ModelAndView();
 		Optional<Course> currCourse = courseRepository.findById(courseId);
 		mv.setViewName("course_students.html");
 		mv.addObject("currCourse",currCourse.get());
 		mv.addObject("person",new Person());
 		
 		if(error!=null && !error.isBlank()) {
 			mv.addObject("errorMsg",error);
 			return mv;
 		}
 		//Add class details to Session
 		httpSession.setAttribute("currCourse",currCourse.get());
 		return mv;
 	}
 	
 	//Add Student to this class
 	 @RequestMapping("/addStudentToCourse")
 	 public String addStudentToCourse(@ModelAttribute("person") Person p,HttpSession session) {
 	 	Person person = personRepository.findByEmail(p.getEmail());
 	 	Course course = (Course) session.getAttribute("currCourse");
 	 		
 	 	if(person==null || !(person.getPersonId()>0)) {
 	 		return "redirect:/admin/viewStudents?error='Invalid Student Email'&courseId="+course.getCourseId();
 	 	}
 	 	//TODO : Add Students in Class Set and update class Id for Student
 	 	person.getCourses().add(course);
 	 	course.getPersons().add(person);
 	 	personRepository.save(person);
 	 	session.setAttribute("currCourse", course);
 	 	//courseRepository.save(course);
 	 	return "redirect:/admin/viewStudents?courseId="+course.getCourseId();
 	 }
 	 
 	@RequestMapping("/deleteStudentFromCourse")
 	public String deleteStudentFromCourse(@RequestParam int personId,HttpSession session) {
 		
 		Course course = (Course) session.getAttribute("currCourse");
        Optional<Person> person = personRepository.findById(personId);
        
        //System.out.println(course.getCourseName()+" "+person.get());
        Set<Course> newCourses = new HashSet<>(person.get().getCourses().stream().filter(c->!(c.getCourseId() == course.getCourseId())).toList());
        //Update Courses Set of Person and remove the course and re save if 
        //this will intern update persons list of Course Class
        //it works as it is mapped to course of Person class
        person.get().setCourses(newCourses);
        personRepository.save(person.get());
        
        session.setAttribute("currCourse",course);
        
        return "redirect:/admin/viewStudents?courseId="+course.getCourseId();
 	}	 
 	
}
