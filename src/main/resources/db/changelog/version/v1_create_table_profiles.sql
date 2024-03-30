--changeset author:wanchalerm 1
CREATE TABLE customers.profiles (
    id INT NOT NULL AUTO_INCREMENT ,
    code CHAR(36) NOT NULL ,
    first_name VARCHAR(50) NOT NULL ,
    last_name INT NOT NULL ,
    birth_date DATE NOT NULL ,
    created_timestamp TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ,
    updated_timestamp TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ,
    is_deleted TINYINT(1) NOT NULL  DEFAULT 0,
PRIMARY KEY (id)) ENGINE = InnoDB;