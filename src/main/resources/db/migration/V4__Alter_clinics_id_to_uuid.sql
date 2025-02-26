CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE clinics ADD COLUMN id_temp UUID DEFAULT uuid_generate_v4();

UPDATE clinics SET id_temp = uuid_generate_v4();

ALTER TABLE clinics ALTER COLUMN id_temp SET NOT NULL;

ALTER TABLE clinics DROP CONSTRAINT clinics_pkey;
ALTER TABLE clinics DROP COLUMN id;

ALTER TABLE clinics RENAME COLUMN id_temp TO id;

ALTER TABLE clinics ADD PRIMARY KEY (id);
