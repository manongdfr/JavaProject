-- 1) Vider les tables
DELETE FROM dbo.Movie;
DELETE FROM dbo.Director;
DELETE FROM dbo.Genre;
DELETE FROM dbo.AppUser;

-- 2) Réinitialiser les compteurs IDENTITY
DBCC CHECKIDENT('dbo.Movie',    RESEED, 0);
DBCC CHECKIDENT('dbo.Genre',    RESEED, 0);
DBCC CHECKIDENT('dbo.Director', RESEED, 0);
DBCC CHECKIDENT('dbo.AppUser',  RESEED, 0);

-- 3) Remplir les répertoires de référence
INSERT INTO dbo.Director (Name) VALUES
  ('Lana Wachowski'),
  ('Christopher Nolan');

INSERT INTO dbo.Genre (Name) VALUES
  ('Sci-Fi'),
  ('Action');

-- 4) Créer les comptes utilisateur
INSERT INTO dbo.AppUser (Username, Password, Role) VALUES
  ('admin', 'admin', 'Admin'),
  ('user',  'user',  'User');

-- 5) Réinsérer les films exemples
INSERT INTO dbo.Movie
  (Title, Description, ReleaseYear, GenreId, DirectorId, ImagePath)
VALUES
  (
    'Matrix',
    'Un hacker découvre que la réalité n''est qu''une illusion créée par des machines.',
    1999,
    1,  -- Sci-Fi → Lana Wachowski
    1,
    'matrix.jpg'
  ),
  (
    'Inception',
    'Un voleur capable d''infiltrer les rêves est chargé d''une mission d''inception.',
    2010,
    1,  -- Sci-Fi → Christopher Nolan
    2,
    'inception.jpg'
  );
