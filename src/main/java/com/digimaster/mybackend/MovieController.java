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
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	private MovieService movieService;
	
	@PostMapping("/create")
	public MovieModel createMovie(@RequestBody MovieModel movieModel)
	{
		return movieService.createMovie(movieModel);
	}
	
	@PostMapping("/creates")
	public Iterable<MovieModel> createMovie(@RequestBody Iterable<MovieModel> movieModel)
	{
		return movieService.createMovie(movieModel);
	}
	
	@GetMapping("/{id}")
	public MovieModel getMovie(@PathVariable int id)
	{
		return movieService.getMovie(id);
	}
	
	@GetMapping("/moviess")
	public BaseResponse<MovieModel> getMovieWithBaseResponse(@RequestParam int id)
	{
		MovieModel moviee = movieService.getMovieWithValidation(id);
		BaseResponse<MovieModel> baseResponse = new BaseResponse<>();
		
		if(moviee != null)
		{
			baseResponse.setCode(200);
			baseResponse.setSuccess(true);
			baseResponse.setMessage("Film Found.");
			baseResponse.setData(moviee);
		}else {
			baseResponse.setCode(404);
			baseResponse.setSuccess(false);
			baseResponse.setMessage("Film Not Found.");
			baseResponse.setData(moviee);
		}
		
		return baseResponse;
	}
	
	@GetMapping("/get")
	public MovieModel getMovieModelByTitleAndGenre(@RequestParam String title,@RequestParam String genre)
	{
		return movieService.getMovie2(title, genre);
	}
	
	@GetMapping("/release")
	public MovieModel getMovieModelByReleaseYearAndGenre(@RequestParam String release_year,@RequestParam String genre)
	{
		return movieService.getMovie(release_year, genre);
	}
	
	@GetMapping("/allmovie")
	public Iterable<MovieModel> getMovies()
	{
		return movieService.getAllMovies();
	}
	
	@GetMapping("/genre")
	public Iterable<MovieModel> getMovieByGenre(@RequestParam String genre)
	{
		return movieService.getMovieByGenre(genre);
	}
	
	@PutMapping("/update/{id}")
	public MovieModel updateMovie(@PathVariable int id, @RequestBody MovieModel movieModel)
	{
		return movieService.updateMovie(movieModel, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deleteMovie(@PathVariable int id)
	{
		return movieService.deleteMovie(id);
	}
	
	@PostMapping("/file")
	public boolean uploadFile(@RequestParam("file") MultipartFile file) 
	{
		movieService.saveFile(file);
		return true;
	}
	
	@PostMapping("/file/id")
	public boolean uploadFile(@RequestParam("file") MultipartFile file,@RequestParam int id) 
	{
		movieService.saveFile(file,id);
		return true;
	}
	

}
