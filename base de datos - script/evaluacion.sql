-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-05-2017 a las 05:25:54
-- Versión del servidor: 5.6.26-log
-- Versión de PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `evaluacion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiante`
--

CREATE TABLE `estudiante` (
  `cedula` varchar(12) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `programa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `estudiante`
--

INSERT INTO `estudiante` (`cedula`, `nombre`, `apellidos`, `programa`) VALUES
('10001', 'juan', 'duran', 504),
('10002', 'raul', 'marti', 505),
('10003', 'alejandro', 'castan', 506);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluacion`
--

CREATE TABLE `evaluacion` (
  `semestre` int(11) NOT NULL,
  `materia` int(11) NOT NULL,
  `estudiante` varchar(12) NOT NULL,
  `grupo` int(11) NOT NULL,
  `profesor` varchar(12) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `realizado` varchar(2) NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE `grupo` (
  `numero` int(11) NOT NULL,
  `materia` int(11) NOT NULL,
  `semestre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`numero`, `materia`, `semestre`) VALUES
(1, 70001, 20171),
(2, 70001, 20171),
(2, 70002, 20171),
(1, 70003, 20171);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo_profesor`
--

CREATE TABLE `grupo_profesor` (
  `numero` int(11) NOT NULL,
  `materia` int(11) NOT NULL,
  `semestre` int(11) NOT NULL,
  `docente` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `grupo_profesor`
--

INSERT INTO `grupo_profesor` (`numero`, `materia`, `semestre`, `docente`) VALUES
(1, 70001, 20171, '20001'),
(2, 70001, 20171, '20001'),
(1, 70001, 20171, '20002'),
(1, 70003, 20171, '20002'),
(2, 70002, 20171, '20003');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia`
--

CREATE TABLE `materia` (
  `codigo` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `materia`
--

INSERT INTO `materia` (`codigo`, `nombre`) VALUES
(70001, 'Algebra Linea'),
(70002, 'Ingles II'),
(70003, 'Matematicas II'),
(70004, 'Fisica I'),
(70005, 'Calculo I'),
(70006, 'Fisica de Campos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matricula`
--

CREATE TABLE `matricula` (
  `semestre` int(11) NOT NULL,
  `materia` int(11) NOT NULL,
  `grupo` int(11) NOT NULL,
  `estudiante` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `matricula`
--

INSERT INTO `matricula` (`semestre`, `materia`, `grupo`, `estudiante`) VALUES
(20171, 70001, 1, '10001'),
(20171, 70001, 1, '10002'),
(20171, 70003, 1, '10001'),
(20171, 70002, 2, '10001');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pregunta`
--

CREATE TABLE `pregunta` (
  `numero` varchar(5) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `descripcion` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pregunta`
--

INSERT INTO `pregunta` (`numero`, `tipo`, `descripcion`) VALUES
('1', 'CONOCIMIENTOS', 'Seguridad en exposiciones'),
('10', 'RELACIONES CON LOS ESTUDIANTES', 'Puntualidad en la entrega de notas'),
('11', 'RELACIONES CON LOS ESTUDIANTES', 'Disposición para atender consultas fuera de la actividad curricular'),
('12', 'RELACIONES CON LOS ESTUDIANTES', 'Ecuanimidad y respeto en el trato con los estudiantes'),
('13', 'MANEJO DE LA EVALUACION', 'Objetivo en las calificaciones'),
('14', 'MANEJO DE LA EVALUACION', 'Elaboracion de pruebas y exámenes'),
('15', 'APRECIACION GENERAL', 'Si usted tuviera que darle una calificación global al profesor¿Cual le pondria?'),
('16', 'EVALUACION DEL CURSO', 'Conveniencia de la intensidad horaria semanal'),
('17', 'EVALUACION DEL CURSO', 'Logros de los objetivos formulados en el curso o actividad curricular'),
('18', 'EVALUACION DEL CURSO', 'Relacion con los prerrequisitos del curso o actividad curricular'),
('19', 'EVALUACION DEL CURSO', 'Interes y actualidad de los contenidos del curso o actividad curricular'),
('2', 'CONOCIMIENTOS', 'Respuesta clara y acertada a preguntas'),
('20', 'EVALUACION DEL CURSO', 'Es importante dentro del plan de estudios'),
('3', 'CONOCIMIENTOS', 'Dominios de los temas del curso o actividad curricular'),
('4', 'METODOLOGIA', 'Eficiencia en el uso del tiempo de clase o actividad curricular'),
('5', 'METODOLOGIA', 'Empleo de recursos didacticos'),
('6', 'METODOLOGIA', 'Orden, coherencia y claridad en las exposiciones de los temas'),
('7', 'METODOLOGIA', 'Capacidad para despertar interes'),
('8', 'METODOLOGIA', 'Apoyo a las actividades de aprendizaje independientes'),
('9', 'METODOLOGIA', 'Puntualidad y asistencia a las sesiones de clase o actividades');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE `profesor` (
  `cedula` varchar(12) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `profesor`
--

INSERT INTO `profesor` (`cedula`, `nombre`, `apellidos`) VALUES
('20001', 'Ana Lucia', 'Duna Rodriguez'),
('20002', 'Marco Antonio', 'Jaramillo Alcala'),
('20003', 'Andy', 'Torrez Nina');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `respuesta`
--

CREATE TABLE `respuesta` (
  `semestre` int(11) NOT NULL,
  `estudiante` varchar(12) NOT NULL,
  `materia` int(11) NOT NULL,
  `grupo` int(11) NOT NULL,
  `profesor` varchar(12) NOT NULL,
  `pregunta` int(11) NOT NULL,
  `respuesta` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `semestre`
--

CREATE TABLE `semestre` (
  `codigo` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `semestre`
--

INSERT INTO `semestre` (`codigo`, `fechaInicio`, `fechaFin`) VALUES
(20171, '2017-02-01', '2017-06-20'),
(20172, '2017-08-01', '2017-12-10');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `estudiante`
--
ALTER TABLE `estudiante`
  ADD PRIMARY KEY (`cedula`);

--
-- Indices de la tabla `evaluacion`
--
ALTER TABLE `evaluacion`
  ADD PRIMARY KEY (`semestre`,`materia`,`estudiante`,`grupo`,`profesor`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`numero`,`materia`,`semestre`),
  ADD KEY `materia` (`materia`),
  ADD KEY `semestre` (`semestre`);

--
-- Indices de la tabla `grupo_profesor`
--
ALTER TABLE `grupo_profesor`
  ADD PRIMARY KEY (`numero`,`materia`,`semestre`,`docente`),
  ADD KEY `docente` (`docente`);

--
-- Indices de la tabla `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `matricula`
--
ALTER TABLE `matricula`
  ADD PRIMARY KEY (`semestre`,`materia`,`grupo`,`estudiante`),
  ADD UNIQUE KEY `materia` (`materia`,`estudiante`),
  ADD KEY `estudiante` (`estudiante`),
  ADD KEY `grupo` (`grupo`,`materia`,`semestre`);

--
-- Indices de la tabla `pregunta`
--
ALTER TABLE `pregunta`
  ADD PRIMARY KEY (`numero`),
  ADD UNIQUE KEY `descripcion` (`descripcion`);

--
-- Indices de la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`cedula`);

--
-- Indices de la tabla `respuesta`
--
ALTER TABLE `respuesta`
  ADD PRIMARY KEY (`semestre`,`estudiante`,`materia`,`grupo`,`profesor`,`pregunta`);

--
-- Indices de la tabla `semestre`
--
ALTER TABLE `semestre`
  ADD PRIMARY KEY (`codigo`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`materia`) REFERENCES `materia` (`codigo`),
  ADD CONSTRAINT `grupo_ibfk_2` FOREIGN KEY (`semestre`) REFERENCES `semestre` (`codigo`);

--
-- Filtros para la tabla `grupo_profesor`
--
ALTER TABLE `grupo_profesor`
  ADD CONSTRAINT `grupo_profesor_ibfk_1` FOREIGN KEY (`numero`,`materia`,`semestre`) REFERENCES `grupo` (`numero`, `materia`, `semestre`),
  ADD CONSTRAINT `grupo_profesor_ibfk_2` FOREIGN KEY (`docente`) REFERENCES `profesor` (`cedula`);

--
-- Filtros para la tabla `matricula`
--
ALTER TABLE `matricula`
  ADD CONSTRAINT `matricula_ibfk_1` FOREIGN KEY (`estudiante`) REFERENCES `estudiante` (`cedula`),
  ADD CONSTRAINT `matricula_ibfk_2` FOREIGN KEY (`grupo`,`materia`,`semestre`) REFERENCES `grupo` (`numero`, `materia`, `semestre`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
