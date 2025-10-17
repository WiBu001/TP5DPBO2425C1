-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 17, 2025 at 05:31 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_product1`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `harga` double NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `kondisi` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `nama`, `harga`, `kategori`, `kondisi`) VALUES
('PRD001', 'Laptop ASUS', 8500000, 'Elektronik', 'Baru'),
('PRD002', 'Mouse Wireless', 150000, 'Elektronik', 'Baru'),
('PRD003', 'Keyboard Gaming', 450000, 'Elektronik', 'Baru'),
('PRD004', 'Monitor 24 inch', 2200000, 'Elektronik', 'Baru'),
('PRD005', 'Headset Gaming', 350000, 'Elektronik', 'Bekas'),
('PRD006', 'Smartphone Samsung', 4500000, 'Elektronik', 'Baru'),
('PRD007', 'Charger USB-C', 85000, 'Aksesoris', 'Bekas'),
('PRD008', 'Power Bank', 250000, 'Aksesoris', 'Bekas'),
('PRD009', 'Webcam HD', 180000, 'Elektronik', 'Baru'),
('PRD010', 'Speaker Bluetooth', 320000, 'Elektronik', 'Baru'),
('PRD011', 'Tablet Android', 2800000, 'Elektronik', 'Baru'),
('PRD012', 'Smartwatch', 1200000, 'Aksesoris', 'Bekas'),
('PRD013', 'Flash Drive 32GB', 65000, 'Penyimpanan', 'Baru'),
('PRD014', 'Hard Disk 1TB', 750000, 'Penyimpanan', 'Baru'),
('PRD015', 'Router WiFi', 420000, 'Jaringan', 'Baru'),
('PRD016', 'Kabel HDMI', 45000, 'Aksesoris', 'Bekas'),
('PRD017', 'Printer Inkjet', 850000, 'Perangkat Kantor', 'Baru'),
('PRD018', 'Scanner Document', 650000, 'Perangkat Kantor', 'Baru'),
('PRD019', 'Cooling Pad', 120000, 'Aksesoris', 'Bekas'),
('PRD020', 'Gaming Chair', 1800000, 'Furniture', 'Baru');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
