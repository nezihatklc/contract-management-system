CREATE DATABASE IF NOT EXISTS contact_app_db
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_turkish_ci;
CREATE USER IF NOT EXISTS 'myuser'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON contact_app_db.* TO 'myuser'@'localhost';
FLUSH PRIVILEGES; 

USE contact_app_db;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    birth_date DATE,
    role ENUM('MANAGER', 'SENIOR_DEVELOPER', 'JUNIOR_DEVELOPER', 'TESTER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, password_hash, name, surname, phone, birth_date, role)
VALUES
('tt',  'tt',  'Test',    'User',     '+905551112233', '1998-04-10', 'TESTER'),
('jd',  'jd',  'Junior',  'Dev',      '+905552223344', '2000-01-15', 'JUNIOR_DEVELOPER'),
('sd',  'sd',  'Senior',  'Dev',      '+905553334455', '1995-07-22', 'SENIOR_DEVELOPER'),
('man', 'man', 'Project', 'Manager',  '+905554445566', '1990-03-05', 'MANAGER');

-- Updated user names (Team Sync)
UPDATE users SET name='Nezihat', surname='Kılıç' WHERE user_id=1;
UPDATE users SET name='Pelin', surname='Cömertler' WHERE user_id=2;
UPDATE users SET name='Simay', surname='Mutlu' WHERE user_id=3;
UPDATE users SET name='Sıla', surname='Şimşek' WHERE user_id=4;

CREATE TABLE contacts (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    phone_primary VARCHAR(20) NOT NULL,
    phone_secondary VARCHAR(20),
    email VARCHAR(100) NOT NULL,
    linkedin_url VARCHAR(255),
    birth_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL
);

INSERT INTO contacts 
(first_name, middle_name, last_name, nickname, city, phone_primary, phone_secondary, email, linkedin_url, birth_date)
VALUES
('Ahmet','Kemal','Yılmaz','ahmetk_98','İstanbul','+905501112233',NULL,'ahmet.kemal98@example.com','https://linkedin.com/in/ahmetyilmaz','1998-04-10'),
('Mehmet',NULL,'Kara','mehmet_55','Ankara','+905502223344','+905502220000','mehmetkara_95@example.com',NULL,'1995-07-22'),
('Ayşe','Naz','Demir','ay$enaz23','İzmir','+905503334455',NULL,'ayse.naz23@example.com','https://linkedin.com/in/aysedemir','2000-01-15'),
('Emre','Can','Aydın','emrecan_98','Adana','+905505556677','+905505550000','emre.can98@example.com','https://linkedin.com/in/emreaydin','1998-05-12'),
('Hakan',NULL,'Arslan','hakan_07','Antalya','+905506667788',NULL,'hakanarslan07@example.com',NULL,'1988-11-05'),
('Zeynep',NULL,'Çelik','zey_2001','Trabzon','+905507778899',NULL,'zeynep.celik01@example.com','https://linkedin.com/in/zcelik','2001-02-20'),
('Burak','Hale','Öztürk','burak1907','Gaziantep','+905508889900','+905508880000','burak.ozturk07@example.com',NULL,'1994-03-03'),
('Gizem',NULL,'Şahin','gizem_07','Kayseri','+905509990011',NULL,'gizem.sahin07@example.com','https://linkedin.com/in/gizemsahin','1996-12-12'),
('Onur','Serhat','Polat','onursp_93','Mersin','+905510101121',NULL,'onur.polat93@example.com',NULL,'1993-06-10'),
('Didem',NULL,'Güneş','dido_01','Hatay','+905511212232',NULL,'didemgunes01@example.com','https://linkedin.com/in/didemgunes','1999-01-01'),
('Barış','Ali','Uçar','barış_ucar92','Eskişehir','+905512323343','+905512320000','baris.ucar92@example.com',NULL,'1992-08-15'),
('Elif','Nisa','Avcı','elifnisa02','Kocaeli','+905513434454',NULL,'elifnisa2002@example.com','https://linkedin.com/in/elifavci','2002-10-25'),
('Tolga',NULL,'Baş','togo_87','Samsun','+905514545565',NULL,'tolgabas87@example.com',NULL,'1987-12-30'),
('Nazan','Su','Tekin','nazan_su98','Denizli','+905515656676',NULL,'nazan.tekin98@example.com',NULL,'1998-05-07'),
('Selim','Yunus','Koç','selimk_94','Malatya','+905516767787',NULL,'selimkoc94@example.com','https://linkedin.com/in/selimkoc','1994-09-10'),
('Tuğçe','Irmak','Yıldırım','tuğce_irm','Erzurum','+905517878898','+905517870000','tugce.yildirim97@example.com',NULL,'1997-03-22'),
('Kaan',NULL,'Özdemir','kaanx44','Tekirdağ','+905518989909',NULL,'kaan.ozdemir44@example.com','https://linkedin.com/in/kaanozdemir','2001-06-06'),
('Ceren',NULL,'Dinç','ceren_d93','Rize','+905519090120',NULL,'ceren.dinc93@example.com',NULL,'1993-04-14'),
('Mert','Can','Ateş','mertates_92','Çanakkale','+905520201221',NULL,'mert.ates92@example.com','https://linkedin.com/in/mertates','1992-07-17'),
('Sude','Mina','Acar','sudemina_00','Manisa','+905521212232',NULL,'sude.acar00@example.com',NULL,'2000-06-23'),
('Ali',NULL,'Yaman','ali_y98','Muğla','+905522323343','+905522320000','aliyaman98@example.com','https://linkedin.com/in/aliyaman','1998-08-04'),
('Eylül',NULL,'Turan','eylul_1999','Aydın','+905523434454',NULL,'eylul.turan99@example.com',NULL,'1999-03-19'),
('Deniz','Aslı','Er','deniz.er96','Konya','+905524545565',NULL,'deniz.asli96@example.com','https://linkedin.com/in/denizer','1996-01-11'),
('Koray',NULL,'Sezer','koray_89','Sivas','+905525656676',NULL,'koray.sezer89@example.com',NULL,'1989-12-09'),
('Aslı','Gül','Orhan','aslııgül_97','Van','+905526767787',NULL,'asli.gul97@example.com','https://linkedin.com/in/asliorhan','1997-10-10'),
('Berk',NULL,'Güler','berk1905','Batman','+905527878898','+905527870000','berk.guler1905@example.com',NULL,'1995-05-28'),
('Melisa','Naz','Kar','melisanaz_01','Kütahya','+905528989909',NULL,'melisa.kar01@example.com','https://linkedin.com/in/melisakar','2001-09-14'),
('Deniz',NULL,'Şen','denizsen_94','Mardin','+905529090120',NULL,'deniz.sen94@example.com',NULL,'1994-07-20'),
('Serhat','Can','Demirtaş','serhatc_93','Kars','+905530201221',NULL,'serhatc93@example.com','https://linkedin.com/in/serhatdemirtas','1993-11-03'),
('Sinem',NULL,'Ateş','sinem_98','Şırnak','+905531212232',NULL,'sinem.ates98@example.com',NULL,'1998-02-12'),
('Furkan','Yiğit','Boz','furkanboz_95','Ordu','+905532323343',NULL,'furkan.boz95@example.com','https://linkedin.com/in/furkanboz','1995-03-18'),
('Ezgi',NULL,'Eker','ezgi_eker00','Bolu','+905533434454','+905533430000','ezgi.eker00@example.com',NULL,'2000-10-09'),
('Gökhan','Emre','Sağlam','gokhan_sag88','Yozgat','+905534545565',NULL,'gokhan.saglam88@example.com','https://linkedin.com/in/gokhansaglam','1988-04-04'),
('Eda',NULL,'Soylu','edaso_97','Tokat','+905535656676',NULL,'eda.soylu97@example.com',NULL,'1997-06-21'),
('Yiğit','Efe','Şimşek','yigit_ef96','Kırşehir','+905536767787',NULL,'yigit.efesimsek96@example.com','https://linkedin.com/in/yigitsimsek','1996-07-29'),
('Buse','Sude','Taş','buse_sude01','Siirt','+905537878898',NULL,'buse.sude01@example.com',NULL,'2001-12-11'),
('Okan',NULL,'Ersoy','okaners_93','Çorum','+905538989909','+905538980000','okan.ersoy93@example.com','https://linkedin.com/in/okanersoy','1993-02-14'),
('Merve','İlayda','Çolak','mervecl_00','Bilecik','+905539090120',NULL,'merve.colak00@example.com',NULL,'2000-03-03'),
('Halil',NULL,'Kurt','halilk_92','Ağrı','+905540201221',NULL,'halil.kurt92@example.com','https://linkedin.com/in/halilkurt','1992-10-01'),
('Pelin','Nur','Gök','pelin_1999','Bayburt','+905541212232',NULL,'pelin.gok99@example.com',NULL,'1999-09-16'),
('Samet',NULL,'Gezgin','sametgz_94','Bitlis','+905542323343',NULL,'samet.gezgin94@example.com','https://linkedin.com/in/sametgezgin','1994-10-20'),
('İrem','Melis','Ulus','iremm_98','Tunceli','+905543434454','+905543430000','irem.melis98@example.com',NULL,'1998-08-08'),
('Oğuz',NULL,'Acar','oguza35','Giresun','+905544545565',NULL,'oguz.acar35@example.com','https://linkedin.com/in/oguzacar','1997-01-27'),
('Hilal','Su','Demirci','hilalsu_96','Ardahan','+905545656676',NULL,'hilal.su96@example.com',NULL,'1996-05-10'),
('Batuhan',NULL,'Çoban','batu1907','Hakkari','+905546767787',NULL,'batu.coban07@example.com','https://linkedin.com/in/batuhancoban','2000-11-22'),
('Selda','Naz','Örnek','seldanaz_93','Sinop','+905547878898',NULL,'selda.ornek93@example.com',NULL,'1993-08-14'),
('Cem','Kerem','Bay','cemk_91','Uşak','+905548989909','+905548980000','cem.bay91@example.com','https://linkedin.com/in/cembay','1991-03-19'),
('Neşe',NULL,'Koşar','neşe_k99','Gümüşhane','+905549090120',NULL,'nese.kosar99@example.com',NULL,'1999-07-04'),
('Tayfun','Said','Özen','tayfuno_95','Nevşehir','+905550201221',NULL,'tayfun.ozen95@example.com','https://linkedin.com/in/tayfunozen','1995-12-29'),
('Arda',NULL,'Kurt','ardak_01','İstanbul','+905551111111',NULL,'arda.kurt01@example.com',NULL,'1999-02-14'),
('Selin','Nur','Acar','selinacar_97','Ankara','+905552222222',NULL,'selin.acar97@example.com','https://linkedin.com/in/selinacar','1997-08-30'),
('Korhan',NULL,'Yıldız','korhany_88','İzmir','+905553333333',NULL,'korhan.yildiz88@example.com',NULL,'1988-11-21'),
('Meryem','Su','Tan','meryemsu_02','Bursa','+905554444444',NULL,'meryem.tan02@example.com','https://linkedin.com/in/meryemtan','2002-07-12'),
('Efe',NULL,'Güler','efeg_95','Konya','+905555555555','+905555550000','efe.guler95@example.com',NULL,'1995-05-05'),
('Derya','Melis','Kara','deryakarax','Eskişehir','+905556666666',NULL,'derya.kara@example.com','https://linkedin.com/in/deryakara','1998-10-09'),
('Umut',NULL,'Saygın','umut_s99','Adana','+905557777777',NULL,'umut.saygin99@example.com',NULL,'1999-12-03'),
('Nazlı','Ece','Ergin','nazlıece98','Trabzon','+905558888888',NULL,'nazliece98@example.com','https://linkedin.com/in/nazliece','1998-01-17'),
('Baturalp',NULL,'Aslan','batu_aslan','Mersin','+905559999999',NULL,'baturalp.aslan@example.com',NULL,'1996-04-28'),
('Seda','Naz','Arı','sedanaz_96','Kayseri','+905560000000',NULL,'seda.ari96@example.com',NULL,'1996-03-19'),
('Yasin','Ali','Özer','yasino_91','Kocaeli','+905561111122','+905561110000','yasin.ozer91@example.com','https://linkedin.com/in/yasinozer','1991-09-11'),
('Elvan','Gül','Gök','elvang_02','Aydın','+905562222233',NULL,'elvan.gok02@example.com',NULL,'2002-02-23'),
('Miraç',NULL,'Demirel','miraç_90','Antalya','+905563333344',NULL,'mirac.demirel90@example.com','https://linkedin.com/in/miracdemirel','1990-06-18'),
('Safiye','İlayda','Ulu','safiyu_00','Hatay','+905564444455',NULL,'safiye.ulu00@example.com',NULL,'2000-04-04'),
('Berkay',NULL,'Çınar','berkc_93','Malatya','+905565555566',NULL,'berkay.cinar93@example.com','https://linkedin.com/in/berkaycinar','1993-07-07'),
('Gülce','Naz','Ay','gulcen_01','Samsun','+905566666677',NULL,'gulce.ay01@example.com',NULL,'2001-11-26'),
('Kerem',NULL,'Koçak','keremkc_89','Denizli','+905567777788','+905567770000','kerem.kocak89@example.com','https://linkedin.com/in/keremkocak','1989-02-09'),
('Tuana','Mina','Öz','tuanaoz_03','Tekirdağ','+905568888899',NULL,'tuana.oz03@example.com',NULL,'2003-08-02'),
('Rıza',NULL,'Sağır','rıza_sağ94','Van','+905569999900',NULL,'riza.sagir94@example.com','https://linkedin.com/in/rizasagir','1994-10-30'),
('Elçin','Ece','Arslan','elçinars_97','Muğla','+905570111122',NULL,'elcin.arslan97@example.com',NULL,'1997-03-14'),
('Halime',NULL,'Özkan','halimeozk_90','Kütahya','+905571212233',NULL,'halime.ozkan90@example.com','https://linkedin.com/in/halimeozkan','1990-12-12'),
('Alican','Serhat','Uluç','alicanulc','Kırıkkale','+905572323344','+905572320000','alican.uluc@example.com',NULL,'1992-01-22'),
('Pırıl','Naz','Çetinkaya','pirilctk','Siirt','+905573434455',NULL,'piril.cetinkaya@example.com','https://linkedin.com/in/pirilcetinkaya','2000-09-09'),
('Harun',NULL,'Dikmen','harund_91','Giresun','+905574545566',NULL,'harun.dikmen91@example.com',NULL,'1991-05-15'),
('Ceyda','İrem','Uzun','ceydairem_98','Balıkesir','+905576767788',NULL,'ceyda.uzun98@example.com','https://linkedin.com/in/ceydairemuzun','1998-07-19'),
('Nisa','Meltem','Sarı','nisams_99','Tokat','+905575656677',NULL,'nisa.sari99@example.com','https://linkedin.com/in/nisasari','1999-06-02');



UPDATE users 
SET password_hash = '63fVi4XgA8Z/Qu7gFXmzXC8eLSbgz1aPS1Vqyl1+f0A=:zQ+105ZbfEWBC4ZzVhMdUA=='
WHERE username = 'tt';

UPDATE users 
SET password_hash = '1SEyaD4T3hOJKC6GprcNFgv59RI63Uv3sSNq2bVDJ1I=:ESqKxhWaj831hm7GaXeoLg=='
WHERE username = 'jd';

UPDATE users 
SET password_hash = '1gsk4Z9iB7ePlnP2YpJETFZpxTB2uQDvLIWqIJ7UpjA=:kHlr9n5p8b18faBtWZyFyQ=='
WHERE username = 'sd';

UPDATE users 
SET password_hash = 'ORQoC32DpxWzoQ7W2U3d/DHN7ndX9JNcjsJ12XXJUJ0=:8bAAMfLnEGEp+FR9igTx5A=='
WHERE username = 'man';
