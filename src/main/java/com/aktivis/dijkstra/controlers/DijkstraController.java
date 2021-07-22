package com.aktivis.dijkstra.controlers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aktivis.dijkstra.form.DijkstraForm;
import com.aktivis.dijkstra.service.DijkstraService;

@Controller
public class DijkstraController {

	@Autowired
	private DijkstraService dijkstraService;
	
	
	@PostMapping(path = "/calculate")
	@ResponseBody
    public String calculate(Model model, @Valid @ModelAttribute("dijkstraForm") DijkstraForm dijkstraForm, BindingResult bindingResult) {	
		return dijkstraService.calculate(dijkstraForm.getIncidenceMatrix(), dijkstraForm.getWeights(), dijkstraForm.getStartVertex(), dijkstraForm.getEndVertex());
    }
	
	@GetMapping(path = "/dijkstra")
    public String displayForm(Model model) {
		model.addAttribute("dijkstraForm", new DijkstraForm()); 
        return "dijkstra";
    }
	
}
