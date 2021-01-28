-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-01-2021 a las 06:11:48
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.3.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `appbusqueda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `autores`
--

CREATE TABLE `autores` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `autores`
--

INSERT INTO `autores` (`id`, `nombre`) VALUES
(2, 'Victor Hugo'),
(3, 'Eduardo Rodriguez'),
(4, 'Ulises Ancona2'),
(5, 'Ulises Ancona2'),
(6, 'Ulises Ancona2'),
(7, 'Ulises Ancona2'),
(8, 'Ulises Ancona'),
(9, 'Ulises Ancona2'),
(10, 'Vladimir Ancona2'),
(11, 'Vladimir Ancona2'),
(12, 'Vladimir Ancona');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `autores_libros`
--

CREATE TABLE `autores_libros` (
  `id` int(11) NOT NULL,
  `id_libro` varchar(45) NOT NULL,
  `id_autor` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `autores_libros`
--

INSERT INTO `autores_libros` (`id`, `id_libro`, `id_autor`) VALUES
(4, '6', '12'),
(5, '6', '11');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE `libros` (
  `id` int(11) NOT NULL,
  `titulo` varchar(45) NOT NULL,
  `autor` varchar(256) NOT NULL,
  `fecha_publicacion` date NOT NULL,
  `editorial` varchar(45) DEFAULT NULL,
  `isbn` varchar(45) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`id`, `titulo`, `autor`, `fecha_publicacion`, `editorial`, `isbn`, `id_usuario`) VALUES
(1, 'Libro1', '', '2019-01-01', NULL, NULL, 3),
(2, 'Libro2', '', '2020-01-01', NULL, NULL, 3),
(3, 'Libro2', '', '2020-01-01', NULL, NULL, 3),
(4, 'Libro2', '', '2020-01-01', NULL, NULL, 3),
(5, 'Libro55', '', '2020-01-01', NULL, NULL, 3),
(6, 'Libro55', '', '2020-01-01', NULL, NULL, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tokens`
--

CREATE TABLE `tokens` (
  `id` int(11) NOT NULL,
  `token` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tokens`
--

INSERT INTO `tokens` (`id`, `token`) VALUES
(1, 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1bGlhbmNvbmE5OCIsImV4cCI6MTYwOTcxODgyNiwiZXhwaXJhY2lvbiI6MTYwOTcxODgyNjAxMSwiaWF0IjoxNjA5NzAzODI2fQ.q_6yr_QFSRa5qbFoEGwpRkE-46sdeosnFAIDSlAQIevHD19S-5Fnia3ULsApDiEvQSQqO3SB7Kuj5vWZNFMduQ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `secret` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `usuario`, `password`, `secret`) VALUES
(2, 'uliancona98', '12345678', 'd7d6cd8f-0ba0-4324-88eb-6bb0f43cb2a4'),
(3, 'shaidbojorquez', '123456789', '150a04ae-a4d6-483c-aa0e-277b832d49d6');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `autores`
--
ALTER TABLE `autores`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `autores_libros`
--
ALTER TABLE `autores_libros`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `token` (`token`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `autores`
--
ALTER TABLE `autores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `autores_libros`
--
ALTER TABLE `autores_libros`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `tokens`
--
ALTER TABLE `tokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
