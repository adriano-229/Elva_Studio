-- ================================================
-- Library Management System - Database Schema
-- ================================================
-- This script creates all tables with proper relationships
-- and inserts sample data for testing
-- ================================================

-- Drop tables if they exist (in correct order due to foreign keys)
DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS book_authors;
DROP TABLE IF EXISTS book_publishers;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS user;

-- ================================================
-- TABLE: user
-- ================================================
CREATE TABLE user
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL COMMENT 'BCrypt encoded password',
    user_role VARCHAR(50)  NOT NULL DEFAULT 'USER' COMMENT 'USER or ADMIN',
    INDEX     idx_email (email),
    INDEX     idx_role (user_role)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ================================================
-- TABLE: book
-- ================================================
CREATE TABLE book
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    isbn             VARCHAR(50)  NOT NULL UNIQUE,
    total_copies     INT          NOT NULL DEFAULT 0,
    loaned_copies    INT          NOT NULL DEFAULT 0,
    publication_year INT COMMENT 'Year of publication',
    image_path       VARCHAR(500) COMMENT 'Path to book cover image',
    INDEX            idx_isbn (isbn),
    INDEX            idx_title (title),
    INDEX            idx_publication_year (publication_year),
    CHECK (total_copies >= 0),
    CHECK (loaned_copies >= 0),
    CHECK (loaned_copies <= total_copies)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ================================================
-- TABLE: author
-- ================================================
CREATE TABLE author
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    INDEX idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ================================================
-- TABLE: publisher
-- ================================================
CREATE TABLE publisher
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    INDEX idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ================================================
-- TABLE: book_authors (Many-to-Many junction table)
-- ================================================
CREATE TABLE book_authors
(
    book_id    BIGINT NOT NULL,
    authors_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, authors_id),
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE,
    FOREIGN KEY (authors_id) REFERENCES author (id) ON DELETE CASCADE,
    INDEX      idx_book_id (book_id),
    INDEX      idx_authors_id (authors_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ================================================
-- TABLE: book_publishers (Many-to-Many junction table)
-- ================================================
CREATE TABLE book_publishers
(
    book_id       BIGINT NOT NULL,
    publishers_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, publishers_id),
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE,
    FOREIGN KEY (publishers_id) REFERENCES publisher (id) ON DELETE CASCADE,
    INDEX         idx_book_id (book_id),
    INDEX         idx_publishers_id (publishers_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ================================================
-- TABLE: loan
-- ================================================
CREATE TABLE loan
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_date   DATE        NOT NULL,
    return_date DATE        NOT NULL,
    status      VARCHAR(50) NOT NULL DEFAULT 'RESERVED' COMMENT 'RESERVED, ACTIVE, RETURNED',
    book_id     BIGINT      NOT NULL,
    user_id     BIGINT      NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    INDEX       idx_loan_date (loan_date),
    INDEX       idx_return_date (return_date),
    INDEX       idx_status (status),
    INDEX       idx_book_id (book_id),
    INDEX       idx_user_id (user_id),
    CHECK (return_date > loan_date)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ================================================
-- INSERT SAMPLE DATA
-- ================================================

-- Insert Users
-- Password for all users is: "password123" (BCrypt encoded)
INSERT INTO user (email, password, user_role)
VALUES ('admin@library.com', '$2a$12$L40Y7nb0gE1rN5yq9EqsfOyxaahUweuXvyGKqaU37UkjkxhfGym1m', 'ADMIN'),
       ('librarian@library.com', '$2a$12$L40Y7nb0gE1rN5yq9EqsfOyxaahUweuXvyGKqaU37UkjkxhfGym1m', 'ADMIN'),
       ('user1@example.com', '$2a$12$L40Y7nb0gE1rN5yq9EqsfOyxaahUweuXvyGKqaU37UkjkxhfGym1m', 'USER'),
       ('user2@example.com', '$2a$12$L40Y7nb0gE1rN5yq9EqsfOyxaahUweuXvyGKqaU37UkjkxhfGym1m', 'USER'),
       ('john.doe@example.com', '$2a$12$L40Y7nb0gE1rN5yq9EqsfOyxaahUweuXvyGKqaU37UkjkxhfGym1m', 'USER'),
       ('jane.smith@example.com', '$2a$12$L40Y7nb0gE1rN5yq9EqsfOyxaahUweuXvyGKqaU37UkjkxhfGym1m', 'USER');

-- Insert Books
INSERT INTO book (title, isbn, total_copies, loaned_copies, publication_year, image_path)
VALUES ('The Great Gatsby', '978-0-7432-7356-5', 5, 2, 1925, NULL),
       ('To Kill a Mockingbird', '978-0-06-112008-4', 3, 1, 1960, NULL),
       ('1984', '978-0-452-28423-4', 4, 0, 1949, NULL),
       ('Pride and Prejudice', '978-0-14-143951-8', 6, 3, 1813, NULL),
       ('The Catcher in the Rye', '978-0-316-76948-0', 2, 2, 1951, NULL),
       ('Animal Farm', '978-0-452-28424-1', 5, 1, 1945, NULL),
       ('Brave New World', '978-0-06-085052-4', 3, 0, 1932, NULL),
       ('The Hobbit', '978-0-547-92822-7', 8, 4, 1937, NULL),
       ('Harry Potter and the Philosopher Stone', '978-0-439-70818-8', 10, 5, 1997, NULL),
       ('The Lord of the Rings', '978-0-544-00341-5', 7, 2, 1954, NULL);

-- Insert Authors (independent entities for Many-to-Many)
INSERT INTO author (name)
VALUES ('F. Scott Fitzgerald'), -- id: 1
       ('Harper Lee'),          -- id: 2
       ('George Orwell'),       -- id: 3
       ('Jane Austen'),         -- id: 4
       ('J.D. Salinger'),       -- id: 5
       ('Aldous Huxley'),       -- id: 6
       ('J.R.R. Tolkien'),      -- id: 7
       ('J.K. Rowling');
-- id: 8

-- Insert Publishers (independent entities for Many-to-Many)
INSERT INTO publisher (name)
VALUES ('Scribner'),                  -- id: 1
       ('J.B. Lippincott & Co.'),     -- id: 2
       ('Secker & Warburg'),          -- id: 3
       ('T. Egerton'),                -- id: 4
       ('Little, Brown and Company'), -- id: 5
       ('Chatto & Windus'),           -- id: 6
       ('George Allen & Unwin'),      -- id: 7
       ('HarperCollins'),             -- id: 8
       ('Bloomsbury'),                -- id: 9
       ('Scholastic');
-- id: 10

-- Insert Book-Author relationships (Many-to-Many junction table)
INSERT INTO book_authors (book_id, authors_id)
VALUES
-- The Great Gatsby - F. Scott Fitzgerald
(1, 1),
-- To Kill a Mockingbird - Harper Lee
(2, 2),
-- 1984 - George Orwell
(3, 3),
-- Pride and Prejudice - Jane Austen
(4, 4),
-- The Catcher in the Rye - J.D. Salinger
(5, 5),
-- Animal Farm - George Orwell
(6, 3),
-- Brave New World - Aldous Huxley
(7, 6),
-- The Hobbit - J.R.R. Tolkien
(8, 7),
-- Harry Potter - J.K. Rowling
(9, 8),
-- The Lord of the Rings - J.R.R. Tolkien
(10, 7);

-- Insert Book-Publisher relationships (Many-to-Many junction table)
INSERT INTO book_publishers (book_id, publishers_id)
VALUES
-- The Great Gatsby - Scribner
(1, 1),
-- To Kill a Mockingbird - J.B. Lippincott & Co.
(2, 2),
-- 1984 - Secker & Warburg
(3, 3),
-- Pride and Prejudice - T. Egerton
(4, 4),
-- The Catcher in the Rye - Little, Brown and Company
(5, 5),
-- Animal Farm - Secker & Warburg
(6, 3),
-- Brave New World - Chatto & Windus
(7, 6),
-- The Hobbit - George Allen & Unwin, HarperCollins
(8, 7),
(8, 8),
-- Harry Potter - Bloomsbury, Scholastic
(9, 9),
(9, 10),
-- The Lord of the Rings - George Allen & Unwin, HarperCollins
(10, 7),
(10, 8);

-- Insert Loans
INSERT INTO loan (loan_date, return_date, status, book_id, user_id)
VALUES
-- Active loans
('2025-10-20', '2025-11-05', 'ACTIVE', 1, 3),
('2025-10-22', '2025-11-08', 'ACTIVE', 1, 4),
('2025-10-23', '2025-11-10', 'ACTIVE', 2, 5),
('2025-10-18', '2025-11-02', 'ACTIVE', 4, 3),
('2025-10-25', '2025-11-12', 'ACTIVE', 4, 6),

-- Reserved loans (future)
('2025-11-01', '2025-11-20', 'RESERVED', 4, 4),
('2025-11-05', '2025-11-25', 'RESERVED', 5, 5),
('2025-11-10', '2025-12-01', 'RESERVED', 5, 6),

-- Active loans for popular books
('2025-10-24', '2025-11-10', 'ACTIVE', 6, 3),
('2025-10-21', '2025-11-07', 'ACTIVE', 8, 4),
('2025-10-19', '2025-11-04', 'ACTIVE', 8, 5),
('2025-10-23', '2025-11-09', 'ACTIVE', 8, 6),
('2025-10-20', '2025-11-06', 'ACTIVE', 8, 3),

-- Harry Potter loans
('2025-10-22', '2025-11-08', 'ACTIVE', 9, 3),
('2025-10-23', '2025-11-09', 'ACTIVE', 9, 4),
('2025-10-24', '2025-11-10', 'ACTIVE', 9, 5),
('2025-10-25', '2025-11-11', 'ACTIVE', 9, 6),
('2025-10-26', '2025-11-12', 'ACTIVE', 9, 3),

-- LOTR loans
('2025-10-21', '2025-11-07', 'ACTIVE', 10, 4),
('2025-10-22', '2025-11-08', 'ACTIVE', 10, 5),

-- Returned loans (historical)
('2025-09-01', '2025-09-15', 'RETURNED', 1, 3),
('2025-09-10', '2025-09-25', 'RETURNED', 2, 4),
('2025-08-15', '2025-09-01', 'RETURNED', 3, 5),
('2025-08-20', '2025-09-05', 'RETURNED', 7, 6);

-- ================================================
-- VERIFICATION QUERIES
-- ================================================

-- Check all tables were created
SELECT 'Tables created successfully' AS status;

-- Show counts
SELECT 'Users' AS entity, COUNT(*) AS count
FROM user
UNION ALL
SELECT 'Books', COUNT(*)
FROM book
UNION ALL
SELECT 'Authors', COUNT(*)
FROM author
UNION ALL
SELECT 'Publishers', COUNT(*)
FROM publisher
UNION ALL
SELECT 'Loans', COUNT(*)
FROM loan;

-- Show book availability
SELECT b.id,
       b.title,
       b.isbn,
       b.publication_year,
       b.total_copies,
       b.loaned_copies,
       (b.total_copies - b.loaned_copies) AS available_copies,
       COUNT(DISTINCT ba.authors_id)      AS num_authors,
       COUNT(DISTINCT bp.publishers_id)   AS num_publishers
FROM book b
         LEFT JOIN book_authors ba ON b.id = ba.book_id
         LEFT JOIN book_publishers bp ON b.id = bp.book_id
GROUP BY b.id, b.title, b.isbn, b.publication_year, b.total_copies, b.loaned_copies
ORDER BY b.title;

-- ================================================
-- NOTES
-- ================================================
-- 1. Default password for all users: "password123"
-- 2. Users:
--    - admin@library.com (ADMIN)
--    - librarian@library.com (ADMIN)
--    - user1@example.com, user2@example.com, etc. (USER)
--
-- 3. Relationships:
--    - book_authors (Many-to-Many junction table)
--    - book_publishers (Many-to-Many junction table)
--    - loan.book_id -> book.id (Many-to-One)
--    - loan.user_id -> user.id (Many-to-One)
--
-- 4. Sample data includes:
--    - 6 users (2 admins, 4 regular users)
--    - 10 books (classic literature + fantasy)
--    - 8 authors (some books share authors)
--    - 10 publishers (some books have multiple publishers)
--    - 23 loans (active, reserved, and returned)
--
-- 5. Book availability is tracked via loaned_copies
--    Available copies = total_copies - loaned_copies
-- ================================================

