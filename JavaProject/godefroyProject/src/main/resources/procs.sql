-- === DROP EXISTING STORED PROCEDURES ===
DECLARE @name NVARCHAR(128);
DECLARE cur CURSOR FOR
  SELECT [name] FROM sys.objects
  WHERE type = 'P' AND [name] LIKE 'sp\_%' ESCAPE '\';
OPEN cur;
FETCH NEXT FROM cur INTO @name;
WHILE @@FETCH_STATUS = 0
BEGIN
    EXEC('DROP PROCEDURE dbo.' + @name);
    FETCH NEXT FROM cur INTO @name;
END
CLOSE cur;
DEALLOCATE cur;
-- ======================================

-- === GENRE ===

CREATE PROCEDURE dbo.sp_GetAllGenres
AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id, Name
      FROM dbo.Genre;
END

CREATE PROCEDURE dbo.sp_CreateGenre
    @Name NVARCHAR(50),
    @NewId INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT dbo.Genre(Name)
    VALUES(@Name);
    SET @NewId = SCOPE_IDENTITY();
END

CREATE PROCEDURE dbo.sp_UpdateGenre
    @Id    INT,
    @Name  NVARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE dbo.Genre
      SET Name = @Name
      WHERE Id = @Id;
END

CREATE PROCEDURE dbo.sp_DeleteGenre
    @Id INT
AS
BEGIN
    SET NOCOUNT ON;
    DELETE dbo.Genre
      WHERE Id = @Id;
END

-- === DIRECTOR ===

CREATE PROCEDURE dbo.sp_GetAllDirectors
AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id, Name
      FROM dbo.Director;
END

CREATE PROCEDURE dbo.sp_CreateDirector
    @FirstName NVARCHAR(50),
    @LastName  NVARCHAR(50),
    @NewId     INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT dbo.Director(FirstName, LastName)
    VALUES(@FirstName, @LastName);
    SET @NewId = SCOPE_IDENTITY();
END

CREATE PROCEDURE dbo.sp_UpdateDirector
    @Id        INT,
    @FirstName NVARCHAR(50),
    @LastName  NVARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE dbo.Director
      SET FirstName = @FirstName,
          LastName  = @LastName
      WHERE Id = @Id;
END

CREATE PROCEDURE dbo.sp_DeleteDirector
    @Id INT
AS
BEGIN
    SET NOCOUNT ON;
    DELETE dbo.Director
      WHERE Id = @Id;
END

-- === MOVIE ===

CREATE PROCEDURE dbo.sp_GetAllMovies
AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id,
           Title,
           Description,
           ReleaseYear,
           GenreId,
           DirectorId,
           ImagePath
      FROM dbo.Movie;
END

CREATE PROCEDURE dbo.sp_CreateMovie
    @Title       NVARCHAR(100),
    @Description NVARCHAR(MAX),
    @ReleaseYear INT,
    @GenreId     INT,
    @DirectorId  INT,
    @ImagePath   NVARCHAR(200),
    @NewId       INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT dbo.Movie(Title, Description, ReleaseYear, GenreId, DirectorId, ImagePath)
    VALUES(@Title, @Description, @ReleaseYear, @GenreId, @DirectorId, @ImagePath);
    SET @NewId = SCOPE_IDENTITY();
END

CREATE PROCEDURE dbo.sp_UpdateMovie
    @Id          INT,
