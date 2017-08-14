package com.shsxt.crm.controller;

import com.shsxt.crm.model.User;
import com.shsxt.crm.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Integer id) {
        User byId = testService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("User", byId);
        return map;

    }

    @PostMapping("/find")
    public Map<String, Object> get() {
        List<User> users = testService.find();
        Map<String, Object> map = new HashMap<>();
        map.put("Users", users);
        return map;

    }


}
