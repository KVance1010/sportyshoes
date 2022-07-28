$(document).ready(function() {
	$("#buttonAdd2Cart").on("click", function(evt) {
		addToCart();
	});
});

function addToCart() {
	quantity = $("#quantity" + productId).val();
	url = "/cart/add/" + productId + "/" + quantity;
	alert(url);
	//	$.ajax({
	//		type: "POST",
	//		url: url,
	//		beforeSend: function(xhr) {
	//			xhr.setRequestHeader(csrfHeaderName, csrfValue);
	//		}
	//	}).done(function(response) {
	//		showModalDialog("Shopping Cart", response);
	//	}).fail(function() {
	//		showErrorModal("Error while adding product to shopping cart.");
	//	});
}