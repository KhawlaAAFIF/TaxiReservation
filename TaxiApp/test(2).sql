-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 10, 2024 at 02:56 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`userId`) VALUES
(1),
(5),
(6);

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `id` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `cartype` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`id`, `userId`, `cartype`) VALUES
(6, 2, 'dacia logan'),
(7, 3, 'fiat 500'),
(8, 4, 'hyundai tucson');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `idConducteur` int(11) NOT NULL,
  `lieu_depa` varchar(250) NOT NULL,
  `datereservation` date NOT NULL,
  `datedepart` date NOT NULL,
  `datearrive` date NOT NULL,
  `information` varchar(250) NOT NULL,
  `numbrplaces` int(11) NOT NULL,
  `lieux_arriv` varchar(250) NOT NULL,
  `prix` double NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`id`, `idClient`, `idConducteur`, `lieu_depa`, `datereservation`, `datedepart`, `datearrive`, `information`, `numbrplaces`, `lieux_arriv`, `prix`, `status`) VALUES
(1, 1, 2, 'gueliz', '2024-01-09', '2024-01-08', '2024-01-08', 'taxi pour  personnes de gueliz vers jnan awrad', 3, 'jnan awrad', 20, 'Accepted'),
(2, 1, 2, 'la gare', '2024-01-10', '2024-01-10', '2024-01-11', 'BESOIN DE TAXI 8h', 1, 'sidi abbad', 20, 'En Cours'),
(3, 1, 2, 'Tamansourt', '2024-01-10', '2024-01-10', '2024-01-10', 'besoin de taxi', 1, 'gueliz', 20, 'En Cours'),
(4, 5, 2, 'Tamansourt', '2024-01-10', '2024-01-10', '2024-01-10', 'besoi', 1, 'gueliz', 20, 'Accepted'),
(5, 1, 2, 'Tamansourt', '2024-01-10', '2024-01-10', '2024-01-10', '9h', 1, 'gueliz', 20, 'Accepted'),
(6, 1, 2, 'jnan awrad', '2024-01-10', '2024-01-10', '2024-01-10', 'taxi à 8h', 1, 'sidi abbad', 20, 'En Cours'),
(7, 1, 4, 'daoudiat', '2024-01-10', '2024-01-11', '2024-01-11', 'taxi à 9h', 3, 'mhamid', 20, 'Accepted'),
(8, 6, 2, 'gueliz', '2024-01-10', '2024-01-10', '2024-01-10', 'taxi à 9h', 1, 'jnan awtad', 20, 'Accepted');

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `datenaissance` date NOT NULL,
  `sexe` varchar(20) NOT NULL,
  `city` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `fullname`, `telephone`, `role`, `email`, `password`, `datenaissance`, `sexe`, `city`) VALUES
(1, 'khawla aafif', '0606060679', 'client', 'khawla@gmail.com', '1234', '2001-01-23', 'Female', 'marrakech'),
(2, 'mourad', '0600999924', 'driver', 'mourad@gmail.com', '1234', '2002-01-04', 'Male', 'marrakech'),
(3, 'hamza', '0698989899', 'driver', 'hamza@gmail.com', '1234', '2000-01-12', 'Male', 'marrakech'),
(4, 'amina', '0603003033', 'driver', 'amina@gmail.com', '1234', '2001-01-02', 'Female', 'marrakech'),
(5, 'amina', '0600000099', 'client', 'amina1@gmail.com', '1234', '2002-01-01', 'Female', 'marrakech'),
(6, 'mourad el', '0600000000', 'client', 'mourad1@gmail.com', '1234', '2024-01-10', 'Female', 'marrakech');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `utilisateur` (`id`);

--
-- Constraints for table `driver`
--
ALTER TABLE `driver`
  ADD CONSTRAINT `driver_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `utilisateur` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
