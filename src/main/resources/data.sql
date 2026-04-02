-- Truncated metro system
-- Line 1: Nation (0) -> Saint-Paul (4)
-- Line 2: Nation (0) -> Père Lachaise (4)
-- Line 3: Gambetta(0) -> Père Lachaise (1)
-- Line 3bis: Gambetta(0) -> Pelleport (1)

-- Special cases:
-- Line 7bis with loop
-- Line 7 Maison Blanche -> Villejuif/Ivry (0)
-- Line 10 Javel(0) -> Boulogne(4)
-- Line 7, 9, 12 Saint-Lazare/Opéra tunnels 

INSERT INTO line (id, name, type) VALUES (1, '1', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (2, '2', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (3, '3', 'STANDARD');
INSERT INTO line (id, name, type) VALUES (4, '3bis', 'STANDARD');  -- id: 4

INSERT INTO line (id, name, type) VALUES (5, '7bis', 'SEMI_LOOP'); -- id: 5
INSERT INTO line (id, name, type) VALUES (7, '7', 'STANDARD');     -- id: 6
INSERT INTO line (id, name, type) VALUES (10, '10', 'STANDARD');    -- id: 7

INSERT INTO line (id, name, type) VALUES (9, '9', 'STANDARD');     
INSERT INTO line (id, name, type) VALUES (12, '12', 'STANDARD');    

-- tunnel
INSERT INTO tunnel_network (name) VALUES ('Saint-Lazare');  -- id: 1

-- Truncated metro
INSERT INTO station (name) VALUES ('Saint-Paul');       -- id: 1
INSERT INTO station (name) VALUES ('Bastille');         -- id: 2
INSERT INTO station (name) VALUES ('Gare de Lyon');     -- id: 3
INSERT INTO station (name) VALUES ('Reuilly-Diderot');  -- id: 4
INSERT INTO station (name) VALUES ('Nation');           -- id: 5
INSERT INTO station (name) VALUES ('Avron');            -- id: 6
INSERT INTO station (name) VALUES ('Alexandre Dumas');  -- id: 7
INSERT INTO station (name) VALUES ('Philippe Auguste'); -- id: 8
INSERT INTO station (name) VALUES ('Père Lachaise');    -- id: 9
INSERT INTO station (name) VALUES ('Gambetta');         -- id: 10
INSERT INTO station (name) VALUES ('Pelleport');        -- id: 11

-- 7bis
INSERT INTO station (name) VALUES ('Louis Blanc');      -- id: 12
INSERT INTO station (name) VALUES ('Jaurès');           -- id: 13
INSERT INTO station (name) VALUES ('Bolivar');          -- id: 14
INSERT INTO station (name) VALUES ('Buttes Chaumont');  -- id: 15
INSERT INTO station (name) VALUES ('Botzaris');         -- id: 16
INSERT INTO station (name) VALUES ('Place des Fêtes');  -- id: 17
INSERT INTO station (name) VALUES ('Pré-Saint-Gervais');-- id: 18
INSERT INTO station (name) VALUES ('Danube');           -- id: 19

-- 7
INSERT INTO station (name) VALUES ('Maison Blanche');   -- id: 20
INSERT INTO station (name) VALUES ('Le Kremlin');       -- id: 21
INSERT INTO station (name) VALUES ('Villejuif-Leo');    -- id: 22
INSERT INTO station (name) VALUES ('Villejuif-Paul');   -- id: 23
INSERT INTO station (name) VALUES ('Villejuif-Louis');  -- id: 24
INSERT INTO station (name) VALUES ('Porte d''Italie');  -- id: 25
INSERT INTO station (name) VALUES ('Porte de Choisy');  -- id: 26
INSERT INTO station (name) VALUES ('Porte d''Ivry');    -- id: 27
INSERT INTO station (name) VALUES ('Curie');            -- id: 28
INSERT INTO station (name) VALUES ('Mairie d''Ivry');   -- id: 29

-- 10
INSERT INTO station (name) VALUES ('Javel');            -- id: 30
INSERT INTO station (name) VALUES ('Église d''Auteuil');-- id: 31
INSERT INTO station (name) VALUES ('Michel d''Auteuil');-- id: 32
INSERT INTO station (name) VALUES ('Porte d''Auteuil'); -- id: 33
INSERT INTO station (name) VALUES ('Boulogne');         -- id: 34
INSERT INTO station (name) VALUES ('Michel Molitor');   -- id: 35
INSERT INTO station (name) VALUES ('Chardon Lagache');  -- id: 36
INSERT INTO station (name) VALUES ('Mirabeau');         -- id: 37

-- 7 (continued)
INSERT INTO station (name, tunnel_id) VALUES ('Opéra', 1);          -- id: 38

-- 9 La Fayette(0) -> Saint-Augustin(2)
INSERT INTO station (name, tunnel_id) VALUES ('La Fayette', NULL);     -- id: 39
INSERT INTO station (name, tunnel_id) VALUES ('Havre Caumartin', 1);-- id: 40
INSERT INTO station (name, tunnel_id) VALUES ('Saint-Augustin', 1); -- id: 41

-- 12 Saint-Lazare(0) -> Madeleine(1)
INSERT INTO station (name, tunnel_id) VALUES ('Saint-Lazare', 1);   -- id: 42
INSERT INTO station (name, tunnel_id) VALUES ('Madeleine', NULL);      -- id: 43

-- 1 termini: Nation, Saint-Paul
INSERT INTO terminus (line_id, station_id) VALUES (1, 1);
INSERT INTO terminus (line_id, station_id) VALUES (1, 5);

-- 2 termini: Nation, Père Lachaise
INSERT INTO terminus (line_id, station_id) VALUES (2, 5);
INSERT INTO terminus (line_id, station_id) VALUES (2, 9);

-- 3 termini: Père Lachaise, Gambetta
INSERT INTO terminus (line_id, station_id) VALUES (3, 9);
INSERT INTO terminus (line_id, station_id) VALUES (3, 10);

-- 3bis termini: Gambetta, Pelleport
INSERT INTO terminus (line_id, station_id) VALUES (4, 10);
INSERT INTO terminus (line_id, station_id) VALUES (4, 11);

-- 7bis termini: Louis-Blanc (loop default)
INSERT INTO terminus (line_id, station_id) VALUES (5, 12);

-- 7 termini: Villejuif-Louis, Mairie d'Ivry, Opéra 
INSERT INTO terminus (line_id, station_id) VALUES (7, 24);
INSERT INTO terminus (line_id, station_id) VALUES (7, 29);
INSERT INTO terminus (line_id, station_id) VALUES (7, 38);

-- 9 termini: La Fayette, Saint-Augistin
INSERT INTO terminus (line_id, station_id) VALUES (9, 39);
INSERT INTO terminus (line_id, station_id) VALUES (9, 41);

-- 10 termini: Javel, Boulogne
INSERT INTO terminus (line_id, station_id) VALUES (10, 30);
INSERT INTO terminus (line_id, station_id) VALUES (10, 34);

-- 12 termini: Saint-Lazare, Madeleine
INSERT INTO terminus (line_id, station_id) VALUES (12, 42);
INSERT INTO terminus (line_id, station_id) VALUES (12, 43);

-- 1: Nation(0) -> Saint-Paul (4)
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (5, 1, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (4, 1, 1, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (3, 1, 2, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (2, 1, 3, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (1, 1, 4, 'BIDIRECTIONAL');

-- 2: Nation(0) -> Père Lachaise (4)
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (5, 2, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (6, 2, 1, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (7, 2, 2, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (8, 2, 3, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (9, 2, 4, 'BIDIRECTIONAL');

-- 3: Gambetta(0) -> Père Lachaise (1)
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (10, 3, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (9, 3, 1, 'BIDIRECTIONAL');

-- 3bis: Gambetta(0) -> Pelleport(1)
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (10, 4, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (11, 4, 1, 'BIDIRECTIONAL');

-- 7bis: Louis-Blanc(0) -> loop
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (12, 5, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (13, 5, 1, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (14, 5, 2, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (15, 5, 3, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (16, 5, 4, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (17, 5, 5, 'INCREASING');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (18, 5, 6, 'INCREASING');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (19, 5, 7, 'INCREASING');

-- 7: Ivry(0) -> Maison Blanche(5)
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (29, 7, 0, 29, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (28, 7, 1, 29, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (27, 7, 2, 29, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (26, 7, 3, 29, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (25, 7, 4, 29, 'BIDIRECTIONAL');
-- Maison Blanche
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (20, 7, 5, NULL, 'BIDIRECTIONAL');
-- Opéra
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (38, 7, 6, NULL, 'BIDIRECTIONAL');

-- 7: Villejuif-Louis(0) -> Maison Blanche(added)
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (24, 7, 0, 24, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (23, 7, 1, 24, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (22, 7, 2, 24, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, branch_id, direction) VALUES (21, 7, 3, 24, 'BIDIRECTIONAL');

-- 9: La Fayette(0) -> Saint-Augustin(2)
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (39, 9, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (40, 9, 1, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (41, 9, 2, 'BIDIRECTIONAL');

-- 10: Javel(0) -> Auteuil -> Boulogne(4) -> Molitor
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (30, 10, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (31, 10, 1, 'INCREASING');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (32, 10, 2, 'INCREASING');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (33, 10, 3, 'INCREASING');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (34, 10, 4, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (35, 10, 3, 'DECREASING');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (36, 10, 2, 'DECREASING');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (37, 10, 1, 'DECREASING');

-- 12: Saint-Lazare(0) -> Madeleine(1)
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (42, 12, 0, 'BIDIRECTIONAL');
INSERT INTO station_line (station_id, line_id, position, direction) VALUES (43, 12, 1, 'BIDIRECTIONAL');