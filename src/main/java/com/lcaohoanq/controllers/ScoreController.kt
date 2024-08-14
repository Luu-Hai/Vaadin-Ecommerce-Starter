package com.lcaohoanq.controller

import com.lcaohoanq.entity.Score
import com.lcaohoanq.repository.ScoreRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["\${v1API}/scores"])
class ScoreController(private val scoreRepository: ScoreRepository) {

//    @GetMapping("")
//    fun getScore(): MutableList<Score> {
//        return scoreRepository.findAll();
//    }

}