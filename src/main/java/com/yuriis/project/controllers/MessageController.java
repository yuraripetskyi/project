package com.yuriis.project.controllers;

import com.yuriis.project.exceprions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;
    private List<Map<String, String>> messages = new ArrayList<>(){{
        add(new HashMap<>(){{
            put("id", "1");put("text", "First Message");
        }});
        add(new HashMap<>(){{
            put("id", "2");put("text", "Second Message");
        }});
        add(new HashMap<>(){{
            put("id", "3");put("text", "Third Message");
        }});
    }};
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, String>> list(){
        return messages;
    }
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Map<String, String> getOne(@PathVariable String id){
        return getMessage(id);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Map<String, String> create(@RequestBody Map<String, String> message){
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Map<String, String> update(@PathVariable String id,
                                      @RequestBody Map<String, String> message){
        Map<String, String> messageFromDB = getMessage(id);
        messageFromDB.putAll(message);
        messageFromDB.put("id",id);
        return messageFromDB;
    }
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id){
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }


    //--------------------------------
    //   Private methods
    //--------------------------------

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

}
