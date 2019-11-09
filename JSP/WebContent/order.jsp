<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
		<link rel="stylesheet" href="./css/style.css">
	</head>
	
	<body>
	 <div class="container">
        <h1>Thanks for your order. Here it is: </h1>
        <h3>( Small: $1.20 / Medium: $1.50 / Large: $1.80 / Extra large: $2.00 )</h3>
        <div class="body" id="body">
        </div>
        <div class="price" id="price"></div>
    </div>
<%
    String action = request.getParameter("action");
    int numbers = Integer.parseInt(request.getParameter("numbers"));
    String size = request.getParameter("size");
    int creams = 0;
    int sugars = 0;
	String slang = "";
    
    if (action == "form1") {
        creams = Integer.parseInt(request.getParameter("creams"));
        sugars = Integer.parseInt(request.getParameter("sugars"));
    } else {
        slang = request.getParameter("slang"); 
    }
%>
    <script>
        var action = "<%= action %>";
        var numbers = "<%= numbers %>";
        var size = "<%= size%>";
        var creams = 0;
        var sugars = 0;
        var slang = "";

        if (action === "form1") {
            creams = "<%= creams %>";
            sugars = "<%= sugars %>";
        } else {
            slang = "<%= slang %>";
            getInfoFromslang(slang);
        }

        // Make a order list by the numbers
        for (var i = 0; i < numbers; i++) {
            makeDivOrder(size, creams, sugars);
        }

        // Make a price
        makePrice(size, numbers);

        // function (make a price)
        function makePrice(size, numbers) {
            var price = 0;
            if (size === "small") {
                price = 1.2;
            } else if (size === "medium") {
                price = 1.5;
            } else if (size === "large") {
                price = 1.8;
            } else if (size === "exLarge") {
                price = 2.0;
            } else {
                price = 1.2;
            }

            var total = "Cost: $" + price.toFixed(2) + " x " + numbers + " + tax = $" + (price * 1.13 * numbers).toFixed(2);
            document.querySelector("#price").innerHTML = total;
        }

        // function (get information about creams and sugars from the slang)
        function getInfoFromslang(slang) {
            switch (slang) {
                case "r":
                    creams = 1;
                    sugars = 1;
                    break;
                case "dd":
                    creams = 2;
                    sugars = 2;
                    break;
                case "tt":
                    creams = 3;
                    sugars = 3;
                    break;
                case "b":
                    creams = 0;
                    sugars = 0;
                    break;
                case "bos":
                    creams = 0;
                    sugars = 1;
                    break;
                case "bts":
                    creams = 0;
                    sugars = 2;
                    break;
                case "bths":
                    creams = 0;
                    sugars = 3;
                    break;
            }
        }

        // function (Make a order element from the size, creams and sugars)
        function makeDivOrder(size, creams, sugars) {
            var div = document.createElement("div");
            div.classList.add("order");

            div = addCoffee(div, size);
            div = addStuff(div, sugars, "sugar");
            div = addStuff(div, creams, "cream");

            var order = document.querySelector("#body");
            order.appendChild(div);
        }

        // function (when making an order element, adding a coffee element)
        function addCoffee(div, size) {
            var img = document.createElement("img");
            img.classList.add(size);
            img.setAttribute("src", "./images/cup.jpg");
            div.append(img);
            return div;
        }

        // function (when making an order element, adding cream and sugar elements)
        function addStuff(div, number, stuff) {
            if (number != 0) {
                var img = document.createElement("img");
                img.setAttribute("src", "./images/plus.jpg");
                div.append(img);

                while (number > 0) {
                    var img = document.createElement("img");
                    img.setAttribute("src", "./images/" + stuff + ".jpg");
                    div.append(img);
                    number--;
                }
            }
            return div;
        }
    </script>
	</body>
</html>