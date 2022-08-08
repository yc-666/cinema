package org.ltc.cinema.service.impl;

import org.ltc.cinema.mapper.MovieMapper;
import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.Movie;
import org.ltc.cinema.service.MovieService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 11:45
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Resource
    MovieMapper movieMapper;
    @Override
    public CinemaResult insertMovie(Movie movie) {
        movieMapper.insertMovie(movie);
        return CinemaResult.success();
    }

    @Override
    public List<Movie> getMovieData() {
        return movieMapper.selectAllMovie();

    }

    @Override
    public void delMovie(String movieId) {
        movieMapper.deleteMovieById(movieId);
    }

    @Override
    public CinemaResult modifyMovie(Movie movie) {
        movieMapper.updateMovieById(movie);
        return CinemaResult.success();
    }
}
