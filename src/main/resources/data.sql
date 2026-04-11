-- Includes lines 1, 2, 3, 3bis, 7, 7bis, 9, 10, and 12.

-- Intended special cases:
-- Line 7bis with loop
-- Line 7 Maison Blanche -> Villejuif/Ivry (0)
-- Line 10 Javel(0) -> Boulogne(4)
-- Line 7, 9, 12 Saint-Lazare/Opéra tunnels

-- NOTE: IDs for line, station, and terminus match the production database.
--       station_line IDs are never called directly by the frontend and only
--       accessed by database relationship so no use defining them explicitly.


-- LINES
INSERT INTO line (id, name, type) VALUES (1, '1', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (2, '2', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (3, '3', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (21, '3bis', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (7, '7', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (22, '7bis', 'SEMI_LOOP');
INSERT INTO line (id, name, type) VALUES (10, '10', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (9, '9', 'STANDARD');     
INSERT INTO line (id, name, type) VALUES (12, '12', 'STANDARD');    

-- TUNNELS
INSERT INTO tunnel_network (id, name) VALUES (1, 'Saint-Lazare');
INSERT INTO tunnel_network (id, name) VALUES (2, 'Gare du Nord');
INSERT INTO tunnel_network (id, name) VALUES (3, 'Saint-Michel');


-- STATIONS
INSERT INTO station (id,name,tunnel_id) VALUES
	 (1,'Abbesses',NULL),
	 (3,'Aimé Césaire',NULL),
	 (5,'Alexandre Dumas',NULL),
	 (6,'Alma - Marceau',NULL),
	 (7,'Anatole France',NULL),
	 (8,'Anvers',NULL),
	 (9,'Argentine',NULL),
	 (10,'Arts et Métiers',NULL),
	 (11,'Assemblée Nationale',NULL),
	 (12,'Aubervilliers - Pantin - Quatre Chemins',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (13,'Avenue Émile Zola',NULL),
	 (14,'Avron',NULL),
	 (18,'Barbès - Rochechouart',NULL),
	 (20,'Bastille',NULL),
	 (22,'Belleville',NULL),
	 (23,'Bérault',NULL),
	 (26,'Billancourt',NULL),
	 (28,'Blanche',NULL),
	 (32,'Bolivar',NULL),
	 (33,'Bonne Nouvelle',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (34,'Botzaris',NULL),
	 (36,'Boulogne - Jean Jaurès',NULL),
	 (37,'Boulogne - Pont de Saint-Cloud',NULL),
	 (38,'Bourse',NULL),
	 (41,'Buttes Chaumont',NULL),
	 (42,'Buzenval',NULL),
	 (43,'Cadet',NULL),
	 (46,'Cardinal Lemoine',NULL),
	 (48,'Censier - Daubenton',NULL),
	 (49,'Champs-Élysées - Clemenceau',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (50,'Chardon Lagache',NULL),
	 (52,'Charles de Gaulle - Étoile',NULL),
	 (53,'Charles Michels',NULL),
	 (54,'Charonne',NULL),
	 (56,'Château de Vincennes',NULL),
	 (57,'Château-Landon',NULL),
	 (59,'Châtelet - Les Halles',NULL),
	 (61,'Chaussée d’Antin - La Fayette',NULL),
	 (66,'Cluny - La Sorbonne',3),
	 (67,'Colonel Fabien',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (69,'Concorde',NULL),
	 (70,'Convention',NULL),
	 (71,'Corentin Cariou',NULL),
	 (72,'Corentin Celton',NULL),
	 (76,'Courcelles',NULL),
	 (77,'Couronnes',NULL),
	 (81,'Crimée',NULL),
	 (82,'Crois de Chavaux',NULL),
	 (83,'Danube',NULL),
	 (88,'Duroc',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (92,'Église d’Auteuil',NULL),
	 (94,'Esplanade de La Défense',NULL),
	 (96,'Europe',NULL),
	 (97,'Exelmans',NULL),
	 (99,'Falguière',NULL),
	 (102,'Fort d’Aubervilliers',NULL),
	 (103,'Franklin D. Roosevelt',NULL),
	 (104,'Front Populaire',NULL),
	 (107,'Gallieni',NULL),
	 (108,'Gambetta',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (109,'Gare d’Austerlitz',NULL),
	 (110,'Gare de l’Est',NULL),
	 (111,'Gare de Lyon',NULL),
	 (114,'George V',NULL),
	 (117,'Grands Boulevards',NULL),
	 (119,'Havre - Caumartin',1),
	 (122,'Hôtel de Ville',NULL),
	 (123,'Iéna',NULL),
	 (126,'Jasmin',NULL),
	 (127,'Jaurès',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (128,'Javel - André Citroën',NULL),
	 (130,'Jules Joffrin',NULL),
	 (131,'Jussieu',NULL),
	 (133,'La Chapelle',2),
	 (134,'La Courneuve - 8 Mai 1945',NULL),
	 (135,'La Défense',NULL),
	 (138,'La Motte-Picquet - Grenelle',NULL),
	 (139,'La Muette',NULL),
	 (141,'Lamarck - Caulaincourt',NULL),
	 (143,'Le Kremlin-Bicêtre',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (144,'Le Peletier',NULL),
	 (148,'Les Gobelins',NULL),
	 (149,'Les Sablons',NULL),
	 (153,'Louis Blanc',NULL),
	 (154,'Louise Michel',NULL),
	 (156,'Louvre - Rivoli',NULL),
	 (157,'Mabillon',NULL),
	 (158,'Madeleine',NULL),
	 (159,'Mairie d’Aubervilliers',NULL),
	 (160,'Mairie d’Issy',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (161,'Mairie d’Ivry',NULL),
	 (163,'Mairie de Montreuil',NULL),
	 (167,'Maison Blanche',NULL),
	 (172,'Malesherbes',NULL),
	 (173,'Maraîchers',NULL),
	 (174,'Marcadet - Poissonniers',NULL),
	 (175,'Marcel Sembat',NULL),
	 (176,'Marx Dormoy',NULL),
	 (177,'Maubert - Mutualité',NULL),
	 (178,'Ménilmontant',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (180,'Michel-Ange - Auteuil',NULL),
	 (181,'Michel-Ange - Molitor',NULL),
	 (182,'Mirabeau',NULL),
	 (183,'Miromesnil',NULL),
	 (184,'Monceau',NULL),
	 (186,'Montparnasse - Bienvenüe',NULL),
	 (189,'Nation',NULL),
	 (191,'Notre-Dame-de-Lorette',NULL),
	 (192,'Notre-Dame-des-Champs',NULL),
	 (193,'Oberkampf',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (194,'Odéon',NULL),
	 (196,'Opéra',1),
	 (198,'Palais Royale - Musée du Louvre',NULL),
	 (199,'Parmentier',NULL),
	 (201,'Pasteur',NULL),
	 (202,'Pelleport',NULL),
	 (203,'Père Lachaise',NULL),
	 (204,'Pereire',NULL),
	 (206,'Philippe Auguste',NULL),
	 (208,'Pierre et Marie Curie',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (209,'Pigalle',NULL),
	 (210,'Place d’Italie',NULL),
	 (211,'Place de Clichy',NULL),
	 (212,'Place des Fêtes',NULL),
	 (213,'Place Monge',NULL),
	 (216,'Poissionnière',NULL),
	 (217,'Pont de Levallois - Bécon',NULL),
	 (218,'Pont de Neuilly',NULL),
	 (219,'Pont de Sèvres',NULL),
	 (221,'Pont Marie',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (222,'Pont Neuf',NULL),
	 (223,'Porte Dauphine',NULL),
	 (224,'Porte d’Auteuil',NULL),
	 (225,'Porte de Bagnolet',NULL),
	 (226,'Porte de Champerret',NULL),
	 (228,'Porte de Choisy',NULL),
	 (231,'Porte de la Chapelle',NULL),
	 (232,'Porte de la Villette',NULL),
	 (233,'Porte de Montreuil',NULL),
	 (235,'Porte de Saint-Cloud',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (238,'Porte de Versailles',NULL),
	 (239,'Porte de Vincennes',NULL),
	 (240,'Porte des Lilas',NULL),
	 (241,'Porte d’Italie',NULL),
	 (242,'Porte d’Ivry',NULL),
	 (245,'Porte Maillot',NULL),
	 (246,'Pré-Saint-Gervais',NULL),
	 (247,'Pyramides',NULL),
	 (251,'Quatre-Septembre',NULL),
	 (253,'Ranelagh',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (255,'Réaumur - Sébastopol',NULL),
	 (256,'Rennes',NULL),
	 (257,'République',NULL),
	 (258,'Reuilly - Diderot',NULL),
	 (260,'Richelieu - Drouot',NULL),
	 (261,'Riquet',NULL),
	 (262,'Robespierre',NULL),
	 (264,'Rome',NULL),
	 (266,'Rue de la Pompe',NULL),
	 (267,'Rue des Boulets',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (268,'Rue du Bac',NULL),
	 (269,'Rue Saint-Maur',NULL),
	 (270,'Saint-Ambroise',NULL),
	 (271,'Saint-Augustin',1),
	 (275,'Saint-Fargeau',NULL),
	 (277,'Saint-Georges',NULL),
	 (280,'Saint-Lazare',1),
	 (281,'Saint-Mandé',NULL),
	 (285,'Saint-Paul',NULL),
	 (286,'Saint-Philippe du Roule',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (290,'Ségur',NULL),
	 (291,'Sentier',NULL),
	 (293,'Sèvres - Babylone',NULL),
	 (296,'Solférino',NULL),
	 (297,'Stalingrad',NULL),
	 (298,'Strasbourg - Saint-Denis',NULL),
	 (299,'Sully - Morland',NULL),
	 (301,'Temple',NULL),
	 (302,'Ternes',NULL),
	 (304,'Tolbiac',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (305,'Trinité - d’Estienne d’Orves',NULL),
	 (306,'Trocadéro',NULL),
	 (307,'Tuileries',NULL),
	 (308,'Vaneau',NULL),
	 (310,'Vaugirard',NULL),
	 (312,'Victor Hugo',NULL),
	 (314,'Villejuif - Léo Lagrange',NULL),
	 (315,'Villejuif - Louis Aragon',NULL),
	 (316,'Villejuif - Paul Vaillant-Couturier',NULL),
	 (317,'Villiers',NULL);
INSERT INTO station (id,name,tunnel_id) VALUES
	 (318,'Volontaires',NULL),
	 (319,'Voltaire',NULL),
	 (320,'Wagram',NULL);


-- TERMINI
-- Ligne 1
INSERT INTO terminus (line_id, station_id) VALUES (1, 56);
INSERT INTO terminus (line_id, station_id) VALUES (1, 135);

-- Ligne 2
INSERT INTO terminus (line_id, station_id) VALUES (2, 189);
INSERT INTO terminus (line_id, station_id) VALUES (2, 223);

-- Ligne 3
INSERT INTO terminus (line_id, station_id) VALUES (3, 107);
INSERT INTO terminus (line_id, station_id) VALUES (3, 217);

-- Ligne 3bis
INSERT INTO terminus (line_id, station_id) VALUES (21, 108);
INSERT INTO terminus (line_id, station_id) VALUES (21, 240);

-- Ligne 7
INSERT INTO terminus (line_id, station_id) VALUES (7, 134);
INSERT INTO terminus (line_id, station_id) VALUES (7, 161);
INSERT INTO terminus (line_id, station_id) VALUES (7, 315);

-- Ligne 7bis
INSERT INTO terminus (line_id, station_id) VALUES (22, 153);

-- Ligne 9
INSERT INTO terminus (line_id, station_id) VALUES (9, 163);
INSERT INTO terminus (line_id, station_id) VALUES (9, 219);

-- Ligne 10
INSERT INTO terminus (line_id, station_id) VALUES (10, 37);
INSERT INTO terminus (line_id, station_id) VALUES (10, 109);

-- Ligne 12
INSERT INTO terminus (line_id, station_id) VALUES (12, 159);
INSERT INTO terminus (line_id, station_id) VALUES (12, 160);


-- STATION TO LINE
-- Ligne 1
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (56, 1, 0, NULL, 'BIDIRECTIONAL'); -- Château de Vincennes
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (23, 1, 1, NULL, 'BIDIRECTIONAL'); -- Bérault
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (281, 1, 2, NULL, 'BIDIRECTIONAL'); -- Saint-Mandé
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (239, 1, 3, NULL, 'BIDIRECTIONAL'); -- Porte de Vincennes
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (189, 1, 4, NULL, 'BIDIRECTIONAL'); -- Nation
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (258, 1, 5, NULL, 'BIDIRECTIONAL'); -- Reuilly - Diderot
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (111, 1, 6, NULL, 'BIDIRECTIONAL'); -- Gare de Lyon
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (20, 1, 7, NULL, 'BIDIRECTIONAL'); -- Bastille
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (285, 1, 8, NULL, 'BIDIRECTIONAL'); -- Saint-Paul
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (122, 1, 9, NULL, 'BIDIRECTIONAL'); -- Hôtel de Ville
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (59, 1, 10, NULL, 'BIDIRECTIONAL'); -- Châtelet - Les Halles
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (156, 1, 11, NULL, 'BIDIRECTIONAL'); -- Louvre - Rivoli
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (198, 1, 12, NULL, 'BIDIRECTIONAL'); -- Palais Royale - Musée du Louvre
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (307, 1, 13, NULL, 'BIDIRECTIONAL'); -- Tuileries
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (69, 1, 14, NULL, 'BIDIRECTIONAL'); -- Concorde
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (49, 1, 15, NULL, 'BIDIRECTIONAL'); -- Champs-Élysées - Clemenceau
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (103, 1, 16, NULL, 'BIDIRECTIONAL'); -- Franklin D. Roosevelt
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (114, 1, 17, NULL, 'BIDIRECTIONAL'); -- George V
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (52, 1, 18, NULL, 'BIDIRECTIONAL'); -- Charles de Gaulle - Étoile
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (9, 1, 19, NULL, 'BIDIRECTIONAL'); -- Argentine
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (245, 1, 20, NULL, 'BIDIRECTIONAL'); -- Porte Maillot
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (149, 1, 21, NULL, 'BIDIRECTIONAL'); -- Les Sablons
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (218, 1, 22, NULL, 'BIDIRECTIONAL'); -- Pont de Neuilly
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (94, 1, 23, NULL, 'BIDIRECTIONAL'); -- Esplanade de La Défense
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (135, 1, 24, NULL, 'BIDIRECTIONAL'); -- La Défense

-- Ligne 2
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (189, 2, 0, NULL, 'BIDIRECTIONAL'); -- Nation
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (14, 2, 1, NULL, 'BIDIRECTIONAL'); -- Avron
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (5, 2, 2, NULL, 'BIDIRECTIONAL'); -- Alexandre Dumas
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (206, 2, 3, NULL, 'BIDIRECTIONAL'); -- Philippe Auguste
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (203, 2, 4, NULL, 'BIDIRECTIONAL'); -- Père Lachaise
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (178, 2, 5, NULL, 'BIDIRECTIONAL'); -- Ménilmontant
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (77, 2, 6, NULL, 'BIDIRECTIONAL'); -- Couronnes
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (22, 2, 7, NULL, 'BIDIRECTIONAL'); -- Belleville
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (67, 2, 8, NULL, 'BIDIRECTIONAL'); -- Colonel Fabien
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (127, 2, 9, NULL, 'BIDIRECTIONAL'); -- Jaurès
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (297, 2, 10, NULL, 'BIDIRECTIONAL'); -- Stalingrad
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (133, 2, 11, NULL, 'BIDIRECTIONAL'); -- La Chapelle
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (18, 2, 12, NULL, 'BIDIRECTIONAL'); -- Barbès - Rochechouart
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (8, 2, 13, NULL, 'BIDIRECTIONAL'); -- Anvers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (209, 2, 14, NULL, 'BIDIRECTIONAL'); -- Pigalle
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (28, 2, 15, NULL, 'BIDIRECTIONAL'); -- Blanche
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (211, 2, 16, NULL, 'BIDIRECTIONAL'); -- Place de Clichy
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (264, 2, 17, NULL, 'BIDIRECTIONAL'); -- Rome
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (317, 2, 18, NULL, 'BIDIRECTIONAL'); -- Villiers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (184, 2, 19, NULL, 'BIDIRECTIONAL'); -- Monceau
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (76, 2, 20, NULL, 'BIDIRECTIONAL'); -- Courcelles
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (302, 2, 21, NULL, 'BIDIRECTIONAL'); -- Ternes
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (52, 2, 22, NULL, 'BIDIRECTIONAL'); -- Charles de Gaulle - Étoile
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (312, 2, 23, NULL, 'BIDIRECTIONAL'); -- Victor Hugo
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (223, 2, 24, NULL, 'BIDIRECTIONAL'); -- Porte Dauphine

-- Ligne 3
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (107, 3, 0, NULL, 'BIDIRECTIONAL'); -- Gallieni
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (225, 3, 1, NULL, 'BIDIRECTIONAL'); -- Porte de Bagnolet
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (108, 3, 2, NULL, 'BIDIRECTIONAL'); -- Gambetta
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (203, 3, 3, NULL, 'BIDIRECTIONAL'); -- Père Lachaise
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (269, 3, 4, NULL, 'BIDIRECTIONAL'); -- Rue Saint-Maur
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (199, 3, 5, NULL, 'BIDIRECTIONAL'); -- Parmentier
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (257, 3, 6, NULL, 'BIDIRECTIONAL'); -- République
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (301, 3, 7, NULL, 'BIDIRECTIONAL'); -- Temple
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (10, 3, 8, NULL, 'BIDIRECTIONAL'); -- Arts et Métiers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (255, 3, 9, NULL, 'BIDIRECTIONAL'); -- Réaumur - Sébastopol
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (291, 3, 10, NULL, 'BIDIRECTIONAL'); -- Sentier
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (38, 3, 11, NULL, 'BIDIRECTIONAL'); -- Bourse
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (251, 3, 12, NULL, 'BIDIRECTIONAL'); -- Quatre-Septembre
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (196, 3, 13, NULL, 'BIDIRECTIONAL'); -- Opéra
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (119, 3, 14, NULL, 'BIDIRECTIONAL'); -- Havre - Caumartin
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (280, 3, 15, NULL, 'BIDIRECTIONAL'); -- Saint-Lazare
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (96, 3, 16, NULL, 'BIDIRECTIONAL'); -- Europe
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (317, 3, 17, NULL, 'BIDIRECTIONAL'); -- Villiers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (172, 3, 18, NULL, 'BIDIRECTIONAL'); -- Malesherbes
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (320, 3, 19, NULL, 'BIDIRECTIONAL'); -- Wagram
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (204, 3, 20, NULL, 'BIDIRECTIONAL'); -- Pereire
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (226, 3, 21, NULL, 'BIDIRECTIONAL'); -- Porte de Champerret
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (154, 3, 22, NULL, 'BIDIRECTIONAL'); -- Louise Michel
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (7, 3, 23, NULL, 'BIDIRECTIONAL'); -- Anatole France
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (217, 3, 24, NULL, 'BIDIRECTIONAL'); -- Pont de Levallois - Bécon

-- Ligne 3bis
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (108, 21, 0, NULL, 'BIDIRECTIONAL'); -- Gambetta
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (202, 21, 1, NULL, 'BIDIRECTIONAL'); -- Pelleport
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (275, 21, 2, NULL, 'BIDIRECTIONAL'); -- Saint-Fargeau
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (240, 21, 3, NULL, 'BIDIRECTIONAL'); -- Porte des Lilas

-- Ligne 7
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (134, 7, 0, NULL, 'BIDIRECTIONAL'); -- La Courneuve - 8 Mai 1945
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (102, 7, 1, NULL, 'BIDIRECTIONAL'); -- Fort d’Aubervilliers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (12, 7, 2, NULL, 'BIDIRECTIONAL'); -- Aubervilliers - Pantin - Quatre Chemins
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (232, 7, 3, NULL, 'BIDIRECTIONAL'); -- Porte de la Villette
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (71, 7, 4, NULL, 'BIDIRECTIONAL'); -- Corentin Cariou
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (81, 7, 5, NULL, 'BIDIRECTIONAL'); -- Crimée
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (261, 7, 6, NULL, 'BIDIRECTIONAL'); -- Riquet
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (297, 7, 7, NULL, 'BIDIRECTIONAL'); -- Stalingrad
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (153, 7, 8, NULL, 'BIDIRECTIONAL'); -- Louis Blanc
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (57, 7, 9, NULL, 'BIDIRECTIONAL'); -- Château-Landon
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (110, 7, 10, NULL, 'BIDIRECTIONAL'); -- Gare de l’Est
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (216, 7, 11, NULL, 'BIDIRECTIONAL'); -- Poissionnière
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (43, 7, 12, NULL, 'BIDIRECTIONAL'); -- Cadet
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (144, 7, 13, NULL, 'BIDIRECTIONAL'); -- Le Peletier
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (61, 7, 14, NULL, 'BIDIRECTIONAL'); -- Chaussée d’Antin - La Fayette
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (196, 7, 15, NULL, 'BIDIRECTIONAL'); -- Opéra
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (247, 7, 16, NULL, 'BIDIRECTIONAL'); -- Pyramides
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (198, 7, 17, NULL, 'BIDIRECTIONAL'); -- Palais Royale - Musée du Louvre
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (222, 7, 18, NULL, 'BIDIRECTIONAL'); -- Pont Neuf
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (59, 7, 19, NULL, 'BIDIRECTIONAL'); -- Châtelet - Les Halles
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (221, 7, 20, NULL, 'BIDIRECTIONAL'); -- Pont Marie
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (299, 7, 21, NULL, 'BIDIRECTIONAL'); -- Sully - Morland
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (131, 7, 22, NULL, 'BIDIRECTIONAL'); -- Jussieu
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (213, 7, 23, NULL, 'BIDIRECTIONAL'); -- Place Monge
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (48, 7, 24, NULL, 'BIDIRECTIONAL'); -- Censier - Daubenton
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (148, 7, 25, NULL, 'BIDIRECTIONAL'); -- Les Gobelins
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (210, 7, 26, NULL, 'BIDIRECTIONAL'); -- Place d’Italie
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (304, 7, 27, NULL, 'BIDIRECTIONAL'); -- Tolbiac
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (167, 7, 28, NULL, 'BIDIRECTIONAL'); -- Maison Blanche
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (241, 7, 29, 161, 'BIDIRECTIONAL'); -- Porte d’Italie
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (228, 7, 30, 161, 'BIDIRECTIONAL'); -- Porte de Choisy
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (242, 7, 31, 161, 'BIDIRECTIONAL'); -- Porte d’Ivry
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (208, 7, 32, 161, 'BIDIRECTIONAL'); -- Pierre et Marie Curie
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (161, 7, 33, 161, 'BIDIRECTIONAL'); -- Mairie d’Ivry
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (143, 7, 29, 315, 'BIDIRECTIONAL'); -- Le Kremlin-Bicêtre
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (314, 7, 30, 315, 'BIDIRECTIONAL'); -- Villejuif - Léo Lagrange
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (316, 7, 31, 315, 'BIDIRECTIONAL'); -- Villejuif - Paul Vaillant-Couturier
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (315, 7, 32, 315, 'BIDIRECTIONAL'); -- Villejuif - Louis Aragon

-- Ligne 7bis
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (153, 22, 0, NULL, 'BIDIRECTIONAL'); -- Louis Blanc
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (127, 22, 1, NULL, 'BIDIRECTIONAL'); -- Jaurès
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (32, 22, 2, NULL, 'BIDIRECTIONAL'); -- Bolivar
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (41, 22, 3, NULL, 'BIDIRECTIONAL'); -- Buttes Chaumont
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (34, 22, 4, NULL, 'BIDIRECTIONAL'); -- Botzaris
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (212, 22, 5, NULL, 'INCREASING'); -- Place des Fêtes
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (246, 22, 6, NULL, 'INCREASING'); -- Pré-Saint-Gervais
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (83, 22, 7, NULL, 'INCREASING'); -- Danube

-- Ligne 9
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (163, 9, 0, NULL, 'BIDIRECTIONAL'); -- Mairie de Montreuil
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (82, 9, 1, NULL, 'BIDIRECTIONAL'); -- Crois de Chavaux
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (262, 9, 2, NULL, 'BIDIRECTIONAL'); -- Robespierre
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (233, 9, 3, NULL, 'BIDIRECTIONAL'); -- Porte de Montreuil
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (173, 9, 4, NULL, 'BIDIRECTIONAL'); -- Maraîchers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (42, 9, 5, NULL, 'BIDIRECTIONAL'); -- Buzenval
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (189, 9, 6, NULL, 'BIDIRECTIONAL'); -- Nation
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (267, 9, 7, NULL, 'BIDIRECTIONAL'); -- Rue des Boulets
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (54, 9, 8, NULL, 'BIDIRECTIONAL'); -- Charonne
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (319, 9, 9, NULL, 'BIDIRECTIONAL'); -- Voltaire
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (270, 9, 10, NULL, 'BIDIRECTIONAL'); -- Saint-Ambroise
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (193, 9, 11, NULL, 'BIDIRECTIONAL'); -- Oberkampf
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (257, 9, 12, NULL, 'BIDIRECTIONAL'); -- République
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (298, 9, 13, NULL, 'BIDIRECTIONAL'); -- Strasbourg - Saint-Denis
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (33, 9, 14, NULL, 'BIDIRECTIONAL'); -- Bonne Nouvelle
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (117, 9, 15, NULL, 'BIDIRECTIONAL'); -- Grands Boulevards
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (260, 9, 16, NULL, 'BIDIRECTIONAL'); -- Richelieu - Drouot
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (61, 9, 17, NULL, 'BIDIRECTIONAL'); -- Chaussée d’Antin - La Fayette
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (119, 9, 18, NULL, 'BIDIRECTIONAL'); -- Havre - Caumartin
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (271, 9, 19, NULL, 'BIDIRECTIONAL'); -- Saint-Augustin
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (183, 9, 20, NULL, 'BIDIRECTIONAL'); -- Miromesnil
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (286, 9, 21, NULL, 'BIDIRECTIONAL'); -- Saint-Philippe du Roule
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (103, 9, 22, NULL, 'BIDIRECTIONAL'); -- Franklin D. Roosevelt
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (6, 9, 23, NULL, 'BIDIRECTIONAL'); -- Alma - Marceau
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (123, 9, 24, NULL, 'BIDIRECTIONAL'); -- Iéna
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (306, 9, 25, NULL, 'BIDIRECTIONAL'); -- Trocadéro
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (266, 9, 26, NULL, 'BIDIRECTIONAL'); -- Rue de la Pompe
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (139, 9, 27, NULL, 'BIDIRECTIONAL'); -- La Muette
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (253, 9, 28, NULL, 'BIDIRECTIONAL'); -- Ranelagh
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (126, 9, 29, NULL, 'BIDIRECTIONAL'); -- Jasmin
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (180, 9, 30, NULL, 'BIDIRECTIONAL'); -- Michel-Ange - Auteuil
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (181, 9, 31, NULL, 'BIDIRECTIONAL'); -- Michel-Ange - Molitor
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (97, 9, 32, NULL, 'BIDIRECTIONAL'); -- Exelmans
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (235, 9, 33, NULL, 'BIDIRECTIONAL'); -- Porte de Saint-Cloud
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (175, 9, 34, NULL, 'BIDIRECTIONAL'); -- Marcel Sembat
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (26, 9, 35, NULL, 'BIDIRECTIONAL'); -- Billancourt
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (219, 9, 36, NULL, 'BIDIRECTIONAL'); -- Pont de Sèvres

-- Ligne 10
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (109, 10, 0, NULL, 'BIDIRECTIONAL'); -- Gare d’Austerlitz
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (131, 10, 1, NULL, 'BIDIRECTIONAL'); -- Jussieu
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (46, 10, 2, NULL, 'BIDIRECTIONAL'); -- Cardinal Lemoine
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (177, 10, 3, NULL, 'BIDIRECTIONAL'); -- Maubert - Mutualité
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (66, 10, 4, NULL, 'BIDIRECTIONAL'); -- Cluny - La Sorbonne
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (194, 10, 5, NULL, 'BIDIRECTIONAL'); -- Odéon
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (157, 10, 6, NULL, 'BIDIRECTIONAL'); -- Mabillon
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (293, 10, 7, NULL, 'BIDIRECTIONAL'); -- Sèvres - Babylone
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (308, 10, 8, NULL, 'BIDIRECTIONAL'); -- Vaneau
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (88, 10, 9, NULL, 'BIDIRECTIONAL'); -- Duroc
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (290, 10, 10, NULL, 'BIDIRECTIONAL'); -- Ségur
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (138, 10, 11, NULL, 'BIDIRECTIONAL'); -- La Motte-Picquet - Grenelle
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (13, 10, 12, NULL, 'BIDIRECTIONAL'); -- Avenue Émile Zola
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (53, 10, 13, NULL, 'BIDIRECTIONAL'); -- Charles Michels
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (128, 10, 14, NULL, 'BIDIRECTIONAL'); -- Javel - André Citroën
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (92, 10, 15, NULL, 'INCREASING'); -- Église d’Auteuil
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (182, 10, 15, NULL, 'DECREASING'); -- Mirabeau
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (50, 10, 16, NULL, 'DECREASING'); -- Chardon Lagache
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (180, 10, 16, NULL, 'INCREASING'); -- Michel-Ange - Auteuil
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (181, 10, 17, NULL, 'DECREASING'); -- Michel-Ange - Molitor
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (224, 10, 17, NULL, 'INCREASING'); -- Porte d’Auteuil
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (36, 10, 18, NULL, 'BIDIRECTIONAL'); -- Boulogne - Jean Jaurès
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (37, 10, 19, NULL, 'BIDIRECTIONAL'); -- Boulogne - Pont de Saint-Cloud

-- Ligne 12
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (159, 12, 0, NULL, 'BIDIRECTIONAL'); -- Mairie d’Aubervilliers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (3, 12, 1, NULL, 'BIDIRECTIONAL'); -- Aimé Césaire
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (104, 12, 2, NULL, 'BIDIRECTIONAL'); -- Front Populaire
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (231, 12, 3, NULL, 'BIDIRECTIONAL'); -- Porte de la Chapelle
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (176, 12, 4, NULL, 'BIDIRECTIONAL'); -- Marx Dormoy
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (174, 12, 5, NULL, 'BIDIRECTIONAL'); -- Marcadet - Poissonniers
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (130, 12, 6, NULL, 'BIDIRECTIONAL'); -- Jules Joffrin
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (141, 12, 7, NULL, 'BIDIRECTIONAL'); -- Lamarck - Caulaincourt
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (1, 12, 8, NULL, 'BIDIRECTIONAL'); -- Abbesses
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (209, 12, 9, NULL, 'BIDIRECTIONAL'); -- Pigalle
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (277, 12, 10, NULL, 'BIDIRECTIONAL'); -- Saint-Georges
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (191, 12, 11, NULL, 'BIDIRECTIONAL'); -- Notre-Dame-de-Lorette
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (305, 12, 12, NULL, 'BIDIRECTIONAL'); -- Trinité - d’Estienne d’Orves
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (280, 12, 13, NULL, 'BIDIRECTIONAL'); -- Saint-Lazare
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (158, 12, 14, NULL, 'BIDIRECTIONAL'); -- Madeleine
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (69, 12, 15, NULL, 'BIDIRECTIONAL'); -- Concorde
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (11, 12, 16, NULL, 'BIDIRECTIONAL'); -- Assemblée Nationale
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (296, 12, 17, NULL, 'BIDIRECTIONAL'); -- Solférino
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (268, 12, 18, NULL, 'BIDIRECTIONAL'); -- Rue du Bac
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (293, 12, 19, NULL, 'BIDIRECTIONAL'); -- Sèvres - Babylone
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (256, 12, 20, NULL, 'BIDIRECTIONAL'); -- Rennes
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (192, 12, 21, NULL, 'BIDIRECTIONAL'); -- Notre-Dame-des-Champs
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (186, 12, 22, NULL, 'BIDIRECTIONAL'); -- Montparnasse - Bienvenüe
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (99, 12, 23, NULL, 'BIDIRECTIONAL'); -- Falguière
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (201, 12, 24, NULL, 'BIDIRECTIONAL'); -- Pasteur
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (318, 12, 25, NULL, 'BIDIRECTIONAL'); -- Volontaires
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (310, 12, 26, NULL, 'BIDIRECTIONAL'); -- Vaugirard
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (70, 12, 27, NULL, 'BIDIRECTIONAL'); -- Convention
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (238, 12, 28, NULL, 'BIDIRECTIONAL'); -- Porte de Versailles
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (72, 12, 29, NULL, 'BIDIRECTIONAL'); -- Corentin Celton
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (160, 12, 30, NULL, 'BIDIRECTIONAL'); -- Mairie d’Issy