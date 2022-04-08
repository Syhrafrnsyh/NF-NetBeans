-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 06, 2022 at 01:16 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nfbeans`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id_barang` int(11) NOT NULL,
  `kode` varchar(10) CHARACTER SET utf8mb4 NOT NULL,
  `nama` varchar(100) CHARACTER SET utf8 NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `stok` int(11) NOT NULL,
  `harga` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `kode`, `nama`, `id_kategori`, `stok`, `harga`) VALUES
(1, 'B1', 'Cupang', 1, 102, 100000.00),
(2, 'B2', 'Nemo', 1, 79, 50000.00),
(3, 'B3', 'Bonsai', 2, 120, 100000.00);

-- --------------------------------------------------------

--
-- Table structure for table `jasa`
--

CREATE TABLE `jasa` (
  `id_jasa` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `tanggal` text NOT NULL,
  `jam` text NOT NULL,
  `telepon` varchar(20) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `keterangan` varchar(100) NOT NULL,
  `menu` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jasa`
--

INSERT INTO `jasa` (`id_jasa`, `nama`, `tanggal`, `jam`, `telepon`, `alamat`, `keterangan`, `menu`) VALUES
(3, 'Satu', '2022-02-13', '22:00', '02112345678', 'Jl.', 'Detail', 'Desain'),
(4, 'Test', '2022-03-21', '00:20', '02198765432', 'Jl.', 'Detail', 'Perawatan'),
(5, 'S', '2022-03-21', '17:34', '021', 'Jl.', 'Abc', 'Setting');

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama`) VALUES
(1, 'Ikan'),
(2, 'Tumbuhan'),
(3, 'Aksesoris');

-- --------------------------------------------------------

--
-- Table structure for table `keluar`
--

CREATE TABLE `keluar` (
  `id_keluar` int(11) NOT NULL,
  `id_barang` int(25) NOT NULL,
  `id_user` int(25) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total` double(10,2) NOT NULL,
  `tanggal` date NOT NULL,
  `no` varchar(11) NOT NULL,
  `ket` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `keluar`
--

INSERT INTO `keluar` (`id_keluar`, `id_barang`, `id_user`, `jumlah`, `total`, `tanggal`, `no`, `ket`) VALUES
(1, 1, 1, 20, 2000000.00, '2022-02-03', 'NF-1', 'TERJUAL'),
(2, 1, 1, 1, 100000.00, '2022-02-24', 'NF-2', 'TERJUAL'),
(3, 2, 1, 1, 50000.00, '2022-02-24', 'NF-2', 'TERJUAL'),
(4, 1, 1, 2, 200000.00, '2022-03-21', 'NF-4', 'return'),
(5, 2, 1, 10, 500000.00, '2022-03-21', 'NF-4', 'return');

-- --------------------------------------------------------

--
-- Table structure for table `kembalikeluar`
--

CREATE TABLE `kembalikeluar` (
  `id_kembalikeluar` int(11) NOT NULL,
  `noretrun` varchar(11) NOT NULL,
  `namaretrun` varchar(100) NOT NULL,
  `tanggalretrun` date NOT NULL,
  `id_keluar` int(11) NOT NULL,
  `no` varchar(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total` double(10,2) NOT NULL,
  `id_user` int(11) NOT NULL,
  `ket` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kembalikeluar`
--

INSERT INTO `kembalikeluar` (`id_kembalikeluar`, `noretrun`, `namaretrun`, `tanggalretrun`, `id_keluar`, `no`, `id_barang`, `tanggal`, `jumlah`, `total`, `id_user`, `ket`) VALUES
(1, 'R0001', 'Customer', '2022-03-21', 4, 'NF-4', 1, '2022-03-21', 2, 200000.00, 1, 'return'),
(2, 'R0001', '', '2022-03-21', 5, 'NF-4', 2, '2022-03-21', 10, 500000.00, 1, 'return');

-- --------------------------------------------------------

--
-- Table structure for table `kembalimasuk`
--

CREATE TABLE `kembalimasuk` (
  `id_kembalimasuk` int(11) NOT NULL,
  `noretrun` varchar(11) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  `tanggalretrun` date NOT NULL,
  `id_masuk` int(11) NOT NULL,
  `no` varchar(11) NOT NULL,
  `tanggal` date NOT NULL,
  `id_barang` int(11) NOT NULL,
  `harga` double(10,2) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total` double(10,2) NOT NULL,
  `ket` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kembalimasuk`
--

INSERT INTO `kembalimasuk` (`id_kembalimasuk`, `noretrun`, `id_supplier`, `tanggalretrun`, `id_masuk`, `no`, `tanggal`, `id_barang`, `harga`, `jumlah`, `total`, `ket`) VALUES
(1, 'R0001', 4, '2022-03-21', 5, 'T0003', '2022-03-21', 2, 50000.00, 11, 550000.00, 'return'),
(2, 'R0001', 4, '2022-03-21', 4, 'T0003', '2022-03-21', 1, 100000.00, 1, 100000.00, 'return');

-- --------------------------------------------------------

--
-- Table structure for table `masuk`
--

CREATE TABLE `masuk` (
  `id_masuk` int(11) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total` double(10,2) NOT NULL,
  `tanggal` date NOT NULL,
  `no` varchar(11) NOT NULL,
  `ket` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `masuk`
--

INSERT INTO `masuk` (`id_masuk`, `id_supplier`, `id_barang`, `jumlah`, `total`, `tanggal`, `no`, `ket`) VALUES
(1, 1, 1, 1, 100000.00, '2022-01-26', 'T0001', 'ACCEPT'),
(2, 2, 2, 50, 2500000.00, '2022-02-03', 'T0002', 'ACCEPT'),
(3, 2, 2, 5, 250000.00, '2022-02-03', 'T0002', 'ACCEPT'),
(4, 4, 1, 1, 100000.00, '2022-03-21', 'T0003', 'return'),
(5, 4, 2, 11, 550000.00, '2022-03-21', 'T0003', 'return');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL,
  `kode` varchar(10) CHARACTER SET utf8mb4 NOT NULL,
  `nama` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `alamat` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `telepon` varchar(20) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `kode`, `nama`, `alamat`, `telepon`) VALUES
(1, 'S1', 'Google', 'Jl.Jakarta', '02123456789'),
(2, 'S2', 'Yahoo', 'Jl.Bandung', '0987654321'),
(3, 'S3', 'Microsoft', 'Jl.Bali', '0123456789'),
(4, 'S4', 'Apple', 'Jl.Jogja', '0219876543');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `alamat` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `telepon` varchar(20) CHARACTER SET utf8mb4 NOT NULL,
  `status` varchar(12) CHARACTER SET utf8mb4 NOT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `akses` varchar(6) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `alamat`, `telepon`, `status`, `username`, `password`, `akses`) VALUES
(1, 'admin', 'Jakarta', '02123456789', 'Aktif', 'admin', 'admin', 'Admin'),
(2, 'Syahrul', 'Mampang Prapatan', '087812345678', 'Aktif', 'Syahrul', '123', 'User'),
(3, 'test', 'Jl.Jakarta', '0219876543', 'Aktif', 'test', '123', 'User');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`),
  ADD KEY `Key` (`id_kategori`);

--
-- Indexes for table `jasa`
--
ALTER TABLE `jasa`
  ADD PRIMARY KEY (`id_jasa`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `keluar`
--
ALTER TABLE `keluar`
  ADD PRIMARY KEY (`id_keluar`),
  ADD KEY `Keybar` (`id_barang`),
  ADD KEY `Keyus` (`id_user`);

--
-- Indexes for table `kembalikeluar`
--
ALTER TABLE `kembalikeluar`
  ADD PRIMARY KEY (`id_kembalikeluar`),
  ADD KEY `Mkeluar` (`id_keluar`),
  ADD KEY `Mbrng` (`id_barang`),
  ADD KEY `Mus` (`id_user`);

--
-- Indexes for table `kembalimasuk`
--
ALTER TABLE `kembalimasuk`
  ADD PRIMARY KEY (`id_kembalimasuk`),
  ADD KEY `Mmsk` (`id_masuk`),
  ADD KEY `Msupp` (`id_supplier`),
  ADD KEY `Mbarank` (`id_barang`);

--
-- Indexes for table `masuk`
--
ALTER TABLE `masuk`
  ADD PRIMARY KEY (`id_masuk`),
  ADD KEY `MkeySup` (`id_supplier`),
  ADD KEY `MkeyBar` (`id_barang`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `jasa`
--
ALTER TABLE `jasa`
  MODIFY `id_jasa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `keluar`
--
ALTER TABLE `keluar`
  MODIFY `id_keluar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `kembalikeluar`
--
ALTER TABLE `kembalikeluar`
  MODIFY `id_kembalikeluar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `kembalimasuk`
--
ALTER TABLE `kembalimasuk`
  MODIFY `id_kembalimasuk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `masuk`
--
ALTER TABLE `masuk`
  MODIFY `id_masuk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `Key` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `keluar`
--
ALTER TABLE `keluar`
  ADD CONSTRAINT `Keybar` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Keyus` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `kembalikeluar`
--
ALTER TABLE `kembalikeluar`
  ADD CONSTRAINT `Mbrng` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Mkeluar` FOREIGN KEY (`id_keluar`) REFERENCES `keluar` (`id_keluar`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Mus` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `kembalimasuk`
--
ALTER TABLE `kembalimasuk`
  ADD CONSTRAINT `Mbarank` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Mmsk` FOREIGN KEY (`id_masuk`) REFERENCES `masuk` (`id_masuk`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Msupp` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `masuk`
--
ALTER TABLE `masuk`
  ADD CONSTRAINT `MkeyBar` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `MkeySup` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
