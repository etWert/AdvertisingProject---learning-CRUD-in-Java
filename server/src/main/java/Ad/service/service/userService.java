package Ad.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ad.dao.IuserRepository;
import Ad.model._User;
import Ad.service.Iservice.IuserService;

@Service
public class userService implements IuserService {

	@Autowired
	private IuserRepository rep;

	@Override
	public _User findUser(String email, String password) {
		_User u = rep.findById(email).get();
		if (u != null && u.getPassword().equals(password)) {
			return u;
		}
		return null;
	}
	
	@Override
	public boolean addUser(_User u) {
		if (rep.existsById(u.getEmail())) {
			return false;
		}
		rep.save(u);
		return true;
	}

}
