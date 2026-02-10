[Practica UD4 Bases de datos OR.pdf](https://github.com/user-attachments/files/25196967/Practica.UD4.Bases.de.datos.OR.pdf)

CREATE TYPE tipo_via AS ENUM ('calle', 'plaza', 'avenida');

CREATE TYPE direccion AS (
  via    tipo_via,
  nombre text,
  numero integer,
  cp     char(5)
);

/* =========================================================
   3) Esquema y tablas
   ========================================================= */
SET search_path TO public;

DROP TABLE IF EXISTS alquileres CASCADE;
DROP TABLE IF EXISTS vehiculos CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;

CREATE TABLE clientes (
  dni      char(9) PRIMARY KEY,
  nombre   varchar(50),
  email    varchar(50),
  telefono varchar(50),
  direccion      direccion  -- tipo compuesto
);

CREATE TABLE vehiculos (
  matricula char(7) PRIMARY KEY,
  marca     varchar(50),
  modelo    varchar(50),
  disponible boolean NOT NULL DEFAULT TRUE
);

CREATE TABLE alquileres (
  id        bigserial PRIMARY KEY,
  dni       char(9) REFERENCES clientes(dni)   ON UPDATE CASCADE ON DELETE RESTRICT,
  matricula char(7) REFERENCES vehiculos(matricula) ON UPDATE CASCADE ON DELETE RESTRICT,
  fecha_inicio date,
  fecha_fin    date
);

CREATE INDEX IF NOT EXISTS idx_alquileres_dni       ON alquileres(dni);
CREATE INDEX IF NOT EXISTS idx_alquileres_matricula ON alquileres(matricula);

/* =========================================================
   4) Datos: CLIENTES (10 filas) con "direccion" compuesta
   ========================================================= */
INSERT INTO clientes (dni, nombre, email, telefono, direccion) VALUES
 ('12345678A', 'Juan Pérez', 'juan.perez@example.com', '600123456'
   , ROW('avenida','del Puerto',10,'46021')::direccion)
,('23456789B', 'Ana López', 'ana.lopez@example.com', '611987654'
   , ROW('calle','San Vicente',45,'46002')::direccion)
,('34567890C', 'Carlos Ruiz', 'carlos.ruiz@example.com', '622345678'
   , ROW('plaza','del Ayuntamiento',3,'46003')::direccion)
,('45678901D', 'María Torres', 'maria.torres@example.com', '633456789'
   , ROW('calle','Colón',12,'46004')::direccion)
,('56789012E', 'Pedro Sánchez', 'pedro.sanchez@example.com', '644567890'
   , ROW('avenida','Blasco Ibáñez',120,'46010')::direccion)
,('67890123F', 'Laura Martínez', 'laura.martinez@example.com', '655678901'
   , ROW('calle','Caballeros',28,'46001')::direccion)
,('78901234G', 'Javier Gómez', 'javier.gomez@example.com', '666789012'
   , ROW('plaza','de la Reina',7,'46003')::direccion)
,('89012345H', 'Sara Navarro', 'sara.navarro@example.com', '677890123'
   , ROW('avenida','de Aragón',5,'46021')::direccion)
,('90123456J', 'Diego Herrera', 'diego.herrera@example.com', '688901234'
   , ROW('calle','Micer Mascó',14,'46010')::direccion)
,('01234567K', 'Elena Vidal', 'elena.vidal@example.com', '699012345'
   , ROW('calle','Serrería',100,'46022')::direccion);

/* =========================================================
   5) Datos: VEHICULOS (10 filas)
   ========================================================= */
INSERT INTO vehiculos (matricula, marca, modelo, disponible) VALUES
 ('1122PQR', 'Mazda', 'Mazda3', FALSE)
,('1234ABC', 'Toyota', 'Corolla', TRUE)
,('7788YZA', 'Kia', 'Rio', FALSE)
,('9012GHI', 'Renault', 'Clio', TRUE)
,('9900BCD', 'Hyundai', 'i30', FALSE)
,('2233RST', 'Ford', 'Focus', TRUE)
,('3344LMN', 'Seat', 'Ibiza', FALSE)
,('4455JKL', 'Volkswagen', 'Golf', TRUE)
,('5566DEF', 'Peugeot', '308', FALSE)
,('6677UVW', 'Citroën', 'C4', TRUE);

/* =========================================================
   6) Datos: ALQUILERES (5 filas)
   ========================================================= */
INSERT INTO alquileres (dni, matricula, fecha_inicio, fecha_fin) VALUES
 ('12345678A', '1234ABC', '2025-06-10', '2025-06-14')
,('23456789B', '9012GHI', '2025-07-01', '2025-07-05')
,('34567890C', '9900BCD', '2025-08-12', '2025-08-18')
,('45678901D', '7788YZA', '2025-09-20', '2025-09-22')
,('56789012E', '1122PQR', '2025-10-03', '2025-10-10');
