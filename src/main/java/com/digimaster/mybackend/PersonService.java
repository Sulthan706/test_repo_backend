package com.digimaster.mybackend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonService {
	private PersonRepository personRepository;
	
	private final Path root = Paths.get("C://Users/PC Gaming/Documents/Backend/images");
	
	public void saveFile(MultipartFile file)
	{
		try {
			if(Files.exists(root)){
				Files.createDirectories(root);
			}
			
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveFile(MultipartFile file, int id)
	{
		try {
			if(Files.exists(root)){
				Files.createDirectories(root);
			}
			
			Optional<PersonModel> currentPerson = personRepository.findById(id);
			if(currentPerson.isPresent()) {
				currentPerson.get().setProfile(file.getOriginalFilename());
				personRepository.save(currentPerson.get());
				Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	public Iterable<PersonModel> createPerson(Iterable<PersonModel> personModel) {
		return personRepository.saveAll(personModel);
	}
	
	public PersonModel createPerson(PersonModel personModel) {
		return personRepository.save(personModel);
	}
	
	public PersonModel getPerson(int id)
	{
		return personRepository.findById(id).get();
	}
	
	public PersonModel getPersonWithValidation(int id)
	{
		if(personRepository.findById(id).isPresent()) {
			return personRepository.findById(id).get();
		}else {
			return null;
		}
	}
	
	public PersonModel getPerson(String name)
	{
		return personRepository.getPersonModelByName(name);
	}
	
	public PersonModel getPerson(String name,String city)
	{
		return personRepository.getPersonModelByNameAndCity(name,city);
	}
	
	public Iterable<PersonModel> getAllPersons()
	{
		return personRepository.findAll();
	}
	
	public Iterable<PersonModel> getPersonByCity(String city)
	{
		return personRepository.getPersonModelByCity(city);
	}
	
	public PersonModel updatePerson(PersonModel personModel,int id)
	{
		Optional<PersonModel> currentPerson = personRepository.findById(id);
		if(currentPerson.isPresent()) {
			currentPerson.get().setAge(personModel.getAge());
			currentPerson.get().setCity(personModel.getCity());
			currentPerson.get().setName(personModel.getName());
			
			return personRepository.save(currentPerson.get());
		}else {
			return personModel;
		}
	}
	
	public boolean deletePerson(int id)
	{
		personRepository.deleteById(id);
		return true;
	}
	
	public void deletePersonByName(String name)
	{
		personRepository.deletePersonModelByName(name);
	}
}	
