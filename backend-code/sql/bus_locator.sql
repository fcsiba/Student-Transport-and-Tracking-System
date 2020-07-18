-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2020 at 02:43 PM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 7.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bus_locator`
--

-- --------------------------------------------------------

--
-- Table structure for table `buses`
--

CREATE TABLE `buses` (
  `id` int(20) NOT NULL,
  `bus_no` varchar(255) NOT NULL,
  `total_seats` int(50) NOT NULL,
  `available_seats` int(50) NOT NULL,
  `status` varchar(255) NOT NULL,
  `current_lat` varchar(255) NOT NULL,
  `current_lng` varchar(255) NOT NULL,
  `current_stop` varchar(255) NOT NULL,
  `previous_stop` varchar(255) NOT NULL,
  `next_stop` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buses`
--

INSERT INTO `buses` (`id`, `bus_no`, `total_seats`, `available_seats`, `status`, `current_lat`, `current_lng`, `current_stop`, `previous_stop`, `next_stop`, `type`) VALUES
(1, 'ALF-123', 50, 50, '1', '0', '0', '2', '1', '3', '1'),
(2, 'ALF-321', 50, 50, '1', '0', '0', '4', '3', '5', '1');

-- --------------------------------------------------------

--
-- Table structure for table `parants`
--

CREATE TABLE `parants` (
  `id` int(11) NOT NULL,
  `s_id` int(20) NOT NULL,
  `s_status` varchar(50) NOT NULL,
  `b_id` int(20) NOT NULL,
  `b_status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `id` int(11) NOT NULL,
  `s_id` int(20) NOT NULL,
  `b_id` int(20) NOT NULL,
  `pickup_stop` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`id`, `s_id`, `b_id`, `pickup_stop`) VALUES
(6, 1, 2, '4');

-- --------------------------------------------------------

--
-- Table structure for table `stops`
--

CREATE TABLE `stops` (
  `id` int(11) NOT NULL,
  `stop_name` varchar(255) NOT NULL,
  `stop_lat` varchar(255) NOT NULL,
  `stop_lng` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stops`
--

INSERT INTO `stops` (`id`, `stop_name`, `stop_lat`, `stop_lng`) VALUES
(1, 'Uni Isb', '33.6738703', '73.0393657'),
(2, 'stop2', '33.6617465', '73.1414719'),
(3, 'Stop 3', '33.6756102', '73.1364938'),
(4, 'Stop 4', '33.6754014', '73.1352029');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `cnic` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `latitude` varchar(255) NOT NULL,
  `longitude` varchar(255) NOT NULL,
  `contact_no` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `father_name` varchar(255) NOT NULL,
  `father_contact_no` varchar(255) NOT NULL,
  `reg_no` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `cnic`, `password`, `latitude`, `longitude`, `contact_no`, `address`, `father_name`, `father_contact_no`, `reg_no`, `type`, `status`) VALUES
(1, 'Muhammad Umar', '6110163926123', 'pakistan', '39.003256', '72.003256', '03439382048', 'Manzoor Colony Karachi', 'Muhammad Saleem', '03125254759', 'SP14-bcs-184', '1', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buses`
--
ALTER TABLE `buses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parants`
--
ALTER TABLE `parants`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stops`
--
ALTER TABLE `stops`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `buses`
--
ALTER TABLE `buses`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `parants`
--
ALTER TABLE `parants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `stops`
--
ALTER TABLE `stops`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
