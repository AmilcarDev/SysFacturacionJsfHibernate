-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-09-2020 a las 04:31:39
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `facturacion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `codCliente` int(11) NOT NULL,
  `nombres` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(150) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`codCliente`, `nombres`, `apellidos`, `direccion`) VALUES
(2, 'CARLOS ANTONIO', 'SERMEÑO AGUILAR', 'SANTA ANA'),
(3, 'SANDRA', 'BONILLA', 'SAN SALVADOR'),
(4, 'JOSUE', 'COREAS', 'SAN MIGUEL'),
(5, 'MARIA ', 'ARTERO GOMEZ', 'SAN SALVADOR'),
(6, 'juan carlos', 'aguirre', 'col. el palmar'),
(7, 'Moises', 'Cortez', 'Cojutepeque'),
(8, 'Elizabet', 'Amaya', 'Santo Domingo'),
(9, 'gris', 'guzman', 'san vicente'),
(10, 'modificacion', 'prueba', 'ok'),
(13, 'davo', 'cheto', 'cojute'),
(14, 'otro', 'otroo', 'otrooo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallefactura`
--

CREATE TABLE `detallefactura` (
  `codDetalle` int(11) NOT NULL,
  `codFactura` int(11) NOT NULL,
  `codProducto` int(11) NOT NULL,
  `codBarra` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `nombreProducto` varchar(75) COLLATE utf8_spanish_ci NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precioVenta` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `detallefactura`
--

INSERT INTO `detallefactura` (`codDetalle`, `codFactura`, `codProducto`, `codBarra`, `nombreProducto`, `cantidad`, `precioVenta`, `total`) VALUES
(1, 1, 1, '10101010', 'TECLADO USB', 10, '6.55', '65.50'),
(2, 2, 5, '10101014', 'LAMPARA CF', 2, '75.00', '150.00'),
(3, 3, 3, '10101012', 'PANTALLA LED 24 °', 2, '250.30', '500.60'),
(4, 3, 9, '11121415', 'Disco SSD', 1, '40.00', '40.00'),
(5, 3, 1, '10101010', 'TECLADO USB', 3, '6.55', '19.65'),
(6, 3, 9, '11121415', 'Disco SSD', 6, '40.00', '240.00'),
(7, 4, 1, '10101010', 'TECLADO USB', 40, '6.55', '262.00'),
(8, 5, 6, '10101015', 'MEMORIA RAM DDR3', 1, '20.00', '20.00');

--
-- Disparadores `detallefactura`
--
DELIMITER $$
CREATE TRIGGER `trgActualizarStock` BEFORE INSERT ON `detallefactura` FOR EACH ROW BEGIN
    SET @stockAntiguo =(SELECT stockActual FROM producto WHERE codProducto=new.codProducto);
	UPDATE producto
	SET stockActual=@stockAntiguo-new.cantidad WHERE codProducto=new.codProducto;
  END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `codFactura` int(11) NOT NULL,
  `numeroFactura` int(11) NOT NULL,
  `codVendedor` int(11) NOT NULL,
  `codCliente` int(11) NOT NULL,
  `totalVenta` decimal(10,2) NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`codFactura`, `numeroFactura`, `codVendedor`, `codCliente`, `totalVenta`, `fechaRegistro`) VALUES
(1, 1, 2, 2, '65.50', '2020-06-21 17:53:06'),
(2, 2, 2, 3, '150.00', '2020-06-21 17:53:06'),
(3, 3, 2, 6, '800.25', '2020-06-21 17:53:06'),
(4, 4, 2, 2, '262.00', '2020-06-21 17:53:06'),
(5, 5, 2, 5, '20.00', '2020-06-21 17:53:06');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `codProducto` int(11) NOT NULL,
  `nombreProducto` varchar(75) COLLATE utf8_spanish_ci NOT NULL,
  `precioVenta` decimal(10,2) NOT NULL,
  `stockMinimo` int(11) NOT NULL,
  `stockActual` int(11) NOT NULL,
  `codBarra` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`codProducto`, `nombreProducto`, `precioVenta`, `stockMinimo`, `stockActual`, `codBarra`) VALUES
(1, 'TECLADO USB', '6.55', 10, 3, '10101010'),
(3, 'PANTALLA LED 24 °', '250.30', 10, 94, '10101012'),
(4, 'CASE ATX', '20.50', 10, 46, '10101013'),
(5, 'LAMPARA CF', '75.00', 5, 5, '10101014'),
(6, 'MEMORIA RAM DDR3', '20.00', 15, 38, '10101015'),
(7, 'DICO DURO 300GB', '50.00', 25, 126, '10101016'),
(9, 'Disco SSD', '40.00', 12, 12, '11121415');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `codUsuario` int(11) NOT NULL,
  `nombreUsuario` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `codVendedor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`codUsuario`, `nombreUsuario`, `password`, `codVendedor`) VALUES
(1, 'BCAGUILAR', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 1),
(2, 'PMLEMUS', '878ae65a92e86cac011a570d4c30a7eaec442b85ce8eca0c2952b5e3cc0628c2e79d889ad4d5c7c626986d452dd86374b6ffaa7cd8b67665bef2289a5c70b0a1', 2),
(4, 'LEMUSP', '81369ddc64ebb26e7ff2d0b2cc3db8add14e13754114f45a297d3f18684ccc93b4b66725ac43e9d6ed4309d5e6e1b9b33473025e8e343110f515e0bf1e7332cd', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor`
--

CREATE TABLE `vendedor` (
  `codVendedor` int(11) NOT NULL,
  `nombres` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `dui` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `celular` varchar(8) COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(150) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `vendedor`
--

INSERT INTO `vendedor` (`codVendedor`, `nombres`, `apellidos`, `dui`, `celular`, `direccion`) VALUES
(1, 'BLANCA CAROLINA', 'AGUILAR', '012451454', '78455124', 'SAN SALVADOR'),
(2, 'PEDRO MIGUEL', 'LEMUS ROJAS', '45126398', '78451253', 'SAN SALVADOR'),
(4, 'wilmer', 'mercado', '123456879', '60154785', 'san cojute');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`codCliente`);

--
-- Indices de la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  ADD PRIMARY KEY (`codDetalle`),
  ADD KEY `FK_detallefactura_factura` (`codFactura`),
  ADD KEY `FK_detallefactura_producto` (`codBarra`),
  ADD KEY `FK_detallefactura_prodcto` (`codProducto`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`codFactura`),
  ADD KEY `FK_factura_vendedor` (`codVendedor`),
  ADD KEY `FK_factura_cliente` (`codCliente`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`codProducto`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`codUsuario`),
  ADD KEY `FK_usuario_vendedor` (`codVendedor`);

--
-- Indices de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  ADD PRIMARY KEY (`codVendedor`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `codCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  MODIFY `codDetalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `codFactura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `codProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  MODIFY `codVendedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  ADD CONSTRAINT `FK_detallefactura_factura` FOREIGN KEY (`codFactura`) REFERENCES `factura` (`codFactura`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_detallefactura_prodcto` FOREIGN KEY (`codProducto`) REFERENCES `producto` (`codProducto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `FK_factura_cliente` FOREIGN KEY (`codCliente`) REFERENCES `cliente` (`codCliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_factura_vendedor` FOREIGN KEY (`codVendedor`) REFERENCES `vendedor` (`codVendedor`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK_usuario_vendedor` FOREIGN KEY (`codVendedor`) REFERENCES `vendedor` (`codVendedor`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
