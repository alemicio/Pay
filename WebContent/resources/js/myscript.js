$(document).ready(function() {
 
	var micio = $("unionName").Text;
	
	if ( micio == "Not setted") {
		$("p").Text("merda lurida");
	}else {
		 $( "p" ).Text(micio);
	}
 });