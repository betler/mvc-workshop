package es.altia.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {

	@Override
	public String greetAdmin() {
		return "Hi, admin! Today is " + new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

}
