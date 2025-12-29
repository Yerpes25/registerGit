SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

Drop database if exists `register1`;
CREATE DATABASE  IF NOT EXISTS `register1`;
USE `register1`;
--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `user` (
  `email` varchar(255) NOT NULL,
  `username` varchar(16) NOT NULL,
  `password` longblob NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT NULL,
  `foto` LONGBLOB DEFAULT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);

INSERT INTO `user` (`email`, `username`, `password`, `active`, `foto`) VALUES
('admin@empresa.com', 'admin', 0xc91d0f919f23f1d56157b94ba87db805, 1, NULL);

--
-- Estructura de tabla para la tabla `ubicacion`
--
CREATE TABLE IF NOT EXISTS `ubicacion` (
	id INT AUTO_INCREMENT,
    codigo VARCHAR(4) UNIQUE,
    nombre VARCHAR(50),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ubicacion` (`codigo`, `nombre`) VALUES
('P1E1', 'Pasillo 1 estanteria 1'),
('P1E2', 'Pasillo 1 estanteria 2'),
('P1E3', 'Pasillo 1 estanteria 3'),
('P2E1', 'Pasillo 2 estanteria 1'),
('P2E2', 'Pasillo 2 estanteria 2'),
('P2E3', 'Pasillo 2 estanteria 3'),
('P3E1', 'Pasillo 3 estanteria 1'),
('P3E2', 'Pasillo 3 estanteria 2'),
('P3E3', 'Pasillo 3 estanteria 3');

--
-- Estructura de tabla para la tabla `almacen`
--
CREATE TABLE IF NOT EXISTS `producto` (
	id INT AUTO_INCREMENT,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(100),
    cantidad INT,
    ubicacion VARCHAR(4) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ubicacion) REFERENCES ubicacion(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `producto` (`codigo`, `descripcion`, `cantidad`, `ubicacion`) VALUES
('A01', 'Alicates de punta curva', 2, 'P1E2'),
('A02', 'Alicates de crimpar', 4, 'P1E3'),
('A03', 'Alicates de corte', 1, 'P1E2'),
('D01', 'Destornillador plano', 4, 'P2E1'),
('D02', 'Destornillador tipo Allen', 10, 'P2E2'),
('D03', 'Destornillador de estrella', 3, 'P2E1'),
('T01', 'Tornillos tirafondos para madera', 100, 'P3E1'),
('T02', 'Tornillos tirafondos para paredes', 1000, 'P3E2'),
('T03', 'Tornillos de roscas cilíndricas', 500, 'P3E2');

select * from producto;
COMMIT;
