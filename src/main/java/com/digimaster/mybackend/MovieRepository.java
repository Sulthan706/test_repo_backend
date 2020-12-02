package com.digimaster.mybackend;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieModel, Integer> {
	MovieModel getMovieModelByTitleAndGenre(String title,String Genre);
	MovieModel getMovieModelByReleaseYearAndGenre(String release_year,String Genre);
	
	Iterable<MovieModel> getMovieModelByGenre(String Genre);
}
