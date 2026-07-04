-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Waktu pembuatan: 04 Jul 2026 pada 17.01
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `toko_retail`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_barang`
--

CREATE TABLE `log_barang` (
  `id` int(11) NOT NULL,
  `aksi` varchar(20) DEFAULT NULL,
  `kode_barang` varchar(10) DEFAULT NULL,
  `nama_barang` varchar(100) DEFAULT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `log_barang`
--

INSERT INTO `log_barang` (`id`, `aksi`, `kode_barang`, `nama_barang`, `waktu`) VALUES
(1, 'INSERT', '2', 'Teh Pucuk', '2026-07-04 16:59:24'),
(2, 'INSERT', '3', 'Paramex', '2026-07-04 16:59:40');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_barang`
--

CREATE TABLE `tbl_barang` (
  `kode_barang` varchar(50) DEFAULT NULL,
  `nama_barang` varchar(150) DEFAULT NULL,
  `harga_barang` int(11) DEFAULT NULL,
  `stok_barang` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_barang`
--

INSERT INTO `tbl_barang` (`kode_barang`, `nama_barang`, `harga_barang`, `stok_barang`) VALUES
('1', 'Pocari', 8000, 5),
('2', 'Teh Pucuk', 4000, 5),
('3', 'Paramex', 500, 5);

--
-- Trigger `tbl_barang`
--
DELIMITER $$
CREATE TRIGGER `trg_barang_delete` AFTER DELETE ON `tbl_barang` FOR EACH ROW BEGIN
INSERT INTO log_barang
(
    aksi,
    kode_barang,
    nama_barang
)
VALUES
(
    'DELETE',
    OLD.kode_barang,
    OLD.nama_barang
);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_barang_insert` AFTER INSERT ON `tbl_barang` FOR EACH ROW BEGIN
INSERT INTO log_barang
(
    aksi,
    kode_barang,
    nama_barang
)
VALUES
(
    'INSERT',
    NEW.kode_barang,
    NEW.nama_barang
);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in struktur untuk tampilan `v_barang`
-- (Lihat di bawah untuk tampilan aktual)
--
CREATE TABLE `v_barang` (
`kode_barang` varchar(50)
,`nama_barang` varchar(150)
,`harga_barang` int(11)
,`stok_barang` int(11)
,`total_nilai` bigint(21)
);

-- --------------------------------------------------------

--
-- Struktur untuk view `v_barang`
--
DROP TABLE IF EXISTS `v_barang`;

CREATE ALGORITHM=UNDEFINED DEFINER=`` SQL SECURITY DEFINER VIEW `v_barang`  AS SELECT `tbl_barang`.`kode_barang` AS `kode_barang`, `tbl_barang`.`nama_barang` AS `nama_barang`, `tbl_barang`.`harga_barang` AS `harga_barang`, `tbl_barang`.`stok_barang` AS `stok_barang`, `tbl_barang`.`harga_barang`* `tbl_barang`.`stok_barang` AS `total_nilai` FROM `tbl_barang` ;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `log_barang`
--
ALTER TABLE `log_barang`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tbl_barang`
--
ALTER TABLE `tbl_barang`
  ADD KEY `kode_barang` (`kode_barang`,`nama_barang`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `log_barang`
--
ALTER TABLE `log_barang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
