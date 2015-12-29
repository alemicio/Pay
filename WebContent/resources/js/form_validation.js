function validateForm() {
	
	var username = document.getElementById("change-detail-form:username").value;
	var password = document.getElementById("change-detail-form:password").value;
	var name = document.getElementById("change-detail-form:name").value;
	var surname = document.getElementById("change-detail-form:surname").value;
	var email = document.getElementById("change-detail-form:email").value;
	var phoneNumber = document.getElementById("change-detail-form:phonenumber").value;
	var postalAddress = document.getElementById("change-detail-form:postaladdress").value;
	
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
		
	} else if (phoneNumber.length === 0) {
		alert("Phone Number field must be not empty.");
		return false;
		
	} else if (postalAddress.length === 0) {
		alert("Postal Address field must be not empty.");
		return false;
	}
	
	return true;
}