CREATE DATABASE package_lockerdb;
DROP DATABASE package_lockerdb;
USE package_lockerdb;

CREATE TABLE package_lockers (
id_package_locker INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
address_locker VARCHAR(100) NOT NULL,

id_locker_total_lockers INT
);

CREATE TABLE total_lockers (
id_locker INT NOT NULL,
number_small_total_lockers INT NOT NULL,
number_medium_total_lockers INT NOT NULL,
number_big_total_lockers INT NOT NULL,

id_package_lockers INT
);

CREATE TABLE clients (
id_client INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
name_client VARCHAR(45) NOT NULL,
last_name_client VARCHAR(45) NOT NULL,
email_client VARCHAR(45) NOT NULL,
phone_number_client BIGINT NOT NULL,
password_client VARCHAR(100) NOT NULL
);

CREATE TABLE staffers (
id_staffer INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
name_staffer VARCHAR(45) NOT NULL,
last_name_staffer VARCHAR(45) NOT NULL,
email_staffer VARCHAR(45) NOT NULL,
phone_number_staffer BIGINT NOT NULL,
password_staffer VARCHAR(100) NOT NULL
);

CREATE TABLE packages (
id_pack INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
size ENUM('small', 'medium', 'big') NOT NULL,
price DECIMAL(5,2) NOT NULL,

id_locker INT
);

CREATE TABLE lockers(
id_locker INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
size ENUM('small','medium','big') NOT NULL,
isEmpty BOOLEAN,

id_package_lockers INT
);

CREATE TABLE shipment(
id_shipment INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
shipment_date DATE NOT NULL,
collection_date DATE,

id_client_sender_package INT,
id_client_receiver_package INT,
id_package INT,
id_package_locker_sender INT,
id_package_locker_receiver INT
);

SET FOREIGN_KEY_CHECKS=1;

ALTER TABLE packages ADD CONSTRAINT fk1 FOREIGN KEY (id_locker) REFERENCES lockers(id_locker);
ALTER TABLE total_lockers ADD CONSTRAINT fk5 FOREIGN KEY (id_package_lockers) REFERENCES package_lockers(id_package_locker);
ALTER TABLE shipment ADD CONSTRAINT fk8 FOREIGN KEY (id_client_receiver_package) REFERENCES clients(id_client);
ALTER TABLE shipment ADD CONSTRAINT fk9 FOREIGN KEY (id_client_sender_package) REFERENCES clients(id_client);
ALTER TABLE shipment ADD CONSTRAINT fk10 FOREIGN KEY (id_package) REFERENCES packages(id_pack);
ALTER TABLE shipment ADD CONSTRAINT fk11 FOREIGN KEY (id_package_locker_sender) REFERENCES package_lockers(id_package_locker);
ALTER TABLE shipment ADD CONSTRAINT fk12 FOREIGN KEY (id_package_locker_receiver) REFERENCES package_lockers(id_package_locker);

SELECT * FROM packages;
SELECT * FROM shipment;
SELECT * FROM package_lockers;
SELECT * FROM staffers;
SELECT * FROM lockers;
SELECT * FROM clients;

-- ************************** WIDOK DLA PACZKOMATU **************************
create view packageLockerView as
select 
l.id_package_lockers,
calculateId(l.id_locker, l.id_package_lockers) as 'id_locker', s.shipment_date,
l.isEmpty as 'isEmpty' from lockers l
join packages p on p.id_locker = l.id_locker
join shipment s on p.id_pack = s.id_shipment
where l.isEmpty = 0;

select * from packageLockerView;

-- ************************** WIDOK DLA KLIENTA **************************
create view packagesView as
select p.id_pack, p.size, s.shipment_date, s.collection_date, p.price, 
concat(c.name_client,' ',c.last_name_client) as 'sender',
concat(cr.name_client,' ',cr.last_name_client)  as 'receiver',
pls.address_locker as 'sender_locker_address', plr.address_locker as 'receiver_locker_address' from packages p
join shipment s on p.id_pack = s.id_shipment
join clients c on s.id_client_sender_package = c.id_client
join clients cr on s.id_client_receiver_package = cr.id_client
join package_lockers pls on s.id_package_locker_sender = pls.id_package_locker
join package_lockers plr on s.id_package_locker_receiver = plr.id_package_locker
order by p.id_pack;

select * from packagesView;

-- ************************** WIDOK DLA FIRMY **************************
create view companyView as
select p.id_pack, p.size, s.shipment_date, s.collection_date, p.price, 
concat(c.name_client,' ',c.last_name_client) as 'sender',
concat(cr.name_client,' ',cr.last_name_client)  as 'receiver',
pls.address_locker as 'sender_locker_address', plr.address_locker as 'receiver_locker_address',
calculateId(p.id_locker,plr.id_package_locker) as 'id_locker' from packages p
join shipment s on p.id_pack = s.id_shipment
join clients c on s.id_client_sender_package = c.id_client
join clients cr on s.id_client_receiver_package = cr.id_client
join package_lockers pls on s.id_package_locker_sender = pls.id_package_locker
join package_lockers plr on s.id_package_locker_receiver = plr.id_package_locker
order by p.id_pack;

select * from companyView;

-- ************************** WIDOK ADRESÓW PACZKOMATÓW ******************
create view addressesView as 
select id_package_locker, address_locker from package_lockers
order by id_package_locker;

-- ************************** WIDOK DLA SUMY PROFITÓW DLA OKRESU CZASU ******************
DROP VIEW sumProfits;

CREATE VIEW sumProfits AS
SELECT pl.id_package_locker, pl.address_locker, sum(p.price) as 'profit', s.shipment_date FROM packages p
JOIN shipment s ON p.id_pack = s.id_shipment
JOIN package_lockers pl ON pl.id_package_locker = s.id_package_locker_sender
GROUP BY pl.id_package_locker order by sum(p.price) desc;

select * from sumProfits where shipment_date = '2021-02-10';

-- ******************** FUNKCJE / PROCEDURY / TRIGGERY *******************************

-- ********************************* OBLICZANIE CENY PACZKI *********************************
DROP FUNCTION calculatePrice;

DELIMITER //
CREATE FUNCTION calculatePrice(size ENUM('small', 'medium', 'big'))
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN

DECLARE response DECIMAL(5,2);

IF size = 'small' THEN SET response = 9.99;
ELSEIF size = 'medium' THEN SET response = 12.99;
ELSEIF size = 'big' THEN SET response = 15.99;
END IF;

RETURN response;
END; //
DELIMITER ;

-- ********************************* NADANIE PACZKI *********************************
DROP PROCEDURE sendPackage;

DELIMITER // 

CREATE PROCEDURE sendPackage(IN size1 ENUM('small', 'medium', 'big'), IN id_sender INT,
IN id_receiver INT, IN id_sender_package_locker INT, IN id_receiver_package_locker INT)
DETERMINISTIC
BEGIN

DECLARE idMin INT;
DECLARE idFromPackages INT;
DECLARE sizeOfLocker ENUM('small', 'medium', 'big');

IF size1 = 'small' AND sendSmall(id_receiver_package_locker) != 0 THEN 
SET idMin = sendSmall(id_receiver_package_locker);

ELSEIF size1 = 'medium' AND sendMedium(id_receiver_package_locker) != 0 THEN
SET idMin = sendMedium(id_receiver_package_locker);

ELSEIF size1 = 'big' AND sendBig(id_receiver_package_locker) != 0 THEN
SET idMin = sendBig(id_receiver_package_locker);

ELSE 

call package_lockerdb.raise(12345, 'There is no free locker for your package :(');
END IF;

SELECT size INTO sizeOfLocker FROM lockers WHERE id_locker = idMin;

INSERT INTO packages (size, price, id_locker)  
VALUES (size1 , calculatePrice(sizeOfLocker), idMin);

SELECT max(id_pack) INTO idFromPackages FROM packages;

INSERT INTO shipment(shipment_date, collection_date , id_client_sender_package, id_client_receiver_package, id_package, id_package_locker_sender, id_package_locker_receiver)
VALUES
(current_date(), null, id_sender, id_receiver, idFromPackages, id_sender_package_locker, id_receiver_package_locker);

UPDATE lockers SET isEmpty = 0 WHERE id_locker = idMin;

END; //
DELIMITER ;

-- *************************************** SMALL ********************************
DELIMITER //
CREATE FUNCTION sendSmall(id_receiver_package_locker INT)
RETURNS INT
DETERMINISTIC
BEGIN

DECLARE isEmptyLocker BOOLEAN;
DECLARE sizeOfLocker ENUM('small', 'medium', 'big');
DECLARE idMin INT;
DECLARE idMax INT;
DECLARE idFromPackages INT;

SELECT id_locker INTO idMin FROM lockers WHERE id_package_lockers = id_receiver_package_locker ORDER BY id_locker LIMIT 1;
SELECT id_locker INTO idMax FROM lockers WHERE id_package_lockers = id_receiver_package_locker ORDER BY id_locker DESC LIMIT 1;
SELECT isEmpty INTO isEmptyLocker FROM lockers WHERE id_locker = idMin;
 
IF isEmptyLocker != 1 THEN
 
myloop: WHILE (isEmptyLocker != 1) DO
SELECT isEmpty INTO isEmptyLocker FROM lockers WHERE id_locker = idMin;
SET idMin = idMin + 1;

IF idMin > idMax +1 THEN 
LEAVE myloop;
END IF;
END WHILE myloop;

SET idMin = idMin - 1;
END IF;
 
IF idMin > idMax OR idMin = null OR idMax = null THEN return 0;
ELSE return idMin;
END IF;
 
END; //

DELIMITER ;

-- **************************** MEDIUM ***************************
DELIMITER //
CREATE FUNCTION sendMedium(id_receiver_package_locker INT)
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE isEmptyLocker BOOLEAN;
DECLARE idMin INT;
DECLARE idMax INT;

DECLARE idFromPackages INT;
SELECT id_locker INTO idMin FROM lockers WHERE id_package_lockers = id_receiver_package_locker AND size = 'medium' ORDER BY id_locker LIMIT 1;
SELECT id_locker INTO idMax FROM lockers WHERE id_package_lockers = id_receiver_package_locker ORDER BY id_locker DESC LIMIT 1;

SELECT isEmpty INTO isEmptyLocker FROM lockers WHERE id_locker = idMin;
 
IF isEmptyLocker != 1 THEN
 
myloop: WHILE (isEmptyLocker != 1) DO
SELECT isEmpty INTO isEmptyLocker FROM lockers WHERE id_locker = idMin;
SET idMin = idMin + 1;

IF idMin > idMax +1 THEN 
LEAVE myloop;
END IF;
END WHILE myloop;

SET idMin = idMin - 1;
END IF;
 
 IF idMin > idMax OR idMin = null OR idMax = null THEN return 0;
 ELSE return idMin;
 END IF;
 
 END; //
DELIMITER ;

-- **************************** BIG ***************************
DELIMITER //
CREATE FUNCTION sendBig(id_receiver_package_locker INT)
RETURNS INT
DETERMINISTIC
BEGIN

DECLARE isEmptyLocker BOOLEAN;
DECLARE idMin INT;
DECLARE idMax INT;
DECLARE idFromPackages INT;

SELECT id_locker INTO idMin FROM lockers WHERE id_package_lockers = id_receiver_package_locker AND size = 'big' ORDER BY id_locker LIMIT 1;
SELECT id_locker INTO idMax FROM lockers WHERE id_package_lockers = id_receiver_package_locker ORDER BY id_locker DESC LIMIT 1;
SELECT isEmpty INTO isEmptyLocker FROM lockers WHERE id_locker = idMin;
 
IF isEmptyLocker != 1 THEN
 
myloop: WHILE (isEmptyLocker != 1) DO
SELECT isEmpty INTO isEmptyLocker FROM lockers WHERE id_locker = idMin;
SET idMin = idMin + 1;

IF idMin > idMax +1 THEN 
LEAVE myloop;
END IF;
END WHILE myloop;

SET idMin = idMin - 1;
END IF;
 
IF idMin > idMax OR idMin = null OR idMax = null THEN return 0;
ELSE return idMin;
END IF;
 
END; //
DELIMITER ;
 
-- ********************************* ODBIÓR PACZKI *********************************
 DELIMITER //
CREATE PROCEDURE receivePackage(IN id_package INT,IN id_receiver INT)
DETERMINISTIC
BEGIN

DECLARE id_locker1 INT;

SELECT id_locker INTO id_locker1 FROM packages where id_package = id_pack;

UPDATE shipment SET collection_date = current_date() WHERE id_shipment = id_package;
UPDATE lockers SET isEmpty = 1 WHERE id_locker = id_locker1;

END; //
DELIMITER ;

-- **************************************** TWORZENIE SKRYTEK DLA KAŻDEGO PACZKOMATU ****************************
DELIMITER //
CREATE PROCEDURE createLockers(IN id_package_locker1 INT)
DETERMINISTIC
BEGIN

DECLARE numberOfSmallLockers INT;
DECLARE numberOfMediumLockers INT;
DECLARE numberOfBigLockers INT;
DECLARE x INT DEFAULT 1;
DECLARE y INT DEFAULT 1;
DECLARE z INT DEFAULT 1;

SELECT t.number_small_total_lockers INTO numberOfSmallLockers FROM
package_lockers p JOIN total_lockers t ON p.id_package_locker = t.id_locker
WHERE t.id_locker = id_package_locker1;

WHILE x <= numberOfSmallLockers DO
    INSERT INTO lockers (size, isEmpty, id_package_lockers)
    VALUES ('small', 1, id_package_locker1);
    
    SET x = x + 1;
END WHILE;

-- medium

SELECT t.number_medium_total_lockers INTO numberOfMediumLockers FROM
package_lockers p JOIN total_lockers t ON p.id_package_locker = t.id_locker
WHERE t.id_locker = id_package_locker1;

WHILE y <= numberOfMediumLockers DO
    INSERT INTO lockers (size, isEmpty, id_package_lockers)
    VALUES ('medium', 1, id_package_locker1);
    
    SET y = y + 1;
END WHILE;

-- big

SELECT t.number_big_total_lockers INTO numberOfBigLockers FROM
package_lockers p JOIN total_lockers t ON p.id_package_locker = t.id_locker
WHERE t.id_locker = id_package_locker1;

WHILE z <= numberOfBigLockers DO
    INSERT INTO lockers (size, isEmpty, id_package_lockers)
    VALUES ('big', 1, id_package_locker1);
    
    SET z = z + 1;
END WHILE;

END; //
DELIMITER ;

-- ****************************************** ZYSK W DANYM DNIU ***********************************************
 DELIMITER //
CREATE FUNCTION profitCalculator(date1 DATE)
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
DECLARE sumFromPackages DECIMAL(5,2);

SELECT sum(p.price) INTO sumFromPackages FROM packages p 
JOIN shipment s ON id_shipment = id_pack WHERE shipment_date = date1;

return sumFromPackages;
END; //
DELIMITER ;

-- ***************************************** ERRORRR ********************************************************
DELIMITER //
CREATE PROCEDURE `raise`(`errno` BIGINT UNSIGNED, `message` VARCHAR(256))
BEGIN
SIGNAL SQLSTATE
    'ERR0R'
SET
    MESSAGE_TEXT = `message`,
    MYSQL_ERRNO = `errno`;
END
//
DELIMITER ;

DELIMITER //
CREATE FUNCTION calculateId(id INT, id_package_locker INT)
RETURNS INT
DETERMINISTIC
BEGIN

return id - 60*(id_package_locker-1);

END //
DELIMITER ;