package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.model.Person;
import server.service.UploadService;
import server.service.UsersUtilService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
                          @RequestParam("names") String[] names,
                          @RequestParam("texts") String[] texts,
                          @RequestParam MultipartFile... file){
        System.out.println(s);
        System.out.println(Arrays.toString(names));
        System.out.println(Arrays.toString(texts));
        uploadService.upload(file);
        return 1;
    }
}
