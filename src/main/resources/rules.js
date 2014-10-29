//-------------------------------
// rules.js
//-------------------------------
println ("in rules.js");

/*
 * 
 */
function getEntityName(tableName) {
	//var str = tableName ;
	return tableName.toLowerCase();
}

/*
 * 
 */
function isOK(tableName) {
	if ( tableName == "BOOK" ) {
		return true ;
	}
	else {
		return false ;
	}
}

println ("end of rules.js");
