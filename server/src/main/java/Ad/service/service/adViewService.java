package Ad.service.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Ad.dao.IadViewRepository;
import Ad.model.AdView;
import Ad.service.Iservice.IadViewService;

@Service
public class adViewService implements IadViewService {

	@Autowired
	private IadViewRepository rep;

	@Override
	public ArrayList<AdView> getAds() {
		ArrayList<AdView> ads = new ArrayList<>();
		rep.findAll().forEach(ad -> {
			if (ad.getCurrentCount() >= ad.getMaxCount()) {
				ad.setStatus(false);
			} else {
				// הוספת כניסה
				ad.setCurrentCount(ad.getCurrentCount() + 1);
				//הוספת הפרסומת לרשימה המוחזרת של הפרסומות
				ads.add(ad);
			}
		});
		if (ads != null) {
			return ads;
		}
		return null;
	}

	@Override
	public boolean add(AdView a) {
		if (rep.existsById(a.getId())) {
			return false;
		}
		//הערה במסמך הסבר המצורף
		//זה רלוונטי רק כאשר מוסיפים בפוסטמן כי בקליינט אין אפשרות בכלל להוסיף פרסומות במיקום תפוס
		if (isLocationAvailable(a.getLocation()) != "") {
			return false;
		}

		a.setCurrentCount(0);
		rep.save(a);
		return true;
	}

	@Override
	public String isLocationAvailable(int location) {
		// הפונקציה בודקת האם המקום פנוי או תפוס
		// פנוי - מחזירה מחרוזת ריקה
		// תפוס - מחזירה את שם התמונה שמוצגת
		ArrayList<AdView> ads = this.getAds();
		for (AdView a : ads) {
			// אם יש פרסומת פעילה
			if (a.getLocation() == location && a.getStatus() == true) {
				// נבדוק האם מספר הכניסות הגיע למקסימום
				if (a.getCurrentCount() >= a.getMaxCount()) {
					a.setStatus(false);
					return "";// התמונה מפסיקה להיות מוצגת
				}
				// אם המקום תפוס, פעיל, ועדיין לא הגיע למס צפיות מקסימלי
				return a.getImage();
			}
		}
		// אם המיקום פנוי
		return "";
	}
}
