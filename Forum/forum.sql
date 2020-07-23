-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 10, 2018 at 02:45 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `forum`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `Id` int(11) NOT NULL,
  `Name` varchar(30) COLLATE utf16_bin NOT NULL,
  `Description` text COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`Id`, `Name`, `Description`) VALUES
(1, 'Technology', ''),
(2, 'Sports', ''),
(3, 'Health', ''),
(4, 'Food', ''),
(5, 'Arts', ''),
(6, 'Culture', ''),
(7, 'Education', ''),
(8, 'Geography', ''),
(9, 'History', ''),
(11, 'Nature', ''),
(12, 'Philosophy', ''),
(13, 'Mathematics', ''),
(14, 'Religion', ''),
(15, 'Universe', ''),
(16, 'Society', '');

-- --------------------------------------------------------

--
-- Table structure for table `follows`
--

CREATE TABLE `follows` (
  `Id1` int(11) NOT NULL,
  `Id2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `follows`
--

INSERT INTO `follows` (`Id1`, `Id2`) VALUES
(2, 3),
(2, 4),
(2, 5),
(3, 1),
(3, 4),
(3, 6),
(4, 2),
(4, 3),
(4, 5),
(5, 4),
(6, 2),
(6, 4);

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `Id` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `PostId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`Id`, `UserId`, `PostId`) VALUES
(2, 2, 1),
(3, 2, 2),
(6, 2, 5),
(7, 3, 5),
(8, 5, 5),
(9, 6, 5),
(10, 6, 4),
(11, 4, 5),
(12, 4, 2),
(15, 2, 4),
(16, 2, 7);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `Id` int(11) NOT NULL,
  `Sender` int(11) NOT NULL,
  `Receiver` int(11) NOT NULL,
  `Content` text COLLATE utf16_bin NOT NULL,
  `Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Read` int(11) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`Id`, `Sender`, `Receiver`, `Content`, `Time`, `Read`) VALUES
(1, 4, 2, 'test poruke', '2018-07-08 01:38:50', 0),
(2, 4, 2, 'test poruke 2', '2018-07-08 01:39:42', 0),
(3, 2, 4, 'test 3', '2018-07-08 01:41:25', 0),
(7, 2, 4, 'odgovor na test poruke', '2018-07-08 02:28:44', 0),
(8, 2, 6, 'jebaga sto niste prosli', '2018-07-08 02:28:58', 0),
(9, 2, 3, 'de si kiske', '2018-07-08 02:31:36', 0),
(10, 4, 3, 'zapratila sam te', '2018-07-08 02:37:45', 0),
(11, 3, 2, 'evo kisketino', '2018-07-08 15:04:12', 0),
(12, 6, 2, 'dogodine', '2018-07-08 16:06:31', 0),
(13, 2, 6, 'ce bude dobro', '2018-07-08 18:33:24', 0),
(14, 6, 4, 'probamo finalnu poruku', '2018-07-08 18:48:04', 0),
(15, 4, 2, 'radiiii', '2018-07-08 18:48:46', 0),
(16, 2, 4, 'da probamo neku duzu poruku zbog word breaka da probamo neku duzu poruku zbog word breaka da probamo neku duzu poruku zbog word breaka da probamo neku duzu poruku zbog word breaka da probamo neku duzu poruku zbog word breaka da probamo neku duzu poruku zbog word breaka da probamo neku duzu poruku zbog word breaka', '2018-07-08 18:54:30', 0),
(17, 4, 2, 'sve je okej', '2018-07-08 18:59:53', 0),
(18, 4, 2, 'sve lepo radi', '2018-07-08 19:00:00', 0),
(19, 2, 3, 'teestttttt', '2018-07-08 19:05:49', 0),
(20, 2, 4, 'odlicno', '2018-07-08 19:13:11', 0),
(21, 3, 2, 'ful', '2018-07-08 19:13:49', 0),
(22, 4, 2, 'test bedza', '2018-07-08 19:27:19', 1),
(23, 4, 2, 'sve cool sa bedzom', '2018-07-08 19:27:26', 1),
(24, 2, 5, 'dje si mile', '2018-07-08 19:35:47', 1),
(25, 2, 6, ':D', '2018-07-09 02:28:28', 1),
(26, 1, 2, 'upozorenje', '2018-07-10 14:36:16', 1);

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `Id` int(11) NOT NULL,
  `Owner` int(11) NOT NULL,
  `CategoryId` int(11) NOT NULL,
  `Subject` varchar(100) COLLATE utf16_bin NOT NULL,
  `Content` text COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`Id`, `Owner`, `CategoryId`, `Subject`, `Content`) VALUES
(1, 2, 5, 'dsadsad', 'dsadsad'),
(2, 2, 5, 'dsadsad', 'dasdsadasdsa'),
(3, 4, 5, 'Test', 'test test test'),
(4, 2, 6, 'Kultura', 'test kulture failed'),
(5, 2, 5, 'dugi', 'djsahdkjsajdkhsakdjhsajkdhkjsahdkjhsakdhkashjkdhaskhdsahkdkjsancjksackjnsakcsahckhkashckaskcbkasjbcbkasbckasbckabsckbakscbabscbkasbcajksbckasbckasbckbaksbckabskcbksabckbaskcbkasbcbaskcbkjasbckbsakcbsakjcbkasbckbaskcbksajcsa'),
(6, 2, 9, 'Test', 'Test istorije'),
(7, 2, 13, 'matematika', 'testa das sadasd');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Id` int(11) NOT NULL,
  `Name` varchar(100) COLLATE utf16_bin NOT NULL,
  `Username` varchar(30) COLLATE utf16_bin NOT NULL,
  `Email` varchar(50) COLLATE utf16_bin NOT NULL,
  `Password` varchar(30) COLLATE utf16_bin NOT NULL,
  `Picture` varchar(500) COLLATE utf16_bin DEFAULT NULL,
  `Type` varchar(45) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Id`, `Name`, `Username`, `Email`, `Password`, `Picture`, `Type`) VALUES
(1, 'admin', 'admin', 'admin', 'admin', 'oficcial-512.jpg', 'admin'),
(2, 'Lazar Avramovic', 'lzr.avr', 'lzr.avr@gmail.com', 'kiske', 'IMG_20171125_132403.jpg', 'user'),
(3, 'Kisuke Urahara', 'kiske', 'kiske@kiske.com', 'malisa', 'default.png', 'user'),
(4, 'Jelisaveta  Avramovic', 'malisa', 'malisa@gmail.com', 'malisa', 'large.jpg', 'user'),
(5, 'Milovan Ostojic', 'mile', 'mile@gmail.com', 'mile', 'lp2qlml2wxl01.jpg', 'user'),
(6, 'Aleksandar Golovin', 'golovin', 'golovin@yandex.ru', 'golovin', 'mosdef.jpg', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `follows`
--
ALTER TABLE `follows`
  ADD PRIMARY KEY (`Id1`,`Id2`) USING BTREE;

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `likes`
--
ALTER TABLE `likes`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
