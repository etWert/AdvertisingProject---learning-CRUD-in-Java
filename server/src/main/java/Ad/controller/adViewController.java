package Ad.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Ad.model.AdView;
import Ad.service.Iservice.IadViewService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class adViewController {

	@Autowired
	private IadViewService serv;

	@GetMapping("/ad/get")
	public ArrayList<AdView> getAds() {
		return serv.getAds();
	}

	@PostMapping("/ad/add")
	public boolean addAdView(@RequestBody AdView a) {
		return serv.add(a);
	}

	@PostMapping("/ad/{location}")
	public String isLocationAvailable(@PathVariable int location) {
		return serv.isLocationAvailable(location);
	}

}
