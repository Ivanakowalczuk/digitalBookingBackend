USE `0523TDPRON2C03LAED1021PT_GRUPO7`;

-- -----------------------------------------------------
-- Table `0523TDPRON2C03LAED1021PT_GRUPO7`.`categorias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `0523TDPRON2C03LAED1021PT_GRUPO7`.`categorias`;

CREATE TABLE IF NOT EXISTS `0523TDPRON2C03LAED1021PT_GRUPO7`.`categorias`
(
    `idCategoria`         INT          NOT NULL AUTO_INCREMENT,
    `codigo`              VARCHAR(10)  NOT NULL,
    `nombre`              VARCHAR(100) NOT NULL,
    `fechaCreacion`       DATETIME     NOT NULL,
    `indicadorHabilitado` TINYINT(1)   NOT NULL,
    PRIMARY KEY (`idCategoria`),
    UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC)
    )
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `0523TDPRON2C03LAED1021PT_GRUPO7`.`productos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `0523TDPRON2C03LAED1021PT_GRUPO7`.`productos`;

CREATE TABLE IF NOT EXISTS `0523TDPRON2C03LAED1021PT_GRUPO7`.`productos`
(
    `idProducto`          INT            NOT NULL AUTO_INCREMENT,
    `idCategoria`         INT            NOT NULL,
    `nombre`              VARCHAR(200)   NOT NULL,
    `marca`               VARCHAR(100)   NULL,
    `modelo`              VARCHAR(45)    NULL,
    `precio`              DECIMAL(12, 2) NOT NULL,
    `indicadorHabilitado` TINYINT(1)     NOT NULL,
    `descripcion`         VARCHAR(500)   NOT NULL,
    `imagen`              BLOB           NOT NULL,
    PRIMARY KEY (`idProducto`),
    UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE,
    INDEX `fk_productos_categorias_idx` (`idCategoria` ASC),
    CONSTRAINT `fk_productos_categorias`
    FOREIGN KEY (`idCategoria`)
    REFERENCES `0523TDPRON2C03LAED1021PT_GRUPO7`.`categorias` (`idCategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    )
    ENGINE = InnoDB;