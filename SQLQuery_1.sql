INSERT INTO dbo.Genre    (Name) VALUES 
  ('Sci-Fi'),('Action'),('Drama'),('Comedy');


INSERT INTO dbo.Director (Name) VALUES 
  ('Lana Wachowski'),
  ('Christopher Nolan'),
  ('Steven Spielberg');

INSERT INTO dbo.AppUser (Username, Password, Role)
VALUES ('admin','admin','Admin');
GO

-- après le seed Admin
INSERT INTO Movie (Title, Description, ReleaseYear, GenreId, DirectorId, ImagePath)
VALUES 
  ('Matrix', 'Sci-fi classique', 1999, 1, 1, 'matrix.jpg'),
  ('Inception', 'Rêves et réalité', 2010, 1, 1, 'inception.jpg');
