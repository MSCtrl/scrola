// Handles the searching and shortlisting of the item list
function search(input) {
  document.getElementById("listing-orders").innerHTML = "";
  var xmldata = $.parseXML(document.getElementById("items-xml").innerHTML);
  var item = xmldata.getElementsByTagName("item");
  for (var i=0; i<item.length; i++) {
    var a = xmldata.getElementsByTagName("title")[i].childNodes[0].nodeValue;
    if (a.toUpperCase().indexOf(input.toUpperCase()) != -1) {
      document.getElementById("listing-orders").innerHTML += '<li><a href="#' + a + '">' + a + '</a></li>';
    }
  }
  if (document.getElementById("listing-orders").innerHTML == "") {
    document.getElementById("listing-orders").innerHTML = '<li><a href="#">No suggestion</a></li>';
  }
}

function generateItemList() {
  // Parse the XML on the HTML page.
  var xmldata = $.parseXML(document.getElementById("items-xml").innerHTML);
  var item = xmldata.getElementsByTagName("item");
  // Iterate the title until finish
  for (var i=0; i<item.length; i++) {
    var a = xmldata.getElementsByTagName("title")[i].childNodes[0].nodeValue;
    document.getElementById("listing-orders").innerHTML += "<li><a href='#" + a + "'>" + a + "</a></li>";
  }
}
