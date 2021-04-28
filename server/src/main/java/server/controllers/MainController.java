package server.controllers;

import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.model.Person;
import server.model.ServerModel;
import server.service.UploadService;
import server.service.UsersUtilService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    UploadService uploadService;
    @Autowired
    UsersUtilService usersUtilService;

    MessageDigest digest;
    {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/findUser")
    @ResponseBody
    public Person findUser(@RequestParam("name") String name, @RequestParam("pass") String pass){
        return usersUtilService.findUser(name, pass);
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public Integer addUser(@RequestParam("name") String name, @RequestParam("pass") String pass){

        StringBuilder builder = new StringBuilder();

        byte[] a = digest.digest(pass.getBytes());
        for (byte b : a) {
            builder.append(b);
        }

        return usersUtilService.addUser(name, builder.toString());
    }

    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public Integer upload(@RequestParam("description") String s,
                          @RequestParam("dates") List<ServerModel> models){
        System.out.println(s);
        return 1;
    }
}
