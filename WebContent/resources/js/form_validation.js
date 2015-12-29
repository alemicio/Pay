function validateForm() {
	
	var username = document.getElementById("change-detail-form:username").value;
	var password = document.getElementById("change-detail-form:password").value;
	var name = document.getElementById("change-detail-form:name").value;
	var surname = document.getElementById("change-detail-form:surname").value;
	var email = document.getElementById("change-detail-form:email").value;
	var phoneNumber = document.getElementById("change-detail-form:phonenumber").value;
	var postalAddress = document.getElementById("change-detail-form:postaladdress").value;
	
	// This is the regular expression used to check the email's validity
	var regExp = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
	if(username.length === 0) {
		alert("Username field must be not empty.");
		return false;
		
	} else if (password.length === 0) {
		alert("Password field must be not empty.");
		return false;
		
	} else if (name.length === 0) {
		alert("Name field must be not empty.");
		return false;
		
	} else if (surname.length === 0) {
		alert("Surname field must be not empty.");
		return false;
		
	} else if (email.length === 0) {
		alert("Email field must be not empty.");
		return false;
		
	} else if ( !(regExp.test(email)) ) {
		alert("Invalid email format");
		return false;
		
	} else if (phoneNumber.length === 0) {
		alert("Phone Number field must be not empty.");
		return false;
		
	} else if (postalAddress.length === 0) {
		alert("Postal Address field must be not empty.");
		return false;
	}
	
	return true;
}