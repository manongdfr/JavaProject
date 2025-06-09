CREATE PROCEDURE sp_CreateMovie
    @Title NVARCHAR(100),
    @Description NVARCHAR(MAX),
    @ReleaseYear INT,
    @GenreId INT,
    @DirectorId INT,
    @ImagePath NVARCHAR(255)
AS
BEGIN
    INSERT INTO Movie (Title, Description, ReleaseYear, GenreId, DirectorId, ImagePath)
    VALUES (@Title, @Description, @ReleaseYear, @GenreId, @DirectorId, @ImagePath);
    SELECT SCOPE_IDENTITY() AS NewId;
END;

CREATE PROCEDURE sp_GetAllMovies
AS
BEGIN
    SELECT * FROM Movie;
END;

-- etc.
