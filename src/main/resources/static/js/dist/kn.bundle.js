import Fuse from 'https://cdn.jsdelivr.net/npm/fuse.js@7.1.0/dist/fuse.mjs';

const FR_TEXT = {
    "title": {
        "translation": "The Knowledge"
    },

    "subtitle": {
        "translation": "Métro de Paris"   
    },

    "progress-title": {
        "translation": "Progrès"
    },
    
    "take-line": {
        "translation": "Prenez la ligne…"
    },

    "current-station": {
        "translation": "(Vous êtes à $station)"
    },

    "towards": {
        "translation": "Direction…"
    },

    "to-station": {
        "translation": "Jusqu'à la station…"
    },

    "tunnel": {
        "translation": "➡️ Correspondance à la ligne $line en passant par $tunnel_station."
    },

    "arrived": {
        "translation": "🎉 Vous êtes arrivé ! 🎉"
    },

    "fail": {
        "translation": "❌"
    },

    "refresh": {
        "translation": "Rechargez pour reésayer."
    },

    "origin-not-on-line": {
        "translation": "❌ $origin n'est pas desservie par la ligne $line"
    },

    "no-tunnel-to-line": {
        "translation": "❌ Pas de liaison entre $origin et la ligne $line"
    },

    "dest-not-on-line": {
        "translation": "❌ $destination n'est pas desservie par la ligne $line"
    },

    "wrong-branch": {
        "translation": "❌ $destination n'est pas située sur cette branche"
    },

    "wrong-way": {
        "translation": "❌ Mauvais direction pour aller à $destination"
    },

    "wrong-way-one-way": {
        "translation": "❌ Cette direction n'est pas desservie par $origin"
    },

    "info1": {
        "translation": "Inspirée par l'examen, célèbrement rigoureux,"
    },

    "info2": {
        "translation": "pour devenir chauffeur du taxi londonien."
    },

    "info3": {
        "translation": "Vous avez deux stations du métro. Essayez de trouver une itinéraire valide pour voyager de l'origine à la destinatoin. Sans plan, et sans erreur. Testez votre connaissance du réseau du métro."
    }
};

const EN_TEXT = {
    "title": {
        "translation": "The Knowledge"
    },

    "subtitle": {
        "translation": "Paris Metro"
    },

    "progress-title": {
        "translation": "Progress"
    },
    
    "take-line": {
        "translation": "Take line…"
    },

    "current-station": {
        "translation": "(You are at $station)"
    },

    "towards": {
        "translation": "Towards…"
    },

    "to-station": {
        "translation": "To the station…"
    },

    "tunnel": {
        "translation": "➡️ Connection to line $line via $tunnel_station."
    },

    "arrived": {
        "translation": "🎉 You have arrived! 🎉"
    },

    "fail": {
        "translation": "❌"
    },

    "refresh": {
        "translation": "Refresh to try again."
    },

    "origin-not-on-line": {
        "translation": "❌ $origin does not connect to line $line"
    },

    "no-tunnel-to-line": {
        "translation": "❌ No connection to line $line from $origin"
    },

    "dest-not-on-line": {
        "translation": "❌ $destination is not on line $line"
    },

    "wrong-branch": {
        "translation": "❌ Wrong branch line for $destination"
    },

    "wrong-way": {
        "translation": "❌ Wrong way to get to $destination"
    },

    "wrong-way-one-way": {
        "translation": "❌ Can't go that way from $origin"
    },

    "info1": {
        "translation": "Inspired by the famously challenging"
    },

    "info2": {
        "translation": "course to become a London taxi driver."
    },

    "info3": {
        "translation": "You are given two metro stations. Try to find a valid path to travel between them. No maps, no mistakes. Test how well you know how to navigate the metro."
    }
};

const LINES = [
      {},
      {
          "id": 1,
          "name": "1",
          "termini": [
              {
                  "id": 135,
                  "name": "La Défense"
              },
              {
                  "id": 56,
                  "name": "Château de Vincennes"
              }
          ]
      },
      {
          "id": 2,
          "name": "2",
          "termini": [
              {
                  "id": 223,
                  "name": "Porte Dauphine"
              },
              {
                  "id": 189,
                  "name": "Nation"
              }
          ]
      },
      {
          "id": 3,
          "name": "3",
          "termini": [
              {
                  "id": 217,
                  "name": "Levallois"
              },
              {
                  "id": 107,
                  "name": "Gallieni"
              }
          ]
      },
      {
          "id": 4,
          "name": "4",
          "termini": [
              {
                  "id": 230,
                  "name": "Porte de Clignancourt"
              },
              {
                  "id": 15,
                  "name": "Bagneux"
              }
          ]
      },
      {
          "id": 5,
          "name": "5",
          "termini": [
              {
                  "id": 29,
                  "name": "Bobigny"
              },
              {
                  "id": 210,
                  "name": "Place d’Italie"
              }
          ]
      },
      {
          "id": 6,
          "name": "6",
          "termini": [
              {
                  "id": 52,
                  "name": "Étoile"
              },
              {
                  "id": 189,
                  "name": "Nation"
              }
          ]
      },
      {
          "id": 7,
          "name": "7",
          "termini": [
              {
                  "id": 134,
                  "name": "La Courneuve"
              },
              {
                  "id": 161,
                  "name": "Ivry"
              },
              {
                  "id": 315,
                  "name": "Villejuif"
              }
          ]
      },
      {
          "id": 8,
          "name": "8",
          "termini": [
              {
                  "id": 16,
                  "name": "Balard"
              },
              {
                  "id": 215,
                  "name": "Créteil"
              }
          ]
      },
      {
          "id": 9,
          "name": "9",
          "termini": [
              {
                  "id": 219,
                  "name": "Pont de Sèvres"
              },
              {
                  "id": 163,
                  "name": "Montreuil"
              }
          ]
      },
      {
          "id": 10,
          "name": "10",
          "termini": [
              {
                  "id": 37,
                  "name": "Boulogne"
              },
              {
                  "id": 109,
                  "name": "Gare d’Austerlitz"
              }
          ]
      },
      {
          "id": 11,
          "name": "11",
          "termini": [
              {
                  "id": 265,
                  "name": "Rosny-Bois-Perrier"
              },
              {
                  "id": 59,
                  "name": "Châtelet"
              }
          ]
      },
      {
          "id": 12,
          "name": "12",
          "termini": [
              {
                  "id": 159,
                  "name": "Aubervilliers"
              },
              {
                  "id": 160,
                  "name": "Mairie d’Issy"
              }
          ]
      },
      {
          "id": 13,
          "name": "13",
          "termini": [
              {
                  "id": 147,
                  "name": "Asnières - Gennevilliers"
              },
              {
                  "id": 274,
                  "name": "Saint-Denis"
              },
              {
                  "id": 60,
                  "name": "Châtillon"
              }
          ]
      },
      {
          "id": 14,
          "name": "14",
          "termini": [
              {
                  "id": 272,
                  "name": "Saint-Denis"
              },
              {
                  "id": 2,
                  "name": "Aéroport d’Orly"
              }
          ]
      },
      {},
      {},
      {},
      {},
      {},
      {},
      {
          "id": 21,
          "name": "3bis",
          "termini": [
              {
                  "id": 240,
                  "name": "Porte des Lilas"
              },
              {
                  "id": 108,
                  "name": "Gambetta"
              }
          ]
      },
      {
          "id": 22,
          "name": "7bis",
          "termini": [
              {
                  "id": 153,
                  "name": "Louis Blanc"
              }
          ]
      }
];

const STATIONS = [
      {
          "id": 1,
          "name": "Abbesses",
          "tags": []
      },
      {
          "id": 2,
          "name": "Aéroport d’Orly",
          "tags": []
      },
      {
          "id": 3,
          "name": "Aimé Césaire",
          "tags": []
      },
      {
          "id": 4,
          "name": "Alésia",
          "tags": []
      },
      {
          "id": 5,
          "name": "Alexandre Dumas",
          "tags": []
      },
      {
          "id": 6,
          "name": "Alma - Marceau",
          "tags": []
      },
      {
          "id": 7,
          "name": "Anatole France",
          "tags": []
      },
      {
          "id": 8,
          "name": "Anvers",
          "tags": []
      },
      {
          "id": 9,
          "name": "Argentine",
          "tags": []
      },
      {
          "id": 10,
          "name": "Arts et Métiers",
          "tags": []
      },
      {
          "id": 11,
          "name": "Assemblée Nationale",
          "tags": []
      },
      {
          "id": 12,
          "name": "Aubervilliers - Pantin - Quatre Chemins",
          "tags": []
      },
      {
          "id": 13,
          "name": "Avenue Émile Zola",
          "tags": []
      },
      {
          "id": 14,
          "name": "Avron",
          "tags": []
      },
      {
          "id": 15,
          "name": "Bagneux - Lucie Aubrac",
          "tags": []
      },
      {
          "id": 16,
          "name": "Balard",
          "tags": []
      },
      {
          "id": 17,
          "name": "Barbara",
          "tags": []
      },
      {
          "id": 18,
          "name": "Barbès - Rochechouart",
          "tags": []
      },
      {
          "id": 19,
          "name": "Basilique de Saint-Denis",
          "tags": []
      },
      {
          "id": 20,
          "name": "Bastille",
          "tags": []
      },
      {
          "id": 21,
          "name": "Bel-Air",
          "tags": []
      },
      {
          "id": 22,
          "name": "Belleville",
          "tags": []
      },
      {
          "id": 23,
          "name": "Bérault",
          "tags": []
      },
      {
          "id": 24,
          "name": "Bercy",
          "tags": []
      },
      {
          "id": 25,
          "name": "Bibiliothèque François Mitterand",
          "tags": []
      },
      {
          "id": 26,
          "name": "Billancourt",
          "tags": []
      },
      {
          "id": 27,
          "name": "Bir-Hakeim",
          "tags": []
      },
      {
          "id": 28,
          "name": "Blanche",
          "tags": []
      },
      {
          "id": 29,
          "name": "Bobigny - Pablo Picasso",
          "tags": []
      },
      {
          "id": 30,
          "name": "Bobigny - Pantin - Raymond Queneau",
          "tags": []
      },
      {
          "id": 31,
          "name": "Boissière",
          "tags": []
      },
      {
          "id": 32,
          "name": "Bolivar",
          "tags": []
      },
      {
          "id": 33,
          "name": "Bonne Nouvelle",
          "tags": []
      },
      {
          "id": 34,
          "name": "Botzaris",
          "tags": []
      },
      {
          "id": 35,
          "name": "Boucicaut",
          "tags": []
      },
      {
          "id": 36,
          "name": "Boulogne - Jean Jaurès",
          "tags": []
      },
      {
          "id": 37,
          "name": "Boulogne - Pont de Saint-Cloud",
          "tags": []
      },
      {
          "id": 38,
          "name": "Bourse",
          "tags": []
      },
      {
          "id": 39,
          "name": "Bréguet - Sabin",
          "tags": []
      },
      {
          "id": 40,
          "name": "Brochant",
          "tags": []
      },
      {
          "id": 41,
          "name": "Buttes Chaumont",
          "tags": []
      },
      {
          "id": 42,
          "name": "Buzenval",
          "tags": []
      },
      {
          "id": 43,
          "name": "Cadet",
          "tags": []
      },
      {
          "id": 44,
          "name": "Cambronne",
          "tags": []
      },
      {
          "id": 45,
          "name": "Campo-Formio",
          "tags": []
      },
      {
          "id": 46,
          "name": "Cardinal Lemoine",
          "tags": []
      },
      {
          "id": 47,
          "name": "Carrefour Pleyel",
          "tags": []
      },
      {
          "id": 48,
          "name": "Censier - Daubenton",
          "tags": []
      },
      {
          "id": 49,
          "name": "Champs-Élysées - Clemenceau",
          "tags": []
      },
      {
          "id": 50,
          "name": "Chardon Lagache",
          "tags": []
      },
      {
          "id": 51,
          "name": "Charenton - Écoles",
          "tags": []
      },
      {
          "id": 52,
          "name": "Charles de Gaulle - Étoile",
          "tags": []
      },
      {
          "id": 53,
          "name": "Charles Michels",
          "tags": []
      },
      {
          "id": 54,
          "name": "Charonne",
          "tags": []
      },
      {
          "id": 55,
          "name": "Château d’Eau",
          "tags": []
      },
      {
          "id": 56,
          "name": "Château de Vincennes",
          "tags": []
      },
      {
          "id": 57,
          "name": "Château-Landon",
          "tags": []
      },
      {
          "id": 58,
          "name": "Château Rouge",
          "tags": []
      },
      {
          "id": 59,
          "name": "Châtelet - Les Halles",
          "tags": ["Chatelet"]
      },
      {
          "id": 60,
          "name": "Châtillon - Montrouge",
          "tags": []
      },
      {
          "id": 61,
          "name": "Chaussée d’Antin - La Fayette",
          "tags": ["Chaussée d’Antin"]
      },
      {
          "id": 62,
          "name": "Chemin Vert",
          "tags": []
      },
      {
          "id": 63,
          "name": "Chevaleret",
          "tags": []
      },
      {
          "id": 64,
          "name": "Chevilly - Larue",
          "tags": []
      },
      {
          "id": 65,
          "name": "Cité",
          "tags": []
      },
      {
          "id": 66,
          "name": "Cluny - La Sorbonne",
          "tags": []
      },
      {
          "id": 67,
          "name": "Colonel Fabien",
          "tags": []
      },
      {
          "id": 68,
          "name": "Commerce",
          "tags": []
      },
      {
          "id": 69,
          "name": "Concorde",
          "tags": []
      },
      {
          "id": 70,
          "name": "Convention",
          "tags": []
      },
      {
          "id": 71,
          "name": "Corentin Cariou",
          "tags": []
      },
      {
          "id": 72,
          "name": "Corentin Celton",
          "tags": []
      },
      {
          "id": 73,
          "name": "Corvisart",
          "tags": []
      },
      {
          "id": 74,
          "name": "Coteaux Beauclair",
          "tags": []
      },
      {
          "id": 75,
          "name": "Cour Saint-Émilion",
          "tags": []
      },
      {
          "id": 76,
          "name": "Courcelles",
          "tags": []
      },
      {
          "id": 77,
          "name": "Couronnes",
          "tags": []
      },
      {
          "id": 78,
          "name": "Créteil - L’Échat",
          "tags": []
      },
      {
          "id": 79,
          "name": "Créteil - Préfecture",
          "tags": []
      },
      {
          "id": 80,
          "name": "Créteil - Univertsité",
          "tags": []
      },
      {
          "id": 81,
          "name": "Crimée",
          "tags": []
      },
      {
          "id": 82,
          "name": "Crois de Chavaux",
          "tags": []
      },
      {
          "id": 83,
          "name": "Danube",
          "tags": []
      },
      {
          "id": 84,
          "name": "Daumesnil",
          "tags": []
      },
      {
          "id": 85,
          "name": "Denfert - Rochereau",
          "tags": []
      },
      {
          "id": 86,
          "name": "Dugommier",
          "tags": []
      },
      {
          "id": 87,
          "name": "Dupleix",
          "tags": []
      },
      {
          "id": 88,
          "name": "Duroc",
          "tags": []
      },
      {
          "id": 89,
          "name": "École Militaire",
          "tags": []
      },
      {
          "id": 90,
          "name": "École Vétérinaire de Maisons-Alfort",
          "tags": []
      },
      {
          "id": 91,
          "name": "Edgar Quinet",
          "tags": []
      },
      {
          "id": 92,
          "name": "Église d’Auteuil",
          "tags": []
      },
      {
          "id": 93,
          "name": "Église de Pantin",
          "tags": []
      },
      {
          "id": 94,
          "name": "Esplanade de La Défense",
          "tags": []
      },
      {
          "id": 95,
          "name": "Étienne Marcel",
          "tags": []
      },
      {
          "id": 96,
          "name": "Europe",
          "tags": []
      },
      {
          "id": 97,
          "name": "Exelmans",
          "tags": []
      },
      {
          "id": 98,
          "name": "Faidherbe - Chaligny",
          "tags": []
      },
      {
          "id": 99,
          "name": "Falguière",
          "tags": []
      },
      {
          "id": 100,
          "name": "Félix Faure",
          "tags": []
      },
      {
          "id": 101,
          "name": "Filles du Calvaire",
          "tags": []
      },
      {
          "id": 102,
          "name": "Fort d’Aubervilliers",
          "tags": []
      },
      {
          "id": 103,
          "name": "Franklin D. Roosevelt",
          "tags": []
      },
      {
          "id": 104,
          "name": "Front Populaire",
          "tags": []
      },
      {
          "id": 105,
          "name": "Gabriel Péri",
          "tags": []
      },
      {
          "id": 106,
          "name": "Gaîté",
          "tags": []
      },
      {
          "id": 107,
          "name": "Gallieni",
          "tags": []
      },
      {
          "id": 108,
          "name": "Gambetta",
          "tags": []
      },
      {
          "id": 109,
          "name": "Gare d’Austerlitz",
          "tags": []
      },
      {
          "id": 110,
          "name": "Gare de l’Est",
          "tags": []
      },
      {
          "id": 111,
          "name": "Gare de Lyon",
          "tags": []
      },
      {
          "id": 112,
          "name": "Gare du Nord",
          "tags": []
      },
      {
          "id": 113,
          "name": "Garibaldi",
          "tags": []
      },
      {
          "id": 114,
          "name": "George V",
          "tags": []
      },
      {
          "id": 115,
          "name": "Glacière",
          "tags": []
      },
      {
          "id": 116,
          "name": "Goncourt",
          "tags": []
      },
      {
          "id": 117,
          "name": "Grands Boulevards",
          "tags": []
      },
      {
          "id": 118,
          "name": "Guy Môquet",
          "tags": []
      },
      {
          "id": 119,
          "name": "Havre - Caumartin",
          "tags": []
      },
      {
          "id": 120,
          "name": "Hoche",
          "tags": []
      },
      {
          "id": 121,
          "name": "Hôpital Bicêtre",
          "tags": []
      },
      {
          "id": 122,
          "name": "Hôtel de Ville",
          "tags": []
      },
      {
          "id": 123,
          "name": "Iéna",
          "tags": []
      },
      {
          "id": 124,
          "name": "Invalides",
          "tags": []
      },
      {
          "id": 125,
          "name": "Jacques Bonsergent",
          "tags": []
      },
      {
          "id": 126,
          "name": "Jasmin",
          "tags": []
      },
      {
          "id": 127,
          "name": "Jaurès",
          "tags": []
      },
      {
          "id": 128,
          "name": "Javel - André Citroën",
          "tags": []
      },
      {
          "id": 129,
          "name": "Jourdain",
          "tags": []
      },
      {
          "id": 130,
          "name": "Jules Joffrin",
          "tags": []
      },
      {
          "id": 131,
          "name": "Jussieu",
          "tags": []
      },
      {
          "id": 132,
          "name": "Kléber",
          "tags": []
      },
      {
          "id": 133,
          "name": "La Chapelle",
          "tags": []
      },
      {
          "id": 134,
          "name": "La Courneuve - 8 Mai 1945",
          "tags": ["La Courneuve"]
      },
      {
          "id": 135,
          "name": "La Défense",
          "tags": []
      },
      {
          "id": 136,
          "name": "La Dhuys",
          "tags": []
      },
      {
          "id": 137,
          "name": "La Fourche",
          "tags": []
      },
      {
          "id": 138,
          "name": "La Motte-Picquet - Grenelle",
          "tags": []
      },
      {
          "id": 139,
          "name": "La Muette",
          "tags": []
      },
      {
          "id": 140,
          "name": "La Tour-Maubourg",
          "tags": []
      },
      {
          "id": 141,
          "name": "Lamarck - Caulaincourt",
          "tags": []
      },
      {
          "id": 142,
          "name": "Laumière",
          "tags": []
      },
      {
          "id": 143,
          "name": "Le Kremlin-Bicêtre",
          "tags": []
      },
      {
          "id": 144,
          "name": "Le Peletier",
          "tags": []
      },
      {
          "id": 145,
          "name": "Ledru-Rollin",
          "tags": []
      },
      {
          "id": 146,
          "name": "Les Agnettes",
          "tags": []
      },
      {
          "id": 147,
          "name": "Les Courtilles",
          "tags": ["Asnieres Gennevilliers Les Courtilles"]
      },
      {
          "id": 148,
          "name": "Les Gobelins",
          "tags": []
      },
      {
          "id": 149,
          "name": "Les Sablons",
          "tags": []
      },
      {
          "id": 150,
          "name": "L’Haÿ-les-Roses",
          "tags": []
      },
      {
          "id": 151,
          "name": "Liberté",
          "tags": []
      },
      {
          "id": 152,
          "name": "Liège",
          "tags": []
      },
      {
          "id": 153,
          "name": "Louis Blanc",
          "tags": []
      },
      {
          "id": 154,
          "name": "Louise Michel",
          "tags": []
      },
      {
          "id": 155,
          "name": "Lourmel",
          "tags": []
      },
      {
          "id": 156,
          "name": "Louvre - Rivoli",
          "tags": []
      },
      {
          "id": 157,
          "name": "Mabillon",
          "tags": []
      },
      {
          "id": 158,
          "name": "Madeleine",
          "tags": []
      },
      {
          "id": 159,
          "name": "Mairie d’Aubervilliers",
          "tags": []
      },
      {
          "id": 160,
          "name": "Mairie d’Issy",
          "tags": []
      },
      {
          "id": 161,
          "name": "Mairie d’Ivry",
          "tags": []
      },
      {
          "id": 162,
          "name": "Mairie de Clichy",
          "tags": []
      },
      {
          "id": 163,
          "name": "Mairie de Montreuil",
          "tags": []
      },
      {
          "id": 164,
          "name": "Mairie de Montrouge",
          "tags": []
      },
      {
          "id": 165,
          "name": "Mairie de Saint-Ouen",
          "tags": []
      },
      {
          "id": 166,
          "name": "Mairie des Lilas",
          "tags": []
      },
      {
          "id": 167,
          "name": "Maison Blanche",
          "tags": []
      },
      {
          "id": 168,
          "name": "Maisons-Alfort - Les Juilliottes",
          "tags": []
      },
      {
          "id": 169,
          "name": "Maisons-Alfort - Stade",
          "tags": []
      },
      {
          "id": 170,
          "name": "Malakoff - Plateau de Vanves",
          "tags": []
      },
      {
          "id": 171,
          "name": "Malakoff - Rue Étienne Dolet",
          "tags": []
      },
      {
          "id": 172,
          "name": "Malesherbes",
          "tags": []
      },
      {
          "id": 173,
          "name": "Maraîchers",
          "tags": []
      },
      {
          "id": 174,
          "name": "Marcadet - Poissonniers",
          "tags": []
      },
      {
          "id": 175,
          "name": "Marcel Sembat",
          "tags": []
      },
      {
          "id": 176,
          "name": "Marx Dormoy",
          "tags": []
      },
      {
          "id": 177,
          "name": "Maubert - Mutualité",
          "tags": []
      },
      {
          "id": 178,
          "name": "Ménilmontant",
          "tags": []
      },
      {
          "id": 179,
          "name": "Michel Bizot",
          "tags": []
      },
      {
          "id": 180,
          "name": "Michel-Ange - Auteuil",
          "tags": []
      },
      {
          "id": 181,
          "name": "Michel-Ange - Molitor",
          "tags": []
      },
      {
          "id": 182,
          "name": "Mirabeau",
          "tags": []
      },
      {
          "id": 183,
          "name": "Miromesnil",
          "tags": []
      },
      {
          "id": 184,
          "name": "Monceau",
          "tags": []
      },
      {
          "id": 185,
          "name": "Montgallet",
          "tags": []
      },
      {
          "id": 186,
          "name": "Montparnasse - Bienvenüe",
          "tags": []
      },
      {
          "id": 187,
          "name": "Montreuil - Hôpital",
          "tags": []
      },
      {
          "id": 188,
          "name": "Mouton-Duvernet",
          "tags": []
      },
      {
          "id": 189,
          "name": "Nation",
          "tags": []
      },
      {
          "id": 190,
          "name": "Nationale",
          "tags": []
      },
      {
          "id": 191,
          "name": "Notre-Dame-de-Lorette",
          "tags": []
      },
      {
          "id": 192,
          "name": "Notre-Dame-des-Champs",
          "tags": []
      },
      {
          "id": 193,
          "name": "Oberkampf",
          "tags": []
      },
      {
          "id": 194,
          "name": "Odéon",
          "tags": []
      },
      {
          "id": 195,
          "name": "Olympiades",
          "tags": []
      },
      {
          "id": 196,
          "name": "Opéra",
          "tags": []
      },
      {
          "id": 197,
          "name": "Ourcq",
          "tags": []
      },
      {
          "id": 198,
          "name": "Palais Royale - Musée du Louvre",
          "tags": []
      },
      {
          "id": 199,
          "name": "Parmentier",
          "tags": []
      },
      {
          "id": 200,
          "name": "Passy",
          "tags": []
      },
      {
          "id": 201,
          "name": "Pasteur",
          "tags": []
      },
      {
          "id": 202,
          "name": "Pelleport",
          "tags": []
      },
      {
          "id": 203,
          "name": "Père Lachaise",
          "tags": []
      },
      {
          "id": 204,
          "name": "Pereire",
          "tags": []
      },
      {
          "id": 205,
          "name": "Pernety",
          "tags": []
      },
      {
          "id": 206,
          "name": "Philippe Auguste",
          "tags": []
      },
      {
          "id": 207,
          "name": "Picpus",
          "tags": []
      },
      {
          "id": 208,
          "name": "Pierre et Marie Curie",
          "tags": []
      },
      {
          "id": 209,
          "name": "Pigalle",
          "tags": []
      },
      {
          "id": 210,
          "name": "Place d’Italie",
          "tags": []
      },
      {
          "id": 211,
          "name": "Place de Clichy",
          "tags": []
      },
      {
          "id": 212,
          "name": "Place des Fêtes",
          "tags": []
      },
      {
          "id": 213,
          "name": "Place Monge",
          "tags": []
      },
      {
          "id": 214,
          "name": "Plaisance",
          "tags": []
      },
      {
          "id": 215,
          "name": "Pointe du Lac",
          "tags": ["Créteil Pointe du Lac"]
      },
      {
          "id": 216,
          "name": "Poissionnière",
          "tags": []
      },
      {
          "id": 217,
          "name": "Pont de Levallois - Bécon",
          "tags": ["Pont de Levallois"]
      },
      {
          "id": 218,
          "name": "Pont de Neuilly",
          "tags": []
      },
      {
          "id": 219,
          "name": "Pont de Sèvres",
          "tags": []
      },
      {
          "id": 220,
          "name": "Pont Cardinet",
          "tags": []
      },
      {
          "id": 221,
          "name": "Pont Marie",
          "tags": []
      },
      {
          "id": 222,
          "name": "Pont Neuf",
          "tags": []
      },
      {
          "id": 223,
          "name": "Porte Dauphine",
          "tags": []
      },
      {
          "id": 224,
          "name": "Porte d’Auteuil",
          "tags": []
      },
      {
          "id": 225,
          "name": "Porte de Bagnolet",
          "tags": []
      },
      {
          "id": 226,
          "name": "Porte de Champerret",
          "tags": []
      },
      {
          "id": 227,
          "name": "Porte de Charenton",
          "tags": []
      },
      {
          "id": 228,
          "name": "Porte de Choisy",
          "tags": []
      },
      {
          "id": 229,
          "name": "Porte de Clichy",
          "tags": []
      },
      {
          "id": 230,
          "name": "Porte de Clignancourt",
          "tags": []
      },
      {
          "id": 231,
          "name": "Porte de la Chapelle",
          "tags": []
      },
      {
          "id": 232,
          "name": "Porte de la Villette",
          "tags": []
      },
      {
          "id": 233,
          "name": "Porte de Montreuil",
          "tags": []
      },
      {
          "id": 234,
          "name": "Porte de Pantin",
          "tags": []
      },
      {
          "id": 235,
          "name": "Porte de Saint-Cloud",
          "tags": []
      },
      {
          "id": 236,
          "name": "Porte de Saint-Ouen",
          "tags": []
      },
      {
          "id": 237,
          "name": "Porte de Vanves",
          "tags": []
      },
      {
          "id": 238,
          "name": "Porte de Versailles",
          "tags": []
      },
      {
          "id": 239,
          "name": "Porte de Vincennes",
          "tags": []
      },
      {
          "id": 240,
          "name": "Porte des Lilas",
          "tags": []
      },
      {
          "id": 241,
          "name": "Porte d’Italie",
          "tags": []
      },
      {
          "id": 242,
          "name": "Porte d’Ivry",
          "tags": []
      },
      {
          "id": 243,
          "name": "Porte Dorée",
          "tags": []
      },
      {
          "id": 244,
          "name": "Porte d’Orléans",
          "tags": []
      },
      {
          "id": 245,
          "name": "Porte Maillot",
          "tags": []
      },
      {
          "id": 246,
          "name": "Pré-Saint-Gervais",
          "tags": []
      },
      {
          "id": 247,
          "name": "Pyramides",
          "tags": []
      },
      {
          "id": 248,
          "name": "Pyrénées",
          "tags": []
      },
      {
          "id": 249,
          "name": "Quai de la Gare",
          "tags": []
      },
      {
          "id": 250,
          "name": "Quai de la Rapée",
          "tags": []
      },
      {
          "id": 251,
          "name": "Quatre-Septembre",
          "tags": []
      },
      {
          "id": 252,
          "name": "Rambuteau",
          "tags": []
      },
      {
          "id": 253,
          "name": "Ranelagh",
          "tags": []
      },
      {
          "id": 254,
          "name": "Raspail",
          "tags": []
      },
      {
          "id": 255,
          "name": "Réaumur - Sébastopol",
          "tags": []
      },
      {
          "id": 256,
          "name": "Rennes",
          "tags": []
      },
      {
          "id": 257,
          "name": "République",
          "tags": []
      },
      {
          "id": 258,
          "name": "Reuilly - Diderot",
          "tags": []
      },
      {
          "id": 259,
          "name": "Richard-Lenoir",
          "tags": []
      },
      {
          "id": 260,
          "name": "Richelieu - Drouot",
          "tags": []
      },
      {
          "id": 261,
          "name": "Riquet",
          "tags": []
      },
      {
          "id": 262,
          "name": "Robespierre",
          "tags": []
      },
      {
          "id": 263,
          "name": "Romainville - Carnot",
          "tags": []
      },
      {
          "id": 264,
          "name": "Rome",
          "tags": []
      },
      {
          "id": 265,
          "name": "Rosny-Bois-Perrier",
          "tags": []
      },
      {
          "id": 266,
          "name": "Rue de la Pompe",
          "tags": []
      },
      {
          "id": 267,
          "name": "Rue des Boulets",
          "tags": []
      },
      {
          "id": 268,
          "name": "Rue du Bac",
          "tags": []
      },
      {
          "id": 269,
          "name": "Rue Saint-Maur",
          "tags": []
      },
      {
          "id": 270,
          "name": "Saint-Ambroise",
          "tags": []
      },
      {
          "id": 271,
          "name": "Saint-Augustin",
          "tags": []
      },
      {
          "id": 272,
          "name": "Saint-Denis - Pleyel",
          "tags": []
      },
      {
          "id": 273,
          "name": "Saint-Denis - Porte de Paris",
          "tags": []
      },
      {
          "id": 274,
          "name": "Saint-Denis - Université",
          "tags": []
      },
      {
          "id": 275,
          "name": "Saint-Fargeau",
          "tags": []
      },
      {
          "id": 276,
          "name": "Saint-François-Xavier",
          "tags": []
      },
      {
          "id": 277,
          "name": "Saint-Georges",
          "tags": []
      },
      {
          "id": 278,
          "name": "Saint-Germain-des-Prés",
          "tags": []
      },
      {
          "id": 279,
          "name": "Saint-Jacques",
          "tags": []
      },
      {
          "id": 280,
          "name": "Saint-Lazare",
          "tags": []
      },
      {
          "id": 281,
          "name": "Saint-Mandé",
          "tags": []
      },
      {
          "id": 282,
          "name": "Saint-Marcel",
          "tags": []
      },
      {
          "id": 283,
          "name": "Saint-Michel",
          "tags": []
      },
      {
          "id": 284,
          "name": "Saint-Ouen",
          "tags": []
      },
      {
          "id": 285,
          "name": "Saint-Paul",
          "tags": []
      },
      {
          "id": 286,
          "name": "Saint-Philippe du Roule",
          "tags": []
      },
      {
          "id": 287,
          "name": "Saint-Placide",
          "tags": []
      },
      {
          "id": 288,
          "name": "Saint-Sébastien - Froissart",
          "tags": []
      },
      {
          "id": 289,
          "name": "Saint-Sulpice",
          "tags": []
      },
      {
          "id": 290,
          "name": "Ségur",
          "tags": []
      },
      {
          "id": 291,
          "name": "Sentier",
          "tags": []
      },
      {
          "id": 292,
          "name": "Serge Gainsbourg",
          "tags": []
      },
      {
          "id": 293,
          "name": "Sèvres - Babylone",
          "tags": []
      },
      {
          "id": 294,
          "name": "Sèvres - Lecourbe",
          "tags": []
      },
      {
          "id": 295,
          "name": "Simplon",
          "tags": []
      },
      {
          "id": 296,
          "name": "Solférino",
          "tags": []
      },
      {
          "id": 297,
          "name": "Stalingrad",
          "tags": []
      },
      {
          "id": 298,
          "name": "Strasbourg - Saint-Denis",
          "tags": []
      },
      {
          "id": 299,
          "name": "Sully - Morland",
          "tags": []
      },
      {
          "id": 300,
          "name": "Télégraphe",
          "tags": []
      },
      {
          "id": 301,
          "name": "Temple",
          "tags": []
      },
      {
          "id": 302,
          "name": "Ternes",
          "tags": []
      },
      {
          "id": 303,
          "name": "Thiais - Orly",
          "tags": []
      },
      {
          "id": 304,
          "name": "Tolbiac",
          "tags": []
      },
      {
          "id": 305,
          "name": "Trinité - d’Estienne d’Orves",
          "tags": []
      },
      {
          "id": 306,
          "name": "Trocadéro",
          "tags": []
      },
      {
          "id": 307,
          "name": "Tuileries",
          "tags": []
      },
      {
          "id": 308,
          "name": "Vaneau",
          "tags": []
      },
      {
          "id": 309,
          "name": "Varenne",
          "tags": []
      },
      {
          "id": 310,
          "name": "Vaugirard",
          "tags": []
      },
      {
          "id": 311,
          "name": "Vavin",
          "tags": []
      },
      {
          "id": 312,
          "name": "Victor Hugo",
          "tags": []
      },
      {
          "id": 313,
          "name": "Villejuif - Gustave Roussy",
          "tags": []
      },
      {
          "id": 314,
          "name": "Villejuif - Léo Lagrange",
          "tags": []
      },
      {
          "id": 315,
          "name": "Villejuif - Louis Aragon",
          "tags": []
      },
      {
          "id": 316,
          "name": "Villejuif - Paul Vaillant-Couturier",
          "tags": []
      },
      {
          "id": 317,
          "name": "Villiers",
          "tags": []
      },
      {
          "id": 318,
          "name": "Volontaires",
          "tags": []
      },
      {
          "id": 319,
          "name": "Voltaire",
          "tags": []
      },
      {
          "id": 320,
          "name": "Wagram",
          "tags": []
      }
];

const State = {
    LINE: 0,
    DIRECTION: 1,
    STATION: 2,
    FAILURE: 3,
    SUCCESS: 4,
};


const Color = {
    GREEN: "#00b59b",
    RED: "#ec1e2b",
    DARK_GRAY: "#7e8285"
};

const LINES_IN_ORDER = [1, 2, 3, 21, 4, 5, 6, 7, 22, 8, 9, 10, 11, 12, 13, 14];


const TEXT_REPLACEMENTS = {};


const FUSE_OPTIONS = {
    ignoreCase: true,
    ignoreDiacritics: true,
    includeScore: true,
    minMatchCharLength: 4,
    threshold: 0.3,
    distance: 4,
    fieldNormWeight: 2.0,
    keys: [
        {
            name: "name",
            getFn: (station) => { return cleanInput(station.name); }
        },
        "tags"
    ]
};

const fuse = new Fuse(STATIONS, FUSE_OPTIONS);


class KnowledgeGameConfig {
    text = EN_TEXT;

    state = State.LINE;

    origin_id = 0;
    destination_id = 0;
    final_id = 0;
    direction_id = 0;
    line_id = 0;

    current_step = null;
    prev_step = null;
    search_box = null;

    line_style_width = 0;
}

const GameConfig = new KnowledgeGameConfig();



function set_language(override_lang = null) {
    if (override_lang != null) {
        document.documentElement.lang = override_lang;
    }

    let lang = document.documentElement.lang;

    if (lang == "fr") {
        GameConfig.text = FR_TEXT;
    } else if (lang == "en") {
        GameConfig.text = EN_TEXT;
    } else {
        return;
    }

    for (let game_text in GameConfig.text) {
        let text_elements = document.getElementsByClassName(game_text);
        for (let text_element of text_elements) {
            text_element.textContent = GameConfig.text[game_text].translation;
            replaceI18NText(text_element, TEXT_REPLACEMENTS[game_text]);
        }
    }
}


function init() {
    set_language();

    let start_dest = document.getElementById("start-dest");
    if (!start_dest.children[0].id.startsWith("origin"))
        return;

    GameConfig.origin_id = Number(start_dest.children[0].id.split("-")[1]);
    GameConfig.final_id = Number(start_dest.children[1].id.split("-")[1]);

    let metro_buttons = document.getElementsByClassName("metro-button");
    for (let i = 0; i < metro_buttons.length; i++) {
        let line_id = metro_buttons[i].id;

        metro_buttons[i].addEventListener(
            "click",
            function (e) { event_metroLineClicked(e, line_id); }
        );
    }

    const fr_button = document.getElementById("lang-fr");
    const en_button = document.getElementById("lang-en");

    fr_button.addEventListener(
        "change",
        function(e) { set_language(e.target.value); }
    );

    en_button.addEventListener(
        "change",
        function(e) { set_language(e.target.value); }
    );

    const progress_display_btn = document.getElementById("progress-display");
    const progress_close_btn = document.getElementById("progress-close");

    progress_display_btn.addEventListener("click", event_progressDisplayClicked);
    progress_close_btn.addEventListener("click", event_progressCloseClicked);

    const info_title_btn = document.getElementById("info-title");
    const info_progress_btn = document.getElementById("info-progress");

    info_title_btn.addEventListener("click", event_infoButtonDisplayClicked);
    info_progress_btn.addEventListener("click", event_infoButtonDisplayClicked);

    const info_overelay = document.getElementById("info-overlay");
    const info_close_btn = document.getElementById("info-close");

    info_overelay.className = "info-overlay";
    info_overelay.addEventListener("click", event_infoCloseClicked);
    info_close_btn.addEventListener("click", event_infoCloseClicked);

    GameConfig.line_style_width = parseInt(getComputedStyle(metro_buttons[0]).width);
    GameConfig.current_step = document.getElementById("line-select");
    GameConfig.state = State.LINE;
}


function cleanInput(input) {
    return input.trim()
        .replaceAll('-', ' ')
        .replaceAll('—', ' ')
        .replaceAll("'", ' ')
        .replaceAll('’', ' ')
        .split(/\s+/)
        .join(' ');
}


function variable_yshift(base) {
    const element_height = Math.trunc(GameConfig.current_step.getBoundingClientRect().height / 2) - 90;
    if (element_height >= base) {
        base = element_height + 20;
    }

    return base;
}


function setSearchBoxToFinalColor(search_box, color) {
    search_box.style.borderColor = color;
    search_box.style.boxShadow = "0px 0px 4px 2px " + color;
}


function replaceI18NText(text_element, replacement_pairs) {
    if (replacement_pairs == null || replacement_pairs.length == 0) return;

    for (let i = 0; i < replacement_pairs.length; i++) {
        text_element.textContent = text_element.textContent.replace(
            replacement_pairs[i].key, replacement_pairs[i].value
        );
    }
}


function createI18NContainer(parent) {
    const text_container = document.createElement("div");
    text_container.className = "i18n";
    parent.appendChild(text_container);

    return text_container;
}


function addI18NTextToContainer(type, key, container, replace = []) {
    const text = document.createElement(type);
    text.className = key;
    text.textContent = GameConfig.text[key].translation;
    replaceI18NText(text, replace);
    container.appendChild(text);

    TEXT_REPLACEMENTS[key] = replace;

    return text;
}


function animate_shiftSteps(current_step, prev_step, next_step) {
    // Copy references to avoid timer issues.
    let current = current_step;
    let previous = prev_step;
    let next = next_step;

    if (previous) {
        previous.style.opacity = "0";
        setTimeout(function () { previous.remove(); }, 1000);
    }

    current.style.transform = "scale(0.5, 0.5) translateY(-90px)";
    current.style.opacity = "0.4";

    next.animate(
        [
            { opacity: "0", },
            { opacity: "1", }
        ],
        { duration: 800 }
    );
}

function animate_invalidSearch() {
    let search_box = GameConfig.search_box;

    search_box.style.borderColor = Color.RED;

    setTimeout(
        function () {
            search_box.style.borderColor = "";
            search_box.value = "";
            search_box.disabled = false;
        },
        1000
    );
}


function animate_nextRound(new_origin_id) {
    let station_select = GameConfig.current_step;
    let search_box = GameConfig.search_box;
    search_box.style.borderColor = Color.GREEN;

    let timeout_ms = 800;

    if (new_origin_id != GameConfig.origin_id) {
        const tunnel_container = createI18NContainer(station_select);
        const tunnel = addI18NTextToContainer(
            "p", "tunnel", tunnel_container,
            [
                { key: "$line", value: LINES[GameConfig.line_id].name },
                { key: "$tunnel_station", value: STATIONS[new_origin_id - 1].name },
            ]
        );
        tunnel.style.marginTop = "10px";

        timeout_ms = 4000;
    }

    setTimeout(
        function () {
            search_box.value = "";
            transitionToLine();
        },
        timeout_ms
    );
}


function animate_finalMessage(message, refresh, tunnel = null) {
    message.animate(
        [
            { opacity: "0" },
            { opacity: "1" }
        ],
        { duration: 200 }
    );

    refresh.animate(
        [
            { opacity: "0" },
            { opacity: "1" }
        ],
        { duration: 200 }
    );

    if (tunnel !== null) {
        tunnel.animate(
            [
                { opacity: "0" },
                { opacity: "1" }
            ],
            { duration: 200 }
        );
    }
}


function transitionToSuccess(new_origin_id) {
    if (GameConfig.state != State.STATION) return;

    let station_select = GameConfig.current_step;
    let search_box = GameConfig.search_box;

    const text_container = createI18NContainer(station_select);
    const success = addI18NTextToContainer("h2", "arrived", text_container);

    let tunnel = null;
    if (new_origin_id != GameConfig.origin_id) {
        tunnel = addI18NTextToContainer(
            "p", "tunnel", text_container,
            [
                { key: "$line", value: LINES[GameConfig.line_id].name },
                { key: "$tunnel_station", value: STATIONS[new_origin_id - 1].name },
            ]
        );
        tunnel.style.marginTop = "10px";
        tunnel.style.marginBottom = "10px";
    }

    const refresh = addI18NTextToContainer("a", "refresh", text_container);
    refresh.href = "./";
    refresh.style.color = Color.DARK_GRAY;
    refresh.style.marginTop = "10px";

    setSearchBoxToFinalColor(search_box, Color.GREEN);

    animate_finalMessage(success, refresh);

    GameConfig.state = State.SUCCESS;
}


function transitionToFailure(failure_reason) {
    if (GameConfig.state != State.STATION) return;

    let station_select = GameConfig.current_step;

    const text_container = createI18NContainer(station_select);
    const failure = addI18NTextToContainer(
        "p", failure_reason, text_container,
        [
            { key: "$origin", value: STATIONS[GameConfig.origin_id - 1].name },
            { key: "$line", value: LINES[GameConfig.line_id].name },
            { key: "$destination", value: STATIONS[GameConfig.destination_id - 1].name },
        ]
    );
    failure.style.marginBottom = "10px";

    const refresh = addI18NTextToContainer("a", "refresh", text_container);
    refresh.href = "./";
    refresh.style.color = Color.DARK_GRAY;
    refresh.style.marginTop = "10px";

    setSearchBoxToFinalColor(GameConfig.search_box, Color.RED);

    animate_finalMessage(failure, refresh);
    GameConfig.state = State.FAILURE;
}


function transitionToLine() {
    if (GameConfig.state != State.STATION) return;

    let steps_container = document.getElementById("steps-container");
    let station_step = GameConfig.current_step;

    const line_select = document.createElement("div");
    line_select.className = "step";
    line_select.style.transform = "translateY(30px)";

    const text_container = createI18NContainer(line_select);
    addI18NTextToContainer("span", "take-line", text_container);

    addI18NTextToContainer(
        "span", "current-station", text_container,
        [{ key: "$station", value: STATIONS[GameConfig.destination_id - 1].name }]
    );

    for (let i = 0; i < LINES_IN_ORDER.length; i++) {
        let line_id = LINES_IN_ORDER[i];
        const button = document.createElement("button");
        button.className = "metro-button";
        button.type = "button";
        button.style.backgroundPosition = -1 * GameConfig.line_style_width * i + "px 0";
        button.onclick = function (e) { event_metroLineClicked(e, line_id); };
        line_select.appendChild(button);
    }

    steps_container.appendChild(line_select);

    animate_shiftSteps(station_step, GameConfig.prev_step, line_select);

    GameConfig.prev_step = station_step;
    GameConfig.current_step = line_select;

    GameConfig.origin_id = GameConfig.destination_id;
    GameConfig.line_id = 0;
    GameConfig.destination_id = 0;

    GameConfig.state = State.LINE;
}


function transitionToStation(direction_id) {
    if (GameConfig.state != State.LINE && GameConfig.state != State.DIRECTION)
        return;

    let steps_container = document.getElementById("steps-container");

    let yshift = 30;
    if (GameConfig.state == State.LINE) {
        yshift = variable_yshift(yshift);
    }

    const station_select = document.createElement("div");
    station_select.className = "step";
    station_select.style.transform = "translateY(" + yshift + "px)";

    const text_container = createI18NContainer(station_select);
    addI18NTextToContainer("p", "to-station", text_container);

    const input_box = document.createElement("input");
    input_box.type = "search";
    input_box.className = "search-box";
    input_box.placeholder = "Station…";
    input_box.addEventListener("keydown", event_stationSearchKeyDown);
    station_select.appendChild(input_box);

    steps_container.appendChild(station_select);

    animate_shiftSteps(GameConfig.current_step, GameConfig.prev_step, station_select);

    GameConfig.direction_id = direction_id;
    GameConfig.prev_step = GameConfig.current_step;
    GameConfig.current_step = station_select;
    GameConfig.search_box = input_box;
    GameConfig.state = State.STATION;
}


function transitionToDirection(line_id) {
    if (GameConfig.state != State.LINE)
        return;

    let steps_container = document.getElementById("steps-container");
    let selected_line = LINES[line_id];
    let line_step = GameConfig.current_step;

    if (!selected_line.termini)
        return;

    if (selected_line.termini.length < 2) {
        GameConfig.line_id = line_id;
        transitionToStation(selected_line.termini[0].id);
        return;
    }

    let yshift = variable_yshift(30);

    const direction_select = document.createElement("div");
    direction_select.className = "step";
    direction_select.style.transform = "translateY(" + yshift + "px)";

    const text_container = createI18NContainer(direction_select);
    addI18NTextToContainer("p", "towards", text_container);

    const button_wrapper = document.createElement("div");
    button_wrapper.className = "direction-button-wrap";
    direction_select.appendChild(button_wrapper);

    for (let i = 0; i < selected_line.termini.length; i++) {
        if (!("termini" in selected_line)) continue;

        let terminus = selected_line.termini[i];

        const button = document.createElement("button");
        button.type = "button";
        button.className = "direction-button";
        button.textContent = terminus.name;
        button.addEventListener("click", function (e) { event_directionClicked(e, terminus.id); });
        button_wrapper.appendChild(button);
    }

    steps_container.appendChild(direction_select);

    animate_shiftSteps(line_step, GameConfig.prev_step, direction_select);

    GameConfig.line_id = line_id;
    GameConfig.prev_step = line_step;
    GameConfig.current_step = direction_select;
    GameConfig.state = State.DIRECTION;
}


function validate() {
    let origin_id = GameConfig.origin_id;
    let destination_id = GameConfig.destination_id;
    let line_id = GameConfig.line_id;
    let direction_id = GameConfig.direction_id;

    if (origin_id == 0 ||
        destination_id == 0 ||
        line_id == 0 ||
        direction_id == 0
    ) {
        animate_invalidSearch();
        return;
    }

    fetch("/maison/verify/line/" + line_id + "/origin/" + origin_id + "/destination/" + destination_id + "/direction/" + direction_id,
        {
            method: 'GET',
            credentials: "same-origin",
            headers: { lang: document.documentElement.lang },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error: Received code: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            if (data.status === -1) {
                transitionToFailure(data.message);
            } else if (data.status === 0) {
                if (GameConfig.destination_id == GameConfig.final_id) {
                    transitionToSuccess(data.origin_id);
                } else {
                    animate_nextRound(data.origin_id);
                }
            }
        })
        .catch(error => {
            console.error(error);
            animate_invalidSearch();
        });
}

function stationSearch() {
    if (GameConfig.state != State.STATION) return;

    let search_box = GameConfig.search_box;

    if (search_box.value.trim().length == 0) {
        return;
    }

    search_box.disabled = true;

    let filtered_value = cleanInput(search_box.value);

    let results = fuse.search(filtered_value);

    if (results.length == 0) {
        animate_invalidSearch();
        return;
    }

    if (results.length > 1) {
        if (results[0].score > 0.005) {
            animate_invalidSearch();
            return;
        }
    }

    let filtered_result = cleanInput(results[0].item.name);
    if ((filtered_value.length / filtered_result.length) < 0.9) {
        if (results[0].item.tags.length > 0) {
            let max_ratio = 0.0;
            for (let tag of results[0].item.tags) {
                let ratio = filtered_value.length / tag.length;
                max_ratio = Math.max(ratio, max_ratio);
            }

            if (max_ratio < 0.9) {
                animate_invalidSearch();
                return;
            }
        } else {
            animate_invalidSearch();
            return;
        }
    }

    GameConfig.destination_id = results[0].item.id;

    validate();

}

function event_metroLineClicked(event, line) {
    if (GameConfig.state != State.LINE) return;

    event.target.style.boxShadow = "0 0 1px 4px black";

    let line_id = Number(line);

    let buttons = document.querySelectorAll(".metro-button");
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
    }

    transitionToDirection(line_id);
}


function event_directionClicked(event, direction) {
    if (GameConfig.state != State.DIRECTION) return;

    event.target.style.borderWidth = "3px";

    let direction_id = Number(direction);

    let buttons = document.querySelectorAll(".direction-button");
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
    }

    transitionToStation(direction_id);
}


function event_progressDisplayClicked() {
    const progress = document.getElementById("progress-window");
    progress.classList.add("active");
}


function event_progressCloseClicked() {
    const progress = document.getElementById("progress-window");
    progress.classList.remove("active");
}


function event_infoButtonDisplayClicked() {
    const overlay = document.getElementById("info-overlay");
    overlay.classList.add("active");
}


function event_infoCloseClicked(event) {
    const overlay = document.getElementById("info-overlay");
    if (event.target.id == "info-overlay" || event.target.id == "info-close") {
        overlay.classList.remove("active");
    }
}


function event_stationSearchKeyDown(event) {
    if (event.keyCode == 13) {
        stationSearch();
    }
}


window.onload = function () {
    init();
};
