package dbchubreast_web.form;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FormPasswordEncoder {

	@NotEmpty(message = "Ne peut pas être vide")
    @Length(min = 8, message = "8 caractères au moins")
	String clearpass;
	
	
	List<String> hashedpass = new ArrayList<String>();
	
	public List<String> getHashedpass() {
		return hashedpass;
	}

	public void setHashedpass(List<String> hashedpass) {
		this.hashedpass = hashedpass;
	}

	public String getClearpass() {
		return clearpass;
	}

	public void setClearpass(String clearpass) {
		this.clearpass = clearpass;
	}

	public void generate(int nb) {
		hashedpass.clear();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		for (int i=0; i<nb; i++) {
			hashedpass.add(passwordEncoder.encode(this.clearpass));
		}
	}
	
	public void clear() {
		hashedpass.clear();
	}

	@Override
	public String toString() {
		return "FormPasswordEncoder [clearpass=" + clearpass + "]";
	}



}
