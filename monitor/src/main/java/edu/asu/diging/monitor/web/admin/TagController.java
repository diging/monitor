package edu.asu.diging.monitor.web.admin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.diging.monitor.core.model.impl.Tag;
import edu.asu.diging.monitor.core.service.ITagManager;

@Controller
@RequestMapping("/admin/apps/tags")
public class TagController {
    
    @Autowired
    private ITagManager tagManager;
    
    @RequestMapping(value = "/getTagList",  method = RequestMethod.GET)
    public @ResponseBody List<Tag> getTagList(@RequestParam("term") String query) {
//        List<String> tagNames = Arrays.asList("tag1", "tag2", "tag3", "tag4", "tag5");
//        List<Tag> tags = 
//        
//        Tag temp = new Tag();
//        temp.setName("tag1");
//        tags.add(temp);
//        
//        List<Tag> matchingTags = tags.stream().filter(tag ->tag.getName().contains(query)).collect(Collectors.toList());
//        
//        return matchingTags;
        List<Tag> tagList = tagManager.getTagList(query);
        return tagList;
    }
    
    @RequestMapping(value = "/addTag", method = RequestMethod.POST)
    public void add(@RequestParam String tagName, RedirectAttributes redirectAttributes) {
        // What to add here?
        
    }
}
