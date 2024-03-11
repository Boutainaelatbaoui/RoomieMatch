-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 28 Janvier 2015 à 23:12
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

-- --------------------------------------------------------

--
-- Structure de la table `ville`
--

CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=403 ;

--
-- Contenu de la table `ville`
--

INSERT INTO cities (id, name) VALUES
                                  (34, 'Azemmour'),
                                  (35, 'Benslimane'),
                                  (36, 'Bouznika'),
                                  (37, 'El Kelaa des Sraghna'),
                                  (38, 'Essaouira'),
                                  (39, 'Khénifra'),
                                  (40, 'Ksar El Kebir'),
                                  (41, 'Laayoune'),
                                  (42, 'Ouarzazate'),
                                  (43, 'Safi'),
                                  (44, 'Salé'),
                                  (45, 'Settat'),
                                  (46, 'Sidi Ifni'),
                                  (47, 'Sidi Kacem'),
                                  (48, 'Tanger'),
                                  (49, 'Tétouan'),
                                  (50, 'Tiznit'),
                                  (51, 'Zagora'),
                                  (52, 'M''diq'),
                                  (53, 'Asilah'),
                                  (54, 'Oujda'),
                                  (55, 'Taza'),
                                  (56, 'Berkane'),
                                  (57, 'Al Hoceïma'),
                                  (58, 'Larache'),
                                  (59, 'Khenifra'),
                                  (60, 'Khemisset'),
                                  (61, 'Beni Mellal'),
                                  (62, 'Khouribga'),
                                  (63, 'Fkih Ben Salah'),
                                  (64, 'Taroudant'),
                                  (65, 'Oued Zem'),
                                  (66, 'Ksar El Kebir'),
                                  (67, 'Sidi Bennour'),
                                  (68, 'Skhirat'),
                                  (69, 'Témara'),
                                  (70, 'Harhoura'),
                                  (71, 'Rabat'),
                                  (72, 'Marrakech'),
                                  (73, 'Casablanca');


