DROP TABLE IF EXISTS equipo;
CREATE TABLE equipo(
    serial NUMERIC PRIMARY KEY,
    nombre VARCHAR(200),
    fecha_compra DATE,
    valor_compra DOUBLE
)