-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 24, 2023 at 11:07 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `auctions`
--

-- --------------------------------------------------------

--
-- Table structure for table `auctions`
--

CREATE TABLE `auctions` (
  `id` int(11) NOT NULL,
  `startingTime` datetime NOT NULL,
  `stoppageTime` datetime NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(256) NOT NULL,
  `location` varchar(32) NOT NULL,
  `type` enum('dutch','english') NOT NULL,
  `incremental` float NOT NULL DEFAULT 0,
  `minimum` float NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `auctions`
--

INSERT INTO `auctions` (`id`, `startingTime`, `stoppageTime`, `name`, `description`, `location`, `type`, `incremental`, `minimum`) VALUES
(1, '2023-03-11 00:00:00', '2023-03-13 00:00:00', 'English-style Auction from 2023-03-11T08:09:39.686401 - 2023-03-13T08:09:39.686401', 'Here you can find the english auction', 'online', 'english', 1, 0),
(2, '2023-03-11 00:00:00', '2023-03-13 00:00:00', 'Dutch-style Auction from 2023-03-11T08:09:39.686401 - 2023-03-13T08:09:39.686401', 'Here you can find the Dutch auction', 'online', 'dutch', 0, 1),
(7, '2023-03-11 00:00:00', '2023-03-13 00:00:00', 'English-style Auction from 2023-03-11T08:19:22.735032 - 2023-03-13T08:19:22.735032', 'Here you can find the english auction', 'online', 'english', 1, 0),
(8, '2023-03-11 00:00:00', '2023-03-13 00:00:00', 'Dutch-style Auction from 2023-03-11T08:19:22.735032 - 2023-03-13T08:19:22.735032', 'Here you can find the Dutch auction', 'online', 'dutch', 0, 1),
(9, '2023-05-24 00:00:00', '2023-05-26 00:00:00', 'English-style Auction from 2023-05-24T23:25:23.561281 - 2023-05-26T23:25:23.561281', 'Here you can find the english auction', 'online', 'english', 1, 0),
(10, '2023-05-24 00:00:00', '2023-05-26 00:00:00', 'Dutch-style Auction from 2023-05-24T23:25:23.561281 - 2023-05-26T23:25:23.561281', 'Here you can find the Dutch auction', 'online', 'dutch', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `auctions_products`
--

CREATE TABLE `auctions_products` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `auction_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `auctions_products`
--

INSERT INTO `auctions_products` (`id`, `product_id`, `auction_id`) VALUES
(3, 2, 1),
(4, 2, 2),
(5, 3, 8),
(6, 4, 8),
(7, 2, 9),
(8, 3, 10),
(9, 5, 9);

-- --------------------------------------------------------

--
-- Table structure for table `bids`
--

CREATE TABLE `bids` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `sum` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bids`
--

INSERT INTO `bids` (`id`, `product_id`, `user_id`, `sum`) VALUES
(1, 3, 1, 25),
(2, 4, 1, 100),
(3, 3, 2, 100),
(4, 5, 2, 10);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(256) NOT NULL,
  `startingPrice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `user_id`, `name`, `description`, `startingPrice`) VALUES
(2, 1, 'Xasd', 'demo', 23.2),
(3, 1, 'Demo', 'This is a demo product/', 13.2),
(4, 1, 'Demo', 'Demo.', 23.2),
(5, 1, 'demo', 'demo', 23.2);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `phone` varchar(16) NOT NULL,
  `password` varchar(256) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `phone`, `password`, `type`) VALUES
(1, 'Radu Vrinceanu', 'radu.seller@demo.ro', '0745324474', '9047eb8c1bca8a149d14cb3a40d49155a9590c4f', 1),
(2, 'Radu Vrinceanu', 'radu.buyer@demo.ro', '0745324474', '9047eb8c1bca8a149d14cb3a40d49155a9590c4f', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auctions`
--
ALTER TABLE `auctions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `auctions_products`
--
ALTER TABLE `auctions_products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_auctions_id` (`auction_id`),
  ADD KEY `FK_products_id` (`product_id`);

--
-- Indexes for table `bids`
--
ALTER TABLE `bids`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_user_id_bids` (`user_id`),
  ADD KEY `FK_product_id_bids` (`product_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auctions`
--
ALTER TABLE `auctions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `auctions_products`
--
ALTER TABLE `auctions_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `bids`
--
ALTER TABLE `bids`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `auctions_products`
--
ALTER TABLE `auctions_products`
  ADD CONSTRAINT `FK_auctions_id` FOREIGN KEY (`auction_id`) REFERENCES `auctions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_products_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bids`
--
ALTER TABLE `bids`
  ADD CONSTRAINT `FK_product_id_bids` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_user_id_bids` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_user_products_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
