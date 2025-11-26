<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="co.edu.konradlorenz.model.*" %>
<%@ page import="co.edu.konradlorenz.model.dao.*" %>
<%@ page import="co.edu.konradlorenz.model.enums.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SuperLorenz</title>
    <style>
        /* General Styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #9f87a7;
            color: white;
            padding: 10px 20px;
        }
        .header .logo {
            font-size: 1.5em;
            font-weight: bold;
            display: flex;
            align-items: center;
        }
        .header .logo img {
            width: 60px;
            height: 60px;
            margin-right: 10px;
        }
        .header .options {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        .header .options a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }
        .header .options a:hover {
            text-decoration: underline;
        }
        .container {
            display: flex;
            justify-content: center;
            padding: 20px;
            background-color: #ead8ee;
        }
        .products, .cart {
            background-color: #663366;
            color: white;
            padding: 20px;
            margin: 10px;
            border-radius: 10px;
        }
        .products {
            width: 60%;
        }
        .product {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            background-color: #9f87a7;
            padding: 10px;
            border-radius: 8px;
        }
        .product .image-container {
            width: 70px;
            height: 70px;
            margin-right: 15px;
            overflow: hidden;
        }
        .product .image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .cart {
            width: 30%;
        }
        .cart ul {
            list-style-type: disc;
            padding-left: 20px;
        }
        .cart .total {
            margin-top: 20px;
            font-weight: bold;
        }
        .cart button {
            background-color: #9f87a7;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .cart button:hover {
            background-color: #7b5e8b;
        }
        .add-button {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .add-button:hover {
            background-color: #45a049;
        }
        .add-button:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="logo">
            <img src="imagenes/LogoKonrad.png" alt="SuperLorenz Logo">
            <span>SuperLorenz</span>
        </div>
        <div class="options">
            <a href="#">Carrito</a>
            <span>¡Hola Pepito!</span>
            <a href="#">Cerrar Sesión</a>
        </div>
    </div>
    <div class="container">
        <div class="products">
            <div class="product">
                <div class="image-container">
                    <!-- Imagen T.V. en lugar del hexágono -->
                    <img src="T.V.png" alt="TV">
                </div>
                <div>
                    <strong>Producto número 1:</strong>
                    <p>c/u $0.00</p>
                    <button class="add-button" id="addProduct1" onclick="addProduct(1)">Añadir más productos</button>
                    <p id="countProduct1">Cantidad seleccionada: 0</p>
                </div>
            </div>
            <div class="product">
                <div class="image-container">
                    <!-- Imagen Fruta en lugar del hexágono -->
                    <img src="fruta.png" alt="Fruta">
                </div>
                <div>
                    <strong>Producto número 2:</strong>
                    <p>c/u $0.00</p>
                    <button class="add-button" id="addProduct2" onclick="addProduct(2)">Añadir más productos</button>
                    <p id="countProduct2">Cantidad seleccionada: 0</p>
                </div>
            </div>
            <div class="product">
                <div class="image-container">
                    <!-- Imagen Iphone en lugar del hexágono -->
                    <img src="iphone.png" alt="iPhone">
                </div>
                <div>
                    <strong>Producto número 3:</strong>
                    <p>c/u $0.00</p>
                    <button class="add-button" id="addProduct3" onclick="addProduct(3)">Añadir más productos</button>
                    <p id="countProduct3">Cantidad seleccionada: 0</p>
                </div>
            </div>
        </div>
        <div class="cart">
            <ul>
                <li>Producto n - [xCantidad]</li>
                <li>Producto n - [xCantidad]</li>
                <li>Producto n - [xCantidad]</li>
                <li>Producto n - [xCantidad]</li>
            </ul>
            <div class="total">
                Valor Total = $0.00
            </div>
            <button>Guardar</button>
        </div>
    </div>

    <script>
        let productCount = {
            1: 0,
            2: 0,
            3: 0
        };

        function addProduct(productId) {
            if (productCount[productId] < 10) {
                productCount[productId]++;
                document.getElementById('countProduct' + productId).innerText = 'Cantidad seleccionada: ' + productCount[productId];
                if (productCount[productId] >= 10) {
                    document.getElementById('addProduct' + productId).disabled = true;
                }
            }
        }
    </script>
</body>
</html>
