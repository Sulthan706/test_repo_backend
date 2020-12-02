package com.digimaster.mybackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private PersonService personService;
	
//	public ApiController(PersonService personService)
//	{
//		this.personService = personService;
//	} cara kedua cara paling simple pake autowired
	
	@GetMapping("/helloWorld")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/param")
	public String returnParam(@RequestParam String username) {
		return "Ini Params : "+ username;
	}
	
	@PostMapping("/post")
	public String postParam(@RequestBody String text) {
		return "Ini post Param : "+text;
	}
	
	@PostMapping("/postBody")
	public String postBody(@RequestBody PersonModel personModel) {
		return "Ini person : "+personModel.getName()+"|"+personModel.getCity();
	}
	
	@PostMapping("/create")
	public PersonModel createPerson(@RequestBody PersonModel personModel) {
		return personService.createPerson(personModel);
	}
	
	@PostMapping("/creates")
	public Iterable<PersonModel> createPerson(@RequestBody Iterable<PersonModel> personModel) {
		return personService.createPerson(personModel);
	}
	
	@GetMapping("/person/{id}")
	public PersonModel getPerson(@PathVariable int id)
	{
		return personService.getPerson(id);
	}
	
	@GetMapping("/personss")
	public BaseResponse<PersonModel> getPersonWithBaseResponse(@RequestParam int id)
	{
		PersonModel person = personService.getPersonWithValidation(id);
		BaseResponse<PersonModel> baseResponse = new BaseResponse<>();
		
		if(person != null)
		{
			baseResponse.setCode(200);
			baseResponse.setSuccess(true);
			baseResponse.setMessage("User Found.");
			baseResponse.setData(person);
		}else {
			baseResponse.setCode(404);
			baseResponse.setSuccess(false);
			baseResponse.setMessage("User Not Found.");
			baseResponse.setData(person);
		}
		
		return baseResponse;
	}
	
	@GetMapping("/person/name")
	public PersonModel getPersonByName(@RequestParam String name)
	{
		return personService.getPerson(name);
	}
	
	@GetMapping("/person/get")
	public PersonModel getPersonByNameAndCity(@RequestParam String name,@RequestParam String city)
	{
		return personService.getPerson(name, city);
	}
	
	@GetMapping("/persons")
	public Iterable<PersonModel> getPersons()
	{
		return personService.getAllPersons();
	}
	
	@GetMapping("/persons2")
	public PersonResponse<PersonModel> getPersonsWithResponse()
	{
		PersonResponse<PersonModel> personResponse = new PersonResponse<>();
		personResponse.setCode(200);
		personResponse.setSuccess(true);
		personResponse.setMessage("Usernya ada banyak.");
		personResponse.setData(personService.getAllPersons());
		
		return personResponse;
	}
	
	@GetMapping("/person/city")
	public Iterable<PersonModel> getPersonsByCity(@RequestParam String city)
	{
		return personService.getPersonByCity(city);
	}
	
	@PutMapping("/person/{id}")
	public PersonModel updatePerson(@PathVariable int id, @RequestBody PersonModel personModel)
	{
		return personService.updatePerson(personModel, id);
	}
	
	@DeleteMapping("/person/{id}")
	public boolean deletePerson(@PathVariable int id)
	{
		return personService.deletePerson(id);
	}
	
	@DeleteMapping("/person/name/{name}")
	public boolean deletePersonByName(@PathVariable String name)
	{
		personService.deletePersonByName(name);
		return true;
	}
	
	@PostMapping("/file")
	public boolean uploadFile(@RequestParam("file") MultipartFile file) 
	{
		personService.saveFile(file);
		return true;
	}
	
	@PostMapping("/file/id")
	public boolean uploadFile(@RequestParam("file") MultipartFile file,@RequestParam int id) 
	{
		personService.saveFile(file,id);
		return true;
	}
	
}
