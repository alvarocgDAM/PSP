-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.19 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para supermercadopsp
CREATE DATABASE IF NOT EXISTS `supermercadopsp` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `supermercadopsp`;

-- Volcando estructura para tabla supermercadopsp.compra
CREATE TABLE IF NOT EXISTS `compra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_compra` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla supermercadopsp.compra: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;

-- Volcando estructura para tabla supermercadopsp.compra_empleado
CREATE TABLE IF NOT EXISTS `compra_empleado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_compra` int NOT NULL DEFAULT '0',
  `id_empleado` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_compra_empleado_compra` (`id_compra`),
  KEY `FK_compra_empleado_empleado` (`id_empleado`),
  CONSTRAINT `FK_compra_empleado_compra` FOREIGN KEY (`id_compra`) REFERENCES `compra` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_compra_empleado_empleado` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla supermercadopsp.compra_empleado: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `compra_empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra_empleado` ENABLE KEYS */;

-- Volcando estructura para tabla supermercadopsp.empleado
CREATE TABLE IF NOT EXISTS `empleado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ultima_sesion` date NOT NULL,
  `fecha_contratacion` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla supermercadopsp.empleado: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` (`id`, `ultima_sesion`, `fecha_contratacion`) VALUES
	(1, '2021-02-24', '2018-09-21'),
	(2, '2021-02-24', '2019-02-24'),
	(3, '2020-02-24', '2017-06-04');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;

-- Volcando estructura para tabla supermercadopsp.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `precio_venta` double DEFAULT NULL,
  `precio_proveedor` double DEFAULT NULL,
  `cantidad_stock` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla supermercadopsp.producto: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id`, `nombre_producto`, `precio_venta`, `precio_proveedor`, `cantidad_stock`) VALUES
	(1, 'Arroz', 1.23, 0.56, 20),
	(2, 'Salchichón', 2.4, 1.72, 15),
	(3, 'Yogures', 1.06, 0.62, 50),
	(4, 'Queso fresco', 2.48, 1.3, 3),
	(5, 'Detergente', 3.89, 2.32, 10);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla supermercadopsp.producto_compra
CREATE TABLE IF NOT EXISTS `producto_compra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_producto` int NOT NULL DEFAULT '0',
  `id_compra` int NOT NULL DEFAULT '0',
  `cantidad_producto` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_producto_compra_compra` (`id_compra`),
  KEY `FK_producto_compra_producto` (`id_producto`),
  CONSTRAINT `FK_producto_compra_compra` FOREIGN KEY (`id_compra`) REFERENCES `compra` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_producto_compra_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla supermercadopsp.producto_compra: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `producto_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto_compra` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
