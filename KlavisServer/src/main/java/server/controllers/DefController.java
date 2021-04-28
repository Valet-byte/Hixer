package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import server.service.DefService;

@Controller
public class DefController {

    @Autowired
    DefService service;

    @RequestMapping("/putStat")
    @ResponseBody
    public Integer putStat(@RequestParam("numChapter") Integer numChapter,
                          @RequestParam("numSubchapter") Integer numSubchapter,
                           @RequestParam("info") String str){
        return service.putStat(numChapter, numSubchapter, str);
    }

    @RequestMapping("/getStat")
    @ResponseBody
    public String getStat(@RequestParam("numChapter") Integer numChapter,
                          @RequestParam("numSubchapter") Integer numSubchapter){
        return service.getStat(numChapter, numSubchapter);
    }
}