package edu.asu.diging.monitor.web.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tags")
public class TagController {
    
    @Autowired
    private ITagManager tagManager;
    
    @RequestMapping(value = "/getTagList",  method = RequestMethod.GET)
    public @ResponseBody List getTagList(@RequestParam("term") String query) {
        List tagList = tagManager.getTagList(query);
        return tagList;
    }
}
