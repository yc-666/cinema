package org.ltc.cinema.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.ibatis.annotations.Delete;
import org.ltc.cinema.common.vo.CinemaResult;
import org.ltc.cinema.entity.*;
import org.ltc.cinema.service.MovieService;
import org.ltc.cinema.entity.Card;
import org.ltc.cinema.entity.Movie;
import org.ltc.cinema.entity.PageResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zrk
 * @version 1.0
 * @date 2020/5/1 0001 11:45
 */
@CrossOrigin
@RestController
public class MovieController {
    @Resource
    MovieService movieService;
    /**
     * //添加电影票数据，需要返回电影票全部数据，因为电影票id需要服务端生成
     * export const insertMovie = query=>{
     *     return request({
     *         url:'insertMovie',
     *         method:'post',
     *         params:query
     *     })
     * };
     */
    @ApiOperation(value = "增加一部电影")
    @ApiImplicitParams({
        @ApiImplicitParam(name="movie",value="电影信息",
                required = true,paramType = "body", dataType = "Movie")
    })
    @ApiResponses({
        @ApiResponse(code = 200,message = "增加成功"),
        @ApiResponse(code = 400,message = "增加失败"),
    })
    @PostMapping("/movie/{name}")
    public CinemaResult insertMovie(Movie movie){
        return movieService.insertMovie(movie);

    }
    /**
     *  //获取电影票数据，请求参数：pageIndex，pageSize，返回电影列表和总记录数
     * export const movieData = query =>{
     *     return request({
     *         url:'getMovie',
     *         method:'get',
     *         params:query
     *     });
     * };
     */
    @ApiOperation(value = "获取电影数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name="pageIndex",value="分页开始位置",
            required = true,paramType = "body", dataType = "String"),
        @ApiImplicitParam(name="pageSize",value="分页大小",
            required = true,paramType = "body", dataType = "String")
    })
    @ApiResponses({
        @ApiResponse(code = 200,message = "获取成功"),
        @ApiResponse(code = 400,message = "获取失败"),
    })
    @GetMapping("movieData")
    public CinemaResult getMovie(String pageIndex,String pageSize){
        //这里使用分页插件pagehelper
        PageResult pageResult = new PageResult();
        PageHelper.startPage(Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
        List lists = movieService.getMovieData();
        PageInfo<Card> pageInfo = new PageInfo<>(lists);
        pageResult.setList(pageInfo.getList());
        pageResult.setPageTotal(pageInfo.getTotal());
        return CinemaResult.success(pageResult);
    }
    /**
     * //删除电影数据，请求参数：movieId
     * export const delMovie = query=>{
     *     return request({
     *         url:'delMovie',
     *         method:'get',
     *         params:query
     *     });
     * };

     */
    @ApiOperation(value = "删除电影数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name="movieId",value="电影Id",
            required = true,paramType = "body", dataType = "String"),
    })
    @ApiResponses({
        @ApiResponse(code = 200,message = "删除成功"),
        @ApiResponse(code = 400,message = "删除失败"),
    })
    @DeleteMapping("/movie/{movieId}")
    public CinemaResult delMovie(String movieId){
        movieService.delMovie(movieId);
        return CinemaResult.success();
    }
    /**
     *      * //修改电影数据，请求参数:movieId、name、price、time
     *      * export const modifyMovie = query=>{
     *      *     return request({
     *      *         url:'modifyMovie',
     *      *         method:'get',
     *      *         params:query
     *      *     });
     *      * };
     */
    @ApiOperation(value = "修改电影数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name="movie",value="电影信息",
            required = true,paramType = "body", dataType = "Movie"),
    })
    @ApiResponses({
        @ApiResponse(code = 200,message = "修改成功"),
        @ApiResponse(code = 400,message = "修改失败"),
    })
    @PutMapping("/movie/{movieId}")
    public CinemaResult modifyMovie(Movie movie){
        return movieService.modifyMovie(movie);
    }
}
/*
*
* //删除电影数据，请求参数：movieId
export const delMovie = query=>{
    return request({
        url:'delMovie',
        method:'get',
        params:query
    });
};
//修改电影数据，请求参数:movieId、name、price、time
export const modifyMovie = query=>{
    return request({
        url:'modifyMovie',
        method:'get',
        params:query
    });
};*/