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
public class MovieService {
	private MovieRepository movieRepository;
	
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
			
			Optional<MovieModel> currentMovie = movieRepository.findById(id);
			if(currentMovie.isPresent()) {
				currentMovie.get().setProfile(file.getOriginalFilename());
				movieRepository.save(currentMovie.get());
				Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MovieService(MovieRepository movieRepository)
	{
		this.movieRepository = movieRepository;
	}
	
	public MovieModel createMovie(MovieModel movieModel) {
		return movieRepository.save(movieModel);
	}
	
	public Iterable<MovieModel> createMovie(Iterable<MovieModel> movieModel) {
		return movieRepository.saveAll(movieModel);
	}
	
	public MovieModel getMovie(int id)
	{
		return movieRepository.findById(id).get();
	}
	
	public MovieModel getMovieWithValidation(int id)
	{
		if(movieRepository.findById(id).isPresent()) {
			return movieRepository.findById(id).get();
		}else {
			return null;
		}
	}
	
	public MovieModel getMovie2(String title,String genre)
	{
		return movieRepository.getMovieModelByTitleAndGenre(title,genre);
	}
	
	public MovieModel getMovie(String release_year,String genre)
	{
		return movieRepository.getMovieModelByReleaseYearAndGenre(release_year,genre);
	}
	
	public Iterable<MovieModel> getAllMovies()
	{
		return movieRepository.findAll();
	}
	
	public Iterable<MovieModel> getMovieByGenre(String genre)
	{
		return movieRepository.getMovieModelByGenre(genre);
	}
	
	public MovieModel updateMovie(MovieModel movieModel,int id)
	{
		Optional<MovieModel> currentMovie = movieRepository.findById(id);
		if(currentMovie.isPresent()) {
			currentMovie.get().setGenre(movieModel.getGenre());
			currentMovie.get().setReleaseYear(movieModel.getReleaseYear());
			currentMovie.get().setTitle(movieModel.getTitle());
			
			return movieRepository.save(currentMovie.get());
		}else {
			return movieModel;
		}
		
	}
	
	public boolean deleteMovie(int id)
	{
		movieRepository.deleteById(id);
		return true;
	}
	
}
